(window.webpackJsonp=window.webpackJsonp||[]).push([[31],{Hyrb:function(e,t,n){"use strict";n.r(t);var r=n("hTPA");n("8SHQ");function o(e,t,n,r,o,c,i){try{var a=e[c](i),s=a.value}catch(e){return void n(e)}a.done?t(s):Promise.resolve(s).then(r,o)}function c(e){return function(){var t=this,n=arguments;return new Promise(function(r,c){var i=e.apply(t,n);function a(e){o(i,r,c,a,s,"next",e)}function s(e){o(i,r,c,a,s,"throw",e)}a(void 0)})}}var i=function(e,t){var n=Object(r.value)(!1),o=Object(r.value)([]);Object(r.onMounted)(c(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return i(),e.next=3,a();case 3:case"end":return e.stop()}},e)}))),Object(r.onDestroyed)(function(){i()});var i=function(){n.value=!1,o.value=[]},a=function(){var r=c(regeneratorRuntime.mark(function r(){var c;return regeneratorRuntime.wrap(function(r){for(;;)switch(r.prev=r.next){case 0:return n.value=!1,"query search($token: String, $body: JSON, $db: String, $collection: String) {\n            results: search(token: $token, body: $body, db: $db, collection: $collection )\n        }",c={size:1e3,sort:[{shortName:"desc"}],query:{bool:{filter:{match:{storeCollection:e.storeCollection}}}}},r.next=5,t.parent.$store.dispatch("vuejx_manager/graphqlQuery",{query:"query search($token: String, $body: JSON, $db: String, $collection: String) {\n            results: search(token: $token, body: $body, db: $db, collection: $collection )\n        }",variables:{body:c,db:"vuejx_cfg",collection:"oai_pmh_async"}}).then(function(e){null!==e.results?o.value=e.results.hits.hits:o.value=[]}).catch(function(e){o.value=[]});case 5:n.value=!0;case 6:case"end":return r.stop()}},r)}));return function(){return r.apply(this,arguments)}}();return{items:o,rendered:n}},a={name:"vuejx-logs",props:{storeCollection:{type:String,default:""},db:{type:String,default:""},collection:{type:String,default:""}},setup:function(e,t){return i(e,t)}},s=n("KHd+"),u=Object(s.a)(a,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"employee__table",staticStyle:{"min-height":"80vh"}},[n("div",{staticClass:"chart__table__preview"},[n("table",[e._m(0),e._v(" "),e.items.length>0?n("tbody",e._l(e.items,function(t,r){return n("tr",{key:r},[n("td",{attrs:{align:"center"}},[e._v("\n            "+e._s(t._source.storeCode)+"\n          ")]),e._v(" "),n("td",{attrs:{align:"center"}},[e._v("\n            "+e._s(""!==t._source.shortName&&void 0!==t._source.shortName?new Date(t._source.shortName).toLocaleString():"")+"\n          ")]),e._v(" "),n("td",{attrs:{align:"center"}},[e._v("\n            "+e._s(t._source.inComingRecord)+"\n          ")]),e._v(" "),n("td",{attrs:{align:"center"}},[e._v("\n            "+e._s(t._source.inComingRecordState)+"\n          ")]),e._v(" "),n("td",{attrs:{align:"center"}},[e._v("\n            "+e._s(t._source.inComingRecordRemove)+"\n          ")]),e._v(" "),n("td",{attrs:{align:"center"}},[e._v("\n            "+e._s(t._source.inComingRecordRemoveState)+"\n          ")])])}),0):e._e()])])])},[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("thead",[n("th",[e._v("Mã")]),e._v(" "),n("th",[e._v("Ngày đồng bộ")]),e._v(" "),n("th",[e._v("Số bản ghi nhận")]),e._v(" "),n("th",[e._v("Cập nhật thành công")]),e._v(" "),n("th",[e._v("Số bản ghi xoá")]),e._v(" "),n("th",[e._v("Xoá thành công")])])}],!1,null,null,null);t.default=u.exports}}]);