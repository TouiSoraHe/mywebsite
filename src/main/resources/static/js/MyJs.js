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
    return regex.replace("yyyy", year).replace("yy", year % 100).replace("MM", month).replace("dd", day)
        .replace("hh", h > 12 ? h % 12 : h).replace("HH", h).replace("mm", m).replace("ss", s).replace("mi", mi);
};

if(typeof (NProgress) === "undefined"){
    console.log("引入NProgress");
    $('body').append('<script src="/nprogress/js/nprogress.js"></script>');
}

NProgress.configure({minimum: 0.4});

function RequestAsyn(url, method, successEvent, form) {
    NProgress.done();
    NProgress.start();
    $.ajax({
        url: url,
        dataType: "json",
        type: method,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data.code != 200) {
                fail(data.code, data.message);
            }
            else if (successEvent != null && successEvent instanceof Function) {
                successEvent(data.data);
            }
        },
        error: function (jqXHR, textStatus) {
            fail(jqXHR.status, textStatus);
        },
        data: form,
        complete: function () {
            NProgress.done();
        }
    });
}

function fail(code, message) {
    if(code === null || code === "" || code === undefined){
        code = 10086;
    }
    if(message === null || message === "" || message === undefined){
        message = "未知错误";
    }
    SimpleModal("服务器开了一点小差", '<div>错误代码:'+code+'<br>错误信息:'+message+'</div>');
}

/**
 * 简单模态框
 * @param title 模态框的标题,String类型
 * @param content 模态框的内容
 * @param okEvent 点击确认按钮时的事件,参数:content(jquery对象)
 * @constructor
 */
function SimpleModal(title, content, okEvent) {
    var modalID = 'modalID' + Date.now();
    var titleID = 'titleID' + Date.now();
    var modal = $(
        '<div class="modal fade" id="modalID" tabindex="-1" role="dialog" aria-labelledby="titleID" aria-hidden="true">\n' +
        '    <div class="modal-dialog">\n' +
        '        <div class="modal-content">\n' +
        '            <div class="modal-header">\n' +
        '                <button type="button" class="close" data-dismiss="modal"\n' +
        '                        aria-hidden="true">×\n' +
        '                </button>\n' +
        '                <h4 class="modal-title" id="titleID">\n' +
        '                    标题\n' +
        '                </h4>\n' +
        '            </div>\n' +
        '            <div class="modal-body">\n' +
        '            </div>\n' +
        '            <div class="modal-footer">\n' +
        '                <button type="button" class="btn btn-default"\n' +
        '                        data-dismiss="modal">确认\n' +
        '                </button>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>');
    $('body').append(modal);
    modal.attr('id', modalID);
    modal.attr('aria-labelledby', titleID);
    var titleDom = modal.find('.modal-title');
    titleDom.attr('id', titleID);
    var contentDom = modal.find('.modal-body');
    var buttonDom = modal.find('.modal-footer > button');
    titleDom.text(title);
    if (!(content instanceof jQuery)) {
        content = $(content);
    }
    contentDom.append(content);
    buttonDom.on('click', function () {
        if (okEvent != null && okEvent instanceof Function) {
            okEvent(content);
        }
    });
    modal.on('hidden.bs.modal', function () {
        modal.remove();
    });
    modal.modal('show');
}

$('body').append('<div id="DragAndDropBox" style="position:fixed;left: 0px;top: 30%;z-index: 100000;"></div>');
var oDiv=document.getElementById('DragAndDropBox');
oDiv.onmousedown=function(ev){
    var disX=ev.clientX-oDiv.offsetLeft;
    var disY=ev.clientY-oDiv.offsetTop;
    document.onmousemove=function(ev){
        var l=ev.clientX-disX;
        var t=ev.clientY-disY;

        oDiv.style.left=l+'px';
        oDiv.style.top=t+'px';
    };
    document.onmouseup=function(){
        document.onmousemove=null;
        document.onmouseup=null
    }
};

if(typeof skPlayer === 'undefined'){
    console.log('引入skPlayer');
    $('body').append('<script src="/skPlayer-3.0/dist/skPlayer.min.js"></script>');
}
var mp3Player = new Mp3Player();

function Mp3Player() {
    this.player = null;
}

Mp3Player.prototype.open = function (songListName) {
    this.close();
    var that = this;
    RequestAsyn("/Audio/"+songListName,"POST",function (data) {
        $("#DragAndDropBox").append('<div id="skPlayerHidden" class="text-center" style="width: 50px;background-color: #d94240;color: white;cursor: pointer;">◁</div>');
        $("#skPlayerHidden").on('click',function () {
            var isHidden = $("#skPlayer").attr("hidden") !== undefined;
            if(isHidden){
                $("#skPlayer").attr("hidden",false);
                $("#skPlayerHidden").text('◁');
            }else{
                $("#skPlayerHidden").text('▷');
                $("#skPlayer").attr("hidden",true);
                $("#DragAndDropBox").css("left","0px");
            }
        });
        $("#DragAndDropBox").append('<div id="skPlayer"></div>');
        that.player = new skPlayer({
            music: {
                type: 'file',
                source:data.source
            }
        });
    },null);
};
Mp3Player.prototype.close = function () {
    if(this.player!=null){
        this.player.destroy();
    }
    var skPlayer = $("#skPlayer");
    if(skPlayer!=null){
        skPlayer.remove();
    }
    var skPlayerHidden = $("#skPlayerHidden");
    if(skPlayerHidden!=null){
        skPlayerHidden.remove();
    }
};