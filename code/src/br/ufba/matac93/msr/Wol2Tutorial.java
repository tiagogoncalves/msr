package br.ufba.matac93.msr;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
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

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import com.clarkparsia.owlapi.explanation.DefaultExplanationGenerator; 
import com.clarkparsia.owlapi.explanation.util.SilentExplanationProgressMonitor; 
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory; 
import org.semanticweb.owlapi.apibinding.OWLManager; 
import org.semanticweb.owlapi.io.OWLObjectRenderer; 
import org.semanticweb.owlapi.model.*; 
import org.semanticweb.owlapi.reasoner.OWLReasoner; 
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory; 
import org.semanticweb.owlapi.reasoner.SimpleConfiguration; 
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary; 
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat; 
import uk.ac.manchester.cs.bhig.util.Tree; 
import uk.ac.manchester.cs.owl.explanation.ordering.ExplanationOrderer; 
import uk.ac.manchester.cs.owl.explanation.ordering.ExplanationOrdererImpl; 
import uk.ac.manchester.cs.owl.explanation.ordering.ExplanationTree; 
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer; 

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
        
        
        OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager(); 
        OWLOntology ontology2 = manager2.loadOntologyFromOntologyDocument(new File("ontofilmes.owl"));
        OWLReasonerFactory reasonerFactory2 = PelletReasonerFactory.getInstance(); 
        OWLReasoner reasoner2 = reasonerFactory2.createReasoner(ontology2, new SimpleConfiguration()); 
        OWLDataFactory factory2 = manager2.getOWLDataFactory(); 
        PrefixOWLOntologyFormat pm2 = (PrefixOWLOntologyFormat) manager2.getOntologyFormat(ontology2);
<<<<<<< HEAD
        String ns = "http://www.semanticweb.org/lasid/ontologies/2014/1/untitled-ontology-3";
=======
        String ns = "http://www.semanticweb.org/administrator/ontologies/2014/1/untitled-ontology-2";
>>>>>>> 0e1f74cc48a0c6f70d5efaec259074f277a6f796
        pm2.setDefaultPrefix(ns + "#"); 
        
        String strPessoa = "<http://www.semanticweb.org/lasid/ontologies/2014/1/untitled-ontology-3#";
        
        OWLClass personClass2 = factory2.getOWLClass(strPessoa + "Pessoa>", pm2); 
        
        for (OWLNamedIndividual person : reasoner2.getInstances(personClass2, false).getFlattened()) { 
            System.out.println("Pessoa : " + renderer.render(person)); 
        }        
                
        OWLNamedIndividual indMaria = factory2.getOWLNamedIndividual(strPessoa + "Maria>",pm2);
        OWLObjectProperty propConhece = factory2.getOWLObjectProperty(strPessoa + "conhece>",pm2);
        
        for (OWLNamedIndividual ind : reasoner2.getObjectPropertyValues(indMaria, propConhece).getFlattened()) { 
            System.out.println("Joao conhece: " + renderer.render(ind)); 
        } 
         

	}

}
