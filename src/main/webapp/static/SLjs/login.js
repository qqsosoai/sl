jQuery(function ($) {
    $("#loginBtn").click(function () {
        var user = new Object();
        user.loginCode=$("#username").val();
        user.password=$("#password").val();
        if (user.loginCode==''||user.loginCode==null){
            $("#username").parent().next().html("用户名不能为空");
            $("#username").focus;
            return;
        }else{
            $("#username").parent().next().html("");
        }
        if (user.password==''||user.password==null){
            $("#password").parent().next().html("密码不能为空");
            $("#password").focus;
            return;
        }else{
            $("#password").parent().next().html("");
        }
       $.ajax({type:"POST",url:"login.html",dataType:"html",data:{"userString":JSON.stringify(user)},
       success:function (data) {
            if (data=='notFind'){//请求错误
                $(".neterror").html("登录失败，请重新登录");
            }else if (data=='usernameEor'){
                $(".neterror").html("用户名不存在，请验证后在登录");
            }else if (data=='pwdEor'){
                $(".neterror").html("密码不正确，请验证后在登录");
            }else{
                window.location.href='main.html';//登录成功跳转主页面
            }
       },
       error:function () {//ajax请求出现错误
            $(".neterror").html("请求错误请重新请求");
       },
       beforeSend:function () {//清除错误信息
           $(".neterror").html("");
       }})
    });
});