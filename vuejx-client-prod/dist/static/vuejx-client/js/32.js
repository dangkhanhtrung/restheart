(window.webpackJsonp=window.webpackJsonp||[]).push([[32],{csgc:function(e,t,n){e.exports=function(e){function t(r){if(n[r])return n[r].exports;var o=n[r]={i:r,l:!1,exports:{}};return e[r].call(o.exports,o,o.exports,t),o.l=!0,o.exports}var n={};return t.m=e,t.c=n,t.d=function(e,n,r){t.o(e,n)||Object.defineProperty(e,n,{configurable:!1,enumerable:!0,get:r})},t.n=function(e){var n=e&&e.__esModule?function(){return e.default}:function(){return e};return t.d(n,"a",n),n},t.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},t.p="/",t(t.s=1)}([function(e,t,n){"use strict";var r=n(4);t.a={name:"vuejx-test",props:{logo_title:{type:String,default:"VueJX CMS"},height:{type:Number,default:42},color:{type:String,default:"transparent"},fixed:{type:Boolean,default:!0},dark:{type:Boolean,default:!1},classCss:{type:String,default:""},bgcolor:{type:String,default:""},colorStyle:{type:String,default:""},headerheight:{type:String,default:""}},setup:function(e,t){return Object(r.a)(e,t)}}},function(e,t,n){e.exports=n(2)},function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=n(0),o=n(8),a=n(3)(r.a,o.a,!1,null,null,null);t.default=a.exports},function(e,t){e.exports=function(e,t,n,r,o,a){var c,u=e=e||{},i=typeof e.default;"object"!==i&&"function"!==i||(c=e,u=e.default);var s,l="function"==typeof u?u.options:u;if(t&&(l.render=t.render,l.staticRenderFns=t.staticRenderFns,l._compiled=!0),n&&(l.functional=!0),o&&(l._scopeId=o),a?(s=function(e){(e=e||this.$vnode&&this.$vnode.ssrContext||this.parent&&this.parent.$vnode&&this.parent.$vnode.ssrContext)||"undefined"==typeof __VUE_SSR_CONTEXT__||(e=__VUE_SSR_CONTEXT__),r&&r.call(this,e),e&&e._registeredComponents&&e._registeredComponents.add(a)},l._ssrRegister=s):r&&(s=r),s){var d=l.functional,f=d?l.render:l.beforeCreate;d?(l._injectStyles=s,l.render=function(e,t){return s.call(t),f(e,t)}):l.beforeCreate=f?[].concat(f,s):[s]}return{esModule:c,exports:u,options:l}}},function(e,t,n){"use strict";var r=n(5),o=n.n(r),a=n(6),c=n.n(a),u=n(7);n.n(u),t.a=function(e,t){var n=this,r=localStorage.getItem("account"),a=Object(u.value)([]),i=Object(u.value)(!1),s=Object(u.value)(!1);Object(u.onMounted)(c()(o.a.mark(function e(){return o.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,l();case 2:case"end":return e.stop()}},e,n)}))),Object(u.onDestroyed)(function(){});var l=function(){var e=c()(o.a.mark(function e(){var r;return o.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return r={size:1e4,_source:{excludes:["pageHeaderConfig","pageFooterConfig","cssConfig"]},query:{bool:{filter:{match:{siteParent:void 0!==window.Vue.$router.history.current.params.site?window.Vue.$router.history.current.params.site:"guest"}},must:[{match:{hidden:!1}},{match:{visibility:void 0!==window.Vue.$router.history.current.meta.visibility?window.Vue.$router.history.current.meta.visibility:"web"}}]}}},e.next=3,t.parent.$store.dispatch("vuejx_manager/pullDBES",{query:"\n                query search($token: String, $body: JSON, $db: String, $collection: String) {\n                    results: search(token: $token, body: $body, db: $db, collection: $collection )\n                }\n            ",variables:{body:r,db:"vuejx_cfg",collection:"vuejx_page"}},{headers:{Authorization:"Bearer "+localStorage.getItem("access_token"),token:localStorage.getItem("token")}}).then(function(e){null!==e.results&&e.results.hits.hits.length>0?a.value=e.results.hits.hits:a.value=[]}).catch(function(e){a.value=[]});case 3:case"end":return e.stop()}},e,n)}));return function(){return e.apply(this,arguments)}}();return{account:r,pages:a,isScrolling:i,onScroll:function(){window.pageYOffset>e.headerheight?i.value=!0:i.value=!1,i.value&&""!==e.bgcolor&&""===e.colorStyle?e.colorStyle="background: "+e.bgcolor+" ; border-color: "+e.bgcolor+";":0===document.documentElement.scrollTop&&(i.value=!1,""!==e.bgcolorstate?e.colorStyle="background: "+e.bgcolorstate+"; border-color: "+e.bgcolorstate+";":e.colorStyle="")},menu:s,logOut:function(){localStorage.setItem("isAuthenticated",!1),window.location.reload()}}}},function(e,t){e.exports=n("14Xm")},function(e,t){e.exports=n("D3Ub")},function(e,t){e.exports=n("hTPA")},function(e,t,n){"use strict";var r={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"vuejx__header"},[n("v-btn",{attrs:{color:"success"}},[e._v("test")])],1)},staticRenderFns:[]};t.a=r}])}}]);