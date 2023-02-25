<%@ page import="app.dto.LibrarianDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/admin" prefix="pf" %>
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
        <script src="${pageContext.request.contextPath}/static/js/admin-scripts.js"></script>
        <link href="${pageContext.request.contextPath}/static/css/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body class="d-flex flex-column min-vh-100" style="background-image: url('${pageContext.request.contextPath}/static/img/background.jpg'); background-repeat: no-repeat; background-attachment: fixed;">
        <pf:adminCreateLibrarianModal/>
        <pf:adminDeleteLibrarianModal/>



        <pf:adminNavbar activePage="librarians"/>



        <div class="container-fluid col-lg-8 col-md-12">

            <div class="card mt-4">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" data-bs-toggle="tab" href="#searchTabPanel" role="tab" ><t:localize key="search"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-bs-toggle="tab" href="#creationTabPanel" role="tab"><t:localize key="new"/></a>
                        </li>
                    </ul>
                </div>

                <div class="tab-content">
                    <div class="tab-pane fade show active" id="searchTabPanel" role="tabpanel">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin/librarians/find" method="post">
                                <div class="row">
                                    <div class="col-9 pr-1">
                                        <input type="text" class="form-control" name="query" placeholder="<t:localize key='nameOrEmailQuery'/>" value="${query}">
                                    </div>

                                    <div class="col-3 pl-1">
                                        <button type="submit" class="btn btn-block btn-primary"><t:localize key="search"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane fade" id="creationTabPanel" role="tabpanel">
                        <div class="card-body">
                            <button type="button" class="btn btn-primary btn-block" onclick="openCreateLibrarianModal();"><t:localize key="createLibrarian"/></button>
                        </div>
                    </div>
                </div>
            </div>





            <!---------Librarians list--------->
            <c:choose>
                <c:when test = "${empty librarians}">

                   <h3 class="text-center mt-4" style="color: white;"><t:localize key="librariansNotFound"/></h3>

                </c:when>
                <c:otherwise>

                    <h3 class="text-center mt-4" style="color: white;"><t:localize key="searchingResults"/></h3>


                    <div class="card mt-4">

                        <div class="card-body pb-0 pt-0">
                            <div class="row">
                                <div class="col-5 py-3 border-right">
                                    <h6 class="card-text"><t:localize key="fullname"/></h6>
                                </div>
                                <div class="col-5 py-3 border-right">
                                    <h6 class="card-text"><t:localize key="email"/></h6>
                                </div>
                                <div class="col-2 py-3">
                                    <h6 class="card-text"><t:localize key="delete"/></h6>
                                </div>
                            </div>
                        </div>

                        <c:forEach var="librarian" items="${librarians}">
                            <div class="card-body border-top pb-0 pt-0">
                                <div class="row">
                                    <div class="col-5 py-3 border-right">
                                        <div class="d-flex h-100 align-items-center">
                                            <h6 class="card-text">${librarian.fullname}</h6>
                                        </div>
                                    </div>
                                    <div class="col-5 py-3 border-right">
                                        <div class="d-flex h-100 align-items-center">
                                            <h6 class="card-text text-muted">${librarian.email}</h6>
                                        </div>
                                    </div>
                                    <div class="col-2 py-3">
                                        <button type="button" class="btn btn-success btn-block btn-sm" onclick="openDeleteLibrarianModal(${librarian.id}, '${librarian.fullname}');"><i class="bi bi-trash-fill"></i></button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                    </div>


                </c:otherwise>
            </c:choose>



            <f:pagination url="${pageContext.request.contextPath}/admin/librarians/show"/>
        </div>

        <f:footer/>
    </body>
</html>