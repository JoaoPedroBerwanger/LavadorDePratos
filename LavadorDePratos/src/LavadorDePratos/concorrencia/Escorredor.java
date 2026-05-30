package LavadorDePratos.concorrencia;

import java.util.logging.Logger;

import LavadorDePratos.model.Prato;

public class Escorredor {

	private static final Logger log = Logger.getLogger(Escorredor.class.getName());

	private final int tamMaximo;
	private final Prato[] pratos;

	private int inicio = 0;
	private int fim = 0;
	private int qtdPratos = 0;

	public Escorredor(int tamMax) {
		
		if (tamMax <= 0) {
			throw new IllegalArgumentException("A capacidade do escorredor deve ser maior que 0");
		}
		
		this.tamMaximo = tamMax;
		this.pratos = new Prato[tamMax];
	}

	public int getMax() {
		return tamMaximo;
	}

	public synchronized void colocarPrato(Prato prato) throws InterruptedException {
		
		while (qtdPratos == tamMaximo) {
			log.info("Escorredor cheio, lavador aguardando");
			wait();
		}

		pratos[fim] = prato;
		fim = (fim + 1) % tamMaximo;
		qtdPratos++;

		verificarConsistencia();

		log.fine("Lavador colocou " + prato + " no escorredor. Total de pratos no escorredor: " + qtdPratos + "/" + tamMaximo);

		notifyAll();
	}

	public synchronized Prato retirarPrato() throws InterruptedException {
		
		while (qtdPratos == 0) {
			log.info("Escorredor vazio, enxugador aguardando");
			wait();
		}

		Prato prato = pratos[inicio];
		pratos[inicio] = null;
		inicio = (inicio + 1) % tamMaximo;
		qtdPratos--;

		verificarConsistencia();

		log.fine("Enxugador retirou " + prato + " do escorredor. Total de pratos no escorredor: " + qtdPratos + "/" + tamMaximo);

		notifyAll();

		return prato;
	}

	private void verificarConsistencia() {
		
		if (qtdPratos < 0 || qtdPratos > tamMaximo) {
			log.severe("Problema encontrado. Quantidade de pratos =" + qtdPratos + ", maior que o tamanho máximo =" + tamMaximo + ". Encerrando a aplicação.");
			System.exit(1);
		}
	}
}
