const cargoService = require('../../services/cargo-service')
const util = require('../../utils/util');
Page({
  data: {

  },
  saveQRCode: function () {
    wx.saveImageToPhotosAlbum({
      filePath: '',
      success(res) {

      }
    })
  },
  onLoad: function (options) {
    const params = {
      'path': 'pages/index?mktId=1', 'width': 430
    }
    cargoService.getQRCode({}, (res) => {
      util.log(res.data.obj)
    }, (res) => {
      util.log(res)
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
