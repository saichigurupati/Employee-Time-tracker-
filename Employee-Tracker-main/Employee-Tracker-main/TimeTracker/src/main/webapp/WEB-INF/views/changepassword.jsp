<!-- ChangePassword.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Password</title>
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
    input[type="text"],[type="password"]{
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
<h2>Change Password</h2>
    <form action="<%= request.getContextPath() %>/ChangePasswordServlet" method="post" onsubmit="return validatePassword()">
        <label for="accountNumber">Employee Id:</label>
        <input type="text" id="accountNumber" name="accountNumber" required><br>
        <label for="oldPassword">Old Password:</label>
        <input type="password" id="oldPassword" name="oldPassword" required><br>
        <label for="newPassword">New Password:</label>
        <input type="password" id="newPassword" name="newPassword" required><br>
        <input type="submit" value="Change Password">
        
        <p><strong>NOTE:</strong>Password Should Contain more than 7 Characters and one Special Character Is Mandatory</p>
    </form>
    <script>
        function validatePassword() {
            var newPassword = document.getElementById("newPassword").value;
            if (newPassword.length < 8 || newPassword.length > 21) {
                alert("Password should be between 8 and 21 characters.");
                return false;
            }
            var specialCharacterRegex = /[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]/;
            if (!specialCharacterRegex.test(newPassword)) {
                alert("Password should contain at least one special character.");
                return false;
            }

            return true;
        }
    </script>
</body>
</html>
