package ticketmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProcessRefundServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        Connection conn = JDBC.getConn();
        PreparedStatement stmt = null;

        try {
            // 获取订单的班号
            String classIdSql = "SELECT 班号 FROM 订单 WHERE 订单号 = ?";
            stmt = conn.prepareStatement(classIdSql);
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            int classId = 0;
            if (rs.next()) {
                classId = rs.getInt("班号");
            }
            rs.close();
            stmt.close();

            // 删除订单
            String deleteSql = "DELETE FROM 订单 WHERE 订单号 = ?";
            stmt = conn.prepareStatement(deleteSql);
            stmt.setString(1, orderId);
            stmt.executeUpdate();
            stmt.close();

            // 更新班次的已售票数
            String updateSql = "UPDATE 班次 SET 已售票数 = 已售票数 - 1 WHERE 班号 = ?";
            stmt = conn.prepareStatement(updateSql);
            stmt.setInt(1, classId);
            stmt.executeUpdate();

            // 提示退票成功并跳转到首页
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<script>alert('退票成功');window.location.href='index.html';</script>");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }
}
