"use strict";var __awaiter=this&&this.__awaiter||function(i,s,o,u){return new(o=o||Promise)(function(e,t){function a(e){try{n(u.next(e))}catch(e){t(e)}}function r(e){try{n(u.throw(e))}catch(e){t(e)}}function n(t){t.done?e(t.value):new o(function(e){e(t.value)}).then(a,r)}n((u=u.apply(i,s||[])).next())})},__generator=this&&this.__generator||function(a,r){var n,i,s,e,o={label:0,sent:function(){if(1&s[0])throw s[1];return s[1]},trys:[],ops:[]};return e={next:t(0),throw:t(1),return:t(2)},"function"==typeof Symbol&&(e[Symbol.iterator]=function(){return this}),e;function t(t){return function(e){return function(t){if(n)throw new TypeError("Generator is already executing.");for(;o;)try{if(n=1,i&&(s=2&t[0]?i.return:t[0]?i.throw||((s=i.return)&&s.call(i),0):i.next)&&!(s=s.call(i,t[1])).done)return s;switch(i=0,s&&(t=[2&t[0],s.value]),t[0]){case 0:case 1:s=t;break;case 4:return o.label++,{value:t[1],done:!1};case 5:o.label++,i=t[1],t=[0];continue;case 7:t=o.ops.pop(),o.trys.pop();continue;default:if(!(s=0<(s=o.trys).length&&s[s.length-1])&&(6===t[0]||2===t[0])){o=0;continue}if(3===t[0]&&(!s||t[1]>s[0]&&t[1]<s[3])){o.label=t[1];break}if(6===t[0]&&o.label<s[1]){o.label=s[1],s=t;break}if(s&&o.label<s[2]){o.label=s[2],o.ops.push(t);break}s[2]&&o.ops.pop(),o.trys.pop();continue}t=r.call(a,o)}catch(e){t=[6,e],i=0}finally{n=s=0}if(5&t[0])throw t[1];return{value:t[0]?t[1]:void 0,done:!0}}([t,e])}}},__asyncValues=this&&this.__asyncValues||function(n){if(!Symbol.asyncIterator)throw new TypeError("Symbol.asyncIterator is not defined.");var e,t=n[Symbol.asyncIterator];return t?t.call(n):(n="function"==typeof __values?__values(n):n[Symbol.iterator](),e={},a("next"),a("throw"),a("return"),e[Symbol.asyncIterator]=function(){return this},e);function a(r){e[r]=n[r]&&function(a){return new Promise(function(e,t){(function(t,e,a,r){Promise.resolve(r).then(function(e){t({value:e,done:a})},e)})(e,t,(a=n[r](a)).done,a.value)})}}},_this=this;Object.defineProperty(exports,"__esModule",{value:!0});var elasticsearch_1=require("../boostrap/elasticsearch"),oai_pmh_1=require("oai-pmh"),constant_1=require("../constant");function sleep(a){return __awaiter(this,void 0,void 0,function(){var t;return __generator(this,function(e){for(t=(new Date).getTime();t+a>=(new Date).getTime(););return[2]})})}function TrimEnd(t,a){return __awaiter(this,void 0,void 0,function(){return __generator(this,function(e){for(;t.endsWith(a)&&""!==t&&""!==a;)t=t.slice(0,-1);return[2,t]})})}function TrimStart(t,a){return __awaiter(this,void 0,void 0,function(){return __generator(this,function(e){for(;t.startsWith(a)&&""!==t&&""!==a;)t=t.slice(1);return[2,t]})})}exports.reindex=function(_,f,m,e,h){return __awaiter(_this,void 0,void 0,function(){var t,a,r,n,i,s,o,u,c,d,l;return __generator(this,function(e){switch(e.label){case 0:t=""!==h&&void 0!==h?"&from="+new Date(parseInt(h)):"",a=new oai_pmh_1.OaiPmh("http://localhost:3000/oai/secure?verb=ListRecords&metadataPrefix="+_+"&set="+f+t),r=a.listRecords({metadataPrefix:_}),n=[],e.label=1;case 1:e.trys.push([1,6,7,12]),i=__asyncValues(r),e.label=2;case 2:return[4,i.next()];case 3:if((s=e.sent()).done)return[3,5];if(void 0!==(o=s.value)){for(u in n.push({index:{_index:_+"___"+f,_type:constant_1._TYPE,_id:o.metadata[f]._id}}),delete o.metadata[f].$,o.metadata[f].id=o.metadata[f]._id,delete o.metadata[f]._id,o.metadata[f])"string"==typeof o.metadata[f][u]?(o.metadata[f][u]=o.metadata[f][u].replace(/^<\!\[CDATA\[|\]\]>$/g,""),isNaN(o.metadata[f][u])?"true"!==o.metadata[f][u]&&"false"!==o.metadata[f][u]||(o.metadata[f][u]="true"===o.metadata[f][u]):o.metadata[f][u]=parseInt(o.metadata[f][u])):"object"==typeof o.metadata[f][u]&&(o.metadata[f][u]=o.metadata[f][u]);n.push(o.metadata[f])}e.label=4;case 4:return[3,2];case 5:return[3,12];case 6:return c=e.sent(),d={error:c},[3,12];case 7:return e.trys.push([7,,10,11]),s&&!s.done&&(l=i.return)?[4,l.call(i)]:[3,9];case 8:e.sent(),e.label=9;case 9:return[3,11];case 10:if(d)throw d.error;return[7];case 11:return[7];case 12:return 0<n.length&&elasticsearch_1.bulkDocument(m,n),[2]}})})},exports.reindexStartUp=function(i,s,o,u){return __awaiter(_this,void 0,void 0,function(){var t,a,r,n;return __generator(this,function(e){switch(e.label){case 0:return t=i,0<String(i).indexOf("?")&&(t=String(i).substring(0,String(i).indexOf("?"))),a=t.split("/"),a[0],r=a[1],n=a[2],a[3],[4,elasticsearch_1.reNewIndex(s,r+"___"+n)];case 1:return e.sent(),[4,exports.reindex(r,n,s,o,u)];case 2:return e.sent(),[2]}})})},exports.reindexStartUpGraphql=function(t,a,r,n,e){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){switch(e.label){case 0:return[4,elasticsearch_1.reNewIndex(r,t+"___"+a)];case 1:return e.sent(),[4,exports.reindex(t,a,r,null,n)];case 2:return e.sent(),[2]}})})},exports.reindexAPI=function(g,I,e){return __awaiter(_this,void 0,void 0,function(){var t,a,r,n,i,s,o,u,c,d,l,_,f,m,h,p,b,v,y,w,x;return __generator(this,function(e){switch(e.label){case 0:for(t=/[?&]([^=#]+)=([^&#]*)/g,a={},r={};r=t.exec("&"+String(g).substring(String(g).indexOf("?")+1));)a[r[1]]=decodeURIComponent(r[2]);return n=g,0<String(g).indexOf("?")&&(n=String(g).substring(0,String(g).indexOf("?"))),i=n.split("/"),i[0],s=i[1],o=i[2],i[3],a.type?(a.from="",a.until="",[4,elasticsearch_1.reNewIndex(I,s+"___"+o)]):[3,2];case 1:e.sent(),e.label=2;case 2:u=a.from,c=a.until,d=""!==u&&void 0!==u?"&from="+new Date(parseInt(u)):"",l=""!==c&&void 0!==c?"&until="+new Date(parseInt(c)):"",_=new oai_pmh_1.OaiPmh("http://localhost:3000/oai/secure?verb=ListRecords&metadataPrefix="+s+"&set="+o+d+l),f=_.listRecords({metadataPrefix:s}),m=[],e.label=3;case 3:e.trys.push([3,8,9,14]),h=__asyncValues(f),e.label=4;case 4:return[4,h.next()];case 5:if((p=e.sent()).done)return[3,7];if(void 0!==(b=p.value)){for(v in m.push({index:{_index:s+"___"+o,_type:constant_1._TYPE,_id:b.metadata[o]._id}}),delete b.metadata[o].$,b.metadata[o].id=b.metadata[o]._id,delete b.metadata[o]._id,b.metadata[o])"string"==typeof b.metadata[o][v]?(b.metadata[o][v]=b.metadata[o][v].replace(/^<\!\[CDATA\[|\]\]>$/g,""),isNaN(b.metadata[o][v])?"true"!==b.metadata[o][v]&&"false"!==b.metadata[o][v]||(b.metadata[o][v]="true"===b.metadata[o][v]):b.metadata[o][v]=parseInt(b.metadata[o][v])):"object"==typeof b.metadata[o][v]&&(b.metadata[o][v]=b.metadata[o][v]);m.push(b.metadata[o])}e.label=6;case 6:return[3,4];case 7:return[3,14];case 8:return y=e.sent(),w={error:y},[3,14];case 9:return e.trys.push([9,,12,13]),p&&!p.done&&(x=h.return)?[4,x.call(h)]:[3,11];case 10:e.sent(),e.label=11;case 11:return[3,13];case 12:if(w)throw w.error;return[7];case 13:return[7];case 14:return 0<m.length&&elasticsearch_1.bulkDocument(I,m),[2]}})})},exports.reindexAPIGraphql=function(v,y,w,x,g,I){return __awaiter(_this,void 0,void 0,function(){var t,a,r,n,i,s,o,u,c,d,l,_,f,m,h,p,b;return __generator(this,function(e){switch(e.label){case 0:return"true"!==I?[3,2]:(g=x="",[4,elasticsearch_1.reNewIndex(w,v+"___"+y)]);case 1:e.sent(),e.label=2;case 2:t=g,a=""!==x&&void 0!==x?"&from="+new Date(parseInt(x)):"",r=""!==t&&void 0!==t?"&until="+new Date(parseInt(t)):"",n=new oai_pmh_1.OaiPmh("http://localhost:3000/oai/secure?verb=ListRecords&metadataPrefix="+v+"&set="+y+a+r),i=n.listRecords({metadataPrefix:v}),s=[],e.label=3;case 3:e.trys.push([3,8,9,14]),o=__asyncValues(i),e.label=4;case 4:return[4,o.next()];case 5:if((u=e.sent()).done)return[3,7];if(void 0!==(c=u.value)){for(d in s.push({index:{_index:v+"___"+y,_type:constant_1._TYPE,_id:c.metadata[y]._id}}),delete c.metadata[y].$,c.metadata[y].id=c.metadata[y]._id,delete c.metadata[y]._id,c.metadata[y])if("string"==typeof c.metadata[y][d])c.metadata[y][d]=c.metadata[y][d].replace(/^<\!\[CDATA\[|\]\]>$/g,""),isNaN(c.metadata[y][d])?"true"!==c.metadata[y][d]&&"false"!==c.metadata[y][d]||(c.metadata[y][d]="true"===c.metadata[y][d]):c.metadata[y][d]=parseInt(c.metadata[y][d]);else if("object"==typeof c.metadata[y][d]){if(Array.isArray(c.metadata[y][d]))for(l=0,_=c.metadata[y][d];l<_.length;l++)for(m in(f=_[l])._source)("string"==typeof f._source[m]&&f._source[m].trim().startsWith("{")&&f._source[m].trim().endsWith("}")||f._source[m].trim().startsWith("[")&&f._source[m].trim().endsWith("]"))&&(f._source[m]=JSON.parse(f._source[m]));c.metadata[y][d]=c.metadata[y][d]}s.push(c.metadata[y])}e.label=6;case 6:return[3,4];case 7:return[3,14];case 8:return h=e.sent(),p={error:h},[3,14];case 9:return e.trys.push([9,,12,13]),u&&!u.done&&(b=o.return)?[4,b.call(o)]:[3,11];case 10:e.sent(),e.label=11;case 11:return[3,13];case 12:if(p)throw p.error;return[7];case 13:return[7];case 14:return 0<s.length&&elasticsearch_1.bulkDocument(w,s),[2]}})})};