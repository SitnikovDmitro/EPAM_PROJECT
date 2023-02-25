<%@ page import="app.dto.ReaderDTO" %>
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
        <pf:adminBlockReaderModal/>
        <pf:adminUnblockReaderModal/>
        <pf:adminPayReaderFineModal/>



        <pf:adminNavbar activePage="readers"/>



        <div class="container-fluid col-lg-8 col-md-12">

            <div class="card mt-4">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" role="tab"><t:localize key="search"/></a>
                        </li>
                    </ul>
                </div>

                <div class="tab-content">
                    <div class="tab-pane fade show active" id="basic" role="tabpanel">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin/readers/find" method="post">
                                <div class="row">
                                    <div class="col-6 pr-1">
                                        <input type="text" class="form-control" name="query" placeholder="<t:localize key='nameOrEmailQuery'/>" value="${query}">
                                    </div>

                                    <div class="col-2 px-1">
                                        <div class="d-flex h-100 justify-content-center align-items-center">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" name="blockedOnly" value="true" id="onlyBlockedCheckbox" ${(blockedOnly == true)?'checked':''}>
                                                <label class="form-check-label" for="onlyBlockedCheckbox"><t:localize key="onlyBlocked"/></label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-2 px-1">
                                        <div class="d-flex h-100 justify-content-center align-items-center">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" name="withFineOnly" value="true" id="onlyWithFineCheckbox" ${(withFineOnly == true)?'checked':''}>
                                                <label class="form-check-label" for="onlyWithFineCheckbox"><t:localize key="onlyWithFine"/></label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-2 pl-1">
                                        <button class="btn btn-primary btn-block" type="submit"><t:localize key="search"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>





            <!---------Readers list--------->
            <c:choose>
                <c:when test = "${empty readers}">

                   <h3 class="text-center mt-4" style="color: white;"><t:localize key="readersNotFound"/></h3>

                </c:when>
                <c:otherwise>

                    <h3 class="text-center mt-4" style="color: white;"><t:localize key="searchingResults"/></h3>


                    <div class="card mt-4">

                        <div class="card-body pb-0 pt-0">
                            <div class="row">
                                <div class="col-3 py-3 border-right">
                                    <h6 class="card-text"><t:localize key="fullname"/></h6>
                                </div>
                                <div class="col-3 py-3 border-right">
                                    <h6 class="card-text"><t:localize key="email"/></h6>
                                </div>
                                <div class="col-2 py-3 border-right">
                                    <h6 class="card-text"><t:localize key="fine"/></h6>
                                </div>
                                <div class="col-2 py-3 border-right">
                                    <h6 class="card-text"><t:localize key="block"/></h6>
                                </div>
                                <div class="col-2 py-3">
                                    <h6 class="card-text"><t:localize key="payFine"/></h6>
                                </div>
                            </div>
                        </div>

                        <c:forEach var="reader" items="${readers}">
                            <div class="card-body border-top pb-0 pt-0">
                                <div class="row">
                                    <div class="col-3 py-3 border-right">
                                        <div class="d-flex h-100 align-items-center">
                                            <h6 class="card-text">${reader.fullname}</h6>
                                        </div>
                                    </div>
                                    <div class="col-3 py-3 border-right">
                                        <div class="d-flex h-100 align-items-center">
                                            <h6 class="card-text text-muted">${reader.email}</h6>
                                        </div>
                                    </div>
                                    <div class="col-2 py-3 border-right">
                                        <div class="d-flex h-100 align-items-center">
                                            <h6 class="card-text text-muted">${reader.fine} <t:localize key="grn"/></h6>
                                        </div>
                                    </div>
                                    <div class="col-2 py-3 border-right">
                                        <c:if test="${!reader.isBlocked}">
                                            <button type="button" class="btn btn-success btn-block btn-sm" onclick="openBlockReaderModal(${reader.id}, '${reader.fullname}');"><i class="bi bi-lock-fill"></i></button>
                                        </c:if>
                                        <c:if test="${reader.isBlocked}">
                                            <button type="button" class="btn btn-warning btn-block btn-sm" onclick="openUnblockReaderModal(${reader.id}, '${reader.fullname}');"><i class="bi bi-unlock-fill"></i></button>
                                        </c:if>
                                    </div>
                                    <div class="col-2 py-3">
                                        <c:if test="${reader.fine != '0'}">
                                            <button type="button" class="btn btn-primary btn-block btn-sm" onclick="openPayReaderFineModal(${reader.id}, '${reader.fullname}');"><t:localize key="payFine"/></button>
                                        </c:if>
                                        <c:if test="${reader.fine == '0'}">
                                            <button type="button" class="btn btn-primary btn-block btn-sm" disabled><t:localize key="payFine"/></button>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                    </div>


                </c:otherwise>
            </c:choose>



            <f:pagination url="${pageContext.request.contextPath}/admin/readers/show"/>
        </div>

        <f:footer/>
    </body>
</html>
