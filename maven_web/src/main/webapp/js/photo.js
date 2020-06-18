$(function () {
    //当页面加载完，获得用户头像
    $.ajax({
        url:"/getUserInfoServlet",
        type:"GET",
        data:{},
        success:function (data) {
            $("#nickName").html(data.nickname+"<span class=\"caret\"></span>");
            if(data.avatar!=null){
                //将路径改为相对路径 ../user_avatar/
                var path=data.avatar.split("user_avatar");
                $("#default_photo").attr("src","../user_avatar"+path[1]);
            }
        }
    });

    //点击搜一搜发起搜索
    $("#search_btn").click(function () {
        var city=$("#input_CityMsg").val();
        $.ajax({
            url:"/searchRoutesServlet",
            type:"GET",
            data:{"input_text":city},
            success:function (data) {
                //实现页面跳转到搜索结果展示页面
                location.href="../routes/searchResult.html";
            }
        })
    });
    
    //点击我的信息实现跳转
    $("#to_info").click(function () {
        location.href="../settings/info.html";
    });

    //点击我的头像实现跳转
    $("#to_photo").click(function () {
        location.href="../settings/photo.html";
    });

    //点击账号安全实现跳转
    $("#to_security").click(function () {
        location.href="../settings/security.html";
    });
    
    /*//点击选择图片按钮，选择本地的图片资源
    $("#select_file").click(function () {
        $("#uploadfile").click();
    });*/

});