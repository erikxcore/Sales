<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <% 
    if(request.getSession(true).getAttribute("isLoggedIn".toString()) == null){
    	session.setAttribute("isLoggedIn", false);
    }
    if((Boolean)request.getSession(true).getAttribute("isLoggedIn") == true)
     {
   	 response.sendRedirect("adminpanel.jsp");
     }
    %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Salerizer</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="This demo web application is an example of a Java powered REST Web Service built on top of Apache Tomcat and MySQL.">
    <meta name="author" content="Eric Barber">
    
	<link href='http://fonts.googleapis.com/css?family=Damion' rel='stylesheet' type='text/css'>
    <link href="/Sale/css/bootstrap.css" rel="stylesheet">
    <link href="/Sale/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="/Sale/css/style.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="/js/html5shiv.js"></script>
    <![endif]-->
    
    <script src="js/jquery-1.10.2.min.js"></script>
    <script src="js/bootstrap.min.js"></script>    
  </head>

  <body>

    <div class="container-narrow">
    <div class="row">
			<form class="form-horizontal login" action='jsp/login.jsp' method="POST">
			  <fieldset>
			    <div id="legend">
			      <legend class="">Login to manage Salerizer</legend>
			    </div>
			    <div class="control-group">
			      <!-- Username -->
			      <label class="control-label"  for="username">Username</label>
			      <div class="controls">
			        <input type="email" id="username" name="username" title="Please enter a valid email to login with." placeholder="admin@salerizer.com" class="input-xlarge" required>
			      </div>
			    </div>
			    <div class="control-group">
			      <!-- Password-->
			      <label class="control-label" for="password">Password</label>
			      <div class="controls">
			        <input type="password" id="password" name="password" title="Please enter your password." placeholder="password" class="input-xlarge" required>
			      </div>
			    </div>
			    <div class="control-group">
			      <!-- Button -->
			      <div class="controls">
			        <button class="btn btn-success">Login</button>
			      </div>
			      <%
			      if(request.getSession(true).getAttribute("loginFail".toString()) == null){
			    	  session.setAttribute("loginFail",false);
			      }else{
			      	if(Boolean.valueOf(request.getSession(true).getAttribute("loginFail").toString()) == true){ 
			      %>
			      <div class="message">
			      <p class="error">There has been an error logging you in. Please check your user name and password and try to login again.</p>
			      </div>
			      <%
			        session.removeAttribute("loginFail");
			      	}
			      }
			      %>
			     <span class="goBack"><a href="index.html">Back to main site.</a></span>
			    </div>
			  </fieldset>
			</form>
	</div>
</div>
