package br.ufba.matac93.msr;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;
import br.ufba.matac93.msr.utils.OntologyMethods;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class Main2Teste {

	private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
	private static final String namSpaceLinkedMD = "http://www.semanticweb.org/matc93/ontology/linkedmdb_lite";
	private static final String nameSpaceFao = "http://www.semanticweb.org/matc93/ontology/fao";
	private static final String nameSpaceMovie = "http://data.linkedmdb.org/resource/movie/";
	private static final String dbPediaProperty = "http://dbpedia.org/property/";
	private static final String ns = "http://dbpedia.org/matc93/ontology/dbpedia_lite";

	public static void main(String[] args) throws OWLOntologyCreationException {

		OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager();

		manager2.loadOntologyFromOntologyDocument(new File("fao.owl"));
		manager2.loadOntologyFromOntologyDocument(new File("linkedmdb_lite.owl"));

		OWLOntology ontology2 = manager2
				.loadOntologyFromOntologyDocument(new File("dbpedia_lite_final.owl"));
		OWLReasonerFactory reasonerFactory2 = PelletReasonerFactory.getInstance();

		OWLDataFactory factory2 = manager2.getOWLDataFactory();

		Model model = ModelFactory.createDefaultModel();

//		model.read("rdfLinkedQY");
		model.read("RDFLinkedmdb4");
		
		

		ResIterator it = model.listSubjects();

		OWLIndividual personTeste = OntologyMethods.addIndividual(nameSpaceFao,
				"#Person", "Peter", manager2, ontology2);
		
		OWLIndividual personTeste2 = OntologyMethods.addIndividual(nameSpaceFao,
				"#Person", "Angela", manager2, ontology2);
		
		OWLIndividual actorTeste = OntologyMethods.addIndividual(nameSpaceFao, "#Actor", "http://data.linkedmdb.org/resource/actor/29685", 
				manager2, ontology2);
		
		OntologyMethods.addIndividualOnObjProperty(nameSpaceFao, personTeste, actorTeste, manager2, ontology2, "#likes");
		OntologyMethods.addIndividualOnObjProperty(nameSpaceFao, personTeste, personTeste2, manager2, ontology2, "#relationship");
		
		System.out.println(personTeste.toString());

		int i = 0;

		while (it.hasNext()) {

			i++;

			Resource r = it.nextResource();

			OWLIndividual individual = OntologyMethods.addIndividual(ns,
					"Film", r.getURI(), manager2, ontology2);

//			if (i == 1) {
//				OntologyMethods.addIndividualOnObjProperty(ns, personTeste,
//						r.getURI(), manager2, ontology2, "#watch");
//			}

			if(r.toString().contains("http://data.linkedmdb.org/resource/film/")){
				Statement stmt2 = r.getRequiredProperty(model.getProperty("http://www.w3.org/2000/01/rdf-schema#label"));
				OntologyMethods.addDataProperty(ns, "label", stmt2.getString(),individual, manager2, ontology2);
			}

			
			// /pegando os generos
			Resource r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie + "genre"));
			
			
			StmtIterator its = r.listProperties(model.getProperty(nameSpaceMovie + "genre"));
			
			if (its != null) {			
								
				Statement stmV ;
				for(; its.hasNext();   ){
					stmV = its.next();		
					OntologyMethods.addIndividualOnObjProperty(ns, individual,stmV.getResource().getURI(), manager2, ontology2, "genre");
				}
				
				
			}

			// pegando os atores					

			r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie+ "actor"));
			
			its = r.listProperties(model.getProperty(nameSpaceMovie + "actor"));
			
			if (its != null) {				
				Statement stmV ;
				for(; its.hasNext();   ){
					stmV = its.next();
					
					Property p = model.getProperty(nameSpaceMovie+"actor_name");
					
					OWLIndividual actorT = OntologyMethods.addIndividual(nameSpaceFao,"#Actor", stmV.getResource().getURI(), manager2, ontology2);
					
					//OntologyMethods.addIndividualOnObjProperty(ns, individual,
					//		stmV.getResource().getURI(), manager2, ontology2, "genre");
					OntologyMethods.addIndividualOnObjProperty(ns, individual, actorT, manager2, ontology2, "starring");
					OntologyMethods.addDataProperty(ns, "actor_name", stmV.getResource().getProperty(p).getObject().toString(),actorT, manager2, ontology2);
				}		
				
			}
			


			// pegando as linguas

			r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie
					+ "language"));
			
			its = r.listProperties(model.getProperty(nameSpaceMovie + "language"));
			
			if (its != null) {				
				Statement stmV ;
				for(; its.hasNext();   ){
					stmV = its.next();
					
					OntologyMethods.addDataProperty(dbPediaProperty, "language",
							stmV.getResource().getURI(), individual, manager2, ontology2);
					
				}		
				
			}
	
			// pegando os escritores

//			r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie
//					+ "writer"));
			
			its = r.listProperties(model.getProperty(nameSpaceMovie + "writer"));
			
			if (its != null) {				
				Statement stmV ;
				for(; its.hasNext();   ){
					stmV = its.next();
					
					OWLIndividual writerT = OntologyMethods.addIndividual(nameSpaceFao,"#Writer", stmV.getResource().getURI(), manager2, ontology2);
					Property p = model.getProperty(nameSpaceMovie+"writer_name");
					System.out.println("writer:"+stmV.getResource().getProperty(p).getObject().toString());
					OntologyMethods.addIndividualOnObjProperty(ns, individual, writerT, manager2, ontology2, "writer");
					OntologyMethods.addDataProperty(ns, "writer_name", stmV.getResource().getProperty(p).getObject().toString(),writerT, manager2, ontology2);
				
				}		
				
			}

			// pegando os diretores

			r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie+ "director"));
			
			its = r.listProperties(model.getProperty(nameSpaceMovie + "director"));
			
			if (its != null) {				
				Statement stmV ;
				for(; its.hasNext();   ){
					stmV = its.next();
					
					OWLIndividual directorT = OntologyMethods.addIndividual(nameSpaceFao,"#Director", stmV.getResource().getURI(), manager2, ontology2);
					Property p = model.getProperty(nameSpaceMovie+"director_name");
					System.out.println("director:"+stmV.getResource().getProperty(p).getObject().toString());
					
					OntologyMethods.addIndividualOnObjProperty(ns, individual, directorT, manager2, ontology2, "director");
					OntologyMethods.addDataProperty(ns, "director_name", stmV.getResource().getProperty(p).getObject().toString(),directorT, manager2, ontology2);
				
				}		
				
			}
			

			// filmes prequel de outro filme

			r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie
					+ "prequel"));
			if (r2 != null) {
				
//				OWLIndividual prequelT = OntologyMethods.addIndividual(ns,
//						"Film", r2.getURI(), manager2, ontology2);
//				System.out.println(writerT.toString());
				
				//OntologyMethods.addIndividualOnObjProperty(ns, individual, writerT, manager2, ontology2, "writer");
				
								
				OntologyMethods.addIndividualOnObjProperty(namSpaceLinkedMD,
						individual, r2.getURI(), manager2, ontology2,
						"#prequel");
			}

			// filmes sequel de outro filme

			r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie+ "sequel"));
			if (r2 != null) {

				OntologyMethods
						.addIndividualOnObjProperty(namSpaceLinkedMD,
								individual, r2.getURI(), manager2, ontology2,"#sequel");
			}

		}

		

		OWLReasoner reasoner2 = reasonerFactory2.createReasoner(ontology2,
				new SimpleConfiguration());

		PrefixOWLOntologyFormat pm2 = (PrefixOWLOntologyFormat) manager2
				.getOntologyFormat(ontology2);

		pm2.setDefaultPrefix(ns + "#");
		
		OWLNamedIndividual filmeLab = searchMovieWithLabel("Star Trek III: The Search for Spock",ontology2, manager2, factory2, pm2, reasoner2);
		OWLNamedIndividual actorLab = searchActorWithLabel("Antonio Banderas",ontology2, manager2, factory2, pm2, reasoner2);
		OWLNamedIndividual directorLab = searchDirectorWithLabel("Jay Levey",ontology2, manager2, factory2, pm2, reasoner2);
		OWLNamedIndividual writerLab = searchWriterWithLabel("William Shatner",ontology2, manager2, factory2, pm2, reasoner2);
		
		
		
		

		if (filmeLab != null) {
			System.out.println("Voltou");
			
		   
			
//			OWLObjectProperty propConhece2 = factory2.getOWLObjectProperty("<" + ns
//					+ "genre>", pm2);
//
//			 for (OWLNamedIndividual ind2 : reasoner2.getObjectPropertyValues(
//						filmeLab, propConhece2).getFlattened()) {
//			 System.out.println("Teste lang: " + renderer.render(ind2));
//			 }
			
			OntologyMethods.addIndividualOnObjProperty(ns, personTeste,filmeLab, manager2, ontology2, "#watch");
			OntologyMethods.addIndividualOnObjProperty(ns, personTeste,actorLab, manager2, ontology2, "#likes");
			OntologyMethods.addIndividualOnObjProperty(ns, personTeste,directorLab, manager2, ontology2, "#likes");
			OntologyMethods.addIndividualOnObjProperty(ns, personTeste,writerLab, manager2, ontology2, "#likes");
			

		}
		
		OWLNamedIndividual filmeLab2 = searchMovieWithLabel("Anacondas: The Hunt for the Blood Orchid",
				ontology2, manager2, factory2, pm2, reasoner2);
		
		if (filmeLab2 != null) {
			System.out.println("Voltou2");
			
			OntologyMethods.addIndividualOnObjProperty(ns, personTeste,
					filmeLab2, manager2, ontology2, "#watch");

		}
		
//		filmeLab2 = searchMovieWithLabel("Free Willy 2: The Adventure Home",
//				ontology2, manager2, factory2, pm2, reasoner2);
//		
//		if (filmeLab2 != null) {
//			System.out.println("Voltou3");
//			
//			OntologyMethods.addIndividualOnObjProperty(ns, personTeste2,
//					filmeLab2, manager2, ontology2, "#watch");
//		}
		
		reasoner2 = reasonerFactory2.createReasoner(ontology2,
				new SimpleConfiguration());
		
		pm2 = (PrefixOWLOntologyFormat) manager2
				.getOntologyFormat(ontology2);

		pm2.setDefaultPrefix(ns + "#");
		

		String strNs = "<" + ns;

		OWLClass personClass2 = factory2.getOWLClass("<" + nameSpaceFao	+ "#Person>", pm2);

		// OWLDataProperty dataPrp =
		// reasoner2.getInstances(factory2.getOWLDataProperty(strNs+"#label",
		// pm2),false);

		for (OWLNamedIndividual person : reasoner2.getInstances(personClass2,
				false).getFlattened()) {
			//System.out.println("Movies : " + renderer.render(person));
			// //
//			OWLDataProperty dataP = factory2.getOWLDataProperty(strNs
//					+ "#watch>", pm2);
//
//			 for (OWLLiteral ind : reasoner2
//			 .getDataPropertyValues(person, dataP)) {
//			 System.out.println("Teste lang: " + renderer.render(ind));
//			 }
			// //
			// //
			// //
			OWLObjectProperty propConhece = factory2.getOWLObjectProperty("<" + ns + "genre>", pm2);
			
			//System.out.println(renderer.render(person));
			
			//System.out.println(renderer.render(person));

			// OntologyMethods.addIndividualOnObjProperty(ns, person,
			// filmeLab.toString(), manager2, ontology2, "#watch");

			//OWLNamedIndividual indNa = factory2.getOWLNamedIndividual("<" + ns+"Peter>",pm2);
			
			for (OWLNamedIndividual ind : reasoner2.getObjectPropertyValues(
					person, propConhece).getFlattened()) {
				
				System.out.println(renderer.render(person) + "\t"
						+ ind);
				
//				OWLObjectProperty propConhece2 = factory2.getOWLObjectProperty("<" + ns
//						+ "genre>", pm2);
//
//				 for (OWLNamedIndividual ind2 : reasoner2.getObjectPropertyValues(
//							ind, propConhece2).getFlattened()) {
//				 System.out.println("Teste lang: " + renderer.render(ind2));
//				 }
				
				
			}

		}

	}

	public static OWLNamedIndividual searchMovieWithLabel(String label,
			OWLOntology onto, OWLOntologyManager manager,
			OWLDataFactory factory, PrefixOWLOntologyFormat pm2,
			OWLReasoner reasoner2) {
		OWLClass movieClass = factory.getOWLClass("<" + ns + "Film>", pm2);

		for (OWLNamedIndividual person : reasoner2.getInstances(movieClass,false).getFlattened()) {

			OWLDataProperty dataP = factory.getOWLDataProperty("<" + ns	+ "#label>", pm2);

			for (OWLLiteral ind : reasoner2.getDataPropertyValues(person, dataP)) {
				if (label.equals(renderer.render(ind))) {
					System.out.println(person);
					return person;
				}

			}

		}

		return null;

	}
	
	public static OWLNamedIndividual searchActorWithLabel(String label,
			OWLOntology onto, OWLOntologyManager manager,
			OWLDataFactory factory, PrefixOWLOntologyFormat pm2,
			OWLReasoner reasoner2) {
		OWLClass actorClass = factory.getOWLClass("<" + nameSpaceFao + "#Actor>", pm2);
		
		for (OWLNamedIndividual person : reasoner2.getInstances(actorClass,	false).getFlattened()) {
			OWLDataProperty dataP = factory.getOWLDataProperty("<"+ ns +"#actor_name>", pm2);

			for (OWLLiteral ind : reasoner2.getDataPropertyValues(person, dataP)) {
				if (label.equals(renderer.render(ind))) {
					System.out.println("achei ator:"+person);
					return person;
				}
			}
		}
		return null;
	}
	
	public static OWLNamedIndividual searchDirectorWithLabel(String label,
			OWLOntology onto, OWLOntologyManager manager,
			OWLDataFactory factory, PrefixOWLOntologyFormat pm2,
			OWLReasoner reasoner2) {
		OWLClass actorClass = factory.getOWLClass("<" + nameSpaceFao + "#Director>", pm2);
		
		for (OWLNamedIndividual person : reasoner2.getInstances(actorClass,	false).getFlattened()) {
			OWLDataProperty dataP = factory.getOWLDataProperty("<"+ ns +"#director_name>", pm2);

			for (OWLLiteral ind : reasoner2.getDataPropertyValues(person, dataP)) {
				if (label.equals(renderer.render(ind))) {
					System.out.println("achei diretor:"+person);
					return person;
				}
			}
		}
		return null;
	}
	
	public static OWLNamedIndividual searchWriterWithLabel(String label,
			OWLOntology onto, OWLOntologyManager manager,
			OWLDataFactory factory, PrefixOWLOntologyFormat pm2,
			OWLReasoner reasoner2) {
		OWLClass actorClass = factory.getOWLClass("<" + nameSpaceFao + "#Writer>", pm2);
		
		for (OWLNamedIndividual person : reasoner2.getInstances(actorClass,	false).getFlattened()) {
			OWLDataProperty dataP = factory.getOWLDataProperty("<"+ ns +"#writer_name>", pm2);

			for (OWLLiteral ind : reasoner2.getDataPropertyValues(person, dataP)) {
				if (label.equals(renderer.render(ind))) {
					System.out.println("achei escritor:"+person);
					return person;
				}
			}
		}
		return null;
	}
}
