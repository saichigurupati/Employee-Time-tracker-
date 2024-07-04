<!-- login.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
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
            width: 100%;
            height: 100%;
            background-image: url('bank.gif');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            opacity: 1; /* Adjust the opacity value */
            z-index: -1; /* Ensure it's behind other content */
        }
    form {
            width: 300px;
            background-color: rgba(238, 225, 225, 0.5); 
            margin: 0 auto;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top:50px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color:black;
            font-weight:bold;
        }

        input[type="text"],
        input[type="password"] {
            width: calc(100% - 20px);
            padding: 10px;
            color: black;
            margin-bottom: 15px;
            border: 2px solid black;
            border-radius: 5px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: black;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            font-weight: bold;
            font-size: 18px;
            
        }
    h2{
            padding-top: 20px;
            text-align: center;
            margin-top: 50px;
            color: #092635;
            
        }
        input[type="submit"]:active {
    transform: translateY(2px); /* Push the button down slightly when clicked */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Add a shadow when clicked */
}

        .forgot-password,
        .change-password {
            justify-content: center;
            display: inline-block;
            text-align: center;
            margin-top: 5px;
            text-decoration: none;
            color: #092635;
        }

        .forgot-password:hover,
        .change-password:hover {
            text-decoration: underline;
            color: #092635;
        }
        .password-links {
            text-align: center;
        }
</style>
</head>
<body>
<h2>Employee Login</h2>
    <form action="<%= request.getContextPath() %>/EmpLoginServlet" method="post">
        <label for="accountNumber">Employee Id:</label>
        <input type="text" id="accountNumber" name="accountNumber" required><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>
        <input type="submit" value="Login"><br>
        <div class="password-links">
        <a class="change-password" href="ChangePasswordServlet">Change Password</a><br>
        </div>
    </form>
</body>
</html>`