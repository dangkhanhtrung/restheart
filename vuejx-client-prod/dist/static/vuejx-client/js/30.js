(window.webpackJsonp=window.webpackJsonp||[]).push([[30],{GfPz:function(e,t,n){"use strict";n.r(t);var r=n("hTPA"),o=n("8SHQ");function u(e,t,n,r,o,u,a){try{var i=e[u](a),s=i.value}catch(e){return void n(e)}i.done?t(s):Promise.resolve(s).then(r,o)}function a(e){return function(){var t=this,n=arguments;return new Promise(function(r,o){var a=e.apply(t,n);function i(e){u(a,r,o,i,s,"next",e)}function s(e){u(a,r,o,i,s,"throw",e)}i(void 0)})}}var i=function(e,t){var n=Object(r.value)(!1),u=Object(r.value)(o.a.vuejx),i=Object(r.value)(null),s=Object(r.value)(null),l=Object(r.value)(""),c=Object(r.value)(!1),v=Object(r.value)(!1),g=Object(r.value)({});Object(r.onMounted)(a(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return d(),e.next=3,f();case 3:v.value=!0;case 4:case"end":return e.stop()}},e)}))),Object(r.onDestroyed)(function(){d()});var d=function(){n.value=!1,c.value=!1,u.value="",i.value=null,s.value=null,l.value="",v.value=!1,g.value={}},f=function(){var n=a(regeneratorRuntime.mark(function n(){var r;return regeneratorRuntime.wrap(function(n){for(;;)switch(n.prev=n.next){case 0:return c.value=!1,r={size:1,_source:{includes:["guestHeaderConfig","guestFooterConfig","cssConfig"]},query:{bool:{filter:{match:{site:"guest"}},must:[{match:{shortName:e.site}}]}}},n.next=4,t.parent.$store.dispatch("vuejx_manager/graphqlQuery",{query:"\n                query search($token: String, $body: JSON, $db: String, $collection: String) {\n                    results: search(token: $token, body: $body, db: $db, collection: $collection )\n                }\n            ",variables:{body:r,db:"vuejx_cfg",collection:"vuejx_site"}}).then(function(e){if(null!==e.results&&e.results.hits.hits.length>0){var t=e.results.hits.hits[0]._source;i.value=t.guestHeaderConfig,s.value=t.guestFooterConfig,l.value=t.cssConfig}else i.value=null,s.value=null,l.value="";c.value=!0}).catch(function(e){i.value=null,s.value=null,l.value="",c.value=!0});case 4:return r={size:1,_source:{includes:["pageHeaderConfig","pageFooterConfig","cssConfig"]},query:{bool:{filter:{match:{siteParent:e.site}},must:[{match:{shortName:e.page}},{match:{visibility:void 0!==window.Vue.$router.history.current.meta.visibility?window.Vue.$router.history.current.meta.visibility:"web"}}]}}},n.next=7,t.parent.$store.dispatch("vuejx_manager/graphqlQuery",{query:"\n                query search($token: String, $body: JSON, $db: String, $collection: String) {\n                    results: search(token: $token, body: $body, db: $db, collection: $collection )\n                }\n            ",variables:{body:r,db:"vuejx_cfg",collection:"vuejx_page"}}).then(function(e){if(null!==e.results&&e.results.hits.hits.length>0){var t=e.results.hits.hits[0]._source;void 0!==t.pageHeaderConfig&&null!==t.pageHeaderConfig&&""!==t.pageHeaderConfig&&(i.value=t.pageHeaderConfig),void 0!==t.pageFooterConfig&&null!==t.pageFooterConfig&&""!==t.pageFooterConfig&&(s.value=t.pageFooterConfig),l.value+=t.cssConfig}v.value=!0}).catch(function(e){v.value=!0});case 7:case"end":return n.stop()}},n)}));return function(){return n.apply(this,arguments)}}();return{loading:n,api:u,vuejx_header:i,vuejx_footer:s,csstext:l,render:c,renderPage:v,pageCustom:g}},s={props:{site:{type:String,default:"guest"},page:{type:String,default:"home"}},components:{VuejxComponentHeader:function(){return n.e(5).then(n.bind(null,"7QBU"))},VuejxComponentFooter:function(){return n.e(4).then(n.bind(null,"2ypY"))}},setup:function(e,t){return i(e,t)}},l=n("KHd+"),c=Object(l.a)(s,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[e.render?n("vuejx-component-header",{attrs:{vuejx_form:e.vuejx_header,page:e.page,site:e.site,csstext:e.csstext}}):e._e(),e._v(" "),n("div",{staticStyle:{"min-height":"80vh !important"}},[e.renderPage?n("vuejx-content",{attrs:{site:e.site,page:e.page}}):e._e()],1),e._v(" "),e.render?n("vuejx-component-footer",{attrs:{vuejx_form:e.vuejx_footer,page:e.page,site:e.site}}):e._e(),e._v(" "),n("back-to-top",{attrs:{bottom:"50px",right:"50px"}},[n("v-btn",{attrs:{text:"",icon:"",color:"primary"}},[n("v-icon",{attrs:{size:"56"}},[e._v("mdi-arrow-up-bold-box")])],1)],1)],1)},[],!1,null,null,null);t.default=c.exports}}]);