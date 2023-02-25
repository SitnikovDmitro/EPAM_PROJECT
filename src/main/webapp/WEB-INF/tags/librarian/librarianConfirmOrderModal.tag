<%@ tag pageEncoding="UTF-8" import="java.time.LocalDate" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>



<div class="modal fade" id="confirmOrderModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
    <div class="modal-dialog">
        <form action="${pageContext.request.contextPath}/librarian/order/confirm" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title"><t:localize key="confirmOrder"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="confirmOrderCodeInput" name="orderCode">
                    <p class="mb-1"><t:localize key="areYouSureToConfirmOrder"/></p>
                    <label for="confirmOrderDeadlineDateInput" class="form-label mt-2"><t:localize key="specifyDeadlineDate"/></label>
                    <input type="date" class="form-control" id="confirmOrderDeadlineDateInput" name="orderDeadlineDate" min="<%= LocalDate.now().plusDays(7) %>" max="<%= LocalDate.now().plusDays(60) %>" required>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>
                    <button type="submit" class="btn btn-primary"><t:localize key="ok"/></button>
                </div>
            </div>
        </form>
    </div>
</div>