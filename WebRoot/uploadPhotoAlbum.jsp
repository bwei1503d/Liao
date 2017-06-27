<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>上传照片到相册</title>

</head>

<body>
	<form action="userAction_uploadPhotoAlbum.action" method="post"
		enctype="multipart/form-data">
		相册名：<input type="text" name="album.albumName" /><br />
		      <input type="file" name="user.file" /><br /> 
		      <input type="submit" value="添加">
	</form>
</body>
</html>
