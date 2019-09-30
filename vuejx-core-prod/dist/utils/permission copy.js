"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
var _this = this;
Object.defineProperty(exports, "__esModule", { value: true });
var arrays_1 = require("./arrays");
var config_1 = require("../config");
var httpRequest_1 = require("../api/httpRequest");
var elasticsearch_1 = require("../boostrap/elasticsearch");
var token_1 = require("./token");
var config_2 = require("../config");
var msg403 = {
    message: "Access denied!",
    error: {
        "statusCode": 403,
        "status": 403,
        "code": 403,
        "message": "Access denied!",
        "name": "permission_denied"
    }
};
var msg409 = {
    message: "Conflict data!",
    error: {
        "statusCode": 409,
        "status": 409,
        "code": 409,
        "message": "Conflict data!",
        "name": "conflict_data"
    }
};
exports.deleteResourceCollection = function (path, db, collection, query) { return __awaiter(_this, void 0, void 0, function () {
    var httpReq, responeEtag, respone;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                httpReq = new httpRequest_1.default(config_1.PROJECT_SERVICE_HOST);
                return [4 /*yield*/, httpReq.fetch_rest(path + '/' + query.storeDb + '/' + query.shortName + '?pagesize=0', {})];
            case 1:
                responeEtag = _a.sent();
                return [4 /*yield*/, httpReq.delete_rest(path + '/' + query.storeDb + '/' + query.shortName, {
                        headers: {
                            'If-Match': responeEtag.headers['etag']
                        }
                    })];
            case 2:
                respone = _a.sent();
                if (respone.status === 204) {
                    return [2 /*return*/, null];
                }
                else {
                    return [2 /*return*/, msg409];
                }
                return [2 /*return*/];
        }
    });
}); };
exports.permission = function (path, user, url, ctx, elasticClient) { return __awaiter(_this, void 0, void 0, function () {
    var httpReq, urlPath, _a, root, db, collection, code, shortNameCHK, pullExits, shortNameCHK, pullExits, _b, respone, reps, respone;
    return __generator(this, function (_c) {
        switch (_c.label) {
            case 0:
                if (user === undefined || user === null || user === '') {
                    return [2 /*return*/, msg403];
                }
                httpReq = new httpRequest_1.default(config_1.PROJECT_SERVICE_HOST);
                urlPath = url;
                if (String(url).indexOf('?') > 0) {
                    urlPath = String(url).substring(0, String(url).indexOf('?'));
                }
                _a = urlPath.split('/'), root = _a[0], db = _a[1], collection = _a[2], code = _a[3];
                if (!(ctx.method === 'POST')) return [3 /*break*/, 5];
                return [4 /*yield*/, permissionWriteCollection(httpReq, collection, user)];
            case 1:
                if (!_c.sent()) return [3 /*break*/, 3];
                shortNameCHK = isNaN(ctx.request.body['shortName']) ? '"' + ctx.request.body['shortName'] + '"' : ctx.request.body['shortName'];
                return [4 /*yield*/, httpReq.fetch_rest(path + url + '?filter={"shortName": ' + shortNameCHK + '}', {})];
            case 2:
                pullExits = _c.sent();
                if (pullExits['data'].length > 0) {
                    return [2 /*return*/, msg409];
                }
                return [3 /*break*/, 4];
            case 3: return [2 /*return*/, msg403];
            case 4: return [3 /*break*/, 9];
            case 5:
                if (!(ctx.method === 'PUT')) return [3 /*break*/, 9];
                shortNameCHK = isNaN(ctx.request.body['shortName']) ? '"' + ctx.request.body['shortName'] + '"' : ctx.request.body['shortName'];
                return [4 /*yield*/, permissionWriteItem(httpReq, ctx.request.body['shortName'], user, db, collection)];
            case 6:
                if (!_c.sent()) return [3 /*break*/, 8];
                return [4 /*yield*/, httpReq.fetch_rest(path + url + '?filter={"shortName": ' + shortNameCHK + '}', {})];
            case 7:
                pullExits = _c.sent();
                if (pullExits['data'].length > 0) {
                    return [2 /*return*/, msg409];
                }
                return [3 /*break*/, 9];
            case 8: return [2 /*return*/, msg403];
            case 9:
                _b = db === 'vuejx_cfg';
                if (!_b) return [3 /*break*/, 11];
                return [4 /*yield*/, arrays_1.found(user['roles'], ['admin'])];
            case 10:
                _b = (_c.sent());
                _c.label = 11;
            case 11:
                if (!_b) return [3 /*break*/, 19];
                if (!(collection === 'vuejx_db')) return [3 /*break*/, 13];
                return [4 /*yield*/, httpReq.put_rest(path + '/' + ctx.request.body['shortName'], {})];
            case 12:
                respone = _c.sent();
                if (respone.status === 201 || respone.status === 200) {
                    return [2 /*return*/, null];
                }
                else {
                    return [2 /*return*/, msg409];
                }
                return [3 /*break*/, 19];
            case 13:
                if (!(collection === 'vuejx_collection')) return [3 /*break*/, 18];
                if (!(ctx.method === 'DELETE')) return [3 /*break*/, 15];
                return [4 /*yield*/, exports.deleteResourceCollection(path, db, collection, ctx.query)];
            case 14:
                reps = _c.sent();
                if (reps !== null) {
                    return [2 /*return*/, msg409];
                }
                return [3 /*break*/, 17];
            case 15:
                if (!(ctx.request.body['storeDb'] !== undefined && ctx.request.body['storeDb'] !== '' && ctx.request.body['storeDb'] !== null)) return [3 /*break*/, 17];
                return [4 /*yield*/, httpReq.put_rest(path + '/' + ctx.request.body['storeDb'] + '/' + ctx.request.body['shortName'], {})];
            case 16:
                respone = _c.sent();
                if (respone.status === 201 || respone.status === 200) {
                    elasticsearch_1.createIndex(elasticClient, ctx.request.body['storeDb'] + '___' + ctx.request.body['shortName']);
                    return [2 /*return*/, null];
                }
                else {
                    return [2 /*return*/, msg409];
                }
                _c.label = 17;
            case 17: return [2 /*return*/, null];
            case 18: return [2 /*return*/, null];
            case 19:
                if (db !== 'vuejx_cfg') {
                    return [2 /*return*/, null];
                }
                else {
                    return [2 /*return*/, msg403];
                }
                return [2 /*return*/];
        }
    });
}); };
exports.permissionQuery = function (user, url) { return __awaiter(_this, void 0, void 0, function () {
    var paramsDes, regex, params, match, roleQuery, _i, _a, role;
    return __generator(this, function (_b) {
        paramsDes = {};
        if (user === undefined || user === null || user === '') {
            user = {
                username: 'guest',
                roles: ['guest']
            };
        }
        else {
            user = JSON.parse(user);
        }
        regex = /[?&]([^=#]+)=([^&#]*)/g, params = {}, match = {};
        while (match = regex.exec("&" + url.substring(url.indexOf('?') + 1))) {
            params[match[1]] = decodeURIComponent(match[2]);
        }
        if (params.hasOwnProperty('filter')) {
            params['filter'] = JSON.parse(params['filter']);
            roleQuery = [
                { "roleRead": { $exists: false } },
            ];
            for (_i = 0, _a = user['roles']; _i < _a.length; _i++) {
                role = _a[_i];
                roleQuery.push({
                    "roleRead": role
                });
            }
            if (params['filter'].hasOwnProperty('$and')) {
                params['filter']['$and'].push({
                    "$or": roleQuery
                });
            }
            else {
                params['filter']["$or"] = roleQuery;
            }
        }
        paramsDes = params;
        return [2 /*return*/, paramsDes];
    });
}); };
exports.permissionQueryES = function (body, token) { return __awaiter(_this, void 0, void 0, function () {
    var decodeToken, mustRole, decodeToken_1, isAdmin, _i, _a, role;
    return __generator(this, function (_b) {
        switch (_b.label) {
            case 0:
                decodeToken = null;
                if (!(typeof token === 'string')) return [3 /*break*/, 2];
                return [4 /*yield*/, token_1.verify(token)];
            case 1:
                decodeToken = _b.sent();
                return [3 /*break*/, 3];
            case 2:
                decodeToken = token;
                _b.label = 3;
            case 3:
                if (decodeToken.hasOwnProperty('tokenExpired') && decodeToken['tokenExpired']) {
                    token === '';
                }
                mustRole = {
                    "bool": {
                        "should": [
                            {
                                "bool": {
                                    "must_not": [
                                        {
                                            "exists": {
                                                "field": "roleRead"
                                            }
                                        }
                                    ]
                                }
                            },
                            {
                                "match": {
                                    "roleRead._source.shortName": "guest"
                                }
                            }
                        ]
                    }
                };
                if (!(token === '')) return [3 /*break*/, 4];
                if (body['query'] !== undefined) {
                    if (body['query']['bool'] !== undefined && body['query']['bool']['must'] === undefined) {
                        body['query']['bool']['must'] = [
                            mustRole
                        ];
                    }
                    else if (body['query']['bool'] !== undefined && body['query']['bool']['must'] !== undefined) {
                        body['query']['bool']['must'].push(mustRole);
                    }
                    else {
                        body['query']['bool'] = {
                            'must': [
                                mustRole
                            ]
                        };
                    }
                }
                else {
                    body['query'] = {
                        'bool': {
                            'must': [
                                mustRole
                            ]
                        }
                    };
                }
                return [3 /*break*/, 7];
            case 4: return [4 /*yield*/, token_1.verify(token)];
            case 5:
                decodeToken_1 = _b.sent();
                return [4 /*yield*/, arrays_1.found(decodeToken_1['roles'], ['admin'])];
            case 6:
                isAdmin = _b.sent();
                if (!isAdmin) {
                    for (_i = 0, _a = decodeToken_1['roles']; _i < _a.length; _i++) {
                        role = _a[_i];
                        mustRole['bool']['should'].push({
                            "match": {
                                "roleRead._source.shortName": role
                            }
                        });
                    }
                }
                else {
                    mustRole['bool']['should'] = [];
                }
                if (body['query'] !== undefined) {
                    if (body['query']['bool'] !== undefined && body['query']['bool']['must'] === undefined) {
                        body['query']['bool']['must'] = [
                            mustRole
                        ];
                    }
                    else if (body['query']['bool'] !== undefined && body['query']['bool']['must'] !== undefined) {
                        body['query']['bool']['must'].push(mustRole);
                    }
                    else {
                        body['query']['bool'] = {
                            'must': [
                                mustRole
                            ]
                        };
                    }
                }
                else {
                    body['query'] = {
                        'bool': {
                            'must': [
                                mustRole
                            ]
                        }
                    };
                }
                _b.label = 7;
            case 7: return [2 /*return*/, body];
        }
    });
}); };
var permissionWriteCollection = function (httpReq, shortName, user) { return __awaiter(_this, void 0, void 0, function () {
    var result, body, isAdmin, _i, _a, role, api, response;
    return __generator(this, function (_b) {
        switch (_b.label) {
            case 0:
                result = false;
                body = {};
                body['query'] = {
                    'bool': {
                        'must': [
                            {
                                "bool": {
                                    "should": [
                                        {
                                            "bool": {
                                                "must_not": [
                                                    {
                                                        "exists": {
                                                            "field": "roleWrite"
                                                        }
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            "match": {
                                                "roleWrite._source.shortName": "guest"
                                            }
                                        }
                                    ]
                                }
                            },
                            {
                                "match": {
                                    "shortName": shortName
                                }
                            }
                        ]
                    }
                };
                return [4 /*yield*/, arrays_1.found(user['roles'], ['admin'])];
            case 1:
                isAdmin = _b.sent();
                if (!isAdmin) {
                    for (_i = 0, _a = user['roles']; _i < _a.length; _i++) {
                        role = _a[_i];
                        body['query']['bool']['must'][0]['bool']['should'].push({
                            "match": {
                                "roleWrite._source.shortName": role
                            }
                        });
                    }
                }
                else {
                    body['query']['bool']['must'][0]['bool']['should'] = [];
                }
                api = '/'.concat('vuejx_cfg')
                    .concat('___')
                    .concat('vuejx_collection')
                    .concat('/')
                    .concat('_search?request_cache=true');
                return [4 /*yield*/, httpReq.search_rest(config_2.esConfig.host + api, body)];
            case 2:
                response = _b.sent();
                if (response.data.hits.total.value > 0) {
                    result = true;
                }
                return [2 /*return*/, result];
        }
    });
}); };
var permissionWriteItem = function (httpReq, shortName, user, db, collection) { return __awaiter(_this, void 0, void 0, function () {
    var result, body, isAdmin, _i, _a, role, api, response;
    return __generator(this, function (_b) {
        switch (_b.label) {
            case 0:
                result = false;
                body = {};
                body['query'] = {
                    'bool': {
                        'must': [
                            {
                                "bool": {
                                    "should": [
                                        {
                                            "bool": {
                                                "must_not": [
                                                    {
                                                        "exists": {
                                                            "field": "roleWrite"
                                                        }
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            "match": {
                                                "roleWrite._source.shortName": "guest"
                                            }
                                        }
                                    ]
                                }
                            },
                            {
                                "match": {
                                    "shortName": shortName
                                }
                            }
                        ]
                    }
                };
                return [4 /*yield*/, arrays_1.found(user['roles'], ['admin'])];
            case 1:
                isAdmin = _b.sent();
                if (!isAdmin) {
                    for (_i = 0, _a = user['roles']; _i < _a.length; _i++) {
                        role = _a[_i];
                        body['query']['bool']['must'][0]['bool']['should'].push({
                            "match": {
                                "roleWrite._source.shortName": role
                            }
                        });
                    }
                }
                else {
                    body['query']['bool']['must'][0]['bool']['should'] = [];
                }
                api = '/'.concat(db)
                    .concat('___')
                    .concat(collection)
                    .concat('/')
                    .concat('_search?request_cache=true');
                return [4 /*yield*/, httpReq.search_rest(config_2.esConfig.host + api, body)];
            case 2:
                response = _b.sent();
                if (response.data.hits.total.value > 0) {
                    result = true;
                }
                return [2 /*return*/, result];
        }
    });
}); };
