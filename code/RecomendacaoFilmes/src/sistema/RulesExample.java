// Copyright (c) 2006 - 2008, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// This source code is available under the terms of the Affero General Public License v3.
//
// Please see LICENSE.txt for full license terms, including the availability of proprietary exceptions.
// Questions, comments, or requests for clarification: licensing@clarkparsia.com

package sistema;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * An example program that tests the DL-safe rules example from Table 3 in the
 * paper: B. Motik, U. Sattler, R. Studer. Query Answering for OWL-DL with
 * Rules. Proc. of the 3rd International Semantic Web Conference (ISWC 2004),
 * Hiroshima, Japan, November, 2004, pp. 549-563
 * 
 * @author Evren Sirin
 */
public class RulesExample {
	public static void main(String[] args) throws IOException {
		String ont = "ontofilmeX.owl";
		String ns = "http://www.semanticweb.org/jandson/ontologies/2014/1/untitled-ontology-4#";
				
		OntModel model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC, null);
		
		model.read( new String(Files.readAllBytes(Paths.get(ont))));
		
//		ObjectProperty sibling = model.getObjectProperty( ns + "gosta" );		
//		
//		OntClass BadChild = model.getOntClass( ns + "Pessoa" );
//		//OntClass Child = model.getOntClass( ont + "#Child" );
//
//		Individual Abel = model.getIndividual( ns + "Joao" );
//	//	Individual Cain = model.getIndividual( ont + "#Cain" );
//	//	Individual Remus = model.getIndividual( ont + "#Remus" );
//	//	Individual Romulus = model.getIndividual( ont + "#Romulus" );
//
//		model.prepare();
//		
//		if(Abel == null){
//			System.out.println("ERRRROR");
//		}else{
//			System.out.println("OKKKK");
//		}
//		
//		if(sibling == null){
//			System.out.println("ERRRROR");
//		}else{
//			System.out.println("OKKKK");
//		}
//
//		// Cain has sibling Abel due to SiblingRule
//		//printPropertyValues( Cain, sibling );
//		// Abel has sibling Cain due to SiblingRule and rule works symmetric
//		printPropertyValues( Abel, sibling );
		// Remus is not inferred to have a sibling because his father is not
		// known
		//printPropertyValues( Remus, sibling );
		// No siblings for Romulus for same reasons
	//	printPropertyValues( Romulus, sibling );

		// Cain is a BadChild due to BadChildRule
		//printInstances( BadChild );
		// Cain is a Child due to BadChildRule and ChildRule2
		// Oedipus is a Child due to ChildRule1 and ChildRule2 combined with the
		// unionOf type
		//printInstances( Child );
	}

	public static void printPropertyValues(Individual ind, Property prop) {
		System.out.print( ind.getLocalName() + " has " + prop.getLocalName() + "(s): " );
		printIterator( ind.listPropertyValues( prop ) );
	}

	public static void printInstances(OntClass cls) {
		System.out.print( cls.getLocalName() + " instances: " );
		printIterator( cls.listInstances() );
	}

	public static void printIterator(ExtendedIterator i) {
		if( !i.hasNext() ) {
			System.out.print( "none" );
		}
		else {
			while( i.hasNext() ) {
				Resource val = (Resource) i.next();
				System.out.print( val.getLocalName() );
				if( i.hasNext() )
					System.out.print( ", " );
			}
		}
		System.out.println();
	}
}
