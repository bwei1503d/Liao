<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'update.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <form action="userAction_updateUser.action" method="get">
		<table align="center" border="1">
			<tr>
				<td>手机号</td>
				<td><input type="text" name="user.phone" value="${loginUser.phone }"><input type="hidden" name="user.userId" value="${loginUser.userId }"></td>
			</tr>
			<tr>
				<td>性别</td>
				<td><input type="text" name="user.gender" value="${loginUser.gender }"></td>
			</tr>
			<tr>
				<td>地区</td>
				<td><input type="text" name="user.area" value="${loginUser.area }"></td>
			</tr>
			<tr>
				<td>昵称</td>
				<td><input type="text" name="user.nickname" value="${loginUser.nickname }"></td>
			</tr>
			<tr>
				<td>简单描述</td>
				<td><input type="text" name="user.introduce" value="${loginUser.introduce }"></td>
			</tr>
			<tr>
				<td><input type="submit" value="确认修改"></td>
			</tr>
		</table>
	</form>
  </body>
</html>
