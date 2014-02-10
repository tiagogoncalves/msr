$(document).ready(function(){

	$(function(){
		jQuery('.site-bg').maximage({
			cycleOptions: 
			{
				speed:1000,
	            timeout: 8000
			}
		});
	});

	FB.init({
		appId      : '1377026162566167', // linkedmovies app id
		secret	   : 'ec4093a8d6daed0b38c34a83fa4d9a30', // linkedmovies secret
		status     : true, // check login status
		cookie     : true, // enable cookies to allow the server to access the session
		xfbml      : true  // parse XFBML
	});

	//Isto fica escutando todas as mudanças na conexão com facebook
	FB.Event.subscribe('auth.authResponseChange', function(response) 
	{
		//SUCCESS
		//Assim que conectado a magia acontece no html
	 	if (response.status === 'connected')
	 	{
	 		setTimeout(function(){
	 			$("#content").fadeOut(200);
	 			$("#linkedmovies").show();

	 			//Obter dados do facebook
	 			fbGetData();
	 		},500);
	 	} 
	  	
	  	//FAILED	 
		else if (response.status === 'not_authorized') alert("Falha na conexão ao Facebook");

		//UNKNOWN ERROR
	    else alert("Deslogado");
	});

	//Se não tiver logado, clicar no botão pra logar disparará este evento
	$(".fb-login").click(function(){
		fbLogin();
	});

});

function fbLogin()
{
	FB.login(function(response) 
	{
		if (!response.authResponse)
			console.log('Falha no login');

	 //scope são as propriedades que usuário vai permitir o app pegar no perfil do cara	
	 },{scope: 'email,user_photos,user_likes,friends_likes'});
}

//Obter dados do facebook
function fbGetData()
{
	//Da pra usar essa implementação básica do Face pra pegar dados ou usar Facebook Query Language
	//Para mais em user_fields ver https://developers.facebook.com/docs/graph-api/reference/user/
	var user_fields = 'id,name,first_name,last_name,gender,picture.type(large),age_range,movies,likes';
	FB.api('/me?fields='+user_fields,function(response) 
	{	
		//Visualizar dados no console
		console.log(response);
		console.log(JSON.stringify(response));

		//Gerar Html dos dados obtidos
		fbShowUserInfo(response);
	});
	
}

function fbShowUserInfo(fb_data)
{

	var html = "<div class='user-info'><img src='"+fb_data.picture.data.url+"'/><h1>"+fb_data.first_name+" "+fb_data.last_name+"</h1></div>";

	//Em caso do usuário não tiver filmes
	if(fb_data.movies)
	{
		html+= "<div class='user-movies'><p>Esses são os filmes que você curtiu<br><br>";
		var movies = fb_data.movies.data;
		for (var i = 0; i < movies.length; i++) 
		{
			if(i == movies.length) html += movies[i].name;
			else html += movies[i].name+", ";
		}
		html+= "</p></div>";
	}

	else html+= "<div class='user-movies'><p>Você não curtiu filmes</p></div>";
	
	html+="<div class='movie-recom'><div class='loader'><p>Ligando Filmes</p><div></div></div>";
	$(".main").append(html);
	
	//TODO Ajax to Java Server
}