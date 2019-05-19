<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
  <head>
  <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title>网上门诊预约挂号系统管理后台</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.css">
    
    <link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
    <link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">

    <script src="lib/jquery-1.7.2.min.js" type="text/javascript"></script>

    <!-- Demo page code -->

    <style type="text/css">
        #line-chart {
            height:300px;
            width:800px;
            margin: 0px auto;
            margin-top: 1em;
        }
        .brand { font-family: georgia, serif; }
        .brand .first {
            color: #ccc;
            font-style: italic;
        }
        .brand .second {
            color: #fff;
            font-weight: bold;
        }
    </style>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="lib/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="../assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
  </head>

  <!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
  <!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
  <!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
  <!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
  <!--[if (gt IE 9)|!(IE)]><!--> 
  <body class=""> 
  <!--<![endif]-->
    
    <%@ include file="../top.jsp" %>
    


    
    <%@ include file="../left.jsp" %>
    

	

    
    <div class="content">
        
        <div class="header">
            
            <h1 class="page-title">${title }</h1>
        </div>
        
                

        <div class="container-fluid">
            <div class="row-fluid">
            
<form action="${url }" method="post">                    
<br/>
         
          
           检查名称:<input name="jname" type="text" class="input-xlarge"  value="${jname }" >
          
            检查用户:<input name="username" type="text" class="input-xlarge"  value="${username }"><br/>
            姓名:<input name="xingming" type="text" class="input-xlarge"  value="${xingming }">
            身份证:<input name="sfz" type="text" class="input-xlarge"  value="${sfz }">
            
            
            <input type="submit" class="button button-small border-green"   value="查询" />
</form>

<div class="well">
    <table class="table">
      <thead>
        <tr>
          <th>检查名称</th>
          <th>检查费用</th>
          <th>检查用户</th>
          <th>姓名</th>
          <th>身份证</th>
          <th>联系方式</th>
          <th>检查日期</th>
          <th>预约日期</th>
          <th>预约状态</th>
          
         
          
        
         
        </tr>
      </thead>
      <tbody>
      
      <c:forEach items="${list}" var="bean">
        <tr>
          <td>${bean.yuyue2.jiancha.jname }</td>
          <td>${bean.yuyue2.jiancha.money }</td>
          <td>${bean.user.username }</td>
          <td>${bean.user.xingming }</td>
          <td>${bean.user.sfz }</td>
          <td>${bean.user.dianhua }</td>
          <td>${bean.yuyue2.riqi }--${bean.yuyue2.week }</td>
          <td>${bean.ytime }</td>
          <td>${bean.status }</td>
         
          
         
        </tr>
        </c:forEach>
        
        
        
        
      </tbody>
    </table>
</div>
<div class="pagination">
    ${pagerinfo }
</div>





                    
                    
                    
            </div>
        </div>
    </div>
    


    <script src="lib/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
    </script>
    
  </body>
</html>
