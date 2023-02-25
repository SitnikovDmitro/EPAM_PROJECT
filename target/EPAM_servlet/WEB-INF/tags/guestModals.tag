<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>










<div class="modal fade" id="signUpModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><t:localize key="signUp"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <div class="row mb-3">
                    <div class="col pr-2">
                        <label for="firstnameInput" class="form-label"><t:localize key="firstname"/></label>
                        <input type="text" class="form-control" id="firstnameInput" maxlength="50">
                        <div class="invalid-feedback" id="firstnameInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>
                    <div class="col pl-2">
                        <label for="lastnameInput" class="form-label"><t:localize key="lastname"/></label>
                        <input type="text" class="form-control" id="lastnameInput" maxlength="50">
                        <div class="invalid-feedback" id="lastnameInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="emailInput" class="form-label"><t:localize key="email"/></label>
                    <input type="email" class="form-control" id="emailInput" maxlength="50">
                    <div class="invalid-feedback" id="emailInvalidFeedback"></div>
                    <div class="valid-feedback"><t:localize key="valid"/></div>
                </div>

                <div class="row mb-1">
                    <div class="col pr-2">
                        <label for="passwordInput" class="form-label"><t:localize key="password"/></label>
                        <input type="password" class="form-control" id="passwordInput" maxlength="50">
                        <div class="invalid-feedback" id="passwordInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>
                    <div class="col pl-2">
                        <label for="passwordConfirmInput" class="form-label"><t:localize key="passwordConfirm"/></label>
                        <input type="password" class="form-control" id="passwordConfirmInput" maxlength="50">
                        <div class="invalid-feedback"><t:localize key="passwordsDoNotMatch"/></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>
                </div>
            </div>

            <div class="modal-body border-top">
                <div class="d-flex w-100">
                    <div class="flex-shrink-1">
                        <div class="g-recaptcha" data-sitekey="6Ld_WHQkAAAAAAJXpku82tsu4X-lnrRdFTsrS96N"></div>
                    </div>
                    <div class="flex-grow-1">
                        <p id="captchaInvalidFeedback" class="text-danger pl-2"><p>
                    </div>
                </div>
            </div>

            <div class="modal-body border-top p-2">
                <p class="text-center m-0"><t:localize key="doYouAlreadyHaveAnAccount"/>? <a type="button" onclick="openSignInModal();" class="text-decoration-none"><t:localize key="signIn"/></a></p>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>
                <button type="button" class="btn btn-primary" onclick="signUp('${pageContext.request.contextPath}');"><t:localize key="signUp"/></button>
            </div>
        </div>
    </div>
</div>






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


<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
    <div id="messageToast" class="toast" role="alert">
        <div class="toast-header">
            <strong class="me-auto"><t:localize key="message"/></strong>
            <small><t:localize key="justNow"/></small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">
            <t:localize key="linkWasSentMessage"/>
        </div>
    </div>
</div>