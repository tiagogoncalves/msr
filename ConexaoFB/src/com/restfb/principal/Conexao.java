package com.restfb.principal;

import java.util.List;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

public class Conexao {

	public static void main(String[] args) {
		FacebookClient facebookCliente = new DefaultFacebookClient(
				"CAACEdEose0cBAK0yZBA0bcmfsIemkv9oWI2pmtOFvfiW2PnTRQEi2JVkNcZANjfZC0AbzgIqQonPVGLzS54xWAVU9OGji0S96Vy8yM22GsM0FuCo9VwRKRIr9VKDaS6N4F3IZCCrQ3t9x7FDsmQJ2sNWZCnCalkhS2gwBMDR0oZBhybaSQubtc4e98KGPLZA5RGy4NoWo67aAZDZD");

		User me = facebookCliente.fetchObject("100003699137532", User.class);

		String query = "SELECT uid,name, movies FROM user WHERE uid=220439 or uid=7901103";
		List<JsonObject> queryMovieResults = facebookCliente.executeQuery(
				query, JsonObject.class);
		for (int i = 0; i < queryMovieResults.size(); i++) {
			System.out.println(queryMovieResults.get(i).getString("movies"));

		}

		String query2 = "SELECT uid,name, interests FROM user WHERE uid=220439 or uid=7901103";
		List<JsonObject> queryInteressResults = facebookCliente.executeQuery(
				query2, JsonObject.class);
		for (int i = 0; i < queryInteressResults.size(); i++) {
			System.out.println(queryInteressResults.get(i).getString(
					"interests"));

		}

	}

}