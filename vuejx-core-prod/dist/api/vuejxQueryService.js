"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
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
var __asyncValues = (this && this.__asyncValues) || function (o) {
    if (!Symbol.asyncIterator) throw new TypeError("Symbol.asyncIterator is not defined.");
    var m = o[Symbol.asyncIterator], i;
    return m ? m.call(o) : (o = typeof __values === "function" ? __values(o) : o[Symbol.iterator](), i = {}, verb("next"), verb("throw"), verb("return"), i[Symbol.asyncIterator] = function () { return this; }, i);
    function verb(n) { i[n] = o[n] && function (v) { return new Promise(function (resolve, reject) { v = o[n](v), settle(resolve, reject, v.done, v.value); }); }; }
    function settle(resolve, reject, d, v) { Promise.resolve(v).then(function(v) { resolve({ value: v, done: d }); }, reject); }
};
Object.defineProperty(exports, "__esModule", { value: true });
var httpRequest_1 = require("./httpRequest");
var config_1 = require("../config");
var arrays_1 = require("../utils/arrays");
var permission_utils_1 = require("../utils/permission_utils");
var config_2 = require("../config");
var ObjectId = require('mongodb').ObjectId;
var api_1 = require("../api");
var jsonMapper = require('json-mapper-json');
var oai_pmh_1 = require("oai-pmh");
var vuejxQueryControl = /** @class */ (function (_super) {
    __extends(vuejxQueryControl, _super);
    function vuejxQueryControl(path) {
        var _this = _super.call(this, config_1.PROJECT_SERVICE_HOST) || this;
        _this.path = "" + config_1.PROJECT_SERVICE_HOST;
        return _this;
    }
    vuejxQueryControl.prototype.findById = function (token, db, collection, id, keys) {
        return __awaiter(this, void 0, void 0, function () {
            var user, filter, detail;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.defaultBody(token, {}, collection, false)];
                    case 1:
                        user = _a.sent();
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, {}, { _id: new ObjectId(id) }, keys)];
                    case 2:
                        filter = _a.sent();
                        if (filter.hasOwnProperty('error') && filter['error']['statusCode'] === 403) {
                            return [2 /*return*/, permission_utils_1.msg403];
                        }
                        return [4 /*yield*/, this.findData(db, collection, filter, { modifiedAt: -1 }, 0, 1, keys)];
                    case 3:
                        detail = _a.sent();
                        if (String(detail).length === 0) {
                            return [2 /*return*/, {
                                    statusCode: 404
                                }];
                        }
                        else {
                            return [2 /*return*/, detail[0]];
                        }
                        return [2 /*return*/];
                }
            });
        });
    };
    vuejxQueryControl.prototype.findByIds = function (token, db, collection, ids, limit, sort, keys) {
        return __awaiter(this, void 0, void 0, function () {
            var user, defaultFilter, key, filter, detail;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.defaultBody(token, {}, collection, false)];
                    case 1:
                        user = _a.sent();
                        defaultFilter = {
                            "$or": []
                        };
                        for (key in ids) {
                            defaultFilter['$or'].push({
                                _id: new ObjectId(ids[key])
                            });
                        }
                        if (sort === undefined || sort === null || sort === '') {
                            sort = { modifiedAt: -1 };
                        }
                        if (limit === undefined || limit === null || limit === '') {
                            limit = 10;
                        }
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, {}, defaultFilter, keys)];
                    case 2:
                        filter = _a.sent();
                        if (filter.hasOwnProperty('error') && filter['error']['statusCode'] === 403) {
                            return [2 /*return*/, permission_utils_1.msg403];
                        }
                        return [4 /*yield*/, this.findData(db, collection, filter, sort, 0, limit, keys)];
                    case 3:
                        detail = _a.sent();
                        return [4 /*yield*/, this.buildRespone(detail)];
                    case 4: return [2 /*return*/, _a.sent()];
                }
            });
        });
    };
    vuejxQueryControl.prototype.findOne = function (token, db, collection, filter, skip, sort, keys) {
        return __awaiter(this, void 0, void 0, function () {
            var user, limit, detail;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.defaultBody(token, {}, collection, false)];
                    case 1:
                        user = _a.sent();
                        if (sort === undefined || sort === null || sort === '') {
                            sort = { modifiedAt: -1 };
                        }
                        if (skip === undefined || skip === null || skip === '') {
                            skip = 0;
                        }
                        limit = 1;
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, {}, filter, keys)];
                    case 2:
                        filter = _a.sent();
                        if (filter.hasOwnProperty('error') && filter['error']['statusCode'] === 403) {
                            return [2 /*return*/, permission_utils_1.msg403];
                        }
                        return [4 /*yield*/, this.findData(db, collection, filter, sort, skip, limit, keys)];
                    case 3:
                        detail = _a.sent();
                        if (String(detail).length === 0) {
                            return [2 /*return*/, {
                                    statusCode: 404
                                }];
                        }
                        else {
                            return [2 /*return*/, detail[0]];
                        }
                        return [2 /*return*/];
                }
            });
        });
    };
    vuejxQueryControl.prototype.findMany = function (token, db, collection, filter, skip, limit, sort, keys) {
        return __awaiter(this, void 0, void 0, function () {
            var user, detail;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.defaultBody(token, {}, collection, false)];
                    case 1:
                        user = _a.sent();
                        if (sort === undefined || sort === null || sort === '') {
                            sort = { modifiedAt: -1 };
                        }
                        if (skip === undefined || skip === null || skip === '') {
                            skip = 0;
                        }
                        if (limit === undefined || limit === null || limit === '') {
                            limit = 10;
                        }
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, {}, filter, keys)];
                    case 2:
                        filter = _a.sent();
                        if (filter.hasOwnProperty('error') && filter['error']['statusCode'] === 403) {
                            return [2 /*return*/, permission_utils_1.msg403];
                        }
                        return [4 /*yield*/, this.findData(db, collection, filter, sort, skip, limit, keys)];
                    case 3:
                        detail = _a.sent();
                        return [4 /*yield*/, this.buildRespone(detail)];
                    case 4: return [2 /*return*/, _a.sent()];
                }
            });
        });
    };
    vuejxQueryControl.prototype.countTotal = function (token, db, collection, filter) {
        return __awaiter(this, void 0, void 0, function () {
            var user, newFilter;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.defaultBody(token, {}, collection, false)];
                    case 1:
                        user = _a.sent();
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, {}, filter, {})];
                    case 2:
                        newFilter = _a.sent();
                        if (newFilter.hasOwnProperty('error') && newFilter['error']['statusCode'] === 403) {
                            return [2 /*return*/, permission_utils_1.msg403];
                        }
                        return [4 /*yield*/, config_2.getClient().db(db).collection(collection).find(newFilter).count()];
                    case 3: return [2 /*return*/, _a.sent()];
                }
            });
        });
    };
    vuejxQueryControl.prototype.pagination = function (token, db, collection, filter, sort, keys, page, perPage) {
        return __awaiter(this, void 0, void 0, function () {
            var user, skip, limit, total, detail, result;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.defaultBody(token, {}, collection, false)];
                    case 1:
                        user = _a.sent();
                        skip = 0, limit = 10;
                        if (sort === undefined || sort === null || sort === '') {
                            sort = { modifiedAt: -1 };
                        }
                        if (page !== undefined && page !== null && page !== '') {
                            skip = page * perPage - perPage;
                        }
                        if (perPage !== undefined && perPage !== null && perPage !== '') {
                            limit = perPage;
                        }
                        return [4 /*yield*/, this.countTotal(token, db, collection, filter)];
                    case 2:
                        total = _a.sent();
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, {}, filter, keys)];
                    case 3:
                        filter = _a.sent();
                        if (filter.hasOwnProperty('error') && filter['error']['statusCode'] === 403) {
                            return [2 /*return*/, permission_utils_1.msg403];
                        }
                        return [4 /*yield*/, this.findData(db, collection, filter, sort, skip, limit, keys)];
                    case 4:
                        detail = _a.sent();
                        return [4 /*yield*/, this.buildRespone(detail)];
                    case 5:
                        result = _a.sent();
                        result['hits']['total'] = {
                            relation: "eq",
                            value: total
                        };
                        return [2 /*return*/, result];
                }
            });
        });
    };
    vuejxQueryControl.prototype.workflowAction = function (token, workflow, parent) {
        return __awaiter(this, void 0, void 0, function () {
            var user, detail, access, _i, _a, role, filter, detail;
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.defaultBody(token, {}, '', false)];
                    case 1:
                        user = _b.sent();
                        return [4 /*yield*/, arrays_1.found(user['role'], ['admin'])];
                    case 2:
                        if (!_b.sent()) return [3 /*break*/, 5];
                        return [4 /*yield*/, this.findData('vuejx_cfg', 'vuejx_workflow_action', {
                                parent: parent,
                                workflow: workflow
                            }, {}, 0, 1000, null)];
                    case 3:
                        detail = _b.sent();
                        return [4 /*yield*/, this.buildRespone(detail)];
                    case 4: return [2 /*return*/, _b.sent()];
                    case 5:
                        access = [];
                        for (_i = 0, _a = user['role']; _i < _a.length; _i++) {
                            role = _a[_i];
                            access.push({
                                "workflowRole._source.shortName": role
                            });
                        }
                        filter = {
                            "$and": []
                        };
                        filter["$and"].push({
                            parent: parent
                        });
                        filter["$and"].push({
                            workflow: workflow
                        });
                        if (access.length > 0) {
                            access.push({
                                "workflowRole": {
                                    $exists: false
                                }
                            });
                            filter["$and"].push({
                                "$or": access
                            });
                        }
                        return [4 /*yield*/, this.findData('vuejx_cfg', 'vuejx_workflow_action', filter, {}, 0, 1000, null)];
                    case 6:
                        detail = _b.sent();
                        return [4 /*yield*/, this.buildRespone(detail)];
                    case 7: return [2 /*return*/, _b.sent()];
                }
            });
        });
    };
    vuejxQueryControl.prototype.userDb = function (token) {
        return __awaiter(this, void 0, void 0, function () {
            var user, listDbs, dbs;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.defaultBody(token, {}, '', false)];
                    case 1:
                        user = _a.sent();
                        return [4 /*yield*/, arrays_1.found(user['role'], ['admin'])];
                    case 2:
                        if (!_a.sent()) return [3 /*break*/, 5];
                        return [4 /*yield*/, this.findDatabases()];
                    case 3:
                        listDbs = _a.sent();
                        dbs = listDbs['databases'];
                        dbs = dbs.filter(function (el) {
                            return el.name !== 'admin' &&
                                el.name !== 'config' &&
                                el.name !== 'local';
                        });
                        return [4 /*yield*/, this.buildRespone(dbs)];
                    case 4: return [2 /*return*/, _a.sent()];
                    case 5: return [2 /*return*/, permission_utils_1.msg403];
                }
            });
        });
    };
    vuejxQueryControl.prototype.userCollection = function (token, db) {
        return __awaiter(this, void 0, void 0, function () {
            var user, listDbs, _a;
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxMutationService.defaultBody(token, {}, '', false)];
                    case 1:
                        user = _b.sent();
                        return [4 /*yield*/, arrays_1.found(user['role'], ['admin'])];
                    case 2:
                        if (!_b.sent()) return [3 /*break*/, 6];
                        return [4 /*yield*/, this.findCollections(db)];
                    case 3:
                        listDbs = _b.sent();
                        _a = this.buildRespone;
                        return [4 /*yield*/, this.filterProperties(listDbs)];
                    case 4: return [4 /*yield*/, _a.apply(this, [_b.sent()])];
                    case 5: return [2 /*return*/, _b.sent()];
                    case 6: return [2 /*return*/, permission_utils_1.msg403];
                }
            });
        });
    };
    vuejxQueryControl.prototype.findData = function (db, collection, filter, sort, skip, limit, keys) {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                if (keys !== undefined && keys !== null && keys !== '') {
                    keys['_id'] = 1;
                }
                return [2 /*return*/, new Promise(function (resolve, reject) {
                        config_2.getClient().db(db).collection(collection).find(filter, {
                            fields: keys
                        }).sort(sort).limit(limit).skip(skip).toArray(function (err, items) {
                            if (err) {
                                reject(err);
                            }
                            else {
                                resolve(items);
                            }
                        });
                    })];
            });
        });
    };
    vuejxQueryControl.prototype.findDatabases = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                return [2 /*return*/, new Promise(function (resolve, reject) {
                        config_2.getClient().db().admin().listDatabases(function (err, result) {
                            if (err) {
                                reject(err);
                            }
                            else {
                                resolve(result);
                            }
                        });
                    })];
            });
        });
    };
    vuejxQueryControl.prototype.findCollections = function (db) {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                return [2 /*return*/, new Promise(function (resolve, reject) {
                        config_2.getClient().db(db).listCollections().toArray(function (err, result) {
                            if (err) {
                                reject(err);
                            }
                            else {
                                resolve(result);
                            }
                        });
                    })];
            });
        });
    };
    vuejxQueryControl.prototype.filterProperties = function (data) {
        return __awaiter(this, void 0, void 0, function () {
            var result;
            return __generator(this, function (_a) {
                result = data.filter(function (el) {
                    return el.name !== '_properties';
                });
                return [2 /*return*/, result];
            });
        });
    };
    vuejxQueryControl.prototype.buildRespone = function (data) {
        var data_1, data_1_1;
        var e_1, _a;
        return __awaiter(this, void 0, void 0, function () {
            var result, el, e_1_1;
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0:
                        result = {
                            "hits": {
                                "hits": []
                            }
                        };
                        _b.label = 1;
                    case 1:
                        _b.trys.push([1, 6, 7, 12]);
                        data_1 = __asyncValues(data);
                        _b.label = 2;
                    case 2: return [4 /*yield*/, data_1.next()];
                    case 3:
                        if (!(data_1_1 = _b.sent(), !data_1_1.done)) return [3 /*break*/, 5];
                        el = data_1_1.value;
                        result['hits']['hits'].push({
                            _id: el['_id'],
                            _source: el
                        });
                        _b.label = 4;
                    case 4: return [3 /*break*/, 2];
                    case 5: return [3 /*break*/, 12];
                    case 6:
                        e_1_1 = _b.sent();
                        e_1 = { error: e_1_1 };
                        return [3 /*break*/, 12];
                    case 7:
                        _b.trys.push([7, , 10, 11]);
                        if (!(data_1_1 && !data_1_1.done && (_a = data_1.return))) return [3 /*break*/, 9];
                        return [4 /*yield*/, _a.call(data_1)];
                    case 8:
                        _b.sent();
                        _b.label = 9;
                    case 9: return [3 /*break*/, 11];
                    case 10:
                        if (e_1) throw e_1.error;
                        return [7 /*endfinally*/];
                    case 11: return [7 /*endfinally*/];
                    case 12: return [2 /*return*/, result];
                }
            });
        });
    };
    vuejxQueryControl.prototype.async = function (token, from, util, id) {
        var e_2, _a;
        return __awaiter(this, void 0, void 0, function () {
            var record, item, converter, oai_json_mapping_datasource, apiResource, _b, _c, _i, key, _d, _e, oaiPmh, identifierIterator, identifierIteratorData, identifierIteratorData2, listToRemove, inComingRecord, inComingRecordState, bulkData, indexData, result, identifierIterator_1, identifierIterator_1_1, identifier, desInput, key, _f, _g, keyOb, _h, _j, keyOb, resultProcess, e_2_1;
            return __generator(this, function (_k) {
                switch (_k.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxService.repoExistOAIES(token, id)];
                    case 1:
                        record = _k.sent();
                        item = null;
                        converter = false;
                        if (record['oai_json'] !== '' && record['oai_json'] !== undefined) {
                            item = eval('( ' + record['oai_json'] + ' )');
                            converter = true;
                        }
                        oai_json_mapping_datasource = {};
                        apiResource = {};
                        if (!(record['oai_json_mapping_datasource'] !== '' && record['oai_json_mapping_datasource'] !== undefined)) return [3 /*break*/, 5];
                        oai_json_mapping_datasource = JSON.parse(record['oai_json_mapping_datasource']);
                        _b = [];
                        for (_c in oai_json_mapping_datasource)
                            _b.push(_c);
                        _i = 0;
                        _k.label = 2;
                    case 2:
                        if (!(_i < _b.length)) return [3 /*break*/, 5];
                        key = _b[_i];
                        if (!(typeof oai_json_mapping_datasource[key] === 'string')) return [3 /*break*/, 4];
                        _d = apiResource;
                        _e = key;
                        return [4 /*yield*/, api_1.vuejxService.pullSourceMapper(token, oai_json_mapping_datasource[key])];
                    case 3:
                        _d[_e] = _k.sent();
                        _k.label = 4;
                    case 4:
                        _i++;
                        return [3 /*break*/, 2];
                    case 5:
                        oaiPmh = new oai_pmh_1.OaiPmh(record['oai_input_url']);
                        identifierIterator = oaiPmh.listRecords({
                            metadataPrefix: record['prefix']
                        });
                        identifierIteratorData = {
                            ListRecords: {
                                record: []
                            }
                        };
                        identifierIteratorData2 = {
                            ListRecords: {
                                record: []
                            }
                        };
                        listToRemove = [];
                        inComingRecord = 0;
                        inComingRecordState = 0;
                        bulkData = [];
                        indexData = 0;
                        result = {
                            insert: 0,
                            update: 0,
                            delete: 0
                        };
                        _k.label = 6;
                    case 6:
                        _k.trys.push([6, 15, 16, 21]);
                        identifierIterator_1 = __asyncValues(identifierIterator);
                        _k.label = 7;
                    case 7: return [4 /*yield*/, identifierIterator_1.next()];
                    case 8:
                        if (!(identifierIterator_1_1 = _k.sent(), !identifierIterator_1_1.done)) return [3 /*break*/, 14];
                        identifier = identifierIterator_1_1.value;
                        indexData = indexData + 1;
                        if (identifier.hasOwnProperty('header') && identifier['header'].hasOwnProperty('$')) {
                            if (identifier['header']['$']['status'] === 'deleted') {
                                listToRemove.push(identifier['metadata']);
                            }
                        }
                        inComingRecord = inComingRecord + 1;
                        desInput = {};
                        if (!converter) return [3 /*break*/, 10];
                        identifierIteratorData.ListRecords.record = [identifier];
                        return [4 /*yield*/, jsonMapper(identifierIteratorData, item).then(function (result) {
                                desInput = result['hits'][0];
                            })];
                    case 9:
                        _k.sent();
                        return [3 /*break*/, 11];
                    case 10:
                        desInput = identifier['metadata']['metadataPrefix'];
                        if (desInput.hasOwnProperty('$')) {
                            delete desInput['$'];
                        }
                        inComingRecordState = inComingRecordState + 1;
                        _k.label = 11;
                    case 11:
                        for (key in oai_json_mapping_datasource) {
                            if (typeof oai_json_mapping_datasource[key] !== 'string') {
                                if (Array.isArray(desInput[key])) {
                                    for (_f = 0, _g = desInput[key]; _f < _g.length; _f++) {
                                        keyOb = _g[_f];
                                        if (keyOb.hasOwnProperty('_source') && keyOb['_source'].hasOwnProperty('shortName')
                                            && keyOb['_source']['shortName'] !== '' && keyOb['_source']['shortName'] !== undefined && keyOb['_source']['shortName'] !== null) {
                                            keyOb['_source']['title'] = oai_json_mapping_datasource[key][keyOb['_source']['shortName']];
                                        }
                                    }
                                }
                                else {
                                    if (desInput[key] !== undefined && desInput[key] !== null && desInput[key].hasOwnProperty('_source') && desInput[key]['_source'].hasOwnProperty('shortName')
                                        && desInput[key]['_source']['shortName'] !== '' && desInput[key]['_source']['shortName'] !== undefined && desInput[key]['_source']['shortName'] !== null) {
                                        desInput[key]['_source']['title'] = oai_json_mapping_datasource[key][desInput[key]['_source']['shortName']];
                                    }
                                }
                            }
                            else {
                                if (Array.isArray(desInput[key])) {
                                    for (_h = 0, _j = desInput[key]; _h < _j.length; _h++) {
                                        keyOb = _j[_h];
                                        if (keyOb.hasOwnProperty('_source') && keyOb['_source'].hasOwnProperty('shortName')
                                            && keyOb['_source']['shortName'] !== '' && keyOb['_source']['shortName'] !== undefined && keyOb['_source']['shortName'] !== null) {
                                            keyOb['_source']['title'] = apiResource[key][keyOb['_source']['shortName']];
                                        }
                                    }
                                }
                                else {
                                    if (desInput[key] !== undefined && desInput[key] !== null && desInput[key].hasOwnProperty('_source') && desInput[key]['_source'].hasOwnProperty('shortName')
                                        && desInput[key]['_source']['shortName'] !== '' && desInput[key]['_source']['shortName'] !== undefined && desInput[key]['_source']['shortName'] !== null) {
                                        desInput[key]['_source']['title'] = apiResource[key][desInput[key]['_source']['shortName']];
                                    }
                                }
                            }
                        }
                        return [4 /*yield*/, api_1.vuejxService.processData(desInput, record['storeDb'], record['storeCollection'])];
                    case 12:
                        resultProcess = _k.sent();
                        result['insert'] = result['insert'] + resultProcess['insert'];
                        result['update'] = result['update'] + resultProcess['update'];
                        result['delete'] = result['delete'] + resultProcess['delete'];
                        _k.label = 13;
                    case 13: return [3 /*break*/, 7];
                    case 14: return [3 /*break*/, 21];
                    case 15:
                        e_2_1 = _k.sent();
                        e_2 = { error: e_2_1 };
                        return [3 /*break*/, 21];
                    case 16:
                        _k.trys.push([16, , 19, 20]);
                        if (!(identifierIterator_1_1 && !identifierIterator_1_1.done && (_a = identifierIterator_1.return))) return [3 /*break*/, 18];
                        return [4 /*yield*/, _a.call(identifierIterator_1)];
                    case 17:
                        _k.sent();
                        _k.label = 18;
                    case 18: return [3 /*break*/, 20];
                    case 19:
                        if (e_2) throw e_2.error;
                        return [7 /*endfinally*/];
                    case 20: return [7 /*endfinally*/];
                    case 21: return [2 /*return*/, {
                            statusCode: 200
                        }];
                }
            });
        });
    };
    vuejxQueryControl.prototype.asyncJSON = function (token, from, util, id) {
        var e_3, _a;
        return __awaiter(this, void 0, void 0, function () {
            var record, oaiPmh, identifierIterator, identifierIteratorData, identifierIterator_2, identifierIterator_2_1, identifier, e_3_1;
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0: return [4 /*yield*/, api_1.vuejxService.repoExistOAIES(token, id)];
                    case 1:
                        record = _b.sent();
                        oaiPmh = new oai_pmh_1.OaiPmh(record['oai_input_url']);
                        identifierIterator = oaiPmh.listRecords({
                            metadataPrefix: record['prefix']
                        });
                        identifierIteratorData = {
                            ListRecords: {
                                record: []
                            }
                        };
                        _b.label = 2;
                    case 2:
                        _b.trys.push([2, 7, 8, 13]);
                        identifierIterator_2 = __asyncValues(identifierIterator);
                        _b.label = 3;
                    case 3: return [4 /*yield*/, identifierIterator_2.next()];
                    case 4:
                        if (!(identifierIterator_2_1 = _b.sent(), !identifierIterator_2_1.done)) return [3 /*break*/, 6];
                        identifier = identifierIterator_2_1.value;
                        identifierIteratorData['ListRecords']['record'].push(identifier);
                        return [3 /*break*/, 6];
                    case 5: return [3 /*break*/, 3];
                    case 6: return [3 /*break*/, 13];
                    case 7:
                        e_3_1 = _b.sent();
                        e_3 = { error: e_3_1 };
                        return [3 /*break*/, 13];
                    case 8:
                        _b.trys.push([8, , 11, 12]);
                        if (!(identifierIterator_2_1 && !identifierIterator_2_1.done && (_a = identifierIterator_2.return))) return [3 /*break*/, 10];
                        return [4 /*yield*/, _a.call(identifierIterator_2)];
                    case 9:
                        _b.sent();
                        _b.label = 10;
                    case 10: return [3 /*break*/, 12];
                    case 11:
                        if (e_3) throw e_3.error;
                        return [7 /*endfinally*/];
                    case 12: return [7 /*endfinally*/];
                    case 13: return [2 /*return*/, identifierIteratorData];
                }
            });
        });
    };
    return vuejxQueryControl;
}(httpRequest_1.default));
exports.default = vuejxQueryControl;
