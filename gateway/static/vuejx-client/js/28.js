(window.webpackJsonp=window.webpackJsonp||[]).push([[28],{vsFi:function(t,e){function n(t){return(n="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t})(t)}t.exports=function(t){function e(o){if(n[o])return n[o].exports;var r=n[o]={i:o,l:!1,exports:{}};return t[o].call(r.exports,r,r.exports,e),r.l=!0,r.exports}var n={};return e.m=t,e.c=n,e.d=function(t,n,o){e.o(t,n)||Object.defineProperty(t,n,{configurable:!1,enumerable:!0,get:o})},e.n=function(t){var n=t&&t.__esModule?function(){return t.default}:function(){return t};return e.d(n,"a",n),n},e.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},e.p="/",e(e.s=1)}([function(t,e,n){"use strict";var o=n(4);e.a={name:"vuejx-footer",props:{logo_title:{type:String,default:"VueJX CMS"},pages:{type:Array,default:[]},dark:{type:Boolean,default:!1},absolute:{type:Boolean,default:!0}},setup:function(t,e){return Object(o.a)(t,e)}}},function(t,e,n){t.exports=n(2)},function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(0),r=n(5),u=n(3)(o.a,r.a,!1,null,null,null);e.default=u.exports},function(t,e){t.exports=function(t,e,o,r,u,s){var a,i=t=t||{},c=n(t.default);"object"!==c&&"function"!==c||(a=t,i=t.default);var f,l="function"==typeof i?i.options:i;if(e&&(l.render=e.render,l.staticRenderFns=e.staticRenderFns,l._compiled=!0),o&&(l.functional=!0),u&&(l._scopeId=u),s?(f=function(t){(t=t||this.$vnode&&this.$vnode.ssrContext||this.parent&&this.parent.$vnode&&this.parent.$vnode.ssrContext)||"undefined"==typeof __VUE_SSR_CONTEXT__||(t=__VUE_SSR_CONTEXT__),r&&r.call(this,t),t&&t._registeredComponents&&t._registeredComponents.add(s)},l._ssrRegister=f):r&&(f=r),f){var p=l.functional,d=p?l.render:l.beforeCreate;p?(l._injectStyles=f,l.render=function(t,e){return f.call(e),d(t,e)}):l.beforeCreate=d?[].concat(d,f):[f]}return{esModule:a,exports:i,options:l}}},function(t,e,n){"use strict";e.a=function(t,e){return{account:localStorage.getItem("account")}}},function(t,e,n){"use strict";var o={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-footer",{staticStyle:{padding:"0"},attrs:{absolute:t.absolute}},[t._t("footer",[n("div",{staticClass:"flex-grow-1"}),t._v(" "),n("div",[t._v("VueJX © "+t._s((new Date).getFullYear()))])])],2)},staticRenderFns:[]};e.a=o}])}}]);