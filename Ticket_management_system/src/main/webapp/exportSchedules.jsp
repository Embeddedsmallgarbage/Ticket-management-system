<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导出班次信息</title>
    <link rel="stylesheet" href="css/exportSchedules.css">
</head>
<body>
    <div class="container">
        <h1>导出班次信息</h1>
        <form action="ExportSchedulesServlet" method="get">
            <button type="submit">导出</button>
        </form>
    </div>
</body>
</html>
