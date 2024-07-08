package ticketmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/buyTicket")
public class BuyTicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classId = request.getParameter("classId");
        String price = request.getParameter("price");
        String destination = request.getParameter("destination");
        String departureTime = request.getParameter("departureTime");

        String passengerName = request.getParameter("passengerName");
        String passengerId = request.getParameter("passengerId");
        String passengerPhone = request.getParameter("passengerPhone");

        Connection conn = JDBC.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Update the sold tickets count in the schedules table
            String updateSql = "UPDATE 班次 SET 已售票数 = 已售票数 + 1 WHERE 班号 = ?";
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, Integer.parseInt(classId));
            pstmt.executeUpdate();
            pstmt.close();

            // Get the last order_id
            String orderId = null;
            String orderSql = "SELECT 订单号 FROM 订单 ORDER BY 订单号 DESC LIMIT 1";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(orderSql);
            if (rs.next()) {
                String lastOrderId = rs.getString("订单号");
                orderId = String.valueOf(Integer.parseInt(lastOrderId) + 1);
            } else {
                orderId = "1"; // If no orders exist, start with "1"
            }

            // Insert the new order into the orders table
            String insertSql = "INSERT INTO 订单 (订单号, 班号, 乘客姓名, 乘客身份证号, 乘客手机号, 终点站, 发车时间, 票价) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, orderId);
            pstmt.setString(2, classId);
            pstmt.setString(3, passengerName);
            pstmt.setString(4, passengerId);
            pstmt.setString(5, passengerPhone);
            pstmt.setString(6, destination);
            pstmt.setTime(7, java.sql.Time.valueOf(departureTime));
            pstmt.setFloat(8, Float.parseFloat(price));
            pstmt.executeUpdate();

            request.setAttribute("message", "购买成功！");
            request.getRequestDispatcher("buyTicket.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}