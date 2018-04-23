//跳转页面
function forwardUrl() {

    $(document).on('click', '.addTab', function () {

        var url = $(this).attr('data-url');
        var type = $(this).attr('data-type');
        toUrl(url, type);
    });

}

function toUrl(url, type) {
    $.get('/bbt/toSpecialAllPages', {url: url, type: type}, function (result) {
        $('.content-wrapper').empty();
        $('.content-wrapper').html(result);
        clearInterval(flush);
        clearInterval(flush2);

        localStorage.setItem("oldUrl", url);
        localStorage.setItem("oldType", type);
    })
}


//确认修改密码
function modifyPassword() {
    $('#modifyPasswordModal').find('.btn-primary').on('click', function () {


        $('#modifyPasswordModal').find('form').ajaxSubmit(function (result) {
            if (result == 'success') {
                alert("修改成功,请重新登录!");
                $('#modifyPasswordModal').modal('hide');
                location.href = "/bbt/doLogout";
            } else {
                alert(result);
            }


        });
    });


    $('#modifyPasswordModal').on('hide.bs.modal', function () {
        $(this).find('.form-group').removeClass('has-error');
        $(this).find('.form-group input,select,textarea').val('');
    })
}

function forwardToOpertion() {

    $(document).off('click', 'li[name="toOpertions"]').on('click', 'li[name="toOpertions"]', function () {
        var url = 'operationsManagement/maintainStatistics';
        var type = 'operations';
        toUrl(url, type);
    })

}

function firstRun() {
    // var url = localStorage.getItem("oldUrl");
    // var type = localStorage.getItem("oldType");
    // if (url) {
    //     toUrl(url, type);
    // } else {
    //
    // }
    $('#index').trigger('click');
}

function doLogout() {
    $(document).off('click', '#logout').on('click', "#logout", function (e) {
        e.preventDefault();
        localStorage.clear();
        location.href = "/bbt/doLogout";

    });




}

//这是用户信息修改结束
$(function () {
    forwardUrl();
    modifyPassword();
    forwardToOpertion();
    firstRun();
    doLogout();
});























