package ticketmanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExportSchedulesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=schedules.csv");

        try (Connection conn = JDBC.getConn();
             Statement stmt = conn.createStatement();
             PrintWriter writer = response.getWriter()) {

            String sql = "SELECT * FROM 班次";
            ResultSet rs = stmt.executeQuery(sql);

            // Write CSV header
            writer.println("班号,起始站,终点站,发车时间,行车时间,额定载客量,已售票数,票价");

            // Write CSV data
            while (rs.next()) {
                writer.printf("%d,%s,%s,%s,%s,%d,%d,%.2f%n",
                        rs.getInt("班号"),
                        rs.getString("起始站"),
                        rs.getString("终点站"),
                        rs.getString("发车时间"),
                        rs.getString("行车时间"),
                        rs.getInt("额定载客量"),
                        rs.getInt("已售票数"),
                        rs.getDouble("票价"));
            }

            // Close resources
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('导出失败'); window.location.href = 'index.html';</script>");
        }
    }
}
