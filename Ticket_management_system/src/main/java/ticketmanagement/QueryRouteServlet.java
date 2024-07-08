package ticketmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/queryRoute")
public class QueryRouteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
        String routeNumber = request.getParameter("routeNumber");
        String destination = request.getParameter("destination");

        Connection conn = JDBC.getConn();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 班次 WHERE 1=1";
            if (routeNumber != null && !routeNumber.isEmpty()) {
                sql += " AND 班号 = " + routeNumber;
            }
            if (destination != null && !destination.isEmpty()) {
                sql += " AND 终点站 = '" + destination + "'";
            }

            rs = stmt.executeQuery(sql);

            StringBuilder result = new StringBuilder();
            result.append("<table border='1'>");
            result.append("<tr><th>班号</th><th>起始站</th><th>终点站</th><th>发车时间</th><th>行车时间</th><th>额定载客量</th><th>已售票数</th><th>票价</th></tr>");

            while (rs.next()) {
                result.append("<tr>");
                result.append("<td>").append(rs.getInt("班号")).append("</td>");
                result.append("<td>").append(rs.getString("起始站")).append("</td>");
                result.append("<td>").append(rs.getString("终点站")).append("</td>");
                result.append("<td>").append(rs.getString("发车时间")).append("</td>");
                result.append("<td>").append(rs.getString("行车时间")).append("</td>");
                result.append("<td>").append(rs.getInt("额定载客量")).append("</td>");
                result.append("<td>").append(rs.getInt("已售票数")).append("</td>");
                result.append("<td>").append(rs.getDouble("票价")).append("</td>");
                result.append("</tr>");
            }
            result.append("</table>");
            request.setAttribute("result", result.toString());
            request.getRequestDispatcher("queryRoute.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
