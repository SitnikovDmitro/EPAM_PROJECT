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
      formatSpan.textContent = data.formatText;
      publisherTitleSpan.textContent = data.publisherTitle;
      averageGradeSpan.textContent = data.averageGrade + " / 5";
      bookImage.src = contextPath+"/book/cover?bookId="+data.id;

      feedbackBlock.innerHTML = feedbacksToHtml(data.feedbacks);
    }
  ).catch(
    error => {
      console.log(error);
    }
  );

  $('#bookDetailsModal').modal('show');
}




function openSignUpModal() {
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

     captchaInvalidFeedback.hidden = true;
     grecaptcha.reset();

     $('#sendAccessLinkModal').modal('hide');
     $('#signInModal').modal('hide');
     $('#signUpModal').modal('show');
}

function openSignInModal() {
     signInEmailInput.value = "";
     signInEmailInput.classList.remove("is-invalid");
     signInEmailInput.classList.remove("is-valid");
     signInPasswordInput.value = "";
     signInPasswordInput.classList.remove("is-invalid");
     signInPasswordInput.classList.remove("is-valid");

     $('#sendAccessLinkModal').modal('hide');
     $('#signUpModal').modal('hide');
     $('#signInModal').modal('show');
}

function openSendAccessLinkModal() {
     sendAccessLinkEmailInput.value = "";
     sendAccessLinkEmailInput.classList.remove("is-invalid");
     sendAccessLinkEmailInput.classList.remove("is-valid");
     sendAccessLinkPasswordInput.value = "";
     sendAccessLinkPasswordInput.classList.remove("is-invalid");
     sendAccessLinkPasswordInput.classList.remove("is-valid");

     $('#signUpModal').modal('hide');
     $('#signInModal').modal('hide');
     $('#sendAccessLinkModal').modal('show');
}

function showMessage() {
     $('#sendAccessLinkModal').modal('hide');
     $('#signUpModal').modal('hide');
     $('#signInModal').modal('hide');
     $('#messageToast').toast('show');
}




function signUp(contextPath) {
    var data = new URLSearchParams();
    data.append("firstname", firstnameInput.value);
    data.append("lastname", lastnameInput.value);
    data.append("email", emailInput.value);
    data.append("password", passwordInput.value);
    data.append("gCaptchaResponse", grecaptcha.getResponse());

    grecaptcha.reset();

    fetch(contextPath+"/sign-up", { method: "POST", body: data }).then(
        response => response.json()
    ).then(
        data => {
            var passwordConfirmValid = passwordConfirmInput.value == passwordInput.value && passwordConfirmInput.value != null && passwordConfirmInput.value != "";

            if (data.success && passwordConfirmValid) {
                window.location.href = contextPath+"/reader/books/show";
            } else {
                if (data.captchaValid) {
                    captchaInvalidFeedback.hidden = true;
                } else {
                    captchaInvalidFeedback.hidden = false;
                    captchaInvalidFeedback.innerText = data.captchaValidationFeedback;
                }

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


function signIn(contextPath) {
    var data = new URLSearchParams();
    data.append("email", signInEmailInput.value);
    data.append("password", signInPasswordInput.value);

    fetch(contextPath+"/sign-in", { method: "POST", body: data }).then(
        response => response.json()
    ).then(
        data => {
            if (data.success) {
                if (data.role == 0) {
                    window.location.href = contextPath+"/admin/books/show";
                } else if (data.role == 1) {
                    window.location.href = contextPath+"/librarian/orders/show";
                } else {
                    window.location.href = contextPath+"/reader/books/show";
                }
            } else {
                signInEmailInput.classList.remove("is-valid");
                signInEmailInput.classList.add("is-invalid");
                signInEmailInvalidFeedback.innerText = data.validationFeedback;
                signInPasswordInput.classList.remove("is-valid");
                signInPasswordInput.classList.add("is-invalid");
                signInPasswordInvalidFeedback.innerText = data.validationFeedback;
            }
        }
    ).catch(
         error => {
               console.log(error);
         }
    );
}

function sendAccessLinkMessage(contextPath) {
    var data = new URLSearchParams();
    data.append("email", sendAccessLinkEmailInput.value);
    data.append("password", sendAccessLinkPasswordInput.value);

    fetch(contextPath+"/access-link/send", { method: "POST", body: data }).then(
        response => response.json()
    ).then(
        data => {
            if (data.success) {
                showMessage();
            } else {
                if (data.emailValid) {
                    sendAccessLinkEmailInput.classList.remove("is-invalid");
                    sendAccessLinkEmailInput.classList.add("is-valid");
                } else {
                    sendAccessLinkEmailInput.classList.remove("is-valid");
                    sendAccessLinkEmailInput.classList.add("is-invalid");
                    sendAccessLinkEmailInvalidFeedback.innerText = data.emailValidationFeedback;
                }

                if (data.passwordValid) {
                    sendAccessLinkPasswordInput.classList.remove("is-invalid");
                    sendAccessLinkPasswordInput.classList.add("is-valid");
                } else {
                    sendAccessLinkPasswordInput.classList.remove("is-valid");
                    sendAccessLinkPasswordInput.classList.add("is-invalid");
                    sendAccessLinkPasswordInvalidFeedback.innerText = data.passwordValidationFeedback;
                }
            }
        }
    ).catch(
         error => {
               console.log(error);
         }
    );
}