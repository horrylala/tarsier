const request = require('../../request/request.js');
const util =  require('../../utils/util.js');
Page({
  data: {
    array: {}
  },
  onLoad: function (options) {
    request.getTest({id: '123', bookName: 'sfwer'}, (res) => {
      this.setData({
        array: res.data.obj
      })
      util.log(res.data.obj)
    }, (res) => {
      util.warn(res)
    })
  },
  onReady: function () {

  },
  onShow: function () {

  },
  onHide: function () {

  },
  onUnload: function () {

  },
  onPullDownRefresh: function () {

  },
  onReachBottom: function () {

  },
  onShareAppMessage: function () {

  }
})
