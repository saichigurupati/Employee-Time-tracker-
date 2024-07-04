<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
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
            width: 80%;
            height: 100%;
            background-image: url('www.gif');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            opacity: 1; /* Adjust the opacity value */
            z-index: -1; /* Ensure it's behind other content */
        }
    input[type="text"],
        input[type="password"] {
            width: 100%;
            background-color: white;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box; 
            color: #3c3633;/* Ensure padding and border are included in width */
        }
        button[type="submit"] {
            background-color: #207fd8;
            color: #f2f1eb;
            padding: 10px 15px;
            align-items: center;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            
            transition: background-color 0.3s ease;
        }
    form {
            background-color: rgba(238, 255, 255, 0.5);
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            margin: auto;
           
            margin-left: 750px;
            color:#092635;
            box-shadow: 0 0 10px #092635;
    }
    h2{
            margin-top: 130px;
            margin-left: 560px;
            padding-top: 20px;
            text-align: center;
            
            color: #092635;
        }
        button[type="submit"]:active {
    transform: translateY(2px); /* Push the button down slightly when clicked */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Add a shadow when clicked */
}
button[type="submit"]:hover {
            font-size: 18px;
            font-weight: bold;
        }
</style>
</head>
<body>
    <h2>Admin Login</h2>
    <form action="<%= request.getContextPath() %>/AdminServlet" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>

        <button type="submit">Login</button>
    </form>
</body>
</html>
