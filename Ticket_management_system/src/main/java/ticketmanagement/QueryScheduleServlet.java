package ticketmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class QueryScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String classId = request.getParameter("class_id");
        String endStation = request.getParameter("end_station");
        String departureTimeStart = request.getParameter("departure_time_start");
        String departureTimeEnd = request.getParameter("departure_time_end");
        String travelTimeStart = request.getParameter("travel_time_start");
        String travelTimeEnd = request.getParameter("travel_time_end");
        String priceStart = request.getParameter("price_start");
        String priceEnd = request.getParameter("price_end");

        Connection conn = JDBC.getConn();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM 班次 WHERE 1=1");
            if (classId != null && !classId.isEmpty()) sql.append(" AND 班号 = ?");
            if (endStation != null && !endStation.isEmpty()) sql.append(" AND 终点站 = ?");
            if (departureTimeStart != null && !departureTimeStart.isEmpty()) sql.append(" AND 发车时间 >= ?");
            if (departureTimeEnd != null && !departureTimeEnd.isEmpty()) sql.append(" AND 发车时间 <= ?");
            if (travelTimeStart != null && !travelTimeStart.isEmpty()) sql.append(" AND 行车时间 >= ?");
            if (travelTimeEnd != null && !travelTimeEnd.isEmpty()) sql.append(" AND 行车时间 <= ?");
            if (priceStart != null && !priceStart.isEmpty()) sql.append(" AND 票价 >= ?");
            if (priceEnd != null && !priceEnd.isEmpty()) sql.append(" AND 票价 <= ?");

            stmt = conn.prepareStatement(sql.toString());

            int index = 1;
            if (classId != null && !classId.isEmpty()) stmt.setString(index++, classId);
            if (endStation != null && !endStation.isEmpty()) stmt.setString(index++, endStation);
            if (departureTimeStart != null && !departureTimeStart.isEmpty()) stmt.setString(index++, departureTimeStart);
            if (departureTimeEnd != null && !departureTimeEnd.isEmpty()) stmt.setString(index++, departureTimeEnd);
            if (travelTimeStart != null && !travelTimeStart.isEmpty()) stmt.setString(index++, travelTimeStart);
            if (travelTimeEnd != null && !travelTimeEnd.isEmpty()) stmt.setString(index++, travelTimeEnd);
            if (priceStart != null && !priceStart.isEmpty()) stmt.setString(index++, priceStart);
            if (priceEnd != null && !priceEnd.isEmpty()) stmt.setString(index++, priceEnd);

            rs = stmt.executeQuery();

            StringBuilder schedules = new StringBuilder();
            schedules.append("<table>");
            schedules.append("<tr><th>班号</th><th>起始站</th><th>终点站</th><th>发车时间</th><th>行车时间</th><th>额定载客量</th><th>已售票数</th><th>票价</th></tr>");

            while (rs.next()) {
                schedules.append("<tr>");
                schedules.append("<td>").append(rs.getInt("班号")).append("</td>");
                schedules.append("<td>").append(rs.getString("起始站")).append("</td>");
                schedules.append("<td>").append(rs.getString("终点站")).append("</td>");
                schedules.append("<td>").append(rs.getTime("发车时间")).append("</td>");
                schedules.append("<td>").append(rs.getTime("行车时间")).append("</td>");
                schedules.append("<td>").append(rs.getInt("额定载客量")).append("</td>");
                schedules.append("<td>").append(rs.getInt("已售票数")).append("</td>");
                schedules.append("<td>").append(rs.getFloat("票价")).append("</td>");
                schedules.append("</tr>");
            }
            schedules.append("</table>");
            request.setAttribute("schedules", schedules.toString());
            request.getRequestDispatcher("querySchedule.jsp").forward(request, response);

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
