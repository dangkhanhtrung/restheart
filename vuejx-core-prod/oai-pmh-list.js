import { get } from 'lodash'

import { parseOaiPmhXml } from './oai-pmh-xml'

function getResumptionToken (result, listSize) {
  const token = result.resumptionToken
  console.log('getResumptionToken', token, listSize);
  
  if (!token || token === undefined) return undefined

  if (typeof token === 'string') return token
  
  /*
  const cursor = get(token, '$.cursor')
  const completeListSize = get(token, '$.completeListSize')
  if (
    cursor &&
    completeListSize 
    &&  parseInt(cursor, 10) + listSize >= parseInt(completeListSize, 10)
  ) return undefined
  */
  return token._
}

export async function * getOaiListItems (oaiPmh, verb, field, options) {
  const initialResponse = await oaiPmh.request({
    url: oaiPmh.baseUrl,
    qs: {
      ...options,
      verb
    }
  })
  let initialParsedResponse = await parseOaiPmhXml(initialResponse.body)
  const initialResult = initialParsedResponse[verb]
  if (Array.isArray(initialResult[field])) {
    for await (const item of initialResult[field]) {
      yield item
    }
  } else {
    yield initialResult[field]
  }
  
  let resumptionToken = undefined;
  if (initialResult[field] !== undefined) {
    resumptionToken = getResumptionToken(initialParsedResponse, initialResult[field].length)
  }
  console.log('resumptionToken', resumptionToken);
  while (resumptionToken !== undefined) {
    const response = await oaiPmh.request({
      url: oaiPmh.baseUrl,
      qs: {
        verb,
        resumptionToken
      }
    })
    initialParsedResponse = await parseOaiPmhXml(response.body)
    if (initialParsedResponse[verb] !== null && initialParsedResponse[verb] !== undefined && Array.isArray(initialParsedResponse[verb][field])) {
      resumptionToken = undefined;
      if (initialParsedResponse[verb] !== undefined && initialParsedResponse[verb][field] !== undefined) {
        resumptionToken = getResumptionToken(initialParsedResponse, initialParsedResponse[verb][field].length)
      }
      console.log('resumptionToken next', resumptionToken);
      for await (const item of initialParsedResponse[verb][field]) {
        yield item
      }
    } else {
      resumptionToken = undefined
    }
  }
}
