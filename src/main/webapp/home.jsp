<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<!-- <link rel="icon" href="img/logo.ico">

	<link href="https://fonts.googleapis.com/css?family=Markazi+Text" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Kaushan+Script" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="style.css"> -->
	<c:import url="./_STYLESHEETS.jsp"/>
	<title>Magie-Magie | La guerre des sorcières</title>
</head>
<body>
    <div id="connexion"<c:choose><c:when test="${cookie.partieNom != null}">style="display: flex;"</c:when><c:when test="${cookie.idPartie != null}">style="display: flex;"</c:when></c:choose> style="background-color: rgba(211,211,211,0.4);">

            <form method="POST" action="./connexion<c:if test="${cookie.partieNom != null}">-join</c:if><c:if test="${cookie.idPartie != null}">-join-game</c:if>">
			<div>
				<h3>Connexion</h3>
				<img id="cross" src="img/cross.png">
			</div>
			<div class="formItem">
				<label for="login">Pseudo</label>
				<input type="text" name="login"  placeholder="Jojo">
			</div>
			<div>
				<label for="izalith">
					<div  class="col25 float">
						<img id="izalithLbl" class="imgCarteslogin" src="img/Izalith.png"></div>
				</label>
				<label for="melusine">
					<div  class="col25 float">
						<img id="melusineLbl" class="imgCarteslogin" src="img/Melusine.png"></div>
				</label>
				<label for="quelaag">
					<div  class="col25 float">
						<img id="quelaagLbl" class="imgCarteslogin" src="img/Quelaag.png"></div>
				</label>
				<label for="salem">
					<div class="col25 float">
						<img id="salemLbl" class="imgCarteslogin" src="img/Salem.png"></div>
				</label>
			</div>
			<div style="visibility:hidden; position: absolute;">
				<input id="izalith" type="radio" name="avatar" value="Izalith">
				<input id="melusine" type="radio" name="avatar" value="Melusine">
				<input id="quelaag" type="radio" name="avatar" value="Quelaag">
				<input id="salem" type="radio" name="avatar" value="Salem">
			</div>
			<button class="btn2" type="submit">Log in</button>

		</form>
	</div>
	<div id="home">
		<div id="header">
			<img src="img/logo.png">
			<div id="nav">
                        <c:if test="${user != null}">
                                    <span>Bienvenue ${user.getPseudo()}</span>
                                </c:if>
				<a class="" href="#createGame" >Rejoindre une partie</a>
				<a class="" href="#inactiveGame" >Créer une partie</a>
                                <c:choose>
                                    <c:when test="${user != null}">
                                        <a class="" href="./deconnexion" >Déconnexion</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="join">Connexion</a>
                                    </c:otherwise>
                                </c:choose>
			</div>
		</div>
		<nav>
			<header><img src="img/topnav.png"></header>
		</nav>
	</div>
	<section id="second">
		<h2>Combattez avec votre sorcière favorite!</h2>
		<article>
			<div class="col25 float"><a href="home.html?avatar=Izalith"><img class="imgCartes" src="img/Izalith.png"></a></div>
			<div class="col25 float"><a href="home.html?avatar=Melusine"><img class="imgCartes" src="img/Melusine.png"></a></div>
			<div class="col25 float"><a href="home.html?avatar=Quelaag"><img class="imgCartes" src="img/Quelaag.png"></a></div>
			<div class="col25"><a href="home.html?avatar=Salem"><img class="imgCartes" src="img/Salem.png"></a></div>
		</article>
	</section>
    <c:import url="./liste-partie.jsp"/>
</body>

<c:import url="./_JAVASCRIPTS.jsp"/>

</html>