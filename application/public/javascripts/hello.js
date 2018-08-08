$(function(){
        console.log("dadad");
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
    $('button').click(function(){
        $('dd.error').hide();
        $('span.error').hide();
    });
    });

$('#example').on("click",".editUser",function() {
        console.log(this);
        var row = $(this).closest("tr");
        // alert(row.length);
        $("#userIdEdit").val(row.find("td:nth-child(1)").text());
        $("#emailEdit").val(row.find("td:nth-child(2)").text());
        var role = "#userRoleEdit_" + row.find("td:nth-child(3)").text().toUpperCase();
        $(role).prop("checked", true);
        var status = "#statusEdit_" + row.find("td:nth-child(4)").text().toUpperCase();
        $(status).prop("checked", true);
    }
);

