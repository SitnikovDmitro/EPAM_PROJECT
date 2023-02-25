<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>



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