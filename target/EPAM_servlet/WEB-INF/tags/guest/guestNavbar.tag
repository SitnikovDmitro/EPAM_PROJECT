<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>
<%@ attribute name="activePage" required="true" rtexprvalue="false" %>



<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand"><i class="bi bi-book"></i>  LIBRARY.UA</a>
    <ul class="navbar-nav mr-auto">
        <c:choose>
            <c:when test = "${activePage == 'homePage'}">
                <li class="nav-item active">
                    <a class="nav-link"><t:localize key="homePage"/></a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/guest/home-page/show"><t:localize key="homePage"/></a>
                </li>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test = "${activePage == 'books'}">
                <li class="nav-item active">
                    <a class="nav-link"><t:localize key="books"/></a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/guest/books/show"><t:localize key="books"/></a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>

    <span class="navbar-text mr-4"><t:localize key="hello"/>, <span class="fst-italic"><t:localize key="guest"/></span></span>

    <div class="dropdown mr-2">
        <button class="btn btn-outline-success dropdown-toggle navbar-btn" type="button" data-bs-toggle="dropdown"><t:localize key="language"/></button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" type="button" onclick="changeLanguage('ukr', '${pageContext.request.contextPath}');">Укр</a></li>
            <li><a class="dropdown-item" type="button" onclick="changeLanguage('eng', '${pageContext.request.contextPath}');">Eng</a></li>
        </ul>
    </div>

    <a class="btn btn-outline-success navbar-btn mr-1" type="button" onclick="openSignInModal();"><t:localize key="signIn"/></a>

    <a class="btn btn-outline-success navbar-btn ml-1" type="button" onclick="openSignUpModal();"><t:localize key="signUp"/></a>
</nav>