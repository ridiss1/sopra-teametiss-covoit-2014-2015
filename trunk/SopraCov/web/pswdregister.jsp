<%-- 
    Document   : Clientconx
    Created on : 9 janv. 2015, 22:46:50
    Author     : Ridiss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    

    <head>

        <meta charset="utf-8">
        <title>SopraCov</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->
        <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
        <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Oleo+Script:400,700'>
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

    </head>

    <body>
        <img id="image" src="./images/Sopragroup.png" alt="" />
        <!--BEGIN #signup-form -->

        <div class="register-container container">
            <div class="row">
                <div class="iphone span5">
                    <img src="assets/img/iphone.png" alt="">
                </div>
                <div class="register span6">
                    <p>Modifiez votre mot de passe en vous assurant de ne pas redonner l'ancien.
						<%-- Vérification de la présence d'un objet utilisateur en session --%>
						<c:if test="${!empty sessionScope.Name}">
                        <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
                        <span> <ul id="menu">
                        
                                     <li>
                        
                            <a href="Clientconnecter.jsp">${sessionScope.Name}</a>
                            <ul>                                
                                <li><a  href="profil.jsp">Supprimer le compte</a></li>
                                <li><a href="<%=request.getContextPath()+"/DeconnxionServlet"%>">Deconnexion</a></li>
                            </ul>
                        </li>
                        
                                    </ul></span>
                        </c:if>					
                    </p>
                    <form method="POST" action="ProfileServlet">
            
                        <label>Ancien Mot de Passe </label>
                        <input type="password" name="Ancien"><br/>
						<label>Nouveau Mot de Passe </label>
                        <input type="password" name="New"><br/>						
						<a href="Clientconnecter.jsp">Annuler</a>
						<button >						
						<a href="<%=request.getContextPath()+"/ProfileServlet"%>">Valider</a>
						</button>                      
						
                    </form>

        
                </div>
            </div>
        </div>

        <!-- Javascript -->
        <script src="assets/js/jquery-1.8.2.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.backstretch.min.js"></script>
        <script src="assets/js/scripts.js"></script>

    </body>

</html>

