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

NProgress.configure({ minimum: 0.4 });

function RequestAsyn(url,method,successEvent,errorContent,errorModal,form) {
    NProgress.done();
    NProgress.start();
    $.ajax({
        url:url,
        dataType:"json",
        type:method,
        success:function (data) {
            if(data.code!=200){
                fail(data.code,data.message,errorContent,errorModal);
            }
            else
            {
                successEvent(data.data);
            }
        },
        error:function (jqXHR, textStatus) {fail(jqXHR.status,textStatus,errorContent,errorModal);},
        data:GetKeyAndValue(form),
        complete:function () {
            NProgress.done();
        }
    });
}

function GetKeyAndValue(m) {
    if(m==null)
    {
        return "";
    }
    var ret = "";
    var count = 0;
    for(var item of m.entries())
    {
        if(count!=0)
        {
            ret+="&";
        }
        ret+=(item[0]+"="+item[1]);
        count++;
    }
    return ret;
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