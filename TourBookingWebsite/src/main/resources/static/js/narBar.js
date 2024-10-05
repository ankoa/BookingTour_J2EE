// --------- Responsive Navbar Active Animation ----------- 
function test() {
    var tabsNewAnim = $('#navbarSupportedContent');
    var activeItemNewAnim = tabsNewAnim.find('.active');
    var activeWidthNewAnimHeight = activeItemNewAnim.innerHeight();
    var activeWidthNewAnimWidth = activeItemNewAnim.innerWidth();
    var itemPosNewAnim = activeItemNewAnim.position();

    $(".hori-selector").css({
        "top": itemPosNewAnim.top + "px",
        "left": itemPosNewAnim.left + "px",
        "height": activeWidthNewAnimHeight + "px",
        "width": activeWidthNewAnimWidth + "px"
    });

    $("#navbarSupportedContent").on("click", "li", function() {
        $('#navbarSupportedContent ul li').removeClass("active");
        $(this).addClass('active');

        var activeWidthNewAnimHeight = $(this).innerHeight();
        var activeWidthNewAnimWidth = $(this).innerWidth();
        var itemPosNewAnim = $(this).position();

        $(".hori-selector").css({
            "top": itemPosNewAnim.top + "px",
            "left": itemPosNewAnim.left + "px",
            "height": activeWidthNewAnimHeight + "px",
            "width": activeWidthNewAnimWidth + "px"
        });
    });
}

$(document).ready(function() {
    setTimeout(function() {
        test();
    }, 500);
});

$(window).on('resize', function() {
    setTimeout(function() {
        test();
    }, 200);
});

$(".navbar-toggler").click(function() {
    $(".navbar-collapse").slideToggle(200);
    setTimeout(function() {
        test();
    }, 500);
});

// -------------- Add active class on page load based on URL --------------
jQuery(document).ready(function($) {
    // Get current path and find target link
    var path = window.location.pathname.replace("/", "");
    console.log(path);

    // Account for home page with empty path
    if (path === '') {
        path = 'index.html'; // Change this if your home page has a different name
    }

    var target = $('#navbarSupportedContent ul li a[href="' + path + '"]');
    console.log(target);

    // Add active class to target link
    target.parent().addClass('active');
});

// Add active class on another page linked
$(window).on('load', function() {
    var current = location.pathname;
    console.log(current);

    $('#navbarSupportedContent ul li a').each(function() {
        var $this = $(this);
        // If the current path matches this link, make it active
        if ($this.attr('href') === current || ($this.attr('href') === '' && current === '/')) {
            $this.parent().addClass('active');
            $this.parents('.menu-submenu').addClass('show-dropdown');
            $this.parents('.menu-submenu').parent().addClass('active');
        } else {
            $this.parent().removeClass('active');
        }
    });
    
    test(); // Call test to update the active selector position
});
