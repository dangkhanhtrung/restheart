module.exports = {
  database: 'mongodb://' + process.env.MONGO_INITDB_ROOT_USERNAME +':' + process.env.MONGO_INITDB_ROOT_PASSWORD +'@' + process.env.WAIT_HOSTS + '/' + process.env.MONGO_INITDB_DATABASE
};

