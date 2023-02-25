<%@ tag import="app.dto.PaginationDTO" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ attribute name="url" required="true" rtexprvalue="true" %>

<c:if test="${pagination.recordsCount > 0}">
    <div class="d-flex justify-content-center">
        <div class="card p-2 my-4" style="width: min-content;">
            <div class="d-flex">
                <div class="flex-fill">
                    <h6 class="text-start p-1 fw-normal text-muted"><t:localize key="recordsFound"/>: ${pagination.recordsCount}</h6>
                </div>
                <div class="flex-fill">
                    <h6 class="text-end p-1 fw-normal text-muted"><t:localize key="pagesCount"/>: ${pagination.pagesCount}</h6>
                </div>
            </div>

            <nav>
                <ul class="pagination mb-2">
                    <c:if test="${empty pagination.firstPage}">
                        <li class="page-item disabled"><a class="page-link"><t:localize key="first"/></a></li>
                    </c:if>
                    <c:if test="${not empty pagination.firstPage}">
                        <li class="page-item"><a class="page-link" href="${url}?page=${pagination.firstPage}&size=${pagination.pageSize}"><t:localize key="first"/></a></li>
                    </c:if>

                    <c:forEach var="page" items="${pagination.previousPages}">
                        <li class="page-item"><a class="page-link" href="${url}?page=${page}&size=${pagination.pageSize}">${page}</a></li>
                    </c:forEach>

                    <c:if test="${empty pagination.previousPage}">
                        <li class="page-item disabled"><a class="page-link"><t:localize key="previous"/></a></li>
                    </c:if>
                    <c:if test="${not empty pagination.previousPage}">
                        <li class="page-item"><a class="page-link" href="${url}?page=${pagination.previousPage}&size=${pagination.pageSize}"><t:localize key="previous"/></a></li>
                    </c:if>

                    <li class="page-item disabled"><a class="page-link">${pagination.pageNumber}</a></li>

                    <c:if test="${empty pagination.nextPage}">
                        <li class="page-item disabled"><a class="page-link"><t:localize key="next"/></a></li>
                    </c:if>
                    <c:if test="${not empty pagination.nextPage}">
                        <li class="page-item"><a class="page-link" href="${url}?page=${pagination.nextPage}&size=${pagination.pageSize}"><t:localize key="next"/></a></li>
                    </c:if>

                    <c:forEach var="page" items="${pagination.nextPages}">
                        <li class="page-item"><a class="page-link" href="${url}?page=${page}&size=${pagination.pageSize}">${page}</a></li>
                    </c:forEach>

                    <c:if test="${empty pagination.lastPage}">
                        <li class="page-item disabled"><a class="page-link"><t:localize key="last"/></a></li>
                    </c:if>
                    <c:if test="${not empty pagination.lastPage}">
                        <li class="page-item"><a class="page-link" href="${url}?page=${pagination.lastPage}&size=${pagination.pageSize}"><t:localize key="last"/></a></li>
                    </c:if>
                </ul>
            </nav>

            <div class="row">
                <div class="col pr-1">
                    <form class="input-group" action="${url}" method="get">
                        <button class="btn btn-primary" type="submit"><t:localize key="goTo"/></button>
                        <input type="hidden" name="size" value="${pagination.pageSize}">
                        <input class="form-control" name="page" type="number" step="1" min="1" max="${pagination.pagesCount}" value="${pagination.pageNumber}">
                    </form>
                </div>
                <div class="col pl-1">
                    <form class="input-group" action="${url}" method="get">
                        <button class="btn btn-primary" type="submit"><t:localize key="show"/></button>
                        <input class="form-control" name="size" type="number" step="1" min="2" max="20" value="${pagination.pageSize}">
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:if>