function confirmDelete(link) {
  let text = "Are you sure you want to delete?";
  if (confirm(text) == true) {
    window.location.replace(link);
  }
}

function submitForm(id) {
  document.getElementById(id).submit();
}
