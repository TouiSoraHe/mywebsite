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

$('body').append('<script src="/nprogress/js/nprogress.js"></script>');
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
    SimpleModal("服务器开了一点小差", "<div>错误代码:" + code + ((message == null || message == "") ? "" : "<br>错误信息:" + message) + "</div>");
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