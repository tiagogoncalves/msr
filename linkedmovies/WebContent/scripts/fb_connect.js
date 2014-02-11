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
	
	fb_login = false;
	//Isto fica escutando todas as mudanças na conexão com facebook
	FB.Event.subscribe('auth.authResponseChange', function(response) 
	{
		//SUCCESS
		//Assim que conectado a magia acontece no html
	 	if (response.status === 'connected' && !fb_login)
	 	{
	 		fb_login = true;
	 		fbLogin();
	 	} 
	  	
	  	//FAILED	 
		else if (response.status === 'not_authorized') console.log("Falha na conex�o ao Facebook");
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
		else
		{
 			$("#content").fadeOut(200);
 			$("#linkedmovies").fadeIn(300);
	 		setTimeout(function(){
	 			//Obter dados do facebook
	 			fbGetData();
	 		},300);			
		}

	 //scope são as propriedades que usuário vai permitir o app pegar no perfil do cara	
	 },{scope: 'user_photos,user_likes,friends_likes,user_birthday,user_religion_politics,user_subscriptions'});
}

//Obter dados do facebook
function fbGetData()
{
	//Da pra usar essa implementação básica do Face pra pegar dados ou usar Facebook Query Language
	//Para mais em user_fields ver https://developers.facebook.com/docs/graph-api/reference/user/
	var user_fields = 'birthday,first_name,last_name,gender,picture.type(large),movies,likes.limit(5),hometown,subscribedto.limit(5),religion';
	FB.api('/me?fields='+user_fields,function(response) 
	{	
		//Visualizar dados no console
		console.log(response);
		console.log(JSON.stringify(response));

		//Gerar Html dos dados obtidos
		fbShowUserInfo(response);
	});
	
/*
 * Modelo pra usar FQL
 * FB.api({
    method: 'fql.query',
    query: query
}, function(response){
    console.log(response);
});
 */	
	
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

	else html+= "<div class='user-movies'><p>Voc� n�o curtiu filmes</p></div>";
	
	html+="<div class='movie-recom'><div class='loader'><p>Ligando Filmes</p><div></div></div>";
	$(".main").append(html);
	
	profile_json = JSON.stringify(fb_data);
    $.ajax({
        type: 'POST',
        dataType: "html",
        url: 'http://localhost:8080/linkedmovies/RestService',
        data:{profile:profile_json},
        async: false,
        success: function (data) {
        	console.log(data);
        },
        error: function () {
            console.log("Ocorreu um erro");
        }
    });

	
}