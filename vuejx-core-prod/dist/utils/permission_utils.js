"use strict";var __awaiter=this&&this.__awaiter||function(o,c,a,i){return new(a=a||Promise)(function(e,s){function r(e){try{n(i.next(e))}catch(e){s(e)}}function t(e){try{n(i.throw(e))}catch(e){s(e)}}function n(s){s.done?e(s.value):new a(function(e){e(s.value)}).then(r,t)}n((i=i.apply(o,c||[])).next())})},__generator=this&&this.__generator||function(r,t){var n,o,c,e,a={label:0,sent:function(){if(1&c[0])throw c[1];return c[1]},trys:[],ops:[]};return e={next:s(0),throw:s(1),return:s(2)},"function"==typeof Symbol&&(e[Symbol.iterator]=function(){return this}),e;function s(s){return function(e){return function(s){if(n)throw new TypeError("Generator is already executing.");for(;a;)try{if(n=1,o&&(c=2&s[0]?o.return:s[0]?o.throw||((c=o.return)&&c.call(o),0):o.next)&&!(c=c.call(o,s[1])).done)return c;switch(o=0,c&&(s=[2&s[0],c.value]),s[0]){case 0:case 1:c=s;break;case 4:return a.label++,{value:s[1],done:!1};case 5:a.label++,o=s[1],s=[0];continue;case 7:s=a.ops.pop(),a.trys.pop();continue;default:if(!(c=0<(c=a.trys).length&&c[c.length-1])&&(6===s[0]||2===s[0])){a=0;continue}if(3===s[0]&&(!c||s[1]>c[0]&&s[1]<c[3])){a.label=s[1];break}if(6===s[0]&&a.label<c[1]){a.label=c[1],c=s;break}if(c&&a.label<c[2]){a.label=c[2],a.ops.push(s);break}c[2]&&a.ops.pop(),a.trys.pop();continue}s=t.call(r,a)}catch(e){s=[6,e],o=0}finally{n=c=0}if(5&s[0])throw s[1];return{value:s[0]?s[1]:void 0,done:!0}}([s,e])}}},_this=this;Object.defineProperty(exports,"__esModule",{value:!0});var arrays_1=require("./arrays"),config_1=require("../config"),httpRequest_1=require("../api/httpRequest"),config_2=require("../config"),ObjectId=require("mongodb").ObjectId;exports.msg403={statusCode:403,message:"Access denied!",error:{statusCode:403,status:403,code:403,message:"Access denied!",name:"permission_denied"}};var msg404={statusCode:404,message:"Record not found!",error:{statusCode:404,status:404,code:404,message:"Record not found!",name:"not_found"}},msg409={statusCode:409,message:"Conflict data!",error:{statusCode:409,status:409,code:409,message:"Conflict data!",name:"conflict_data"}},msgChange={message:"status"};exports.deleteResourceCollection=function(t,e,s,n){return __awaiter(_this,void 0,void 0,function(){var s,r;return __generator(this,function(e){switch(e.label){case 0:return[4,(s=new httpRequest_1.default(config_1.PROJECT_SERVICE_HOST)).fetch_rest(t+"/"+n.storeDb+"/"+n.shortName+"?pagesize=0",{})];case 1:return r=e.sent(),[4,s.delete_rest(t+"/"+n.storeDb+"/"+n.shortName,{headers:{"If-Match":r.headers.etag}})];case 2:return 204===e.sent().status?[2,null]:[2,msg409]}})})},exports.queryBuilder=function(e,f,s,m,_,h,r){return __awaiter(_this,void 0,void 0,function(){var s,r,t,n,o,c,a,i,l,u,d;return __generator(this,function(e){switch(e.label){case 0:return s={},r=null==f||""===f,t={},null==m?[3,2]:[4,config_2.getClient().db("vuejx_cfg").collection("vuejx_collection").findOne({shortName:m},{fields:{rightMode:1,publicAccess:1,accessRoles:1,accessUsers:1,accessEmails:1,workflow:1}})];case 1:t=e.sent(),e.label=2;case 2:return 0===t.publicAccess&&r?[2,exports.msg403]:(null!=h&&(s=h),(n=0!==t.rightMode)?[3,5]:(o=!r)?[4,arrays_1.found(f.role,["admin"])]:[3,4]);case 3:o=e.sent(),e.label=4;case 4:n=o,e.label=5;case 5:return n?[2,s]:[3,6];case 6:return c=[],void 0!==s.$and&&null!==s.$and||(s.$and=[]),(a=!r)?[4,arrays_1.foundScope(f.scope,c)]:[3,8];case 7:a=!e.sent(),e.label=8;case 8:if(a)return[2,exports.msg403];if(r)void 0!==_.email&&null!==_.email&&""!==_.email&&(s.$and.push({accessEmails:{$exists:!0}}),s.$and.push({"accessEmails.shortName":_.email}));else{for(s.$and.push({openAccess:1}),i=[],l=0,u=f.role;l<u.length;l++)d=u[l],i.push({"accessRoles.shortName":d});i.push({username:f.username}),i.push({"accessUsers.shortName":f.username}),s.$and.push({$or:i})}e.label=9;case 9:return void 0!==s.$and&&0===s.$and.length?delete s.$and:void 0!==s.$and&&void 0!==s.$and.$or&&0===s.$and.$or.length&&delete s.$and.$or,[2,s]}})})},exports.permissionPOST=function(e,u,d,f,m){return __awaiter(_this,void 0,void 0,function(){var s,r,t,n,o,c,a,i,l;return __generator(this,function(e){switch(e.label){case 0:if(s=exports.msg403,r=null,Array.isArray(m))for(r={$or:[]},t=0,n=m;t<n.length;t++)o=n[t],r.$or.push({shortName:o.shortName,site:o.site});else r={shortName:m.shortName,site:m.site};return c=null==u||""===u,a={},null==f?[3,2]:[4,config_2.getClient().db("vuejx_cfg").collection("vuejx_collection").findOne({shortName:f},{fields:{rightMode:1,publicAccess:1,accessRoles:1,accessUsers:1,accessEmails:1,workflow:1}})];case 1:a=e.sent(),e.label=2;case 2:return void 0!==(i=a.workflow)&&""!==i||(i=null),(l=2===a.rightMode)?[3,4]:[4,arrays_1.found(u.role,["admin"])];case 3:l=e.sent(),e.label=4;case 4:return l?("dict_item"===f&&(r.dictCollection=m.dictCollection._source.shortName),[4,config_2.getClient().db(d).collection(f).findOne(r)]):[3,6];case 5:return null===e.sent()?[2,null]:[2,msg409];case 6:return 1===a.publicAccess&&c?[2,exports.msg403]:0===a.publicAccess&&c?[2,exports.msg403]:[4,arrays_1.foundPermission(u.role,a.accessRoles)];case 7:return e.sent().some(function(e){if(2===e)return!0})?("dict_item"===f&&(r.dictCollection=m.dictCollection._source.shortName),[4,config_2.getClient().db(d).collection(f).findOne(r)]):[3,9];case 8:return null===e.sent()?[2,i]:[2,msg409];case 9:return[4,arrays_1.foundPermission(u.role,a.accessUsers)];case 10:return e.sent().some(function(e){if(2===e)return!0})?("dict_item"===f&&(r.dictCollection=m.dictCollection._source.shortName),[4,config_2.getClient().db(d).collection(f).findOne(r)]):[3,12];case 11:return null===e.sent()?[2,i]:[2,msg409];case 12:return[2,s]}})})},exports.permissionPUT=function(e,i,s,l,u,d){return __awaiter(_this,void 0,void 0,function(){var s,r,t,n,o,c,a;return __generator(this,function(e){switch(e.label){case 0:return s=exports.msg403,null,void 0!==u.shortName&&null!==u.shortName&&""!==u.shortName?{shortName:u.shortName}:void 0!==u._id&&null!==u._id&&""!==u._id&&{_id:new ObjectId(u._id)},r=null==i||""===i,t={},null==l?[3,2]:[4,config_2.getClient().db("vuejx_cfg").collection("vuejx_collection").findOne({shortName:l},{fields:{rightMode:1,publicAccess:1,accessRoles:1,accessUsers:1,accessEmails:1,workflow:1}})];case 1:t=e.sent(),e.label=2;case 2:return void 0!==(n=t.workflow)&&""!==n||(n=null),1===t.publicAccess&&r?[2,exports.msg403]:0===t.publicAccess&&r?[2,exports.msg403]:null===d?[2,msg404]:(o=null!==d)?[4,arrays_1.found(i.role,["admin"])]:[3,4];case 3:o=e.sent(),e.label=4;case 4:return o?[2,n]:null!==d&&2===t.rightMode?[2,n]:null===d?[2,msg409]:[4,arrays_1.foundPermission(i.role,d.accessRoles)];case 5:return c=e.sent(),(a=c.some(function(e){if(3===e)return!0}))?[2,n]:!a&&(a=c.some(function(e){if(2===e)return!0}))?(u.openAccess=d.openAccess,u.accessRoles=d.accessRoles,u.accessUsers=d.accessUsers,u.accessEmails=d.accessEmails,[2,n]):a?[3,7]:[4,arrays_1.foundPermission(i.role,d.accessUsers)];case 6:if(c=e.sent(),a=c.some(function(e){if(3===e)return!0}))return[2,n];if(!a&&(a=c.some(function(e){if(2===e)return!0})))return u.openAccess=d.openAccess,u.accessRoles=d.accessRoles,u.accessUsers=d.accessUsers,u.accessEmails=d.accessEmails,[2,n];e.label=7;case 7:return a?[3,9]:[4,arrays_1.foundPermission(i.role,d.accessEmails)];case 8:if(c=e.sent(),a=c.some(function(e){if(3===e)return!0}))return[2,n];if(!a&&(a=c.some(function(e){if(2===e)return!0})))return u.openAccess=d.openAccess,u.accessRoles=d.accessRoles,u.accessUsers=d.accessUsers,u.accessEmails=d.accessEmails,[2,n];e.label=9;case 9:return[2,s]}})})},exports.permissionDELETE=function(e,a,s,i,l){return __awaiter(_this,void 0,void 0,function(){var s,r,t,n,o,c;return __generator(this,function(e){switch(e.label){case 0:return s=exports.msg403,r=null==a||""===a,t={},null==i?[3,2]:[4,config_2.getClient().db("vuejx_cfg").collection("vuejx_collection").findOne({shortName:i},{fields:{rightMode:1,publicAccess:1,accessRoles:1,accessUsers:1,accessEmails:1,workflow:1}})];case 1:t=e.sent(),e.label=2;case 2:return 1===t.publicAccess&&r?[2,exports.msg403]:0===t.publicAccess&&r?[2,exports.msg403]:null===l?[2,msg404]:(n=2===t.rightMode)?[3,4]:[4,arrays_1.found(a.role,["admin"])];case 3:n=e.sent(),e.label=4;case 4:return n?null!==l?[2,null]:[2,msg409]:[4,arrays_1.foundPermission(a.role,l.accessRoles)];case 5:return o=e.sent(),(c=o.some(function(e){if(4===e)return!0}))?[2,null]:c?[3,7]:[4,arrays_1.foundPermission(a.role,l.accessUsers)];case 6:if(o=e.sent(),c=o.some(function(e){if(4===e)return!0}))return[2,null];e.label=7;case 7:return[2,s]}})})},exports.permissionQuery=function(e,s){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return[2]})})},exports.permissionQueryES=function(o,e,s,c){return __awaiter(_this,void 0,void 0,function(){var s,r,t,n;return __generator(this,function(e){switch(e.label){case 0:return s=null,s={bool:{should:[{bool:{must:[{bool:{must_not:{exists:{field:"accessRoles"}}}},{bool:{must_not:{exists:{field:"accessUsers"}}}},{bool:{must_not:{exists:{field:"accessEmails"}}}}]}}]}},o.hasOwnProperty("tokenExpired")&&o.tokenExpired?[4,buildQuery(c,s)]:[3,2];case 1:return c=e.sent(),[3,5];case 2:return[4,arrays_1.found(o.role,["admin"])];case 3:if(e.sent())s.bool.should=[];else for(r=0,t=o.role;r<t.length;r++)n=t[r],s.bool.should.push({bool:{must:{match:{"accessRoles.shortName":n}}}});return[4,buildQuery(c,s)];case 4:c=e.sent(),e.label=5;case 5:return[2,c]}})})};var buildQuery=function(r,t){return __awaiter(_this,void 0,void 0,function(){var s;return __generator(this,function(e){return void 0!==r.query?void 0!==r.query.bool&&void 0===r.query.bool.must?r.query.bool.must=[t]:void 0!==r.query.bool&&void 0!==r.query.bool.must?Array.isArray(r.query.bool.must)?r.query.bool.must.push(t):(s=JSON.parse(JSON.stringify(r.query.bool.must)),r.query.bool.must=[t],r.query.bool.must.push(s)):r.query=t:r.query=t,[2,r]})})};