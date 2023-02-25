<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!doctype html>
<html lang="en">
    <head>
         <meta charset="utf-8">
         <meta name="viewport" content="width=device-width, initial-scale=1">

         <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/img/favicon.ico">

         <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
         <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
    </head>
    <body style="background-image: url('${pageContext.request.contextPath}/static/img/background.jpg'); background-repeat: no-repeat; background-attachment: fixed;">
        <div class="container col-lg-4 col-md-5 col-sm-6 p-4">
            <div class="card">
                <img class="card-img-top" src="${pageContext.request.contextPath}/static/img/error.png" alt="Error image">

                <div class="card-body border-top">
                    <h4 class="text-center my-0">${code}</h4>
                </div>

                <div class="card-body border-top border-bottom">
                    <h5 class="text-center my-0">${description}</h5>
                </div>

                <div class="card-body">
                    <a type="button" href="${pageContext.request.contextPath}/guest/home-page/show" class="btn btn-primary">Return</a>
                </div>
            </div>
        </div>
    </body>
</html>