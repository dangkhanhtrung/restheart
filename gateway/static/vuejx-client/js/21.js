(window.webpackJsonp=window.webpackJsonp||[]).push([[21],{YuTi:function(t,e){t.exports=function(t){return t.webpackPolyfill||(t.deprecate=function(){},t.paths=[],t.children||(t.children=[]),Object.defineProperty(t,"loaded",{enumerable:!0,get:function(){return t.l}}),Object.defineProperty(t,"id",{enumerable:!0,get:function(){return t.i}}),t.webpackPolyfill=1),t}},cNbQ:function(t,e,n){(function(t){var n,o,a,i;function r(t){return(r="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t})(t)}"undefined"!=typeof self&&self,i=function(){return function(t){function e(o){if(n[o])return n[o].exports;var a=n[o]={i:o,l:!1,exports:{}};return t[o].call(a.exports,a,a.exports,e),a.l=!0,a.exports}var n={};return e.m=t,e.c=n,e.d=function(t,n,o){e.o(t,n)||Object.defineProperty(t,n,{configurable:!1,enumerable:!0,get:o})},e.n=function(t){var n=t&&t.__esModule?function(){return t.default}:function(){return t};return e.d(n,"a",n),n},e.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},e.p="",e(e.s=4)}([function(t,e){t.exports=function(t,e,n,o,a,i){var s,l=t=t||{},c=r(t.default);"object"!==c&&"function"!==c||(s=t,l=t.default);var u,d="function"==typeof l?l.options:l;if(e&&(d.render=e.render,d.staticRenderFns=e.staticRenderFns,d._compiled=!0),n&&(d.functional=!0),a&&(d._scopeId=a),i?(u=function(t){(t=t||this.$vnode&&this.$vnode.ssrContext||this.parent&&this.parent.$vnode&&this.parent.$vnode.ssrContext)||"undefined"==typeof __VUE_SSR_CONTEXT__||(t=__VUE_SSR_CONTEXT__),o&&o.call(this,t),t&&t._registeredComponents&&t._registeredComponents.add(i)},d._ssrRegister=u):o&&(u=o),u){var v=d.functional,_=v?d.render:d.beforeCreate;v?(d._injectStyles=u,d.render=function(t,e){return u.call(e),_(t,e)}):d.beforeCreate=_?[].concat(_,u):[u]}return{esModule:s,exports:l,options:d}}},function(t,e,n){"use strict";function o(t){return t&&t.__esModule?t:{default:t}}Object.defineProperty(e,"__esModule",{value:!0});var a=o(n(6)),i=o(n(8));e.default={name:"qlvt-bieudoxechay",components:{DialogThemNot:a.default,DialogChuThich:i.default},data:function(t){return{isDialogThemNot:!1,isDiaLogChuThich:!1,select:"Programming",items:["Programming","Design","Vue","Vuetify"],date:(new Date).toISOString().substr(0,10),dateFormatted:null,menu:!1,getDayInMonth:31}}}},function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={props:{isDialogThemNot:{type:Boolean,default:!1},getDayInMonth:{type:Number,default:0}},components:{},data:function(t){return{}},mounted:function(){},computed:{},watch:{isDialogThemNot:function(t){!1===t&&this.closeDialog()}},methods:{closeDialog:function(){this.$emit("closeDialog",!1)}}}},function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={props:{isDiaLogChuThich:{type:Boolean,default:!1}},components:{},data:function(t){return{headers:[{text:"STT"},{text:"Ký hiệu "},{text:"Tên doanh nghiệp- Đầu A"}],headers2:[{text:"STT"},{text:"Ký hiệu "},{text:"Tên doanh nghiệp- Đầu B"}],lists:[1,2,3,4,5]}},mounted:function(){},computed:{},watch:{isDiaLogChuThich:function(t){!1===t&&this.closeDialog()}},methods:{closeDialog:function(){this.$emit("closeDialog",!1)}}}},function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.Vue3Component=void 0;var o=function(t){return t&&t.__esModule?t:{default:t}}(n(5)),a={install:function(t){t.component(o.default.name,o.default)}};e.default=a,e.Vue3Component=a,"undefined"!=typeof window&&window.Vue&&window.Vue.use(a)},function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(1),a=n.n(o);for(var i in o)"default"!==i&&function(t){n.d(e,t,function(){return o[t]})}(i);var r=n(10),s=n(0)(a.a,r.a,!1,null,null,null);e.default=s.exports},function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(2),a=n.n(o);for(var i in o)"default"!==i&&function(t){n.d(e,t,function(){return o[t]})}(i);var r=n(7),s=n(0)(a.a,r.a,!1,null,null,null);e.default=s.exports},function(t,e,n){"use strict";var o={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-dialog",{attrs:{"max-width":"700",persistent:"",scrollable:"",transition:"fade-transition"},model:{value:t.isDialogThemNot,callback:function(e){t.isDialogThemNot=e},expression:"isDialogThemNot"}},[n("v-card",[n("v-app-bar",{staticStyle:{"border-radius":"0"},attrs:{color:"primary",dark:"",height:"42",tile:""}},[n("v-toolbar-title",[t._v("Thêm nốt")]),t._v(" "),n("div",{staticClass:"flex-grow-1"}),t._v(" "),n("v-btn",{attrs:{icon:""}},[n("v-icon",[t._v("mdi-close")])],1)],1),t._v(" "),n("v-divider"),t._v(" "),n("v-card-text",{staticClass:"pa-0",staticStyle:{height:"300px"}},[n("div",{staticClass:"container pa-4 default__screen grid-list-md"},[n("v-layout",{attrs:{wrap:""}},[n("v-flex",{attrs:{sm6:""}},[n("v-subheader",{staticClass:"vjx_label"},[t._v("\n                Giờ xuất bến tại bến đi\n                "),n("font",{staticClass:"required__class"},[t._v("*")])],1),t._v(" "),n("v-text-field",{staticClass:"v__border",attrs:{text:"",solo:""}})],1),t._v(" "),n("v-flex",{attrs:{sm6:""}},[n("v-subheader",{staticClass:"vjx_label"},[t._v("\n                Giờ xuất bến tại bến về\n                "),n("font",{staticClass:"required__class"},[t._v("*")])],1),t._v(" "),n("v-text-field",{staticClass:"v__border",attrs:{text:"",solo:""}})],1),t._v(" "),n("v-flex",{staticClass:"mb-2",attrs:{sm12:""}},[n("v-autocomplete",{staticClass:"v__border",attrs:{text:"",solo:"","hide-no-data":"","hide-details":"","return-object":!0,"small-chips":"","append-icon":(t.rendered,"$vuetify.icons.dropdown"),label:"Ngày xuất bến tại bến đi - về"}})],1),t._v(" "),n("v-flex",{attrs:{sm12:""}},[n("v-autocomplete",{staticClass:"v__border",attrs:{text:"",solo:"","hide-no-data":"","hide-details":"","return-object":!0,"small-chips":"","append-icon":(t.rendered,"$vuetify.icons.dropdown"),label:"Trạng thái"}})],1)],1)],1)]),t._v(" "),n("v-divider"),t._v(" "),n("v-card-actions",[n("div",{staticClass:"flex-grow-1"}),t._v(" "),n("v-btn",{attrs:{color:"error",outlined:"",dark:""},on:{click:function(e){t.isDialogThemNot=!1}}},[t._v("Quay lại")]),t._v(" "),n("v-btn",{attrs:{color:"primary"},on:{click:function(e){t.isDialogThemNot=!1}}},[t._v("Lưu")])],1)],1)],1)},staticRenderFns:[]};e.a=o},function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(3),a=n.n(o);for(var i in o)"default"!==i&&function(t){n.d(e,t,function(){return o[t]})}(i);var r=n(9),s=n(0)(a.a,r.a,!1,null,null,null);e.default=s.exports},function(t,e,n){"use strict";var o={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-dialog",{attrs:{"max-width":"700"},model:{value:t.isDiaLogChuThich,callback:function(e){t.isDiaLogChuThich=e},expression:"isDiaLogChuThich"}},[n("v-card",[n("div",{staticClass:"dialog-title"},[n("span",[t._v("Chú thích")])]),t._v(" "),n("v-layout",{attrs:{wrap:""}},[n("v-flex",{attrs:{xs6:""}},[n("div",{staticClass:"table-danhsach"},[n("table",[n("thead",[n("tr",t._l(t.headers,function(e){return n("th",{key:e.text},[n("span",[t._v(t._s(e.text))])])}),0)]),t._v(" "),n("tbody",t._l(t.lists,function(e,o){return n("tr",{key:o},[n("td",[t._v(t._s(o+1))]),t._v(" "),n("td",[t._v(t._s(o+1))]),t._v(" "),n("td",[t._v(t._s(o+1))])])}),0)])])]),t._v(" "),n("v-flex",{attrs:{xs6:""}},[n("div",{staticClass:"table-danhsach"},[n("table",[n("thead",[n("tr",t._l(t.headers2,function(e){return n("th",{key:e.text},[n("span",[t._v(t._s(e.text))])])}),0)]),t._v(" "),n("tbody",t._l(t.lists,function(e,o){return n("tr",{key:o},[n("td",[t._v(t._s(o+1))]),t._v(" "),n("td",[t._v(t._s(o+1))]),t._v(" "),n("td",[t._v(t._s(o+1))])])}),0)])])])],1),t._v(" "),n("v-card-actions",[n("div",{staticClass:"flex-grow-1"}),t._v(" "),n("button",{staticClass:"btn-chitiet",staticStyle:{"background-color":"rgb(26, 115, 232)",color:"white"},on:{click:t.closeDialog}},[t._v("\n      Đóng\n            \n      ")])])],1)],1)},staticRenderFns:[]};e.a=o},function(t,e,n){"use strict";var o={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("v-layout",{attrs:{wrap:""}},[n("v-flex",{attrs:{xs12:""}},[n("div",{staticClass:"filter-group"},[n("v-layout",{attrs:{wrap:""}},[n("v-flex",{attrs:{sm2:""}},[n("v-autocomplete",{staticClass:"v__border",attrs:{text:"",solo:"","hide-no-data":"","hide-details":"","return-object":!0,"small-chips":"","append-icon":(t.rendered,"$vuetify.icons.dropdown"),label:"Chọn tuyến"}})],1),t._v(" "),n("v-flex",{attrs:{sm3:""}},[n("v-autocomplete",{staticClass:"v__border",attrs:{text:"",solo:"","hide-no-data":"","hide-details":"","return-object":!0,"small-chips":"","append-icon":(t.rendered,"$vuetify.icons.dropdown"),label:"Đơn vị khai thác"}})],1),t._v(" "),n("v-flex",{attrs:{sm4:""}},[n("v-autocomplete",{staticClass:"v__border",attrs:{text:"",solo:"","hide-no-data":"","hide-details":"","return-object":!0,"small-chips":"","append-icon":(t.rendered,"$vuetify.icons.dropdown"),label:"Chọn ngày"}})],1),t._v(" "),n("v-flex",{attrs:{sm1:""}},[n("v-autocomplete",{staticClass:"v__border",attrs:{text:"",solo:"","hide-no-data":"","hide-details":"","return-object":!0,"small-chips":"","append-icon":(t.rendered,"$vuetify.icons.dropdown"),label:"Giờ đi"}})],1),t._v(" "),n("v-flex",{attrs:{sm1:""}},[n("v-autocomplete",{staticClass:"v__border",attrs:{text:"",solo:"","hide-no-data":"","hide-details":"","return-object":!0,"small-chips":"","append-icon":(t.rendered,"$vuetify.icons.dropdown"),label:"Giờ đến"}})],1),t._v(" "),n("v-flex",{attrs:{sm1:""}},[n("v-btn",{attrs:{color:"primary"},on:{click:function(e){t.isDialogThemNot=!0}}},[n("v-icon",{attrs:{size:"18"}},[t._v("mdi-playlist-plus")]),t._v("\n                  Lịch chạy")],1)],1)],1)],1)]),t._v(" "),n("v-flex",{attrs:{xs12:""}},[n("div",{staticClass:"employee__table",staticStyle:{"overflow-x":"auto"}},[n("div",{staticClass:"chart__table__preview bieu__do__xe"},[n("table",[n("thead",[n("tr",[n("th",{attrs:{rowspan:"3"}},[t._v("STT")]),t._v(" "),n("th",{staticStyle:{width:"50px"},attrs:{rowspan:"3"}},[t._v(" Thao tác")]),t._v(" "),n("th",{staticStyle:{"padding-left":"16% !important"},attrs:{colspan:2*t.getDayInMonth,align:"left"}},[t._v("\n                      Giờ xuất bến ngày trong tháng\n                    ")])]),t._v(" "),n("tr",t._l(t.getDayInMonth,function(e,o){return n("th",{key:o,attrs:{colspan:"2"}},[t._v(t._s("Ngày"+e))])}),0),t._v(" "),n("tr",t._l(2*t.getDayInMonth,function(e,o){return n("th",{key:o,attrs:{width:"60"}},[t._v(t._s(e%2==0?"Đến":"Đi"))])}),0)]),t._v(" "),n("tbody",t._l(10,function(e,o){return n("tr",{key:o},[n("td",[t._v(t._s(o+1))]),t._v(" "),n("td",{attrs:{align:"center"}},[n("v-icon",{attrs:{size:"18"}},[t._v("mdi-playlist-edit")])],1),t._v(" "),t._l(2*t.getDayInMonth,function(e,o){return n("td",{key:o,staticClass:"bieu__do__column",class:{"da-duoc-khai-thac":e%2==0,"dang-duoc-dang-ky":e%5==0,"chua-duoc-dang-ky":e%3==0}},[t._v("05h30")])})],2)}),0)])])])])],1),t._v(" "),n("v-layout",{attrs:{row:"",wrap:""}},[n("v-flex",{attrs:{xs12:""}},[n("v-btn",{attrs:{color:"primary"},on:{click:function(e){t.isDialogThemNot=!0}}},[t._v("Thêm nốt")]),t._v(" "),n("v-btn",{attrs:{outlined:"",color:"primary",dark:""},on:{click:t.backToURL}},[t._v("Xuất excel")]),t._v(" "),n("v-btn",{attrs:{outlined:"",color:"primary",dark:""},on:{click:t.backToURL}},[t._v("Quay lại")])],1)],1),t._v(" "),n("DialogThemNot",{attrs:{isDialogThemNot:t.isDialogThemNot,getDayInMonth:t.getDayInMonth},on:{closeDialog:function(e){t.isDialogThemNot=!1}}}),t._v(" "),n("DialogChuThich",{attrs:{isDiaLogChuThich:t.isDiaLogChuThich},on:{closeDialog:function(e){t.isDiaLogChuThich=!1}}})],1)},staticRenderFns:[]};e.a=o}])},"object"==r(e)&&"object"==r(t)?t.exports=i():(o=[],void 0===(a="function"==typeof(n=i)?n.apply(e,o):n)||(t.exports=a))}).call(this,n("YuTi")(t))}}]);