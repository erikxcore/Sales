<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    if(request.getSession(true).getAttribute("isLoggedIn".toString()) == null){
    	session.setAttribute("isLoggedIn", false);
    }
    if((Boolean)request.getSession(true).getAttribute("isLoggedIn") == false){
            response.sendRedirect("admin.jsp");
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
	<script src="js/main.js"></script>
    <script src="js/admin.js"></script>

  </head>
  <body>
	<div class="navbar">
  	  <div class="navbar-inner">
		<div class="container-fluid">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<a class="brand" href="#" name="top">Salerizer</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li><span><i class="icon-home"></i> Admin Panel</span></li>
					<li class="divider-vertical"></li>
					<li><a href="index.html" target="_new"><i class="icon-file"></i>Main Site</a></li>
					<li class="divider-vertical"></li>
				</ul>
				<div class="btn-group pull-right">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i> admin	<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="jsp/logout.jsp"><i class="icon-share"></i> Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
	  </div>
	</div>
	
	<div class="container-fluid">
	 <div class="row">
		<div class="sidebar-nav span3">
    		<div class="well">
				<ul class="nav nav-list"> 
		  			<li class="nav-header">Admin Menu</li>        
		  			<li class="active"><a href="#" class="adminHome"><i class="icon-home"></i> Dashboard</a></li>
          			<li><a href="#" class="adminAllItems"><i class="icon-list"></i> All Items</a></li>
          			<li><a href="#" class="adminApprovedItems"><i class="icon-list"></i> Approved Items</a></li>
		  			<li><a href="#" class="adminView"><i class="icon-file"></i> View a specific Item by ID</a></li>
          			<li><a href="#" class="adminApprove"><i class="icon-thumbs-up"></i> Approve an Item By ID</a></li>
          			<li><a href="#" class="adminAdd"><i class="icon-plus-sign"></i> Add an Item</a></li>
          			<li class="divider"></li>
		  			<li><a href="jsp/logout.jsp"><i class="icon-share"></i> Logout</a></li>
				</ul>
			</div>
		</div>

		<div class="adminPanel span9">
			<div id="adminHome" class="page">
				<h1>Welcome.</h1>
					<p>This is the backbone of Salerizer. Well, not really - but you knew that. This is the front-end-of-the-back-end-but-only-a-little.
						From here instead of using the web service directly or through an interface (or through PHPMyAdmin, for completionist's sake.) you can add new items,
						view all items, view items that need approving, approve items, and view specific items - yes, deletion exists; no, you can't have access to that. Unless you
						want to sort the database.</p>
					<p>That said, stop sitting here and do something. Over there. To the <strong>left.</strong></p>
			</div>
			
			<div id="adminAllItems" class="page">
				<div class="results"></div>
				<div class="loader"></div>  
				<a href="#" class="allitems searchAgain">Click here to search again.</a>
			</div>
			<div id="adminApprovedItems" class="page">
					<div class="results"></div>
				    <div class="loader"></div>  
			</div>
			<div id="adminView" class="page">
			<h1>Search for a specific item.</h1>
			<form method="GET" id="searchItem">
				<div class="input-append searchItem">
                    <input type="number" name="viewItem" class="search-query" placeholder="Search">
                    <button type="submit" class="btn viewSearch"><i class="icon-search"></i></button>
                </div>
            </form>
            <div class="loader"></div>    
			<div class="results"></div>
			<a href="#" class="addnew searchAgain">Click here to search again.</a>
			</div>
			
			<div id="adminApprove" class="page">
			<h1>Approve a specific item.</h1>
			<form method="GET" id="searchItem">
				<div class="input-append searchItem">
                    <input type="number" name="approveItem" class="search-query" placeholder="Search">
                    <button type="submit" class="btn approveSearch"><i class="icon-search"></i></button>
                </div>
            </form>
             <div class="loader"></div>    
			 <div class="results"></div>
			 <a href="#" class="addnew searchAgain">Click here to search again.</a>
			</div>
			
			<div id="adminAdd" class="page">
			 	<div class="addsale">
    			<h2>Add a new sale</h2>
    			<form method="POST" class="additem">
    			<label>Title</label>
    			<input type="text" name="title" class="span3 addtitle" data-toggle="tooltip" placeholder="Dashing Oven Mitts" title="Please enter a title!">
    			<label>URL</label>
    			<input type="url" name="url" class="span3 addurl" data-toggle="tooltip" placeholder="www.NotYourMothersMitts.com/AsSeenOnTv" title="Please enter a valid URL.">
    			<label>Thumbnail URL</label>
    			<input type="url" name="thumb" class="span3 addthumb" data-toggle="tooltip" placeholder="www.NotYourMothersMitts.com/i/MittsThumb.png" title="Please enter a valid URL to an image.">
    			<label>Description</label>
    			<textarea name="desc" class="span3 adddesc" data-toggle="tooltip" placeholder="This Oven Mitts are just plain stunning. And diamond studded. Did we mention they also keep your hands cool while handling hot items? Wowza!" title="Please enter some kind of description!"></textarea>
    			<div class="clearfix"></div>
    			<input type="submit" value="Add Sale" class="btn btn-primary center	">
    			<div class="clearfix"></div>
   		 </form>
   		 </div>
   		 <div class="thanks">
   		 	<p>Thanks for submitting your sale!.</p>
   		 	<a href="#" class="addnew">Would you like to add another?</a>
   		 </div>
   		 <div class="opps">
   		 	<p>There was a problem submitting your sale. Please try again later.</p>
   		 	<a href="#" class="addnew">Click here to retry.</a>
   		 </div>
			</div>
				
		</div>
	</div>
</div>
</body>
 </html>