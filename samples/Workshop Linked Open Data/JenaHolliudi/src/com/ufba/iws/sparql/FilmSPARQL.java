package com.ufba.iws.sparql;
import java.io.File;
import java.io.FileWriter;
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
import com.hp.hpl.jena.rdf.model.Model;
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
        //QueryExecution qe = Querr
       // System.out.println("okk");
        try {
        	films = new ArrayList<Film>();
        	//ResultSet results = qe.execSelect();  
       // 	Query query = QueryFactory.create(queryString);
//        	        	
  //      	ResultSetFormatter.out(System.out, results, query); 
        	
    //    	QueryExecution qe = QueryExecutionFactory.create(query, oracleSemModel) ;
        	Model model = qe.execConstruct();
        	
        	//Model model = qe.execConstruct();
        	
        	//Model model = ModelFactory.createDefaultModel();
        	
        	//model.read("rdfLinked");
        	
        	FileWriter fw = new FileWriter(new File("rdfLinkedQ"));
        	
        	model.write(fw, "RDF/XML-ABBREV");
//        	ResIterator it = model.listSubjects();
//        	
//        	while(it.hasNext()){
//        		
//        		Resource r = it.nextResource();
//        		
//        		System.out.println(r.getURI());
//        		
//        		Statement stmt2 = r.getRequiredProperty(model.getProperty("http://www.w3.org/2000/01/rdf-schema#label"));
//        		
//        		System.out.println(stmt2.getString());        				
//        		
//        		       		
//
////        		
//        	}
//        	
//        	Resource r = it.next();
//        	
//        	StmtIterator sIt = r.listProperties();
//        	
//        	while(sIt.hasNext()){
//        		
//        		Statement stm = sIt.next();
//        		
//        		System.out.println(stm.toString());
//        	}
        	
        	//System.out.println(r.getProperty(model.getProperty("movie:writer")).toString());
        	
//        	for ( ; it.hasNext() ; ){
//        		
//        		Resource r = it.nextResource();        		
//        		
//        		StmtIterator sit = r.listProperties();
//        		
//        		while(sit.hasNext()){
//        			
//        			Statement stmt = sit.next();
//        			Resource res2 = stmt.getSubject();        			
//        	
//        	        System.out.print(res2.getNameSpace(  ) + res2.getLocalName(  ));
//        	        
//        	        // Get predicate, print
//        	        Property prop = stmt.getPredicate(  );
//        	        System.out.print(" " + prop.getNameSpace(  ) + prop.getLocalName(  ));
//
//        	        // Get object, print
//        	        RDFNode node = stmt.getObject(  );
//        	        System.out.println(" " + node.toString(  ) + "\n");
//        			
//        		}
//        		
//        		
//        		
//        		//System.out.println(r.getProperty(model.getProperty("rdfs:label")));
//        		
//        		System.out.println();
        		
        	//}
        	
        	
//        	Model results2 = qe.execConstruct();
//        	results2.write(System.out, "TURTLE");
            
            
            
            //for (; results.hasNext();) {

                //QuerySolution sol = (QuerySolution) results.next();
                

//                Film film = new Film();
//                film.setFilmURL(sol.get("?url").toString());
//                film.setSequel(sol.get("?sequel").toString());
//                film.setSequel(sol.get("?sequel").toString());
//                film.setFilmLabel(sol.get("?filmLabel").toString());
//                film.setDirector(sol.get("?directorName").toString());
//                film.setCountry(sol.get("?country").toString());
                
                
//                film.setActors(new ActorSPARQL().listActor(sol.get("?filmLabel").toString()));
//                film.setDescription(sol.get("?description").toString());
                

               // System.out.println(  sol.get("?film").toString());

                
                
               // RDFNode prequel =sol.get("?prequel");
             //   RDFNode sequel =sol.get("?sequel");
                
//				System.out.println(sol.get("?film"));
//				if(prequel!=null)
//					System.out.println("| prequel:" + prequel.toString());
//				if(sequel!=null)
//					System.out.println("| sequel:" + sequel.toString());
				
               // films.add(film);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // qe.close();
        }
		return films;

	}

}