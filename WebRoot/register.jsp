<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>注册</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>

	<form action="userAction_add.action" method="get">
		昵称： <input name="user.nickname" /><br /> 密码：<input type="password"
			name="user.password"><br /> 性别： <input type="radio"
			name="user.gender" value="男" checked="checked" /> 男 <input
			type="radio" name="user.gender" value="女" /> 女<br /> 地区： <input
			name="user.area" /><br /> 手机号： <input name="user.phone" /><br />
		简单描述： <input name="user.introduce" /><br /> <input type="submit"
			value="注 册">
	</form>
	<s:debug></s:debug>
</body>
</html>
