<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:if test="${}"></c:if>
<div class="navbar">
        <div class="navbar-inner">
                <ul class="nav pull-right">
                    
                    <li><a href="method!changepwd" class="hidden-phone visible-tablet visible-desktop" role="button">修改密码</a></li>
                    <li><a href="method!loginout" class="hidden-phone visible-tablet visible-desktop" role="button">退出系统</a></li>
                    
                    
                    
                </ul>
                <a class="brand" ><span class="second">网上门诊预约挂号系统管理后台</span></a>
        </div>
</div>