function showToast(obj) {
  /*
  显示toast提示
  */
  if (typeof obj == 'object' && obj.title) {
    if (!obj.duration || typeof obj.duration != 'number') { obj.duration = 1500; }//默认1.5s后消失
    if (obj.icon == 'info') { obj.icon = '/images/msg_info.png'; }
    else if (obj.icon == 'error') { obj.icon = '/images/msg_error.png'; }
    var that = getCurrentPages()[getCurrentPages().length - 1];//获取当前page实例
    obj.isShow = true;//开启toast
    if (obj.duration < 10000) {
      setTimeout(function () {
        obj.isShow = false;
        obj.cb && typeof obj.cb == 'function' && obj.cb();//如果有成功的回调则执行
        that.setData({
          'showToast.isShow': obj.isShow
        });
      }, obj.duration);
    }
    that.setData({
      showToast: obj
    });
  } else if (typeof obj == 'string') {
    var that = getCurrentPages()[getCurrentPages().length - 1];//获取当前page实例
    var objData = { title: obj, duration: 1000, isShow: true };
    setTimeout(function () {
      objData.isShow = false;
      that.setData({
        showToast: objData
      });
    }, objData.duration);
    that.setData({
      showToast: objData
    });
  } else {
    console.log('showToast fail:请确保传入的是对象并且title必填');
  }
}
/**
 *手动关闭toast提示
 */
function hideToast() {
  var that = getCurrentPages()[getCurrentPages().length - 1];//获取当前page实例
  if (that.data.showToast) {
    that.setData({
      'showToast.isShow': false
    });
  }
}
module.exports = {
  showToast: showToast,
  hideToast: hideToast
}