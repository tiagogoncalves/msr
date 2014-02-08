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
import com.ufba.iws.content.Actor;

public class ActorSPARQL {

	
	public List<Actor> listActor(String resource) throws IOException {
		
		List<Actor> actors = null;
		
        PropertyConfigurator.configure("jena-log4j.properties");
        String path = "queryActor";

        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String queryString = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
        queryString = queryString.replace("$resource$", resource);
        String service = "http://dbpedia.org/sparql";

        QueryExecution qe = QueryExecutionFactory.sparqlService(service,queryString);
        try {
        	actors = new ArrayList<Actor>();
            ResultSet results = qe.execSelect();
            for (; results.hasNext();) {

                QuerySolution sol = (QuerySolution) results.next();

                Actor actor = new Actor();
          
                actor.setName(sol.get("?actorName").toString());
                actor.setPhotoUri(sol.get("?photoUri").toString());
                actor.setBirthDate(sol.get("?birthDate").toString());
                actor.setBirthPlace(sol.get("?birthPlace").toString());
//            	actor.setStarringOf(new FilmSPARQL().listFilms());
                actors.add(actor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qe.close();
        }
		return actors;

	}

}