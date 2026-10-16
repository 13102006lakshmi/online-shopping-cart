import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class ShoppingCartServlet extends HttpServlet {
 
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
 
        response.setContentType("text/html;charset=UTF-8");
 
        String item = request.getParameter("item");
        String cartValue = "";
 
        Cookie[] cookies = request.getCookies();
 
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("cart".equals(c.getName())) {
                    cartValue = c.getValue();
                    break;
                }
            }
        }
 
        if (item != null && !item.trim().isEmpty()) {
            item = item.trim().replace("|", "");
 
            cartValue = cartValue.isEmpty()
                    ? item
                    : cartValue + "|" + item;
        }
 
        // Send one updated cart cookie on every request
        Cookie cartCookie = new Cookie("cart", cartValue);
        cartCookie.setMaxAge(60 * 30);
        cartCookie.setPath(
                request.getContextPath().isEmpty()
                ? "/" : request.getContextPath()
        );
 
        response.addCookie(cartCookie);
 
        PrintWriter out = response.getWriter();
 
        out.println("<html><body>");
        out.println("<h3>Your Shopping Cart</h3>");
        out.println("Items in cart: "
                + (cartValue.isEmpty()
                ? "empty"
                : cartValue.replace("|", ", ")));
        out.println("</body></html>");
    }
}
