<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.User, java.util.List"%>
<%
List<User> userList = (List<User>)request.getAttribute("userList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DAOの練習</title>
</head>
<body>
	<h1>DAOパターンの練習</h1>
	<form action="Main" method="post">
		名前：<input type="text" name="name"><br>
		年齢：<input type="number" name="age"><br>
		<input type="submit" value="送信">
	</form>

<% if (userList != null && userList.size() > 0) { %>
<table border="1">
	<tr>
		<th>名前</th>
		<th>年齢</th>
	</tr>
	<% for (User user : userList) { %>
	<tr>
		<td><%= user.getName() %></td>
		<td><%= user.getAge() %></td>
	</tr>
	<% } %>
</table>
<% } %>
</body>
</html>