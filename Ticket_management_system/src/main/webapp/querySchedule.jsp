<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>查询班次</title>
    <link rel="stylesheet" type="text/css" href="css/querySchedule.css">
</head>
<body>
    <div class="container">
        <h2>查询班次</h2>
        <form method="get" action="QueryScheduleServlet" class="form-grid">
            <div class="form-row">
                <label for="class_id">班号:</label>
                <input type="text" id="class_id" name="class_id">
                <label for="end_station">终点站:</label>
                <input type="text" id="end_station" name="end_station">
            </div>
            <div class="form-row">
                <label for="departure_time_start">发车时间开始:</label>
                <input type="text" id="departure_time_start" name="departure_time_start">
                <label for="departure_time_end">发车时间结束:</label>
                <input type="text" id="departure_time_end" name="departure_time_end">
            </div>
            <div class="form-row">
                <label for="travel_time_start">行车时间开始:</label>
                <input type="text" id="travel_time_start" name="travel_time_start">
                <label for="travel_time_end">行车时间结束:</label>
                <input type="text" id="travel_time_end" name="travel_time_end">
            </div>
            <div class="form-row">
                <label for="price_start">票价开始:</label>
                <input type="text" id="price_start" name="price_start">
                <label for="price_end">票价结束:</label>
                <input type="text" id="price_end" name="price_end">
            </div>
            <div class="form-row">
                <input type="submit" value="查询" class="btn">
            </div>
        </form>
        <br>
        <% if (request.getAttribute("schedules") != null) { %>
            <div class="table-responsive">
                <%= request.getAttribute("schedules") %>
            </div>
        <% } %>
    </div>
</body>
</html>