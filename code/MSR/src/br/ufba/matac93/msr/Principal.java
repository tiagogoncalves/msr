package br.ufba.matac93.msr;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class Principal {
	
	public static void main(String[] args) {
		MSROntology mo;
		try {
			mo = new MSROntology();
			mo.populate();
			mo.test();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		
	}

}
