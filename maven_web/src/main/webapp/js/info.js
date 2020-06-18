$(function () {
    //当页面加载完，获得用户昵称
    $.ajax({
        url:"/getUserInfoServlet",
        type:"GET",
        data:{},
        success:function (data) {
            $("#nickName").html(data.nickname+"<span class=\"caret\"></span>");
            $("#input_nickname").val(data.nickname);
            //选择性别
            if(data.sex=="1"){
                //男性
                $("#male").attr("checked",true);
            }else if(data.sex=="0"){
                //女性
                $("#female").attr("checked",true);
            }else if(data.sex=="2"){
                //保密
                $("#secrecy").attr("checked",true);
            }
            $("#input_locationCity").val(data.city);
            $("#input_birthday").val(data.birthday);
            $("#input_intro").val(data.profile);
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

    //点击保存按钮，更新数据库
    $("#submit_btn").click(function () {
        //获取各输入框信息
        var nickname=$("#input_nickname").val();

        var radio=document.getElementsByName("gender");
        var sex=null;   //  selectvalue为radio中选中的值
        for(var i=0;i<radio.length;i++) {
            if (radio[i].checked == true) {
                sex = radio[i].value;
                break;
            }
        }
        var city=$("#input_locationCity").val();
        var birthday=$("#input_birthday").val();
        var profile=$("#input_intro").val();

        $.ajax({
            url:"/updateUserInfoServlet",
            type:"POST",
            data:{"user_nickname":nickname,"user_sex":sex,"user_city":city,"user_birthday":birthday,"user_profile":profile},
            success:function (data) {
                //重新加载本页面
                location.href="../settings/info.html";
            },
            error:function () {
                //跳转到500.html
                location.href="../errors/500.html";
            }
        })
    })
});