module.exports = {
  database: 'mongodb://' + process.env.MONGO_INITDB_OAUTH2_USERNAME +':' + process.env.MONGO_INITDB_OAUTH2_PASSWORD +'@' + process.env.MONGO_HOSTS + '/' + process.env.MONGO_INITDB_DATABASE
};

