function openBookDetailsModal(bookId, contextPath) {
  fetch(contextPath+"/reader/book/details?bookId="+bookId).then(
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
      bookIdInput2.value = data.id;
      bookIdInput3.value = data.id;
      bookIdInput4.value = data.id;

      addToBookmarksButton.hidden = data.isInUserBookmarks;
      deleteFromBookmarksButton.hidden = !data.isInUserBookmarks;
      makeOrderButton.hidden = data.isInUserOrders;
      downloadBookButton.hidden = !data.hasElectronicFormat;

      feedbackBookIdInput.value = data.id;
    }
  ).catch(
    error => {
      console.log(error);
    }
  );

  $('#bookDetailsModal').modal('show');
}

function switchToAll() {
  advancedSearchBookmarksOnlyInput.value = "false";
  basicSearchBookmarksOnlyInput.value = "false";

  if (basicSearchTab.classList.contains("active")) {
    basicSearchBooksForm.submit();
  } else {
    advancedSearchBooksForm.submit();
  }
}

function switchToBookmarks() {
  advancedSearchBookmarksOnlyInput.value = "true";
  basicSearchBookmarksOnlyInput.value = "true";

  if (basicSearchTab.classList.contains("active")) {
    basicSearchBooksForm.submit();
  } else {
    advancedSearchBooksForm.submit();
  }
}



function openCreateFeedbackModal() {
    $('#createFeedbackModal').modal('show');
}

function changeFeedbackGrade(grade) {
    feedbackGradeInput.value = grade;
    if (grade >= 2) {
        feedbackGrade2ButtonIcon.className = 'bi bi-star-fill';
    } else {
        feedbackGrade2ButtonIcon.className = 'bi bi-star';
    }

    if (grade >= 3) {
        feedbackGrade3ButtonIcon.className = 'bi bi-star-fill';
    } else {
        feedbackGrade3ButtonIcon.className = 'bi bi-star';
    }

    if (grade >= 4) {
        feedbackGrade4ButtonIcon.className = 'bi bi-star-fill';
    } else {
        feedbackGrade4ButtonIcon.className = 'bi bi-star';
    }

    if (grade >= 5) {
        feedbackGrade5ButtonIcon.className = 'bi bi-star-fill';
    } else {
        feedbackGrade5ButtonIcon.className = 'bi bi-star';
    }
}

function openCancelOrderModal(orderCode) {
    cancelOrderCodeInput.value = orderCode;
    $('#cancelOrderModal').modal('show');
}

function openDeleteOrderModal(orderCode) {
    deleteOrderCodeInput.value = orderCode;
    $('#deleteOrderModal').modal('show');
}



function editProfile(contextPath) {
    var data = new URLSearchParams();
    data.append("firstname", firstnameInput.value);
    data.append("lastname", lastnameInput.value);
    data.append("email", emailInput.value);
    data.append("password", passwordInput.value);
    data.append("passwordConfirm", passwordConfirmInput.value);

    fetch(contextPath+"/reader/profile/edit", { method: "POST", body: data }).then(
        response => response.json()
    ).then(
        data => {
            var passwordConfirmValid = passwordConfirmInput.value == passwordInput.value && passwordConfirmInput.value != null && passwordConfirmInput.value != "";

            if (data.success && passwordConfirmValid) {
                window.location.reload();
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

                if (passwordConfirmValid) {
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

function openPayFineModal() {
    $('#payFineModal').modal('show');
}

function payFine(language, contextPath) {
    fetch(contextPath+"/reader/fine/pay", { method: "POST"}).then(
        response => { window.location.reload(); }
    ).catch(
        error => { console.log(error); }
    );
}