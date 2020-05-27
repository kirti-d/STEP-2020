

<!-- jsp file for comments -->
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
   String uploadUrl = blobstoreService.createUploadUrl("/addComment"); %>
<!DOCTYPE html>
<html>

<head>
	<!-- add css -->
	<link rel="stylesheet" href="style.css">
</head>

<body  onload="checkAuth()">
	<!-- comments and login -->
	<section class="mb50pxtop">
		<!-- choose no of comments you want to see -->
		<form>
			<input id="commentNo" value="5" type="number" min="0" max="100000"></input>
		</form>
		<button onclick="fetchMessage()">change</button>
		<!-- choose no of comments you want to see -->


		<div>
			<!-- add comments form -->
			<form id="addComment" class="hide" method="post" enctype="multipart/form-data" action="<%= uploadUrl %>">
				<input name="comment"></input>
                <input name="image" type="file"></input>
				<button class="next" type="submit" >add</button>
			</form>
			<!-- add comments form -->
			<!-- logout form-->
			<form id="logout" class="hide" method="post" action="/logout">
				<button class="next" type="submit" >logout</button>
			</form>
			<!-- logout form-->
			<!-- login form-->
			<form id="authenticate" class="hide" method="post" action="/authenticate">
				<button class="next" type="submit" >authenticate</button>
			</form>
			<!-- login form-->
		</div>

		<!-- show comments -->
		<ul id="msg"></ul>

	</section>
	<!-- comments and login -->

	<script src="fetchMessage.js"></script>
</body>

</html>