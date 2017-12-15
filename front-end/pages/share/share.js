const cargoService = require('../../services/cargo-service')
const util = require('../../utils/util');
Page({
  data: {
    QRImageUrl: ''
  },
  saveQRCode: function () {
    wx.saveImageToPhotosAlbum({
      filePath: `http://10.2.4.33/codeImg/${this.data.QRImageUrl}`,
      success(res) {
      }
    })
  },
  onLoad: function (options) {
    const params = {
      'path': 'pages/index?mktId=1', 'width': 430
    }
    cargoService.getQRCode(params, (res) => {
      this.setData({
        QRImageUrl: res.data.obj
      })
      util.log(res.data.obj)
    }, (res) => {
      util.log(res)
    })
  }
})
