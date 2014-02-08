package com.ufba.iws.principal;
import java.io.IOException;

import com.ufba.iws.content.Actor;
import com.ufba.iws.content.Film;
import com.ufba.iws.sparql.FilmSPARQL;


public class Principal {
public static void main(String[] args) {
	FilmSPARQL fs = new FilmSPARQL();
	try {
		System.out.println("Filmes");
		for (Film film : fs.listFilms()) {
			System.out.println("Label: "+film.getFilmLabel());
			System.out.println("Descrição: "+film.getDescription());
			System.out.println("Diretor: "+film.getDirector());
			System.out.println("Country: "+film.getCountry());
			System.out.println("Atores:");
			for (Actor actor : film.getActors()) {
				System.out.println("Name: "+actor.getName());
				System.out.println("Data: "+actor.getBirthDate());
				System.out.println("Lugar de nascimento: "+actor.getBirthPlace());
				System.out.println("Álbum: "+actor.getPhotoUri());
			}
		}
		System.out.println("");
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}
