<%@ tag import="app.dto.BookBriefDTO" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>

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
                                    <h5 class="card-text mb-3 mt-2">
                                        <i class="${book.averageGrade>=1?'bi-star-fill':'bi-star'}"></i>
                                        <i class="${book.averageGrade>=2?'bi-star-fill':'bi-star'}"></i>
                                        <i class="${book.averageGrade>=3?'bi-star-fill':'bi-star'}"></i>
                                        <i class="${book.averageGrade>=4?'bi-star-fill':'bi-star'}"></i>
                                        <i class="${book.averageGrade>=5?'bi-star-fill':'bi-star'}"></i>
                                    </h5>
                                    <button class="btn btn-primary btn-block" onclick="openBookDetailsModal(${book.id}, '${pageContext.request.contextPath}');"><t:localize key="show"/></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </c:otherwise>
</c:choose>