$(document).ready(function () {
    $(function(){
        $('#email').focusin(function(){
            $('dd.error').hide();
            $('span.error').hide();
        });
        $('#password').focusin(function(){
            $('dd.error').hide();
            $('span.error').hide();
        });
        $('.form-control').focusin(function(){
            $('dd.error').hide();
            $('span.error').hide();
        });

    });

});


