const app = getApp()
const cargoService = require('../../services/cargo-service');
const util = require('../../utils/util');

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    deadline: '',
    mktData: {
     userCount: 0,
     freeCount: 0,
     completePercent: 0,
     deadline: '2018-01-06 11:17:11',
     marketBase: {
      mktId: 'c40dfd11-e140-11e7-b870-000000005aad',
      mktNameShow: 'test',
      dailyMinPackages: null,
      weightMin: null,
      weightMax: null,
      basePrice: 0,
      baseWeight: 0,
      groupLimit: 0,
      groupDuration: 0,
      useRequire: 0
     },
     users: [{
      imageUrl: 'https://qq.com/sdfwe/ewrs',
      userName: 'sdfds'
     },
     {
      imageUrl: 'https://qq.com/sdfwe/ewrs',
      userName: 'sdfds'
     }]
   }
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
    if (this.userInfo !== null || this.userInfo !== undefined) {
      cargoService.getCargoInfo({mktId: 'c40dfd11-e140-11e7-b870-000000005aad'}, (res) => {
        util.log(res.data.obj)
        let response = res.data.obj
        this.setData({
          mktData: res.data.obj,
          deadline: response.deadline
        })
      }, (res) => {
        util.warn(res)
      })
    }
  },
  onAttend: function () {
    wx.navigateTo({
      url: `../ship-info/ship-info?mktId=${this.data.mktData.marketBase.mktId}`,
    })
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
