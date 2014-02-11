package com.ufba.iws.principal;

import java.io.IOException;

import com.ufba.iws.sparql.SPARQL;

public class Principal {
	public static void main(String[] args) throws IOException {
		SPARQL.writedmdbRDF();
//		SPARQL.writeDBPediaRDF();//TODO com erro
	}
}
