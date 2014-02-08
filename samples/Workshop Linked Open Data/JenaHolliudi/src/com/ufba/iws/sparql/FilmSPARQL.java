package com.ufba.iws.sparql;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.ufba.iws.content.Film;

public class FilmSPARQL {

	
	public List<Film> listFilms() throws IOException {
		
		List<Film> films = null;
		
        PropertyConfigurator.configure("jena-log4j.properties");
        String path = "query";

        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String queryString = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();

//        String service = "http://dbpedia.org/sparql";
        String service = "http://data.linkedmdb.org/sparql";

        QueryExecution qe = QueryExecutionFactory.sparqlService(service,queryString);
        try {
        	films = new ArrayList<Film>();
            ResultSet results = qe.execSelect();
            for (; results.hasNext();) {

                QuerySolution sol = (QuerySolution) results.next();

                Film film = new Film();
                film.setFilmLabel(sol.get("?filmLabel").toString());
                film.setDirector(sol.get("?directorName").toString());
                film.setCountry(sol.get("?country").toString());
                
                
                film.setActors(new ActorSPARQL().listActor(sol.get("?filmLabel").toString()));
                film.setDescription(sol.get("?description").toString());
                
                films.add(film);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qe.close();
        }
		return films;

	}

}