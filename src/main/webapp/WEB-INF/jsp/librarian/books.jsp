<%@ page import="app.dto.BookBriefDTO"%>
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



        <pf:librarianNavbar activePage="books"/>



        <div class="container-fluid col-lg-8 col-md-12">
            <div class="card mt-4">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" role="tab"><t:localize key="search"/></a>
                        </li>
                    </ul>
                </div>

                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/librarian/books/find" method="post" id="advancedSearchBooksForm">
                        <div class="row">
                            <div class="col-9 pr-1">
                                <input type="text" class="form-control" name="query" value="${query}" placeholder="<t:localize key='query'/>">
                            </div>

                            <div class="col-3 pl-1">
                                <button type="submit" class="btn btn-block btn-primary"><t:localize key="search"/></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>




            <!---------Book list--------->
            <c:choose>
                <c:when test = "${empty books}">

                    <h3 class="text-center mt-4" style="color: white;"><t:localize key="booksNotFound"/></h3>

                </c:when>
                <c:otherwise>

                    <h3 class="text-center mt-4" style="color: white;"><t:localize key="searchingResults"/></h3>

                    <div class="row row-cols-lg-4 row-cols-md-3 row-cols-sm-2 row-cols-xs-1 g-4 mt-2">
                        <c:forEach var="book" items="${books}">
                            <div class="col">
                                <div class="card border-white border-3 h-100">
                                    <img class="card-img-top" src="${pageContext.request.contextPath}/book/cover?bookId=${book.id}" alt="Book cover image" height="250">
                                    <div class="card-body h-100">
                                        <div class="d-flex flex-column justify-content-between h-100">
                                            <div class="flex-shrink-1">
                                                <h5 class="card-title">${book.title}</h5>
                                                <h6 class="card-subtitle mb-2 text-muted">${book.propertiesText}</h6>
                                            </div>

                                            <div class="flex-shrink-1">
                                                <h6 class="text-muted property-text">
                                                  <span class="lspan"><t:localize key="freeCopies"/></span><span class="mspan"></span><span class="rspan">${book.freeCopiesNumber}</span>
                                                </h6>
                                                <h6 class="text-muted property-text mb-0">
                                                  <span class="lspan"><t:localize key="totalCopies"/></span><span class="mspan"></span><span class="rspan">${book.totalCopiesNumber}</span>
                                                </h6>
                                            </div>

                                            <div class="flex-shrink-1">
                                                <h5 class="card-text mb-3 mt-2">
                                                    <i class="${book.averageGrade>=1?'bi-star-fill':'bi-star'}"></i>
                                                    <i class="${book.averageGrade>=2?'bi-star-fill':'bi-star'}"></i>
                                                    <i class="${book.averageGrade>=3?'bi-star-fill':'bi-star'}"></i>
                                                    <i class="${book.averageGrade>=4?'bi-star-fill':'bi-star'}"></i>
                                                    <i class="${book.averageGrade>=5?'bi-star-fill':'bi-star'}"></i>
                                                </h5>
                                                <form action="${pageContext.request.contextPath}/librarian/book/select" method="post">
                                                    <input type="hidden" name="bookId" value="${book.id}">
                                                    <button type="submit" class="btn btn-primary btn-block"><t:localize key="select"/></button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                </c:otherwise>
            </c:choose>



            <f:pagination url="${pageContext.request.contextPath}/librarian/books/show"/>
        </div>

        <f:footer/>
    </body>
</html>