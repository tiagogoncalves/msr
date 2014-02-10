package br.ufba.matac93.msr.utils;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OntologyMethods {

	public static void addIndividual(String uri, String nameClass,
			String nameIndividual, OWLOntologyManager manager,
			OWLOntology ontology) {

		OWLDataFactory factory = manager.getOWLDataFactory();

		// IRI ontologyIRI = IRI.create(uri + nameClass);

		OWLIndividual individual = factory.getOWLNamedIndividual(IRI.create(uri
				+ "#"+nameIndividual));
		OWLClass relatedClass = factory
				.getOWLClass(IRI.create(uri + "#Pessoa"));

		OWLClassAssertionAxiom axiom1 = factory.getOWLClassAssertionAxiom(
				relatedClass, individual);

		AddAxiom addAxiom = new AddAxiom(ontology, axiom1);

		manager.applyChange(addAxiom);
	}

}
