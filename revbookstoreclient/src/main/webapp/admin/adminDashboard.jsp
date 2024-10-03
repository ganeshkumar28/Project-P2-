<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RevBookStore Dashboard</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome Icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f6f9;
        }
        .wrapper {
            display: flex;
            width: 100%;
            height: auto;
            min-height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #333;
            color: #fff;
            padding-top: 20px;
            position: sticky;
            top: 0;
            height: 100%;
            min-height: 100vh;
            border-right: 1px solid #e0e0e0;
        }
        .sidebar h3 {
            text-align: center;
            font-weight: bold;
            color: #ffffff;
        }
        .sidebar ul {
            list-style-type: none;
            padding: 0;
        }
        .sidebar ul li {
            padding: 15px 20px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .sidebar ul li:hover {
            background-color: #009688;
        }
        .sidebar ul li a {
            color: #fff;
            text-decoration: none;
            font-size: 16px;
            display: block;
        }
        .main-content {
            flex-grow: 1;
            padding: 20px;
            background-color: #f9f9f9;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #fff;
            padding: 15px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
        .header h1 {
            font-size: 24px;
            color: #333;
        }
        .header button {
            background-color: #ff4d4d;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 20px;
            cursor: pointer;
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .header button:hover {
            background-color: #ff1a1a;
            box-shadow: 0 6px 8px rgba(0, 0, 0, 0.2);
        }
        .card {
            border: none;
            background-color: #fff;
            border-radius: 15px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            text-align: center;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .card h5 {
            font-size: 18px;
            margin-bottom: 15px;
            color: #333;
        }
        .card p {
            font-size: 24px;
            font-weight: bold;
            margin: 0;
            color: #333;
        }
        .row {
            margin-top: 30px;
        }
        .chart-container {
            padding: 20px;
            background-color: #fff;
            border-radius: 15px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <!-- Sidebar -->
        <div class="sidebar">
            <h3>RevBookStore Dashboard</h3>
            <ul>
                <li><a href="viewBuyers.jsp"><i class="fas fa-users"></i> Buyers</a></li>
                <li><a href="viewSellers.jsp"><i class="fas fa-store"></i> Sellers</a></li>
                <li><a href="viewComplaints.jsp"><i class="fas fa-exclamation-circle"></i> Complaints</a></li>
                <li><a href="viewOrders.jsp"><i class="fas fa-box"></i> Orders</a></li>
            </ul>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <div class="header">
                <h1>Dashboard Overview</h1>
                <button class="btn btn-primary">Logout</button>
            </div>

            <!-- Cards Section -->
            <div class="row">
                <div class="col-md-3">
                    <div class="card">
                        <h5>Total Buyers</h5>
                        <p><%= request.getAttribute("noofcustomer") != null ? request.getAttribute("noofcustomer") : 0 %></p>
                        <a href="viewBuyers.jsp">View Buyers</a>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card">
                        <h5>Total Sellers</h5>
                        <p><%= request.getAttribute("noofproduct") != null ? request.getAttribute("noofproduct") : 0 %></p>
                        <a href="viewSellers.jsp">View Sellers</a>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card">
                        <h5>Total Complaints</h5>
                        <p><%= request.getAttribute("noofcomplaint") != null ? request.getAttribute("noofcomplaint") : 0 %></p>
                        <a href="viewComplaints.jsp">View Complaints</a>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card">
                        <h5>Total Orders</h5>
                        <p><%= request.getAttribute("nooforder") != null ? request.getAttribute("nooforder") : 0 %></p>
                        <a href="viewOrders.jsp">View Orders</a>
                    </div>
                </div>
            </div>

            <!-- Line Charts Section -->
            <div class="row">
                <div class="col-md-6 chart-container">
                    <canvas id="buyersChart"></canvas>
                </div>
                <div class="col-md-6 chart-container">
                    <canvas id="sellersChart"></canvas>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 chart-container">
                    <canvas id="complaintsChart"></canvas>
                </div>
                <div class="col-md-6 chart-container">
                    <canvas id="ordersChart"></canvas>
                </div>
            </div>
        </div>
    </div>

    <script>
        const buyersCount = <%= request.getAttribute("noofcustomer") != null ? request.getAttribute("noofcustomer") : 0 %>;
        const sellersCount = <%= request.getAttribute("noofproduct") != null ? request.getAttribute("noofproduct") : 0 %>;
        const complaintsCount = <%= request.getAttribute("noofcomplaint") != null ? request.getAttribute("noofcomplaint") : 0 %>;
        const ordersCount = <%= request.getAttribute("nooforder") != null ? request.getAttribute("nooforder") : 0 %>;

        const createLineChart = (ctx, label, data, bgColor, borderColor) => {
            return new Chart(ctx, {
                type: 'line',
                data: {
                    labels: [label],
                    datasets: [{
                        label: label,
                        data: [data],
                        backgroundColor: bgColor,
                        borderColor: borderColor,
                        borderWidth: 2,
                        fill: true // Fill the area under the line
                    }]
                },
                options: { scales: { y: { beginAtZero: true } } }
            });
        };

        createLineChart(document.getElementById('buyersChart'), 'Buyers', buyersCount, 'rgba(54, 162, 235, 0.2)', 'rgba(54, 162, 235, 1)');
        createLineChart(document.getElementById('sellersChart'), 'Sellers', sellersCount, 'rgba(75, 192, 192, 0.2)', 'rgba(75, 192, 192, 1)');
        createLineChart(document.getElementById('complaintsChart'), 'Complaints', complaintsCount, 'rgba(255, 99, 132, 0.2)', 'rgba(255, 99, 132, 1)');
        createLineChart(document.getElementById('ordersChart'), 'Orders', ordersCount, 'rgba(153, 102, 255, 0.2)', 'rgba(153, 102, 255, 1)');
    </script>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>
