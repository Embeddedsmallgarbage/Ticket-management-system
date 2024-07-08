package ticketmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addSchedule")
public class AddScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = JDBC.getConn();
        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO 班次 (班号, 起始站, 终点站, 发车时间, 行车时间, 额定载客量, 已售票数, 票价) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, Integer.parseInt(request.getParameter("班号")));
            pstmt.setString(2, request.getParameter("起始站"));
            pstmt.setString(3, request.getParameter("终点站"));
            pstmt.setString(4, request.getParameter("发车时间"));
            pstmt.setString(5, request.getParameter("行车时间"));
            pstmt.setInt(6, Integer.parseInt(request.getParameter("额定载客量")));
            pstmt.setInt(7, Integer.parseInt(request.getParameter("已售票数")));
            pstmt.setFloat(8, Float.parseFloat(request.getParameter("票价")));

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                response.sendRedirect("index.html");
            } else {
                response.getWriter().write("添加班次失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }
}
