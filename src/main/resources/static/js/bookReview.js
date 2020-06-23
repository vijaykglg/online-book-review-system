$(document).ready(function () {
    const isAuthenticated = $('#isAuthenticated').text();
    $('.nBtn, .table .eBtn').on('click', function (event) {
        event.preventDefault();

        if (isAuthenticated === 'true'){
            $('#myModal').modal('hide');

            $('.myForm #rating').val('');
            $('.myForm #reviewText').val('');
            $('.myForm #exampleModal').modal();
        }else{
            $('.myForm #exampleModal').modal('hide');

            $('#myModal').modal();
        }
    });

    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        const href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });

    $(".hBack").on("click", function(e){
        e.preventDefault();
        window.history.back();
    });
});