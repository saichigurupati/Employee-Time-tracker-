<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee DashBoard</title>
<style>
    body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            user-select: none;
        }
        body::after {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width:100%;
            height:100%;
            background-image: url('rr.gif');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            opacity: 1; /* Adjust the opacity value */
            z-index: -1; /* Ensure it's behind other content */
        }
    h1{
        text-align: center;
        margin-top: 40px;
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
        background-color: rgba(238, 225, 225, 0.5); 
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
<%
    if (request.getSession().getAttribute("loggedIn") != null && (boolean) request.getSession().getAttribute("loggedIn")) {
%>
    <h1>Welcome to your Dashboard, <%= request.getSession().getAttribute("accountNumber") %>!</h1>

            <ul>
        <li><button><a href="<%= request.getContextPath() %>/EmployeeAddTaskServlet">Add Task</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/EmployeeChartServlet">Charts</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/EmployeeViewServlet">View Task Duration</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/EmployeeEditServlet">Edit Task</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/EmployeeDeleteServlet">Delete Task</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/ViewDetailsServlet">View Task Details</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/BoardServlet">LeaderBoard</a></button></li>
        <li><button><a href="<%= request.getContextPath() %>/LogutServlet">Logout</a></button></li>
    </ul>
<%
    } else {
%>
    <p>You are not logged in. Please log in to access the dashboard.</p>
<%
    }
%>
</body>
</html>