$(document).ready(function () {
/*    $('.table .deactBtn').on('click', function (event) {
        event.preventDefault();
        const href = $(this).attr('href');
        $('#deactivateModel #deactivateRef').attr('href', href);
        $('#deactivateModel').modal();
    });*/

    $('.table .actDeactBtn').on('click', function (event) {
        event.preventDefault();
        const href = $(this).attr('href');
        const post = href.split('?');
        const text = $(this).text(); //return New or Edit

        var alertMessage = "";
        var alertBtn = "";
        var alertlabel = "";
        if (text === 'Activate') {
            alertMessage = "Are you sure you want to activate this User?";
            alertBtn = "Activate";
            alertlabel = "Activate User";
        }
        else{
            alertMessage = "Are you sure you want to deactivate this User?";
            alertBtn = "Deactivate";
            alertlabel = "Deactivate User";
        }

        $('#actDeactivateModel #alertMessage').text(alertMessage);
        $('#actDeactivateModel #actDeactivateRef').text(alertBtn);
        $('#actDeactivateModel #actDeactivateModelLabel').text(alertlabel);
        $('#actDeactivateModel').modal();

        $.post(post[0], post[1], function(i) {
        });
    });
});