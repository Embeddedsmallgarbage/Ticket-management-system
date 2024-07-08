package ticketmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = JDBC.getConn();
        Statement stmt = null;
        ResultSet rs = null;
        String classId = request.getParameter("classId");
        String endStation = request.getParameter("endStation");

        try {
            stmt = conn.createStatement();
            StringBuilder sql = new StringBuilder("SELECT * FROM 班次 WHERE 1=1");
            if (classId != null && !classId.trim().isEmpty()) {
                sql.append(" AND 班号 = '").append(classId).append("'");
            }
            if (endStation != null && !endStation.trim().isEmpty()) {
                sql.append(" AND 终点站 LIKE '%").append(endStation).append("%'");
            }
            rs = stmt.executeQuery(sql.toString());

            StringBuilder schedules = new StringBuilder();
            schedules.append("<table border='1'>");
            schedules.append("<tr><th>班号</th><th>起始站</th><th>终点站</th><th>发车时间</th><th>行车时间</th><th>操作</th></tr>");

            while (rs.next()) {
                schedules.append("<tr>");
                schedules.append("<td>").append(rs.getInt("班号")).append("</td>");
                schedules.append("<td>").append(rs.getString("起始站")).append("</td>");
                schedules.append("<td>").append(rs.getString("终点站")).append("</td>");
                schedules.append("<td>").append(rs.getString("发车时间")).append("</td>");
                schedules.append("<td>").append(rs.getString("行车时间")).append("</td>");
                schedules.append("<td><button onclick=\"confirmDelete('").append(rs.getInt("班号")).append("')\">删除</button></td>");
                schedules.append("</tr>");
            }
            schedules.append("</table>");
            request.setAttribute("schedules", schedules.toString());
            request.getRequestDispatcher("deleteSchedule.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
