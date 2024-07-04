package register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

@WebServlet("/register/ViewServlet")
public class ViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/view.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        response.getWriter().println("Error: " + e.getMessage());
	        return;
	    }
        String employeeName = request.getParameter("employeeId");
        String selection = request.getParameter("selection");

        if (employeeName != null && !employeeName.isEmpty()) {
            int totalDuration = calculateTotalDurationFromDatabase(employeeName, selection);
            response.getWriter().write(String.valueOf(totalDuration));
        } else {
            response.getWriter().write("Employee name is required.");
        }
    }

    private int calculateTotalDurationFromDatabase(String employeeName, String selection) {
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
                statement.setString(1, employeeName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        totalDuration = resultSet.getInt("total");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalDuration;
    }
}
