const oauthMiddlewares = require('./oauthServerMiddlewares');
const usersController = require('./controllers/users');
const clientsController = require('./controllers/clients');

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
  
}

module.exports = initialize;
