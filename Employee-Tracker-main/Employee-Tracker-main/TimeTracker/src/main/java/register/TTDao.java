package register;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.SecureRandom;
import java.util.Base64;
public class TTDao {
    private String jdbcUrl = "jdbc:mysql://localhost:3306/time?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private String dbUser = "root";
    private String dbPassword = "Chaithu@9515";

    public TTDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public boolean isDuplicateTask(String employeeName, String taskDate, String startTime) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
            return false;
        }
    }

    public boolean isTaskOverlap(String employeeName, String taskDate, String startTime, String endTime, String employeeId) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String sql = "SELECT COUNT(*) FROM tasks WHERE employee_id = ? AND employee_name = ? AND task_date = ? AND " +
                         "((start_time < ? AND end_time > ?) OR " +
                         "(start_time >= ? AND start_time < ?) OR " +
                         "(end_time > ? AND end_time <= ?))";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, employeeId);
                statement.setString(2, employeeName);
                statement.setString(3, taskDate);
                statement.setString(4, endTime);
                statement.setString(5, startTime);
                statement.setString(6, startTime);
                statement.setString(7, endTime);
                statement.setString(8, startTime);
                statement.setString(9, endTime);
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
            return true;
        }
    }

    public void addTask(String employeeId, String employeeName, String role, String project, String taskDate, String startTime, String endTime, String taskCategory, String description) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public int calculateDuration(String startTime, String endTime) {
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
    public boolean addEmployee(String name) {
        int accountNumber = generateUniqueAccountNumber();
        String password = generateRandomPassword();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String insertQuery = "INSERT INTO employee (employee_id, name, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, accountNumber);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, password);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Existing methods...

    private int generateUniqueAccountNumber() {
        int newAccountNumber;
        do {
            newAccountNumber = 100000000 + new SecureRandom().nextInt(900000000);
        } while (isAccountNumberExists(newAccountNumber));

        return newAccountNumber;
    }

    private boolean isAccountNumberExists(int accountNumber) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String checkQuery = "SELECT COUNT(*) FROM employee WHERE employee_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
                preparedStatement.setInt(1, accountNumber);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[16];
        random.nextBytes(randomBytes);

        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
