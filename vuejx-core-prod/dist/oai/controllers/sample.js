"use strict";Object.defineProperty(exports,"__esModule",{value:!0});var core_oai_provider_1=require("../core/core-oai-provider"),oai_response_1=require("../core/oai-response");exports.oai=function(e,r){switch(r.set("Content-Type","text/xml"),e.query.verb){case"Identify":case"ListMetadataFormats":case"ListIdentifiers":case"ListRecords":case"ListSets":case"GetRecord":break;default:var o={baseUrl:e.protocol+"://"+e.get("host")+e.path};r.send(oai_response_1.generateException(o,core_oai_provider_1.EXCEPTION_CODES.BAD_VERB))}};