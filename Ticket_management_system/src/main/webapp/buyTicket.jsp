<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购票</title>
    <link rel="stylesheet" href="css/buyTicket.css">
    <script>
        function showAlertAndRedirect(message, url) {
            alert(message);
            window.location.href = url;
        }
    </script>
</head>
<body>
    <div class="header">
        <h1>购票</h1>
    </div>
    <div class="content">
        <h2>请输入乘客信息</h2>
        <form action="BuyTicketServlet" method="post">
            <input type="hidden" name="classId" value="<%= request.getParameter("classId") %>">
            <input type="hidden" name="price" value="<%= request.getParameter("price") %>">
            <input type="hidden" name="destination" value="<%= request.getParameter("destination") %>">
            <input type="hidden" name="departureTime" value="<%= request.getParameter("departureTime") %>">

            <label for="passengerName">姓名:</label>
                        <input type="text" id="passengerName" name="passengerName" required>
            <label for="passengerId">身份证号:</label>
            <input type="text" id="passengerId" name="passengerId" required>
            <label for="passengerPhone">手机号:</label>
            <input type="text" id="passengerPhone" name="passengerPhone" required>
            <button type="submit">确认购买</button>
        </form>
        <% if (request.getAttribute("message") != null) { %>
            <script>
                showAlertAndRedirect('<%= request.getAttribute("message") %>', 'index.html');
            </script>
        <% } %>
    </div>
</body>
</html>

