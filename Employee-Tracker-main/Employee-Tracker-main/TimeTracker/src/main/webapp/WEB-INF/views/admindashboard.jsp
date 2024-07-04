<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin DashBoard</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f2f1eb;
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
    }
    h1 {
        text-align: center;
        margin-top: 50px;
        color: #092635;
    }
   
    ul {
        list-style-type: none;
        padding: 7px;
        margin: 0;
        border-radius: 10px;
       
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
        background-color:  #afc8ad; 
        border: 2px solid #092635;
        box-shadow: 0 0 10px #092635;
    }
    li {
        margin-right: 1rem;
        margin-top: 10px;
        margin-bottom: 10px;
    }
    button {
        background-color: #092635;
        color: #f2f1eb; /* Change button text color to black */
        padding: 10px 10px;
        
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease, color 0.3s ease, font-size 0.3s ease, font-weight 0.3s ease;
    }
    button a {
        text-decoration: none; 
        color: white;/* Remove underline */
        /* Change hyperlink color to black */
    }
    button a:hover {
        color: #092635;/* Remove underline */
        /* Change hyperlink color to black */
    }
    button:hover {
        
        
            background-color: #f2f1eb;
            color: #092635;
        
        font-size: 18px;
    font-weight: bold;
    }
    p {
        text-align: center;
        color: #092635;
        animation: blink 1s infinite alternate; /* Add blinking animation */
    }
    @keyframes blink {
        0% {
            opacity: 1;
        }
        100% {
            opacity: 0;
        }
    }
    
</style>
</head>
<body>
<h1>Welcome to the Admin Dashboard</h1>
    <p>This is a secure area for administrators.</p>

    <ul>
     <li><button><a href="<%= request.getContextPath() %>/AddEmployee">Add Employee</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/AddTaskServlet">Add Task</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/GenerateServlet">Charts</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/ViewServlet">View Task Details</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/EditServlet">Edit Task Details</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/CompletedServlet">Task Completion</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/DeleteServlet">Delete Task</a></button></li>
    </ul>
</body>
</html>