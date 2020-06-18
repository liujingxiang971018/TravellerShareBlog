$(function () {
    //点击登录，直接跳转到登录页面
    $("#to_login").click(function () {
        location.href="./login.html";
    });


    //邮箱输入框失去焦点，判断邮箱是否已注册
    $("#re_email").blur(function () {
        var email=$("#re_email").val();
        $.ajax({
            url:'/verifyUserServlet',
            type:'GET',
            data:{"email":email},
            success:function (data) {
                if(data.userExist==false){
                    //未注册
                    $("#verify").attr("disabled",false);
                }else{
                    //已注册，错误提示
                    $("#msg").html("<p style='color: red'>该邮箱已被注册</p>");
                    $("#verify").attr("disabled",false);
                }
            }
        })
    });
    //邮箱输入框获取焦点时，清空错误输出
    $("#re_email").focus(function () {
        $("#msg").html("");
    })

    //点击获取验证码
    $("#verify").click(function () {
        var email=$("#re_email").val();
        $.ajax({
            url:'/mailSendServlet',
            type:'GET',
            data:{"email":email},
            success:function (data) {
                if(data.sendSuccess==true){
                    $("#active").attr("disabled",false);
                    alert("发送成功，请查看邮箱");
                }
            }
        })
    });

    //当验证码input获取焦点时，清空verify_code
    $("#verify_code").focus(function () {
        $("#verify_msg").html("");
    });

    //点击激活，局部刷新页面，设置密码
    $("#active").click(function () {
        var verify_code=$("#verify_code").val();
        $.ajax({
            url:"/mailActiveServlet",
            type:"GET",
            data:{"verify_code":verify_code},
            success:function (data) {
                if(data.verify_result){
                    $("#to_register").html("<div>\n" +
                        "    <label>\n" +
                        "        <span>新密码</span>\n" +
                        "        <input type=\"password\" id=\"re_password\"/>\n" +
                        "        <span id=\"re_password_msg\" class=\"verify\"></span>\n" +
                        "    </label><br>\n" +
                        "    <label>\n" +
                        "        <span>再次输入密码</span>\n" +
                        "        <input type=\"password\" id=\"re_password_again\"/>\n" +
                        "        <span id=\"re_password_again_msg\" class=\"verify\"></span>\n" +
                        "    </label>\n" +
                        "    <div id=\"to_act\">\n" +
                        "        <button type=\"button\" class=\"submit\" id=\"re_btn\" disabled>注 册</button>\n" +
                        "    </div>\n" +
                        "    <script src=\"../js/register_after.js\"></script>\n" +
                        "</div>\n");
                }else{
                    $("#verify_msg").html("验证码错误，或验证码已失效");
                }
            }
        })
    });

});
