$(function () {
    //点击注册进行切换
    $("#to_register").click(function () {
        location.href="./register.html";
    });

    //输入完邮箱，进行验证是否存在
    $("#login_email").blur(function () {
        var email=$(this).val();
        $.ajax({
            url:'/verifyUserServlet',
            type:'GET',
            data:{"email":email},
            success:function (data) {
                if(data.userExist){
                    //已注册
                }else{
                    //未注册，错误提示
                    $("#msg").html("<p style='color: red'>该邮箱未注册</p>");
                    $("#login_email").css("border-color","#ac2925");
                }
            }
        })
    });

    //获取焦点，清空msg
    $("#login_email").focus(function () {
        $("#login_email").css("border-color","rgba(0, 0, 0, 0.4)");
        $("#msg").html("");
    })

    //输入密码框，使用正则表达式判断格式是否正确
    $("#password").blur(function () {
        //至少包含字母和数字
        var re=/^(?=.*[a-zA-Z])(?=.*\d)[^]{8,12}$/;
        var password=$(this).val();
        var flag=re.test(password);
        if(flag){
            //正确格式
            // $("#password").css("border-color","#ac2925");
        }else{
            //提示密码格式
            $("#password").css("border-color","#ac2925");
            $("#error_msg").html("包含字母和数字的8-12个字符");
        }
    });

    //获取焦点，清空re_password_msg
    $("#password").focus(function () {
        $("#password").css("border-color","rgba(0, 0, 0, 0.4)");
        $("#error_msg").html("");
    });

    //点击忘记密码，发起ajax请求，发送一个带有临时密码的邮件
    $("#forget_password").click(function () {
        var email=$("#login_email").val();
        $.ajax({
            url:"/forgetPasswordServlet",
            type:"GET",
            data:{"email":email},
            success:function (data) {
                if(data.sendSuccess){
                    //发送成功
                    alert("临时密码获取成功，请查看邮箱");
                }else{
                    //发送失败
                    location.href="../errors/500.html";
                }
            }
        })
    });

    //点击登录，先判断是否存在临时密码，没有则判断原始密码是否正确
    $("#login_btn").click(function () {
        var email=$("#login_email").val();
        var password=$("#password").val();
        $.ajax({
            url:"/loginUserServlet",
            type:"POST",
            data:{"email":email,"password":password},
            success:function (data) {
                if(!data.login_success){
                    //登录失败
                    $("#error_msg").html(data.error_msg);
                    $("#error_msg").css("color","red");
                }else{
                    //登录成功,跳转
                    if(data.isFirstLogin){
                        //true
                        location.href="../settings/info.html";//信息完善页
                    }else{
                        location.href="../index.html";//首页
                        // location.href="../settings/info.html";//信息完善页
                    }
                }
            }
        })
    })
});