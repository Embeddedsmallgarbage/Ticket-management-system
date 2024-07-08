<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>统计售票情况</title>
    <link rel="stylesheet" href="css/ticketStatistics.css">
</head>
<body>
    <div class="header">
        <h1>统计售票情况</h1>
    </div>
    <div class="content">
        <h2>班次售票信息</h2>
        <div>
            <%= request.getAttribute("statistics") != null ? request.getAttribute("statistics") : "暂无售票信息" %>
        </div>
    </div>
</body>
</html>
