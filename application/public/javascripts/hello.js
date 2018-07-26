$(document).ready(function () {
    if (window.console) {
        console.log("Welcome to your Play application's JavaScript!");
    }

    $(function(){
        $('#email').focusin(function(){
          console.log("sjhdfksdf");
            $('dd.error').hide();
            $('span.error').hide();
        });
        $('#password').focusin(function(){
            console.log("sjhdfksdf");
            $('dd.error').hide();
            $('span.error').hide();
        });
    });
})