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

/**
 * Servlet implementation class EmployeeViewServlet
 */
@WebServlet("/register/EmployeeViewServlet")
public class EmployeeViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/empview.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
            return;
        }
        
        String selection = request.getParameter("selection");
        System.out.println(selection);
        String accountNumber = (String) request.getSession().getAttribute("accountNumber");
        System.out.println(accountNumber);

        if (accountNumber != null && !accountNumber.isEmpty()) {
            int totalDuration = calculateTotalDurationFromDatabase(accountNumber, selection);
            System.out.println("Total Duration: " + totalDuration);
            response.getWriter().write(String.valueOf(totalDuration));
            System.out.println(accountNumber);
        } else {
        	System.out.println("Total Duration: ");
            response.getWriter().write("Account number is required.");
        }
    }

    private int calculateTotalDurationFromDatabase(String accountNumber, String selection) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String dbUser = "root";
        String dbPassword = "Chaithu@9515";
        int totalDuration = 0;

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String sql = "";
            if (selection.equals("daily")) {
                sql = "SELECT SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)) AS total FROM tasks WHERE employee_id = ? AND task_date = CURDATE()";
            } else if (selection.equals("weekly")) {
                sql = "SELECT SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)) AS total FROM tasks WHERE employee_id = ? AND YEARWEEK(task_date) = YEARWEEK(CURDATE())";
            } else if (selection.equals("monthly")) {
                sql = "SELECT SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)) AS total FROM tasks WHERE employee_id = ? AND MONTH(task_date) = MONTH(CURDATE()) AND YEAR(task_date) = YEAR(CURDATE())";
            }

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, accountNumber);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        totalDuration = resultSet.getInt("total");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }

        return totalDuration;
    }
}