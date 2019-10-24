"use strict";var __awaiter=this&&this.__awaiter||function(o,s,c,u){return new(c=c||Promise)(function(e,t){function n(e){try{i(u.next(e))}catch(e){t(e)}}function r(e){try{i(u.throw(e))}catch(e){t(e)}}function i(t){t.done?e(t.value):new c(function(e){e(t.value)}).then(n,r)}i((u=u.apply(o,s||[])).next())})},__generator=this&&this.__generator||function(n,r){var i,o,s,e,c={label:0,sent:function(){if(1&s[0])throw s[1];return s[1]},trys:[],ops:[]};return e={next:t(0),throw:t(1),return:t(2)},"function"==typeof Symbol&&(e[Symbol.iterator]=function(){return this}),e;function t(t){return function(e){return function(t){if(i)throw new TypeError("Generator is already executing.");for(;c;)try{if(i=1,o&&(s=2&t[0]?o.return:t[0]?o.throw||((s=o.return)&&s.call(o),0):o.next)&&!(s=s.call(o,t[1])).done)return s;switch(o=0,s&&(t=[2&t[0],s.value]),t[0]){case 0:case 1:s=t;break;case 4:return c.label++,{value:t[1],done:!1};case 5:c.label++,o=t[1],t=[0];continue;case 7:t=c.ops.pop(),c.trys.pop();continue;default:if(!(s=0<(s=c.trys).length&&s[s.length-1])&&(6===t[0]||2===t[0])){c=0;continue}if(3===t[0]&&(!s||t[1]>s[0]&&t[1]<s[3])){c.label=t[1];break}if(6===t[0]&&c.label<s[1]){c.label=s[1],s=t;break}if(s&&c.label<s[2]){c.label=s[2],c.ops.push(t);break}s[2]&&c.ops.pop(),c.trys.pop();continue}t=r.call(n,c)}catch(e){t=[6,e],o=0}finally{i=s=0}if(5&t[0])throw t[1];return{value:t[0]?t[1]:void 0,done:!0}}([t,e])}}},_this=this;Object.defineProperty(exports,"__esModule",{value:!0});var elasticsearch_1=require("elasticsearch"),config_1=require("../config"),vuejxcfg_1=require("./vuejxcfg");exports.elasticSearchConnection=function(){return new elasticsearch_1.Client(config_1.esConfig)},exports.pingElasticsearch=function(t){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return[2,t.ping({requestTimeout:3e3})]})})},exports.createIndex=function(t,n){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return exports.indexExists(t,n.toLowerCase()).then(function(e){e||exports.init(t,n)}),[2]})})},exports.initIndex=function(n){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return["vuejx_cfg___vuejx_page","vuejx_cfg___vuejx_site","vuejx_cfg___vuejx_db","vuejx_cfg___vuejx_collection","vuejx_cfg___scope","vuejx_cfg___projection","vuejx_cfg___oai_pmh_config","vuejx_cfg___oai_pmh_async","vuejx_cfg____schemas","profilesdatabase___person","directory___role","directory___profile","directory___dict_collection","directory___dict_item","directory___permission","directory___storage","directory___open_access"].forEach(function(t){exports.indexExists(n,t.toLowerCase()).then(function(e){e||exports.init(n,t),vuejxcfg_1.mappingIndices(n,t)})}),[2]})})},exports.removeIndex=function(t,n){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return exports.indexExists(t,n.toLowerCase()).then(function(e){e&&t.indices.delete({index:n.toLowerCase()})}),[2]})})},exports.reNewIndex=function(t,n){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){switch(e.label){case 0:return[4,t.indices.delete({index:n.toLowerCase()}).catch(function(e){}).finally(function(){return __awaiter(this,void 0,void 0,function(){return __generator(this,function(e){switch(e.label){case 0:return[4,exports.init(t,n)];case 1:return e.sent(),[2]}})})})];case 1:return e.sent(),[2]}})})},exports.addIndex=function(t,n){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return exports.indexExists(t,n.toLowerCase()).then(function(e){e||exports.init(t,n)}),[2]})})},exports.addDocument=function(n,r,i,o,s){return __awaiter(_this,void 0,void 0,function(){var t;return __generator(this,function(e){switch(e.label){case 0:return e.trys.push([0,2,,3]),[4,n.index({index:r,type:i,id:o,body:s})];case 1:return[2,e.sent()];case 2:return t=e.sent(),console.error(t),[2,void 0];case 3:return[2]}})})},exports.bulkDocument=function(n,r){return __awaiter(_this,void 0,void 0,function(){var t;return __generator(this,function(e){switch(e.label){case 0:return e.trys.push([0,2,,3]),[4,n.bulk({body:r})];case 1:return[2,e.sent()];case 2:return t=e.sent(),console.error(t),[2,void 0];case 3:return[2]}})})},exports.updateDocument=function(n,r,i,o,s){return __awaiter(_this,void 0,void 0,function(){var t;return __generator(this,function(e){switch(e.label){case 0:return e.trys.push([0,2,,3]),[4,n.update({index:r,type:i,id:o,body:{doc:s}})];case 1:return[2,e.sent()];case 2:return t=e.sent(),console.error(t),[2,void 0];case 3:return[2]}})})},exports.putMapping=function(client,documentType,body){return __awaiter(_this,void 0,void 0,function(){var raw,config,_i,config_2,field;return __generator(this,function(_a){try{for(raw=eval("( "+body.config+" )"),config=raw.config.form,_i=0,config_2=config;_i<config_2.length;_i++)field=config_2[_i],field.hasOwnProperty("aggs")&&console.log(field)}catch(e){return console.error(e),[2,void 0]}return[2]})})},exports.deleteDocument=function(t,n,r,i){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){switch(e.label){case 0:return e.trys.push([0,2,,3]),[4,t.delete({index:n.toLowerCase(),type:r,id:String(i)})];case 1:return[2,e.sent()];case 2:return e.sent(),[2,void 0];case 3:return[2]}})})},exports.searchDocument=function(n,r,i,o){return __awaiter(_this,void 0,void 0,function(){var t;return __generator(this,function(e){switch(e.label){case 0:return e.trys.push([0,2,,3]),[4,n.search({index:r,type:i,body:o})];case 1:return[2,e.sent()];case 2:return t=e.sent(),console.error(t),[2,void 0];case 3:return[2]}})})},exports.elasticClient=exports.elasticSearchConnection(),exports.init=function(t,n){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){switch(e.label){case 0:return[4,t.indices.create({index:n.toLowerCase(),body:{settings:{index:{number_of_shards:2,number_of_replicas:1,refresh_interval:"1s",max_result_window:config_1.max_result_window},analysis:{normalizer:{lowercase_normalizer:{type:"custom",char_filter:[],filter:["lowercase","asciifolding"]}}}}}})];case 1:return e.sent(),[4,vuejxcfg_1.mappingIndices(t,n)];case 2:return e.sent(),[2]}})})},exports.initClone=function(t,n){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return exports.indexExists(t,n).then(function(e){e||exports.init(t,n)}),[2]})})},exports.indexExists=function(t,n){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){switch(e.label){case 0:return[4,t.indices.exists({index:n.toLowerCase()})];case 1:return[2,e.sent()]}})})};var initBaseEntity=function(e,t){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return[2]})})};exports.initBaseUser=function(e,t,n){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return[2]})})};