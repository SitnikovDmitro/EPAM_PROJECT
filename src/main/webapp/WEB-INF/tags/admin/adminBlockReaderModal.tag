<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>



<div class="modal fade" id="blockReaderModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/admin/reader/block" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel"><t:localize key="blockReader"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="blockUserIdInput" name="userId">
                    <p class="mb-1"><t:localize key="areYouSureToBlockReader"/> <span id="blockFullnameSpan"></span>?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>
                    <button type="submit" class="btn btn-primary"><t:localize key="ok"/></button>
                </div>
            </form>
        </div>
    </div>
</div>