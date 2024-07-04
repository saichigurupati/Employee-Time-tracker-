<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Task Duration</title>
    <script>
function showTotalDuration() {
    var accountNumber = <%= request.getSession().getAttribute("accountNumber") %>;
    var selection = document.querySelector('input[name="selection"]:checked').value;
    
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "<%= request.getContextPath() %>/register/EmployeeViewServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("totalDuration").innerHTML = "Total Duration: " + xhr.responseText + " hours";
        }
    };
    xhr.send("accountNumber=" + accountNumber + "&selection=" + selection);
}
</script>
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
    h1{
            padding-top: 20px;
            text-align: center;
            margin-top: 25px;
            color: #092635;
        }
        input[type="number"]{
            width: 100%;
            background-color: white;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box; 
            color: #3c3633;/* Ensure padding and border are included in width */
        }
        input[type="radio"]{
            margin-bottom: 10px;
        }
        button[type="button"] {
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
        button[type="button"]:active {
    		transform: translateY(2px); /* Push the button down slightly when clicked */
    		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Add a shadow when clicked */
		}
		button[type="button"]:hover {
            background-color: #f2f1eb;
            color: #092635;
        }
        div[id="totalDuration"]{
        text-align: center;
		margin-top: 20px;
    	}
</style>
</head>
<body>
<%
    if (request.getSession().getAttribute("loggedIn") != null && (boolean) request.getSession().getAttribute("loggedIn")) {
%>
    <h1>Welcome to your View Task Duration, <%= request.getSession().getAttribute("accountNumber") %>!</h1>
<form>
    <label for="daily">Daily:</label>
    <input type="radio" id="daily" name="selection" value="daily">
    <label for="weekly">Weekly:</label>
    <input type="radio" id="weekly" name="selection" value="weekly">
    <label for="monthly">Monthly:</label>
    <input type="radio" id="monthly" name="selection" value="monthly"><br>
    <button type="button" onclick="showTotalDuration()">Calculate Total Duration</button>
</form>
<div id="totalDuration"></div>

    <%
    } else {
%>
    <p>You are not logged in. Please log in to access the dashboard.</p>
<%
    }
%>
</body>
</html>
