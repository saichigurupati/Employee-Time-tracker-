<%@ page import="java.util.List" %>
<%@ page import="register.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Leaderboard</title>
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
        table {
    width: 80%;
    margin: auto;
    border-collapse: collapse;
    box-shadow: 0 0 10px black;
    animation: fadeIn 1s ease-in-out;
    background-color: #afc8ad; 
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
    <h2>Leaderboard</h2>
    <% 
    List<Employee> leaderboard = (List<Employee>) request.getAttribute("leaderboard");
    if (leaderboard != null && !leaderboard.isEmpty()) {
    %>
        <table border="1">
            <thead>
                <tr>
                    <th>Employee ID</th>
                    <th>Total Hours</th>
                </tr>
            </thead>
            <tbody>
                <% for (Employee employee : leaderboard) { %>
                    <tr>
                        <td><%= employee.getId() %></td>
                        <td><%= employee.getHours() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } else { %>
        <p>No data found.</p>
    <% } %>
</body>
</html>
