<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>删除班次</title>
    <link rel="stylesheet" href="css/deleteSchedule.css">
</head>
<body>
    <div class="header">
        <h1>删除班次</h1>
    </div>
    <div class="content">
        <form action="DeleteScheduleServlet" method="post">
            <label for="classId">班号:</label>
            <input type="text" id="classId" name="classId">
            <label for="endStation">终点站:</label>
            <input type="text" id="endStation" name="endStation">
            <button type="submit">查询</button>
        </form>
        <div>
            <%= request.getAttribute("schedules") != null ? request.getAttribute("schedules") : "暂无班次信息" %>
        </div>
    </div>
    <script>
        function confirmDelete(classId) {
            if (confirm("确定要删除这个班次吗?")) {
                window.location.href = 'ProcessDeleteServlet?classId=' + classId;
            }
        }
    </script>
</body>
</html>
