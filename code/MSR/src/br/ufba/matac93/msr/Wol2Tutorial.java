package br.ufba.matac93.msr;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
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


public class Wol2Tutorial {

	
	private static final String BASE_URL = "http://acrab.ics.muni.cz/ontologies/tutorial.owl"; 
    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer(); 
	
	public static void main(String[] args) throws OWLOntologyCreationException {
		
//		OWLOntologyManager manager = OWLManager.createOWLOntologyManager(); 
//        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI.create(BASE_URL)); 
//        OWLReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance(); 
//        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology, new SimpleConfiguration()); 
//        OWLDataFactory factory = manager.getOWLDataFactory(); 
//        PrefixOWLOntologyFormat pm = (PrefixOWLOntologyFormat) manager.getOntologyFormat(ontology); 
//        pm.setDefaultPrefix(BASE_URL + "#"); 
//        
//      //get class and its individuals 
//        OWLClass personClass = factory.getOWLClass(":Person", pm); 
// 
//        for (OWLNamedIndividual person : reasoner.getInstances(personClass, false).getFlattened()) { 
//            System.out.println("person : " + renderer.render(person)); 
//        } 
        
		 String ns = "http://www.semanticweb.org/lasid/ontologies/2014/1/untitled-ontology-3";
        
        OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager(); 
        OWLOntology ontology2 = manager2.loadOntologyFromOntologyDocument(new File("ontofilmes.owl"));
        OWLReasonerFactory reasonerFactory2 = PelletReasonerFactory.getInstance();                                
        
        OWLDataFactory factory2 = manager2.getOWLDataFactory();
        
        IRI ontologyIRI = IRI.create(ns);
        
//        OWLIndividual john = factory2.getOWLNamedIndividual(IRI.create(ontologyIRI + "#John"));
//        
//        
//       // IRI indidualTiago = IRI.create(ontology2.getOntologyID().getOntologyIRI() + "#Tiago");
//        
//        OWLClass classePessoa = manager2.getOWLDataFactory().getOWLClass(IRI.create(ontologyIRI + "#Pessoa"));
//        
//        OWLClassAssertionAxiom axiom1 = factory2.getOWLClassAssertionAxiom(classePessoa, john);
//        
//        AddAxiom addAxiom = new AddAxiom(ontology2, axiom1);
//        
//        manager2.applyChange(addAxiom);
        
        OntologyMethods.addIndividual(ns, "Pessoa", "John", manager2, ontology2);
        
        OWLReasoner reasoner2 = reasonerFactory2.createReasoner(ontology2, new SimpleConfiguration()); 
        
        
        
        PrefixOWLOntologyFormat pm2 = (PrefixOWLOntologyFormat) manager2.getOntologyFormat(ontology2);        
        
       


        pm2.setDefaultPrefix(ns + "#");        
                
        String strPessoa = "<http://www.semanticweb.org/lasid/ontologies/2014/1/untitled-ontology-3#";
        
        OWLClass personClass2 = factory2.getOWLClass(strPessoa + "Pessoa>", pm2);        
        
        for (OWLNamedIndividual person : reasoner2.getInstances(personClass2, false).getFlattened()) { 
            System.out.println("Pessoa : " + renderer.render(person)); 
        }        
                
        OWLNamedIndividual indMaria = factory2.getOWLNamedIndividual(strPessoa + "Joao>",pm2);
        OWLObjectProperty propConhece = factory2.getOWLObjectProperty(strPessoa + "gosta>",pm2);
        
        for (OWLNamedIndividual ind : reasoner2.getObjectPropertyValues(indMaria, propConhece).getFlattened()) { 
            System.out.println("Joao conhece: " + renderer.render(ind)); 
        } 
         

	}

}
