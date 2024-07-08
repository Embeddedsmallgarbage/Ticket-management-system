<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>售票</title>
    <link rel="stylesheet" href="css/purchaseTicket.css">
</head>
<body>
    <div class="header">
        <h1>售票</h1>
    </div>
    <div class="content">
        <h2>查询班次</h2>
        <form action="purchaseTicket" method="post">
            <label for="endStation">终点站:</label>
            <input type="text" id="endStation" name="endStation" required>
            <button type="submit">查询班次</button>
        </form>
        <div>
            <%= request.getAttribute("schedules") != null ? request.getAttribute("schedules") : "暂无符合条件的班次" %>
        </div>
    </div>
</body>
</html>
