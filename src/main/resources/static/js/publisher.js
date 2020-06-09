$(document).ready(function () {
    /*$('.nBtn, .table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');//Get the action to be called from HTML's href tag
        var text = $(this).text(); //return New or Edit

        if (text === 'Edit') {
            $.get(href, function (publisher, status) {
                $('.myForm #id').val(publisher.id);
                $('.myForm #name').val(publisher.name);
                $('.myForm #description').val(publisher.description);
            });
            $(".myForm #exampleModal").modal("show");
        } else {
            $('.myForm #name').val('');
            $('.myForm #description').val('');
            $('.myForm #exampleModal').modal();
        }
    });*/

    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });
});