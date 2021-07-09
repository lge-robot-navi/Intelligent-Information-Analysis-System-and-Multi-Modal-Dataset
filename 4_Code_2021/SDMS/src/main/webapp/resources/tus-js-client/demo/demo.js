/* global tus */
/* eslint no-console: 0 */

"use strict";

var upload          = null;
var uploadIsRunning = false;
var toggleBtn       = document.querySelector("#toggle-btn");
var resumeCheckbox  = document.querySelector("#resume");
var input           = document.querySelector("input[type=file]");
var progress        = document.querySelector(".progress");
//var progressBar     = progress.querySelector(".bar");
var progressBar     = progress.querySelector(".progress-bar");
var progressBarText = document.querySelector("#progress-bar-text");
var alertBox        = document.querySelector("#support-alert");
var uploadList      = document.querySelector("#upload-list");
var chunkInput      = document.querySelector("#chunksize");
var endpointInput   = document.querySelector("#endpoint");
var dbInsert      	= document.querySelector("#db-insert");
var fileUid			= document.querySelector("#fileuid");
var uploadUrl		= "";

if (!tus.isSupported) {
  alertBox.classList.remove("hidden");
}

if (!toggleBtn) {
  throw new Error("Toggle button not found on this page. Aborting upload-demo. ");
}

toggleBtn.addEventListener("click", function (e) {
  e.preventDefault();

  if (upload) {
    if (uploadIsRunning) {
      upload.abort();
      toggleBtn.textContent = "Resume Upload";
      uploadIsRunning = false;
    } else {
      upload.start();
      toggleBtn.textContent = "Pause Upload";
      uploadIsRunning = true;
    }
  } else {
    if (input.files.length > 0) {
      startUpload();
    } else {
      input.click();
    }
  }
});

//input.addEventListener("change", startUpload);
input.addEventListener("change", changeInput);

function changeInput() {
	progressBar.style.width = "0%";
	dbInsert.style.visibility = "hidden";
}

function startUpload() {
	
  var file = input.files[0];
  // Only continue if a file has actually been selected.
  // IE will trigger a change event even if we reset the input element
  // using reset() and we do not want to blow up later.
  if (!file) {
    return;
  }

//  alert("endpoint: "+endpointInput.value);

  var endpoint = endpointInput.value;
  var chunkSize = parseInt(chunkInput.value, 10);
  if (isNaN(chunkSize)) {
    chunkSize = Infinity;
  }

  toggleBtn.textContent = "Pause Upload";

  var options = {
    endpoint: endpoint,
//    resume  : !resumeCheckbox.checked,
    resume : true,
    chunkSize: chunkSize,
    retryDelays: [0, 1000, 3000, 5000],
    metadata: {
      filename: file.name,
      filetype: file.type
    },
    onError : function (error) {
      if (error.originalRequest) {
        if (window.confirm("Failed because: " + error + "\nDo you want to retry?")) {
          upload.start();
          uploadIsRunning = true;
          return;
        }
      } else {
        window.alert("Failed because: " + error);
      }

      reset();
    },
    onProgress: function (bytesUploaded, bytesTotal) {
      var percentage = (bytesUploaded / bytesTotal * 100).toFixed(2);
      progressBar.style.width = percentage + "%";
      progressBarText.innerHTML = percentage + "%";
      console.log(bytesUploaded, bytesTotal, percentage + "%");
    },
    onSuccess: function () {
//      var anchor = document.createElement("a");
//      anchor.textContent = "Download " + upload.file.name + " (" + upload.file.size + " bytes)";
//      anchor.href = upload.url;
//      anchor.className = "btn btn-warning";
//      uploadList.appendChild(anchor);

      uploadUrl = upload.url;
      var urlPos = uploadUrl.lastIndexOf("/");
      uploadUrl = uploadUrl.substring(urlPos+1);
      fileUid.value = uploadUrl;
      
      dbInsert.style.visibility = "visible";
      
      reset();
    }
  };

  upload = new tus.Upload(file, options);
  upload.start();
  uploadIsRunning = true;
}

function reset() {
  input.value = "";
  toggleBtn.textContent = "Start Upload";
  upload = null;
  uploadIsRunning = false;
}
