package jus.poc.prodcons;

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