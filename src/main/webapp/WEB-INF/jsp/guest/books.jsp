<%@ page import="app.dto.BookBriefDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/guest" prefix="pf" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/img/favicon.ico">

        <link href="${pageContext.request.contextPath}/static/css/bootstrap.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/static/css/bootstrap-min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/scripts.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/guest-scripts.js"></script>
        <link href="${pageContext.request.contextPath}/static/css/bootstrap-icons.css" rel="stylesheet">

        <script src="https://www.google.com/recaptcha/api.js"></script>
    </head>
    <body class="d-flex flex-column min-vh-100" style="background-image: url('${pageContext.request.contextPath}/static/img/background.jpg'); background-repeat: no-repeat; background-attachment: fixed;">
        <pf:guestMessageToast/>
        <pf:guestSendAccessLinkModal/>
        <pf:guestSignInModal/>
        <pf:guestSignUpModal/>
        <pf:guestBookDetailsModal/>
        <f:selectPublisherModal/>



        <pf:guestNavbar activePage="books"/>



        <div class="container-fluid col-lg-8 col-md-12">

            <div class="card mt-4">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="${(advancedSearch == true)?'nav-link':'nav-link active'}" data-bs-toggle="tab" href="#basicSearchTabPanel" role="tab" id="basicSearchTab"><t:localize key="basicSearch"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="${(advancedSearch == true)?'nav-link  active':'nav-link'}" data-bs-toggle="tab" href="#advancedSearchTabPanel" role="tab" id="advancedSearchTab"><t:localize key="advancedSearch"/></a>
                        </li>
                    </ul>
                </div>

                <div class="tab-content">
                    <div class="${(advancedSearch == true)?'tab-pane fade':'tab-pane fade show active'}" id="basicSearchTabPanel" role="tabpanel">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/guest/books/find/basic" method="post" id="basicSearchBooksForm">
                                <div class="row">
                                    <div class="col-9 pr-1">
                                        <input type="text" class="form-control" name="query" value="${query}" placeholder="<t:localize key='query'/>">
                                    </div>

                                    <div class="col-3 pl-1">
                                        <button type="submit" class="btn btn-block btn-primary"><t:localize key="search"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="${(advancedSearch == true)?'tab-pane fade show active':'tab-pane fade'}" id="advancedSearchTabPanel" role="tabpanel">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/guest/books/find/advanced" method="post" id="advancedSearchBooksForm">
                                <div class="row">

                                    <div class="col-3 pr-1">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><t:localize key="format"/></span>
                                            </div>
                                            <select class="custom-select" name="format">
                                                <option ${(format eq null)?'selected':''} value=""><t:localize key="all"/></option>
                                                <option ${(format eq 0)?'selected':''} value="0"><t:localize key="eBook"/></option>
                                                <option ${(format eq 1)?'selected':''} value="1"><t:localize key="printedBook"/></option>
                                                <option ${(format eq 2)?'selected':''} value="2"><t:localize key="valuableBook"/></option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-3 px-1">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><t:localize key="genre"/></span>
                                            </div>
                                            <select class="custom-select" name="genre">
                                                <option ${(genre == null)?'selected':''} value=""><t:localize key="all"/></option>
                                                <option ${(genre == 0)?'selected':''} value="0"><t:localize key="adventures"/></option>
                                                <option ${(genre == 1)?'selected':''} value="1"><t:localize key="horror"/></option>
                                                <option ${(genre == 2)?'selected':''} value="2"><t:localize key="comedy"/></option>
                                                <option ${(genre == 3)?'selected':''} value="3"><t:localize key="romance"/></option>
                                                <option ${(genre == 4)?'selected':''} value="4"><t:localize key="thriller"/></option>
                                                <option ${(genre == 5)?'selected':''} value="5"><t:localize key="drama"/></option>
                                                <option ${(genre == 6)?'selected':''} value="6"><t:localize key="encyclopedia"/></option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-3 px-1">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><t:localize key="language"/></span>
                                            </div>
                                            <select class="custom-select" name="language">
                                                <option ${(language == null)?'selected':''} value=""><t:localize key="all"/></option>
                                                <option ${(language == 0)?'selected':''} value="0"><t:localize key="english"/></option>
                                                <option ${(language == 1)?'selected':''} value="1"><t:localize key="russian"/></option>
                                                <option ${(language == 2)?'selected':''} value="2"><t:localize key="japanese"/></option>
                                                <option ${(language == 3)?'selected':''} value="3"><t:localize key="ukrainian"/></option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-3 pl-1">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><t:localize key="sortBy"/></span>
                                            </div>
                                            <select class="custom-select" name="sortCriteria">
                                                <option ${(sortCriteria == 0 || sortCriteria == null)?'selected':''} value=""><t:localize key="popularity"/></option>
                                                <option ${(sortCriteria == 1)?'selected':''} value="1"><t:localize key="alphabet"/></option>
                                                <option ${(sortCriteria == 2)?'selected':''} value="2"><t:localize key="publication"/></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>


                                <div class="row mt-2">
                                    <div class="col pr-1">
                                        <input type="text" class="form-control" name="titleQuery" value="${titleQuery}" placeholder="<t:localize key='titleQuery'/>" maxlength="50">
                                    </div>

                                    <div class="col pl-1">
                                        <input type="text" class="form-control" name="authorQuery" value="${authorQuery}" placeholder="<t:localize key='authorQuery'/>" maxlength="50">
                                    </div>
                                </div>


                                <div class="row mt-2">
                                    <div class="col-3 pr-1">
                                        <input type="number" class="form-control" name="isbn" value="${isbn}" placeholder="<t:localize key='isbn'/>" maxlength="4" min="0" step="1" max="9999">
                                    </div>

                                    <div class="col-6 px-1">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><t:localize key="publisher"/></span>
                                            </div>
                                            <input type="text" class="form-control" name="publisherQuery" value="${publisherQuery}" id="publisherQueryInput" readonly>
                                            <button type="button" class="btn btn-primary" onclick="publisherQueryInput.value = '';"><t:localize key="clear"/></button>
                                            <button type="button" class="btn btn-primary" onclick="onSelectPublisherButtonClick();"><t:localize key="select"/></button>
                                        </div>
                                    </div>

                                    <div class="col-3 pl-1">
                                        <button type="submit" class="btn btn-primary btn-block"><t:localize key="search"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>



            <f:bookCardList/>

            <f:pagination url="${pageContext.request.contextPath}/guest/books/show"/>
        </div>

        <f:footer/>
    </body>
</html>
