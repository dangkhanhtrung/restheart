(window.webpackJsonp=window.webpackJsonp||[]).push([[17],{"3dNW":function(e,t,n){"use strict";n.r(t);var a=n("Xp2Q"),r={props:{database:{type:String,default:"vuejx_cfg"},collection:{type:String,default:""},id:{type:String,default:""},site:{type:String,default:"guest"}},setup:function(e,t){var n=Object(a.a)(e,t);return{from:n.from,until:n.until,type:n.type,reindex:n.reindex,reindexAll:n.reindexAll}}},o=n("KHd+"),i=Object(o.a)(r,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"default__screen"},[n("v-banner",{staticClass:"px-0",attrs:{"single-line":"",sticky:!0}},[n("v-btn",{staticStyle:{float:"left"},attrs:{text:"",icon:""},on:{click:function(t){return e.$router.go(-1)}}},[n("v-icon",[e._v("mdi-backburger")])],1),e._v(" "),n("v-toolbar-title",{staticStyle:{"max-width":"800px",float:"left"}},[e._v("Extension")])],1),e._v(" "),n("v-sheet",{staticClass:"mx-auto"},[n("v-tabs",{attrs:{vertical:"","background-color":"grey lighten-5"}},[n("v-tab",[n("v-icon",{attrs:{left:""}},[e._v("mdi-database-settings")]),e._v("ReIndex\n      ")],1),e._v(" "),n("v-tab",[n("v-icon",{attrs:{left:""}},[e._v("mdi-database-import")]),e._v("Import\n      ")],1),e._v(" "),n("v-tab-item",{attrs:{transition:"fade-transition","reverse-transition":"fade-transition"}},[n("v-card",{attrs:{flat:""}},[n("v-card-text",[n("v-layout",{attrs:{row:"",wrap:""}},[n("v-flex",{attrs:{xs12:"",sm3:""}},[n("v-subheader",{staticClass:"vjx_label"},[e._v("\n                  Start Date:\n                ")]),e._v(" "),n("v-menu",{ref:"menu",attrs:{"close-on-content-click":!1,"return-value":e.from,transition:"scale-transition","offset-y":"","full-width":"","min-width":"290px"},on:{"update:returnValue":function(t){e.from=t},"update:return-value":function(t){e.from=t}},scopedSlots:e._u([{key:"activator",fn:function(t){var a=t.on;return[n("v-text-field",e._g({staticClass:"v__border pt-0 mt-0",attrs:{"append-icon":"mdi-calendar-check",readonly:"",text:"",solo:""},model:{value:e.from,callback:function(t){e.from=t},expression:"from"}},a))]}}]),model:{value:e.menu,callback:function(t){e.menu=t},expression:"menu"}},[e._v(" "),n("v-date-picker",{attrs:{"no-title":"",scrollable:""},model:{value:e.from,callback:function(t){e.from=t},expression:"from"}},[n("div",{staticClass:"flex-grow-1"}),e._v(" "),n("v-btn",{attrs:{text:"",color:"primary"},on:{click:function(t){e.menu=!1}}},[e._v("Cancel")]),e._v(" "),n("v-btn",{attrs:{text:"",color:"primary"},on:{click:function(t){return e.$refs.menu.save(e.from)}}},[e._v("OK")])],1)],1)],1),e._v(" "),n("v-flex",{attrs:{xs12:"",sm3:""}},[n("v-subheader",{staticClass:"vjx_label"},[e._v("\n                  End Date:\n                ")]),e._v(" "),n("v-menu",{ref:"menu2",attrs:{"close-on-content-click":!1,"return-value":e.until,transition:"scale-transition","offset-y":"","full-width":"","min-width":"290px"},on:{"update:returnValue":function(t){e.until=t},"update:return-value":function(t){e.until=t}},scopedSlots:e._u([{key:"activator",fn:function(t){var a=t.on;return[n("v-text-field",e._g({staticClass:"v__border pt-0 mt-0",attrs:{"append-icon":"mdi-calendar-check",readonly:"",text:"",solo:""},model:{value:e.until,callback:function(t){e.until=t},expression:"until"}},a))]}}]),model:{value:e.menu2,callback:function(t){e.menu2=t},expression:"menu2"}},[e._v(" "),n("v-date-picker",{attrs:{"no-title":"",scrollable:""},model:{value:e.until,callback:function(t){e.until=t},expression:"until"}},[n("div",{staticClass:"flex-grow-1"}),e._v(" "),n("v-btn",{attrs:{text:"",color:"primary"},on:{click:function(t){e.menu2=!1}}},[e._v("Cancel")]),e._v(" "),n("v-btn",{attrs:{text:"",color:"primary"},on:{click:function(t){return e.$refs.menu2.save(e.until)}}},[e._v("OK")])],1)],1)],1),e._v(" "),n("v-flex",{attrs:{xs12:"",sm3:""}},[n("v-subheader",{staticClass:"vjx_label"},[e._v("\n                  Type:\n                ")]),e._v(" "),n("v-autocomplete",{staticClass:"v__border",attrs:{items:[{value:!1,text:"Default"},{value:!0,text:"Clean"}],text:"",solo:"","hide-no-data":"","hide-details":"",multiple:!1,"return-object":!1,"small-chips":""},model:{value:e.type,callback:function(t){e.type=t},expression:"type"}})],1),e._v(" "),n("v-flex",{attrs:{xs12:"",sm3:""}},[n("v-subheader",{staticClass:"vjx_label"},[e._v("\n                  Action:\n                ")]),e._v(" "),n("v-layout",{attrs:{row:"",wrap:""}},[n("v-flex",[n("v-btn",{attrs:{outlined:"",dark:"",color:"primary",block:""},on:{click:e.reindex}},[e._v("Reindex")])],1),e._v(" "),n("v-flex",[n("v-btn",{attrs:{outlined:"",dark:"",color:"primary",block:""},on:{click:e.reindexAll}},[e._v("ReindexAll")])],1)],1)],1)],1)],1)],1)],1),e._v(" "),n("v-tab-item",{attrs:{transition:"fade-transition","reverse-transition":"fade-transition"}},[n("v-card",{attrs:{flat:""}},[n("v-card-text")],1)],1)],1)],1)],1)},[],!1,null,null,null);t.default=i.exports},Xp2Q:function(module,__webpack_exports__,__webpack_require__){"use strict";var vue_function_api__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("hTPA"),_config__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("8SHQ");function asyncGeneratorStep(e,t,n,a,r,o,i){try{var l=e[o](i),c=l.value}catch(e){return void n(e)}l.done?t(c):Promise.resolve(c).then(a,r)}function _asyncToGenerator(e){return function(){var t=this,n=arguments;return new Promise(function(a,r){var o=e.apply(t,n);function i(e){asyncGeneratorStep(o,a,r,i,l,"next",e)}function l(e){asyncGeneratorStep(o,a,r,i,l,"throw",e)}i(void 0)})}}function service(props,ctx){var loading=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(!1),render=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(!1),api=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(_config__WEBPACK_IMPORTED_MODULE_1__.a.vuejx),apiReindexAll=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(_config__WEBPACK_IMPORTED_MODULE_1__.a.reindexAll),postData=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)({}),metadataConfig=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)({}),valid=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(!1),from=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(null),until=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(null),type=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(!1),sites=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)([]),siteSelected=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(props.site);Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.onDestroyed)(function(){clean()});var clean=function(){loading.value=!1,render.value=!1,api.value="",postData.value=null,metadataConfig.value=null,valid.value=null},pullConfig=function(){var _ref=_asyncToGenerator(regeneratorRuntime.mark(function _callee(){return regeneratorRuntime.wrap(function _callee$(_context){for(;;)switch(_context.prev=_context.next){case 0:if(""===props.collection){_context.next=3;break}return _context.next=3,ctx.parent.$store.dispatch("vuejx_manager/userOne",{db:"vuejx_cfg",collection:"vuejx_collection",filter:{shortName:props.collection},keys:{metadataConfig:1}}).then(function(data){metadataConfig.value=null!=data?eval("("+data.metadataConfig+")").form:{}}).catch(function(e){metadataConfig.value={}});case 3:case"end":return _context.stop()}},_callee)}));return function(){return _ref.apply(this,arguments)}}(),pullDetail=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,pullConfig();case 2:if("_schemas"!==props.collection){e.next=7;break}return e.next=5,ctx.parent.$store.dispatch("vuejx_manager/pullDB",{db:props.database,collection:props.collection,params:{pagesize:1e3}}).then(function(e){postData.value=e._embedded.find(function(e){return e._id===props.id});var t={};for(var n in postData.value)"_id"!==n&&"id"!==n&&"$schema"!==n&&"_etag"!==n&&(t[n]=postData.value[n]);postData.value.shortName=postData.value._id,postData.value.tableConfig=JSON.stringify(t),postData.value.tableConfig=void 0!==postData.value.tableConfig?postData.value.tableConfig:"{}"}).catch(function(e){postData.value={}});case 5:e.next=9;break;case 7:return e.next=9,ctx.parent.$store.dispatch("vuejx_manager/userById",{db:props.database,collection:props.collection,id:props.id}).then(function(e){postData.value=e}).catch(function(e){postData.value={}});case 9:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),toPage=function(e){var t=window.Vue.$router.history.current.params;Vue.redirect(null,!1,"/admin/"+(void 0!==t.database?t.database:"vuejx_cfg")+"/"+e+"/"+(void 0!==t.site?t.site:"guest"),!0)},refresh=function(){location.reload(!0)},addData=function(){ctx.refs.form.validate()&&(postData.value.site=props.site,loading.value=!0,ctx.parent.$store.dispatch("vuejx_manager/userCreate",{db:props.database,collection:props.collection,body:postData.value}).then(function(e){loading.value=!1,backURL()}).catch(function(e){loading.value=!1}),loading.value=!1)},editData=function(e){ctx.refs.form.validate()&&(postData.value.site=props.site,loading.value=!0,ctx.parent.$store.dispatch("vuejx_manager/userUpdateById",{db:props.database,collection:props.collection,body:postData.value}).then(function(t){loading.value=!1,window.Vue.toastr.success("Success."),0===e&&backURL()}).catch(function(e){loading.value=!1}))},removeData=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,ctx.parent.$store.dispatch("vuejx_manager/userRemoveById",{db:props.database,collection:props.collection,id:props.id});case 2:backURL();case 3:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),backURL=function(){Vue.$router.go(-1)},toURL=function(e){Vue.redirect(null,!1,e)},reindex=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,ctx.parent.$store.dispatch("vuejx_manager/reindex",{api:_config__WEBPACK_IMPORTED_MODULE_1__.a.reindex,db:props.database,collection:props.collection,from:null!==from.value?new Date(from.value).getTime():"",until:null!==until.value?new Date(until.value).getTime():"",type:type.value,all:"false"}).then(function(e){}).catch(function(e){console.log(e)});case 2:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),reindexAll=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,ctx.parent.$store.dispatch("vuejx_manager/reindex",{api:_config__WEBPACK_IMPORTED_MODULE_1__.a.reindex,db:props.database,collection:props.collection,from:null!==from.value?new Date(from.value).getTime():"",until:null!==until.value?new Date(until.value).getTime():"",type:type.value,all:"true"}).then(function(e){}).catch(function(e){console.log(e)});case 2:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),mergeOAI=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:Vue.redirect(null,!1,"/admin/"+props.database+"/"+props.collection+"/"+props.site+"/"+props.id+"/async/"+postData.value.shortName+"/"+postData.value.storeCollection);case 1:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),doChangeSite=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:Vue.redirect(null,!1,"/admin/"+props.database+"/"+props.collection+"/"+siteSelected.value);case 1:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),asyncOAI=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,ctx.parent.$store.dispatch("vuejx_manager/pullAPI",{api:_config__WEBPACK_IMPORTED_MODULE_1__.a.async+props.id,params:{from:null!==from.value?new Date(from.value).getTime():"",until:null!==until.value?new Date(until.value).getTime():""}}).then(function(e){}).catch(function(e){console.log(e)});case 2:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),initSite=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){var t;return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return t={_source:{includes:["shortName","title"]},query:{bool:{}}},e.next=3,ctx.parent.$store.dispatch("vuejx_manager/graphqlQuery",{query:"\n                query search($token: String, $body: JSON, $db: String, $collection: String) {\n                    results: search(token: $token, body: $body, db: $db, collection: $collection )\n                }\n            ",variables:{body:t,db:"vuejx_cfg",collection:"vuejx_site"}}).then(function(e){void 0!==e.results&&null!==e.results?sites.value=e.results.hits.hits:sites.value=[]}).catch(function(e){sites.value=[]});case 3:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}();return{clean:clean,loading:loading,postData:postData,valid:valid,toPage:toPage,addData:addData,refresh:refresh,backURL:backURL,editData:editData,toURL:toURL,api:api,pullDetail:pullDetail,metadataConfig:metadataConfig,render:render,pullConfig:pullConfig,removeData:removeData,reindex:reindex,from:from,until:until,type:type,mergeOAI:mergeOAI,asyncOAI:asyncOAI,initSite:initSite,sites:sites,doChangeSite:doChangeSite,siteSelected:siteSelected,apiReindexAll:apiReindexAll,reindexAll:reindexAll}}__webpack_exports__.a=service}}]);