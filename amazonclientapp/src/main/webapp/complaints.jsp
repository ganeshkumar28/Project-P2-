<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Submit Complaint</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            width: 50%;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            background-color: #f9f9f9;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input, textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <h2>Submit a Complaint</h2>

    <!-- Form for submitting complaint -->
    <form action="${pageContext.request.contextPath}/buyer/submitcomplaint" method="post">
        <div class="form-group">
            <label for="userName">Your Name:</label>
            <input type="text" id="userName" name="username" class="form-control" required>
        </div>
        
        <div class="form-group">
            <label for="complaintText">Complaint:</label>
            <textarea id="complaintText" name="complaintText" rows="5" class="form-control" required></textarea>
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary">Submit Complaint</button>
            <!-- Optional link to cancel or go back to products -->
            <a href="${pageContext.request.contextPath}/buyer/submitcomplaint" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>

</body>
</html>
