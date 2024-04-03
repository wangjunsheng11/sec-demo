import java.sql.*;

public class VulnerableCode {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String JDBC_USER = "username";
    private static final String JDBC_PASS = "password";

    public static void main(String[] args) {
        String userInput = "userSuppliedData"; // 假设这是从用户那里获取的数据  
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM users WHERE username = '" + userInput + "'"; // 不安全的SQL语句  
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // 处理查询结果  
                System.out.println(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}