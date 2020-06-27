$(document).ready(function () {
    $('.table .roleBtn').on('click', function (event) {
        event.preventDefault();
        const href = $(this).attr('href');
        const post = href.split('?');

        $('#roleModal').modal();

        //To make GET call as POST
        $.post(post[0], post[1], function(i) {
        })
    });

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

        $('#actDeactivateModal #alertMessage').text(alertMessage);
        $('#actDeactivateModal #actDeactivateRef').text(alertBtn);
        $('#actDeactivateModal #actDeactivateModalLabel').text(alertlabel);
        $('#actDeactivateModal').modal();

        //To make GET call as POST
        $.post(post[0], post[1], function(i) {
        });
    });
});