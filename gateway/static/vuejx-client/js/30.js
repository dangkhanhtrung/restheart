(window.webpackJsonp=window.webpackJsonp||[]).push([[30],{GfPz:function(e,t,r){"use strict";r.r(t);var n=r("hTPA"),a=r("8SHQ");function o(e,t,r,n,a,o,u){try{var s=e[o](u),i=s.value}catch(e){return void r(e)}s.done?t(i):Promise.resolve(i).then(n,a)}function u(e){return function(){var t=this,r=arguments;return new Promise(function(n,a){var u=e.apply(t,r);function s(e){o(u,n,a,s,i,"next",e)}function i(e){o(u,n,a,s,i,"throw",e)}s(void 0)})}}var s=function(e,t){var r=Object(n.value)(!1),o=Object(n.value)(a.a.vuejx),s=Object(n.value)(null),i=Object(n.value)(null),l=Object(n.value)(""),c=Object(n.value)(""),v=Object(n.value)(!1),g=Object(n.value)(!1),d=Object(n.value)({}),p=Object(n.value)("home"),f=Object(n.value)(!1),h=Object(n.value)(-1);Object(n.onMounted)(u(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,m();case 2:return e.next=4,b();case 4:case"end":return e.stop()}},e)}))),Object(n.onDestroyed)(function(){m()});var m=function(){var e=u(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:r.value=!1,v.value=!1,o.value="",l.value="",g.value=!1,d.value={},f.value=!1,s.value=null,i.value=null;case 9:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}();Object(n.watch)(function(){return e.page},function(){var e=u(regeneratorRuntime.mark(function e(t){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(!0!==f.value){e.next=4;break}return e.next=3,b();case 3:h.value>=0&&(v.value=!1,setTimeout(function(){v.value=!0},0));case 4:case"end":return e.stop()}},e)}));return function(t){return e.apply(this,arguments)}}(),{lazy:!1,deep:!0,flush:"sync"});var b=function(){var r=u(regeneratorRuntime.mark(function r(){var n,a,o,u;return regeneratorRuntime.wrap(function(r){for(;;)switch(r.prev=r.next){case 0:if(f.value=!1,g.value=!1,null!==t.parent.$store.state.vuejx_manager.vuejx_header){r.next=9;break}return v.value=!1,n={size:1e4,sort:[{order:"asc"}],_source:{includes:["shortName","title","hiddenNav"]},query:{bool:{filter:{match:{siteParent:void 0!==window.Vue.$router.history.current.params.site?window.Vue.$router.history.current.params.site:"guest"}},must:[{match:{hidden:!1}},{match:{visibility:void 0!==window.Vue.$router.history.current.meta.visibility?window.Vue.$router.history.current.meta.visibility:"web"}}]}}},a={size:1,_source:{includes:["guestHeaderConfig","guestFooterConfig","cssConfig"]},query:{bool:{filter:{match:{site:"guest"}},must:[{match:{shortName:e.site}}]}}},r.next=8,t.parent.$store.dispatch("vuejx_manager/graphqlQuery",{query:'\n                  query search($token: String, $body: JSON, $db: String, $collection: String, $bodySite: JSON) {\n                      results: search(token: $token, body: $body, db: $db, collection: $collection ),\n                      resultsSite: search(token: $token, body: $bodySite, db: "vuejx_cfg", collection: "vuejx_site" )\n                  }\n              ',variables:{body:n,db:"vuejx_cfg",collection:"vuejx_page",bodySite:a}}).then(function(e){if(null!==e.results&&null!==e.resultsSite){var r={},n=!0,a=!1,o=void 0;try{for(var u,c=e.results.hits.hits[Symbol.iterator]();!(n=(u=c.next()).done);n=!0){var v=u.value;r[v._source.shortName]=v._source.hiddenNav}}catch(e){a=!0,o=e}finally{try{n||null==c.return||c.return()}finally{if(a)throw o}}t.parent.$store.state.vuejx_manager.hiddenObj=r,t.parent.$store.state.vuejx_manager.pages=e.results.hits.hits;var g=e.resultsSite.hits.hits[0]._source;t.parent.$store.state.vuejx_manager.vuejx_header=g.guestHeaderConfig,t.parent.$store.state.vuejx_manager.vuejx_footer=g.guestFooterConfig,s.value=g.guestHeaderConfig,i.value=g.guestFooterConfig,l.value=g.cssConfig}else t.parent.$store.state.vuejx_manager.vuejx_header=null,t.parent.$store.state.vuejx_manager.vuejx_footer=null,t.parent.$store.state.vuejx_manager.hiddenObj={},t.parent.$store.state.vuejx_manager.pages=[],l.value=""}).catch(function(e){t.parent.$store.state.vuejx_manager.vuejx_header=null,t.parent.$store.state.vuejx_manager.vuejx_footer=null,t.parent.$store.state.vuejx_manager.hiddenObj={},t.parent.$store.state.vuejx_manager.pages=[],l.value=""});case 8:v.value=!0;case 9:if(void 0===e.page||null===e.page){r.next=17;break}return(o={size:1,sort:[{order:"asc"}],_source:{includes:["shortName","pageHeaderConfig","pageFooterConfig","cssConfig","pageConfig"]},query:{bool:{filter:{match:{siteParent:e.site}},must:[{match:{visibility:void 0!==window.Vue.$router.history.current.meta.visibility?window.Vue.$router.history.current.meta.visibility:"web"}}]}}}).query.bool.must.push({match:{shortName:e.page}}),t.parent.$store.state.vuejx_manager.vuejx_form=null,r.next=15,t.parent.$store.dispatch("vuejx_manager/graphqlQuery",{query:"\n                query search($token: String, $body: JSON, $db: String, $collection: String) {\n                    results: search(token: $token, body: $body, db: $db, collection: $collection )\n                }\n            ",variables:{body:o,db:"vuejx_cfg",collection:"vuejx_page"}}).then(function(e){if(null!==e.results&&e.results.hits.hits.length>0){var r=e.results.hits.hits[0]._source;void 0!==r.pageHeaderConfig&&null!==r.pageHeaderConfig&&""!==r.pageHeaderConfig?(t.parent.$store.state.vuejx_manager.vuejx_header=r.pageHeaderConfig,h.value=2):t.parent.$store.state.vuejx_manager.vuejx_header=s.value,void 0!==r.pageFooterConfig&&null!==r.pageFooterConfig&&""!==r.pageFooterConfig?(t.parent.$store.state.vuejx_manager.vuejx_footer=r.pageFooterConfig,h.value=2):t.parent.$store.state.vuejx_manager.vuejx_footer=i.value,h.value>0?h.value=h.value-1:h.value=-1,c.value=r.cssConfig,t.parent.$store.state.vuejx_manager.vuejx_form=r.pageConfig,t.parent.$store.state.vuejx_manager.css_config=r.cssConfig}}).catch(function(e){t.parent.$store.state.vuejx_manager.vuejx_form=null});case 15:r.next=20;break;case 17:return u={size:1,sort:[{order:"asc"}],_source:{includes:["shortName"]},query:{bool:{filter:{match:{siteParent:e.site}},must:[{match:{visibility:void 0!==window.Vue.$router.history.current.meta.visibility?window.Vue.$router.history.current.meta.visibility:"web"}}]}}},r.next=20,t.parent.$store.dispatch("vuejx_manager/graphqlQuery",{query:"\n                query search($token: String, $body: JSON, $db: String, $collection: String) {\n                    results: search(token: $token, body: $body, db: $db, collection: $collection )\n                }\n            ",variables:{body:u,db:"vuejx_cfg",collection:"vuejx_page"}}).then(function(t){if(null!==t.results&&t.results.hits.hits.length>0){var r=t.results.hits.hits[0]._source;window.Vue.redirect([],!1,"/"+window.Vue.$router.history.current.meta.visibility+"/"+e.site+"/"+r.shortName,!0)}}).catch(function(e){});case 20:f.value=!0,g.value=!0,setTimeout(function(){v.value=!0},0);case 23:case"end":return r.stop()}},r)}));return function(){return r.apply(this,arguments)}}();return{loading:r,api:o,csstext:l,csstextExt:c,render:v,renderPage:g,pageCustom:d,pageDefault:p,counterAttack:h}},i={props:{site:{type:String,default:"guest"},page:{type:String,default:null}},components:{VuejxComponentHeader:function(){return r.e(5).then(r.bind(null,"7QBU"))},VuejxComponentFooter:function(){return r.e(4).then(r.bind(null,"2ypY"))}},setup:function(e,t){return s(e,t)}},l=r("KHd+"),c=Object(l.a)(i,function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",[e.render?r("vuejx-component-header",{attrs:{page:e.page,site:e.site,csstext:e.csstext,csstextExt:e.csstextExt}}):e._e(),e._v(" "),r("div",{staticStyle:{"min-height":"80vh !important"}},[e.renderPage?r("vuejx-content",{attrs:{site:e.site,page:e.page,pageDefault:e.pageDefault}}):e._e()],1),e._v(" "),e.render?r("vuejx-component-footer",{attrs:{page:e.page,site:e.site}}):e._e(),e._v(" "),r("back-to-top",{attrs:{bottom:"50px",right:"50px"}},[r("v-btn",{attrs:{text:"",icon:"",color:"primary"}},[r("v-icon",{attrs:{size:"56"}},[e._v("mdi-arrow-up-bold-box")])],1)],1)],1)},[],!1,null,null,null);t.default=c.exports}}]);