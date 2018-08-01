<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="ennemiBoard">
    <c:forEach items="${joueurs}" var="joueur">
        <c:if test="${joueur.getId() != user.getId()}">
            <div class="ennemiItem <c:if test="${joueur.getId() == joueurALaMain.getId()}">ALaMain</c:if>">
        	<img src="./img/${joueur.getAvatar()}.png">
        	<div>
        		<h5>${joueur.getPseudo()}</h5>
        		<span>Cartes restantes : ${joueur.getCartes().size()}</span>
        	</div>
            </div>
        </c:if>
    </c:forEach>
</div>
<h2>Tour de jeu : ${joueurALaMain.getPseudo()}</h2>
<div id="help">
                <div id="helpIcon"><img src="./img/icon.png"></div>
                <div id="helpContent">
                    <div class="helpItems">
                        <h3>Divination</h3>
                        <img src="./img/AILE_CHAUVE_SOURIS.png">
                        <img src="./img/LAPIS_LAZULI.png">
                    </div>
                    <div class="helpItems">
                        <h3>Invisibilité</h3>
                        <img src="./img/CORNE_LICORNE.png">
                        <img src="./img/BAVE_CRAPAUD.png">
                    </div>
                    <div class="helpItems">
                        <h3>Hypnose</h3>
                        <img src="./img/CORNE_LICORNE.png">
                        <img src="./img/MANDRAGORE.png">
                    </div>
                    <div class="helpItems">
                        <h3>Philtre d'amour</h3>
                        <img src="./img/LAPIS_LAZULI.png">
                        <img src="./img/BAVE_CRAPAUD.png">
                    </div>
                    <div class="helpItems">
                        <h3>Sommeil profond</h3>
                        <img src="./img/AILE_CHAUVE_SOURIS.png">
                        <img src="./img/MANDRAGORE.png">
                    </div>
                </div>
            </div>
<div id="PlayerBoard">
	<c:forEach items="${joueurs}" var="joueur">
        <c:if test="${joueur.getId() == user.getId()}">
	        <div id="Player">
	        	<img src="./img/${joueur.getAvatar()}.png">
				<h5>${joueur.getPseudo()}</h5>	
	        </div>
	        <div id="PlayerHand">
	        	<form method="POST" action="./lancer-sort">
	        		<div id="cardHand">
		        		<c:forEach items="${joueur.getCartes()}" var="carte">
		        		<div class="CardItem">
		        			<label for="${carte.getId()}">
					        	<img src="./img/${carte.getTypeCarte()}.png">
					        </label>
					        	
					        <input class="checkbox" type="checkbox" name="carte" id="${carte.getId()}">
		        		</div>
	       				</c:forEach>
	        		</div>
	        		<div id="actionItem">
                                    <c:if test="${user.getId() != joueurALaMain.getId()}">
                                            <div id="locked"><img src="./img/locked-padlock.png"></div>
                                        </c:if>
	        			
	        			<span class="btn btn-primary" id="lancerSort">Lancer un sort</span>
	        			<span id="passerTour" class="btn btn-danger">Passer son tour</a> 
	        		</div>		
	        	</form>
	        </div>
        </c:if>
    </c:forEach>
</div>
<script type="text/javascript">
	function cardSizeAdapt(){
                var nb = $(".CardItem").length;
//                console.log(nb);
                var adapt = 100/nb;
//                console.log(nb);
                $(".CardItem").css({width: ""+adapt+"%"});
            }
            cardSizeAdapt();
    //selected
	$(".CardItem label").on("click", function(){

		if ($(this).hasClass("selected")) {
			$(this).addClass("deselected");
			$(this).removeClass("selected");
			// $(this).removeClass("deselected");
		} else {
			if ($(".selected").length < 2) {
				$(this).addClass("selected");
			} 
		}

		if ($(".selected").length < 2) {
                    $("#lancerSort").css({display: "none"});
                  
		} else {
                    $("#lancerSort").css({display: "block"});
		}
                
					    
	});
        function checked(){
            if ($('.checkbox:checked').length > 1) {
            $('.checkbox:not(:checked)').attr('disabled',true);
            } else {
            $('.checkbox:not(:checked').attr('disabled',false);
            }
        }
        setInterval(checked, 500);
        
        function Passer_Son_Tour() {
                
                $.get("passer-tour");
            }

	$("#passerTour").on("click", function(){
		Passer_Son_Tour();
	});
        
        $("#lancerSort").on("click", function(){
           $.ajax({
                url : "lancer-sort",
                type : "POST",
                data : { carte1 : "valeurId1",
                         carte2 : "valeurId2"
                },
                success : function(response){
                 console.log(response);
                }
            });
           
        });
        
        
	$(document).ready(function(){
		var compt = 0;
                $("#helpIcon").on("click", function(){
                	if (compt === 0) {
                		compt = 1;
                		$("#helpContent").css({width : "250px"});	
                		$("#help").css({width : "20%"});	
                	} else {
                		compt = 0;
                		$("#helpContent").css({width : "0px"});	
                		$("#help").css({width : "4%"});	
                	}
                    

                });
            });
            
            
</script>