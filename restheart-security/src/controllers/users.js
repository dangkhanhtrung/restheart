const db = require('../database');
const crypto = require('crypto');
const utils = require('../utils');

const User = db.User;

module.exports.createUser = async (req, res) => {
  let user = new User(req.body);
  const userExit = await utils.Query.findOne(User, { username: user.username });
  if (userExit) {
    if (userExit.password === crypto.createHash('sha256').update(user.password).digest('hex')) {
      user = userExit;
      res.json({ id: user._id });
    } else {
      res.json({ msg: 'password not match!', id: 0 });
    }
  } else {
    user.password = crypto.createHash('sha256').update(user.password).digest('hex');
    user.save()
    .then(() => res.json({ id: user._id }));
  }
};
