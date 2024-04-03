// SecureCode.java
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class SecureCode extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userInput = request.getParameter("input");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Result</title></head>");
        out.println("<body>");
        out.println("<h2>Welcome " + escapeHtml(username) + "!</h2>");
        out.println("<h3>Your password is: " + escapeHtml(password) + "</h3>");

        // 防止SQL注入
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                out.println("<p>Login successful!</p>");
            } else {
                out.println("<p>Invalid credentials!</p>");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("<h2>User Input (Escaped)</h2>");
        out.println("<p>" + escapeHtml(userInput) + "</p>"); // 输出转义后的用户输入，避免XSS漏洞
        out.println("</body>");
        out.println("</html>");
    }

    // 对用户输入进行HTML转义
    private String escapeHtml(String input) {
        return input != null ? input.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#39;") : "";
    }
}
