
    function confirmDelete(link) {
        let text = "Are you sure you want to delete?";
        if (confirm(text) == true) {
          window.location.replace(link);
        }
    }

    function showAlertToast(alertMessage) {
        document.getElementById('alertMessage').innerHTML = alertMessage;
        const alertToast = document.getElementById('alertToast')
        const toastBootstrap = bootstrap.Toast.getOrCreateInstance(alertToast)
        document.addEventListener('DOMContentLoaded', () => {
            toastBootstrap.show()
        })
    }

    function submitForm(id) {
        document.getElementById(id).submit();
    }

    function checkForToastAlert(alertMessage) {
        if (alertMessage) {
            showAlertToast(alertMessage);
        }
    }

    checkForToastAlert(alertMessage);