<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    
    <script language="javascript" type="text/javascript">

function checkform()
{
	 
	

	if (document.getElementById('nameid').value=="")
	{
		alert("姓名不能为空");
		return false;
	}
	
if (document.getElementById('zhiwuid').value=="")
	{
		alert("职务不能为空");
		return false;
	}
	if (document.getElementById('renshuid').value=="")
	{
		alert("每天最多预约人数不能为空");
		return false;
	}
	
	
	//验证正整数
var reg1 =  /^\d+$/;
	 
	if (document.getElementById('renshuid').value.match(reg1) == null)
	{
		alert("每天最多预约人数必须为正整数");
		return false;
		
	}
	
	return true;
	
}


</script>
    
    
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
                    

<div class="well">
    
    <div id="myTabContent" class="tab-content">
      <div class="tab-pane active in" id="home">
    <form id="tab"	method="post" action="${url }" onsubmit="return checkform()" enctype="multipart/form-data">
    
    
        <label>所属科室</label>
        <select name="keshiid">
        <c:forEach items="${keshilist}" var="keshi">
        <option value="${keshi.id }" <c:if test="${bean.keshi.id==keshi.id }">selected</c:if> >${keshi.ktitle }</option>
        </c:forEach>
        </select>
        
        
        <label>医生姓名</label>
        <input type="text" name="name" id="nameid" class="input-xlarge"  value="${bean.name }">
        
        <label>职务</label>
        <input type="text" name="zhiwu" id="zhiwuid" class="input-xlarge" value="${bean.zhiwu }">
        
        <label>照片</label>
       <img src="<%=basePath %>uploadfile/${bean.photo }" width="100" height="100" />
       
       <label>重新上传照片</label>
       <input type="file" name="uploadfile"  id="uploadfileid"/>
       
       <label>每天最多预约人数</label>
        <input type="text" name="renshu" id="renshuid" class="input-xlarge"  value="${bean.renshu }">
        
        <label>门诊时间</label>
        星期一：<input type="checkbox" name="week" value="星期一" <c:if test="${week1!=null }">checked</c:if> />
        星期二：<input type="checkbox" name="week" value="星期二" <c:if test="${week2!=null }">checked</c:if>/>
        星期三：<input type="checkbox" name="week" value="星期三" <c:if test="${week3!=null }">checked</c:if>/>
        星期四：<input type="checkbox" name="week" value="星期四" <c:if test="${week4!=null }">checked</c:if>/>
        星期五：<input type="checkbox" name="week" value="星期五" <c:if test="${week5!=null }">checked</c:if>/>
        星期六：<input type="checkbox" name="week" value="星期六" <c:if test="${week6!=null }">checked</c:if>/>
        星期日：<input type="checkbox" name="week" value="星期日" <c:if test="${week7!=null }">checked</c:if>/>
       
        <label>学术擅长</label>
       <textarea rows="8" cols="60" name="shanchang">${bean.shanchang }</textarea>
        
        <label>医生简介</label>
       <textarea rows="8" cols="60" name="jianjie">${bean.jianjie }</textarea>
       
    
    <label>操作</label>
     
  
    <input type="submit" value="提交"  /> 
     
    <input type="button" value="返回" onclick="javascript:history.go(-1);"/>
     
   
    
    </form>
      </div>
      
      
  </div>

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



