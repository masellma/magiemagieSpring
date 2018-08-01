<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="ennemiBoard">
    <c:forEach items="${joueurs}" var="joueur">
        <c:if test="${joueur.getId() != user.getId()}">
            <div class="ennemiItem">
        	<img src="./img/${joueur.getAvatar()}.png">
        	<div>
        		<h5>${joueur.getPseudo}</h5>
        		<span>Cartes restantes : ${joueur.getCartes().size()}</span>
        	</div>
            </div>
        </c:if>
    </c:forEach>
</div>
<h2>Tour de jeu : ${joueurALaMain}</h2>
<div id="PlayerBoard">
	<c:forEach items="${joueurs}" var="joueur">
        <c:if test="${joueur.getId() == user.getId()}">
	        <div>
	        	<img src="./img/${joueur.getAvatar()}.png">
				<h5>${joueur.getPseudo()}</h5>	
	        </div>
	        <div>
	        	<form method="POST" action="./lancer-sort">
	        		<div id="cardHand">
		        		<c:ForEach items="${joueur.getCartes()}" var="carte">
					        <label for="${carte.getId()}">
					        	<img src="./img/${carte.getTypeCarte()}.png">
					        </label>
					        	
					        <input type="checkbox" name="carte" id="${carte.getId()}">
	       				</c:ForEach>
	        		</div>
	        		<div id="actionItem">
	        			<button type="submit">Lancer un sort</button>
	        		</div>		
	        	</form>
	        </div>
        </c:if>
    </c:forEach>
</div>
