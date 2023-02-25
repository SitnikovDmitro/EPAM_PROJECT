<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>



<div class="modal fade" id="createFeedbackModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form class="modal-content" action="${pageContext.request.contextPath}/reader/feedback/create" method="post">
            <div class="modal-header">
                <h5 class="modal-title"><t:localize key="makeFeedback"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" id="feedbackGradeInput" name="grade" value="5">
                <textarea class="form-control mb-3" id="feedbackTextInput" name="text" rows="4"></textarea>
                <div class="d-flex justify-content-center w-100">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-primary" onclick="changeFeedbackGrade(1);"><i class="bi bi-star-fill" id="feedbackGrade1ButtonIcon"></i></button>
                        <button type="button" class="btn btn-primary" onclick="changeFeedbackGrade(2);"><i class="bi bi-star-fill" id="feedbackGrade2ButtonIcon"></i></button>
                        <button type="button" class="btn btn-primary" onclick="changeFeedbackGrade(3);"><i class="bi bi-star-fill" id="feedbackGrade3ButtonIcon"></i></button>
                        <button type="button" class="btn btn-primary" onclick="changeFeedbackGrade(4);"><i class="bi bi-star-fill" id="feedbackGrade4ButtonIcon"></i></button>
                        <button type="button" class="btn btn-primary" onclick="changeFeedbackGrade(5);"><i class="bi bi-star-fill" id="feedbackGrade5ButtonIcon"></i></button>
                    </div>
                </div>
                <input type="hidden" id="feedbackBookIdInput" name="bookId">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>
                <button type="submit" class="btn btn-primary"><t:localize key="submit"/></button>
            </div>
        </form>
    </div>
</div>