// Responsive-navbar-active-animation
function test() {
    var tabsNewAnim = $('#navbarSupportedContent');
    var activeItemNewAnim = tabsNewAnim.find('.active');
    
    // Xuất thông tin về mục đang hoạt động
    console.log("Active item:", activeItemNewAnim);

    var activeWidthNewAnimHeight = activeItemNewAnim.innerHeight();
    var activeWidthNewAnimWidth = activeItemNewAnim.innerWidth();
    var itemPosNewAnimTop = activeItemNewAnim.position();
    var itemPosNewAnimLeft = activeItemNewAnim.position();

    // Xuất thông tin kích thước và vị trí
    console.log("Active item height:", activeWidthNewAnimHeight);
    console.log("Active item width:", activeWidthNewAnimWidth);
    console.log("Active item position:", itemPosNewAnimTop, itemPosNewAnimLeft);

    $(".hori-selector").css({
        "top": itemPosNewAnimTop.top + "px",
        "left": itemPosNewAnimLeft.left + "px",
        "height": activeWidthNewAnimHeight + "px",
        "width": activeWidthNewAnimWidth + "px"
    });

    $("#navbarSupportedContent").on("click", "li", function (e) {
        $('#navbarSupportedContent ul li').removeClass("active");
        $(this).addClass('active');
        
        // Xuất thông tin về mục được nhấn
        console.log("Clicked item:", $(this));

        var activeWidthNewAnimHeight = $(this).innerHeight();
        var activeWidthNewAnimWidth = $(this).innerWidth();
        var itemPosNewAnimTop = $(this).position();
        var itemPosNewAnimLeft = $(this).position();
        
        // Xuất thông tin kích thước và vị trí của mục được nhấn
        console.log("Clicked item height:", activeWidthNewAnimHeight);
        console.log("Clicked item width:", activeWidthNewAnimWidth);
        console.log("Clicked item position:", itemPosNewAnimTop, itemPosNewAnimLeft);

        $(".hori-selector").css({
            "top": itemPosNewAnimTop.top + "px",
            "left": itemPosNewAnimLeft.left + "px",
            "height": activeWidthNewAnimHeight + "px",
            "width": activeWidthNewAnimWidth + "px"
        });
    });
}

$(document).ready(function () {
    setTimeout(function () { test(); });
});

$(window).on('resize', function () {
    console.log("Window resized");
    setTimeout(function () { test(); }, 200);
});

$(".navbar-toggler").click(function () {
    $(".navbar-collapse").slideToggle(200);
    console.log("Navbar toggled");
    setTimeout(function () { test(); });
});

// Add active class-on another-page move
jQuery(document).ready(function ($) {
    // Get current path and find target link
    var path = window.location.pathname.replace("localhost:8080/", "");
    
    // Account for home page with empty path
    if (path === '') {
        path = 'index.html';
    }

    console.log("Current path:", path);

    var target = $('#navbarSupportedContent ul li a[href="' + path + '"]');

    // Add active class to target link
    target.parent().addClass('active');
    console.log("Target link activated:", target);
});

// Add active class on another page linked
$(window).on('load', function () {
    var current = location.pathname;
    console.log("Current pathname on load:", current);

    $('#navbarSupportedContent ul li a').each(function () {
        var $this = $(this);
        // if the current path is like this link, make it active
        if ($this.attr('href').indexOf(current) !== -1) {
            $this.parent().addClass('active');
            $this.parents('.menu-submenu').addClass('show-dropdown');
            $this.parents('.menu-submenu').parent().addClass('active');
            console.log("Link activated:", $this);
        } else {
            $this.parent().removeClass('active');
        }
    });
    test();
});

// Logout function
function logout() {
    localStorage.clear();
    console.log("Logged out and localStorage cleared");
}
