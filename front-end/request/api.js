//gank address
const API_BASE = "http://gank.io/api";

/**
 * 获取每日数据
 * @param {int,int,int} year, month, day 年月日
 * @returns {string}
 */
function getDayNews(year, month, day){
    return API_BASE + "/day/" + year + "/"+ month + "/" + day;
}

/**
 * 获取分类数据
 * @param {string,int,int} category, size, page 分类、每页大小、页数
 * @returns {string}
 */
function getCategoryNews(category, size, page){
    return API_BASE+ "/data/" + category + "/" + size + "/" + page ;
}

module.exports = {
    getDayNews: getDayNews,
    getCategoryNews: getCategoryNews
}