function showAlertToast(alertMessage) {
  document.getElementById("alertMessage").innerHTML = alertMessage;
  const alertToast = document.getElementById("alertToast");
  const toastBootstrap = bootstrap.Toast.getOrCreateInstance(alertToast);
  document.addEventListener("DOMContentLoaded", () => {
    toastBootstrap.show();
  });
}

function checkForToastAlert(alertMessage) {
  if (alertMessage) {
    showAlertToast(alertMessage);
  }
}

checkForToastAlert(alertMessage);
