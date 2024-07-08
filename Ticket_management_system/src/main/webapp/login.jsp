<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
	<meta charset="UTF-8">
	<title>登录</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
	<div class="login-container">
		<h1>车票管理系统</h1>
        <h2>登录</h2>
        <form action="login" method="post">
            <label for="username">账号:</label>
            <input type="text" id="username" name="username" required>
            <label for="password">密码:</label>
            <input type="password" id="password" name="password" required>
            <button type="submit">登录</button>
        </form>
        <div class="error-message">
                <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                <% if (errorMessage != null) { %>
                    <p style="color:red;"><%= errorMessage %></p>
                <% } %>
            </div>
    </div>
</body>
</html>