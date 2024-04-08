// SecureCode.java
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import org.apache.commons.lang3.StringEscapeUtils;
import com.yumc.diana.utils.HashTools;

public class SecureCode extends HttpServlet {
    @Value("${jdbc.url}")
    private String jdbc;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        MessageDigest md = MessageDigest.getInstance("MD5");
        String username = StringEscapeUtils.escapeHtml4(request.getParameter("username"));
        String password = StringEscapeUtils.escapeHtml4(request.getParameter("password"));
        md.update(password.getBytes());
		byte[] digestByte = md.digest();
        password = HashTools.bytesToHex(digestByte);

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Result</title></head>");
        out.println("<body>");
        out.println("<h2>Welcome " + StringEscapeUtils.escapeHtml4(username) + "!</h2>");
        out.println("<h3>Your password is: " + StringEscapeUtils.escapeHtml4(password) + "</h3>");
        try {
            
            Connection connection = DriverManager.getConnection(jdbc, jdbcUsername, jdbcPassword);
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
            out.println("<p>Login failed!</p>");
            return;
        }

        out.println("<h2>User Input (Escaped)</h2>");
        out.println("</body>");
        out.println("</html>");
    }


}
