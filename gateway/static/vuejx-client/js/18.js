(window.webpackJsonp=window.webpackJsonp||[]).push([[18],{"1ZGa":function(e,t,n){"use strict";n.r(t);n("bvwr");var a=n("hTPA"),r=n("Xp2Q"),o={name:"vuejx-manager",props:{database:{type:String,default:"vuejx_cfg"},collection:{type:String,default:""},site:{type:String,default:"guest"}},setup:function(e,t){var n=Object(r.a)(e,t),o=n.toURL,i=n.api,u=n.apiReindexAll;Object(a.onDestroyed)(function(){i.value=""});return{api:i,addDataPage:function(){o("/admin/"+e.database+"/"+e.collection+"/"+e.site+"/NULL")},editDataPage:function(t){"vuejx_workflow"===e.collection?o("/admin/workflow/"+e.database+"/"+e.collection+"/"+e.site+"?_id="+t.rowData._id):o("/admin/"+e.database+"/"+e.collection+"/"+e.site+"/"+t.rowData._id)},apiReindexAll:u}}},i=n("KHd+"),u=Object(i.a)(o,function(){var e=this.$createElement,t=this._self._c||e;return t("div",[t("vuejx-table",{attrs:{api:this.api,database:this.database,collection:this.collection,site:this.site},on:{addData:this.addDataPage,editData:this.editDataPage}})],1)},[],!1,null,null,null);t.default=u.exports},Xp2Q:function(module,__webpack_exports__,__webpack_require__){"use strict";var vue_function_api__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("hTPA"),_config__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("8SHQ");function asyncGeneratorStep(e,t,n,a,r,o,i){try{var u=e[o](i),c=u.value}catch(e){return void n(e)}u.done?t(c):Promise.resolve(c).then(a,r)}function _asyncToGenerator(e){return function(){var t=this,n=arguments;return new Promise(function(a,r){var o=e.apply(t,n);function i(e){asyncGeneratorStep(o,a,r,i,u,"next",e)}function u(e){asyncGeneratorStep(o,a,r,i,u,"throw",e)}i(void 0)})}}function service(props,ctx){var loading=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(!1),render=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(!1),api=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(_config__WEBPACK_IMPORTED_MODULE_1__.a.vuejx),apiReindexAll=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(_config__WEBPACK_IMPORTED_MODULE_1__.a.reindexAll),postData=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)({}),metadataConfig=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)({}),valid=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(!1),from=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(null),until=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(null),type=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(!1),sites=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)([]),siteSelected=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(props.site),renderWorkflow=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(!1),buckets=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)([]),bucket=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(""),previewJSON=Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.value)(null);Object(vue_function_api__WEBPACK_IMPORTED_MODULE_0__.onDestroyed)(function(){clean()});var clean=function(){loading.value=!1,render.value=!1,api.value="",postData.value=null,metadataConfig.value=null,valid.value=null},pullConfig=function(){var _ref=_asyncToGenerator(regeneratorRuntime.mark(function _callee(){return regeneratorRuntime.wrap(function _callee$(_context){for(;;)switch(_context.prev=_context.next){case 0:if(""===props.collection){_context.next=3;break}return _context.next=3,ctx.parent.$store.dispatch("vuejx_manager/userOne",{db:"vuejx_cfg",collection:"vuejx_collection",filter:{shortName:props.collection},keys:{metadataConfig:1}}).then(function(data){metadataConfig.value=null!=data?eval("("+data.metadataConfig+")").form:{}}).catch(function(e){metadataConfig.value={}});case 3:case"end":return _context.stop()}},_callee)}));return function(){return _ref.apply(this,arguments)}}(),pullDetail=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,pullConfig();case 2:if("_schemas"!==props.collection){e.next=7;break}return e.next=5,ctx.parent.$store.dispatch("vuejx_manager/pullDB",{db:props.database,collection:props.collection,params:{pagesize:1e3}}).then(function(e){postData.value=e._embedded.find(function(e){return e._id===props.id});var t={};for(var n in postData.value)"_id"!==n&&"id"!==n&&"$schema"!==n&&"_etag"!==n&&(t[n]=postData.value[n]);postData.value.shortName=postData.value._id,postData.value.tableConfig=JSON.stringify(t),postData.value.tableConfig=void 0!==postData.value.tableConfig?postData.value.tableConfig:"{}"}).catch(function(e){postData.value={}});case 5:e.next=9;break;case 7:return e.next=9,ctx.parent.$store.dispatch("vuejx_manager/userById",{db:props.database,collection:props.collection,id:props.id}).then(function(e){postData.value=e}).catch(function(e){postData.value={}});case 9:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),toPage=function(e){var t=window.Vue.$router.history.current.params;Vue.redirect(null,!1,"/admin/"+(void 0!==t.database?t.database:"vuejx_cfg")+"/"+e+"/"+(void 0!==t.site?t.site:"guest"),!0)},refresh=function(){location.reload(!0)},addData=function(){ctx.refs.form.validate()&&(postData.value.site=props.site,loading.value=!0,ctx.parent.$store.dispatch("vuejx_manager/userCreate",{db:props.database,collection:props.collection,body:postData.value}).then(function(e){loading.value=!1,backURL()}).catch(function(e){loading.value=!1}),loading.value=!1)},editData=function(e){ctx.refs.form.validate()&&(postData.value.site=props.site,loading.value=!0,ctx.parent.$store.dispatch("vuejx_manager/userUpdateById",{db:props.database,collection:props.collection,body:postData.value}).then(function(t){loading.value=!1,window.Vue.toastr.success("Success."),0===e&&backURL()}).catch(function(e){loading.value=!1}))},removeData=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,ctx.parent.$store.dispatch("vuejx_manager/userRemoveById",{db:props.database,collection:props.collection,id:props.id});case 2:backURL();case 3:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),backURL=function(){Vue.$router.go(-1)},toURL=function(e){Vue.redirect(null,!1,e)},reindex=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,ctx.parent.$store.dispatch("vuejx_manager/reindex",{api:_config__WEBPACK_IMPORTED_MODULE_1__.a.reindex,db:props.database,collection:props.collection,from:null!==from.value?new Date(from.value).getTime():"",until:null!==until.value?new Date(until.value).getTime():"",type:type.value,all:"false"}).then(function(e){}).catch(function(e){console.log(e)});case 2:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),reindexAll=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,ctx.parent.$store.dispatch("vuejx_manager/reindex",{api:_config__WEBPACK_IMPORTED_MODULE_1__.a.reindex,db:props.database,collection:props.collection,from:null!==from.value?new Date(from.value).getTime():"",until:null!==until.value?new Date(until.value).getTime():"",type:type.value,all:"true"}).then(function(e){}).catch(function(e){console.log(e)});case 2:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),mergeOAI=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:Vue.redirect(null,!1,"/admin/"+props.database+"/"+props.collection+"/"+props.site+"/"+props.id+"/async/"+postData.value.shortName+"/"+postData.value.storeCollection);case 1:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),doChangeSite=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:Vue.redirect(null,!1,"/admin/"+props.database+"/"+props.collection+"/"+siteSelected.value);case 1:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),asyncOAI=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(t){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return console.log("asyncOAIasyncOAIasyncOAI",t),previewJSON.value=null,e.next=4,ctx.root.$store.dispatch("vuejx_manager/async",{from:null!==from.value?new Date(from.value).getTime():"",until:null!==until.value?new Date(until.value).getTime():"",id:props.id,view:t}).then(function(e){console.log("datadatadatadatadata",e),previewJSON.value=e}).catch(function(e){console.log(e),previewJSON.value=null});case 4:case"end":return e.stop()}},e)}));return function(t){return e.apply(this,arguments)}}(),initSite=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){var t;return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return t={_source:{includes:["shortName","title"]},query:{bool:{}}},e.next=3,ctx.parent.$store.dispatch("vuejx_manager/graphqlQuery",{query:"\n                query search($token: String, $body: JSON, $db: String, $collection: String) {\n                    results: search(token: $token, body: $body, db: $db, collection: $collection )\n                }\n            ",variables:{body:t,db:"vuejx_cfg",collection:"vuejx_site"}}).then(function(e){void 0!==e.results&&null!==e.results?sites.value=e.results.hits.hits:sites.value=[]}).catch(function(e){sites.value=[]});case 3:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),pullDetailPrototype=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(t){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:renderWorkflow.value=!1,postData.value=t,setTimeout(function(){renderWorkflow.value=!0},100);case 3:case"end":return e.stop()}},e)}));return function(t){return e.apply(this,arguments)}}(),pullBuckets=function(){var e=_asyncToGenerator(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return render.value=!1,buckets.value=[],e.next=4,ctx.parent.$store.dispatch("vuejx_manager/userMany",{db:"vuejx_cfg",collection:"vuejx_collection",keys:{shortName:1,title:1},limit:1e3}).then(function(e){buckets.value=null!=e?e.hits.hits:[],render.value=!0}).catch(function(e){buckets.value=[],render.value=!0});case 4:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),doChangeBuckets=function(){};return{clean:clean,loading:loading,postData:postData,valid:valid,toPage:toPage,addData:addData,refresh:refresh,backURL:backURL,editData:editData,toURL:toURL,api:api,pullDetail:pullDetail,metadataConfig:metadataConfig,render:render,pullConfig:pullConfig,removeData:removeData,reindex:reindex,from:from,until:until,type:type,mergeOAI:mergeOAI,asyncOAI:asyncOAI,initSite:initSite,sites:sites,doChangeSite:doChangeSite,siteSelected:siteSelected,apiReindexAll:apiReindexAll,reindexAll:reindexAll,pullDetailPrototype:pullDetailPrototype,renderWorkflow:renderWorkflow,buckets:buckets,pullBuckets:pullBuckets,bucket:bucket,doChangeBuckets:doChangeBuckets,previewJSON:previewJSON}}__webpack_exports__.a=service}}]);