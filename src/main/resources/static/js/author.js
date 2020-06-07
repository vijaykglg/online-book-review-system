$(document).ready(function () {

    $('.nBtn, .table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');//Get the action to be called from HTML's href tag
        var text = $(this).text(); //return New or Edit

        if (text === 'Edit') {
            $.get(href, function (author, status) {
                $('.myForm #id').val(author.id);
                $('.myForm #name').val(author.name);
                $('.myForm #description').val(author.description);
            });
            $('.myForm #exampleModal').modal();
        } else {
            $('.myForm #name').val('');
            $('.myForm #description').val('');
            $('.myForm #exampleModal').modal();
        }
    });

    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });
});