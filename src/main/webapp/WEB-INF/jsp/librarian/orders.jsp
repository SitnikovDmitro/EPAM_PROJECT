<%@ page import="app.dto.OrderDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/librarian" prefix="pf" %>
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
        <script src="${pageContext.request.contextPath}/static/js/librarian-scripts.js"></script>
        <link href="${pageContext.request.contextPath}/static/css/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body class="d-flex flex-column min-vh-100" style="background-image: url('${pageContext.request.contextPath}/static/img/background.jpg'); background-repeat: no-repeat; background-attachment: fixed;">
        <pf:librarianCancelOrderModal/>
        <pf:librarianConfirmOrderModal/>
        <pf:librarianCloseOrderModal/>


        <pf:librarianNavbar activePage="orders"/>



        <div class="container-fluid col-lg-8 col-md-12">
            <div class="card mt-4">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" role="tab" id="searchTab" data-bs-toggle="tab" href="#searchTabPanel"><t:localize key="search"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" role="tab" id="reportsTab" data-bs-toggle="tab" href="#reportsTabPanel"><t:localize key="reports"/></a>
                        </li>
                    </ul>
                </div>

                <div class="tab-content">
                    <div class="tab-pane fade show active" id="searchTabPanel" role="tabpanel">
                        <div class="card-body">

                            <div class="row">
                                <div class="col-6 pr-1">
                                    <form action="${pageContext.request.contextPath}/librarian/reader/select" method="post">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><t:localize key="reader"/></span>
                                            </div>
                                            <input type="text" class="form-control" value="${readerFullname}" disabled>
                                            <button type="submit" class="btn btn-primary"><t:localize key="clear"/></button>
                                            <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/librarian/readers/show"><t:localize key="select"/></a>
                                        </div>
                                   </form>
                                </div>


                                <div class="col-6 pl-1">
                                    <form action="${pageContext.request.contextPath}/librarian/book/select" method="post">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><t:localize key="book"/></span>
                                            </div>
                                            <input type="text" class="form-control" value="${bookTitle}" disabled>
                                            <button type="submit" class="btn btn-primary"><t:localize key="clear"/></button>
                                            <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/librarian/books/show"><t:localize key="select"/></a>
                                        </div>
                                    </form>
                                </div>
                            </div>

                            <form action="${pageContext.request.contextPath}/librarian/orders/find" method="post">
                                <div class="row mt-2">

                                    <div class="col-2 pr-1">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><t:localize key="code"/></span>
                                            </div>
                                            <input type="text" class="form-control" name="code" value="${code}">
                                        </div>
                                    </div>

                                    <div class="col-3 px-1">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><t:localize key="state"/></span>
                                            </div>
                                            <select class="custom-select" name="state">
                                                <option ${(state == null)?'selected':''} value=""><t:localize key="all"/></option>
                                                <option ${(state == 0)?'selected':''} value="0"><t:localize key="waitingForConfirmation"/></option>
                                                <option ${(state == 1)?'selected':''} value="1"><t:localize key="confirmed"/></option>
                                                <option ${(state == 2)?'selected':''} value="2"><t:localize key="closed"/></option>
                                                <option ${(state == 3)?'selected':''} value="3"><t:localize key="cancelled"/></option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-5 px-1">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><t:localize key="sortBy"/></span>
                                            </div>
                                            <select class="custom-select" name="sortCriteria">
                                                <option ${(sortCriteria == 0)?'selected':''} value="0"><t:localize key="creationDateFromOlderToNewer"/></option>
                                                <option ${(sortCriteria == 1)?'selected':''} value="1"><t:localize key="creationDateFromNewerToOlder"/></option>
                                                <option ${(sortCriteria == 2)?'selected':''} value="2"><t:localize key="deadlineDateFromOlderToNewer"/></option>
                                                <option ${(sortCriteria == 3)?'selected':''} value="3"><t:localize key="deadlineDateFromNewerToOlder"/></option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-2 pl-1">
                                        <button type="submit" class="btn btn-primary btn-block"><t:localize key="search"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane" id="reportsTabPanel" role="tabpanel">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-6 pr-1">
                                    <a type="button" class="btn btn-block btn-primary" href="${pageContext.request.contextPath}/librarian/generate-book-data-table"><t:localize key="generateBookDataTable"/></a>
                                </div>
                                <div class="col-6 pl-1">
                                    <a type="button" class="btn btn-block btn-primary" href="${pageContext.request.contextPath}/librarian/generate-reader-data-table"><t:localize key="generateReaderDataTable"/></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>










            <!---------Order list--------->
            <c:choose>
                <c:when test="${empty orders}">

                    <h3 class="text-center mt-4" style="color: white;"><t:localize key="ordersNotFound"/></h3>

                </c:when>
                <c:otherwise>

                    <h3 class="text-center mt-4" style="color: white;"><t:localize key="searchingResults"/></h3>

                    <div class="row row-cols-lg-3 row-cols-md-2 row-cols-sm-1 g-4 mt-2">
                        <c:forEach var="order" items="${orders}">
                            <div class="col">
                                <div class="card border-white border-3 h-100">
                                    <div class="row">
                                        <div class="col-5 pr-0">
                                            <img src="${pageContext.request.contextPath}/book/cover?bookId=${order.bookId}" style="border-top-left-radius: 4px; height: 100%; width: 100%;" class="w-100 h-100 mw-100 mh-100" alt="Book cover">
                                        </div>

                                        <div class="col-7">
                                            <div class="card-body h-100 pl-1">
                                                <div class="d-flex flex-column justify-content-between h-100">
                                                    <div>
                                                        <h5 class="card-title">${order.code}</h5>
                                                        <h6 class="card-subtitle text-muted">${order.bookTitle}</h6>
                                                    </div>

                                                    <div>
                                                        <c:if test="${order.state == 'WAITING_FOR_CONFIRMATION' || order.state == 'CONFIRMED'}">
                                                            <div class="dropdown">
                                                                <button class="btn btn-primary btn-block mt-3 dropdown-toggle" type="button" data-bs-toggle="dropdown"><t:localize key="actions"/></button>
                                                                <ul class="dropdown-menu">
                                                                    <c:if test="${order.state == 'WAITING_FOR_CONFIRMATION'}">
                                                                        <li>
                                                                            <button class="dropdown-item" type="button" onclick="openCancelOrderModal(${order.code});"><t:localize key="cancelOrder"/></button>
                                                                        </li>
                                                                        <c:if test="${order.bookHasFreeCopies}">
                                                                            <li>
                                                                                <li><button class="dropdown-item" type="button" onclick="openConfirmOrderModal(${order.code}, ${order.bookIsValuable});"><t:localize key="confirmOrder"/></button></li>
                                                                            </li>
                                                                        </c:if>
                                                                    </c:if>
                                                                    <c:if test="${order.state == 'CONFIRMED'}">
                                                                        <li>
                                                                            <li><button class="dropdown-item" type="button" onclick="openCloseOrderModal(${order.code});"><t:localize key="closeOrder"/></button></li>
                                                                        </li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body border-top">
                                        <h6 class="text-muted property-text"><span class="lspan"><t:localize key="createdBy"/></span><span class="mspan"></span><span class="rspan">${order.userFullname}</span></h6>
                                        <c:if test="${not empty order.creationDate}">
                                            <h6 class="text-muted property-text"><span class="lspan"><t:localize key="createdAt"/></span><span class="mspan"></span><span class="rspan">${order.creationDate}</span></h6>
                                        </c:if>
                                        <c:if test="${not empty order.deadlineDate}">
                                            <h6 class="text-muted property-text"><span class="lspan"><t:localize key="expiresAt"/></span><span class="mspan"></span><span class="rspan">${order.deadlineDate}</span></h6>
                                        </c:if>
                                        <h6 class="text-muted property-text"><span class="lspan"><t:localize key="state"/></span><span class="mspan"></span><span class="rspan">${order.stateText}</span></h6>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                </c:otherwise>
            </c:choose>

            <f:pagination url="${pageContext.request.contextPath}/librarian/orders/show"/>
        </div>

        <f:footer/>
    </body>
</html>
