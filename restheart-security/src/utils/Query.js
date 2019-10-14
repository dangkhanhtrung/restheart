module.exports.findOne = (collection, body) => {
    return new Promise((resolve, reject) => {
        collection.findOne(body, (err, results) => {
            (err) ? reject(err) : resolve(results);
        });
    });
};
