$(document).ready(function() {
    var table = $('#gradesUser').DataTable({

        initComplete: function () {
            $(".pagination").append($(".dataTables_paginate"));
            this.api().columns([0,1,3,4]).every(function () {
                var column = this;
                var select = $('<select><option value="">Select</option></select>')
                    .appendTo($(column.footer()))
                    .on('change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );

                        column
                            .search(val ? '^' + val + '$' : '', true, false)
                            .draw();
                    });

                column.data().unique().sort().each(function (d, j) {
                    select.append('<option value="' + d + '">' + d + '</option>')
                });
            });
        },
        "fnHeaderCallback": function () {
            $('.dataTables_filter').hide();
            $('.dataTables_length').hide();
        },
        language: {
            paginate: {
                next: '>>',
                previous: '<<'
            }
        },
        "drawCallback": function (settings) {

            $(".paginate_button").addClass('btn btn-outline-secondary');
            $(".current").css("background", "#9e9e9e26")
        },
        "order": [[4, "desc"]]
    });


//
// #myInput is a <input type="text"> element
    $('#inputFilterListSubject').on('keyup', function () {
        table.search(this.value).draw();
    });
//
    $('#length_changegradeUser').val(table.page.len());
    $('#length_changegradeUser').change(function () {
        table.page.len($(this).val()).draw();
    });
    $('tfoot select').addClass('form-control')

});