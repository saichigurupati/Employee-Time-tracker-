package register;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/register/ViewDetailsServlet")
public class ViewDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = (String) request.getSession().getAttribute("accountNumber");
        List<Task> taskList = new ArrayList<>();
        String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String dbUser = "root";
        String dbPassword = "Chaithu@9515";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String sql = "SELECT * FROM tasks WHERE employee_id = ? ORDER BY task_date ASC";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, employeeId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Task task = new Task();
                    task.setEmployeeId(rs.getString("employee_id"));
                    task.setEmployeeName(rs.getString("employee_name"));
                    task.setRole(rs.getString("role"));
                    task.setProject(rs.getString("project"));
                    task.setTaskDate(rs.getDate("task_date"));
                    java.sql.Time sqlStartTime = java.sql.Time.valueOf(rs.getObject("start_time", LocalTime.class));
                    java.sql.Time sqlEndTime = java.sql.Time.valueOf(rs.getObject("end_time", LocalTime.class));
                    task.setStartTime(sqlStartTime);
                    task.setEndTime(sqlEndTime);
                    task.setTaskCategory(rs.getString("task_category"));
                    task.setDescription(rs.getString("description"));
                    taskList.add(task);
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        request.setAttribute("taskList", taskList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/viewdetails.jsp");
        dispatcher.forward(request, response);
    }
}
