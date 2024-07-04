<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="register.Task" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task Details</title>

    <style>
    body{
        font-family: Arial, sans-serif;
            background-color: #f2f1eb;
            background-size: cover;
            background-position: center;
            justify-content: center;
            align-items: center;
            height: 100%;
    }
    h1{
            padding-top: 20px;
            text-align: center;
            margin-top: 25px;
            color: #092635;
        }
        table {
    width: 80%;
    margin: auto;
    border-collapse: collapse;
    box-shadow: 0 0 10px black;
    animation: fadeIn 1s ease-in-out;
    background-color: #afc8ad; 
    margin-bottom:70px;
}


th, td {
    border: 1px solid black;
    padding: 12px;
    text-align: left;
    transition: background-color 0.3s ease-in-out;
}

th {
    background-color: #092635;
    color: white; 
    animation: fadeInLeft 1s ease-in-out;
}
p {
        text-align: center;
        color: black;
        animation: blink 1s infinite alternate;
    }
    @keyframes blink {
        0% {
            opacity: 1;
        }
        100% {
            opacity: 0;
        }
    }
    tr{
transition: background-color 0.3s ease-in-out;
}
tr:hover {
    background-color: #40713c;
    animation: fadeIn 0.5s ease-in-out;
    color: white;
   
}



 @keyframes fadeIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }

    @keyframes fadeInUp {
        from {
            opacity: 0;
            transform: translateY(50px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    @keyframes fadeInLeft {
        from {
            opacity: 0;
            transform: translateX(-50px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }

</style>
</head>
<body>
    <h1>Task Details</h1>
    <table>
        <tr>
            <th>Employee ID</th>
            <th>Employee Name</th>
            <th>Role</th>
            <th>Project</th>
            <th>Task Date</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Task Category</th>
            <th>Description</th>
        </tr>
        <% 
        List<Task> taskList = (List<Task>) request.getAttribute("taskList");
        if (taskList != null && !taskList.isEmpty()) {
            for (Task task : taskList) {
        %>
            <tr>
                <td><%= task.getEmployeeId() %></td>
                <td><%= task.getEmployeeName() %></td>
                <td><%= task.getRole() %></td>
                <td><%= task.getProject() %></td>
                <td><%= task.getTaskDate() %></td>
                <td><%= task.getStartTime() %></td>
                <td><%= task.getEndTime() %></td>
                <td><%= task.getTaskCategory() %></td>
                <td><%= task.getDescription() %></td>
            </tr>
        <% 
            }
        } else { 
        %>
            <tr>
                <td colspan="9">No data found.</td>
            </tr>
        <% 
        } 
        %>
    </table>
</body>
</html>
