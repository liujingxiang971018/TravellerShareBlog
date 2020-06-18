$(function () {
    //当页面加载完，获得用户昵称
    $.ajax({
        url:"/getUserInfoServlet",
        type:"GET",
        data:{},
        success:function (data) {
            $("#nickName").html(data.nickname+"<span class=\"caret\"></span>");
            $("#user_email").html(data.email);
        }
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

    //点击修改密码，弹出div
    $("#change_password").click(function () {
        $(".change").removeAttr("style");
    })

    //输入密码框，使用正则表达式判断格式是否正确
    $("#init_password").blur(function () {
        //至少包含字母和数字
        var re=/^(?=.*[a-zA-Z])(?=.*\d)[^]{8,12}$/;
        var password=$(this).val();
        var flag=re.test(password);
        if(flag){
            //正确格式,判断密码是否正确
            $.ajax({
                url:"/getUserInfoServlet",
                type:"POST",
                data:{},
                success:function (data) {
                    if(password!=data.password){
                        //不是原始密码，弹出错误
                        $("#init_password").css("border-color","#ac2925");
                        $("#init_password_msg").html("与原始密码不符");
                    }
                }
            })
        }else{
            //提示密码格式
            $("#init_password").css("border-color","#ac2925");
            $("#init_password_msg").html("包含字母和数字的8-12个字符");
        }
    });

    //获取焦点，清空init_password_msg
    $("#init_password").focus(function () {
        $("#init_password").css("border-color","rgba(0, 0, 0, 0.4)");
        $("#init_password_msg").html("");
    });

    //输入密码框，使用正则表达式判断格式是否正确
    $("#new_password").blur(function () {
        //至少包含字母和数字
        var re=/^(?=.*[a-zA-Z])(?=.*\d)[^]{8,12}$/;
        var password=$(this).val();
        var flag=re.test(password);
        if(flag){
            //正确格式
            // $("#password").css("border-color","#ac2925");
        }else{
            //提示密码格式
            $("#new_password").css("border-color","#ac2925");
            $("#new_password_msg").html("包含字母和数字的8-12个字符");
        }
    });

    //获取焦点，清空init_password_msg
    $("#new_password").focus(function () {
        $("#new_password").css("border-color","rgba(0, 0, 0, 0.4)");
        $("#new_password_msg").html("");
    });

    //点击申请注销的超链接，弹出提示框，并发起请求
    $("#user_del").click(function () {
        var flag = confirm("注销账号将清除账号所有的信息，确定是本人操作？");
        if(flag){
            //true
            $.ajax({
                url:"/deleteUserServlet",
                type:"GET",
                data:{},
                success:function (data) {
                    if(data.isDeleteSuccess){
                        //true,删除成功  跳转到注册界面
                        location.href="../login/register.html";
                    }else{
                        location.href="../errors/500.html";
                    }
                }
            })
        }
    });
    
    //点击保存，更新密码
    $("#submit_btn").click(function () {
        var password=$("#new_password").val();
        $.ajax({
            url:"/updateUserInfoServlet",
            type:"POST",
            data:{"user_password":password},
            success:function (data) {
                location.href="./settings/security.html";
            }
        })
    })
});