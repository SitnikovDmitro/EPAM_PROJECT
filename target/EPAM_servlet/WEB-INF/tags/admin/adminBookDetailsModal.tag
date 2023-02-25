<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>



<div class="modal fade" id="bookDetailsModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-body">

                <div class="card mb-3">

                    <div class="card-header">
                        <ul class="nav nav-tabs card-header-tabs" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" data-bs-toggle="tab" href="#information" role="tab"><t:localize key="information"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-bs-toggle="tab" href="#edition" role="tab"><t:localize key="edition"/></a>
                            </li>
                        </ul>
                    </div>

                    <div class="tab-content">

                        <!----------Book information---------->
                        <div class="tab-pane fade show active" id="information" role="tabpanel">
                            <div class="row no-gutters">
                                <div class="col-5">
                                    <img src="" style="border-bottom-left-radius: 4px;" class="img-fluid pt-3 h-100 w-100" alt="Book image" id="bookImage">
                                </div>
                                <div class="col-7">
                                    <div class="d-flex flex-column h-100">

                                        <div class="flex-shrink-1">
                                            <div class="card-body border-top mt-3">
                                                <h5 class="card-title" id="titleHeader"></h5>
                                                <p class="card-text" id="descriptionHeader"></p>
                                            </div>
                                        </div>

                                        <div class="flex-grow-1 border-top border-bottom">
                                            <f:bookPropertiesBlock/>
                                        </div>

                                        <div class="flex-shrink-1">
                                            <div class="card-body">
                                                <form action="${pageContext.request.contextPath}/admin/book/delete" method="post" id="deleteBookForm">
                                                    <input type="hidden" id="bookIdInput1" name="bookId">
                                                    <button type="submit" class="btn btn-success"><t:localize key="delete"/></button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <!----------Book edition---------->
                        <div class="tab-pane fade" id="edition" role="tabpanel">
                            <div class="card-body">

                                <div class="row mb-3">
                                    <div class="col-5 pr-2">
                                        <label for="editionTitleInput" class="form-label"><t:localize key="title"/></label>
                                        <input type="text" class="form-control" id="editionTitleInput" maxlength="50">
                                        <div class="invalid-feedback" id="editionTitleInvalidFeedback"></div>
                                        <div class="valid-feedback"><t:localize key="valid"/></div>
                                    </div>
                                    <div class="col-4 px-2">
                                        <label for="editionAuthorInput" class="form-label"><t:localize key="author"/></label>
                                        <input type="text" class="form-control" id="editionAuthorInput" maxlength="50">
                                        <div class="invalid-feedback" id="editionAuthorInvalidFeedback"></div>
                                        <div class="valid-feedback"><t:localize key="valid"/></div>
                                    </div>
                                    <div class="col-3 pl-2">
                                        <label for="editionPublicationDateInput" class="form-label"><t:localize key="publicationDate"/></label>
                                        <input type="date" class="form-control" id="editionPublicationDateInput" name="publicationDate">
                                        <div class="invalid-feedback" id="editionPublicationDateInvalidFeedback"></div>
                                        <div class="valid-feedback"><t:localize key="valid"/></div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-5 h-100 pr-2">
                                        <label for="editionDescriptionTextArea" class="form-label"><t:localize key="description"/></label>
                                        <textarea class="form-control h-100" id="editionDescriptionTextArea" rows="15" maxlength="500"></textarea>
                                        <div class="invalid-feedback" id="editionDescriptionInvalidFeedback"></div>
                                        <div class="valid-feedback"><t:localize key="valid"/></div>

                                        <input type="hidden" id="editionIdInput" name="id">
                                        <button type="button" class="btn btn-primary btn-block mt-3" onclick="editBook('${pageContext.request.contextPath}');"><t:localize key="change"/></button>
                                    </div>

                                    <div class="col-7 pl-2">
                                        <div class="d-flex flex-column justify-content-between h-100">
                                            <div class="row mb-3">
                                                <div class="col pr-2">
                                                    <label for="editionGenreSelect" class="form-label"><t:localize key="genre"/></label>
                                                    <select class="custom-select" id="editionGenreSelect">
                                                        <option value="0"><t:localize key="adventures"/></option>
                                                        <option value="1"><t:localize key="horror"/></option>
                                                        <option value="2"><t:localize key="comedy"/></option>
                                                        <option value="3"><t:localize key="romance"/></option>
                                                        <option value="4"><t:localize key="thriller"/></option>
                                                        <option value="5"><t:localize key="drama"/></option>
                                                        <option value="6"><t:localize key="encyclopedia"/></option>
                                                    </select>
                                                    <div class="invalid-feedback" id="editionGenreInvalidFeedback"></div>
                                                    <div class="valid-feedback"><t:localize key="valid"/></div>
                                                </div>
                                                <div class="col pl-2">
                                                    <label for="editionLanguageSelect" class="form-label"><t:localize key="language"/></label>
                                                    <select class="custom-select" id="editionLanguageSelect">
                                                        <option value="0"><t:localize key="english"/></option>
                                                        <option value="1"><t:localize key="russian"/></option>
                                                        <option value="2"><t:localize key="japanese"/></option>
                                                        <option value="3"><t:localize key="ukrainian"/></option>
                                                    </select>
                                                    <div class="invalid-feedback" id="editionLanguageInvalidFeedback"></div>
                                                    <div class="valid-feedback"><t:localize key="valid"/></div>
                                                </div>
                                            </div>


                                            <div class="row mb-3">
                                                <div class="col-5 pr-2">
                                                    <label for="editionTotalCopiesNumberInput" class="form-label"><t:localize key="copiesNumber"/></label>
                                                    <input type="number" class="form-control" id="editionTotalCopiesNumberInput" min="0" max="10000" step="1">
                                                    <div class="invalid-feedback" id="editionTotalCopiesNumberInvalidFeedback"></div>
                                                    <div class="valid-feedback"><t:localize key="valid"/></div>
                                                </div>

                                                <div class="col-7 pl-2">
                                                    <label for="editionIsbnInput" class="form-label"><t:localize key="isbn"/></label>
                                                    <input type="number" class="form-control" id="editionIsbnInput" maxlength="8" step="1">
                                                    <div class="invalid-feedback" id="editionIsbnInvalidFeedback"></div>
                                                    <div class="valid-feedback"><t:localize key="valid"/></div>
                                                </div>
                                            </div>



                                            <div>
                                                <label for="editionPublisherTitleInput" class="form-label"><t:localize key="publisher"/></label>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" id="editionPublisherTitleInput" maxlength="50" disabled>
                                                    <button type="button" class="btn btn-primary" onclick="editionPublisherTitleInput.value = '';"><t:localize key="clear"/></button>
                                                    <button type="button" class="btn btn-primary rounded-end" onclick="onSelectPublisherEditionButtonClick();"><t:localize key="select"/></button>
                                                    <div class="invalid-feedback" id="editionPublisherTitleInvalidFeedback"></div>
                                                    <div class="valid-feedback"><t:localize key="valid"/></div>
                                                </div>
                                            </div>


                                            <div id="editionElectronicFormatBlock">
                                                <label for="editionContentInput" class="form-label"><t:localize key="electronicFormat"/></label>
                                                <input type="file" accept="application/pdf" class="form-control" id="editionContentInput">
                                            </div>

                                            <div>
                                                <label for="editionCoverInput" class="form-label"><t:localize key="coverImageFile"/></label>
                                                <input type="file" accept="image/png" class="form-control" id="editionCoverInput">
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!----------Book feedbacks---------->
                <div id="feedbackBlock"></div>
            </div>
        </div>
    </div>
</div>