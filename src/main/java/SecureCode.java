import java.sql.*;

public class SecureCode {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String JDBC_USER = "username";
    private static final String JDBC_PASS = "password";

    public static void main(String[] args) {
        String userInput = "userSuppliedData"; // 假设这是从用户那里获取的数据
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {

            // 使用PreparedStatement来避免SQL注入
            pstmt.setString(1, userInput);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // 处理查询结果
                System.out.println(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}