package ticketmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProcessDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
        String classId = request.getParameter("classId");
        Connection conn = JDBC.getConn();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            String sql = "DELETE FROM 班次 WHERE 班号 = " + classId;
            stmt.executeUpdate(sql);

            response.getWriter().println("<script>alert('删除成功'); window.location.href='index.html';</script>");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('删除失败'); window.location.href='deleteSchedule.jsp';</script>");
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
