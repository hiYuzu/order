//格式化时间
function formatTime(_time) {
    var year = _time.getFullYear();
    var month = _time.getMonth() + 1 < 10 ? "0" + (_time.getMonth() + 1) : _time.getMonth() + 1;
    var day = _time.getDate() < 10 ? "0" + _time.getDate() : _time.getDate();
    return year + "-" + month + "-" + day;
}

//去除table.cache中的空数据
function formatTableCacheData(tableCacheData){
    var tableCacheDataNew = new Array();
    if(tableCacheData != null && tableCacheData.length>0){
        for (var i=0;i<tableCacheData.length;i++){
            if(tableCacheData[i] != null && tableCacheData[i].LAY_TABLE_INDEX != null ){
                tableCacheDataNew.push(tableCacheData[i]);
            }
        }
    }
    return tableCacheDataNew;
}