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
     deadline: '',
     marketBase: {
      mktId: '',
      mktNameShow: '',
      dailyMinPackages: null,
      weightMin: null,
      weightMax: null,
      basePrice: 0,
      baseWeight: 0,
      groupLimit: 0,
      groupDuration: 0,
      useRequire: 0
     },
     users: []
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
      cargoService.getCargoInfo({mktId: ''}, (res) => {
        util.log(res.data.obj)
        let response = res.data.obj
        this.setData({
          mktData: res.data.obj,
          deadline: response.deadline
        })
        let arr = this.data.mktData.deadline.split('-')
        let month = arr[1]
        let date = arr[2]
        if (month.charAt(0) === '0') {
          month = month.charAt(1)
        }
        if (date.charAt(0)) {
          date = date.charAt(1)
        }
        this.setData({
          deadline: `${month}月${date}日`
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
  onShare:function(){
    wx.navigateTo({
      url: `../share/share`,
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
