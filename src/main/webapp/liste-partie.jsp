<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section id="third">
        <header>
            <img src="img/topnav.png">
        </header>
        <h2>Créer une partie</h2>
        <form method="POST" id="createGame" action="./creer-partie<c:if test="${user == null}">-unlogged</c:if>">
            <label>Nom de la partie</label>
            <input type="text" name="partieNom" placeholder="Jojo's bizarre adventure">
            <button class="btn1" type="submit">Créer</button>
        </form>
        <h2>Liste de partie en attente :</h2>
        <div id="inactiveGame">
            <c:forEach items="${parties}" var="partie">
            <div class="inactiveGame float">
                <h4>${partie.getNom()}</h4>
                <h6>Joueurs:</h6>
                <ul>
                    <c:forEach items="${partie.getJoueurs()}" var="joueur">
                        <li>${joueur.getPseudo()}</li>
                    </c:forEach>
                </ul>
                <span class="join"><a style="color:goldenrod" href="./join<c:if test="${user == null}">-unlogged</c:if>?idPartie=${partie.getId()}">Rejoindre</a></span>
            </div>
            </c:forEach>
        </div>
    </section>