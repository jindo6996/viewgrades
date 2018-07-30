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
        // window.history.forward();
        // window.history.forward();
    });
    // if (window.hasClass('error')) alert(2);
    // if (window.history && window.history.pushState) {
    //
    //     window.history.pushState("forward", null, null);
    //
    //     $(window).on('popstate', function() {
    //          alert(1);//location.replace(document.referrer);
    //     });
    //
    // }
});


