$(document).ready(function () {
    $('#categoryForm').validate();

    $('.nBtn, .table .eBtn').on('click', function (event) {
        event.preventDefault();
        const href = $(this).attr('href');//Get the action to be called from HTML's href tag
        const text = $(this).text(); //return New or Edit

        if (text === 'Edit') {
            $.get(href, function (category, status) {
                $('.myForm #id').val(category.id);
                $('.myForm #name').val(category.name);
                $('.myForm #description').val(category.description);
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
        const href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });
});