
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/pages/commons/head.jsp"%>
<div>
    <ul class="breadcrumb">
        <li><a href="#">后台管理</a> <span class="divider">/</span></li>
        <li><a href="/backend/userlist.html">用户管理</a></li>
    </ul>
</div>
<div class="row-fluid sortable">
    <div class="box span12">
        <div class="box-header well" data-original-title>
            <h2><i class="icon-user"></i> 用户列表</h2>
            <div class="box-icon">
                <span class="icon32 icon-color icon-add custom-setting adduser"/>
            </div>
        </div>

        <div class="box-content">
            <form>
                <div class="searcharea">
                    用户名称:
                    <input type="text" id="loginCode" value="" />
                    推荐人：
                    <input type="text" id="referCode" value="" />
                    角色：
                    <select id="roleId" style="width:100px;">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${roleList}" var="role">
                            <option value="${role.id}">${role.roleName}</option>
                        </c:forEach>
                    </select>
                    是否启用：
                    <select id="isStart" style="width:100px;">
                        <option value="" selected="selected">--请选择--</option>
                        <option value="1">启用</option>
                        <option value="2">未启用</option>
                    </select>
                    <button type="button" class="btn btn-primary"><i class="icon-search icon-white"></i> 查询 </button>
                </div>
            </form>
            <table class="table table-striped table-bordered bootstrap-datatable">
                <thead>
                <tr>
                    <th>用户名</th>
                    <th>角色</th>
                    <th>会员类型</th>
                    <th>推荐人</th>
                    <th>启用状态(启用/禁用)</th>
                    <th>注册时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td class="center">${user.loginCode }</td>
                        <td class="center">${user.roleName }</td>
                        <td class="center">${user.userTypeName }</td>
                        <td class="center">${user.referCode}</td>
                        <td class="center">
                            <input type="checkbox" <c:if test="${user.isStart == 1}">checked="true"</c:if> disabled="disabled">
                        </td>
                        <td class="center">
                            <fmt:formatDate value="${user.createTime }" pattern="yyyy-MM-dd"/>
                        </td>
                        <td class="center">
                            <a class="btn btn-success viewuser" href="#" id="${user.id }">
                                <i class="icon-zoom-in icon-white"></i>
                                查看
                            </a>
                            <a class="btn btn-info modifyuser" href="#" id="${user.id }">
                                <i class="icon-edit icon-white"></i>
                                修改
                            </a>
                            <a class="btn btn-danger deluser" href="#" id="${user.id }"	logincode="${user.loginCode }" idcardpicpath="${user.idCardPicPath }" bankpicpath="${user.bankPicPath }" usertype="${user.userType }" usertypename="${user.userTypeName}">
                                <i class="icon-trash icon-white"></i>
                                删除
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="pagination pagination-centered">
                <ul>
                    <li class="active"><a href="javascript:;" name="prev" title="上一页">上一页</a></li>
                    <c:if test="${page.prevPages != null }">
                        <c:forEach items="${page.prevPages}" var="num">
                            <li>
                                <a href="javascript:;" name="page" title="${num }">
                                        ${num }</a>
                            </li>
                        </c:forEach>
                    </c:if>
                    <li class="active">
                        <a href="javascript:;" name="page" title="${page.pageIndex}">${page.pageIndex}</a>
                    </li>
                    <c:if test="${page.nextPages != null }">
                        <c:forEach items="${page.nextPages}" var="num">
                            <li>
                                <a href="javascript:;" name="page" title="${num}">
                                        ${num}</a>
                            </li>
                        </c:forEach>
                    </c:if>
                    <li><a href="javascript:;" name="next" title="下一页">下一页</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="pageIndex" value="${page.pageIndex}">
<input type="hidden" id="pageSize" value="${page.pageSize}">
<input type="hidden" id="pageCount" value="${page.pageCount}">
<input type="hidden" id="sqlCount" value="${page.sqlCount}">
<div class="modal hide fade" id="addUserDiv">
<form action="/backend/adduser.html" method="post" enctype="multipart/form-data" onsubmit="return addUserFunction()">
    <div class="modal-header">
        <button type="button" class="close addusercancel" data-dismiss="modal">×</button>
        <h3>添加用户信息</h3>
    </div>
    <div class="modal-body">
        <ul id="add_formtip"></ul>
        <ul class="topul">
            <li>
                <label>角色：</label>
                <input id="selectrolename" type="hidden" name="roleName" value=""/>
                <select id="selectrole" name="roleId" style="width:100px;">
                    <option value="">--请选择--</option>
                    <c:if test="${roleList!=null}">
                        <c:forEach items="${roleList}" var="role">
                            <option value="${role.id}">${role.roleName}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <span style="color:red;font-weight: bold;">*</span>
            </li>
            <li>
                <label>会员类型：</label>
                <input id="selectusertypename" type="hidden" name="userTypeName" value=""/>
                <select id="selectusertype" name="userType" style="width:100px;">
                    <option value="">--请选择--</option>
                </select>
            </li>
            <li>
                <label>用户名：</label>
                <input type="text" id="a_logincode" name="loginCode" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
                <span style="color:red;font-weight: bold;">*</span>
            </li>
            <li>
                <label>姓名：</label>
                <input type="text" id="a_username" name="userName" />
                <span style="color:red;font-weight: bold;">*</span>
            </li>
            <li>
                <label>性别：</label>
                <select name="sex" style="width:100px;">
                    <option value="男">男</option>
                    <option value="女">女</option>
                </select>
            </li>
            <li>
                <label>证件类型：</label>
                <input id="selectcardtypename" type="hidden" name="cardTypeName" value=""/>
                <select id="selectcardtype" name="cardType" style="width:100px;">
                    <option value="" selected="selected">--请选择--</option>
                    <c:if test="${cardTypeList != null}">
                        <c:forEach items="${cardTypeList}" var="cardType">
                            <option value="${cardType.valueId}">${cardType.valueName}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <span style="color:red;font-weight: bold;">*</span>
            </li>
            <li>
                <label>证件号码：</label>
                <input type="text" id="a_idcard" name="idCard" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
                <span style="color:red;font-weight: bold;">*</span>
            </li>
            <li>
                <label>生日：</label>
                <input class="Wdate" id="a_birthday" size="15" name="birthday" readonly="readonly"  type="text" onClick="WdatePicker();"/>
                <!--<input type="text" class="input-xlarge datepicker" id="a_birthday" name="birthday" value="" readonly="readonly"/> -->
            </li>
            <li>
                <label>收货国家：</label><input type="text" name="country" value="中国"/>
            </li>
            <li>
                <label>联系电话：</label><input type="text" id="a_mobile" name="mobile" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                <span style="color:red;font-weight: bold;">*</span>
            </li>
            <li>
                <label>Email：</label><input type="text" id="a_email" name="email"/>
            </li>
            <li>
                <label>邮政编码：</label><input type="text" id="a_postCode" name="postCode"
                                           onkeyup="this.value=this.value.replace(/\D/g,'')"
                                           onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
            </li>
            <li>
                <label>开户行：</label><input type="text" id="a_bankname" name="bankName"/>
                <span style="color:red;font-weight: bold;">*</span>
            </li>
            <li>
                <label>开户卡号：</label><input type="text" id="a_bankaccount" name="bankAccount"
                                           onkeyup="this.value=this.value.replace(/\D/g,'')"
                                           onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                <span style="color:red;font-weight: bold;">*</span>
            </li>
            <li>
                <label>开户人：</label><input type="text" id="a_accountholder" name="accountHolder"/>
                <span style="color:red;font-weight: bold;">*</span>
            </li>
            <li>
                <label>推荐人：</label><input type="text" name="referCode"
                                          value="${sessionScope.loginUser.loginCode}"
                                          readonly="readonly"/>
            </li>
            <li>
                <label>注册时间：</label>
                <input type="text" id="a_cdate"  value="" readonly="readonly"/>
            </li>
            <li>
                <label>是否启用：</label>
                <select name="isStart" style="width:100px;">
                    <option value="1" selected="selected">启用</option>
                    <option value="2">不启用</option>
                </select> <span style="color:red;font-weight: bold;">*</span>
            </li>
            <li class="lastli">
                <label>收货地址：</label><textarea id="a_useraddress" name="userAddress"></textarea>
            </li>
        </ul>
    </div>
    <div class="clear"></div>
    <ul class="downul">
        <li>
            <label>上传身份证图片：</label>
            <input type="hidden" id="a_fileInputIDPath" name="idCardPicPath" value=""/>
            <input id="a_fileInputID" name="a_fileInputID" type="file"/>
            <input type="button" id="a_uploadbtnID" value="上传"/>
            <p><span style="color:red;font-weight: bold;">*注：1、正反面.2、大小不得超过50k.3、图片格式：jpg、png、jpeg、pneg</span></p>
            <div id="a_idPic"></div>
        </li>
    </ul>
    <ul class="downul">
        <li>
            <label>上传银行卡图片：</label>
            <input type="hidden" id="a_fileInputBankPath" name="bankPicPath" value=""/>
            <input id="a_fileInputBank" name="a_fileInputBank" type="file"/>
            <input type="button" id="a_uploadbtnBank" value="上传"/>
            <p><span style="color:red;font-weight: bold;">*注：1、大小不得超过50k.2、图片格式：jpg、png、jpeg、pneg</span></p>
            <div id="a_bankPic"></div>
        </li>
    </ul>
    <div class="modal-footer">
        <a href="#" class="btn addusercancel" data-dismiss="modal">取消</a>
        <input type="submit"  class="btn btn-primary" value="保存" />
    </div>
</form>
<script src="/static/SLjs/userList.js"></script>
<script src="/static/SLjs/ajax.js"></script>
<%@include file="/WEB-INF/pages/commons/foot.jsp"%>