// InsecureCode.java
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsecureCode extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userInput = request.getParameter("input");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Result</title></head>");
        out.println("<body>");
        out.println("<h2>Welcome " + username + "!</h2>");
        out.println("<h3>Your password is: " + password + "</h3>");

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                out.println("<p>Login successful!</p>");
            } else {
                out.println("<p>Invalid credentials!</p>");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("<h2>User Input (Potentially Unsafe)</h2>");
        out.println("<p>" + userInput + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}
