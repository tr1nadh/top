  function openUpdate(id) {
    let inputField = document.getElementById('field-' + id);
    let closeBtn = document.getElementById('field-' + id + '-close');
    let isDisabled = inputField.disabled;
    if (isDisabled === true) {
      inputField.removeAttribute('disabled');
      closeBtn.style.display = 'block';
    }
  }

  function closeUpdate(id) {
    document.getElementById('field-' + id).setAttribute('disabled','disabled');
    document.getElementById('field-' + id + '-close').style.display = 'none';
  }

  function confirmDelete(link) {
    let text = "Are you sure you want to delete?";
    if (confirm(text) == true) {
      window.location.replace(link);
    }
  }

  function swapElements(id1, id2) {
      toggleData(id1);
      toggleData(id2);
  }

  function toggleData(id) {
      let displayData = document.getElementById(id).style.display;
      if (displayData === 'block') {
          document.getElementById(id).style.display = 'none';
      } else {
          document.getElementById(id).style.display = 'block';
      }
  }