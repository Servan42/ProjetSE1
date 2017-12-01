package jus.poc.prodcons.step1;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

	public TestProdCons(Observateur observateur) {
		super(observateur);
	}

	protected void run() throws Exception {
		// le corps de votre programme principal
		String filename = "option.xml";
		init("jus/poc/prodcons/options/" + filename);
	}
	
	/**
	 * Fonction qui initialise le fichier xml d'option
	 * @param path Chemin vers le fichier
	 */
	protected void init(String path){
		
	}

	public static void main(String[] args) {
		new TestProdCons(new Observateur()).start();
	}
}