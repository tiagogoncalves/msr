package br.ufba.matac93.msr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// String ont = "ontofilmeX.owl";
		// String ns =
		// "http://www.semanticweb.org/jandson/ontologies/2014/1/untitled-ontology-4#";
		//
		// // create Pellet reasoner
		// Reasoner reasoner = PelletReasonerFactory.theInstance().create();
		//
		// // create an empty model
		// // Model emptyModel = ModelFactory.createDefaultModel( );
		// //
		// // // create an inferencing model using Pellet reasoner
		// OntModel model = ModelFactory.createOntologyModel(
		// PelletReasonerFactory.THE_SPEC, null);
		// //
		// // // read the file
		// model.read( ont );
		// //
		// // ValidityReport report = model.validate();
		// // printIterator( report.getReports(), "Validation Results" );
		//
		// OntClass BadChild = model.getOntClass( ns + "Pessoa" );
		// Individual angelina = model.getIndividual( ns + "Joao" );
		// ObjectProperty estrelando = model.getObjectProperty( ns + "gosta" );
		//
		// // ValidityReport report = model.validate();
		// //printIterator( report.getReports(), "Validation Results" );
		//
		// model.prepare();
		//
		// printPropertyValues( angelina, estrelando );
		//
		// //Model raModel = ModelFactory.createDefaultModel();
		// Reasoner r = PelletReasonerFactory.theInstance().create();
		//
		// InfModel modelInfr = ModelFactory.createInfModel(r, model);
		//
		// //modelInfr.read(ont);
		//
		// modelInfr.validate();
		// modelInfr.prepare();
		//
		// ValidityReport report = model.validate();
		// printIterator( report.getReports(), "Validation Results" );
		//
		// // print superclasses
		// Resource c = model.getResource( ns + "Pessoa" );
		// ObjectProperty objs = model.getObjectProperty("ns" + "gosta");
		//
		// Resource c2 = modelInfr.getResource(ns + "Pesssoa");
		// Model mdf = modelInfr.getRawModel();
		//
		// Resource c2t = modelInfr.getResource(ns + "Joao");
		// Property prf = modelInfr.getProperty(ns + "gosta");
		// printIterator(modelInfr.listObjectsOfProperty(c2t,prf),
		// "All super classes of " + c2t.getLocalName());
		//
		//
		// //printIterator(modelInfr.listResourcesWithProperty("gosta"),
		// "All super classes of " + c.getLocalName());
		//
		//
		//
		// //ObjectProperty estrelando2 = model.getObjectProperty( ns + "gosta"
		// );
		//
		// //printIterator(model.listObjectsOfProperty(c, RDFS.subClassOf),
		// "All super classes of " + c.getLocalName());

		String ont = "ontofilmeX.owl";
		String ns = "http://www.semanticweb.org/jandson/ontologies/2014/1/untitled-ontology-4#";

		OntModel model = ModelFactory.createOntologyModel(
				PelletReasonerFactory.THE_SPEC, null);

		model.read(ont);

		model.prepare();

		ObjectProperty sibling = model.getObjectProperty(ns + "gosta");

		OntClass BadChild = model.getOntClass(ns + "Pessoa");
		// OntClass Child = model.getOntClass( ns + "#Child" );

		Individual Abel = model.getIndividual(ns + "Joao");
		// Individual Cain = model.getIndividual( ont + "#Cain" );
		// Individual Remus = model.getIndividual( ont + "#Remus" );
		// Individual Romulus = model.getIndividual( ont + "#Romulus" );

		model.prepare();

		// Cain has sibling Abel due to SiblingRule
		printPropertyValues(Abel, sibling);
		// Abel has sibling Cain due to SiblingRule and rule works symmetric
		// printPropertyValues( Abel, sibling );
		// Remus is not inferred to have a sibling because his father is not
		// known
		// printPropertyValues( Remus, sibling );
		// No siblings for Romulus for same reasons
		// printPropertyValues( Romulus, sibling );

		// Cain is a BadChild due to BadChildRule
		printInstances(BadChild);
		// Cain is a Child due to BadChildRule and ChildRule2
		// Oedipus is a Child due to ChildRule1 and ChildRule2 combined with the
		// unionOf type
		// printInstances( Child );

		Model model2 = ModelFactory.createOntologyModel();
		RDFReader g = model.getReader();

		OntModel ontologyModel = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		InputStream ont2 = FileManager.get().open(ont);
		try {
			ont2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
		reasoner = reasoner.bindSchema(ontologyModel);

		Model infModel = ModelFactory.createInfModel(reasoner, model);
		
		Reasoner reasoner2 = PelletReasonerFactory.theInstance().create();	
		
		reasoner2 = reasoner2.bindSchema(model);
		
		Model infModel2 = ModelFactory.createInfModel(reasoner2, model);		

		Resource c2t = infModel2.getResource(ns + "Joao");
		Property prf = infModel2.getProperty(ns + "gosta");
		printIterator(infModel2.listObjectsOfProperty(c2t, prf),
				"All super classes of " + c2t.getLocalName());

	}

	public static void printInstances(OntClass cls) {
		System.out.print(cls.getLocalName() + " instances: ");
		printIterator(cls.listInstances());
	}

	public static void printIterator(Iterator<?> i, String header) {
		System.out.println(header);
		for (int c = 0; c < header.length(); c++)
			System.out.print("=");
		System.out.println();

		if (i.hasNext()) {
			while (i.hasNext())
				System.out.println(i.next());
		} else
			System.out.println("<EMPTY>");

		System.out.println();
	}

	public static void printPropertyValues(Individual ind, Property prop) {
		System.out.print(ind.getLocalName() + " has " + prop.getLocalName()
				+ "(s): ");
		printIterator(ind.listPropertyValues(prop));
	}

	public static void printIterator(ExtendedIterator i) {
		if (!i.hasNext()) {
			System.out.print("none");
		} else {
			while (i.hasNext()) {
				Resource val = (Resource) i.next();
				System.out.print(val.getLocalName());
				if (i.hasNext())
					System.out.print(", ");
			}
		}
		System.out.println();
	}

}
