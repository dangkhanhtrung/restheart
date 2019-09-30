"use strict";var __awaiter=this&&this.__awaiter||function(a,i,u,s){return new(u=u||Promise)(function(e,t){function r(e){try{o(s.next(e))}catch(e){t(e)}}function n(e){try{o(s.throw(e))}catch(e){t(e)}}function o(t){t.done?e(t.value):new u(function(e){e(t.value)}).then(r,n)}o((s=s.apply(a,i||[])).next())})},__generator=this&&this.__generator||function(r,n){var o,a,i,e,u={label:0,sent:function(){if(1&i[0])throw i[1];return i[1]},trys:[],ops:[]};return e={next:t(0),throw:t(1),return:t(2)},"function"==typeof Symbol&&(e[Symbol.iterator]=function(){return this}),e;function t(t){return function(e){return function(t){if(o)throw new TypeError("Generator is already executing.");for(;u;)try{if(o=1,a&&(i=2&t[0]?a.return:t[0]?a.throw||((i=a.return)&&i.call(a),0):a.next)&&!(i=i.call(a,t[1])).done)return i;switch(a=0,i&&(t=[2&t[0],i.value]),t[0]){case 0:case 1:i=t;break;case 4:return u.label++,{value:t[1],done:!1};case 5:u.label++,a=t[1],t=[0];continue;case 7:t=u.ops.pop(),u.trys.pop();continue;default:if(!(i=0<(i=u.trys).length&&i[i.length-1])&&(6===t[0]||2===t[0])){u=0;continue}if(3===t[0]&&(!i||t[1]>i[0]&&t[1]<i[3])){u.label=t[1];break}if(6===t[0]&&u.label<i[1]){u.label=i[1],i=t;break}if(i&&u.label<i[2]){u.label=i[2],u.ops.push(t);break}i[2]&&u.ops.pop(),u.trys.pop();continue}t=n.call(r,u)}catch(e){t=[6,e],a=0}finally{o=i=0}if(5&t[0])throw t[1];return{value:t[0]?t[1]:void 0,done:!0}}([t,e])}}},_this=this;Object.defineProperty(exports,"__esModule",{value:!0});var Router=require("koa-router"),async_mapper_1=require("../oai/oai-provider/repository/async-mapper"),asyncMapper=new async_mapper_1.AsyncMapper,asyncRouter=new Router;asyncRouter.get("/async/secure/:id",function(t,r){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return asyncMapper.oaiAsyncdes(t.params.id,t.request.header.user),t.response.body="ok",t.response.status=200,r(),[2]})})}),asyncRouter.post("/auth/google",function(t,r){return __awaiter(_this,void 0,void 0,function(){return __generator(this,function(e){return t.response.status=200,t.response.body={id:"105846938824979476627",email:"binhth.alpaca@gmail.com",verified_email:!0,name:"Bình Tạ Hữu",given_name:"Bình",family_name:"Tạ Hữu",picture:"https://lh6.googleusercontent.com/-K8vCcqvEpyo/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rcof_kH-MRsSoqCgXieEySsM8xPQA/photo.jpg",locale:"en"},r(),[2]})})}),exports.default=asyncRouter;