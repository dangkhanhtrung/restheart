"use strict";var __awaiter=this&&this.__awaiter||function(i,u,s,a){return new(s=s||Promise)(function(e,t){function r(e){try{o(a.next(e))}catch(e){t(e)}}function n(e){try{o(a.throw(e))}catch(e){t(e)}}function o(t){t.done?e(t.value):new s(function(e){e(t.value)}).then(r,n)}o((a=a.apply(i,u||[])).next())})},__generator=this&&this.__generator||function(r,n){var o,i,u,e,s={label:0,sent:function(){if(1&u[0])throw u[1];return u[1]},trys:[],ops:[]};return e={next:t(0),throw:t(1),return:t(2)},"function"==typeof Symbol&&(e[Symbol.iterator]=function(){return this}),e;function t(t){return function(e){return function(t){if(o)throw new TypeError("Generator is already executing.");for(;s;)try{if(o=1,i&&(u=2&t[0]?i.return:t[0]?i.throw||((u=i.return)&&u.call(i),0):i.next)&&!(u=u.call(i,t[1])).done)return u;switch(i=0,u&&(t=[2&t[0],u.value]),t[0]){case 0:case 1:u=t;break;case 4:return s.label++,{value:t[1],done:!1};case 5:s.label++,i=t[1],t=[0];continue;case 7:t=s.ops.pop(),s.trys.pop();continue;default:if(!(u=0<(u=s.trys).length&&u[u.length-1])&&(6===t[0]||2===t[0])){s=0;continue}if(3===t[0]&&(!u||t[1]>u[0]&&t[1]<u[3])){s.label=t[1];break}if(6===t[0]&&s.label<u[1]){s.label=u[1],u=t;break}if(u&&s.label<u[2]){s.label=u[2],s.ops.push(t);break}u[2]&&s.ops.pop(),s.trys.pop();continue}t=n.call(r,s)}catch(e){t=[6,e],i=0}finally{o=u=0}if(5&t[0])throw t[1];return{value:t[0]?t[1]:void 0,done:!0}}([t,e])}}},_this=this;Object.defineProperty(exports,"__esModule",{value:!0}),exports.found=function(t,r){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return null!=r&&0<r.length?(!1,[2,r.some(function(e){if(-1!==t.indexOf(e))return!0})]):[2,!0]})})},exports.foundPermission=function(r,n){return __awaiter(_this,void 0,void 0,function(){var t;return __generator(this,function(e){return t=[],null!=n&&0<n.length&&n.some(function(e){-1!==r.indexOf(e.shortName)&&t.push(parseInt(e.permission))}),[2,t]})})},exports.foundScope=function(t,r){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return null==t||""===t?[2,!1]:0===r.length?[2,!0]:(!1,[2,r.some(function(e){if(-1!==t.indexOf(e.shortName))return!0})])})})},exports.verifyBody=function(i){return __awaiter(_this,void 0,void 0,function(){var t,r,n,o;return __generator(this,function(e){for(t in i)if("object"==typeof i[t])if(Array.isArray(i[t]))for(r in i[t])void 0!==i[t][r]&&null!==i[t][r]&&i[t][r].hasOwnProperty("_source")&&(i[t][r]={_source:{shortName:i[t][r]._source.shortName,title:i[t][r]._source.title,type:i[t][r]._source.type}});else if(void 0!==i[t]&&null!==i[t]&&i[t].hasOwnProperty("_source"))i[t]={_source:{shortName:i[t]._source.shortName,title:i[t]._source.title,type:i[t]._source.type}};else if(void 0!==i[t]&&null!==i[t]&&!i[t].hasOwnProperty("_source"))for(n in i[t])if("object"==typeof i[t][n])if(Array.isArray(i[t][n]))for(o in i[t][n])void 0!==i[t][n][o]&&i[t][n][o].hasOwnProperty("_source")&&(i[t][n][o]={_source:{shortName:i[t][n][o]._source.shortName,title:i[t][n][o]._source.title,type:i[t][n][o]._source.type}});else void 0!==i[t][n]&&null!==i[t][n]&&i[t][n].hasOwnProperty("_source")&&(i[t][n]={_source:{shortName:i[t][n]._source.shortName,title:i[t][n]._source.title,type:i[t][n]._source.type}});return[2]})})};