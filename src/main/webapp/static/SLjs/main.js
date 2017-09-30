$(function () {
    $(".modifypwd").click(function () {
        $("#oldpassword").val("");
        $("#newpassword").val("");
        $("#aginpassword").val("");
        $("#modifypwdtip").html("");
        $("#myModal").show();
    });
    $("#modifySavePassword").bind("click",update);
    function update() {
        var pass=$("#oldpassword").val();
        var newpass=$("#newpassword").val();
        var agpass=$("#aginpassword").val();
        if (!/^[0-9|A-Za-z]{6,20}$/.test(pass)){
            $("#modifypwdtip").css("color","red").html("密码6位,最多不超过20位");
            return;
        }
        if (!/^[0-9|A-Za-z]{6,20}$/.test(newpass)){
            $("#modifypwdtip").css("color","red").html("新密码最少6位,最多不超过20位");
            return;
        }
        if (newpass!=agpass){
            $("#modifypwdtip").css("color","red").html("两次密码输入不一致，请重新输入");
            return;
        }
        $.ajax({
            url:"/backend/modifyPwd.html",
            type:"POST",
            data:{password:pass,newPassword:newpass},
            dataType:"html",
            beforeSend:function(){
                $("#modifySavePassword").unbind("click");
            },
            success:function (data) {
                if (data=="notFind"){
                    $("#modifypwdtip").css("color","red").html("修改密码失败");
                    $("#modifySavePassword").bind("click",update);
                }else if (data=="pwdEor"){
                    $("#modifypwdtip").css("color","red").html("原密码输入错误");
                    $("#modifySavePassword").bind("click",update);
                }else{
                    $("#modifypwdtip").css("color","green").html("密码修改成功，三秒后自动隐藏");
                    setTimeout(function () {
                        $("#myModal").hide();
                    },3000);
                }
            },
            error:function () {
                $("#modifypwdtip").css("color","red").html("修改密码错误，请重新提交请求");
                $("#modifySavePassword").bind("click",update);
            }
        });
    }
})