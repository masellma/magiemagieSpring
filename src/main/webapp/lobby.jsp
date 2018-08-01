<%-- 
    Document   : lobby
    Created on : 16 juil. 2018, 12:30:43
    Author     : Administrateur
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>JSP Page</title>
    <c:import url="./_STYLESHEETS.jsp"/>
    <c:import url="./_JAVASCRIPTS.jsp"/>
    </head>
    <body>
        <div class="" id="container-lobby">
        	<div id="lobby"></div>
                <div style="background-color: rgba(51,51,51,0.5); width: 100%; text-align: center; padding: 15px 0; ">
                    <c:if test="${user.getOrdre() == 1}"><a href="./loading?idPartie=${idPartie}" class="btn btn-primary">Lancer la partie</a></c:if>
                    <c:if test="${user.getOrdre() != 1}"><span class="btn btn-secondary" style="cursor: auto;">En attente de lancement</span></c:if>
                </div>
        	<form>
        		<div>
        			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
        			tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
        			quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        			consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
        			cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
        			proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
        			tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
        			quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        			consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
        			cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
        			proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
        			tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
        			quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        			consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
        			cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
        			proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
        		</div>
	        	<div class="input-group mb-3">
				  <input type="text" class="form-control" placeholder="Send message" aria-label="Recipient's username" aria-describedby="button-addon2">
				  <div class="input-group-append">
				    <button class="btn btn-danger" type="button" id="button-addon2">Button</button>
				  </div>
				</div>
        	</form>
        </div>
        <script type="text/javascript">
            function refresh_Joueur() {
//                
                $.ajax({
                    url : "joueur-in-lobby", success : function (responseText){
                        if(responseText.includes("true") && responseText.length < 10){
                            document.location = 'game?idPartie=${idPartie}';
                        } else {
                            $("#lobby").load("joueur-in-lobby");
                        }
                        console.log(responseText);
                }
            });
            }
            
            refresh_Joueur();
            setInterval(refresh_Joueur, 1000);
        </script>
    </body>
</html>
