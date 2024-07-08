<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>退票</title>
    <link rel="stylesheet" href="css/refundTicket.css">
</head>
<body>
    <div class="header">
        <h1>退票</h1>
    </div>
    <div class="content">
        <form action="RefundTicketServlet" method="post">
            <label for="phone">手机号:</label>
            <input type="text" id="phone" name="phone" required>
            <button type="submit">查询订单</button>
        </form>
        <div id="orderList">
            <%
                String orders = (String) request.getAttribute("orders");
                if (orders != null) {
                    out.print(orders);
                }
            %>
        </div>
    </div>
</body>
</html>
