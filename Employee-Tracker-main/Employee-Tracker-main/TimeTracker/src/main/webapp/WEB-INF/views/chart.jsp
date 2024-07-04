<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Duration in Chart</title>
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
      input[type="number"],select[id="chartType"]{
        width: 100%;
            background-color: white;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box; 
            color: #3c3633;/* Ensure padding and border are included in width */
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
        #chart {
        width: 50%;
        height: 300px;
        margin: 0 auto;
    }
</style>
    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <script>
        function generateChart() {
            var employeeName = document.getElementById("employeeId").value;
            var chartType = document.getElementById("chartType").value;

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "<%= request.getContextPath() %>/GenerateServlet", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var data = JSON.parse(xhr.responseText);
                    if (chartType === "daily") {
                        generatePieChart(data);
                    } else {
                        generateBarChart(data);
                    }
                }
            };
            xhr.send("employeeId=" + employeeName + "&chartType=" + chartType);
        }

        function generatePieChart(data) {
            var options = {
                chart: {
                    type: 'pie'
                },
                series: data.map(item => item.value),
                labels: data.map(item => item.label)
            };

            var chart = new ApexCharts(document.querySelector("#chart"), options);
            chart.render();
        }

        function generateBarChart(data) {
            var options = {
                chart: {
                    type: 'bar'
                },
                series: [{
                    name: 'Hours',
                    data: data.map(item => item.value)
                }],
                xaxis: {
                    categories: data.map(item => item.label),
                    labels: {
                        rotate: -90
                    }
                }
            };

            var chart = new ApexCharts(document.querySelector("#chart"), options);
            chart.render();
        }
    </script>
</head>
<body>
<h1>Charts</h1>
<form>
    Employee Id: <input type="number" id="employeeId"><br>
    Chart Type:
    <select id="chartType">
        <option value="daily">Daily (Pie Chart)</option>
        <option value="weekly">Weekly (Bar Chart)</option>
        <option value="monthly">Monthly (Bar Chart)</option>
    </select><br>
    <button type="button" onclick="generateChart()">Generate Chart</button>
</form>
<div id="chart"></div>
</body>
</html>
