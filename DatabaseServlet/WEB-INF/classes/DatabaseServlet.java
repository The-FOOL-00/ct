import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DatabaseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Employee Records</h2>");

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            try (
                Connection con = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/company",
                    "root",
                    "Sundar@1024"
                );

                Statement stmt = con.createStatement();

                ResultSet rs = stmt.executeQuery(
                    "SELECT id, age, first_name, last_name FROM Employees"
                )
            ) {
                out.println("<table border='1' cellpadding='8'>");
                out.println("<tr><th>ID</th><th>Age</th>"
                    + "<th>First Name</th><th>Last Name</th></tr>");

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getInt("age") + "</td>");
                    out.println("<td>" + 
                        rs.getString("first_name") + "</td>");
                    out.println("<td>" + 
                        rs.getString("last_name") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + 
                e.getMessage());
        }

        out.println("</body></html>");
    }


}