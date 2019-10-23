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
Object.defineProperty(exports, "__esModule", { value: true });
var httpRequest_1 = require("./httpRequest");
var config_1 = require("../config");
var elasticsearch_1 = require("../boostrap/elasticsearch");
var constant_1 = require("../constant");
var arrays_1 = require("../utils/arrays");
var permission_es_1 = require("../utils/permission_es");
var reindex_1 = require("../utils/reindex");
var permission_utils_1 = require("../utils/permission_utils");
var token_1 = require("../utils/token");
var config_2 = require("../config");
var ObjectId = require('mongodb').ObjectId;
var vuejxMutationControl = /** @class */ (function (_super) {
    __extends(vuejxMutationControl, _super);
    function vuejxMutationControl(path) {
        var _this = _super.call(this, config_1.PROJECT_SERVICE_HOST) || this;
        _this.path = "" + config_1.PROJECT_SERVICE_HOST;
        return _this;
    }
    vuejxMutationControl.prototype.defaultBody = function (token, body, collection, isAdd) {
        return __awaiter(this, void 0, void 0, function () {
            var user;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        body['type'] = collection;
                        return [4 /*yield*/, token_1.verify(token)];
                    case 1:
                        user = _a.sent();
                        if (user !== undefined && user !== null && user.hasOwnProperty('tokenExpired') && user['tokenExpired']) {
                            body['username'] = 'guest';
                            user = null;
                        }
                        else {
                            body['username'] = user['username'];
                        }
                        if (isAdd) {
                            body['createdAt'] = new Date().getTime();
                            if (body['order'] === undefined || body['order'] === null || body['order'] === '') {
                                body['order'] = 0;
                            }
                            if (body['site'] === undefined || body['site'] === null || body['site'] === '') {
                                body['site'] = 'guest';
                            }
                            if (body['storage'] === undefined || body['storage'] === null || body['storage'] === '') {
                                body['storage'] = 'regular';
                            }
                            if (body['openAccess'] === undefined || body['openAccess'] === null || body['openAccess'] === '') {
                                body['openAccess'] = 0;
                            }
                            if (collection === 'vuejx_collection') {
                                if (body['rightMode'] === undefined || body['rightMode'] === null || body['rightMode'] === '') {
                                    body['rightMode'] = 0;
                                }
                                if (body['publicAccess'] === undefined || body['publicAccess'] === null || body['publicAccess'] === '') {
                                    body['publicAccess'] = 0;
                                }
                            }
                        }
                        else {
                            body['modifiedAt'] = new Date().getTime();
                            delete body['username'];
                        }
                        return [2 /*return*/, user];
                }
            });
        });
    };
    vuejxMutationControl.prototype.createOne = function (token, db, collection, body, opts, actionCode) {
        return __awaiter(this, void 0, void 0, function () {
            var user, error, errorES, permissProcess, workflowAction, insertOne, e_1;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, this.defaultBody(token, body, collection, true)];
                    case 1:
                        user = _a.sent();
                        if (body['shortName'] == undefined || body['shortName'] == null || body['shortName'] == '') {
                            body['shortName'] = new ObjectId();
                        }
                        return [4 /*yield*/, permission_utils_1.permissionPOST(this.path, user, db, collection, body)];
                    case 2:
                        error = _a.sent();
                        return [4 /*yield*/, permission_es_1.permissionESPOST(this.path, user, db, collection, body, config_2.getClient(), elasticsearch_1.elasticClient)];
                    case 3:
                        errorES = _a.sent();
                        if (!((error === null || typeof error === 'string') && errorES === null)) return [3 /*break*/, 14];
                        return [4 /*yield*/, arrays_1.verifyBody(body)];
                    case 4:
                        _a.sent();
                        _a.label = 5;
                    case 5:
                        _a.trys.push([5, 12, , 13]);
                        permissProcess = true;
                        if (!(typeof error === 'string')) return [3 /*break*/, 8];
                        return [4 /*yield*/, config_2.getClient().db('vuejx_cfg').collection('vuejx_workflow_action').findOne({
                                id: actionCode,
                                workflow: error
                            })];
                    case 6:
                        workflowAction = _a.sent();
                        if (workflowAction['nextVal'] !== undefined && workflowAction['nextVal'] !== null) {
                            body[workflowAction['field']]['_source']['shortName'] = workflowAction['nextVal']['_source']['shortName'];
                            body[workflowAction['field']]['_source']['title'] = workflowAction['nextVal']['_source']['title'];
                        }
                        return [4 /*yield*/, arrays_1.foundWorkflowRole(user['role'], workflowAction['workflowRole'])];
                    case 7:
                        permissProcess = _a.sent();
                        _a.label = 8;
                    case 8:
                        if (!permissProcess) return [3 /*break*/, 10];
                        return [4 /*yield*/, config_2.getClient().db(db).collection(collection).insertOne(body, opts)];
                    case 9:
                        insertOne = _a.sent();
                        if (insertOne['insertedCount'] > 0) {
                            reindex_1.reindexAPIGraphql(db, collection, elasticsearch_1.elasticClient, body['modifiedAt'], null, 'false');
                            return [2 /*return*/, {
                                    statusCode: 201,
                                    data: {
                                        insertedId: insertOne['insertedId']
                                    }
                                }];
                        }
                        return [3 /*break*/, 11];
                    case 10: return [2 /*return*/, permission_utils_1.msg403];
                    case 11: return [3 /*break*/, 13];
                    case 12:
                        e_1 = _a.sent();
                        console.error(e_1);
                        return [2 /*return*/, {
                                statusCode: 500
                            }];
                    case 13:
                        ;
                        return [3 /*break*/, 15];
                    case 14: return [2 /*return*/, error];
                    case 15: return [2 /*return*/];
                }
            });
        });
    };
    vuejxMutationControl.prototype.createMany = function (token, db, collection, body, opts, actionCode) {
        return __awaiter(this, void 0, void 0, function () {
            var user, error, errorES, _a, _b, _i, key, permissProcess, workflowAction, _c, body_1, bodyEl, insertMany, e_2;
            return __generator(this, function (_d) {
                switch (_d.label) {
                    case 0: return [4 /*yield*/, this.defaultBody(token, body, collection, true)];
                    case 1:
                        user = _d.sent();
                        return [4 /*yield*/, permission_utils_1.permissionPOST(this.path, user, db, collection, body)];
                    case 2:
                        error = _d.sent();
                        return [4 /*yield*/, permission_es_1.permissionESPOST(this.path, user, db, collection, body, config_2.getClient(), elasticsearch_1.elasticClient)];
                    case 3:
                        errorES = _d.sent();
                        if (!((error === null || typeof error === 'string') && errorES === null)) return [3 /*break*/, 16];
                        _a = [];
                        for (_b in body)
                            _a.push(_b);
                        _i = 0;
                        _d.label = 4;
                    case 4:
                        if (!(_i < _a.length)) return [3 /*break*/, 7];
                        key = _a[_i];
                        return [4 /*yield*/, arrays_1.verifyBody(body[key])];
                    case 5:
                        _d.sent();
                        if ((body[key]['shortName'] == undefined || body[key]['shortName'] == null || body[key]['shortName'] == '') &&
                            typeof body[key] === 'object') {
                            body[key]['shortName'] = new ObjectId();
                        }
                        _d.label = 6;
                    case 6:
                        _i++;
                        return [3 /*break*/, 4];
                    case 7:
                        _d.trys.push([7, 14, , 15]);
                        permissProcess = true;
                        if (!(typeof error === 'string')) return [3 /*break*/, 10];
                        return [4 /*yield*/, config_2.getClient().db('vuejx_cfg').collection('vuejx_workflow_action').findOne({
                                id: actionCode,
                                workflow: error
                            })];
                    case 8:
                        workflowAction = _d.sent();
                        if (body !== undefined && Array.isArray(body)) {
                            for (_c = 0, body_1 = body; _c < body_1.length; _c++) {
                                bodyEl = body_1[_c];
                                if (workflowAction['nextVal'] !== undefined && workflowAction['nextVal'] !== null) {
                                    bodyEl[workflowAction['field']]['_source']['shortName'] = workflowAction['nextVal']['_source']['shortName'];
                                    bodyEl[workflowAction['field']]['_source']['title'] = workflowAction['nextVal']['_source']['title'];
                                }
                            }
                        }
                        return [4 /*yield*/, arrays_1.foundWorkflowRole(user['role'], workflowAction['workflowRole'])];
                    case 9:
                        permissProcess = _d.sent();
                        _d.label = 10;
                    case 10:
                        if (!permissProcess) return [3 /*break*/, 12];
                        return [4 /*yield*/, config_2.getClient().db(db).collection(collection).insertMany(body, opts)];
                    case 11:
                        insertMany = _d.sent();
                        if (insertMany['insertedCount'] > 0) {
                            reindex_1.reindexAPIGraphql(db, collection, elasticsearch_1.elasticClient, body['modifiedAt'], null, 'false');
                            return [2 /*return*/, {
                                    statusCode: 201,
                                    data: {
                                        insertedIds: insertMany['insertedIds']
                                    }
                                }];
                        }
                        return [3 /*break*/, 13];
                    case 12: return [2 /*return*/, permission_utils_1.msg403];
                    case 13: return [3 /*break*/, 15];
                    case 14:
                        e_2 = _d.sent();
                        console.error(e_2);
                        return [2 /*return*/, {
                                statusCode: 500
                            }];
                    case 15:
                        ;
                        return [3 /*break*/, 17];
                    case 16: return [2 /*return*/, error];
                    case 17: return [2 /*return*/];
                }
            });
        });
    };
    vuejxMutationControl.prototype.updateById = function (token, db, collection, body, opts, actionCode) {
        return __awaiter(this, void 0, void 0, function () {
            var user, findOneQuery, detail, error, permissProcess, workflowAction, updateOne, e_3;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, this.defaultBody(token, body, collection, false)];
                    case 1:
                        user = _a.sent();
                        findOneQuery = null;
                        if (body['shortName'] !== undefined && body['shortName'] !== null && body['shortName'] !== '') {
                            findOneQuery = { shortName: body['shortName'] };
                        }
                        if (body['_id'] !== undefined && body['_id'] !== null && body['_id'] !== '') {
                            findOneQuery = { _id: new ObjectId(body['_id']) };
                        }
                        return [4 /*yield*/, this.findDataChecker(db, collection, findOneQuery, { modifiedAt: -1 }, 0, 1)];
                    case 2:
                        detail = _a.sent();
                        if (String(detail).length === 0) {
                            return [2 /*return*/, {
                                    statusCode: 404
                                }];
                        }
                        return [4 /*yield*/, permission_utils_1.permissionPUT(this.path, user, db, collection, body, detail)];
                    case 3:
                        error = _a.sent();
                        if (!(error === null || typeof error === 'string')) return [3 /*break*/, 14];
                        return [4 /*yield*/, arrays_1.verifyBody(body)];
                    case 4:
                        _a.sent();
                        _a.label = 5;
                    case 5:
                        _a.trys.push([5, 12, , 13]);
                        permissProcess = true;
                        if (!(typeof error === 'string')) return [3 /*break*/, 8];
                        return [4 /*yield*/, config_2.getClient().db('vuejx_cfg').collection('vuejx_workflow_action').findOne({
                                id: actionCode,
                                workflow: error
                            })];
                    case 6:
                        workflowAction = _a.sent();
                        if (!(workflowAction !== undefined && workflowAction !== null)) return [3 /*break*/, 8];
                        if (workflowAction['nextVal'] !== undefined && workflowAction['nextVal'] !== null) {
                            body[workflowAction['field']]['_source']['shortName'] = workflowAction['nextVal']['_source']['shortName'];
                            body[workflowAction['field']]['_source']['title'] = workflowAction['nextVal']['_source']['title'];
                        }
                        return [4 /*yield*/, arrays_1.foundWorkflowRole(user['role'], workflowAction['workflowRole'])];
                    case 7:
                        permissProcess = _a.sent();
                        _a.label = 8;
                    case 8:
                        if (!permissProcess) return [3 /*break*/, 10];
                        delete body['_id'];
                        return [4 /*yield*/, config_2.getClient().db(db).collection(collection).updateOne({ _id: new ObjectId(detail[0]['_id']) }, { $set: body }, opts)];
                    case 9:
                        updateOne = _a.sent();
                        if (updateOne['modifiedCount'] > 0) {
                            reindex_1.reindexAPIGraphql(db, collection, elasticsearch_1.elasticClient, body['modifiedAt'], null, 'false');
                            return [2 /*return*/, {
                                    statusCode: 200,
                                    data: {
                                        modifiedCount: updateOne['modifiedCount'],
                                        upsertedId: updateOne['upsertedId'],
                                        upsertedCount: updateOne['upsertedCount'],
                                        matchedCount: updateOne['matchedCount']
                                    }
                                }];
                        }
                        return [3 /*break*/, 11];
                    case 10: return [2 /*return*/, permission_utils_1.msg403];
                    case 11: return [3 /*break*/, 13];
                    case 12:
                        e_3 = _a.sent();
                        console.error(e_3);
                        return [2 /*return*/, {
                                statusCode: 500
                            }];
                    case 13:
                        ;
                        return [3 /*break*/, 15];
                    case 14: return [2 /*return*/, error];
                    case 15: return [2 /*return*/];
                }
            });
        });
    };
    vuejxMutationControl.prototype.updateOne = function (token, db, collection, body, filter, sort, skip, opts, actionCode) {
        return __awaiter(this, void 0, void 0, function () {
            var user, detail, error, permissProcess, workflowAction, updateOne, e_4;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, this.defaultBody(token, body, collection, false)];
                    case 1:
                        user = _a.sent();
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, body, filter, {})];
                    case 2:
                        filter = _a.sent();
                        if (sort === undefined || sort === null || sort === '') {
                            sort = { modifiedAt: -1 };
                        }
                        if (skip === undefined || skip === null || skip === '') {
                            skip = 0;
                        }
                        return [4 /*yield*/, this.findDataChecker(db, collection, filter, sort, skip, 1)];
                    case 3:
                        detail = _a.sent();
                        if (String(detail).length === 0) {
                            return [2 /*return*/, {
                                    statusCode: 404
                                }];
                        }
                        return [4 /*yield*/, permission_utils_1.permissionPUT(this.path, user, db, collection, body, detail[0])];
                    case 4:
                        error = _a.sent();
                        if (!(error === null || typeof error === 'string')) return [3 /*break*/, 15];
                        return [4 /*yield*/, arrays_1.verifyBody(body)];
                    case 5:
                        _a.sent();
                        _a.label = 6;
                    case 6:
                        _a.trys.push([6, 13, , 14]);
                        permissProcess = true;
                        if (!(typeof error === 'string')) return [3 /*break*/, 9];
                        return [4 /*yield*/, config_2.getClient().db('vuejx_cfg').collection('vuejx_workflow_action').findOne({
                                id: actionCode,
                                workflow: error
                            })];
                    case 7:
                        workflowAction = _a.sent();
                        if (workflowAction['nextVal'] !== undefined && workflowAction['nextVal'] !== null) {
                            body[workflowAction['field']]['_source']['shortName'] = workflowAction['nextVal']['_source']['shortName'];
                            body[workflowAction['field']]['_source']['title'] = workflowAction['nextVal']['_source']['title'];
                        }
                        return [4 /*yield*/, arrays_1.foundWorkflowRole(user['role'], workflowAction['workflowRole'])];
                    case 8:
                        permissProcess = _a.sent();
                        _a.label = 9;
                    case 9:
                        if (!permissProcess) return [3 /*break*/, 11];
                        delete body['_id'];
                        return [4 /*yield*/, config_2.getClient().db(db).collection(collection).updateOne({ _id: new ObjectId(detail[0]['_id']) }, { $set: body }, opts)];
                    case 10:
                        updateOne = _a.sent();
                        if (updateOne['modifiedCount'] > 0) {
                            reindex_1.reindexAPIGraphql(db, collection, elasticsearch_1.elasticClient, body['modifiedAt'], null, 'false');
                            return [2 /*return*/, {
                                    statusCode: 200,
                                    data: {
                                        modifiedCount: updateOne['modifiedCount'],
                                        upsertedId: updateOne['upsertedId'],
                                        upsertedCount: updateOne['upsertedCount'],
                                        matchedCount: updateOne['matchedCount']
                                    }
                                }];
                        }
                        return [3 /*break*/, 12];
                    case 11: return [2 /*return*/, permission_utils_1.msg403];
                    case 12: return [3 /*break*/, 14];
                    case 13:
                        e_4 = _a.sent();
                        console.error(e_4);
                        return [2 /*return*/, {
                                statusCode: 500
                            }];
                    case 14:
                        ;
                        return [3 /*break*/, 16];
                    case 15: return [2 /*return*/, error];
                    case 16: return [2 /*return*/];
                }
            });
        });
    };
    vuejxMutationControl.prototype.updateMany = function (token, db, collection, body, filter, sort, skip, limit, opts, actionCode) {
        return __awaiter(this, void 0, void 0, function () {
            var user, detail, error, errorCount, ids, errorWorkflow, key, permissProcess, workflowAction, updateMany, e_5;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        if (body === undefined || body === null) {
                            body = {};
                        }
                        return [4 /*yield*/, this.defaultBody(token, body, collection, false)];
                    case 1:
                        user = _a.sent();
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, body, filter, {})];
                    case 2:
                        filter = _a.sent();
                        if (sort === undefined || sort === null || sort === '') {
                            sort = { modifiedAt: -1 };
                        }
                        if (skip === undefined || skip === null || skip === '') {
                            skip = 0;
                        }
                        if (limit === undefined || limit === null || skip === '') {
                            limit = 10;
                        }
                        return [4 /*yield*/, this.findDataChecker(db, collection, filter, sort, skip, limit)];
                    case 3:
                        detail = _a.sent();
                        if (String(detail).length === 0) {
                            return [2 /*return*/, {
                                    statusCode: 404
                                }];
                        }
                        error = null;
                        errorCount = 0;
                        ids = [];
                        errorWorkflow = '';
                        key = 0;
                        _a.label = 4;
                    case 4:
                        if (!(key < limit)) return [3 /*break*/, 7];
                        if (!(detail[key] !== undefined && detail[key] !== null)) return [3 /*break*/, 6];
                        return [4 /*yield*/, permission_utils_1.permissionPUT(this.path, user, db, collection, body, detail[key])];
                    case 5:
                        error = _a.sent();
                        ids.push(detail[key]['_id']);
                        if (typeof error === 'string') {
                            errorWorkflow = error;
                        }
                        if (error !== null && typeof error !== 'string') {
                            errorCount = 1;
                            return [3 /*break*/, 7];
                        }
                        _a.label = 6;
                    case 6:
                        key++;
                        return [3 /*break*/, 4];
                    case 7:
                        if (!(errorCount === 0)) return [3 /*break*/, 18];
                        return [4 /*yield*/, arrays_1.verifyBody(body)];
                    case 8:
                        _a.sent();
                        _a.label = 9;
                    case 9:
                        _a.trys.push([9, 16, , 17]);
                        permissProcess = true;
                        if (!(errorWorkflow !== '')) return [3 /*break*/, 12];
                        return [4 /*yield*/, config_2.getClient().db('vuejx_cfg').collection('vuejx_workflow_action').findOne({
                                id: actionCode,
                                workflow: errorWorkflow
                            })];
                    case 10:
                        workflowAction = _a.sent();
                        body = {};
                        if (workflowAction['nextVal'] !== undefined && workflowAction['nextVal'] !== null) {
                            body[workflowAction['field']] = {
                                _source: {
                                    shortName: workflowAction['nextVal']['_source']['shortName'],
                                    title: workflowAction['nextVal']['_source']['title']
                                }
                            };
                        }
                        return [4 /*yield*/, arrays_1.foundWorkflowRole(user['role'], workflowAction['workflowRole'])];
                    case 11:
                        permissProcess = _a.sent();
                        _a.label = 12;
                    case 12:
                        if (!permissProcess) return [3 /*break*/, 14];
                        return [4 /*yield*/, config_2.getClient().db(db).collection(collection).updateMany({
                                _id: {
                                    $in: ids
                                }
                            }, {
                                $set: body
                            }, opts)];
                    case 13:
                        updateMany = _a.sent();
                        if (updateMany['modifiedCount'] > 0) {
                            reindex_1.reindexAPIGraphql(db, collection, elasticsearch_1.elasticClient, body['modifiedAt'], null, 'false');
                            return [2 /*return*/, {
                                    statusCode: 200,
                                    data: {
                                        modifiedCount: updateMany['modifiedCount'],
                                        upsertedId: updateMany['upsertedId'],
                                        upsertedCount: updateMany['upsertedCount'],
                                        matchedCount: updateMany['matchedCount']
                                    }
                                }];
                        }
                        return [3 /*break*/, 15];
                    case 14: return [2 /*return*/, permission_utils_1.msg403];
                    case 15: return [3 /*break*/, 17];
                    case 16:
                        e_5 = _a.sent();
                        console.error(e_5);
                        return [2 /*return*/, {
                                statusCode: 500
                            }];
                    case 17:
                        ;
                        return [3 /*break*/, 19];
                    case 18: return [2 /*return*/, error];
                    case 19: return [2 /*return*/];
                }
            });
        });
    };
    vuejxMutationControl.prototype.removeById = function (token, db, collection, id, opts, actionCode) {
        return __awaiter(this, void 0, void 0, function () {
            var user, findOneQuery, detail, error, errorES, removeById, e_6;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, this.defaultBody(token, {}, collection, false)];
                    case 1:
                        user = _a.sent();
                        findOneQuery = null;
                        findOneQuery = { _id: new ObjectId(id) };
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, {}, findOneQuery, {})];
                    case 2:
                        findOneQuery = _a.sent();
                        return [4 /*yield*/, config_2.getClient().db(db).collection(collection).findOne(findOneQuery, {
                                username: 1,
                                openAccess: 1,
                                accessRoles: 1,
                                accessUsers: 1,
                                accessEmails: 1
                            })];
                    case 3:
                        detail = _a.sent();
                        if (String(detail).length === 0) {
                            return [2 /*return*/, {
                                    statusCode: 404
                                }];
                        }
                        return [4 /*yield*/, permission_utils_1.permissionDELETE(this.path, user, db, collection, detail)];
                    case 4:
                        error = _a.sent();
                        return [4 /*yield*/, permission_es_1.permissionESDELETE(this.path, user, db, collection, detail, config_2.getClient(), elasticsearch_1.elasticClient)];
                    case 5:
                        errorES = _a.sent();
                        if (!(error === null && errorES === null)) return [3 /*break*/, 10];
                        _a.label = 6;
                    case 6:
                        _a.trys.push([6, 8, , 9]);
                        return [4 /*yield*/, config_2.getClient().db(db).collection(collection).remove({ _id: new ObjectId(id) }, opts)];
                    case 7:
                        removeById = _a.sent();
                        if (removeById['result']['ok'] > 0) {
                            elasticsearch_1.deleteDocument(elasticsearch_1.elasticClient, db + '___' + collection, constant_1._TYPE, String(id) + '');
                            return [2 /*return*/, {
                                    statusCode: 200
                                }];
                        }
                        return [3 /*break*/, 9];
                    case 8:
                        e_6 = _a.sent();
                        console.error(e_6);
                        return [2 /*return*/, {
                                statusCode: 500
                            }];
                    case 9:
                        ;
                        return [3 /*break*/, 11];
                    case 10: return [2 /*return*/, error];
                    case 11: return [2 /*return*/];
                }
            });
        });
    };
    vuejxMutationControl.prototype.removeOne = function (token, db, collection, filter, sort, opts, actionCode) {
        return __awaiter(this, void 0, void 0, function () {
            var user, detail, error, errorES, removeById, e_7;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, this.defaultBody(token, {}, collection, false)];
                    case 1:
                        user = _a.sent();
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, {}, filter, {})];
                    case 2:
                        // GET DETAIL
                        filter = _a.sent();
                        return [4 /*yield*/, this.findDataChecker(db, collection, filter, sort, 0, 1)];
                    case 3:
                        detail = _a.sent();
                        if (String(detail).length === 0) {
                            return [2 /*return*/, {
                                    statusCode: 404
                                }];
                        }
                        return [4 /*yield*/, permission_utils_1.permissionDELETE(this.path, user, db, collection, detail[0])];
                    case 4:
                        error = _a.sent();
                        return [4 /*yield*/, permission_es_1.permissionESDELETE(this.path, user, db, collection, detail[0], config_2.getClient(), elasticsearch_1.elasticClient)];
                    case 5:
                        errorES = _a.sent();
                        if (!(error === null && errorES === null)) return [3 /*break*/, 10];
                        _a.label = 6;
                    case 6:
                        _a.trys.push([6, 8, , 9]);
                        return [4 /*yield*/, config_2.getClient().db(db).collection(collection).remove({ _id: new ObjectId(detail[0]['_id']) }, opts)];
                    case 7:
                        removeById = _a.sent();
                        if (removeById['result']['ok'] > 0) {
                            elasticsearch_1.deleteDocument(elasticsearch_1.elasticClient, db + '___' + collection, constant_1._TYPE, String(detail[0]['_id']) + '');
                            return [2 /*return*/, {
                                    statusCode: 200
                                }];
                        }
                        return [3 /*break*/, 9];
                    case 8:
                        e_7 = _a.sent();
                        console.error(e_7);
                        return [2 /*return*/, {
                                statusCode: 500
                            }];
                    case 9:
                        ;
                        return [3 /*break*/, 11];
                    case 10: return [2 /*return*/, error];
                    case 11: return [2 /*return*/];
                }
            });
        });
    };
    vuejxMutationControl.prototype.removeMany = function (token, db, collection, filter, opts, actionCode) {
        return __awaiter(this, void 0, void 0, function () {
            var user, detail, error, errorCount, key, errorES, removeMany, key, e_8;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, this.defaultBody(token, {}, collection, false)];
                    case 1:
                        user = _a.sent();
                        return [4 /*yield*/, permission_utils_1.queryBuilder(this.path, user, db, collection, {}, filter, {})];
                    case 2:
                        // GET DETAIL
                        filter = _a.sent();
                        return [4 /*yield*/, this.findDataChecker(db, collection, filter, { modifiedAt: -1 }, 0, 1000)];
                    case 3:
                        detail = _a.sent();
                        if (String(detail).length === 0) {
                            return [2 /*return*/, {
                                    statusCode: 404
                                }];
                        }
                        error = null;
                        errorCount = 0;
                        key = 0;
                        _a.label = 4;
                    case 4:
                        if (!(key < 1000)) return [3 /*break*/, 7];
                        if (!(detail[key] !== undefined && detail[key] !== null)) return [3 /*break*/, 6];
                        return [4 /*yield*/, permission_utils_1.permissionDELETE(this.path, user, db, collection, detail[key])];
                    case 5:
                        error = _a.sent();
                        if (error !== null) {
                            errorCount = 1;
                            return [3 /*break*/, 7];
                        }
                        _a.label = 6;
                    case 6:
                        key++;
                        return [3 /*break*/, 4];
                    case 7: return [4 /*yield*/, permission_es_1.permissionESDELETE(this.path, user, db, collection, detail, config_2.getClient(), elasticsearch_1.elasticClient)];
                    case 8:
                        errorES = _a.sent();
                        if (!(errorCount === 0 && errorES === null)) return [3 /*break*/, 13];
                        _a.label = 9;
                    case 9:
                        _a.trys.push([9, 11, , 12]);
                        return [4 /*yield*/, config_2.getClient().db(db).collection(collection).remove(filter, opts)];
                    case 10:
                        removeMany = _a.sent();
                        if (removeMany['result']['ok'] > 0) {
                            for (key = 0; key < 1000; key++) {
                                if (detail[key] !== undefined && detail[key] !== null) {
                                    elasticsearch_1.deleteDocument(elasticsearch_1.elasticClient, db + '___' + collection, constant_1._TYPE, String(detail[key]['_id']) + '');
                                }
                            }
                            return [2 /*return*/, {
                                    statusCode: 200
                                }];
                        }
                        return [3 /*break*/, 12];
                    case 11:
                        e_8 = _a.sent();
                        console.error(e_8);
                        return [2 /*return*/, {
                                statusCode: 500
                            }];
                    case 12:
                        ;
                        return [3 /*break*/, 14];
                    case 13: return [2 /*return*/, error];
                    case 14: return [2 /*return*/];
                }
            });
        });
    };
    vuejxMutationControl.prototype.reindex = function (token, db, collection, from, until, type, all) {
        return __awaiter(this, void 0, void 0, function () {
            var user;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0: return [4 /*yield*/, this.defaultBody(token, {}, collection, false)];
                    case 1:
                        user = _a.sent();
                        return [4 /*yield*/, arrays_1.found(user['role'], ['admin'])];
                    case 2:
                        if (_a.sent()) {
                            if (all === 'true') {
                                config_2.getClient().db('vuejx_cfg').collection('vuejx_collection').find().limit(1000).toArray(function (err, items) {
                                    return __awaiter(this, void 0, void 0, function () {
                                        var _i, items_1, el;
                                        return __generator(this, function (_a) {
                                            switch (_a.label) {
                                                case 0:
                                                    _i = 0, items_1 = items;
                                                    _a.label = 1;
                                                case 1:
                                                    if (!(_i < items_1.length)) return [3 /*break*/, 4];
                                                    el = items_1[_i];
                                                    if (!(el['storeDb'] !== undefined && el['storeDb'] !== null && el['storeDb'] !== '')) return [3 /*break*/, 3];
                                                    return [4 /*yield*/, reindex_1.reindexAPIGraphql(db, collection, elasticsearch_1.elasticClient, null, null, type)];
                                                case 2:
                                                    _a.sent();
                                                    _a.label = 3;
                                                case 3:
                                                    _i++;
                                                    return [3 /*break*/, 1];
                                                case 4: return [2 /*return*/];
                                            }
                                        });
                                    });
                                });
                            }
                            else {
                                reindex_1.reindexAPIGraphql(db, collection, elasticsearch_1.elasticClient, from, until, type);
                            }
                            return [2 /*return*/, {
                                    status: 200
                                }];
                        }
                        else {
                            return [2 /*return*/, {}];
                        }
                        return [2 /*return*/];
                }
            });
        });
    };
    vuejxMutationControl.prototype.findDataChecker = function (db, collection, filter, sort, skip, limit) {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                return [2 /*return*/, new Promise(function (resolve, reject) {
                        config_2.getClient().db(db).collection(collection).find(filter, {
                            _id: 1,
                            username: 1,
                            openAccess: 1,
                            accessRoles: 1,
                            accessUsers: 1,
                            accessEmails: 1
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
    vuejxMutationControl.prototype.transaction = function (token, body) {
        return __awaiter(this, void 0, void 0, function () {
            var session, opts, _a, _b, _i, key, userUpdateByIdRP, userRemoveManyRP, userCreateManyRP, error_1;
            return __generator(this, function (_c) {
                switch (_c.label) {
                    case 0:
                        session = config_2.getClient().startSession();
                        session.startTransaction();
                        _c.label = 1;
                    case 1:
                        _c.trys.push([1, 14, , 16]);
                        opts = { session: session, returnOriginal: false };
                        _a = [];
                        for (_b in body)
                            _a.push(_b);
                        _i = 0;
                        _c.label = 2;
                    case 2:
                        if (!(_i < _a.length)) return [3 /*break*/, 12];
                        key = _a[_i];
                        if (!(key === 'userUpdateById')) return [3 /*break*/, 5];
                        return [4 /*yield*/, this.updateById(token, body[key].db, body[key].collection, body[key].body, opts, body[key].actionCode)];
                    case 3:
                        userUpdateByIdRP = _c.sent();
                        console.log('userUpdateByIdRPuserUpdateByIdRPuserUpdateByIdRP', userUpdateByIdRP);
                        if (!(userUpdateByIdRP === undefined || userUpdateByIdRP['statusCode'] === 500)) return [3 /*break*/, 5];
                        return [4 /*yield*/, session.abortTransaction()];
                    case 4:
                        _c.sent();
                        session.endSession();
                        _c.label = 5;
                    case 5:
                        if (!(key === 'userRemoveMany')) return [3 /*break*/, 8];
                        return [4 /*yield*/, this.removeMany(token, body[key].db, body[key].collection, body[key].filter, opts, body[key].actionCode)];
                    case 6:
                        userRemoveManyRP = _c.sent();
                        console.log('userRemoveManyRPuserRemoveManyRPuserRemoveManyRP', userRemoveManyRP);
                        if (!(userRemoveManyRP === undefined || userRemoveManyRP['statusCode'] === 500)) return [3 /*break*/, 8];
                        return [4 /*yield*/, session.abortTransaction()];
                    case 7:
                        _c.sent();
                        session.endSession();
                        _c.label = 8;
                    case 8:
                        if (!(key === 'userCreateMany')) return [3 /*break*/, 11];
                        return [4 /*yield*/, this.createMany(token, body[key].db, body[key].collection, body[key].body, opts, body[key].actionCode)];
                    case 9:
                        userCreateManyRP = _c.sent();
                        console.log('userCreateManyuserCreateManyuserCreateManyuserCreateMany', userCreateManyRP);
                        if (!(userCreateManyRP === undefined || userCreateManyRP['statusCode'] === 500)) return [3 /*break*/, 11];
                        return [4 /*yield*/, session.abortTransaction()];
                    case 10:
                        _c.sent();
                        session.endSession();
                        _c.label = 11;
                    case 11:
                        _i++;
                        return [3 /*break*/, 2];
                    case 12: return [4 /*yield*/, session.commitTransaction()];
                    case 13:
                        _c.sent();
                        session.endSession();
                        return [2 /*return*/, {
                                status: 200
                            }];
                    case 14:
                        error_1 = _c.sent();
                        console.error(error_1);
                        return [4 /*yield*/, session.abortTransaction()];
                    case 15:
                        _c.sent();
                        session.endSession();
                        return [3 /*break*/, 16];
                    case 16: return [2 /*return*/];
                }
            });
        });
    };
    return vuejxMutationControl;
}(httpRequest_1.default));
exports.default = vuejxMutationControl;
