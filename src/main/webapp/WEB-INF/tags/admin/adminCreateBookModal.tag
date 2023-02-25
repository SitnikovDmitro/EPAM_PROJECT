<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>



<div class="modal fade" id="createBookModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title"><t:localize key="bookCreation"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body">
                <div class="row mb-3">
                    <div class="col-5 pr-2">
                        <label for="creationTitleInput" class="form-label"><t:localize key="title"/></label>
                        <input type="text" class="form-control" id="creationTitleInput" maxlength="50">
                        <div class="invalid-feedback" id="creationTitleInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>

                    <div class="col-4 px-2">
                        <label for="creationAuthorInput" class="form-label"><t:localize key="author"/></label>
                        <input type="text" class="form-control" id="creationAuthorInput" maxlength="50">
                        <div class="invalid-feedback" id="creationAuthorInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>

                    <div class="col-3 pl-2">
                        <label for="creationPublicationDateInput" class="form-label"><t:localize key="publicationDate"/></label>
                        <input type="date" class="form-control" id="creationPublicationDateInput">
                        <div class="invalid-feedback" id="creationPublicationDateInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-5 h-100 pr-2">
                        <label for="creationDescriptionTextArea" class="form-label"><t:localize key="description"/></label>
                        <textarea class="form-control h-100" id="creationDescriptionTextArea" rows="18" maxlength="500"></textarea>
                        <div class="invalid-feedback" id="creationDescriptionInvalidFeedback"></div>
                        <div class="valid-feedback"><t:localize key="valid"/></div>
                    </div>

                    <div class="col-7 pl-2">
                        <div class="d-flex flex-column justify-content-between h-100">

                            <div class="row mb-3">
                                <div class="col pr-2">
                                    <label for="creationGenreSelect" class="form-label"><t:localize key="genre"/></label>
                                    <select class="custom-select" id="creationGenreSelect">
                                        <option selected value=""><t:localize key="selectEllipsis"/></option>
                                        <option value="0"><t:localize key="adventures"/></option>
                                        <option value="1"><t:localize key="horror"/></option>
                                        <option value="2"><t:localize key="comedy"/></option>
                                        <option value="3"><t:localize key="romance"/></option>
                                        <option value="4"><t:localize key="thriller"/></option>
                                        <option value="5"><t:localize key="drama"/></option>
                                        <option value="6"><t:localize key="encyclopedia"/></option>
                                    </select>
                                    <div class="invalid-feedback" id="creationGenreInvalidFeedback"></div>
                                    <div class="valid-feedback"><t:localize key="valid"/></div>
                                </div>
                                <div class="col pl-2">
                                    <label for="creationLanguageSelect" class="form-label">Language</label>
                                    <select class="custom-select" id="creationLanguageSelect">
                                        <option selected value=""><t:localize key="selectEllipsis"/></option>
                                        <option value="0"><t:localize key="english"/></option>
                                        <option value="1"><t:localize key="russian"/></option>
                                        <option value="2"><t:localize key="japanese"/></option>
                                        <option value="3"><t:localize key="ukrainian"/></option>
                                    </select>
                                    <div class="invalid-feedback" id="creationLanguageInvalidFeedback"></div>
                                    <div class="valid-feedback"><t:localize key="valid"/></div>
                                </div>
                            </div>


                            <div class="row mb-3">
                                <div class="col-5 pr-2">
                                    <label for="creationTotalCopiesNumberInput" class="form-label"><t:localize key="copiesNumber"/></label>
                                    <input type="number" class="form-control" id="creationTotalCopiesNumberInput" min="0" max="10000" step="1">
                                    <div class="invalid-feedback" id="creationTotalCopiesNumberInvalidFeedback"></div>
                                    <div class="valid-feedback"><t:localize key="valid"/></div>
                                </div>

                                <div class="col-7 pl-2">
                                    <label for="creationIsbnInput" class="form-label"><t:localize key="isbn"/></label>
                                    <input type="number" class="form-control" id="creationIsbnInput" maxlength="8" step="1">
                                    <div class="invalid-feedback" id="creationIsbnInvalidFeedback"></div>
                                    <div class="valid-feedback"><t:localize key="valid"/></div>
                                </div>
                            </div>


                            <div>
                                <label for="creationPublisherTitleInput" class="form-label"><t:localize key="publisher"/></label>
                                <div class="input-group">
                                    <input type="text" class="form-control" id="creationPublisherTitleInput" maxlength="50" disabled>
                                    <button type="button" class="btn btn-primary" onclick="creationPublisherTitleInput.value = '';"><t:localize key="clear"/></button>
                                    <button type="button" class="btn btn-primary rounded-end" onclick="onSelectPublisherCreationButtonClick();"><t:localize key="select"/></button>
                                    <div class="invalid-feedback" id="creationPublisherTitleInvalidFeedback"></div>
                                    <div class="valid-feedback"><t:localize key="valid"/></div>
                                </div>
                            </div>


                            <div class="form-check ml-1 text-center">
                                <input class="form-check-input" id="creationIsValuableCheckbox" type="checkbox" value="true">
                                <label class="form-check-label" for="creationIsValuableCheckbox"><t:localize key="onlyForUsingInReadingHall"/></label>
                            </div>

                            <div class="mb-3">
                                <label for="creationContentInput" class="form-label"><t:localize key="electronicFormat"/></label>
                                <input type="file" accept="application/pdf" class="form-control" id="creationContentInput">
                            </div>

                            <div>
                                <label for="creationCoverInput" class="form-label"><t:localize key="coverImageFile"/></label>
                                <input type="file" accept="image/png" class="form-control" id="creationCoverInput">
                            </div>

                        </div>

                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><t:localize key="cancel"/></button>
                <button type="button" class="btn btn-primary" onclick="createBook('${pageContext.request.contextPath}');"><t:localize key="submit"/></button>
            </div>

        </div>
    </div>
</div>