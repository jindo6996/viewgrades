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
    });

$('.editUser').click(
    function getData() {
        console.log(this);
        var row = $(this).closest("tr");
        alert(row.length);
        $("#userIdEdit").val(row.find("td:nth-child(1)").text());
        $("#emailEdit").val(row.find("td:nth-child(2)").text());
        //4:ngay nghi phep, 3departmen, 5date entry, 6role,7 status, 8 last
        $("#entryCompanyDateEdit").val(row.find("td:nth-child(5)").text());
        $("#annualLeaveEdit").val(row.find("td:nth-child(4)").text());
        var role = "#userRoleEdit_" + row.find("td:nth-child(6)").text().toUpperCase();
        $(role).prop("checked", true);
        var status = "#statusEdit_" + row.find("td:nth-child(7)").text().toUpperCase();
        $(status).prop("checked", true);
        $("#departmentEdit").val(row.find("td:nth-child(3)").text());
    }
);