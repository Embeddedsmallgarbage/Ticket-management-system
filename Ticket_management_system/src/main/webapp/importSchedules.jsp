<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>导入班次信息</title>
    <link rel="stylesheet" href="css/importSchedules.css">
</head>
<body>
    <div class="container">
        <h1>导入班次信息</h1>
        <form action="ImportSchedulesServlet" method="post" enctype="multipart/form-data">
            <label for="file">选择.csv文件:</label>
            <input type="file" id="file" name="file" accept=".csv" required>
            <button type="submit">上传</button>
        </form>
    </div>
</body>
</html>
