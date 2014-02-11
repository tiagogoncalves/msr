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

public class MSROntology {

	private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
	private static final String namSpaceLinkedMD = "http://www.semanticweb.org/matc93/ontology/linkedmdb_lite";
	private static final String nameSpaceFao = "http://www.semanticweb.org/matc93/ontology/fao";
	private static final String nameSpaceMovie = "http://data.linkedmdb.org/resource/movie/";
	private static final String dbPediaProperty = "http://dbpedia.org/property/";
	private static final String ns = "http://dbpedia.org/matc93/ontology/dbpedia_lite";
	private static final String RDF = "RDFLinkedmdb4";
	private static final String FAO = "fao.owl";
	private static final String DMDB = "linkedmdb_lite.owl";
	private static final String MAIN_ONTO = "dbpedia_lite_sem_regras.owl";
	
	
	private OWLOntologyManager manager;
	private OWLOntology ontology;
	private OWLReasonerFactory reasonerFactory;
	private OWLDataFactory factory;
	private Model model;
	
	
	public MSROntology() throws OWLOntologyCreationException{
		manager = OWLManager.createOWLOntologyManager();

		manager.loadOntologyFromOntologyDocument(new File(FAO));
		manager.loadOntologyFromOntologyDocument(new File(DMDB));

		ontology = manager.loadOntologyFromOntologyDocument(new File(MAIN_ONTO));
		reasonerFactory = PelletReasonerFactory.getInstance();

		factory = manager.getOWLDataFactory();
		model = ModelFactory.createDefaultModel();
		model.read(RDF);
	}

	
	
	public void populate(){
		int i = 0;
		ResIterator it = model.listSubjects();
		while (it.hasNext()) {

			i++;

			Resource r = it.nextResource();

			OWLIndividual individual = OntologyMethods.addIndividual(ns,"Film", r.getURI(), manager, ontology);


			if(r.toString().contains("http://data.linkedmdb.org/resource/film/")){
				Statement stmt2 = r.getRequiredProperty(model.getProperty("http://www.w3.org/2000/01/rdf-schema#label"));
				OntologyMethods.addDataProperty(ns, "label", stmt2.getString(),individual, manager, ontology);
			}

			
			// /pegando os generos
			Resource r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie + "genre"));
			StmtIterator its = r.listProperties(model.getProperty(nameSpaceMovie + "genre"));
			if (its != null) {			
				Statement stmV ;
				for(; its.hasNext();   ){
					stmV = its.next();		
					OntologyMethods.addIndividualOnObjProperty(ns, individual,stmV.getResource().getURI(), manager, ontology, "genre");
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
					OWLIndividual actorT = OntologyMethods.addIndividual(nameSpaceFao,"#Actor", stmV.getResource().getURI(), manager, ontology);
					OntologyMethods.addIndividualOnObjProperty(ns, individual, actorT, manager, ontology, "starring");
					OntologyMethods.addDataProperty(ns, "actor_name", stmV.getResource().getProperty(p).getObject().toString(),actorT, manager, ontology);
				}		
				
			}
			


			// pegando as linguas
			r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie+ "language"));
			its = r.listProperties(model.getProperty(nameSpaceMovie + "language"));
			if (its != null) {				
				Statement stmV ;
				for(; its.hasNext();   ){
					stmV = its.next();
					
					OntologyMethods.addDataProperty(dbPediaProperty, "language",
							stmV.getResource().getURI(), individual, manager, ontology);
				}		
				
			}
	
			// pegando os escritores

			its = r.listProperties(model.getProperty(nameSpaceMovie + "writer"));
			
			if (its != null) {				
				Statement stmV ;
				for(; its.hasNext();   ){
					stmV = its.next();
					
					OWLIndividual writerT = OntologyMethods.addIndividual(nameSpaceFao,"#Writer", stmV.getResource().getURI(), manager, ontology);
					Property p = model.getProperty(nameSpaceMovie+"writer_name");
					System.out.println("writer:"+stmV.getResource().getProperty(p).getObject().toString());
					OntologyMethods.addIndividualOnObjProperty(ns, individual, writerT, manager, ontology, "writer");
					OntologyMethods.addDataProperty(ns, "writer_name", stmV.getResource().getProperty(p).getObject().toString(),writerT, manager, ontology);
				
				}		
				
			}

			// pegando os diretores

			r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie+ "director"));
			
			its = r.listProperties(model.getProperty(nameSpaceMovie + "director"));
			
			if (its != null) {				
				Statement stmV ;
				for(; its.hasNext();   ){
					stmV = its.next();
					
					OWLIndividual directorT = OntologyMethods.addIndividual(nameSpaceFao,"#Director", stmV.getResource().getURI(), manager, ontology);
					Property p = model.getProperty(nameSpaceMovie+"director_name");
					System.out.println("director:"+stmV.getResource().getProperty(p).getObject().toString());
					
					OntologyMethods.addIndividualOnObjProperty(ns, individual, directorT, manager, ontology, "director");
					OntologyMethods.addDataProperty(ns, "director_name", stmV.getResource().getProperty(p).getObject().toString(),directorT, manager, ontology);
				
				}		
				
			}
			

			// filmes prequel de outro filme

			r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie
					+ "prequel"));
			if (r2 != null) {
				OntologyMethods.addIndividualOnObjProperty(namSpaceLinkedMD,
						individual, r2.getURI(), manager, ontology,
						"#prequel");
			}

			// filmes sequel de outro filme
			r2 = r.getPropertyResourceValue(model.getProperty(nameSpaceMovie+ "sequel"));
			if (r2 != null) {

				OntologyMethods
						.addIndividualOnObjProperty(namSpaceLinkedMD,
								individual, r2.getURI(), manager, ontology,"#sequel");
			}

		}
	}

	private OWLNamedIndividual searchMovieWithLabel(String label,
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
	
	private OWLNamedIndividual searchActorWithLabel(String label,
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
	
	private OWLNamedIndividual searchDirectorWithLabel(String label,
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
	
	private static OWLNamedIndividual searchWriterWithLabel(String label,
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
	
	public void test(){
		OWLIndividual personTeste = OntologyMethods.addIndividual(nameSpaceFao,"#Person", "Peter", manager, ontology);
		
		OWLIndividual personTeste2 = OntologyMethods.addIndividual(nameSpaceFao,"#Person", "Angela", manager, ontology);
		
		OWLIndividual actorTeste = OntologyMethods.addIndividual(nameSpaceFao, "#Actor", "http://data.linkedmdb.org/resource/actor/29685",manager, ontology);
		
		OntologyMethods.addIndividualOnObjProperty(nameSpaceFao, personTeste, actorTeste, manager, ontology, "#likes");
		OntologyMethods.addIndividualOnObjProperty(nameSpaceFao, personTeste, personTeste2, manager, ontology, "#relationship");
		
		System.out.println(personTeste.toString());

		int i = 0;

		OWLReasoner reasoner2 = reasonerFactory.createReasoner(ontology,
				new SimpleConfiguration());

		PrefixOWLOntologyFormat pm2 = (PrefixOWLOntologyFormat) manager
				.getOntologyFormat(ontology);

		pm2.setDefaultPrefix(ns + "#");
		
		OWLNamedIndividual filmeLab = searchMovieWithLabel("Star Trek III: The Search for Spock",ontology, manager, factory, pm2, reasoner2);
		OWLNamedIndividual actorLab = searchActorWithLabel("Antonio Banderas",ontology, manager, factory, pm2, reasoner2);
		OWLNamedIndividual directorLab = searchDirectorWithLabel("Jay Levey",ontology, manager, factory, pm2, reasoner2);
		OWLNamedIndividual writerLab = searchWriterWithLabel("William Shatner",ontology, manager, factory, pm2, reasoner2);
		
		
		
		

		if (filmeLab != null) {
			System.out.println("Voltou");
			
		   
			
//			OWLObjectProperty propConhece2 = factory2.getOWLObjectProperty("<" + ns
//					+ "genre>", pm2);
//
//			 for (OWLNamedIndividual ind2 : reasoner2.getObjectPropertyValues(
//						filmeLab, propConhece2).getFlattened()) {
//			 System.out.println("Teste lang: " + renderer.render(ind2));
//			 }
			
			OntologyMethods.addIndividualOnObjProperty(ns, personTeste,filmeLab, manager, ontology, "#watch");
			OntologyMethods.addIndividualOnObjProperty(ns, personTeste,actorLab, manager, ontology, "#likes");
			OntologyMethods.addIndividualOnObjProperty(ns, personTeste,directorLab, manager, ontology, "#likes");
			OntologyMethods.addIndividualOnObjProperty(ns, personTeste,writerLab, manager, ontology, "#likes");
			
			

		}
		
		OWLNamedIndividual filmeLab2 = searchMovieWithLabel("Star Trek II: The Wrath of Khan",	ontology, manager, factory, pm2, reasoner2);
		
		if (filmeLab2 != null) {
			System.out.println("Voltou2");
			
			OntologyMethods.addIndividualOnObjProperty(ns, filmeLab,filmeLab2, manager, ontology, "#sequel");
//			OntologyMethods.addIndividualOnObjProperty(ns, personTeste,filmeLab2, manager2, ontology2, "#watchLater");

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
		
		reasoner2 = reasonerFactory.createReasoner(ontology,new SimpleConfiguration());
		
		pm2 = (PrefixOWLOntologyFormat) manager
				.getOntologyFormat(ontology);

		pm2.setDefaultPrefix(ns + "#");
		

		String strNs = "<" + ns;

		OWLClass personClass2 = factory.getOWLClass("<" + ns	+ "#Films>", pm2);

		// OWLDataProperty dataPrp =
		// reasoner2.getInstances(factory2.getOWLDataProperty(strNs+"#label",
		// pm2),false);

		for (OWLNamedIndividual person : reasoner2.getInstances(personClass2,false).getFlattened()) {
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
			OWLObjectProperty propConhece = factory.getOWLObjectProperty("<" + ns + "#watchLater>", pm2);
			
			//System.out.println(renderer.render(person));
			
			//System.out.println(renderer.render(person));

			// OntologyMethods.addIndividualOnObjProperty(ns, person,
			// filmeLab.toString(), manager2, ontology2, "#watch");

			//OWLNamedIndividual indNa = factory2.getOWLNamedIndividual("<" + ns+"Peter>",pm2);
			
			for (OWLNamedIndividual ind : reasoner2.getObjectPropertyValues(person, propConhece).getFlattened()) {
				
				System.out.println("inferência: "+renderer.render(person) + "\t"+ ind);
				
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
}
