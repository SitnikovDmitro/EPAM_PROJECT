function openBookDetailsModal(bookId, contextPath) {
  fetch(contextPath+"/book/details?bookId="+bookId).then(
    response => response.json()
  ).then(
    data => {
      titleHeader.textContent = data.title;
      descriptionHeader.textContent = data.description;
      isbnSpan.textContent = data.isbn;
      authorSpan.textContent = data.author;
      languageSpan.textContent = data.languageText;;
      genreSpan.textContent = data.genreText;
      publicationDateSpan.textContent = data.publicationDate;
      freeCopiesNumberSpan.textContent = data.freeCopiesNumber;
      formatSpan.textContent =  data.formatText;
      publisherTitleSpan.textContent = data.publisherTitle;
      averageGradeSpan.textContent = data.averageGrade + " / 5";
      bookImage.src = contextPath+"/book/cover?bookId="+data.id;

      feedbackBlock.innerHTML = feedbacksToHtml(data.feedbacks);

      bookIdInput1.value = data.id;

      editionIdInput.value = data.id;
      editionIsbnInput.value = data.isbn;
      editionTitleInput.value = data.title;
      editionDescriptionTextArea.value = data.description;
      editionAuthorInput.value = data.author;
      editionPublicationDateInput.value = data.publicationDate;
      editionLanguageSelect.value = data.language;
      editionGenreSelect.value = data.genre;
      editionPublisherTitleInput.value = data.publisherTitle;
      editionElectronicFormatBlock.hidden = !data.hasElectronicFormat;
      editionTotalCopiesNumberInput.value = data.totalCopiesNumber;
    }
  ).catch(
    error => {
      console.log(error);
    }
  );

  $('#bookDetailsModal').modal('show');
}

function hasElectronicFormatCheckboxClick() {
  if (creationHasElectronicFormatCheckbox.checked) {
    creationContentInput.disabled = false;
  } else {
    creationContentInput.value = "";
    creationContentInput.disabled = true;
  }
}

function hasPrintedFormatCheckboxClick() {
  if (creationHasPrintedFormatCheckbox.checked) {
    creationIsValuableCheckbox.disabled = false;
    creationTotalCopiesNumberInput.disabled = false;
  } else {
    creationIsValuableCheckbox.checked = false;
    creationTotalCopiesNumberInput.value = "";
    creationIsValuableCheckbox.disabled = true;
    creationTotalCopiesNumberInput.disabled = true;
  }
}


function createBook(contextPath) {
    const data  = new FormData();

    data.append("isbn", creationIsbnInput.value);
    data.append("title", creationTitleInput.value);
    data.append("author", creationAuthorInput.value);
    data.append("description", creationDescriptionTextArea.value);
    data.append("genre", creationGenreSelect.value);
    data.append("language", creationLanguageSelect.value);
    data.append("totalCopiesNumber", creationTotalCopiesNumberInput.value);
    data.append("publicationDate", creationPublicationDateInput.value);
    data.append("isValuable", creationIsValuableCheckbox.value);
    data.append("publisherTitle", creationPublisherTitleInput.value);
    if (creationCoverInput.files.length >= 1) data.append("cover", creationCoverInput.files[0]);
    if (creationContentInput.files.length >= 1) data.append("content", creationContentInput.files[0]);

    fetch(contextPath+"/admin/book/create", { method: "POST", headers: {'Accept': '*.*'}, body: data }).then(
        response => response.json()
    ).then(
        data => {
            if (data.success) {
                window.location.href = contextPath+"/admin/books/show";
            } else {
                if (data.isbnValid) {
                    creationIsbnInput.classList.remove("is-invalid");
                    creationIsbnInput.classList.add("is-valid");
                } else {
                    creationIsbnInput.classList.remove("is-valid");
                    creationIsbnInput.classList.add("is-invalid");
                    creationIsbnInvalidFeedback.innerText = data.isbnValidationFeedback;
                }

                if (data.titleValid) {
                    creationTitleInput.classList.remove("is-invalid");
                    creationTitleInput.classList.add("is-valid");
                } else {
                    creationTitleInput.classList.remove("is-valid");
                    creationTitleInput.classList.add("is-invalid");
                    creationTitleInvalidFeedback.innerText = data.titleValidationFeedback;
                }

                if (data.authorValid) {
                    creationAuthorInput.classList.remove("is-invalid");
                    creationAuthorInput.classList.add("is-valid");
                } else {
                    creationAuthorInput.classList.remove("is-valid");
                    creationAuthorInput.classList.add("is-invalid");
                    creationAuthorInvalidFeedback.innerText = data.authorValidationFeedback;
                }

                if (data.descriptionValid) {
                    creationDescriptionTextArea.classList.remove("is-invalid");
                    creationDescriptionTextArea.classList.add("is-valid");
                } else {
                    creationDescriptionTextArea.classList.remove("is-valid");
                    creationDescriptionTextArea.classList.add("is-invalid");
                    creationDescriptionInvalidFeedback.innerText = data.descriptionValidationFeedback;
                }

                if (data.publicationDateValid) {
                    creationPublicationDateInput.classList.remove("is-invalid");
                    creationPublicationDateInput.classList.add("is-valid");
                } else {
                    creationPublicationDateInput.classList.remove("is-valid");
                    creationPublicationDateInput.classList.add("is-invalid");
                    creationPublicationDateInvalidFeedback.innerText = data.publicationDateValidationFeedback;
                }

                if (data.genreValid) {
                    creationGenreSelect.classList.remove("is-invalid");
                    creationGenreSelect.classList.add("is-valid");
                } else {
                    creationGenreSelect.classList.remove("is-valid");
                    creationGenreSelect.classList.add("is-invalid");
                    creationGenreInvalidFeedback.innerText = data.genreValidationFeedback;
                }

                if (data.languageValid) {
                    creationLanguageSelect.classList.remove("is-invalid");
                    creationLanguageSelect.classList.add("is-valid");
                } else {
                    creationLanguageSelect.classList.remove("is-valid");
                    creationLanguageSelect.classList.add("is-invalid");
                    creationLanguageInvalidFeedback.innerText = data.languageValidationFeedback;
                }

                if (data.totalCopiesNumberValid) {
                    creationTotalCopiesNumberInput.classList.remove("is-invalid");
                    creationTotalCopiesNumberInput.classList.add("is-valid");
                } else {
                    creationTotalCopiesNumberInput.classList.remove("is-valid");
                    creationTotalCopiesNumberInput.classList.add("is-invalid");
                    creationTotalCopiesNumberInvalidFeedback.innerText = data.totalCopiesNumberValidationFeedback;
                }

                if (data.publisherTitleValid) {
                    creationPublisherTitleInput.classList.remove("is-invalid");
                    creationPublisherTitleInput.classList.add("is-valid");
                } else {
                    creationPublisherTitleInput.classList.remove("is-valid");
                    creationPublisherTitleInput.classList.add("is-invalid");
                    creationPublisherTitleInvalidFeedback.innerText = data.publisherTitleValidationFeedback;
                }
            }
        }
    ).catch(
         error => {
               console.log(error);
         }
    );
}



function editBook(contextPath) {
    const data  = new FormData();

    data.append("id", editionIdInput.value);
    data.append("isbn", editionIsbnInput.value);
    data.append("title", editionTitleInput.value);
    data.append("author", editionAuthorInput.value);
    data.append("description", editionDescriptionTextArea.value);
    data.append("genre", editionGenreSelect.value);
    data.append("language", editionLanguageSelect.value);
    data.append("totalCopiesNumber", editionTotalCopiesNumberInput.value);
    data.append("publicationDate", editionPublicationDateInput.value);
    data.append("publisherTitle", editionPublisherTitleInput.value);
    if (editionCoverInput.files.length >= 1) data.append("cover", editionCoverInput.files[0]);
    if (editionContentInput.files.length >= 1) data.append("content", editionContentInput.files[0]);

    fetch(contextPath+"/admin/book/edit", { method: "POST", headers: {'Accept': '*.*'}, body: data }).then(
        response => response.json()
    ).then(
        data => {
            if (data.success) {
                window.location.href = contextPath+"/admin/books/show";
            } else {
                if (data.isbnValid) {
                    editionIsbnInput.classList.remove("is-invalid");
                    editionIsbnInput.classList.add("is-valid");
                } else {
                    editionIsbnInput.classList.remove("is-valid");
                    editionIsbnInput.classList.add("is-invalid");
                    editionIsbnInvalidFeedback.innerText = data.isbnValidationFeedback;
                }

                if (data.titleValid) {
                    editionTitleInput.classList.remove("is-invalid");
                    editionTitleInput.classList.add("is-valid");
                } else {
                    editionTitleInput.classList.remove("is-valid");
                    editionTitleInput.classList.add("is-invalid");
                    editionTitleInvalidFeedback.innerText = data.titleValidationFeedback;
                }

                if (data.authorValid) {
                    editionAuthorInput.classList.remove("is-invalid");
                    editionAuthorInput.classList.add("is-valid");
                } else {
                    editionAuthorInput.classList.remove("is-valid");
                    editionAuthorInput.classList.add("is-invalid");
                    editionAuthorInvalidFeedback.innerText = data.authorValidationFeedback;
                }

                if (data.descriptionValid) {
                    editionDescriptionTextArea.classList.remove("is-invalid");
                    editionDescriptionTextArea.classList.add("is-valid");
                } else {
                    editionDescriptionTextArea.classList.remove("is-valid");
                    editionDescriptionTextArea.classList.add("is-invalid");
                    editionDescriptionInvalidFeedback.innerText = data.descriptionValidationFeedback;
                }

                if (data.publicationDateValid) {
                    editionPublicationDateInput.classList.remove("is-invalid");
                    editionPublicationDateInput.classList.add("is-valid");
                } else {
                    editionPublicationDateInput.classList.remove("is-valid");
                    editionPublicationDateInput.classList.add("is-invalid");
                    editionPublicationDateInvalidFeedback.innerText = data.publicationDateValidationFeedback;
                }

                if (data.genreValid) {
                    editionGenreSelect.classList.remove("is-invalid");
                    editionGenreSelect.classList.add("is-valid");
                } else {
                    editionGenreSelect.classList.remove("is-valid");
                    editionGenreSelect.classList.add("is-invalid");
                    editionGenreInvalidFeedback.innerText = data.genreValidationFeedback;
                }

                if (data.languageValid) {
                    editionLanguageSelect.classList.remove("is-invalid");
                    editionLanguageSelect.classList.add("is-valid");
                } else {
                    editionLanguageSelect.classList.remove("is-valid");
                    editionLanguageSelect.classList.add("is-invalid");
                    editionLanguageInvalidFeedback.innerText = data.languageValidationFeedback;
                }

                if (data.totalCopiesNumberValid) {
                    editionTotalCopiesNumberInput.classList.remove("is-invalid");
                    editionTotalCopiesNumberInput.classList.add("is-valid");
                } else {
                    editionTotalCopiesNumberInput.classList.remove("is-valid");
                    editionTotalCopiesNumberInput.classList.add("is-invalid");
                    editionTotalCopiesNumberInvalidFeedback.innerText = data.totalCopiesNumberValidationFeedback;
                }

                if (data.publisherTitleValid) {
                    editionPublisherTitleInput.classList.remove("is-invalid");
                    editionPublisherTitleInput.classList.add("is-valid");
                } else {
                    editionPublisherTitleInput.classList.remove("is-valid");
                    editionPublisherTitleInput.classList.add("is-invalid");
                    editionPublisherTitleInvalidFeedback.innerText = data.publisherTitleValidationFeedback;
                }
            }
        }
    ).catch(
         error => {
               console.log(error);
         }
    );
}

function openDeleteLibrarianModal(userId, userFullname) {
    userIdInput.value = userId;
    userFullnameSpan.innerText = userFullname;
    $('#deleteLibrarianModal').modal('show');
}

function openCreateLibrarianModal() {
    firstnameInput.value = "";
    firstnameInput.classList.remove("is-invalid");
    firstnameInput.classList.remove("is-valid");
    lastnameInput.value = "";
    lastnameInput.classList.remove("is-invalid");
    lastnameInput.classList.remove("is-valid");
    emailInput.value = "";
    emailInput.classList.remove("is-invalid");
    emailInput.classList.remove("is-valid");
    passwordInput.value = "";
    passwordInput.classList.remove("is-invalid");
    passwordInput.classList.remove("is-valid");
    passwordConfirmInput.value = "";
    passwordConfirmInput.classList.remove("is-invalid");
    passwordConfirmInput.classList.remove("is-valid");

    $('#createLibrarianModal').modal('show');
}


function createLibrarian(contextPath) {
    var data = new URLSearchParams();
    data.append("firstname", firstnameInput.value);
    data.append("lastname", lastnameInput.value);
    data.append("email", emailInput.value);
    data.append("password", passwordInput.value);

    fetch(contextPath+"/admin/librarian/create", { method: "POST", body: data }).then(
        response => response.json()
    ).then(
        data => {
            if (data.success) {
                window.location.href = contextPath+"/admin/librarians/show";
            } else {
                if (data.firstnameValid) {
                    firstnameInput.classList.remove("is-invalid");
                    firstnameInput.classList.add("is-valid");
                } else {
                    firstnameInput.classList.remove("is-valid");
                    firstnameInput.classList.add("is-invalid");
                    firstnameInvalidFeedback.innerText = data.firstnameValidationFeedback;
                }

                if (data.lastnameValid) {
                    lastnameInput.classList.remove("is-invalid");
                    lastnameInput.classList.add("is-valid");
                } else {
                    lastnameInput.classList.remove("is-valid");
                    lastnameInput.classList.add("is-invalid");
                    lastnameInvalidFeedback.innerText = data.lastnameValidationFeedback;
                }

                if (data.emailValid) {
                    emailInput.classList.remove("is-invalid");
                    emailInput.classList.add("is-valid");
                } else {
                    emailInput.classList.remove("is-valid");
                    emailInput.classList.add("is-invalid");
                    emailInvalidFeedback.innerText = data.emailValidationFeedback;
                }

                if (data.passwordValid) {
                    passwordInput.classList.remove("is-invalid");
                    passwordInput.classList.add("is-valid");
                } else {
                    passwordInput.classList.remove("is-valid");
                    passwordInput.classList.add("is-invalid");
                    passwordInvalidFeedback.innerText = data.passwordValidationFeedback;
                }

                if (passwordConfirmInput.value == passwordInput.value && passwordConfirmInput.value != null && passwordConfirmInput.value != "") {
                    passwordConfirmInput.classList.remove("is-invalid");
                    passwordConfirmInput.classList.add("is-valid");
                } else {
                    passwordConfirmInput.classList.remove("is-valid");
                    passwordConfirmInput.classList.add("is-invalid");
                }
            }
        }
    ).catch(
         error => {
               console.log(error);
         }
    );
}

function openBlockReaderModal(userId, fullname) {
    blockUserIdInput.value = userId;
    blockFullnameSpan.innerText = fullname;
    $('#blockReaderModal').modal('show');
}

function openUnblockReaderModal(userId, fullname) {
    unblockUserIdInput.value = userId;
    unblockFullnameSpan.innerText = fullname;
    $('#unblockReaderModal').modal('show');
}

function openPayReaderFineModal(userId, fullname) {
    payFineUserIdInput.value = userId;
    payFineFullnameSpan.innerText = fullname;
    $('#payReaderFineModal').modal('show');
}

function onSelectPublisherEditionButtonClick() {
    openSelectPublisherModal(true, (publisher) => {
       editionPublisherTitleInput.value = publisher;
    });
}

function onSelectPublisherCreationButtonClick() {
    openSelectPublisherModal(true, (publisher) => {
       creationPublisherTitleInput.value = publisher;
    });
}