"use strict";var __awaiter=this&&this.__awaiter||function(a,s,i,u){return new(i=i||Promise)(function(e,r){function t(e){try{n(u.next(e))}catch(e){r(e)}}function o(e){try{n(u.throw(e))}catch(e){r(e)}}function n(r){r.done?e(r.value):new i(function(e){e(r.value)}).then(t,o)}n((u=u.apply(a,s||[])).next())})},__generator=this&&this.__generator||function(t,o){var n,a,s,e,i={label:0,sent:function(){if(1&s[0])throw s[1];return s[1]},trys:[],ops:[]};return e={next:r(0),throw:r(1),return:r(2)},"function"==typeof Symbol&&(e[Symbol.iterator]=function(){return this}),e;function r(r){return function(e){return function(r){if(n)throw new TypeError("Generator is already executing.");for(;i;)try{if(n=1,a&&(s=2&r[0]?a.return:r[0]?a.throw||((s=a.return)&&s.call(a),0):a.next)&&!(s=s.call(a,r[1])).done)return s;switch(a=0,s&&(r=[2&r[0],s.value]),r[0]){case 0:case 1:s=r;break;case 4:return i.label++,{value:r[1],done:!1};case 5:i.label++,a=r[1],r=[0];continue;case 7:r=i.ops.pop(),i.trys.pop();continue;default:if(!(s=0<(s=i.trys).length&&s[s.length-1])&&(6===r[0]||2===r[0])){i=0;continue}if(3===r[0]&&(!s||r[1]>s[0]&&r[1]<s[3])){i.label=r[1];break}if(6===r[0]&&i.label<s[1]){i.label=s[1],s=r;break}if(s&&i.label<s[2]){i.label=s[2],i.ops.push(r);break}s[2]&&i.ops.pop(),i.trys.pop();continue}r=o.call(t,i)}catch(e){r=[6,e],a=0}finally{n=s=0}if(5&r[0])throw r[1];return{value:r[0]?r[1]:void 0,done:!0}}([r,e])}}},__asyncValues=this&&this.__asyncValues||function(n){if(!Symbol.asyncIterator)throw new TypeError("Symbol.asyncIterator is not defined.");var e,r=n[Symbol.asyncIterator];return r?r.call(n):(n="function"==typeof __values?__values(n):n[Symbol.iterator](),e={},t("next"),t("throw"),t("return"),e[Symbol.asyncIterator]=function(){return this},e);function t(o){e[o]=n[o]&&function(t){return new Promise(function(e,r){(function(r,e,t,o){Promise.resolve(o).then(function(e){r({value:e,done:t})},e)})(e,r,(t=n[o](t)).done,t.value)})}}};Object.defineProperty(exports,"__esModule",{value:!0});var api_1=require("../../../api"),jsonMapper=require("json-mapper-json"),oai_pmh_1=require("oai-pmh"),AsyncMapper=function(){function AsyncMapper(){}return AsyncMapper.prototype.oaiAsyncdes=function(id,user){var e_1,_a;return __awaiter(this,void 0,void 0,function(){var record,item,converter,oai_json_mapping_datasource,apiResource,_b,_c,_i,key,_d,_e,oaiPmh,identifierIterator,identifierIteratorData,identifierIteratorData2,listToRemove,inComingRecord,inComingRecordState,bulkData,indexData,result,identifierIterator_1,identifierIterator_1_1,identifier,desInput,key,_f,_g,keyOb,_h,_j,keyOb,resultProcess,e_1_1;return __generator(this,function(_k){switch(_k.label){case 0:return[4,api_1.vuejxService.repoExistOAIES(user,id)];case 1:if(record=_k.sent(),item=null,converter=!1,""!==record.oai_json&&void 0!==record.oai_json&&(item=eval("( "+record.oai_json+" )"),converter=!0),oai_json_mapping_datasource={},apiResource={},""===record.oai_json_mapping_datasource||void 0===record.oai_json_mapping_datasource)return[3,5];for(_c in oai_json_mapping_datasource=JSON.parse(record.oai_json_mapping_datasource),_b=[],oai_json_mapping_datasource)_b.push(_c);_i=0,_k.label=2;case 2:return _i<_b.length?(key=_b[_i],"string"!=typeof oai_json_mapping_datasource[key]?[3,4]:(_d=apiResource,_e=key,[4,api_1.vuejxService.pullSourceMapper(user,oai_json_mapping_datasource[key])])):[3,5];case 3:_d[_e]=_k.sent(),_k.label=4;case 4:return _i++,[3,2];case 5:oaiPmh=new oai_pmh_1.OaiPmh(record.oai_input_url),identifierIterator=oaiPmh.listRecords({metadataPrefix:record.prefix}),identifierIteratorData={ListRecords:{record:[]}},identifierIteratorData2={ListRecords:{record:[]}},listToRemove=[],inComingRecord=0,inComingRecordState=0,bulkData=[],indexData=0,result={insert:0,update:0,delete:0},_k.label=6;case 6:_k.trys.push([6,15,16,21]),identifierIterator_1=__asyncValues(identifierIterator),_k.label=7;case 7:return[4,identifierIterator_1.next()];case 8:return identifierIterator_1_1=_k.sent(),identifierIterator_1_1.done?[3,14]:(identifier=identifierIterator_1_1.value,indexData+=1,identifier.hasOwnProperty("header")&&identifier.header.hasOwnProperty("$")&&"deleted"===identifier.header.$.status&&listToRemove.push(identifier.metadata),inComingRecord+=1,desInput={},converter?(identifierIteratorData.ListRecords.record=[identifier],[4,jsonMapper(identifierIteratorData,item).then(function(e){desInput=e.hits[0]})]):[3,10]);case 9:return _k.sent(),[3,11];case 10:desInput=identifier.metadata.metadataPrefix,desInput.hasOwnProperty("$")&&delete desInput.$,inComingRecordState+=1,_k.label=11;case 11:for(key in oai_json_mapping_datasource)if("string"!=typeof oai_json_mapping_datasource[key])if(Array.isArray(desInput[key]))for(_f=0,_g=desInput[key];_f<_g.length;_f++)keyOb=_g[_f],keyOb.hasOwnProperty("_source")&&keyOb._source.hasOwnProperty("shortName")&&""!==keyOb._source.shortName&&void 0!==keyOb._source.shortName&&null!==keyOb._source.shortName&&(keyOb._source.title=oai_json_mapping_datasource[key][keyOb._source.shortName]);else void 0!==desInput[key]&&null!==desInput[key]&&desInput[key].hasOwnProperty("_source")&&desInput[key]._source.hasOwnProperty("shortName")&&""!==desInput[key]._source.shortName&&void 0!==desInput[key]._source.shortName&&null!==desInput[key]._source.shortName&&(desInput[key]._source.title=oai_json_mapping_datasource[key][desInput[key]._source.shortName]);else if(Array.isArray(desInput[key]))for(_h=0,_j=desInput[key];_h<_j.length;_h++)keyOb=_j[_h],keyOb.hasOwnProperty("_source")&&keyOb._source.hasOwnProperty("shortName")&&""!==keyOb._source.shortName&&void 0!==keyOb._source.shortName&&null!==keyOb._source.shortName&&(keyOb._source.title=apiResource[key][keyOb._source.shortName]);else void 0!==desInput[key]&&null!==desInput[key]&&desInput[key].hasOwnProperty("_source")&&desInput[key]._source.hasOwnProperty("shortName")&&""!==desInput[key]._source.shortName&&void 0!==desInput[key]._source.shortName&&null!==desInput[key]._source.shortName&&(desInput[key]._source.title=apiResource[key][desInput[key]._source.shortName]);return[4,api_1.vuejxService.processData(desInput,record.storeDb,record.storeCollection)];case 12:if(resultProcess=_k.sent(),result.insert=result.insert+resultProcess.insert,result.update=result.update+resultProcess.update,result.delete=result.delete+resultProcess.delete,100<=indexData)return[3,14];_k.label=13;case 13:return[3,7];case 14:return[3,21];case 15:return e_1_1=_k.sent(),e_1={error:e_1_1},[3,21];case 16:return _k.trys.push([16,,19,20]),identifierIterator_1_1&&!identifierIterator_1_1.done&&(_a=identifierIterator_1.return)?[4,_a.call(identifierIterator_1)]:[3,18];case 17:_k.sent(),_k.label=18;case 18:return[3,20];case 19:if(e_1)throw e_1.error;return[7];case 20:return[7];case 21:return result.totalInput=inComingRecord,result.totalRemove=listToRemove.length,result.storeCollection=record.storeCollection,result.storeDb=record.storeDb,result.storeCode=record.shortName,[2,identifierIteratorData2]}})})},AsyncMapper}();exports.AsyncMapper=AsyncMapper;