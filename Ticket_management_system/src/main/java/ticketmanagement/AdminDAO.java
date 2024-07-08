package ticketmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//该类用于确定输入的账号密码是否正确
public class AdminDAO {
	public boolean validate(String username, String password) {
        Connection conn = JDBC.getConn();
        String sql = "SELECT * FROM 管理员 WHERE 账号 = ? AND 密码 = ?";
        
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                return true; // User exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
