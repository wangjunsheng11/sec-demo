import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {

    public static void main(String[] args) {
        String username = "john' OR '1'='1"; // 恶意输入的用户名
        String password = "password"; // 假设用户输入的密码

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_name", "user", "password");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // 使用参数预处理防止SQL注入
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // 执行查询
            preparedStatement.executeQuery();

            // 验证登录
            System.out.println("登录成功！");

        } catch (SQLException e) {
            System.out.println("登录失败！");
            e.printStackTrace();
        }
    }
}