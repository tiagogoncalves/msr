package br.ufba.matac93.msr.utils;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OntologyMethods {

	public static OWLIndividual addIndividual(String uri, String nameClass,
			String nameIndividual, OWLOntologyManager manager,
			OWLOntology ontology) {

		OWLDataFactory factory = manager.getOWLDataFactory();

		// IRI ontologyIRI = IRI.create(uri + nameClass);

		OWLIndividual individual = factory.getOWLNamedIndividual(IRI.create(uri
				+ "#"+nameIndividual));
		OWLClass relatedClass = factory
				.getOWLClass(IRI.create(uri + nameClass));

		OWLClassAssertionAxiom axiom1 = factory.getOWLClassAssertionAxiom(
				relatedClass, individual);

		AddAxiom addAxiom = new AddAxiom(ontology, axiom1);

		manager.applyChange(addAxiom);
		
		return individual;
	}
	
	public static void addDataProperty(String uri, String nameData,
			String valueData, OWLIndividual individual, OWLOntologyManager manager,
			OWLOntology ontology) {

		OWLDataFactory factory = manager.getOWLDataFactory();		
		
		OWLDataProperty dataProperty = factory.getOWLDataProperty(IRI.create(uri +"#"+nameData));

		OWLDataPropertyAssertionAxiom axiom1 = factory.getOWLDataPropertyAssertionAxiom(dataProperty, individual, valueData);
		
		AddAxiom addAxiom = new AddAxiom(ontology, axiom1);

		manager.applyChange(addAxiom);
	}
	
	public static void addIndividualOnObjProperty(String uri, OWLIndividual individualFrom, String uriIndividualRange, 
			OWLOntologyManager manager,
			OWLOntology ontology, String propertyName) {

		OWLDataFactory factory = manager.getOWLDataFactory();

		OWLObjectProperty objProperty = factory.getOWLObjectProperty(IRI.create(uri+propertyName));
		
		OWLIndividual indRange = factory.getOWLNamedIndividual(IRI.create(uriIndividualRange));
		
		OWLObjectPropertyAssertionAxiom axiom1 = factory.getOWLObjectPropertyAssertionAxiom(objProperty, individualFrom, indRange);
		
		AddAxiom addAxiom = new AddAxiom(ontology, axiom1);

		manager.applyChange(addAxiom);
	}
	
	public static void addIndividualOnObjProperty(String uri, OWLIndividual individualFrom, OWLIndividual individualRange, 
			OWLOntologyManager manager,
			OWLOntology ontology, String propertyName) {

		OWLDataFactory factory = manager.getOWLDataFactory();

		OWLObjectProperty objProperty = factory.getOWLObjectProperty(IRI.create(uri+propertyName));
		
		OWLIndividual indRange = individualRange;
		
		OWLObjectPropertyAssertionAxiom axiom1 = factory.getOWLObjectPropertyAssertionAxiom(objProperty, individualFrom, indRange);
		
		AddAxiom addAxiom = new AddAxiom(ontology, axiom1);

		manager.applyChange(addAxiom);
	}

}
