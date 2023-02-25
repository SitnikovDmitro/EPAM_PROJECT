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
        <pf:readerCancelOrderModal/>
        <pf:readerDeleteOrderModal/>
        <pf:readerPayFineModal/>



        <pf:readerNavbar activePage="orders"/>



        <div class="container-fluid col-lg-8 col-md-12">
            <c:choose>
                <c:when test="${empty orders}">

                    <h3 class="text-center mt-4" style="color: white;"><t:localize key="noOrdersYet"/></h3>

                </c:when>
                <c:otherwise>

                    <h3 class="text-center mt-4" style="color: white;"><t:localize key="myOrders"/></h3>

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
                                                        <c:if test="${order.state == 'WAITING_FOR_CONFIRMATION' || order.state == 'CANCELLED' || order.state == 'CLOSED'}">
                                                            <div class="dropdown">
                                                                <button class="btn btn-primary btn-block mt-3 dropdown-toggle" type="button" data-bs-toggle="dropdown"><t:localize key="actions"/></button>
                                                                <ul class="dropdown-menu">
                                                                    <c:if test="${order.state == 'WAITING_FOR_CONFIRMATION'}">
                                                                        <li>
                                                                            <button class="dropdown-item" type="button" onclick="openCancelOrderModal(${order.code});"><t:localize key="cancelOrder"/></button>
                                                                        </li>
                                                                    </c:if>
                                                                    <c:if test="${order.state == 'CANCELLED' || order.state == 'CLOSED'}">
                                                                        <li>
                                                                            <button class="dropdown-item" type="button" onclick="openDeleteOrderModal(${order.code});"><t:localize key="deleteOrder"/></button>
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



            <f:pagination url="${pageContext.request.contextPath}/reader/orders/show"/>
        </div>

        <f:footer/>
    </body>
</html>