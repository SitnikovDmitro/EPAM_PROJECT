<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/guest" prefix="pf" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/img/favicon.ico">

        <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/static/css/bootstrap-min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/scripts.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/guest-scripts.js"></script>
        <link href="${pageContext.request.contextPath}/static/css/bootstrap-icons.css" rel="stylesheet">

        <script src="https://www.google.com/recaptcha/api.js"></script>
    </head>
    <body class="d-flex flex-column min-vh-100" style="background-image: url('${pageContext.request.contextPath}/static/img/background.jpg'); background-repeat: no-repeat; background-attachment: fixed;">
        <pf:guestMessageToast/>
        <pf:guestSendAccessLinkModal/>
        <pf:guestSignInModal/>
        <pf:guestSignUpModal/>



        <pf:guestNavbar activePage="homePage"/>



        <div class="container-fluid col-8">
            <div class="row mt-4">
                <div class="col">
                    <div class="card lc">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/static/img/easy.png" alt="Book cover image" style="border-radius: 50%;">
                        <div class="card-body">
                            <h2 class="text-center">Easy</h2>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card mc">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/static/img/free.png" alt="Book cover image" style="border-radius: 50%;">
                        <div class="card-body">
                            <h2 class="text-center">Free</h2>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card rc">
                        <img class="card-img-top" src="${pageContext.request.contextPath}/static/img/fast.png" alt="Book cover image" style="border-radius: 50%;">
                        <div class="card-body">
                            <h2 class="text-center">Fast</h2>
                        </div>
                    </div>
                </div>
            </div>

            <div class="d-flex justify-content-center">
                <div style="width: min-content;">
                    <h2 class="display-2 text-center text-nowrap" style="color: white;"><t:localize key="electronicLibraryService"/></h2>

                    <a class="btn btn-block btn-lg mt-3" style="background: linear-gradient(10deg, purple, orange) border-box; color: white;" href="${pageContext.request.contextPath}/guest/books/show"><t:localize key="showBookCatalog"/></a>
                </div>
            </div>
        </div>



        <f:footer/>
    </body>
</html>