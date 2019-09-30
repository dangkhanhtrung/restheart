"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var graphql_compose_elasticsearch_1 = require("graphql-compose-elasticsearch");
var elasticsearch_1 = require("../boostrap/elasticsearch");
var mapping = {
    properties: {
        type: { type: 'string', index: 'not_analyzed' },
        createdAt: { type: 'long', index: 'not_analyzed' },
        modifiedAt: { type: 'long', index: 'not_analyzed' },
        id: { type: 'string', index: 'not_analyzed' },
        uid: { type: 'string', index: 'not_analyzed' },
        author: { type: 'string', index: 'not_analyzed' },
        shortName: { type: 'string', index: 'not_analyzed' },
        hotContent: { type: 'integer', index: 'not_analyzed' },
        idRef: { type: 'string', index: 'not_analyzed' },
        order: { type: 'integer', index: 'not_analyzed' },
        status: { type: 'string', index: 'not_analyzed' },
        config: { type: 'string', index: 'not_analyzed' },
        site: { type: 'string', index: 'not_analyzed' },
        syncSite: { type: 'string', index: 'not_analyzed' }
    },
};
exports.vuejxMO = graphql_compose_elasticsearch_1.composeWithElastic({
    elasticIndex: 'admin',
    elasticType: '_doc',
    graphqlTypeName: 'vuejxMO',
    elasticMapping: mapping,
    elasticClient: elasticsearch_1.elasticClient
});
