<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>



<div class="modal fade" id="bookDetailsModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-body">

                <!--Book information-->
                <div class="card mb-3">
                    <div class="row no-gutters">
                        <div class="col-5">
                            <img src="" class="img-fluid rounded-start h-100 w-100" id="bookImage" alt="Book image">
                        </div>
                        <div class="col-7">
                            <div class="d-flex flex-column h-100">

                                <div class="flex-shrink-1">
                                    <div class="card-body">
                                        <h5 class="card-title" id="titleHeader"></h5>
                                        <p class="card-text" id="descriptionHeader"></p>
                                    </div>
                                </div>

                                <div class="flex-grow-1 border-top border-bottom">
                                    <f:bookPropertiesBlock/>
                                </div>

                                <div class="flex-shrink-1">
                                    <div class="card-body">
                                        <button type="button" class="btn btn-success m-1" id="addToBookmarksButton" onclick="addToBookmarksForm.submit();"><t:localize key="addToBookmarks"/>
                                        </button><button type="button" class="btn btn-success m-1" id="deleteFromBookmarksButton" onclick="deleteFromBookmarksForm.submit();"><t:localize key="deleteFromBookmarks"/>
                                        </button><button type="button" class="btn btn-success m-1" id="makeOrderButton" onclick="makeOrderForm.submit();"><t:localize key="makeOrder"/>
                                        </button><button type="button" class="btn btn-success m-1" id="downloadBookButton" onclick="downloadBookForm.submit();"><t:localize key="download"/>
                                        </button><button type="button" class="btn btn-success m-1" onclick="openCreateFeedbackModal();"><t:localize key="makeFeedback"/></button>

                                        <form action="${pageContext.request.contextPath}/reader/book/add-to-bookmarks" method="post" id="addToBookmarksForm">
                                            <input type="hidden" id="bookIdInput1" name="bookId">
                                        </form>
                                        <form action="${pageContext.request.contextPath}/reader/book/delete-from-bookmarks" method="post" id="deleteFromBookmarksForm">
                                            <input type="hidden" id="bookIdInput2" name="bookId">
                                        </form>
                                        <form action="${pageContext.request.contextPath}/reader/order/create" method="post" id="makeOrderForm">
                                            <input type="hidden" id="bookIdInput3" name="bookId">
                                        </form>
                                        <form action="${pageContext.request.contextPath}/book/content" method="get" id="downloadBookForm">
                                            <input type="hidden" id="bookIdInput4" name="bookId">
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>



                <!--Book feedbacks-->
                <div id="feedbackBlock"></div>
            </div>
        </div>
    </div>
</div>