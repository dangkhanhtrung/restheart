(window.webpackJsonp=window.webpackJsonp||[]).push([[6],{sADc:function(e,t,n){"use strict";n.r(t);var r=n("hTPA");n("8SHQ");function u(e,t,n,r,u,a,i){try{var o=e[a](i),c=o.value}catch(e){return void n(e)}o.done?t(c):Promise.resolve(c).then(r,u)}function a(e){return function(){var t=this,n=arguments;return new Promise(function(r,a){var i=e.apply(t,n);function o(e){u(i,r,a,o,c,"next",e)}function c(e){u(i,r,a,o,c,"throw",e)}o(void 0)})}}var i=function(e,t){var n=Object(r.value)(!1),u=Object(r.value)(null),i=Object(r.value)(!1),o=Object(r.value)(!1);Object(r.onMounted)(a(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return c(),e.next=3,s();case 3:o.value=!0;case 4:case"end":return e.stop()}},e)}))),Object(r.onDestroyed)(function(){c()}),Object(r.watch)(function(){return e.page},function(){var e=a(regeneratorRuntime.mark(function e(t){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(!o.value){e.next=5;break}return c(),e.next=4,s();case 4:o.value=!0;case 5:case"end":return e.stop()}},e)}));return function(t){return e.apply(this,arguments)}}(),{lazy:!1,deep:!0,flush:"sync"});var c=function(){n.value=!1,i.value=!1,u.value=null},s=function(){var n=a(regeneratorRuntime.mark(function n(){var r;return regeneratorRuntime.wrap(function(n){for(;;)switch(n.prev=n.next){case 0:return i.value=!1,r={size:1,query:{bool:{filter:{match:{site:"guest"}},must:[{match:{shortName:e.page}},{match:{siteParent:e.site}},{match:{visibility:void 0!==window.Vue.$router.history.current.meta.visibility?window.Vue.$router.history.current.meta.visibility:"web"}}]}}},n.next=4,t.parent.$store.dispatch("vuejx_manager/graphqlQuery",{query:"\n                query search($token: String, $body: JSON, $db: String, $collection: String) {\n                    results: search(token: $token, body: $body, db: $db, collection: $collection )\n                }\n            ",variables:{body:r,db:"vuejx_cfg",collection:"vuejx_page"}}).then(function(e){if(null!==e.results&&e.results.hits.hits.length>0){var t=e.results.hits.hits[0]._source;u.value=t.pageConfig}else u.value=null;i.value=!0}).catch(function(e){u.value=null,i.value=!0});case 4:case"end":return n.stop()}},n)}));return function(){return n.apply(this,arguments)}}();return{loading:n,vuejx:u,render:i}},o={components:{VuejxComponent:function(){return n.e(15).then(n.bind(null,"LLfy"))}},props:{page:{type:String,default:"home"},site:{type:String,default:"guest"}},setup:function(e,t){return i(e,t)}},c=n("KHd+"),s=Object(c.a)(o,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[null!==e.vuejx&&void 0!==e.vuejx&&e.render?n("vuejx-component",{attrs:{vuejx_form:e.vuejx,page:e.page,site:e.site}}):n("div",{staticClass:"hd__screen py-5"},[e.render?n("v-alert",{staticClass:"pa-4",class:{hidden:null!==e.vuejx},attrs:{text:"",outlined:"",color:"deep-orange",icon:"mdi-fire"}},[e._v("\n      Page not found!.\n    ")]):e._e()],1)],1)},[],!1,null,null,null);t.default=s.exports}}]);