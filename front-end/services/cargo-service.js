const request = require('../request/request.js');
const config = require('../config')

function getTest(data, successCallback, errorCallback) {
  return request.requestData(`${config.API_BASE}/book/test`, data, successCallback, errorCallback)
}

function saveCargoAttend(data, successCallback, errorCallback) {
  return request.requestData(`${config.API_BASE}/book/test`, data, successCallback, errorCallback);
}

function getCargoInfo(data, successCallback, errorCallback) {
  return request.requestData(`${config.API_BASE}/market/queryMarketBaseInfo`, data, successCallback, errorCallback);
}

function getQRCode(data, successCallback, errorCallback) {
  return request.requestData(`${config.API_BASE}/book/test`, data, successCallback, errorCallback);
}


module.exports = {
  getTest: getTest,
  saveCargoAttend: saveCargoAttend,
  getCargoInfo: getCargoInfo,
  getQRCode: getQRCode
};
