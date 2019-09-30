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
var arrays_1 = require("./arrays");
var config_1 = require("../config");
var httpRequest_1 = require("../api/httpRequest");
var msg403 = {
    message: "Access denied!",
    error: {
        "statusCode": 403,
        "status": 403,
        "code": 403,
        "message": "Access denied!",
        "name": "permission_denied"
    }
};
var msg409 = {
    message: "Conflict data!",
    error: {
        "statusCode": 409,
        "status": 409,
        "code": 409,
        "message": "Conflict data!",
        "name": "conflict_data"
    }
};
var msgChange = {
    message: "status"
};
exports.deleteResourceCollection = function (path, db, collection, query) { return __awaiter(_this, void 0, void 0, function () {
    var httpReq, responeEtag, respone;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                httpReq = new httpRequest_1.default(config_1.PROJECT_SERVICE_HOST);
                return [4 /*yield*/, httpReq.fetch_rest(path + '/' + query.storeDb + '/' + query.shortName + '?pagesize=0', {})];
            case 1:
                responeEtag = _a.sent();
                return [4 /*yield*/, httpReq.delete_rest(path + '/' + query.storeDb + '/' + query.shortName, {
                        headers: {
                            'If-Match': responeEtag.headers['etag']
                        }
                    })];
            case 2:
                respone = _a.sent();
                if (respone.status === 204) {
                    return [2 /*return*/, null];
                }
                else {
                    return [2 /*return*/, msg409];
                }
                return [2 /*return*/];
        }
    });
}); };
exports.permission = function (path, user, db, collection, body) { return __awaiter(_this, void 0, void 0, function () {
    var queryBuilder, httpReq, isGuest, collectionPermission, collectionRespone, _a, _b, scopesProjections, _c, access, _i, _d, role, _e, permissionList, chk, detailRespone, perChk, detailRespone, perChk;
    return __generator(this, function (_f) {
        switch (_f.label) {
            case 0:
                queryBuilder = {};
                httpReq = new httpRequest_1.default(config_1.PROJECT_SERVICE_HOST);
                isGuest = (user === undefined || user === null || user === '');
                collectionPermission = {};
                if (!(collection !== undefined && collection !== null)) return [3 /*break*/, 2];
                return [4 /*yield*/, httpReq.fetch_rest(path + '/vuejx_cfg/vuejx_collection', {
                        params: {
                            filter: {
                                shortName: collection
                            },
                            keys: {
                                rightMode: 1,
                                publicAccess: 1,
                                accessRoles: 1,
                                accessUsers: 1,
                                accessEmails: 1
                            }
                        }
                    })];
            case 1:
                collectionRespone = _f.sent();
                if (collectionRespone.data.length > 0) {
                    collectionPermission = collectionRespone.data[0];
                }
                _f.label = 2;
            case 2:
                if (collectionPermission['publicAccess'] === 0 && isGuest) {
                    return [2 /*return*/, msg403];
                }
                if (!(ctx.method === 'GET')) return [3 /*break*/, 10];
                if (ctx.query.filter !== undefined) {
                    queryBuilder = JSON.parse(ctx.query.filter);
                }
                _a = collectionPermission['rightMode'] !== 0;
                if (_a) return [3 /*break*/, 5];
                _b = !isGuest;
                if (!_b) return [3 /*break*/, 4];
                return [4 /*yield*/, arrays_1.found(user['role'], ['admin'])];
            case 3:
                _b = (_f.sent());
                _f.label = 4;
            case 4:
                _a = (_b);
                _f.label = 5;
            case 5:
                if (!_a) return [3 /*break*/, 6];
                return [3 /*break*/, 9];
            case 6:
                scopesProjections = [];
                if (queryBuilder["$and"] === undefined || queryBuilder["$and"] === null) {
                    queryBuilder["$and"] = [];
                }
                _c = !isGuest;
                if (!_c) return [3 /*break*/, 8];
                return [4 /*yield*/, arrays_1.foundScope(user['scope'], scopesProjections)];
            case 7:
                _c = !(_f.sent());
                _f.label = 8;
            case 8:
                if (_c) {
                    return [2 /*return*/, msg403];
                }
                else {
                    // BUILD KEYS
                }
                // _openAccess = 2
                if (isGuest) {
                    if (ctx.query.email !== undefined && ctx.query.email !== null && ctx.query.email !== '') {
                        queryBuilder['$and'].push({
                            "accessEmails": {
                                $exists: true
                            }
                        });
                        queryBuilder['$and'].push({
                            "accessEmails.shortName": ctx.query.email
                        });
                    }
                    // _accessEmail chứa email truy cập
                }
                else {
                    queryBuilder['$and'].push({
                        "openAccess": 1
                    });
                    access = [];
                    for (_i = 0, _d = user['role']; _i < _d.length; _i++) {
                        role = _d[_i];
                        access.push({
                            "accessRoles.shortName": role
                        });
                    }
                    access.push({
                        "username": user['username']
                    });
                    access.push({
                        "accessUsers.shortName": user['username']
                    });
                    queryBuilder['$and'].push({
                        "$or": access
                    });
                    // _username=username
                    // _openAccess = 1 
                    // _accessRoles chứa role truy cập 
                    // _accessUsers chứa username truy cập
                }
                _f.label = 9;
            case 9:
                if (queryBuilder['$and'] !== undefined && queryBuilder['$and'].length === 0) {
                    delete queryBuilder['$and'];
                }
                else {
                    if (queryBuilder['$and'] !== undefined && queryBuilder['$and']['$or'] !== undefined && queryBuilder['$and']['$or'].length === 0) {
                        delete queryBuilder['$and']['$or'];
                    }
                }
                return [2 /*return*/, queryBuilder];
            case 10:
                _e = collectionPermission['rightMode'] === 2;
                if (_e) return [3 /*break*/, 12];
                return [4 /*yield*/, arrays_1.found(user['role'], ['admin'])];
            case 11:
                _e = (_f.sent());
                _f.label = 12;
            case 12:
                // OTHER METHOD
                if (_e) {
                    // REMOVE PERMISSION
                    return [2 /*return*/, null];
                }
                if (collectionPermission['publicAccess'] === 1 && isGuest) {
                    return [2 /*return*/, msg403];
                }
                else if (collectionPermission['publicAccess'] === 0 && isGuest) {
                    return [2 /*return*/, msg403];
                }
                return [4 /*yield*/, arrays_1.foundPermission(user['role'], collectionPermission['accessRoles'])];
            case 13:
                permissionList = _f.sent();
                if (!(ctx.method === 'POST')) return [3 /*break*/, 15];
                chk = msg403;
                if (permissionList.some(function (v) {
                    if (v === 2) {
                        return true;
                    }
                })) {
                    return [2 /*return*/, null];
                }
                return [4 /*yield*/, arrays_1.foundPermission(user['role'], collectionPermission['accessUsers'])];
            case 14:
                permissionList = _f.sent();
                if (permissionList.some(function (v) {
                    if (v === 2) {
                        return true;
                    }
                })) {
                    return [2 /*return*/, null];
                }
                return [2 /*return*/, chk];
            case 15:
                if (!(ctx.method === 'PUT')) return [3 /*break*/, 22];
                return [4 /*yield*/, httpReq.fetch_rest(path + '/vuejx_cfg/vuejx_collection/' + code, {
                        params: {
                            keys: {
                                username: 1,
                                openAccess: 1,
                                accessRoles: 1,
                                accessUsers: 1,
                                accessEmails: 1
                            }
                        }
                    })];
            case 16:
                detailRespone = _f.sent();
                if (detailRespone.data['username'] !== undefined && detailRespone.data['username'] === user['username']) {
                    return [2 /*return*/, null];
                }
                return [4 /*yield*/, arrays_1.foundPermission(user['role'], detailRespone.data['accessRoles'])];
            case 17:
                permissionList = _f.sent();
                perChk = permissionList.some(function (v) {
                    if (v === 3) {
                        return true;
                    }
                });
                if (perChk) {
                    return [2 /*return*/, null];
                }
                if (!perChk) {
                    perChk = permissionList.some(function (v) {
                        if (v === 2) {
                            return true;
                        }
                    });
                    if (perChk) {
                        body['openAccess'] = detailRespone.data['openAccess'];
                        body['accessRoles'] = detailRespone.data['accessRoles'];
                        body['accessUsers'] = detailRespone.data['accessUsers'];
                        body['accessEmails'] = detailRespone.data['accessEmails'];
                        return [2 /*return*/, null];
                    }
                }
                if (!!perChk) return [3 /*break*/, 19];
                return [4 /*yield*/, arrays_1.foundPermission(user['role'], detailRespone.data['accessUsers'])];
            case 18:
                permissionList = _f.sent();
                perChk = permissionList.some(function (v) {
                    if (v === 3) {
                        return true;
                    }
                });
                if (perChk) {
                    return [2 /*return*/, null];
                }
                if (!perChk) {
                    perChk = permissionList.some(function (v) {
                        if (v === 2) {
                            return true;
                        }
                    });
                    if (perChk) {
                        body['openAccess'] = detailRespone.data['openAccess'];
                        body['accessRoles'] = detailRespone.data['accessRoles'];
                        body['accessUsers'] = detailRespone.data['accessUsers'];
                        body['accessEmails'] = detailRespone.data['accessEmails'];
                        return [2 /*return*/, null];
                    }
                }
                _f.label = 19;
            case 19:
                if (!!perChk) return [3 /*break*/, 21];
                return [4 /*yield*/, arrays_1.foundPermission(user['role'], detailRespone.data['accessEmails'])];
            case 20:
                permissionList = _f.sent();
                perChk = permissionList.some(function (v) {
                    if (v === 3) {
                        return true;
                    }
                });
                if (perChk) {
                    return [2 /*return*/, null];
                }
                if (!perChk) {
                    perChk = permissionList.some(function (v) {
                        if (v === 2) {
                            return true;
                        }
                    });
                    if (perChk) {
                        body['openAccess'] = detailRespone.data['openAccess'];
                        body['accessRoles'] = detailRespone.data['accessRoles'];
                        body['accessUsers'] = detailRespone.data['accessUsers'];
                        body['accessEmails'] = detailRespone.data['accessEmails'];
                        return [2 /*return*/, null];
                    }
                }
                _f.label = 21;
            case 21: return [2 /*return*/, msg403];
            case 22:
                if (!(ctx.method === 'DELETE')) return [3 /*break*/, 27];
                return [4 /*yield*/, httpReq.fetch_rest(path + '/vuejx_cfg/vuejx_collection/' + code, {
                        params: {
                            keys: {
                                username: 1,
                                openAccess: 1,
                                accessRoles: 1,
                                accessUsers: 1,
                                accessEmails: 1
                            }
                        }
                    })];
            case 23:
                detailRespone = _f.sent();
                if (detailRespone.data['username'] !== undefined && detailRespone.data['username'] === user['username']) {
                    return [2 /*return*/, null];
                }
                return [4 /*yield*/, arrays_1.foundPermission(user['role'], detailRespone.data['accessRoles'])];
            case 24:
                permissionList = _f.sent();
                perChk = permissionList.some(function (v) {
                    if (v === 4) {
                        return true;
                    }
                });
                if (perChk) {
                    return [2 /*return*/, null];
                }
                if (!!perChk) return [3 /*break*/, 26];
                return [4 /*yield*/, arrays_1.foundPermission(user['role'], detailRespone.data['accessUsers'])];
            case 25:
                permissionList = _f.sent();
                perChk = permissionList.some(function (v) {
                    if (v === 4) {
                        return true;
                    }
                });
                if (perChk) {
                    return [2 /*return*/, null];
                }
                _f.label = 26;
            case 26: return [2 /*return*/, msg403];
            case 27: return [2 /*return*/, queryBuilder];
        }
    });
}); };
exports.permissionQuery = function (user, url) { return __awaiter(_this, void 0, void 0, function () {
    return __generator(this, function (_a) {
        return [2 /*return*/];
    });
}); };
exports.permissionQueryES = function (body, token) { return __awaiter(_this, void 0, void 0, function () {
    return __generator(this, function (_a) {
        return [2 /*return*/];
    });
}); };
