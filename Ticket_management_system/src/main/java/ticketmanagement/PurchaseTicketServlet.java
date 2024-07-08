package ticketmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/purchaseTicket")
public class PurchaseTicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String endStation = request.getParameter("endStation");
        Connection conn = JDBC.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM 班次 WHERE 终点站 = ? AND 额定载客量 > 已售票数 AND 发车时间 > ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, endStation);
            pstmt.setTime(2, new java.sql.Time(new Date().getTime()));
            rs = pstmt.executeQuery();

            StringBuilder schedules = new StringBuilder();
            schedules.append("<table border='1'>");
            schedules.append("<tr><th>班号</th><th>起始站</th><th>终点站</th><th>发车时间</th><th>行车时间</th><th>票价</th><th>操作</th></tr>");

            while (rs.next()) {
                int classId = rs.getInt("班号");
                String startStation = rs.getString("起始站");
                String destination = rs.getString("终点站");
                java.sql.Time departureTime = rs.getTime("发车时间");
                java.sql.Time travelTime = rs.getTime("行车时间");
                float price = rs.getFloat("票价");

                schedules.append("<tr>");
                schedules.append("<td>").append(classId).append("</td>");
                schedules.append("<td>").append(startStation).append("</td>");
                schedules.append("<td>").append(destination).append("</td>");
                schedules.append("<td>").append(departureTime).append("</td>");
                schedules.append("<td>").append(travelTime).append("</td>");
                schedules.append("<td>").append(price).append("</td>");
                schedules.append("<td><form action='buyTicket.jsp' method='post'>")
                         .append("<input type='hidden' name='classId' value='").append(classId).append("'>")
                         .append("<input type='hidden' name='price' value='").append(price).append("'>")
                         .append("<input type='hidden' name='destination' value='").append(destination).append("'>")
                         .append("<input type='hidden' name='departureTime' value='").append(departureTime).append("'>")
                         .append("<input type='submit' value='购买'></form></td>");
                schedules.append("</tr>");
            }
            schedules.append("</table>");

            request.setAttribute("schedules", schedules.toString());
            request.getRequestDispatcher("purchaseTicket.jsp").forward(request, response);

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
