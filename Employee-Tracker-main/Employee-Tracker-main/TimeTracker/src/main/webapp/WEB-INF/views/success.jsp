<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Success</title>
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
           animation: blink 1s infinite alternate;
       }
       p{
        text-align: center;
           margin-top: 25px;
          
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
    <h1>Registration Successful!</h1>
    <p>Your account number: <b>${accountNumber}</b></p>
    <p>Your password: <b>${password}</b></p>
</body>
</html>
