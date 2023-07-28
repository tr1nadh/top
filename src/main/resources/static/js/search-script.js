function placeTrigger(prePlaceholder) {
    let select = document.getElementById('search-option-select');
    let searchInputField = document.getElementById('search-field');
    searchInputField.placeholder = prePlaceholder + ' ' + select.value;
}
function clearSearch() {
    let searchField = document.getElementById('search-field');
    searchField.value = '';
    document.getElementById('clear-btn').style.display = 'none';
    document.getElementById('search-form').submit();
}