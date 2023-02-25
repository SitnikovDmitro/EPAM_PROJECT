<%@ tag import="app.dto.PaginationDTO" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>

<div class="modal fade" id="selectPublisherModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="selectPublisherLabel"><t:localize key="selectPublisher"/></h1>
                <h1 class="modal-title fs-5" id="selectPublisherOrCreateNewLabel"><t:localize key="selectPublisherOrCreateNew"/></h1>

                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body" style="min-height: 318px;">
                <input type="text" class="form-control mb-3" id="publisherInput" oninput="loadPublishers('${pageContext.request.contextPath}')" placeholder="<t:localize key='publisherQuery'/>">

                <h6 class="text-center text-muted mb-3" id="noResultsLabel"><t:localize key="noResults"/></h6>
                <h6 class="text-center text-muted mb-3" id="searchResultsLabel"><t:localize key="searchResults"/></h6>
                <h6 class="text-center text-muted mb-3" id="inputQueryLabel"><t:localize key="inputQueryToFindPublisher"/></h6>

                <div class="list-group" id="publisherList"></div>

                <input type="hidden" id="publisherHiddenInput">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>
                <button type="button" class="btn btn-primary" id="selectButton"><t:localize key="select"/></button>
                <button type="button" class="btn btn-primary" id="createNewAndSelectButton"><t:localize key="createNewAndSelect"/></button>
            </div>
        </div>
    </div>
</div>