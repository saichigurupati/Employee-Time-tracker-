package register;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Time;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CompletedServlet
 */
public class CompletedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompletedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
            return;
        }

        String employeeId = request.getParameter("employeeId");
        String taskCategory = request.getParameter("taskCategory");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        if (employeeId == null || employeeId.isEmpty() || taskCategory == null || taskCategory.isEmpty() ||
                startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
            response.getWriter().println("Invalid input parameters.");
            return;
        }

        try {
            String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String dbUser = "root";
            String dbPassword = "Chaithu@9515";

            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                // Update task end time in tasks table
                String updateTaskSQL = "UPDATE tasks SET end_time = ? WHERE employee_id = ? AND task_category = ? AND start_time = ?";
                try (PreparedStatement updateTaskStatement = conn.prepareStatement(updateTaskSQL)) {
                    updateTaskStatement.setString(1, endTime);
                    updateTaskStatement.setString(2, employeeId);
                    updateTaskStatement.setString(3, taskCategory);
                    updateTaskStatement.setString(4, startTime);
                    int rowsUpdated = updateTaskStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        // Insert completed task into completed table
                        String insertCompletedSQL = "INSERT INTO completed (employee_id, task_category, start_time, end_time) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertCompletedStatement = conn.prepareStatement(insertCompletedSQL)) {
                            insertCompletedStatement.setString(1, employeeId);
                            insertCompletedStatement.setString(2, taskCategory);
                            insertCompletedStatement.setString(3, startTime);
                            insertCompletedStatement.setString(4, endTime);
                            int rowsCompleted = insertCompletedStatement.executeUpdate();
                            if (rowsCompleted > 0) {
                                response.getWriter().println("Task completed and recorded successfully.");
                            } else {
                                response.getWriter().println("Task recorded in tasks table but failed to record in completed table.");
                            }
                        }
                    } else {
                        response.getWriter().println("No matching task found to update in the tasks table.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/completed.jsp");
        dispatcher.forward(request, response);
    }
}