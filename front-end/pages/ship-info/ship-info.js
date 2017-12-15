const cargoService = require('../../services/cargo-service')
const util = require('../../utils/util');
Page({
  data: {
    address: '',
    senderName: '',
    senderPhone: '',
    shipCount: '',
    averWeight: ''
  },
  attendShip: function () {
    const params = {

    }
    cargoService.saveCargoAttend(params, (res) => {
      util.log(res.data.obj)
    }, (res) => {
      util.log(res.data.obj)
    })
  },
  onLoad: function (options) {

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
