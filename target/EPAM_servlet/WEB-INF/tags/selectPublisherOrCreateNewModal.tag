<%@ tag import="app.dto.PaginationDTO" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ attribute name="selectCallbackName" required="true" rtexprvalue="true" %>
<%@ attribute name="createNewEnabled" required="true" rtexprvalue="true" %>

<div class="modal fade" id="selectPublisherModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5">
                    <c:choose>
                        <c:when test = "${createNewEnabled}">
                            <t:localize key="selectPublisherOrCreateNew"/>
                        </c:when>
                        <c:otherwise>
                            <t:localize key="selectPublisher"/>
                        </c:otherwise>
                    </c:choose>
                </h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body">
                <input type="text" class="form-control mb-3" id="publisherInput" onchange="loadPublishers(this.value, '${pageContext.request.contextPath}')" placeholder="<t:localize key='publisherQuery'/>">

                <div class="list-group" id="publisherList">
                    <button type="button" class="list-group-item list-group-item-action" onclick="onPublisherButtonClick('Publisher RANOK', this);">Publisher RANOK</button>
                    <button type="button" class="list-group-item list-group-item-action" onclick="onPublisherButtonClick('New yourk time', this);">New yourk times</button>
                    <button type="button" class="list-group-item list-group-item-action" onclick="onPublisherButtonClick('Canada', this);">Canada</button>
                    <button type="button" class="list-group-item list-group-item-action" onclick="onPublisherButtonClick('UK books', this);">UK books</button>
                </div>

                <input type="hidden" id="publisherHiddenInput" name="publisherName">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>

                <button type="button" class="btn btn-primary" id="selectButton" onclick="${selectCallbackName}(publisherHiddenInput.value)" disabled><t:localize key="select"/></button>

                <c:if test="${createNewEnabled}">
                    <button type="button" class="btn btn-primary" id="createNewAndSelectButton" onclick="${selectCallbackName}(publisherInput.value)"><t:localize key="createNewAndSelect"/></button>
                </c:if>
            </div>
        </div>
    </div>
</div>