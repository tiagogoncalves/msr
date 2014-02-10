package com.ufba.iws.sparql;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.PropertyConfigurator;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;

public class SPARQL {
	
	public static final String pathDBMDBSPARQL = "queries/LinkedmdbSPARQL";
	public static final String pathDBPediaSPARQL = "queries/DBpediaSPARQL";
	public static final String pathDestinyDBPedia = "target/RDFDBPedia";
	public static final String pathDestinyDBMDB = "target/RDFLinkedmdb";
	public static final String serviceDBMDB = "http://data.linkedmdb.org/sparql";
	public static final String serviceDBpedia = "http://dbpedia.org/sparql";

	public static void writedmdbRDF(){
        PropertyConfigurator.configure("jena-log4j.properties");
        byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(pathDBMDBSPARQL));
			String queryString = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
	        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceDBMDB,queryString);
	        Model model = qe.execConstruct();
        	FileWriter fw = new FileWriter(new File(pathDestinyDBMDB));
        	
        	model.write(fw, "RDF/XML-ABBREV");
        	qe.close();
		} catch (IOException e1) {
			e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
	}
	
	public static void writeDBPediaRDF(){
        PropertyConfigurator.configure("jena-log4j.properties");
        byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(pathDBPediaSPARQL));
			String queryString = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();

	        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceDBpedia,queryString);
	        Model model = qe.execConstruct();
        	FileWriter fw = new FileWriter(new File(pathDestinyDBPedia));
        	
        	model.write(fw, "RDF/XML-ABBREV");
        	qe.close();
		} catch (IOException e1) {
			e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
	}
}
