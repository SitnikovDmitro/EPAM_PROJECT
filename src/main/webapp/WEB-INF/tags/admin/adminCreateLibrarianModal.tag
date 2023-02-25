<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>



<div class="modal fade" id="createLibrarianModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><t:localize key="librarianCreation"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <div class="row mb-3">
                    <div class="col pr-2">
                        <label for="firstnameInput" class="form-label"><t:localize key="firstname"/></label>
                        <input type="text" class="form-control" id="firstnameInput" name="firstname" required>
                        <div class="invalid-feedback" id="firstnameInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>
                    <div class="col pl-2">
                        <label for="lastnameInput" class="form-label"><t:localize key="lastname"/></label>
                        <input type="text" class="form-control" id="lastnameInput" name="lastname" required>
                        <div class="invalid-feedback" id="lastnameInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="emailInput" class="form-label"><t:localize key="email"/></label>
                    <input type="email" class="form-control" id="emailInput" name="title" required>
                    <div class="invalid-feedback" id="emailInvalidFeedback"></div>
                    <div class="valid-feedback"><t:localize key="valid"/></div>
                </div>

                <div class="row mb-2">
                    <div class="col pr-2">
                        <label for="passwordInput" class="form-label"><t:localize key="password"/></label>
                        <input type="password" class="form-control" id="passwordInput" name="password" required>
                        <div class="invalid-feedback" id="passwordInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>
                    <div class="col pl-2">
                        <label for="passwordConfirmInput" class="form-label"><t:localize key="passwordConfirm"/></label>
                        <input type="password" class="form-control" id="passwordConfirmInput" name="passwordConfirm" required>
                        <div class="invalid-feedback"><t:localize key="passwordsDoNotMatch"/></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>
                <button type="button" class="btn btn-primary" onclick="createLibrarian('${pageContext.request.contextPath}');"><t:localize key="create"/></button>
            </div>
        </div>
    </div>
</div>