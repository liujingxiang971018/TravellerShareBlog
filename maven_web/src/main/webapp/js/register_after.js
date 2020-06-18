$(function () {
    //进行正则表达式验证密码
    $("#re_password").blur(function () {
        //至少包含字母和数字
        var re=/^(?=.*[a-zA-Z])(?=.*\d)[^]{8,12}$/;
        var password=$(this).val();
        var flag=re.test(password);
        if(flag){
            //正确格式
            // $("#re_password").css("border-color","#ac2925");
        }else{
            //提示密码格式
            $("#re_password").css("border-color","#ac2925");
            $("#re_password_msg").html("包含字母和数字的8-12个字符");
        }
    });

    //当获取焦点时，清空错误输入框
    $("#re_password").focus(function () {
        $("#re_password").css("border-color","rgba(0, 0, 0, 0.4)");
        $("#re_password_msg").html("");
    });

    //判断两次密码是否一致
    $("#re_password_again").blur(function () {
        var password1=$("#re_password").val();
        var password2=$("#re_password_again").val();
        if(password1!=password2){
            //提示密码不相同
            $("#re_password_again").css("border-color","#ac2925");
            $("#re_password_again_msg").html("两次密码输入不一致");
        }else{
            $("#re_btn").attr("disabled",false);
        }
    });

    //当获取焦点时，清空错误输入框
    $("#re_password_again").focus(function () {
        $("#re_password_again").css("border-color","rgba(0, 0, 0, 0.4)");
        $("#re_password_again_msg").html("");
    });

    //点击注册，创建用户
    $("#re_btn").click(function () {
        var password=$("#re_password").val();
        $.ajax({
            url:"/registerUserServlet",
            type:"GET",
            data:{"password":password},
            success:function (data) {
                if(data.createUserSuccess){
                    location.href="./login.html";
                }else {
                    location.href="../errors/500.html";
                }
            }
        })
    });
})