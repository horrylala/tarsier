const util = require('../utils/util')
// requestData
function requestData(url, data, successCallback, errorCallback, completeCallback) {
  wx.request({
    url: url,
    data: data,
    // login: true,
    method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
    // header: {}, // 设置请求的 header
    header: { "Content-Type": "application/json"},
    success: function (res) {
      // success
      if (res.statusCode == 200)
        return util.isFunction(successCallback) && successCallback(res);
      else
        return util.isFunction(errorCallback) && errorCallback(res);
    },
    fail: function (res) {
      // fail
      wx.showToast({
        title: '请求服务器失败',
        icon: 'loading',
        duration: config.toastShort
      })
      return util.isFunction(errorCallback) && errorCallback(res);
    },
    complete: function (res) {
      // complete
      return util.isFunction(completeCallback) && completeCallback(res);
    }
  })
}

module.exports = {
  requestData: requestData
}
