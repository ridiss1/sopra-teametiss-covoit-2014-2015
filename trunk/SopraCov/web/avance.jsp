<%-- 
    Document   : avance.jsp
    Created on : 11 janv. 2015, 10:23:08
    Author     : Ridiss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

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
		<!---------- CSS ------------>
	<link rel="stylesheet" type="text/css" href="./css2/style2.css">
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
                     <%-- Vérification de la présence d'un objet utilisateur en session --%>
                    <c:if test="${!empty sessionScope.Name}">
                        <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
                        <span> <ul id="menu">
                        
                                     <li>
                        
                            <a href="Clientconnecter.jsp">${sessionScope.Name}</a>
                            <ul>
                                <li><a  href="pswdregister.html">Modifier mot de passe</a></li>
                                <li><a  href="pswdregister.html">Supprimer le compte</a></li>
                                <li><a  href="pswdregister.html">MAsJ profil</a></li>
                                <li><a href="<%=request.getContextPath()+"/DeconnxionServlet"%>">Deconnexion</a></li>
                            </ul>
                        </li>
                        
                                    </ul></span>
                        </c:if>
                <form method="POST" action="AvanceServlet">
                    
            <label><h4>Nom Conducteur: Prénom et Nom</h4> </label> 
            <input type="text" name="Nom"><br/>            
            <label><h4>Commune </h4></label>
            <input type="text" name="Commune"><br/>
            <label><h4>Code Postal </h4></label>
            <input type="text" name="CodePostal"><br/>
            <label><h4>Lieu de travail </h4></label>
            <SELECT name="Workplace">
                <OPTION VALUE="BLAGNAC">Blagnac</OPTION>
                <OPTION VALUE="COLOMIERS">Colomiers</OPTION>           
            </SELECT>
            <br/>
            <button id="submit" type="submit">Rechercher</button>
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

