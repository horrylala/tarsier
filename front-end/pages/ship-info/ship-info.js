const cargoService = require('../../services/cargo-service')
const util = require('../../utils/util');
Page({
  data: {
    // address: '',
    addr: 'sda',
    // senderName: '',
    userName: 'sdd',
    mobile: '11111',
    // senderPhone: '',
    weight: '123',
    imageUrl: 'https://qq.com/sdfwe/ewrs',
    // shipCount: '',
    senderNum: "123",
    mktId: 'c40dfd11-e140-11e7-b870-000000005aad',
    averWeight: ''
  },

  attendShip: function () {
    const params = this.data
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
