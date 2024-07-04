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

@WebServlet("/register/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        response.getWriter().println("Error: " + e.getMessage());
	        return;
	    }
        String taskCategory = request.getParameter("taskCategory");
        String employeeid = request.getParameter("employeeId");
        System.out.println(employeeid);
        
        if (taskCategory == null || taskCategory.isEmpty()) {
            response.getWriter().println("Invalid task category.");
            return;
        }
        
        try {
            String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String dbUser = "root";
            String dbPassword = "Chaithu@9515";

            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "DELETE FROM tasks WHERE task_category = ? and employee_id = ?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, taskCategory);
                    statement.setString(2, employeeid);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        response.getWriter().println("Tasks with category '" + taskCategory + "' deleted successfully.");
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
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/delete.jsp");
        dispatcher.forward(request, response);
    }
    
    }
