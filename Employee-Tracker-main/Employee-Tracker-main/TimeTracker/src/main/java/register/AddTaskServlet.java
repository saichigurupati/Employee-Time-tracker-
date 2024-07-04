package register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

/**
 * Servlet implementation class AddTaskServlet
 */
@WebServlet("/register/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddTaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/add.jsp");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        response.getWriter().println("Error: " + e.getMessage());
	        return;
	    }
	    String employeeId=request.getParameter("employeeid");
	    String employeeName = request.getParameter("employeeName");
	    String role = request.getParameter("role");
	    String project = request.getParameter("project");
	    String taskDate = request.getParameter("taskDate");
	    String startTime = request.getParameter("startTime");
	    String endTime = request.getParameter("endTime");
	    String taskCategory = request.getParameter("taskCategory");
	    String description = request.getParameter("description");
	    addTask(request, response,employeeId, employeeName, role, project, taskDate, startTime, endTime, taskCategory, description);
	    
	}
	private void addTask(HttpServletRequest request, HttpServletResponse response,String employeeId, String employeeName, String role, String project, String taskDate, String startTime, String endTime, String taskCategory, String description) throws IOException {
	    try {
	        String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	        String dbUser = "root";
	        String dbPassword = "Chaithu@9515";

	        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
	        	if (isTaskOverlap(employeeName, taskDate, startTime, endTime,employeeId)) {
	                response.getWriter().println("Another task is already scheduled for the same date and time.");
	                return;
	            }
	            // Check if the task already exists for the same date and time
	            if (isDuplicateTask(conn, employeeName, taskDate, startTime)) {
	                response.getWriter().println("Task already exists for the same date and time.");
	                return;
	            }
	            
	            // Check if the task duration exceeds 8 hours
	            if (calculateDuration(startTime, endTime) > 8) {
	                response.getWriter().println("Task duration exceeds 8 hours.");
	                return;
	            }

	            String sql = "INSERT INTO tasks (employee_id,employee_name, role, project, task_date, start_time, end_time, task_category, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
	            try (PreparedStatement statement = conn.prepareStatement(sql)) {
	            	statement.setString(1, employeeId);
	                statement.setString(2, employeeName);
	                statement.setString(3, role);
	                statement.setString(4, project);
	                statement.setString(5, taskDate);
	                statement.setString(6, startTime);
	                statement.setString(7, endTime);
	                statement.setString(8, taskCategory);
	                statement.setString(9, description);
	                statement.executeUpdate();
	                response.sendRedirect("/TimeTracker/AdminDashServlet");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.getWriter().println("Error: " + e.getMessage());
	    }
	}

	private boolean isDuplicateTask(Connection conn, String employeeName, String taskDate, String startTime) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM tasks WHERE employee_name = ? AND task_date = ? AND start_time = ?";
	    try (PreparedStatement statement = conn.prepareStatement(sql)) {
	        statement.setString(1, employeeName);
	        statement.setString(2, taskDate);
	        statement.setString(3, startTime);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            resultSet.next();
	            return resultSet.getInt(1) > 0;
	        }
	    }
	}

	private int calculateDuration(String startTime, String endTime) {
	    int startHour = Integer.parseInt(startTime.split(":")[0]);
	    int startMinute = Integer.parseInt(startTime.split(":")[1]);
	    int endHour = Integer.parseInt(endTime.split(":")[0]);
	    int endMinute = Integer.parseInt(endTime.split(":")[1]);
	    int durationHour = endHour - startHour;
	    int durationMinute = endMinute - startMinute;
	    if (durationMinute < 0) {
	        durationHour--;
	        durationMinute += 60;
	    }
	    return durationHour + (durationMinute / 60);
	}
	private boolean isTaskOverlap(String employeeName, String taskDate, String startTime, String endTime,String employeeId) {
	    try {
	        String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	        String dbUser = "root";
	        String dbPassword = "Chaithu@9515";

	        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
	            String sql = "SELECT COUNT(*) FROM tasks WHERE employee_name = ? AND task_date = ? AND " +
	                         "((start_time < ? AND end_time > ?) OR " +
	                         "(start_time >= ? AND start_time < ?) OR " +
	                         "(end_time > ? AND end_time <= ?))";
	            try (PreparedStatement statement = conn.prepareStatement(sql)) {
	                statement.setString(1, employeeName);
	                statement.setString(2, taskDate);
	                statement.setString(3, endTime);
	                statement.setString(4, startTime);
	                statement.setString(5, startTime);
	                statement.setString(6, endTime);
	                statement.setString(7, startTime);
	                statement.setString(8, endTime);
	                try (ResultSet resultSet = statement.executeQuery()) {
	                    resultSet.next();
	                    return resultSet.getInt(1) > 0;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return true; 
	    }
	}
}