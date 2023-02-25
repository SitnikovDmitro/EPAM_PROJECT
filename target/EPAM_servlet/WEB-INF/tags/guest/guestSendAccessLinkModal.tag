<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>




<div class="modal fade" id="sendAccessLinkModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
    <div class="modal-dialog modal-sm">

        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><t:localize key="sendAccessLink"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body border-bottom">
                <p class="mb-0"><t:localize key="sendAccessLinkMessage"/></p>
            </div>

            <div class="modal-body">
                <div class="mb-3">
                    <label for="sendAccessLinkEmailInput" class="form-label"><t:localize key="email"/></label>
                    <input type="email" class="form-control" id="sendAccessLinkEmailInput" maxlength="50">
                    <div class="invalid-feedback" id="sendAccessLinkEmailInvalidFeedback"></div>
                    <div class="valid-feedback"><t:localize key="valid"/></div>
                </div>
                <div class="mb-1">
                    <label for="sendAccessLinkPasswordInput" class="form-label"><t:localize key="newPassword"/></label>
                    <input type="password" class="form-control" id="sendAccessLinkPasswordInput" maxlength="50">
                    <div class="invalid-feedback" id="sendAccessLinkPasswordInvalidFeedback"></div>
                    <div class="valid-feedback"><t:localize key="valid"/></div>
                </div>
            </div>


            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>
                <button type="button" class="btn btn-primary" onclick="sendAccessLinkMessage('${pageContext.request.contextPath}');"><t:localize key="send"/></button>
            </div>
        </div>
    </div>
</div>