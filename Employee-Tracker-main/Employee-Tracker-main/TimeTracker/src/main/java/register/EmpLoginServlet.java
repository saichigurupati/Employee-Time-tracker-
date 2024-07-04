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


public class EmpLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EmpLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/employeelogin.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        String accountNumber = request.getParameter("accountNumber");
        String password = request.getParameter("password");

        if (isValidLogin(accountNumber, password)) {
            request.getSession().setAttribute("loggedIn", true);
            request.getSession().setAttribute("accountNumber", accountNumber);
            
            System.out.println("LoginServlet - Setting loggedIn to true");
            System.out.println("LoginServlet - Setting accountNumber: " + accountNumber);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/employeedashboard.jsp");
           
            dispatcher.forward(request, response);
        } else {
        	response.getWriter().println("Error: Employee Id Or Password Incorrect");
    }
    }

    private boolean isValidLogin(String accountNumber, String password) {
    	String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    	String dbUser = "root";
    	String dbPassword = "Chaithu@9515";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String checkQuery = "SELECT COUNT(*) FROM employee WHERE employee_id = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
                preparedStatement.setString(1, accountNumber);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
