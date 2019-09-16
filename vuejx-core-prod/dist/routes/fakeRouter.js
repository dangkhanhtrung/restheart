"use strict";var __awaiter=this&&this.__awaiter||function(a,s,i,u){return new(i=i||Promise)(function(e,r){function o(e){try{n(u.next(e))}catch(e){r(e)}}function t(e){try{n(u.throw(e))}catch(e){r(e)}}function n(r){r.done?e(r.value):new i(function(e){e(r.value)}).then(o,t)}n((u=u.apply(a,s||[])).next())})},__generator=this&&this.__generator||function(o,t){var n,a,s,e,i={label:0,sent:function(){if(1&s[0])throw s[1];return s[1]},trys:[],ops:[]};return e={next:r(0),throw:r(1),return:r(2)},"function"==typeof Symbol&&(e[Symbol.iterator]=function(){return this}),e;function r(r){return function(e){return function(r){if(n)throw new TypeError("Generator is already executing.");for(;i;)try{if(n=1,a&&(s=2&r[0]?a.return:r[0]?a.throw||((s=a.return)&&s.call(a),0):a.next)&&!(s=s.call(a,r[1])).done)return s;switch(a=0,s&&(r=[2&r[0],s.value]),r[0]){case 0:case 1:s=r;break;case 4:return i.label++,{value:r[1],done:!1};case 5:i.label++,a=r[1],r=[0];continue;case 7:r=i.ops.pop(),i.trys.pop();continue;default:if(!(s=0<(s=i.trys).length&&s[s.length-1])&&(6===r[0]||2===r[0])){i=0;continue}if(3===r[0]&&(!s||r[1]>s[0]&&r[1]<s[3])){i.label=r[1];break}if(6===r[0]&&i.label<s[1]){i.label=s[1],s=r;break}if(s&&i.label<s[2]){i.label=s[2],i.ops.push(r);break}s[2]&&i.ops.pop(),i.trys.pop();continue}r=t.call(o,i)}catch(e){r=[6,e],a=0}finally{n=s=0}if(5&r[0])throw r[1];return{value:r[0]?r[1]:void 0,done:!0}}([r,e])}}},_this=this;Object.defineProperty(exports,"__esModule",{value:!0});var Router=require("koa-router"),oai_response_1=require("../oai/core/oai-response"),core_oai_provider_1=require("../oai/core/core-oai-provider"),mongodb_mapper_1=require("../oai/oai-provider/repository/mongodb-mapper"),mongodbDcMapper=new mongodb_mapper_1.MongodbDcMapper,fakeRouter=new Router({prefix:"/api"});fakeRouter.get("/mongodb/oai",function(t,n){return __awaiter(_this,void 0,void 0,function(){var r,o;return __generator(this,function(e){switch(e.label){case 0:return t.response.type="xml",t.query.metadataPrefix&&"Identify"===t.query.verb?(r=[],t.response.body=oai_response_1.generateResponse(t.query,t.request.header.host+t.request.url,r),[3,4]):[3,1];case 1:return t.query.metadataPrefix&&"ListRecords"===t.query.verb?[4,mongodbDcMapper.mapListRecordsAsync(t)]:[3,3];case 2:return r=e.sent(),t.response.body=oai_response_1.generateResponse(t.query,t.request.header.host+t.request.url,r),[3,4];case 3:t.query.metadataPrefix&&t.query.identifier&&"GetRecord"===t.query.verb?null===(r=[])?(o={baseUrl:t.request.header.host+t.request.url},t.response.body=oai_response_1.generateException(o,core_oai_provider_1.EXCEPTION_CODES.BAD_ARGUMENT)):0===r.length?(o={baseUrl:t.request.header.host+t.request.url},t.response.body=oai_response_1.generateException(o,core_oai_provider_1.EXCEPTION_CODES.NO_RECORDS_MATCH)):t.response.body=oai_response_1.generateResponse(t.query,t.request.header.host+t.request.url,r):"ListMetadataFormats"===t.query.verb?(r=[],t.response.body=oai_response_1.generateResponse(t.query,t.request.header.host+t.request.url,r)):(o={baseUrl:t.request.header.host+t.request.url},t.response.body=oai_response_1.generateException(o,core_oai_provider_1.EXCEPTION_CODES.BAD_VERB)),e.label=4;case 4:return n(),[2]}})})}),exports.default=fakeRouter;