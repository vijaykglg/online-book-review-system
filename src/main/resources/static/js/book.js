$(document).ready(function () {
    $('#bookForm').validate({
        rules:
            {
                category:{
                    required: true
                },
                author:{
                    required: true
                }
            }
    });

    $('.nBtn, .table .eBtn').on('click', function (event) {
        event.preventDefault();
        const href = $(this).attr('href');//Get the action to be called from HTML's href tag
        const text = $(this).text(); //return New or Edit

        if (text === 'Edit') {
            $.get(href, function (book, status) {
                $('.myForm #id').val(book.id);
                $('.myForm #isbn').val(book.isbn).attr('disabled', 'disabled');
                $('.myForm #title').val(book.title);
                $('.myForm #description').val(book.description);
                $('.myForm #bookImage').val(book.bookImage);

                $('.myForm #releaseDate').attr('type','text');
                $('.myForm #releaseDate').val(book.releaseDate).attr('disabled', 'disabled');

                $('.myForm #category option[value="'+book.category.id+'"]').attr("selected",true);
                $('.myForm #author option[value="'+book.author.id+'"]').attr("selected",true);
                // $('.myForm #author').attr("selected",true).attr('disabled', 'disabled');
            });

            $(".myForm #exampleModal").modal("show");
        } else {
            $('.myForm #isbn').val('');
            $('.myForm #title').val('');
            $('.myForm #description').val('');
            $('.myForm #releaseDate').val('');
            $('.myForm #bookImage').val('');

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