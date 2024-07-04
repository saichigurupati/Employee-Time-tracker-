<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Task</title>
<style>
    body{
        font-family: Arial, sans-serif;
            background-color: #f2f1eb;
            background-size: cover;
            background-position: center;
            justify-content: center;
            align-items: center;
            height: 100vh;
    }
    h1{
            padding-top: 20px;
            text-align: center;
            margin-top: 25px;
            color: #092635;
        }
    form {
            background-color: #afc8ad;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            width: 400px;
            margin: auto;
            color:#092635;
            box-shadow: 0 0 10px #092635;
    }
    input[type="text"],input[type="time"]{
            width: 100%;
            background-color: white;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box; 
            color: #3c3633;/* Ensure padding and border are included in width */
        }
        input[type="submit"] {
            background-color: #092635;
            color: #f2f1eb;
            padding: 10px 15px;
            align-items: center;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:active {
    		transform: translateY(2px); /* Push the button down slightly when clicked */
    		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Add a shadow when clicked */
		}
		input[type="submit"]:hover {
            background-color: #f2f1eb;
            color: #092635;
        }
</style>
</head>
<body>
<%
    if (request.getSession().getAttribute("loggedIn") != null && (boolean) request.getSession().getAttribute("loggedIn")) {
%>
    <h1>Welcome to your Task Edit Category, <%= request.getSession().getAttribute("accountNumber") %>!</h1>
    <form action="EmployeeEditServlet" method="post">
    Task Category to Edit: <input type="text" name="taskCategory"><br>
    New Task Category: <input type="text" name="newCategory"><br>
    New Task Description: <input type="text" name="newDescription"><br>
    New Start Time: <input type="time" name="newStartTime"><br>
    New End Time: <input type="time" name="newEndTime"><br>
    <input type="submit" value="Edit">
</form>
         <%
    } else {
%>
    <p>You are not logged in. Please log in to access the dashboard.</p>
<%
    }
%>
</body>
</html>
