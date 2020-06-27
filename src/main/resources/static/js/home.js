jQuery(document).ready(function($) {
    $('#books .w3-container .tagBtn').click(function(e) {
        $('a').removeClass('active');
        $(this).addClass('active');
        $(this).filter(function(){return this.href==location.href}).parent().addClass('active').siblings().removeClass('active')
    });

    $(function() {
        if( location.href.split("#")[1] != "") {
            $('#books .w3-container .tagBtn[href*="' + location.href.split("#")[1] + '"]').click();
        }
    });

    $(".hBack").on("click", function(e){
        e.preventDefault();
        window.history.back();
    });
})