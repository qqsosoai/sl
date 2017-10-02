
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

<script src="/static/SLjs/ajax.js"></script>
<%@include file="/WEB-INF/pages/commons/foot.jsp"%>