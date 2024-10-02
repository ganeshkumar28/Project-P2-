<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Space</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Admin Header -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Admin Space</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">Manage Users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/products">Products</a>
                    </li>
                   
                </ul>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/profile">
						<i class="bi bi-person-circle"></i> Hi, ${sessionScope.name}
					</a></li>
					<li class="nav-item"><a class="btn btn-outline-danger ms-3"
						href="${pageContext.request.contextPath}/logout"> <i class="bi bi-box-arrow-right"></i> Logout
					</a></li>
            </div>
        </div>
    </nav>
</body>
</html>
