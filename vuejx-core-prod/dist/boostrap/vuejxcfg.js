"use strict";var __awaiter=this&&this.__awaiter||function(i,a,s,c){return new(s=s||Promise)(function(e,t){function r(e){try{o(c.next(e))}catch(e){t(e)}}function n(e){try{o(c.throw(e))}catch(e){t(e)}}function o(t){t.done?e(t.value):new s(function(e){e(t.value)}).then(r,n)}o((c=c.apply(i,a||[])).next())})},__generator=this&&this.__generator||function(r,n){var o,i,a,e,s={label:0,sent:function(){if(1&a[0])throw a[1];return a[1]},trys:[],ops:[]};return e={next:t(0),throw:t(1),return:t(2)},"function"==typeof Symbol&&(e[Symbol.iterator]=function(){return this}),e;function t(t){return function(e){return function(t){if(o)throw new TypeError("Generator is already executing.");for(;s;)try{if(o=1,i&&(a=2&t[0]?i.return:t[0]?i.throw||((a=i.return)&&a.call(i),0):i.next)&&!(a=a.call(i,t[1])).done)return a;switch(i=0,a&&(t=[2&t[0],a.value]),t[0]){case 0:case 1:a=t;break;case 4:return s.label++,{value:t[1],done:!1};case 5:s.label++,i=t[1],t=[0];continue;case 7:t=s.ops.pop(),s.trys.pop();continue;default:if(!(a=0<(a=s.trys).length&&a[a.length-1])&&(6===t[0]||2===t[0])){s=0;continue}if(3===t[0]&&(!a||t[1]>a[0]&&t[1]<a[3])){s.label=t[1];break}if(6===t[0]&&s.label<a[1]){s.label=a[1],a=t;break}if(a&&s.label<a[2]){s.label=a[2],s.ops.push(t);break}a[2]&&s.ops.pop(),s.trys.pop();continue}t=n.call(r,s)}catch(e){t=[6,e],i=0}finally{o=a=0}if(5&t[0])throw t[1];return{value:t[0]?t[1]:void 0,done:!0}}([t,e])}}},_this=this;Object.defineProperty(exports,"__esModule",{value:!0});var config_1=require("../config"),httpRequest_1=require("../api/httpRequest"),config_2=require("../config"),vuejxcfg_directory_1=require("./vuejxcfg_directory"),vuejxcfg_profilesdatabase_1=require("./vuejxcfg_profilesdatabase"),vuejxcfg_base_1=require("./vuejxcfg_base"),crypto=require("crypto"),config_3=require("../config");exports.mappingIndices=function(elasticClient,indices){return __awaiter(_this,void 0,void 0,function(){var httpReq,_a,db,collection,pullDefaultDB,metadataConfig,confiii,propertiesPut,_i,metadataConfig_1,el;return __generator(this,function(_b){switch(_b.label){case 0:return httpReq=new httpRequest_1.default(config_1.PROJECT_SERVICE_HOST),_a=indices.split("___"),db=_a[0],collection=_a[1],[4,config_3.getClient().db("vuejx_cfg").collection("vuejx_collection").findOne({shortName:collection},{username:1})];case 1:if(pullDefaultDB=_b.sent(),null==pullDefaultDB)return[3,3];for(metadataConfig=[],confiii=pullDefaultDB.metadataConfig,""!==confiii&&void 0!==eval("( "+confiii+" )")&&(metadataConfig=eval("( "+confiii+" )").form),propertiesPut={properties:{}},_i=0,metadataConfig_1=metadataConfig;_i<metadataConfig_1.length;_i++)el=metadataConfig_1[_i],void 0!==el.model&&null!==el.model&&""!==el.model&&"storeDb"!==el.model&&"publicOAI"!==el.model&&"recordStatus"!==el.model&&"type"!==el.model&&"username"!==el.model&&"order"!==el.model&&("number"===el.dataType?propertiesPut.properties[el.model]={type:"long"}:"string"===el.dataType?propertiesPut.properties[el.model]={type:"keyword",normalizer:"lowercase_normalizer"}:"object"===el.dataType?(propertiesPut.properties[el.model+"._source.shortName"]={type:"keyword",normalizer:"lowercase_normalizer"},propertiesPut.properties[el.model+"._source.shortNameRef"]={type:"keyword",normalizer:"lowercase_normalizer"},propertiesPut.properties[el.model+"._source.title"]={type:"keyword",normalizer:"lowercase_normalizer"},"not_ngay"===el.model&&(propertiesPut.properties["not_ngay._source.gio_bendi"]={type:"object"},propertiesPut.properties["not_ngay._source.gio_bendi.status"]={type:"long"},propertiesPut.properties["not_ngay._source.gio_bendi.ben_xe"]={type:"keyword"},propertiesPut.properties["not_ngay._source.gio_benden"]={type:"object"},propertiesPut.properties["not_ngay._source.gio_benden.ben_xe"]={type:"keyword"},propertiesPut.properties["not_ngay._source.gio_benden.status"]={type:"long"})):"keyword"===el.dataType?propertiesPut.properties[el.model]={type:el.dataType,normalizer:"lowercase_normalizer"}:propertiesPut.properties[el.model]={type:el.dataType});return propertiesPut.properties.storeDb={type:"keyword",normalizer:"lowercase_normalizer"},propertiesPut.properties.publicOAI={type:"boolean"},propertiesPut.properties.recordStatus={type:"boolean"},propertiesPut.properties.type={type:"text"},propertiesPut.properties.username={type:"keyword",normalizer:"lowercase_normalizer"},propertiesPut.properties.order={type:"long"},propertiesPut.properties.shortName={type:"keyword",normalizer:"lowercase_normalizer"},[4,httpReq.put_rest(config_2.esConfig.host+"/"+indices+"/_mapping",propertiesPut,{})];case 2:_b.sent(),_b.label=3;case 3:return[2]}})})},exports.initVueJX=function(a){return __awaiter(_this,void 0,void 0,function(){var t,r,n,o,i;return __generator(this,function(e){switch(e.label){case 0:return t=(new Date).getTime(),[4,config_3.getClient().db("vuejx_cfg").createCollection("_schemas")];case 1:return e.sent(),[4,config_3.getClient().db("vuejx_cfg").createCollection("vuejx_site")];case 2:return e.sent(),[4,config_3.getClient().db("vuejx_cfg").createCollection("vuejx_page")];case 3:return e.sent(),[4,config_3.getClient().db("vuejx_cfg").createCollection("vuejx_db")];case 4:return e.sent(),[4,config_3.getClient().db("vuejx_cfg").createCollection("vuejx_collection")];case 5:return e.sent(),[4,config_3.getClient().db("vuejx_cfg").createCollection("oai_pmh_config")];case 6:return e.sent(),[4,config_3.getClient().db("vuejx_cfg").createCollection("oai_pmh_async")];case 7:return e.sent(),[4,config_3.getClient().db("vuejx_cfg").createCollection("scope")];case 8:return e.sent(),[4,config_3.getClient().db("vuejx_cfg").createCollection("projection")];case 9:return e.sent(),[4,config_3.getClient().db("oauth2").createCollection("users")];case 10:return e.sent(),[4,config_3.getClient().db("oauth2").createCollection("oauthclients")];case 11:return e.sent(),[4,config_3.getClient().db("directory").createCollection("role")];case 12:return e.sent(),[4,config_3.getClient().db("directory").createCollection("permission")];case 13:return e.sent(),[4,config_3.getClient().db("directory").createCollection("open_access")];case 14:return e.sent(),[4,config_3.getClient().db("directory").createCollection("storage")];case 15:return e.sent(),[4,config_3.getClient().db("directory").createCollection("dict_collection")];case 16:return e.sent(),[4,config_3.getClient().db("directory").createCollection("dict_item")];case 17:return e.sent(),[4,config_3.getClient().db("profilesdatabase").createCollection("person")];case 18:e.sent(),(r=config_3.getClient().startSession()).startTransaction(),e.label=19;case 19:return e.trys.push([19,41,,43]),n={session:r,returnOriginal:!1},[4,config_3.getClient().db("vuejx_cfg").collection("vuejx_collection").findOne({shortName:"_schemas"},{username:1})];case 20:return null!=e.sent()?[3,23]:[4,config_3.getClient().db("vuejx_cfg").collection("vuejx_collection").insertOne({type:"vuejx_collection",createdAt:t,modifiedAt:t,shortName:"_schemas",email:"binhth.vuejx@gmail.com",username:"binhth",title:"JSON Schema Validation",order:9999,status:{_source:{shortName:"activate",title:"Activate"}},site:"guest",storage:"regular",openAccess:2,accessRoles:[],accessUsers:[],accessEmails:[],description:"JSON Schema Validation",metadataConfig:"{\n                    type: 0,\n                    form: [\n                        {\n                            model: 'shortName',\n                            dataType: 'keyword',\n                            type: 'text',\n                            class: 'xs12',\n                            label: 'ShortName:'\n                        },\n                        {\n                            model: 'tableConfig',\n                            dataType: 'text',\n                            type: 'codemirror',\n                            class: 'xs12',\n                            label: 'tableConfig:'\n                        },\n                    ]\n                }",tableConfig:"{\n                    title: \"JSON Schema Validation\",\n                    columns: [\"shortName\"],\n                    config_columns: [\n                        {\n                            field: '_source.id',\n                            field_query: 'id',\n                            field_sort: 'id',\n                            headerText: 'ShortName',\n                            textAlign: 'left',\n                            width: 220,\n                            minWidth: 220\n                        }\n                    ],\n                    sort: [\n                        { \"order\": \"asc\" }\n                    ]\n                }"},n)];case 21:return e.sent().insertedCount<=0?[4,r.abortTransaction()]:[3,23];case 22:e.sent(),r.endSession(),e.label=23;case 23:return[4,vuejxcfg_directory_1.initRole(a,r,n)];case 24:return e.sent(),[4,vuejxcfg_directory_1.initPermission(a,r,n)];case 25:return e.sent(),[4,vuejxcfg_directory_1.initOpenAccess(a,r,n)];case 26:return e.sent(),[4,vuejxcfg_directory_1.initMode(a,r,n)];case 27:return e.sent(),[4,vuejxcfg_directory_1.initDictCollection(a,r,n)];case 28:return e.sent(),[4,vuejxcfg_directory_1.initItem(a,r,n)];case 29:return e.sent(),[4,vuejxcfg_profilesdatabase_1.initPerson(a,r,n)];case 30:return e.sent(),[4,vuejxcfg_base_1.initSite(a,r,n)];case 31:return e.sent(),[4,vuejxcfg_base_1.initPage(a,r,n)];case 32:return e.sent(),[4,vuejxcfg_base_1.initDB(a,r,n)];case 33:return e.sent(),[4,vuejxcfg_base_1.initCollection(a,r,n)];case 34:return e.sent(),[4,vuejxcfg_base_1.initOaiPmhConfig(a,r,n)];case 35:return e.sent(),[4,vuejxcfg_base_1.initOaiPmhAsync(a,r,n)];case 36:return e.sent(),[4,vuejxcfg_base_1.initScope(a,r,n)];case 37:return e.sent(),[4,vuejxcfg_base_1.initProjection(a,r,n)];case 38:return e.sent(),[4,vuejxcfg_base_1.initWorkflow(a,r,n)];case 39:return e.sent(),[4,r.commitTransaction()];case 40:return e.sent(),r.endSession(),[3,43];case 41:return o=e.sent(),console.error(o),[4,r.abortTransaction()];case 42:return e.sent(),r.endSession(),[3,43];case 43:return new httpRequest_1.default(config_1.PROJECT_SERVICE_HOST),[4,config_3.getClient().db("oauth2").collection("users").findOne({username:"admin"})];case 44:return null!=(i=e.sent())?[3,46]:[4,config_3.getClient().db("oauth2").collection("users").insertOne({username:"admin",password:crypto.createHash("sha256").update("admin").digest("hex"),scope:"admin",roles:["admin"]})];case 45:e.sent(),e.label=46;case 46:return[4,config_3.getClient().db("oauth2").collection("oauthclients").findOne({name:"admin"})];case 47:return null!=e.sent()?[3,49]:null==i?[3,49]:[4,config_3.getClient().db("oauth2").collection("oauthclients").insertOne({name:"admin",clientId:crypto.createHash("md5").update(crypto.randomBytes(16)).digest("hex"),clientSecret:crypto.createHash("sha256").update(crypto.randomBytes(32)).digest("hex"),redirectUris:["/oauth/callback"],grants:["authorization_code","password","refresh_token"],scope:"admin",user:i._id})];case 48:e.sent(),e.label=49;case 49:return e.trys.push([49,51,,52]),[4,config_3.getClient().db("oauth2").addUser("oauth2_user","123456Aa",{roles:[{role:"dbAdmin",db:"oauth2"},{role:"dbOwner",db:"oauth2"},{role:"enableSharding",db:"oauth2"},{role:"userAdmin",db:"oauth2"},{role:"read",db:"oauth2"},{role:"readWrite",db:"oauth2"}]})];case 50:return e.sent(),[3,52];case 51:return e.sent(),[3,52];case 52:return[2]}})})};