<%-- 
    Document   : result
    Created on : 10 janv. 2015, 10:49:38
    Author     : Jean
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
                    <form method="POST" action="ProfileServlet">
            
                       <div>
							<table id='results'>
								<thead>
									<tr id='header_row'>
										<th>Domicile</th><th/><th/></th><th/><th/>
										<th>Lieu de travail</th><th/><th/></th><th/><th/>
										<th>Conducteur</th><th/><th/></th><th/><th/>
										<th>Numero</th><th/><th/></th><th/><th/>
                                                                                <th>Jour</th><th/><th/></th><th/><th/>
										<th>Details</th><th/><th/></th><th/><th/>
									</tr>
								</thead>

								<tbody id='row_1'> ${trajets} </tbody>

							</table>
						</div>
						<p><br/>
							<a href="client.html">Retour</a><span class="fontawesome-arrow-right"></span>
						</p> 
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
