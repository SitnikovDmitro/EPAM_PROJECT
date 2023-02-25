<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>
<%@ attribute name="activePage" required="true" rtexprvalue="false" %>



<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand"><i class="bi bi-book"></i>  LIBRARY.UA</a>

    <ul class="navbar-nav mr-auto">
        <c:choose>
            <c:when test = "${activePage == 'books'}">
                <li class="nav-item active">
                    <a class="nav-link"><t:localize key="books"/></a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/reader/books/show"><t:localize key="books"/></a>
                </li>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test = "${activePage == 'orders'}">
                <li class="nav-item active">
                    <a class="nav-link"><t:localize key="orders"/></a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/reader/orders/show"><t:localize key="orders"/></a>
                </li>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test = "${activePage == 'profile'}">
                <li class="nav-item active">
                    <a class="nav-link"><t:localize key="profile"/></a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/reader/profile/show"><t:localize key="profile"/></a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>

    <span class="navbar-text mr-4"><t:localize key="hello"/>, <span class="fst-italic">${readerFullname}</span></span>

    <c:if test="${not empty readerFine}">
        <span class="navbar-text mr-4"><t:localize key="fine"/>: ${readerFine} <t:localize key="grn"/></span>
        <button class="btn btn-outline-success navbar-btn mr-2" type="button" onclick="openPayFineModal();"><t:localize key="payFine"/></button>
    </c:if>

    <div class="dropdown mr-2">
        <button class="btn btn-outline-success dropdown-toggle navbar-btn" type="button" data-bs-toggle="dropdown"><t:localize key="language"/></button>
        <ul class="dropdown-menu">
            <li><button class="dropdown-item" type="button" onclick="changeLanguage('ukr', '${pageContext.request.contextPath}');">Укр</button></li>
            <li><button class="dropdown-item" type="button" onclick="changeLanguage('eng', '${pageContext.request.contextPath}');">Eng</button></li>
        </ul>
    </div>

    <button class="btn btn-outline-success navbar-btn" type="button" onclick="logout('${pageContext.request.contextPath}');"><t:localize key="logOut"/></button>
</nav>