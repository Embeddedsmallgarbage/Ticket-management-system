package ticketmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ticketStatistics")
public class TicketStatisticsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = JDBC.getConn();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT 班号, 起始站, 终点站, 发车时间, 已售票数, 额定载客量 FROM 班次";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            StringBuilder statistics = new StringBuilder();

            statistics.append("<table border='1'>");
            statistics.append("<tr><th>班号</th><th>起始站</th><th>终点站</th><th>发车时间</th><th>已售票数</th><th>剩余票数</th></tr>");

            while (rs.next()) {
                int soldTickets = rs.getInt("已售票数");
                int capacity = rs.getInt("额定载客量");
                int remainingTickets = capacity - soldTickets;

                statistics.append("<tr>");
                statistics.append("<td>").append(rs.getInt("班号")).append("</td>");
                statistics.append("<td>").append(rs.getString("起始站")).append("</td>");
                statistics.append("<td>").append(rs.getString("终点站")).append("</td>");
                statistics.append("<td>").append(rs.getString("发车时间")).append("</td>");
                statistics.append("<td>").append(soldTickets).append("</td>");
                statistics.append("<td>").append(remainingTickets).append("</td>");
                statistics.append("</tr>");
            }

            statistics.append("</table>");
            request.setAttribute("statistics", statistics.toString());
            request.getRequestDispatcher("ticketStatistics.jsp").forward(request, response);

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
