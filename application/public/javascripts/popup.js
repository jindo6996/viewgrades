// Get the modal
// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    var modal = document.getElementById('create_user');
    var row = document.getElementsByClassName("row");
    console.log(modal);
    console.log(event.target);
    if (event.target == modal) {
        modal.style.display = "none";
    }
}