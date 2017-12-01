package jus.poc.prodcons.step1;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

	public TestProdCons(Observateur observateur) {
		super(observateur);
	}

	protected void run() throws Exception {
		// le corps de votre programme principal
	}
	
	/**
	 * Fonction qui initialise le fichier xml d'option
	 * @param path Chemin vers le fichier
	 */
	private static void init(String path){
		
	}

	public static void main(String[] args) {
		new TestProdCons(new Observateur()).start();
		String filename = "option.x";
		init("jus/poc/prodcons/option/" + filename);
	}
}