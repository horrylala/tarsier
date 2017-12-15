const cargoService = require('../../services/cargo-service')
const util = require('../../utils/util');
Page({
  data: {
    QRImageUrl: ''
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
    cargoService.getQRCode(params, (res) => {
      const base64 = wx.arrayBufferToBase64(res.data.obj);
      this.setData({
        QRImageUrl: `data:image/png;base64,${base64}`
      })
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
