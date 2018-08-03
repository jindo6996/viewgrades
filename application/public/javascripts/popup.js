// Get the modal
// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    var modal = document.getElementById('create_user');
    // var row = document.getElementsByClassName("row");
    // console.log(modal);
    // console.log(event.target);
    if (event.target == modal) {
        modal.style.display = "none";
    }
    var modal2 = document.getElementById('edit_user');
    var errorEdit=document.getElementsByClassName('error');
    if (event.target == modal2) {
        modal2.style.display = "none";
        // errorEdit.style.display = "none";

    }
};
