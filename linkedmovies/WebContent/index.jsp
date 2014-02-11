<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link rel="stylesheet" type="text/css" href="stylesheets/fonts.css" />
<link rel="stylesheet" type="text/css" href="stylesheets/msr_style.css" />
<link rel="stylesheet" type="text/css"
	href="stylesheets/maximage.min.css" />
<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript" src="//connect.facebook.net/en_UK/all.js"></script>
<script type="text/javascript" src="scripts/fb_connect.js"></script>
<script type="text/javascript" src="scripts/jquery.cycle.all.min.js"></script>
<script type="text/javascript" src="scripts/jquery.maximage.min.js"></script>
<title>Linked Movies - MSR</title>
</head>

<body>

	<div class='site-bg'>
		<img src='images/bg1.jpg' width="1200" height="800" /> <img
			src='images/bg2.jpg' width="1200" height="800" /> <img
			src='images/bg3.jpg' width="1200" height="800" /> <img
			src='images/bg4.jpg' width="1200" height="800" />
	</div>

	<div id="content">
		<h1>Linked Movies</h1>
		<p class='page-info'>Analisando o seu perfil, recomendaremos novos
			filmes. Acredite, boas ligações podem levar a incríveis
			recomendações. Tente agora!</p>
		<div class='fb-login'></div>
	</div>

	<div id='linkedmovies'>
		<div class='main'></div>
	</div>

	<div id="fb-root"></div>
</body>

</html>