<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>



<div class="modal fade" id="signInModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
    <div class="modal-dialog modal-sm">

        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><t:localize key="signIn"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <div class="mb-3">
                    <label for="emailInput" class="form-label"><t:localize key="email"/></label>
                    <input type="email" class="form-control" id="signInEmailInput" maxlength="50">
                    <div class="invalid-feedback" id="signInEmailInvalidFeedback"></div>
                    <div class="valid-feedback"><t:localize key="valid"/></div>
                </div>

                <div class="mb-1">
                    <label for="passwordInput" class="form-label"><t:localize key="password"/></label>
                    <input type="password" class="form-control" id="signInPasswordInput" maxlength="50">
                    <div class="invalid-feedback" id="signInPasswordInvalidFeedback"></div>
                    <div class="valid-feedback"><t:localize key="valid"/></div>
                </div>
            </div>

            <div class="modal-body border-top p-2">
                <p class="text-center m-0"><t:localize key="forgotPassword"/>? <a type="button" onclick="openSendAccessLinkModal();" class="text-decoration-none"><t:localize key="getAccess"/></a></p>
            </div>

            <div class="modal-body border-top p-2">
                <p class="text-center m-0"><t:localize key="haveNotRegisteredYet"/>? <a type="button" onclick="openSignUpModal();" class="text-decoration-none"><t:localize key="signUp"/></a></p>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>
                <button type="button" class="btn btn-primary" onclick="signIn('${pageContext.request.contextPath}');"><t:localize key="signIn"/></button>
            </div>
        </div>
    </div>
</div>