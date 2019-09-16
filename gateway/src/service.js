var monitor = require('node-docker-monitor');
var http = require('http');
var httpProxy = require('http-proxy');
var parseurl = require('parseurl');
var axios = require('axios');
const fs = require("fs");
var path = require('path');
const jsonwebtoken = require('jsonwebtoken');
const SECREST_KEY = process.env.SECREST_KEY; 
const cpuCount = process.env.CLUSTER_NODE; 
const cluster = require('cluster');

if (cluster.isMaster) {
    for (var i = 0; i < cpuCount; i += 1) {
        cluster.fork();
    }
    cluster.on('exit', function (worker) {
        console.log('Worker %d died :(', worker.id);
        cluster.fork();
    });
  } else {
    const verify = async (token) => {
        const result = await jsonwebtoken.verify(token, SECREST_KEY, function(err, decoded) {
            if (err) {
                return {
                    tokenExpired: true,
                    name: 'TokenExpiredError',
                    message: 'jwt expired',
                    expiredAt: new Date(err.expiredAt).getTime()
                };
            }
            return decoded;
        });
        return result;    
    };
    const mimeLookup = {
        '.js': 'application/javascript',
        '.css': 'text/css; charset=UTF-8',
        '.png': 'image/png',
        '.jpg': 'image/jpeg',
        '.jpeg': 'image/jpeg',
    };
    var dockerOpts = { socketPath: process.env.DOCKER_SOCKET };
    if (!dockerOpts.socketPath) {
        dockerOpts.host = process.env.DOCKER_HOST;
        dockerOpts.port = process.env.DOCKER_PORT;
        if (!dockerOpts.host) {
            dockerOpts.socketPath = '/var/run/docker.sock';
        }
    }
    var httpPort = process.env.HTTP_HOST || 8080;
    var routes = {};
    console.log('Connecting to Docker: %j', dockerOpts);
    monitor({
        onContainerUp: function (containerInfo, docker) {
            if (containerInfo.Labels && containerInfo.Labels.api_route) {
                var container = docker.getContainer(containerInfo.Id);
                container.inspect(function (err, containerDetails) {
                    if (err) {
                        console.log('Error getting container details for: %j', containerInfo, err);
                    } else {
                        try {
                            var route = {
                                apiRoute: containerInfo.Labels.api_route,
                                upstreamUrl: getUpstreamUrl(containerDetails)
                            };
                            routes[containerInfo.Id] = route;
                            console.log('Registered new api route:', route);
                        } catch (e) {
                            console.log('Error creating new api route for: %j', containerDetails, e);
                        }
                    }
                });
            }
        },
        onContainerDown: function (container) {
            if (container.Labels && container.Labels.api_route) {
                var route = routes[container.Id];
                if (route) {
                    delete routes[container.Id];
                    console.log('Removed api route: %j', route);
                }
            }
        }
    }, dockerOpts);
    var server = http.createServer(async function (req, res) {
        for (id in routes) {
            if (id !== '' && await handleRoute(routes[id], req, res)) {
                return;
            }
        }
        if (req.method == 'GET' && path !== undefined && (req.url.startsWith('/static/'))) {
            let fileurl = req.url;
            let filepath = path.resolve('/app' + fileurl);
            let fileExt = path.extname(filepath);
            let mimeType = mimeLookup[fileExt];
            fs.exists(filepath, (exists) => {
                if (exists) {
                    if (mimeType !== undefined && mimeType !== null && mimeType !== '') {
                        res.setHeader('Content-Type', mimeType);
                        res.setHeader('Accept-Ranges', 'bytes');
                        res.setHeader('Transfer-Encoding', 'chunked');
                        res.setHeader('Vary', 'Accept-Encoding');
                        res.setHeader('X-Powered-By', 'Express');
                    }
                    fs.createReadStream(filepath).pipe(res);
                } else {
                    fs.createReadStream('/app/static/empty').pipe(res);
                }
            });
        } else {
            returnError(req, res);
        }
    });
    server.listen(httpPort);
    var proxy = httpProxy.createProxyServer();
    proxy.on('error', function (err, req, res) {
        console.log('err: ', err);
        returnError(req, res);
    });
    function unAuthorMsg(res, message) {
        res.writeHead(401, { 'Content-Type': 'application/json' });
        res.write(JSON.stringify({
            "message": message,
            "error": {
                "statusCode": 401,
                "status": 401,
                "code": 401,
                "message": message,
                "name": "unauthorized_request"
            }
        }));
        res.end();
    }
    function oauth(req) {
        return new Promise((resolve, reject) => {
            axios.get('http://security:3000/oauth', {
                headers: { 'Authorization': req.headers['authorization'] !== undefined ? req.headers['authorization'] : '' }
            }).then(function (response) {
                resolve(response.data);
            }).catch(function () {
                resolve(null);
            })
        });
    };
    async function handleRoute(route, req, res) {
        var url = req.url;
        var parsedUrl = parseurl(req);
        if (parsedUrl.path !== null && parsedUrl.path.indexOf(route.apiRoute) === 0) {
            var isAuthor = false;
            let oauthInfo = null;
            if ((route.apiRoute.endsWith('/core') || route.apiRoute.endsWith('/secure') || parsedUrl.path.indexOf('/secure') > 0) && parsedUrl.path.indexOf('/unsecure') < 0) {
                const decodeToken = await verify(req.headers.token);
                if (decodeToken.hasOwnProperty('tokenExpired') && decodeToken['tokenExpired'] || !req.headers.hasOwnProperty('token')) {
                    oauthInfo = await oauth(req);
                    if (oauthInfo !== null) {
                        if (new Date(oauthInfo['accessTokenExpiresAt']).getTime() > new Date().getTime()) {
                            isAuthor = true;
                            Object.assign(req.headers, { user: JSON.stringify(decodeToken) });
                        } else {
                            unAuthorMsg(res, 'TokenExpiresAt: ' + oauthInfo['accessTokenExpiresAt']);
                        }
                    } else {
                        unAuthorMsg(res, 'Unauthorized');
                    }
                } else {
                    Object.assign(req.headers, { user: JSON.stringify(decodeToken) });
                    isAuthor = true;
                }
            } else if (route.apiRoute.endsWith('/core') && parsedUrl.path.indexOf('/unsecure') > 0) {
                Object.assign(req.headers, { user: null });
                isAuthor = true;
            } else {
                isAuthor = true;
            }
            if (isAuthor) {
                if (route.apiRoute.endsWith('/core')) {
                    route.upstreamUrl = process.env.SERVICE_SECURITY
                }
                req.url = url.replace(route.apiRoute, '');
                proxy.web(req, res, { target: route.upstreamUrl });
            }
            return true;
        }
    }
    function getUpstreamUrl(containerDetails) {
        var ports = containerDetails.NetworkSettings.Ports;
        for (id in ports) {
            if (ports.hasOwnProperty(id)) {
                return 'http://' + containerDetails.NetworkSettings.Networks.restheart_restheart_network.IPAddress + ':' + id.split('/')[0];
            }
        }
    }
    function returnError(req, res) {
        res.writeHead(502, { 'Content-Type': 'text/plain' });
        res.write('Bad Gateway for: ' + req.url);
        res.end();
    }
}