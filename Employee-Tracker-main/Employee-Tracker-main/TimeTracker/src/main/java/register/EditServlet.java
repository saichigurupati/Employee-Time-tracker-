package register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register/EditServlet")
public class EditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        response.getWriter().println("Error: " + e.getMessage());
	        return;
	    }
    	String employeeid = request.getParameter("employeeId");
        String taskCategory = request.getParameter("taskCategory");
        String newCategory = request.getParameter("newCategory");
        String newDescription = request.getParameter("newDescription");
        String newStartTime = request.getParameter("newStartTime");
        String newEndTime = request.getParameter("newEndTime");

        if (taskCategory == null || taskCategory.isEmpty() || newCategory == null || newCategory.isEmpty() || 
            newDescription == null || newDescription.isEmpty() || newStartTime == null || newStartTime.isEmpty() || 
            newEndTime == null || newEndTime.isEmpty()) {
            response.getWriter().println("Invalid task details.");
            return;
        }

        try {
            String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String dbUser = "root";
            String dbPassword = "Chaithu@9515";

            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "UPDATE tasks SET task_category = ?, description = ?, start_time = ?, end_time = ? WHERE task_category = ? and employee_id = ?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, newCategory);
                    statement.setString(2, newDescription);
                    statement.setString(3, newStartTime);
                    statement.setString(4, newEndTime);
                    statement.setString(5, taskCategory);
                    statement.setString(6, employeeid);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        response.getWriter().println("Tasks with category '" + taskCategory + "' updated successfully.");
                    } else {
                        response.getWriter().println("No tasks found with the provided category.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/edit.jsp");
        dispatcher.forward(request, response);
    }
    
}
