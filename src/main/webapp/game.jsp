<%-- 
    Document   : game
    Created on : 17 juil. 2018, 15:01:50
    Author     : Administrateur
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <c:import url="./_STYLESHEETS.jsp"></c:import>

        </head>
        <body>
            <div id="PlateauDejeu"></div>
        <c:import url="./_JAVASCRIPTS.jsp"></c:import>
        <script type="text/javascript">
            
            function loadTheBoard(){
                $("#PlateauDejeu").load("in-game-players");
            }
            loadTheBoard(); 
            
              function showChanges(){
                  console.log("${joueur0.getPseudo()}");
                  console.log("${joueur0.getCartes().size()}");
                  console.log("${joueur0.getEtatJoueur().toString()}");
                  $.get("refresh-change", function(data){
                      if (data === "true"){
                          console.log(data);
//                          $("#PlateauDejeu").load("in-game-players");
                      } else {
                          console.log(data);
                      }
                  });
              }
              showChanges();
              setInterval(showChanges, 10000);

//            function refresh() {
//                $.get("refresh", function (changes){
//                    if (changes === "true"){
//                        $("#PlateauDejeu").load("in-game-players");
//                    }
//                });
//            }
//
//            function refreshJoueurALaMain() {
//                $.get("joueur-a-la-main", function (data) {
//                    if (data !== "true") {
//                        console.log("setInterval actif");
//                        setInterval(refresh, 500);
//                    } else {
//
//                    }
//                });
//            }
//
//            $(function () {
//                refresh();
//                refreshJoueurALaMain();
//                
//                setInterval(refreshJoueurALaMain, 500);
//            });

            
        </script>

    </body>
</html>
