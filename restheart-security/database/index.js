const mongoose = require('mongoose');
const config = require('config');
const OAuthAccessToken = require('./OAuthAccessToken');
const OAuthAuthorizationCode = require('./OAuthAuthorizationCode');
const OAuthClient = require('./OAuthClient');
const OAuthRefreshToken = require('./OAuthRefreshToken');
const OAuthScope = require('./OAuthScope');
const User = require('./User');


mongoose.Promise = Promise;

function connect() {
  mongoose.connect(config.database, {
    useNewUrlParser: true,
    user: process.env.MONGO_INITDB_OAUTH2_USERNAME,
    pass: process.env.MONGO_INITDB_OAUTH2_PASSWORD,
  }).then(() => {
    console.log('Mongoose Connected');
  }).catch((err) => {
    console.log(err);
  });
}

module.exports = {
  connect,
  OAuthAccessToken,
  OAuthAuthorizationCode,
  OAuthClient,
  OAuthRefreshToken,
  OAuthScope,
  User
};
