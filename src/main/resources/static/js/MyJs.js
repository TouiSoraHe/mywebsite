//格式化时间
function FormatDateTime(time, regex) {
    var myDate = new Date(time);
    var year = myDate.getFullYear();
    var month = ("0" + (myDate.getMonth() + 1)).slice(-2);
    var day = ("0" + myDate.getDate()).slice(-2);
    var h = ("0" + myDate.getHours()).slice(-2);
    var m = ("0" + myDate.getMinutes()).slice(-2);
    var s = ("0" + myDate.getSeconds()).slice(-2);
    var mi = ("00" + myDate.getMilliseconds()).slice(-3);
    return regex.replace("yyyy",year).replace("yy",year%100).replace("MM",month).replace("dd",day)
        .replace("hh",h>12?h%12:h).replace("HH",h).replace("mm",m).replace("ss",s).replace("mi",mi);
};

function RequestAsyn(url,method,successEvent,errorContent,errorModal,form) {
    var request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if(request.readyState === 4) {
            if(request.status === 200){
                var text = request.responseText;
                if(text==null || text == "") {
                    fail(-1,null,errorContent,errorModal);
                    return;
                }
                else{
                    var item = $.parseJSON(text);
                    if(item.code!=200){
                        fail(item.code,item.message,errorContent,errorModal);
                        return;
                    }
                }
                successEvent(item.data);
            }
            else {
                fail(request.status,null,errorContent,errorModal);
            }
        }
    }
    request.open(method,url);
    if(form!=null && form instanceof FormData) {
        request.send(form);
    }
    else {
        request.send();
    }
}

function fail(code,message,errorContent,errorModal) {
    if(errorContent!=null && errorModal!=null){
        errorContent.html("错误代码:"+code+((message==null || message=="")?"":"<br>错误信息:"+message));
        errorModal.modal('show');
    }
    else{
        console.log("错误代码:"+code);
        console.log("错误信息:"+message);
    }
}