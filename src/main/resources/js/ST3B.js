function showFields(generalfield, selectedfield, submitBtn) {  // handles dataset specific fields
    defaultfield.classList.remove("hidden");
    selectedfield.classList.remove("hidden");
    submitBtn.classList.remove("hidden");
}
function hideAllFields() {
    document.getElementById("indig-status-field").classList.add("hidden");
    document.getElementById("Population-field").classList.add("hidden");
    document.getElementById("school-completion-field").classList.add("hidden");
    document.getElementById("non-school-completion-field").classList.add("hidden");
    document.getElementById("Health-condition-field").classList.add("hidden");
}

document.getElementById("select-dataset-field").addEventListener("change", function () {
    let selectedOption = this.value;
    selectedOption = document.getElementById(selectedOption);
    let generalField = document.getElementById("indig-status-field");
    let submitBtn = document.getElementById("3BformSubmit");

    // Hide all option-specific fields first
    //hideAllFields();

    // Show the fields based on the selected option
    showFields(generalField, selectedOption, submitBtn);
    
});