$(function () {
    var pageIndex=parseInt($("#pageIndex").val());    //当前页码
    var pageSize=parseInt($("#pageSize").val());     //页大小
    var pageCount=parseInt($("#pageCount").val());    //总页数
    var sqlCount=parseInt($("#sqlCount").val());   //总记录数
    var pageBtns="";    //分页按钮总个数
    var num=""; //总共显示多少按钮

    $("div.pagination ul").on("click","a[name='prev']",function () {//上一页按钮绑定事件
        if (pageIndex<=1){//判断用户是否在首页
            return;
        }
        pageAjax(pageIndex-1);
    });
    $("div.pagination ul").on("click","a[name='next']",function () {//下一页绑定事件
        if (pageIndex>=pageCount){//用户在末页点击了下一页
            return;
        }
        pageAjax(pageIndex+1);
    });
    $("div.pagination ul").on("click","a[name='page']",function () {//页码按钮绑定事件
        var page=$(this).html();
        if (page==pageIndex){//判断用户点击的是否是当前页
            return;
        }
        pageAjax(parseInt(page));
    });
    function pageAjax(pageIndex){
        $.ajax({
            url:"/backend/userlist.html",
            type:"POST",
            data:{pageIndex:pageIndex,loginCode:$("#loginCode").val(),
                referCode:$("#referCode").val(),roleId:$("#s_roleId").val(),
                isStart:$("#isStart").val()},
            dataType:"json",
            success:callBack
        });
    }
    function callBack(data) {
        $("tbody").html("");
        $(data).each(function () {
            if (this.id){
                $("tbody").append("<tr>"
                    +"   <td class='center'>"+this.loginCode +"</td>"
                    +"   <td class='center'>"+this.roleName +"</td>"
                    +"   <td class='center'>"+this.userTypeName +"</td>"
                    +"   <td class='center'>"+this.referCode+"</td>"
                    +"   <td class='center'>"
                    +"   <input type='checkbox'"+function(){if(this.isStart==1){return "checked='true'"}}+" disabled='disabled'>"
                    +"   </td>"
                    +"   <td class='center'>"
                    +format(this.createTime)
                    +"   </td>"
                    +"   <td class='center'>"
                    +"   <a class='btn btn-success viewuser' href='#' id='"+this.id +"'>"
                    +"   <i class='icon-zoom-in icon-white'></i>"
                    +"   查看"
                    +"   </a>"
                    +"   <a class='btn btn-info modifyuser' href='#' id='"+this.id +"'>"
                    +"   <i class='icon-edit icon-white'></i>"
                    +"   修改"
                    +"   </a>"
                    +"   <a class='btn btn-danger deluser' href='#' id='"+this.id +"'	logincode='"+this.loginCode +"' idcardpicpath='"+this.idCardPicPath +"' bankpicpath='"+this.bankPicPath +"' usertype='"+this.userType +"' usertypename='"+this.userTypeName+"'>"
                    +"   <i class='icon-trash icon-white'></i>"
                    +"   删除"
                    +"   </a>"
                    +"   </td>"
                    +"  </tr>");
            }else if (this.pageIndex){
                pageIndex = this.pageIndex;
                pageSize = this.pageSize;
                pageCount = this.pageCount;
                sqlCount = this.sqlCount;
                num=this.num*2+1;
                paging();
                btn();

            }else{
                $("tbody").html("<tr><td colspan='7' style='font-size: 15px'>暂无数据</td></tr>");
            }
        });
    }
    //计算分页按钮
    function paging(){
        if(pageCount <= num && pageCount > 0) {//总页数不足按钮显示数
            pageBtns = new Array(pageCount);
            for(var i = 0; i < pageCount; i++) {
                pageBtns[i] = i + 1;
            }
            return;
        }
        pageBtns = new Array(num);//实例化按钮数组
        var index=num%2==0?parseInt(num/2):parseInt(num/2)+1;//计算中间页
        if(pageIndex >= 1 && pageIndex <= index) {//当前页在中间页之前或中间页
            for(var i = 0; i < num; i++) {
                pageBtns[i] = i + 1;
            }
            return;
        }
        if(pageIndex >= pageCount - (index-1) &&//当前页大于中间页并小于总页数
            pageIndex <= pageCount){
            var flag=num-1;
            for (var i=0;i<num;i++){
                pageBtns[i]=pageCount - flag;
                flag--;
            }
            return;
        }
        //当前页为中间页
        var flag=index-1;
        var end=num-1;
        for (var i=0;i<index;i++){
            if (i!=flag) {
                pageBtns[i] = pageCount - flag;
                pageBtns[end] = pageCount + flag;
            }else{
                pageBtns[i] = pageIndex;
            }
            flag--;
            end--;
        }
    }
    //生成按钮添加到页面上
    function btn(){
        //获取分页功能区
        var $pageDiv = $("div.pagination ul");
        //清空上一次生成的分页组件
        $pageDiv.html("");
        //拼接上一页
        var pageBtn="<li ";//存放按钮
        if (pageIndex<=1) {//当前页在首页时添加样式
            pageBtn += " class='active' ";
        }
        pageBtn+=" ><a href='javascript:;' name='prev' title='上一页'>上一页</a></li>";
        //遍历页码
        for (var i=0;i<pageBtns.length;i++) {
            pageBtn+="<li ";
            if (pageBtns[i]==pageIndex){//遍历当前页的时候
                pageBtn+=" class='active' ";
            }
            pageBtn+= " ><a href='javascript:' name='page' title='${num}'>" + pageBtns[i] + "</a></li> ";
        }
        //拼接下一页
        pageBtn+="<li";
        if (pageIndex>=pageCount){
            pageBtn+=" class='active' ";
        }
        pageBtn+="><a href='javascript:;' name='next' title='下一页'>下一页</a></li>";
        $pageDiv.append(pageBtn);//将按钮拼接到页面
    }

    //日期转换方法
    function format(time){
        var datetime=new Date(time);
        var year = datetime.getFullYear();
        var month = datetime.getMonth()+1;//js从0开始取
        var date = datetime.getDate();
        var hour = datetime.getHours();
        var minutes = datetime.getMinutes();
        var second = datetime.getSeconds();
        if(month<10){
            month = "0" + month;
        }
        if(date<10){
            date = "0" + date;
        }
        if(hour <10){
            hour = "0" + hour;
        }
        if(minutes <10){
            minutes = "0" + minutes;
        }
        if(second <10){
            second = "0" + second ;
        }
        year = year.toString();
        year = year.substring(2);
        var time1 = year+"年"+month+"月"+date+"日";
        var time2 = hour+"时"+minutes+"分";//09年06月12日 17时18分
        var time={time1:time1,time2:time2}    //json格式
// alert(time);
        return time1;
    }
});