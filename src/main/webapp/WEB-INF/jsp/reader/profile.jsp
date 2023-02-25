<%@ page import="app.dto.OrderDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/reader" prefix="pf" %>
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
        <script src="${pageContext.request.contextPath}/static/js/reader-scripts.js"></script>
        <link href="${pageContext.request.contextPath}/static/css/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body class="d-flex flex-column min-vh-100" style="background-image: url('${pageContext.request.contextPath}/static/img/background.jpg'); background-repeat: no-repeat; background-attachment: fixed;">
        <pf:readerPayFineModal/>



        <pf:readerNavbar activePage="profile"/>



        <div class="container-fluid col-lg-5 col-md-12">
            <div class="card mt-4 mb-4">
                <div class="card-body border-bottom">
                    <div class="row mb-3">
                        <div class="col pr-2">
                            <label for="firstnameInput" class="form-label"><t:localize key="firstname"/></label>
                            <input type="text" class="form-control" id="firstnameInput" name="firstname" value="${readerFirstname}">
                            <div class="invalid-feedback" id="firstnameInvalidFeedback"></div>
                            <div class="valid-feedback"><t:localize key="valid"/></div>
                        </div>
                        <div class="col pl-2">
                            <label for="lastnameInput" class="form-label"><t:localize key="lastname"/></label>
                            <input type="text" class="form-control" id="lastnameInput" name="lastname" value="${readerLastname}">
                            <div class="invalid-feedback" id="lastnameInvalidFeedback"></div>
                            <div class="valid-feedback"><t:localize key="valid"/></div>
                        </div>
                    </div>

                    <div>
                        <label for="emailInput" class="form-label"><t:localize key="email"/></label>
                        <input type="email" class="form-control" id="emailInput" name="title" value="${readerEmail}">
                        <div class="invalid-feedback" id="emailInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>
                </div>

                <div class="collapse card-body border-bottom" id="passwordEditionBlock">
                    <div class="row">
                        <div class="col pr-2">
                            <label for="passwordInput" class="form-label"><t:localize key="password"/></label>
                            <input type="password" class="form-control" id="passwordInput" name="password">
                            <div class="invalid-feedback" id="passwordInvalidFeedback"></div>
                            <div class="valid-feedback"><t:localize key="valid"/></div>
                        </div>
                        <div class="col pl-2">
                            <label for="passwordConfirmInput" class="form-label"><t:localize key="passwordConfirm"/></label>
                            <input type="password" class="form-control" id="passwordConfirmInput" name="passwordConfirm">
                            <div class="invalid-feedback"><t:localize key="passwordsDoNotMatch"/></div>
                            <div class="valid-feedback"><t:localize key="valid"/></div>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div class="d-flex h-100 w-100 justify-content-end">
                        <button type="button" class="btn btn-secondary mr-2"><t:localize key="clearChanges"/></button>
                        <button type="button" class="btn btn-secondary mr-2" data-bs-toggle="collapse" data-bs-target="#passwordEditionBlock"><t:localize key="editPasswordToo"/></button>
                        <button type="button" class="btn btn-primary" onclick="editProfile('${pageContext.request.contextPath}');"><t:localize key="submit"/></button>
                    </div>
                </div>
            </div>
        </div>



        <f:footer/>
    </body>
</html>