"use strict";var __awaiter=this&&this.__awaiter||function(i,s,o,c){return new(o=o||Promise)(function(e,t){function r(e){try{a(c.next(e))}catch(e){t(e)}}function n(e){try{a(c.throw(e))}catch(e){t(e)}}function a(t){t.done?e(t.value):new o(function(e){e(t.value)}).then(r,n)}a((c=c.apply(i,s||[])).next())})},__generator=this&&this.__generator||function(r,n){var a,i,s,e,o={label:0,sent:function(){if(1&s[0])throw s[1];return s[1]},trys:[],ops:[]};return e={next:t(0),throw:t(1),return:t(2)},"function"==typeof Symbol&&(e[Symbol.iterator]=function(){return this}),e;function t(t){return function(e){return function(t){if(a)throw new TypeError("Generator is already executing.");for(;o;)try{if(a=1,i&&(s=2&t[0]?i.return:t[0]?i.throw||((s=i.return)&&s.call(i),0):i.next)&&!(s=s.call(i,t[1])).done)return s;switch(i=0,s&&(t=[2&t[0],s.value]),t[0]){case 0:case 1:s=t;break;case 4:return o.label++,{value:t[1],done:!1};case 5:o.label++,i=t[1],t=[0];continue;case 7:t=o.ops.pop(),o.trys.pop();continue;default:if(!(s=0<(s=o.trys).length&&s[s.length-1])&&(6===t[0]||2===t[0])){o=0;continue}if(3===t[0]&&(!s||t[1]>s[0]&&t[1]<s[3])){o.label=t[1];break}if(6===t[0]&&o.label<s[1]){o.label=s[1],s=t;break}if(s&&o.label<s[2]){o.label=s[2],o.ops.push(t);break}s[2]&&o.ops.pop(),o.trys.pop();continue}t=n.call(r,o)}catch(e){t=[6,e],i=0}finally{a=s=0}if(5&t[0])throw t[1];return{value:t[0]?t[1]:void 0,done:!0}}([t,e])}}},_this=this;Object.defineProperty(exports,"__esModule",{value:!0});var config_1=require("../config"),httpRequest_1=require("../api/httpRequest"),config_2=require("../config"),vuejxcfg_directory_1=require("./vuejxcfg_directory"),vuejxcfg_profilesdatabase_1=require("./vuejxcfg_profilesdatabase"),vuejxcfg_base_1=require("./vuejxcfg_base"),crypto=require("crypto");exports.mappingIndices=function(elasticClient,indices){return __awaiter(_this,void 0,void 0,function(){var _a,db,collection,httpReq,pullDefaultDB,metadataConfig,propertiesPut,_i,metadataConfig_1,el;return __generator(this,function(_b){switch(_b.label){case 0:return _a=indices.split("___"),db=_a[0],collection=_a[1],httpReq=new httpRequest_1.default(config_1.PROJECT_SERVICE_HOST),[4,httpReq.fetch_rest(config_1.PROJECT_SERVICE_HOST+"/vuejx_cfg/vuejx_collection",{params:{filter:{shortName:collection}}})];case 1:if(pullDefaultDB=_b.sent(),!(0<pullDefaultDB.data._embedded.length))return[3,3];for(metadataConfig=pullDefaultDB.data._embedded[0].metadataConfig,""!==metadataConfig&&(metadataConfig=eval("( "+metadataConfig+" )").form),propertiesPut={properties:{}},_i=0,metadataConfig_1=metadataConfig;_i<metadataConfig_1.length;_i++)el=metadataConfig_1[_i],void 0!==el.model&&null!==el.model&&""!==el.model&&("number"===el.dataType?propertiesPut.properties[el.model]={type:"long"}:"string"===el.dataType?propertiesPut.properties[el.model]={type:"keyword"}:propertiesPut.properties[el.model]={type:el.dataType},"storeDb"===el.model&&(propertiesPut.properties[el.model]={type:"keyword"}),"publicOAI"===el.model&&(propertiesPut.properties[el.model]={type:"boolean"}),"recordStatus"===el.model&&(propertiesPut.properties[el.model]={type:"boolean"}));return propertiesPut.properties.type={type:"text"},propertiesPut.properties.username={type:"keyword"},[4,httpReq.put_rest(config_2.esConfig.host+"/"+indices+"/_mapping",propertiesPut,{})];case 2:_b.sent(),_b.label=3;case 3:return[2]}})})},exports.initVueJX=function(u){return __awaiter(_this,void 0,void 0,function(){var t,r,n,a,i,s,o,c;return __generator(this,function(e){switch(e.label){case 0:return t=(new Date).getTime(),[4,(r=new httpRequest_1.default(config_1.PROJECT_SERVICE_HOST)).put_rest(config_1.PROJECT_SERVICE_HOST+"/profilesdatabase",{})];case 1:return e.sent(),[4,r.put_rest(config_1.PROJECT_SERVICE_HOST+"/vuejx_cfg",{})];case 2:return e.sent(),[4,r.put_rest(config_1.PROJECT_SERVICE_HOST+"/directory",{})];case 3:return e.sent(),[4,r.put_rest(config_1.PROJECT_SERVICE_HOST+"/vuejx_cfg/_schemas",{})];case 4:return e.sent(),[4,r.put_rest(config_1.PROJECT_SERVICE_HOST+"/vuejx_cfg/vuejx_collection",{})];case 5:return e.sent(),[4,r.put_rest(config_1.PROJECT_SERVICE_HOST+"/oauth2",{})];case 6:return e.sent(),[4,r.put_rest(config_1.PROJECT_SERVICE_HOST+"/oauth2/users",{})];case 7:return e.sent(),[4,r.put_rest(config_1.PROJECT_SERVICE_HOST+"/oauth2/oauthclients",{})];case 8:return e.sent(),[4,vuejxcfg_directory_1.initRole(u,r)];case 9:return e.sent(),[4,vuejxcfg_directory_1.initPermission(u,r)];case 10:return e.sent(),[4,vuejxcfg_directory_1.initOpenAccess(u,r)];case 11:return e.sent(),[4,vuejxcfg_directory_1.initMode(u,r)];case 12:return e.sent(),[4,vuejxcfg_directory_1.initDictCollection(u,r)];case 13:return e.sent(),[4,vuejxcfg_directory_1.initItem(u,r)];case 14:return e.sent(),[4,vuejxcfg_profilesdatabase_1.initPerson(u,r)];case 15:return e.sent(),[4,vuejxcfg_base_1.initSite(u,r)];case 16:return e.sent(),[4,vuejxcfg_base_1.initPage(u,r)];case 17:return e.sent(),[4,vuejxcfg_base_1.initDB(u,r)];case 18:return e.sent(),[4,vuejxcfg_base_1.initCollection(u,r)];case 19:return e.sent(),[4,vuejxcfg_base_1.initOaiPmhConfig(u,r)];case 20:return e.sent(),[4,vuejxcfg_base_1.initOaiPmhAsync(u,r)];case 21:return e.sent(),[4,vuejxcfg_base_1.initScope(u,r)];case 22:return e.sent(),[4,vuejxcfg_base_1.initProjection(u,r)];case 23:return e.sent(),[4,r.fetch_rest(config_1.PROJECT_SERVICE_HOST+'/vuejx_cfg/vuejx_collection?filter={"shortName":"_schemas"}',{})];case 24:return 0!==e.sent().data._embedded.length?[3,26]:[4,r.search_rest(config_1.PROJECT_SERVICE_HOST+"/vuejx_cfg/vuejx_collection",{type:"vuejx_collection",createdAt:t,modifiedAt:t,shortName:"_schemas",email:"binhth.vuejx@gmail.com",username:"binhth",title:"JSON Schema Validation",order:9999,status:{_source:{shortName:"activate",title:"Activate"}},site:"guest",storage:"regular",openAccess:2,accessRoles:[],accessUsers:[],accessEmails:[],description:"JSON Schema Validation",metadataConfig:"{\n                type: 0,\n                form: [\n                    {\n                        model: 'shortName',\n                        dataType: 'keyword',\n                        type: 'text',\n                        class: 'xs12',\n                        label: 'ShortName:'\n                    },\n                    {\n                        model: 'tableConfig',\n                        dataType: 'text',\n                        type: 'codemirror',\n                        class: 'xs12',\n                        label: 'tableConfig:'\n                    },\n                ]\n            }",tableConfig:"{\n                title: \"JSON Schema Validation\",\n                columns: [\"shortName\"],\n                config_columns: [\n                    {\n                        field: '_source.id',\n                        field_query: 'id',\n                        field_sort: 'id',\n                        headerText: 'ShortName',\n                        textAlign: 'left',\n                        width: 220,\n                        minWidth: 220\n                    }\n                ],\n                sort: [\n                    { \"order\": \"asc\" }\n                ]\n            }"})];case 25:e.sent(),e.label=26;case 26:return[4,r.fetch_rest(config_1.PROJECT_SERVICE_HOST+'/vuejx_cfg/vuejx_collection?filter={"schema":{ $exists: true }}',{})];case 27:for(n=e.sent(),a=0,i=n.data;a<i.length;a++)s=i[a],r.put_rest(config_1.PROJECT_SERVICE_HOST+"/"+s.storeDb+"/"+s.shortName,{checkers:[{name:"jsonSchema",args:{schemaId:s.schema,schemaStoreDb:"vuejx_cfg"}}]});return[4,r.fetch_rest(config_1.PROJECT_SERVICE_HOST+'/oauth2/users?filter={"username":"admin"}',{})];case 28:return 0!==e.sent().data._embedded.length?[3,30]:[4,r.search_rest(config_1.PROJECT_SERVICE_HOST+"/oauth2/users",{username:"admin",password:crypto.createHash("sha256").update("admin").digest("hex"),scope:"admin",roles:["admin"]})];case 29:e.sent(),e.label=30;case 30:return[4,r.fetch_rest(config_1.PROJECT_SERVICE_HOST+'/oauth2/oauthclients?filter={"name":"admin"}',{})];case 31:return 0!==e.sent().data._embedded.length?[3,34]:[4,r.fetch_rest(config_1.PROJECT_SERVICE_HOST+'/oauth2/users?filter={"username":"admin"}',{})];case 32:return 0<(o=e.sent()).data._embedded.length?(c=o.data._embedded[0]._id.$oid,[4,r.search_rest("http://security:3000/clients",{user:c,name:"admin",redirectUris:["/oauth/callback"]})]):[3,34];case 33:e.sent(),e.label=34;case 34:return[2]}})})};