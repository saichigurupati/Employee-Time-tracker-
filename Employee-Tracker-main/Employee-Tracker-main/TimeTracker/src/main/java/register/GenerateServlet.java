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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/register/GenerateServlet")
public class GenerateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/chart.jsp");
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
        String chartType = request.getParameter("chartType");

        JSONArray data = new JSONArray();
        if (employeeName != null && !employeeName.isEmpty() && chartType != null && !chartType.isEmpty()) {
            if (chartType.equals("daily")) {
                data = fetchDailyChartData(employeeName);
            } else if (chartType.equals("weekly") || chartType.equals("monthly")) {
                data = fetchBarChartData(employeeName, chartType);
            }
        }

        response.getWriter().write(data.toJSONString());
    }

    private JSONArray fetchDailyChartData(String employeeName) {
        JSONArray data = new JSONArray();
        String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String dbUser = "root";
        String dbPassword = "Chaithu@9515";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String sql = "SELECT task_category, SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)) AS total_hours " +
                    "FROM tasks " +
                    "WHERE employee_Id = ? AND task_date = CURDATE() " +
                    "GROUP BY task_category";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, employeeName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        JSONObject obj = new JSONObject();
                        obj.put("label", resultSet.getString("task_category"));
                        obj.put("value", resultSet.getInt("total_hours"));
                        data.add(obj);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private JSONArray fetchBarChartData(String employeeName, String chartType) {
        JSONArray data = new JSONArray();
        String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String dbUser = "root";
        String dbPassword = "Chaithu@9515";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String sql = "";
            if (chartType.equals("weekly")) {
            	sql = "SELECT DAYNAME(task_date) AS day_of_week, " +
            		      "SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)) AS total_hours " +
            		      "FROM tasks " +
            		      "WHERE employee_id = ? " +
            		      "AND YEARWEEK(task_date, 1) = YEARWEEK(CURDATE(), 1) " +
            		      "GROUP BY DAYNAME(task_date), task_date " +
            		      "ORDER BY DAYOFWEEK(task_date)";
            } else if (chartType.equals("monthly")) {
                sql = "SELECT WEEK(task_date) AS week_of_year, " +
                        "SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)) AS total_hours " +
                        "FROM tasks " +
                        "WHERE employee_id = ? " +
                        "AND YEAR(task_date) = YEAR(CURDATE()) " +
                        "AND MONTH(task_date) = MONTH(CURDATE()) " +
                        "GROUP BY YEAR(task_date), MONTH(task_date), WEEK(task_date) " +
                        "ORDER BY YEAR(task_date), MONTH(task_date), WEEK(task_date)";
            }


            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, employeeName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        JSONObject obj = new JSONObject();
                        if (chartType.equals("weekly")) {
                            obj.put("label", resultSet.getString("day_of_week"));
                        } else if (chartType.equals("monthly")) {
                            obj.put("label", "Week " + resultSet.getString("week_of_year"));
                        }
                        obj.put("value", resultSet.getInt("total_hours"));
                        data.add(obj);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}