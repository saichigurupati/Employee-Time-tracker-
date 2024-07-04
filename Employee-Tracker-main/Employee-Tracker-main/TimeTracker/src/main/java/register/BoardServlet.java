package register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register/BoardServlet")
public class BoardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> leaderboard = new ArrayList<>();
        String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String dbUser = "root";
        String dbPassword = "Chaithu@9515";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String sql = "SELECT employee_id, SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)) as total_hours FROM completed GROUP BY employee_id ORDER BY total_hours DESC";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getString("employee_id"));
                    employee.setHours(rs.getInt("total_hours"));
                    leaderboard.add(employee);
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        request.setAttribute("leaderboard", leaderboard);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/leaderboard.jsp");
        dispatcher.forward(request, response);
    }
}
