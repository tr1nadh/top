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

function popupWindow(url, name) {
    let params = `scrollbars=no,resizable=no,status=no,location=no,toolbar=no,menubar=no,
    width=600,height=800,left=200,top=100`;

    window.open(url, name, params);
}
