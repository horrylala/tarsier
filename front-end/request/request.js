const util = require('../utils/util')
const config = require('../config')

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

function getTest(data, successCallback, errorCallback) {
  return requestData(`${config.API_BASE}/book/test`, data, successCallback, errorCallback)
}

// requestXCX (增加GET/POST等请求方法和Content-Type设置)  == jt add
function requestXCX(url, data, method, contentType, successCallback, errorCallback, completeCallback) {
  wx.request({
    url: url,
    data: data,
    // login: true,
    method: method, // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
    // header: {}, // 设置请求的 header
    header: { "Content-Type": contentType, 'Cookie': getApp().globalData.sessionId },
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

function getVoteData(data, successCallback, errorCallback, completeCallback) {
  return requestData(config.voteListUrl, data, successCallback, errorCallback, completeCallback);
}

//Cos signature
function getCosAuth(successCallback, errorCallback, completeCallback) {
  return requestData(config.cosAuthUrl, {}, successCallback, errorCallback, completeCallback);
}

//save
function uploadCos(data, successCallback, errorCallback, completeCallback) {
  wx.uploadFile({
    url: config.cosUploadUrl + '/' + data.fileName,
    filePath: data.filePath,
    header: {
      'Authorization': data.signature
    },
    name: 'filecontent',
    formData: {
      op: 'upload'
    },
    success: function (res) {
      // success
      if (res.statusCode == 200)
        return util.isFunction(successCallback) && successCallback(res);
      else
        return util.isFunction(errorCallback) && errorCallback(res);
    },
    fail: function (res) {
      // fail
      return util.isFunction(errorCallback) && errorCallback(res);
    },
    complete: function (res) {
      // complete
      return util.isFunction(completeCallback) && completeCallback(res);
    }
  })
}

function savePhotos(data, successCallback, errorCallback, completeCallback) {
  wx.uploadFile({
    url: config.savePhotoUrl,
    filePath: data.photoPaths,
    name: 'file',
    header: { 'Content-Type': 'multipart/form-data' }, // 设置请求的 header
    formData: {
      jobcard: data.jobcard,
      username: data.username,
      content: data.content,
      activityId: data.activityId,
    }, // HTTP 请求中其他额外的 form data
    success: function (res) {
      // success
      if (res.statusCode == 200)
        return util.isFunction(successCallback) && successCallback(res);
      else
        return util.isFunction(errorCallback) && errorCallback(res);
    },
    fail: function (res) {
      // fail
      return util.isFunction(errorCallback) && errorCallback(res);
    },
    complete: function (res) {
      // complete
      return util.isFunction(completeCallback) && completeCallback(res);
    }
  })
}

// 培训申请
function saveTrain(data, successCallback, errorCallback, completeCallback) {
  wx.uploadFile({
    url: config.saveTrainUrl,
    filePath: data.photoPaths,
    name: 'file',
    header: { 'Content-Type': 'mutipart/form-data' }, // 设置请求的 header
    formData: {
      trainTitle: data.trainTitle,
      trainContent: data.trainContent,
      startTime: data.startTime,//开始时间
      endTime: data.endTime,//结束时间
      place: data.trainPlace,
      applyUsername: data.applyUsername,
      mark: data.mark,
      applyUser: data.applyUser,
      department: data.department
    }, // HTTP 请求中其他额外的 form data
    success: function (res) {
      // success
      if (res.statusCode == 200)
        return util.isFunction(successCallback) && successCallback(res);
      else
        return util.isFunction(errorCallback) && errorCallback(res);
    },
    fail: function (res) {
      // fail
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

// 上传书籍
function saveBook(data, successCallback, errorCallback, completeCallback) {
  wx.uploadFile({
    url: config.saveBookUrl,
    filePath: data.photoUrl,
    name: 'file',
    header: { 'Content-Type': 'mutipart/form-data' }, // 设置请求的 header
    formData: {
      bookName: data.bookName,
      bookId: data.bookId,
      bookAuthor: data.bookAuthor,
      ownerPhone: data.ownerPhone,
      remark: data.remark,
      ownerName: data.ownerName,
      ownerJobCard: data.ownerJobCard,
      borrowDeadline: data.borrowDeadline,
    }, // HTTP 请求中其他额外的 form data
    success: function (res) {
      // success
      if (res.statusCode == 200)
        return util.isFunction(successCallback) && successCallback(res);
      else
        return util.isFunction(errorCallback) && errorCallback(res);
    },
    fail: function (res) {
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


function queryUser(data, successCallback, errorCallback, completeCallback) {
  return requestData(config.queryUser, data, successCallback, errorCallback, completeCallback);
}



// index

function loginUser(data, successCallback, errorCallback, completeCallback) {
  let requestTask = wx.request({
    url: config.loginUser,
    data: data,
    // login: true,
    method: 'POST', // OPTIONS, GET, HHEAD, POST, PUT, DELETE, TRACE, CONNECT
    // header: {}, // 设置请求的 header
    header: { "Content-Type": "application/json", 'Cookie': getApp().globalData.sessionId },
    success: function (res) {
      // success
      if (res.statusCode == 200)
        return util.isFunction(successCallback) && successCallback(res);
      else
        return util.isFunction(errorCallback) && errorCallback(res);
    },
    fail: function (res) {
      // fail
    },
    complete: function (res) {
      // complete
      return util.isFunction(completeCallback) && completeCallback(res);
    }
  })
  return requestTask
}


function myEnrollList(data, successCallback, errorCallback, completeCallback) {
  return requestXCX(config.myEnrollListUrl, data, 'POST', "application/json", successCallback, errorCallback, completeCallback);
}


module.exports = {
  getVoteData: getVoteData,
  getCosAuth: getCosAuth,
  getTest: getTest
}
