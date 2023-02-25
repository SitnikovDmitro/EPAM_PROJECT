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

                                <div class="flex-grow-1 border-top">
                                    <f:bookPropertiesBlock/>
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
