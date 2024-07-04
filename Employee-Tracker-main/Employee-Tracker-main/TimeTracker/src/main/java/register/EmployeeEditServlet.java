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
import java.sql.SQLException;

/**
 * Servlet implementation class EmployeeEditServlet
 */
@WebServlet("/register/EmployeeEditServlet")
public class EmployeeEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/employeeedit.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        response.getWriter().println("Error: " + e.getMessage());
	        return;
	    }
    	String employeeid = (String) request.getSession().getAttribute("accountNumber");
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

}
