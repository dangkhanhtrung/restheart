const express = require('express');
const logger = require('morgan');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const bluebird = require('bluebird');
const authRoutes = require('./routes');
const database = require('./database');
const cluster = require('cluster');
const cpuCount = process.env.CLUSTER_NODE;
global.Promise = bluebird;

database.connect();

// Code to run if we're in the master process
if (cluster.isMaster) {

  // Count the machine's CPUs
  //var cpuCount = require('os').cpus().length;

  // Create a worker for each CPU
  for (var i = 0; i < cpuCount; i += 1) {
    cluster.fork();
  }

  // Listen for dying workers
  cluster.on('exit', function (worker) {

    // Replace the dead worker, we're not sentimental
    console.log('Worker %d died :(', worker.id);

    cluster.fork();

  });

  // Code to run if we're in a worker process
} else {

  const app = express();

  app.use(logger('dev'));
  app.use(bodyParser.json());
  app.use(bodyParser.urlencoded({ extended: false }));
  app.use(cookieParser());

  authRoutes(app);

  app.use((req, res, next) => {
    const err = new Error('Not Found');
    err.status = 404;
    next(err);
  });

  app.use((err, req, res, next) => {
    res.status(err.status || 500);
    res.json({
      message: err.message,
      error: err
    });
  });

  app.listen(3000, err => (err ? console.log('Error happened', err) : console.log('Server is up!')));

}
