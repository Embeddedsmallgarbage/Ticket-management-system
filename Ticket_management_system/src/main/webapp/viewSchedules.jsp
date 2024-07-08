<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>浏览班次</title>
    <link rel="stylesheet" href="css/viewSchedules.css">
</head>
<body>
    <div class="header">
        <h1>浏览班次</h1>
    </div>
    <div class="content">
        <h2>班次信息</h2>
        <div>
            <%= request.getAttribute("schedules") != null ? request.getAttribute("schedules") : "暂无班次信息" %>
        </div>
    </div>
</body>
</html>
