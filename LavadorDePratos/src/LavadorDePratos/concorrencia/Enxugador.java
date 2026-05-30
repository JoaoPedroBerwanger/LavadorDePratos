package LavadorDePratos.concorrencia;

import java.util.Random;
import java.util.logging.Logger;

import LavadorDePratos.model.Prato;

public class Enxugador implements Runnable {

	private static final Logger log = Logger.getLogger(Enxugador.class.getName());

	private static final int tempoMinimo = 1000;
	private static final int tempoMaximo = 3000;

	private final Escorredor escorredor;
	private final Random random = new Random();

	private boolean done = false;

	public Enxugador(Escorredor escorredor) {
		this.escorredor = escorredor;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public void run() {
		
		log.info("Enxugador iniciado.");
		
		while (!done) {
			try {
				Prato prato = escorredor.retirarPrato();

				int tempoEnxugar = tempoMinimo + random.nextInt(tempoMaximo - tempoMinimo + 1);
				log.fine("Enxugador enxugando " + prato + " (vai levar " + tempoEnxugar + "ms)");
				Thread.sleep(tempoEnxugar);

				log.fine("Enxugador terminou " + prato + ".");

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				log.info("Enxugador interrompido.");
				break;
			}
		}
		
		log.info("Enxugador finalizado.");
	}
}
