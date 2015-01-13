<%-- 
    Document   : conxion
    Created on : 10 janv. 2015, 07:49:11
    Author     : Ridiss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">

<head>
  <title>SopraCov</title>
    <meta property="og:image" itemprop="image primaryImageOfPage" content="/images/Sopragroup.png" />
    <link rel="stylesheet" href="css/style.css" media="screen" type="text/css" />
    <meta charset="utf-8">

    <title>Bienvenue dans SopraCov</title>
	
	<body style="background-image:url(./images/1.png)" "background-repeat:no-repeat" >

    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,700">
</head>

<body>
	
	<img id="signup-icon" src="./images/Sopragroup.png" alt="" />
	<br /><br /><h1>Bienvenue dans SOPRACOV</h1>
	
	<div class="container">

      <div id="login">

        <!--<form action="javascript:void(0);" method="get"> -->
        <form method="POST" action="LogServlet">
            
          
          <fieldset class="clearfix">

            <p><span class="fontawesome-user"></span><input type="text" name="Mail" value="Adresse mail" onBlur="if(this.value == '') this.value = 'Adresse mail'" onFocus="if(this.value == 'Adresse mail') this.value = ''" required></p> <!-- JS because of IE support; better: placeholder="Username" -->
            <p><span class="fontawesome-lock"></span><input type="password"  name="Passe" value="Password" onBlur="if(this.value == '') this.value = 'Password'" onFocus="if(this.value == 'Password') this.value = ''" required></p> <!-- JS because of IE support; better: placeholder="Password" -->
                
                
            <p><input type="submit" value="Se connecter"></p>

          </fieldset>

        </form>

        <p>Pas un membre ? <a href="register.html">Creer compte</a><span class="fontawesome-arrow-right"></span></p>

      </div> 

    </div>

</body>

</html>>
