const crypto = require('crypto');
const db = require('../database');
const utils = require('../utils');

const OAuthClient = db.OAuthClient;

module.exports.createClient = async (req, res) => {
  const client = new OAuthClient(req.body);
  const clientExit = await utils.Query.findOne(OAuthClient, { name: client.name });

  if (clientExit) {
    res.json(clientExit)
  } else {
    client.clientId = crypto.createHash('md5').update(crypto.randomBytes(16)).digest('hex'); // 32 chars
    client.clientSecret = crypto.createHash('sha256').update(crypto.randomBytes(32)).digest('hex'); // 64 chars
    client.scope = 'profile';

    client.save()
      .then(() => res.json({ id: client }));
  }
};
module.exports.createClientGet = async (req, res) => {
  console.log(req.query);
  
  await createClient(req, res)
};
module.exports.getClient = (req, res) => {
  OAuthClient.findOne({ name: req.query.name })
    .then((client) => {
      res.json(client);
    });
};
