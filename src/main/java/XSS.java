import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class XSS extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 假设我们有一个从请求中获取的用户输入
        Strin ignput = request.getParameter("input");

        // 使用API提供的方法来防止XSS攻击
        String safeInput = escapeHtml4(input);

        // 然后可以将safeInput用于响应中，比如设置响应内容
        response.getWriter().write("Hello, " + safeInput);
    }

    // 使用Apache Commons Text的escapeHtml4方法来避免XSS攻击
    private String escapeHtml4(String input) {
        return org.apache.commons.text.StringEscapeUtils.escapeHtml4(input);
    }
}