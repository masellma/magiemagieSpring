$(document).ready(function(){

	$(".imgCarteslogin").on("click", function(){
	    // var checked = $("input:checked").val();
	    $("img").removeClass("labelSelect");
	    $(this).addClass("labelSelect");
	});
	$(".join").on("click", function(){

	    $("#connexion").css({display: "flex"});
	    
	});
	$("#cross").on("click", function(){

	    $("#connexion").css({display: "none"});
	    
	});

	
   
        
    
        

	
	$(function() {
    /**
    * Smooth scrolling to page anchor on click
    **/
    $("a[href*='#']:not([href='#'])").click(function() {
        if (
            location.hostname == this.hostname
            && this.pathname.replace(/^\//,"") == location.pathname.replace(/^\//,"")
        ) {
            var anchor = $(this.hash);
            anchor = anchor.length ? anchor : $("[name=" + this.hash.slice(1) +"]");
            if ( anchor.length ) {
                $("html, body").animate( { scrollTop: anchor.offset().top }, 1000);
            }
        }
    });
});

	

});
