var express = require('express');
var serveStatic = require('serve-static');
var port = process.env.PORT || 8801;
var hostname = process.env.HOST || 'localhost';
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
  app = express();
  app.use(serveStatic(__dirname + "/dist"));
  app.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
  });
}
