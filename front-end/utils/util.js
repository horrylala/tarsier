const hasConsole = typeof console !== 'undefined'

function log(msg) {
  if (hasConsole) {
    console.log(`[wx log]: ${msg}`)
  }
}

function warn(msg) {
  if (hasConsole) {
    console.error(`[wx warn]: ${msg}`)
  }
}

function tip(msg) {
  if (hasConsole) {
    console.error(`[wx warn]: ${msg}`)
  }
}

function isFunction(val) {
  return typeof val === 'function'
}

function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()
  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function isFunction(val){
  return typeof val === 'function'
}


function formatDate(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  return [year, month, day].map(formatNumber).join('-')
}

// 手机校验
function phoneVerify(phone) {
  return /^1[34578]\d{9}$/.test(this.removeAllEmpty(phone))
}

module.exports = {
  formatTime: formatTime,
  isFunction: isFunction,
  formatDate: formatDate,
  phoneVerify: phoneVerify,
  log: log,
  warn: warn,
  tip: tip
}
