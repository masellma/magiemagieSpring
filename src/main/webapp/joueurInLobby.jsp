
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:forEach items="${joueurs}" var="joueur">
        <div class="item">
            <img src="img/${joueur.getAvatar()}.png">
            <p>${joueur.getPseudo()}</p>
        </div>
</c:forEach>