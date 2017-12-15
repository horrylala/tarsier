const cargoService = require('../../services/cargo-service')
const util = require('../../utils/util');
const tcity = require("../../utils/citys.js");
Page({
  data: {
    addr: '',
    userName: '',
    mobile: '',
    weight: '',
    imageUrl: '',
    senderNum: '',
    mktId: '2222',
    provinces: [],
    province: "",
    citys: [],
    city: "",
    countys: [],
    county: '',
    value: [0, 0, 0],
    values: [0, 0, 0],
    condition: false
  },
  bindAverWeight: function (e) {
    this.setData({
      weight: e.detail.value
    })
  },
  bindSenderCount: function (e) {
    this.setData({
      senderNum: e.detail.value
    })
  },
  bindSenderPhone: function (e) {
    this.setData({
      mobile: e.detail.value
    })
  },
  bindSenderName: function (e) {
    this.setData({
      userName: e.detail.value
    })
  },
  attendShip: function () {
    this.setData({
      addr: `${this.data.province}-${this.data.city}-${this.data.country}`
    })
    const params = {
      addr: this.data.addr,
      userName: this.data.userName,
      mobile: this.data.mobile,
      weight: this.data.weight,
      imageUrl: this.data.imageUrl,
      senderNum: this.data.senderNum,
      mktId: this.data.mktId,
      averWeight: this.data.averWeight,
    }
    cargoService.saveCargoAttend(params, (res) => {
      util.log(res.data.obj)
      if (res.data.success) {
        wx.navigateBack({delta: 2})
        wx.navigateTo({
          url: `../share/share?mktId=${this.data.mktId}`,
        })
      }
    }, (res) => {
      util.log(res.data.obj)
    })
  },
  bindChange: function (e) {
    var val = e.detail.value
    var t = this.data.values;
    var cityData = this.data.cityData;

    if (val[0] != t[0]) {
      const citys = [];
      const countys = [];

      for (let i = 0; i < cityData[val[0]].sub.length; i++) {
        citys.push(cityData[val[0]].sub[i].name)
      }
      for (let i = 0; i < cityData[val[0]].sub[0].sub.length; i++) {
        countys.push(cityData[val[0]].sub[0].sub[i].name)
      }

      this.setData({
        province: this.data.provinces[val[0]],
        city: cityData[val[0]].sub[0].name,
        citys: citys,
        county: cityData[val[0]].sub[0].sub[0].name,
        countys: countys,
        values: val,
        value: [val[0], 0, 0]
      })

      return;
    }
    if (val[1] != t[1]) {
      const countys = [];

      for (let i = 0; i < cityData[val[0]].sub[val[1]].sub.length; i++) {
        countys.push(cityData[val[0]].sub[val[1]].sub[i].name)
      }

      this.setData({
        city: this.data.citys[val[1]],
        county: cityData[val[0]].sub[val[1]].sub[0].name,
        countys: countys,
        values: val,
        value: [val[0], val[1], 0]
      })
      return;
    }
    if (val[2] != t[2]) {
      this.setData({
        county: this.data.countys[val[2]],
        values: val
      })
      return;
    }
  },
  open: function () {
    this.setData({
      condition: !this.data.condition
    })
  },
  onLoad: function (option) {
    this.setData({
      mktId: option.mktId
    })
    var that = this;
    tcity.init(that);
    var cityData = that.data.cityData;
    const provinces = [];
    const citys = [];
    const countys = [];

    for (let i = 0; i < cityData.length; i++) {
      provinces.push(cityData[i].name);
    }
    for (let i = 0; i < cityData[0].sub.length; i++) {
      citys.push(cityData[0].sub[i].name)
    };
    for (let i = 0; i < cityData[0].sub[0].sub.length; i++) {
      countys.push(cityData[0].sub[0].sub[i].name)
    }

    that.setData({
      'provinces': provinces,
      'citys': citys,
      'countys': countys,
      'province': cityData[0].name,
      'city': cityData[0].sub[0].name,
      'county': cityData[0].sub[0].sub[0].name
    })
  }
})
