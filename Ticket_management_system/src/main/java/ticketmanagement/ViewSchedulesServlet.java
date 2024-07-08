package ticketmanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/viewSchedules")
public class ViewSchedulesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = JDBC.getConn();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 班次";
            rs = stmt.executeQuery(sql);

            StringBuilder schedules = new StringBuilder();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dbSdf = new SimpleDateFormat("HH:mm:ss"); // 新增数据库时间格式
            Date now = new Date();

            schedules.append("<table border='1'>");
            schedules.append("<tr><th>班号</th><th>起始站</th><th>终点站</th><th>发车时间</th><th>行车时间</th><th>状态</th></tr>");

            while (rs.next()) {
                String departureTimeStr = rs.getString("发车时间");
                @SuppressWarnings("unused")
				Date departureTime = dbSdf.parse(departureTimeStr);

                // 将当前日期和发车时间合并
                String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(now);
                Date fullDepartureTime = sdf.parse(currentDate + " " + departureTimeStr);
                
                boolean hasDeparted = now.after(fullDepartureTime);

                schedules.append("<tr>");
                schedules.append("<td>").append(rs.getInt("班号")).append("</td>");
                schedules.append("<td>").append(rs.getString("起始站")).append("</td>");
                schedules.append("<td>").append(rs.getString("终点站")).append("</td>");
                schedules.append("<td>").append(departureTimeStr).append("</td>");
                schedules.append("<td>").append(rs.getString("行车时间")).append("</td>");
                schedules.append("<td>").append(hasDeparted ? "已发车" : "未发车").append("</td>");
                schedules.append("</tr>");
            }
            schedules.append("</table>");
            request.setAttribute("schedules", schedules.toString());
            request.getRequestDispatcher("viewSchedules.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
