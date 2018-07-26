$(document).ready(function () {
    $(function(){
        $('#email').focusin(function(){
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