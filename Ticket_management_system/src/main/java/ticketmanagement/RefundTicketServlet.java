package ticketmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RefundTicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");

        Connection conn = JDBC.getConn();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM 订单 WHERE 乘客手机号 = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, phone);
            rs = stmt.executeQuery();

            StringBuilder orders = new StringBuilder();
            orders.append("<table border='1'>");
            orders.append("<tr><th>订单号</th><th>班号</th><th>乘客姓名</th><th>乘客身份证号</th><th>终点站</th><th>发车时间</th><th>票价</th><th>操作</th></tr>");

            while (rs.next()) {
                orders.append("<tr>");
                orders.append("<td>").append(rs.getString("订单号")).append("</td>");
                orders.append("<td>").append(rs.getInt("班号")).append("</td>");
                orders.append("<td>").append(rs.getString("乘客姓名")).append("</td>");
                orders.append("<td>").append(rs.getString("乘客身份证号")).append("</td>");
                orders.append("<td>").append(rs.getString("终点站")).append("</td>");
                orders.append("<td>").append(rs.getString("发车时间")).append("</td>");
                orders.append("<td>").append(rs.getFloat("票价")).append("</td>");
                orders.append("<td><form action='ProcessRefundServlet' method='post'><input type='hidden' name='orderId' value='").append(rs.getString("订单号")).append("'><button type='submit'>退票</button></form></td>");
                orders.append("</tr>");
            }
            orders.append("</table>");
            
            request.setAttribute("orders", orders.toString());
            request.getRequestDispatcher("refundTicket.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }
}
