package ticketmanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
public class ImportSchedulesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        File file = new File(uploadPath + File.separator + fileName);
        filePart.write(file.getAbsolutePath());

        // 打印文件路径，检查文件是否存在
        System.out.println("上传的文件路径: " + file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("文件不存在！");
            response.getWriter().write("<script>alert('文件上传失败'); window.location.href = 'index.html';</script>");
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            Connection conn = JDBC.getConn();
            String sql = "INSERT INTO 班次 (班号, 起始站, 终点站, 发车时间, 行车时间, 额定载客量, 已售票数, 票价) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                // 打印每一行数据
                System.out.println("读取到的第 " + lineNumber + " 行数据: " + line);
                if (lineNumber == 1) {
                    continue; // Skip header row
                }

                String[] values = line.split(",");
                if (values.length < 8) {
                    System.out.println("数据格式错误: " + line);
                    continue; // Skip invalid row
                }

                pstmt.setInt(1, Integer.parseInt(values[0]));
                pstmt.setString(2, values[1]);
                pstmt.setString(3, values[2]);
                pstmt.setString(4, values[3]);
                pstmt.setString(5, values[4]);
                pstmt.setInt(6, Integer.parseInt(values[5]));
                pstmt.setInt(7, Integer.parseInt(values[6]));
                pstmt.setDouble(8, Double.parseDouble(values[7]));
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.close();
            file.delete();

            response.getWriter().write("<script>alert('导入成功'); window.location.href = 'index.html';</script>");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('导入失败'); window.location.href = 'index.html';</script>");
        }
    }
}
