$(".adduser").click(function () {//添加模式窗口
    $('#add_formtip').html('');
    $("#addUserDiv").modal("show");
});
//将添加用户所有信息清空
$('.addusercancel').click(function(){
    $("#add_formtip").html('');
    $("#a_idPic").html('');
    $("#a_bankPic").html('');
    $("#selectrole").val('');
    $("#selectusertype").val('');
    $("#selectusertype").html('<option value=\"\" selected=\"selected\">--请选择--</option>');
    $("#a_logincode").val('');
    $("#a_username").val('');
    $("#selectcardtype").val('');
    $("#a_idcard").val('');
    $("#a_mobile").val('');
    $("#a_email").val('');
    $("#a_postCode").val('');
    $("#a_bankname").val('');
    $("#a_bankaccount").val('');
    $("#a_accountholder").val('');
    $("#a_useraddress").val('');
});
//选择角色获取会员类型
$("#selectrole").change(function () {
    var $userType=$("#selectusertype").html("");
    $userType.append('<option value="">--请选择--</option>');
    var user=$("#selectrole").val();
    if (user!=2){
        return;
    }
    $.post("/backend/loadUserTypeList.html",{},function (data) {
        $(data).each(function () {
            $userType.append("<option value='"+this.valueId+"'>"+this.valueName+"</option>");
        });
    },"json");
});
//验证用户名是否存在
$("#a_logincode").blur(function () {
    var login=$(this).val();
    if (login==''){
        $("#add_formtip").css("color","red").html("<li>用户名不能为空</li>");
        return;
    }
    $.post("/backend/logincodeisexit.html",{loginCode:login,id:"-1"},function (data) {
        if (data=='eor'){
            $("#add_formtip").css("color","red").html("<li>请求错误</li>");
        }else if(data=="exist"){
            $("#add_formtip").css("color","red").html("<li>用户名已存在，请重新输入</li>");
            $("#add_formtip").attr("key","1");
        }else{
            $("#add_formtip").css("color","green").html("<li>用户名可以使用</li>");
            $("#add_formtip").attr("key","0");
        }
    },"html");
});
$("#a_email").blur(function(){
    var flag = checkEmail($("#a_email").val());
    if(flag == false){
        $("#add_formtip").css("color","red");
        $("#add_formtip").html("<li>email格式不正确</li>");
        $("#add_formtip").attr("email","1");
    }else{
        $("#add_formtip").html("");
        $("#add_formtip").attr("email","0");
    }
});
$("#selectrole").change(function () {//获取角色给隐藏域
    $("#selectrolename").val($(this).find("option:selected").html());
});
$("#selectusertype").change(function () {//获取会员类型给隐藏域
    $("#selectusertypename").val($(this).find("option:selected").html());
});
$("#selectcardtype").change(function () {//获取证件类型为隐藏域赋值
    $("#selectcardtypename").val($(this).find("option:selected").html());
});

//添加用户信息验证
function addUserFunction(){
    $("#add_formtip").html("");
    var result = true;
    if($("#selectrole").val() == ""){
        $("#add_formtip").css("color","red");
        $("#add_formtip").append("<li>对不起，角色不能为空。</li>");
        result = false;
    }
    if($.trim($("#a_logincode").val()) == "" || $("#a_logincode").val() == null){
        $("#add_formtip").css("color","red");
        $("#add_formtip").append("<li>对不起，用户名不能为空。</li>");
        result = false;
    }else{
        if($("#add_formtip").attr("key") == "1"){
            $("#add_formtip").append("<li>对不起，该用户名已存在。</li>");
            result = false;
        }
    }
    if($.trim($("#a_username").val()) == "" || $("#a_username").val() == null){
        $("#add_formtip").css("color","red");
        $("#add_formtip").append("<li>对不起，真实姓名不能为空。</li>");
        result = false;
    }
    if($("#selectcardtype").val() == ""){
        $("#add_formtip").css("color","red");
        $("#add_formtip").append("<li>对不起，证件类型不能为空。</li>");
        result = false;
    }
    if($.trim($("#a_idcard").val()) == "" || $("#a_idcard").val() == null){
        $("#add_formtip").css("color","red");
        $("#add_formtip").append("<li>对不起，证件号码不能为空。</li>");
        result = false;
    }else{
        if($("#a_idcard").val().length < 6){
            $("#add_formtip").css("color","red");
            $("#add_formtip").append("<li>对不起，证件号码长度必须超过6位。</li>");
            result = false;
        }
    }
    if($.trim($("#a_mobile").val()) == "" || $("#a_mobile").val() == null){
        $("#add_formtip").css("color","red");
        $("#add_formtip").append("<li>对不起，联系电话不能为空。</li>");
        result = false;
    }
    if($.trim($("#a_bankname").val()) == "" || $("#a_bankname").val() == null){
        $("#add_formtip").css("color","red");
        $("#add_formtip").append("<li>对不起，开户行不能为空。</li>");
        result = false;
    }
    if($.trim($("#a_bankaccount").val()) == "" || $("#a_bankaccount").val() == null){
        $("#add_formtip").css("color","red");
        $("#add_formtip").append("<li>对不起，开户卡号不能为空。</li>");
        result = false;
    }
    if($.trim($("#a_accountholder").val()) == "" || $("#a_accountholder").val() == null){
        $("#add_formtip").css("color","red");
        $("#add_formtip").append("<li>对不起，开户人不能为空。</li>");
        result = false;
    }
    if($.trim($("#a_email").val()) != "" && $("#a_email").val() != null && $("#add_formtip").attr("email") == "1"){
        $("#add_formtip").css("color","red");
        $("#add_formtip").append("<li>email格式不正确</li>");
        result = false;
    }
    return result;
}
//验证email方法
function checkEmail(str){
    var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(str == null || str == "" || reg.test(str))
        return true;
    else
        return false;
}

$("#a_uploadbtnID").click(function () {
    fileUploadAjax('0','a_fileInputID','button',
        'a_idPic','/backend/upload.html','a_fileInputIDPath');
});
//'0','a_fileInputID','a_uploadbtnID','a_idPic','a_fileInputIDPath'
//type：上传文件类型(修改，新增);file：文件上传ID,btn：上传按钮,showImg：图片显示区,url：请求地址,hidden：存放图片路径隐藏域ID
function fileUploadAjax(type,file,btn,showImg,url,hidden) {
    alert($("#"+file).val());
    if ($("#"+file).val()==null || $("#"+file).val()==''){
        alert("请选择文件");
        return;
    }
    $.ajaxFileUpload
    ({
        url:url, //处理上传文件的服务端
        secureuri:false,
        fileElementId:file,
        dataType: 'json',
        success: function(data) {
            data = data.replace(/(^\s*)|(\s*$)/g, "");
            if(data == "1"){
                alert("上传文件大小不能超过50000K！");
                $("#uniform-"+file+" span:first").html('无文件');
                $("input[name='"+file+"']").change(function(){//标签选择器
                    var fn = $("input[name='"+t1+"']").val(); //取出要上传的文件名
                    if($.browser.msie){//判断浏览器（因为不同的浏览器，取出来内容不一样）
                        fn = fn.substring(fn.lastIndexOf("\\")+1);//我们只取文件名
                    }
                    $("#uniform-"+file+" span:first").html(fn);
                });
            }else if(data == "2"){
                alert("上传图片格式不正确");
                $("#uniform-"+file+" span:first").html('无文件');
                $("input[name='"+file+"']").change(function(){
                    var fn = $("input[name='"+file+"']").val();
                    if($.browser.msie){
                        fn = fn.substring(fn.lastIndexOf("\\")+1);
                    }
                    $("#uniform-"+file+" span:first").html(fn);
                });
            } else if(data == "3"){
                alert("上传图片失败请重新上传");
                $("#uniform-"+file+" span:first").html('无文件');
                $("input[name='"+file+"']").change(function(){
                    var fn = $("input[name='"+file+"']").val();
                    if($.browser.msie){
                        fn = fn.substring(fn.lastIndexOf("\\")+1);
                    }
                    $("#uniform-"+file+" span:first").html(fn);
                });
            }else{
                var path=data;
                $("#"+showImg).html("<p " +
                    "onclick=\"delpic('"+type+"','"+showImg+"','"+path+"','"+btn+"')\">x<span>" +
                    "<img src='"+path+"?"+Math.random()+"'></span></p>");
                $("#"+btn).hide();
                $("#"+hidden).val(path);
                $("input[name='"+file+"']").change(function(){
                    var fn = $("input[name='"+file+"']").val();
                    if($.browser.msie){
                        fn = fn.substring(fn.lastIndexOf("\\")+1);
                    }
                    $("#uniform-"+file+" span:first").html(fn);
                });
            }
        },
        error: function() {
            alert("上传失败！");
        }
    });
}
//删除图片方法
function delpic(type,img,path,btn) {
    
}