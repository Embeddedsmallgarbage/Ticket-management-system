package ticketmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classId = request.getParameter("class_id");
        String endStation = request.getParameter("end_station");

        Connection conn = JDBC.getConn();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM 班次 WHERE 班号 = ? OR 终点站 = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, classId);
            stmt.setString(2, endStation);
            rs = stmt.executeQuery();

            StringBuilder schedules = new StringBuilder();
            schedules.append("<table border='1'>");
            schedules.append("<tr><th>班号</th><th>起始站</th><th>终点站</th><th>发车时间</th><th>行车时间</th><th>额定载客量</th><th>已售票数</th><th>票价</th><th>操作</th></tr>");

            while (rs.next()) {
                schedules.append("<tr>");
                schedules.append("<form method='post' action='UpdateScheduleServlet'>");
                schedules.append("<td><input type='hidden' name='class_id' value='" + rs.getInt("班号") + "'>" + rs.getInt("班号") + "</td>");
                schedules.append("<td><input type='text' name='start_station' value='" + rs.getString("起始站") + "'></td>");
                schedules.append("<td><input type='text' name='end_station' value='" + rs.getString("终点站") + "'></td>");
                schedules.append("<td><input type='text' name='departure_time' value='" + rs.getTime("发车时间") + "'></td>");
                schedules.append("<td><input type='text' name='travel_time' value='" + rs.getTime("行车时间") + "'></td>");
                schedules.append("<td><input type='text' name='capacity' value='" + rs.getInt("额定载客量") + "'></td>");
                schedules.append("<td><input type='text' name='sold_tickets' value='" + rs.getInt("已售票数") + "'></td>");
                schedules.append("<td><input type='text' name='price' value='" + rs.getFloat("票价") + "'></td>");
                schedules.append("<td><input type='submit' value='提交'></td>");
                schedules.append("</form>");
                schedules.append("</tr>");
            }
            schedules.append("</table>");
            request.setAttribute("schedules", schedules.toString());
            request.getRequestDispatcher("updateSchedule.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int classId = Integer.parseInt(request.getParameter("class_id"));
        String startStation = request.getParameter("start_station");
        String endStation = request.getParameter("end_station");
        String departureTime = request.getParameter("departure_time");
        String travelTime = request.getParameter("travel_time");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        int soldTickets = Integer.parseInt(request.getParameter("sold_tickets"));
        float price = Float.parseFloat(request.getParameter("price"));

        Connection conn = JDBC.getConn();
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE 班次 SET 起始站 = ?, 终点站 = ?, 发车时间 = ?, 行车时间 = ?, 额定载客量 = ?, 已售票数 = ?, 票价 = ? WHERE 班号 = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, startStation);
            stmt.setString(2, endStation);
            stmt.setString(3, departureTime);
            stmt.setString(4, travelTime);
            stmt.setInt(5, capacity);
            stmt.setInt(6, soldTickets);
            stmt.setFloat(7, price);
            stmt.setInt(8, classId);
            stmt.executeUpdate();

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<script>alert('更新成功');window.location.href='index.html';</script>");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }
}
