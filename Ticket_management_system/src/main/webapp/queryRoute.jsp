<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>查询路线</title>
    <link rel="stylesheet" href="css/queryRoute.css">
</head>
<body>
    <div class="header">
        <h1>查询路线</h1>
    </div>
    <div class="content">
        <form action="queryRoute" method="get">
            <label for="routeNumber">班号:</label>
            <input type="text" id="routeNumber" name="routeNumber">
            <label for="destination">终点站:</label>
            <input type="text" id="destination" name="destination">
            <button type="submit">查询</button>
        </form>
        <div>
            <h2>查询结果</h2>
            <div>
                <%= request.getAttribute("result") %>
            </div>
        </div>
    </div>
</body>
</html>

