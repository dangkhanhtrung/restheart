const oauthMiddlewares = require('./oauthServerMiddlewares');
const usersController = require('./controllers/users');
const clientsController = require('./controllers/clients');
var multer = require('multer');
var mongoose = require('mongoose');
var Grid = require('gridfs-stream');
Grid.mongo = mongoose.mongo;
const config = require('config');
var GridFsStorage = require('multer-gridfs-storage');

var conn = mongoose.createConnection(config.database);
var gfs = null;
conn.once('open', function () {
  gfs = Grid(conn.db);
})
var storage = GridFsStorage({
  url: config.database,
  gfs: gfs,
  file: (req, file) => {
    var datetimestamp = Date.now();
    console.log('req.params', req.params);
    console.log('req.query', req.query);
    return {
      bucketName: req.params.bucket,
      filename: file.fieldname + '-' + datetimestamp + '.' + file.originalname.split('.')[file.originalname.split('.').length - 1]
    };
  },
});
var upload = multer({ //multer settings for single upload
  storage: storage
}).single('file');

function initialize(app) {

  app.all('/oauth/token', oauthMiddlewares.token);

  app.get('/oauth/authorize', oauthMiddlewares.authorize);

  app.post('/oauth/authorize', oauthMiddlewares.authorize);

  app.post('/users', usersController.createUser);

  app.post('/clients', clientsController.createClient);

  app.get('/clients', clientsController.getClient);

  app.get('/oauth', oauthMiddlewares.authenticate, (req, res) => {
    delete req.user.user['password'];
    res.json(req.user);
  });
  /** API path that will upload the files */
  app.post('/upload/:bucket', function (req, res) {
    upload(req, res, function (err) {
      if (err) {
        res.json({ error_code: 1, err_desc: err });
        return;
      }
      res.json({ error_code: 0, err_desc: null });
    });
  });

  app.get('/file/:bucket/:filename', function (req, res) {
    gfs.collection(req.params.bucket); //set collection name to lookup into

    /** First check if file exists */
    gfs.files.find({ filename: req.params.filename }).toArray(function (err, files) {
      if (!files || files.length === 0) {
        return res.status(404).json({
          responseCode: 1,
          responseMessage: "error"
        });
      }
      /** create read stream */
      var readstream = gfs.createReadStream({
        filename: files[0].filename,
        root: req.params.bucket
      });
      /** set the proper content type */
      res.set('Content-Type', files[0].contentType)
      res.set("Cache-Control","max-age=0,must-revalidate");
      /** return response */
      return readstream.pipe(res);
    });
  });
}

module.exports = initialize;
