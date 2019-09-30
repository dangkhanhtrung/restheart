"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
var _this = this;
Object.defineProperty(exports, "__esModule", { value: true });
var schema_1 = require("../schema");
var graphql_compose_1 = require("graphql-compose");
var api_1 = require("../api");
schema_1.vuejxES.addResolver({
    name: 'findById',
    kind: 'query',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        id: 'String',
        keys: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxQueryService.findById(args.token, args.db, args.collection, args.id, args.keys)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    }
});
schema_1.vuejxES.addResolver({
    name: 'findByIds',
    kind: 'query',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        ids: 'JSON',
        limit: 'Int',
        sort: 'JSON',
        keys: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxQueryService.findByIds(args.token, args.db, args.collection, args.ids, args.limit, args.sort, args.keys)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    }
});
schema_1.vuejxES.addResolver({
    name: 'findOne',
    kind: 'query',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        filter: 'JSON',
        skip: 'Int',
        sort: 'JSON',
        keys: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxQueryService.findOne(args.token, args.db, args.collection, args.filter, args.skip, args.sort, args.keys)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    }
});
schema_1.vuejxES.addResolver({
    name: 'findMany',
    kind: 'query',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        filter: 'JSON',
        skip: 'Int',
        limit: 'Int',
        sort: 'JSON',
        keys: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxQueryService.findMany(args.token, args.db, args.collection, args.filter, args.skip, args.limit, args.sort, args.keys)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    }
});
schema_1.vuejxES.addResolver({
    name: 'countTotal',
    kind: 'query',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        filter: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxQueryService.countTotal(args.token, args.db, args.collection, args.filter)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    }
});
schema_1.vuejxES.addResolver({
    name: 'connection',
    kind: 'query',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        first: 'Int',
        after: 'String',
        last: 'Int',
        before: 'String',
        filter: 'JSON',
        sort: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                return [2 /*return*/, {
                        id: 123,
                        records: [
                            {
                                abc: 123,
                                dkm: 32323
                            }
                        ]
                    }];
            });
        });
    }
});
schema_1.vuejxES.addResolver({
    name: 'pagination',
    kind: 'query',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        page: 'Int',
        perPage: 'Int',
        filter: 'JSON',
        sort: 'JSON',
        keys: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxQueryService.pagination(args.token, args.db, args.collection, args.filter, args.sort, args.keys, args.page, args.perPage)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    }
});
schema_1.vuejxES.addResolver({
    name: 'pagination',
    kind: 'query',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        page: 'Int',
        perPage: 'Int',
        filter: 'JSON',
        sort: 'JSON',
        keys: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxQueryService.pagination(args.token, args.db, args.collection, args.filter, args.sort, args.keys, args.page, args.perPage)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    }
});
schema_1.vuejxES.addResolver({
    name: 'userDb',
    kind: 'query',
    type: 'JSON',
    args: {
        token: 'String'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxQueryService.userDb(args.token)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    }
});
schema_1.vuejxES.addResolver({
    name: 'userCollection',
    kind: 'query',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxQueryService.userCollection(args.token, args.db)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    }
});
graphql_compose_1.schemaComposer.Query.addFields({
    userById: schema_1.vuejxES.getResolver('findById'),
    userByIds: schema_1.vuejxES.getResolver('findByIds'),
    userOne: schema_1.vuejxES.getResolver('findOne'),
    userMany: schema_1.vuejxES.getResolver('findMany'),
    userTotal: schema_1.vuejxES.getResolver('countTotal'),
    userPagination: schema_1.vuejxES.getResolver('pagination'),
    userDb: schema_1.vuejxES.getResolver('userDb'),
    userCollection: schema_1.vuejxES.getResolver('userCollection')
});
schema_1.vuejxES.addResolver({
    name: 'createOne',
    kind: 'mutation',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        body: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.createOne(args.token, args.db, args.collection, args.body)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    },
});
schema_1.vuejxES.addResolver({
    name: 'createMany',
    kind: 'mutation',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        body: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.createMany(args.token, args.db, args.collection, args.body)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    },
});
schema_1.vuejxES.addResolver({
    name: 'updateById',
    kind: 'mutation',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        body: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.updateById(args.token, args.db, args.collection, args.body)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    },
});
schema_1.vuejxES.addResolver({
    name: 'updateOne',
    kind: 'mutation',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        body: 'JSON',
        filter: 'JSON',
        sort: 'JSON',
        skip: 'Int'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.updateOne(args.token, args.db, args.collection, args.body, args.filter, args.sort, args.skip)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    },
});
schema_1.vuejxES.addResolver({
    name: 'updateMany',
    kind: 'mutation',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        body: 'JSON',
        filter: 'JSON',
        sort: 'JSON',
        skip: 'Int',
        limit: 'Int'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.updateMany(args.token, args.db, args.collection, args.body, args.filter, args.sort, args.skip, args.limit)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    },
});
schema_1.vuejxES.addResolver({
    name: 'removeById',
    kind: 'mutation',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        id: 'String'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.removeById(args.token, args.db, args.collection, args.id)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    },
});
schema_1.vuejxES.addResolver({
    name: 'removeOne',
    kind: 'mutation',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        filter: 'JSON',
        sort: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.removeOne(args.token, args.db, args.collection, args.filter, args.sort)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    },
});
schema_1.vuejxES.addResolver({
    name: 'removeMany',
    kind: 'mutation',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        filter: 'JSON'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.removeMany(args.token, args.db, args.collection, args.filter)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    },
});
schema_1.vuejxES.addResolver({
    name: 'reindex',
    kind: 'mutation',
    type: 'JSON',
    args: {
        token: 'String',
        db: 'String',
        collection: 'String',
        from: 'String',
        until: 'String',
        type: 'String',
        all: 'String'
    },
    resolve: function (_a) {
        var args = _a.args;
        return __awaiter(_this, void 0, void 0, function () {
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.reindex(args.token, args.db, args.collection, args.from, args.until, args.type, args.all)];
                    case 1: return [2 /*return*/, _b.sent()];
                }
            });
        });
    },
});
graphql_compose_1.schemaComposer.Mutation.addFields({
    userCreate: schema_1.vuejxES.getResolver('createOne'),
    userCreateMany: schema_1.vuejxES.getResolver('createMany'),
    userUpdateById: schema_1.vuejxES.getResolver('updateById'),
    userUpdateOne: schema_1.vuejxES.getResolver('updateOne'),
    userUpdateMany: schema_1.vuejxES.getResolver('updateMany'),
    userRemoveById: schema_1.vuejxES.getResolver('removeById'),
    userRemoveOne: schema_1.vuejxES.getResolver('removeOne'),
    userRemoveMany: schema_1.vuejxES.getResolver('removeMany'),
    reindex: schema_1.vuejxES.getResolver('reindex'),
});
exports.default = graphql_compose_1.schemaComposer.buildSchema();
