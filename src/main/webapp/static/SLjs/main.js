$(function () {
    $(".modifypwd").click(function () {
        $("#oldpassword").val("");
        $("#newpassword").val("");
        $("#aginpassword").val("");
        $("#aginpassword").next().html("");
        $("#myModal").show();
    });
    $("#modifySavePassword").click(update);
    function update() {
        var pass=$("#oldpassword").val();
        var newpass=$("#newpassword").val();
        var agpass=$("#aginpassword").val();
        if (!/^[0-9|A-Za-z]{6,20}$/.test(pass)){
            $("#aginpassword").next().html("密码6位,最多不超过20位");
            return;
        }
        if (/^[0-9|A-Za-z]{6,20}$/.test(newpass)){
            $("#aginpassword").next().html("新密码最少6位,最多不超过20位");
            return;
        }
        if (newpass!=agpass){
            $("#aginpassword").next().html("两次密码输入不一致，请重新输入");
        }
        $.ajax({
            url:"",
            type:"POST",
            dataType:"html",
            beforeSend:function(){
                
            },
            success:function (data) {
                if (data=="notFind"){
                    $("#aginpassword").next().html("修改密码失败");
                }else if (data=="pwdEor"){
                    $("#aginpassword").next().html("原密码输入错误");
                }else{
                    $("#myModal").hide();
                }
            },
            error:function () {
                
            }
        });
    }
})