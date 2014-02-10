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
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class Main2Teste {

	private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();
	private static final String namSpaceLinkedMD = "http://www.semanticweb.org/matc93/ontology/linkedmdb_lite#prequel";

	public static void main(String[] args) throws OWLOntologyCreationException {
		String ns = "http://dbpedia.org/matc93/ontology/dbpedia_lite";

		OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager();

		manager2.loadOntologyFromOntologyDocument(new File("fao.owl"));
		manager2.loadOntologyFromOntologyDocument(new File("linkedmdb_lite.owl"));

		OWLOntology ontology2 = manager2
				.loadOntologyFromOntologyDocument(new File("dbpedia_lite.owl"));
		OWLReasonerFactory reasonerFactory2 = PelletReasonerFactory
				.getInstance();

		OWLDataFactory factory2 = manager2.getOWLDataFactory();

		Model model = ModelFactory.createDefaultModel();

		model.read("rdfLinked");

		ResIterator it = model.listSubjects();

		while (it.hasNext()) {

			Resource r = it.nextResource();

			OWLIndividual individual = OntologyMethods.addIndividual(ns,
					"Film", r.getURI(), manager2, ontology2);

			// System.out.println(r.getURI());

			Statement stmt2 = r.getRequiredProperty(model
					.getProperty("http://www.w3.org/2000/01/rdf-schema#label"));

			OntologyMethods.addDataProperty(ns, "label", stmt2.getString(),
					individual, manager2, ontology2);

			// System.out.println(stmt2.getString());

			//
		}

		// OntologyMethods
		// .addIndividual(ns, "Film", "HomemAranha", manager2, ontology2);
		//
		OWLReasoner reasoner2 = reasonerFactory2.createReasoner(ontology2,
				new SimpleConfiguration());

		PrefixOWLOntologyFormat pm2 = (PrefixOWLOntologyFormat) manager2
				.getOntologyFormat(ontology2);

		pm2.setDefaultPrefix(ns + "#");

		String strNs = "<" + ns;

		OWLClass personClass2 = factory2.getOWLClass(strNs + "Film>", pm2);

		for (OWLNamedIndividual person : reasoner2.getInstances(personClass2,
				false).getFlattened()) {
			System.out.println("Movies : " + renderer.render(person));

			OWLDataProperty dataP = factory2.getOWLDataProperty(strNs
					+ "#label>", pm2);

			for (OWLLiteral ind : reasoner2
					.getDataPropertyValues(person, dataP)) {
				System.out.println("Teste label: " + renderer.render(ind));
			}

		}

	}

}
