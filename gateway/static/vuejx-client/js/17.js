(window.webpackJsonp=window.webpackJsonp||[]).push([[17],{LLfy:function(e,t,r){"use strict";r.r(t);var o=r("MtVN").a,u=r("KHd+"),n=Object(u.a)(o,function(){var e=this.$createElement,t=this._self._c||e;return null!==this.vuejx_form&&void 0!==this.vuejx_form&&"undefined"!==this.vuejx_form&&this.render?t("my-dynamic-view"):this._e()},[],!1,null,null,null);t.default=n.exports},MtVN:function(module,__webpack_exports__,__webpack_require__){"use strict";var _config__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("8SHQ");__webpack_exports__.a={name:"vuejx-component",props:{vuejx_form:{type:Object,default:null},page:{type:String,default:""},site:{type:String,default:""},csstextExt:{type:String,default:""}},data:function(){return{api:_config__WEBPACK_IMPORTED_MODULE_0__.a.vuejx,render:!1}},mounted:function mounted(){var vm=this;vm.render=!1,null!==vm.vuejx_form&&void 0!==vm.vuejx_form&&"undefined"!==vm.vuejx_form&&(vm.vuejx_form=eval("( "+vm.vuejx_form+" ) "),Vue.component("my-dynamic-view",{metaInfo:function(){return{style:[{cssText:vm.csstextExt,type:"text/css"}]}},template:vm.vuejx_form.template,data:eval(" ( "+vm.vuejx_form.data+" ) "),beforeCreate:eval(" ( "+vm.vuejx_form.beforeCreate+" ) "),created:eval(" ( "+vm.vuejx_form.created+" ) "),beforeMount:eval(" ( "+vm.vuejx_form.beforeMount+" ) "),mounted:eval(" ( "+vm.vuejx_form.mounted+" ) "),watch:vm.vuejx_form.watch,beforeUpdate:eval(" ( "+vm.vuejx_form.beforeMount+" ) "),updated:eval(" ( "+vm.vuejx_form.updated+" ) "),beforeDestroy:eval(" ( "+vm.vuejx_form.beforeDestroy+" ) "),destroyed:eval(" ( "+vm.vuejx_form.destroyed+" ) "),methods:vm.vuejx_form.methods}),vm.render=!0)},beforeDestroy:function(){},destroyed:function(){this.$destroy()},methods:{permission:function(e){var t=JSON.parse(localStorage.getItem("user"));return!(null!=e&&e.length>0)||t.role.some(function(t){if(-1!==e.indexOf(t._source.shortName))return!0})}}}}}]);