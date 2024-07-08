<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改班次信息</title>
    <link rel="stylesheet" type="text/css" href="css/updateSchedule.css">
</head>
<body>
    <h2>修改班次信息</h2>
    <form method="get" action="UpdateScheduleServlet">
        班号: <input type="text" name="class_id">
        终点站: <input type="text" name="end_station">
        <input type="submit" value="查询">
    </form>
    <br>
    <% if (request.getAttribute("schedules") != null) { %>
        <%= request.getAttribute("schedules") %>
    <% } %>
</body>
</html>
