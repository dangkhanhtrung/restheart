"use strict";Object.defineProperty(exports,"__esModule",{value:!0});var fs=require("fs");function hasCredentialsFile(e){if(!e)return!1;try{return fs.statSync(e),!0}catch(e){return!1}}function getCredentials(e){try{return JSON.parse(fs.readFileSync(e,"utf8"))}catch(e){}}exports.hasCredentialsFile=hasCredentialsFile,exports.getCredentials=getCredentials;