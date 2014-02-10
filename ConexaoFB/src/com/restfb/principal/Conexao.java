package com.restfb.principal;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class Conexao {

	public static void main(String[] args) {
		FacebookClient facebookCliente = new DefaultFacebookClient("CAACEdEose0cBAP5suo37ottZAl5yJWaVZB77mZCMu8x0Dk7ulAh7zbLHZBVASVur4UqmJqC15dHq4y8q1GxrkZCq6rcTGSYgZAmQXh5op5vTzkV7dpZBhED4lkq5deZCw9pOOc8Fuf7P5OTOXzmUlRiyySJmWJshFmcJkOZCS4knRHtikiZAOF3zdOiraKMAbnQepLS2LuTlHN1gZDZD");
	
		User me = facebookCliente.fetchObject("100003699137532",User.class);
		User friend = facebookCliente.fetchObject("amanda.carneiro.566",User.class);
		
		System.out.println(me.getName());

		System.out.println(friend.getFirstName());
	}
	
	
	
	
}
