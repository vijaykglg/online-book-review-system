$(document).ready(function () {
    $('.nBtn, .table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');//Get the action to be called from HTML's href tag
        var text = $(this).text(); //return New or Edit

        if (text === 'Edit') {
            $.get(href, function (book, status) {
                $('.myForm #id').val(book.id);
                $('.myForm #isbn').val(book.isbn);
                $('.myForm #title').val(book.title);

                $('.myForm #category option:selected').val(book.category.id);
                $('.myForm #author option:selected').val(book.author.id);
                $('.myForm #category option:selected').text();
            });

            $(".myForm #exampleModal").modal("show");
        } else {
            $('.myForm #isbn').val('');
            $('.myForm #title').val('');
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