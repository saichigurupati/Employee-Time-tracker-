<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Complete Task</title>
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
    h2{
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
    input[type="text"],input[type="time"],input[type="number"]{
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
    <h2>Complete Task</h2>
    <form action="CompletedServlet" method="post">
        Employee Id: <input type="text" id="employeeId" name="employeeId" required><br><br>
        Task Category: <input type="text" id="taskCategory" name="taskCategory" required><br><br>
		Start Time: <input type="time" id="startTime" name="startTime" required><br><br>
		End Time: <input type="time" id="endTime" name="endTime" required><br><br>
        
        <input type="submit" value="Submit">
    </form>
</body>
</html>
