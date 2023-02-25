<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!doctype html>
<html lang="en">
  <head>
     <meta charset="utf-8">
     <meta name="viewport" content="width=device-width, initial-scale=1">

     <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/img/favicon.ico">

     <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
     <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet">
     <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
     <script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
     <script src="${pageContext.request.contextPath}/static/js/scripts.js"></script>
  </head>
  <body style="background-image: url('${pageContext.request.contextPath}/static/img/background.jpg'); background-repeat: no-repeat; background-attachment: fixed;">

    <div class="row">
      <div class="container col-md-3">
        <div class="card mt-3">
          <div class="card-body">
            <h4 class="card-title text-center">${code}</h4>
          </div>

          <ul class="list-group list-group-flush">
            <li class="list-group-item">
              <h5 class="text-center">${description}</h5>
            </li>
          </ul>

          <div class="card-body">
            <a type="button" href="${pageContext.request.contextPath}/guest/home-page/show" class="btn btn-primary">Return</a>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>