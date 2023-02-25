<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>



<div class="modal fade" id="deleteOrderModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
    <div class="modal-dialog">
        <form action="${pageContext.request.contextPath}/reader/order/delete" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title"><t:localize key="deleteOrder"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="deleteOrderCodeInput" name="orderCode">
                    <p class="mb-1"><t:localize key="areYouSureToDeleteOrder"/>?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>
                    <button type="submit" class="btn btn-primary"><t:localize key="ok"/></button>
                </div>
            </div>
        </form>
    </div>
</div>