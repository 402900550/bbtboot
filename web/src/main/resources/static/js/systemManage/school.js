
//加载个人信息
function loadUserInfo() {
    $.ajax({
        url: 'user/loadUserInfo',
        dataType: 'html',
        type: 'post',
        data: {},
        async: false,
        success: function (result) {
            console.log(result);
            console.log(123);
          //  $('.basic').html(result);

        }
    });
};


//查询学校
function loadSchoolAll(page) {
    var json = {
        schoolName: $("#schoolName").val(),
        startNumber: page,
        sizeNumber: 5
    }
    $.ajax({
        url:"school/loadSchoolList",
        data:json,
        type:"POST",
        dataType : "html",
        success: function(data) {

            console.log(data);

            // $(".content").html(data);
        },
    })
};

//查询学校列表
function searchSchoolList() {
    $('#faSearch').on('click',function () {
        loadSchoolAll(1);
    });
}

$(function () {


});


