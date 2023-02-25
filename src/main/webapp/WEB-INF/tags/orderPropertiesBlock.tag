<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>
<%@ attribute name="includeUserFullnameLine" required="true" type="java.lang.Boolean" rtexprvalue="false" %>



<div class="card-body border-top">
    <c:if test="${includeUserFullnameLine}">
        <h6 class="text-muted property-text"><span class="lspan"><t:localize key="createdBy"/></span><span class="mspan"></span><span class="rspan">${order.userFullname}</span></h6>
    </c:if>
    <c:if test="${not empty order.creationDate}">
        <h6 class="text-muted property-text"><span class="lspan"><t:localize key="createdAt"/></span><span class="mspan"></span><span class="rspan">${order.creationDate}</span></h6>
    </c:if>
    <c:if test="${not empty order.deadlineDate}">
        <h6 class="text-muted property-text"><span class="lspan"><t:localize key="expiresAt"/></span><span class="mspan"></span><span class="rspan">${order.deadlineDate}</span></h6>
    </c:if>
    <h6 class="text-muted property-text"><span class="lspan"><t:localize key="state"/></span><span class="mspan"></span><span class="rspan">${order.stateText}</span></h6>
</div>