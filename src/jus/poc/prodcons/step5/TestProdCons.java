package jus.poc.prodcons.step5;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {
	public TestProdCons(Observateur observateur) {
		super(observateur);
	}

	protected void run() throws Exception {
		// le corps de votre programme principal

	}

	public static void main(String[] args) {
		new TestProdCons(new Observateur()).start();
	}
}