const _ = require('lodash');
const db = require('./database');

const User = db.User;
const OAuthClient = db.OAuthClient;
const OAuthAccessToken = db.OAuthAccessToken;
const OAuthAuthorizationCode = db.OAuthAuthorizationCode;
const OAuthRefreshToken = db.OAuthRefreshToken;
const utils = require('./utils');
const crypto = require('crypto');

function getAccessToken(accessToken) {
  return OAuthAccessToken
    .findOne({ accessToken })
    .populate('user')
    .populate('client')
    .lean()
    .then(dbToken => dbToken)
    .catch((err) => {
      console.log('getAccessToken - Err: ', err);
    });
}

function getClient(clientId, clientSecret) {
  const query = { clientId };
  if (clientSecret) {
    query.clientSecret = clientSecret;
  }

  return OAuthClient
    .findOne(query)
    .lean()
    .then(client => (client ? Object.assign(client, { id: clientId }) : null))
    .catch((err) => {
      console.log('getClient - Err: ', err);
    });
}


async function getUser(username, password) {
  // TODO: Hashing of password
  const userExit = await utils.Query.findOne(User, { username: username });
  if (userExit) {
    if (userExit['password'] === crypto.createHash('sha256').update(password).digest('hex')) {
      return userExit
    } else {
      return null;
    }
  } else {
    return null;
  }
}

function revokeAuthorizationCode(code) {
  return OAuthAuthorizationCode.findOneAndRemove({ code: code.code })
    .then(removed => !!removed)
    .catch((err) => {
      console.log('revokeAuthorizationCode - Err: ', err);
    });
}

function revokeToken(token) {
  return OAuthRefreshToken.findOneAndRemove({ refreshToken: token.refreshToken })
    .then(removed => !!removed)
    .catch((err) => {
      console.log('revokeToken - Err: ', err);
    });
}


function saveToken(token, client, user) {
  return Promise.all([
    OAuthAccessToken.create({
      accessToken: token.accessToken,
      accessTokenExpiresAt: token.accessTokenExpiresAt,
      client: client._id,
      user: user._id,
      scope: token.scope
    }),
    token.refreshToken ? OAuthRefreshToken.create({ // no refresh token for client_credentials
      refreshToken: token.refreshToken,
      refreshTokenExpiresAt: token.refreshTokenExpiresAt,
      client: client._id,
      user: user._id,
      scope: token.scope
    }) : Promise.resolve()
  ])
    .then(() => _.assign({ client, user }, token))
    .catch((err) => {
      console.log('revokeToken - Err: ', err);
    });
}

function getAuthorizationCode(code) {
  return OAuthAuthorizationCode
    .findOne({ code })
    .populate('user')
    .populate('client')
    .lean()
    .then((authCodeModel) => {
      if (!authCodeModel) {
        return false;
      }

      const extendedClient = Object.assign(authCodeModel.client, { id: authCodeModel.client.clientId });
      return Object.assign(authCodeModel, { client: extendedClient });
    })
    .catch((err) => {
      console.log('getAuthorizationCode - Err: ', err);
    });
}

function saveAuthorizationCode(code, client, user) {
  return OAuthAuthorizationCode
    .create({
      expiresAt: code.expiresAt,
      client: client._id,
      code: code.authorizationCode,
      user: user._id,
      scope: code.scope
    })
    .then(() => ({ // TODO: Consider changing expiresAt to expiresIn (seconds)
      authorizationCode: code.authorizationCode,
      authorization_code: code.authorizationCode,
      expires_in: Math.floor((code.expiresAt - new Date()) / 1000)
    }))
    .catch((err) => {
      console.log('saveAuthorizationCode - Err: ', err);
    });
}

function getUserFromClient(client) {
  return User.findById(client.user)
    .lean()
    .then(dbUser => dbUser)
    .catch((err) => {
      console.log('getUserFromClient - Err: ', err);
    });
}

function getRefreshToken(refreshToken) {
  return OAuthRefreshToken
    .findOne({ refreshToken })
    .populate('user')
    .populate('client')
    .lean()
    .then((dbToken) => {
      if (!dbToken) {
        return false;
      }

      const extendedClient = Object.assign(dbToken.client, { id: dbToken.client.clientId });
      return Object.assign(dbToken, { client: extendedClient });
    })
    .catch((err) => {
      console.log('getRefreshToken - Err: ', err);
    });
}

/**
In case there is a need to scopes for the user, uncomment the code.
It will also be required to provide scopes for both user and client
*/
// eslint-disable-next-line
function validateScope(user, client, scope) {
  // console.log('validateScope', user, client, scope);
  // return (user.scope === scope && client.scope === scope && scope !== null) ? scope: false;
  return '*';
}

/**
In case there is a need to scopes for the user, uncomment the code.
It will also be required to provide scopes for both user and client (They should also match)
*/
// eslint-disable-next-line
function verifyScope(token, scope) {
  // console.log('verifyScope', token, scope);
  // return token.scope === scope;
  return true;
}

module.exports = {
  // generateAccessToken(client, user, scope) optional
  // generateAuthorizationCode(), optional
  // generateRefreshToken(client, user, scope) - optional
  getAccessToken,
  getAuthorizationCode,
  getClient,
  getRefreshToken,
  getUser,
  getUserFromClient,
  revokeAuthorizationCode,
  revokeToken,
  saveToken,
  saveAuthorizationCode,
  validateScope,
  verifyScope
};
