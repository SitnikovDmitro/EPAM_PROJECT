function changeLanguage(language, contextPath) {
    var data = new URLSearchParams();
    data.append("language", language);

    fetch(contextPath+"/language/change", { method: "POST", body: data }).then(
        response => { window.location.reload(); }
    ).catch(
        error => { console.log(error); }
    );
}


function logout(contextPath) {
    fetch(contextPath+"/logout", { method: "POST" }).then(
        response => { window.location.href = contextPath+"/guest/books/show" }
    ).catch(
        error => { console.log(error); }
    );
}


function feedbacksToHtml(feedbacks) {
    var content = [];
    feedbacks.forEach(function(feedback) {
        content.push("<div class=\"card mt-3\">");
        content.push("<div class=\"card-header\">");
        content.push("<span style=\"float: left;\">"+feedback.userFullname+"</span>");
        content.push("<span style=\"float: right;\">");
        for (i = 1; i <= 5; i++) {
            if (i <= feedback.grade) {
                content.push(" <i class=\"bi-star-fill\"></i>");
            } else {
                content.push(" <i class=\"bi-star\"></i>");
            }
        }
        content.push("</span>");
        content.push("</div>");
        content.push("<div class=\"card-body\">");
        content.push("<p class=\"card-text\">"+feedback.text+"</p>");
        content.push("<small class=\"text-muted m-0 p-0\" style=\"float: right;\">"+feedback.creationDate+"</small>");
        content.push("</div>");
        content.push("</div>");
    });
    return content.join("");
}

function publishersToHtml(publishers) {
    var content = [];
    publishers.forEach(function(publisher) {
        content.push("<button type=\"button\" class=\"list-group-item list-group-item-action\" onclick=\"onPublisherButtonClick('"+publisher+"', this);\">"+publisher+"</button>");
    });
    return content.join("");
}


function onPublisherButtonClick(publisherTitle, clickedButton) {
    var wasActive = clickedButton.classList.contains("active");

    publisherList.querySelectorAll("button").forEach(function(button) {
        button.classList.remove("active");
    });

    if (wasActive) {
        publisherHiddenInput.value = "";
        selectButton.disabled = true;
        createNewAndSelectButton.disabled = false;
        clickedButton.classList.remove("active");
    } else {
        publisherHiddenInput.value = publisherTitle;
        selectButton.disabled = false;
        createNewAndSelectButton.disabled = true;
        clickedButton.classList.add("active");
    }
}


function loadPublishers(contextPath) {
    fetch(contextPath+"/publishers/find?query="+publisherInput.value).then(
        response => response.json()
    ).then(
        data => {
            if (publisherInput.value.length === 0) {
                noResultsLabel.hidden = true;
                searchResultsLabel.hidden = true;
                inputQueryLabel.hidden = false;

                selectButton.disabled = true;
                createNewAndSelectButton.disabled = true;

                publisherList.innerHTML = "";

                return;
            }

            publisherList.innerHTML = publishersToHtml(data.publishers);
            selectButton.disabled = true;
            createNewAndSelectButton.disabled = false;

            if (data.publishers.length > 0) {
                noResultsLabel.hidden = true;
                searchResultsLabel.hidden = false;
            } else {
                noResultsLabel.hidden = false;
                searchResultsLabel.hidden = true;
            }
            inputQueryLabel.hidden = true;
        }
    ).catch(
        error => {
            console.log(error);
        }
    );
}

function openSelectPublisherModal(createNewEnabled, selectPublisherCallback) {
    if (createNewEnabled) {
        selectPublisherLabel.hidden = true;
        selectPublisherOrCreateNewLabel.hidden = false;
        createNewAndSelectButton.hidden = false;
    } else {
        selectPublisherLabel.hidden = false;
        selectPublisherOrCreateNewLabel.hidden = true;
        createNewAndSelectButton.hidden = true;
    }

    noResultsLabel.hidden = true;
    searchResultsLabel.hidden = true;
    inputQueryLabel.hidden = false;

    selectButton.disabled = true;
    createNewAndSelectButton.disabled = true;

    publisherList.innerHTML = "";
    publisherInput.value = "";

    selectButton.addEventListener("click", function() {
        selectPublisherCallback(publisherHiddenInput.value);
        $('#selectPublisherModal').modal('hide');
    });

    createNewAndSelectButton.addEventListener("click", function() {
        selectPublisherCallback(publisherInput.value);
        $('#selectPublisherModal').modal('hide');
    });

    $('#selectPublisherModal').modal('show');
}

function selectPublisher(publisher) {
     publisherQueryInput.value = publisher;
     $('#selectPublisherModal').modal('hide');
}

function onSelectPublisherButtonClick() {
     openSelectPublisherModal(false, (publisher) => {
        publisherQueryInput.value = publisher;
     });
}


