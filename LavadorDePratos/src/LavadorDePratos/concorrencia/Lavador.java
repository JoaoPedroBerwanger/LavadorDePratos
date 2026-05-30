package LavadorDePratos.concorrencia;

import java.util.logging.Logger;

import LavadorDePratos.factory.PratosSujosFactory;
import LavadorDePratos.model.NivelSujeira;
import LavadorDePratos.model.Prato;

public class Lavador implements Runnable {

	private static final Logger log = Logger.getLogger(Lavador.class.getName());
	
	private static final long tempoSujeiraBaixa = 1000;
	private static final long tempoSujeiraMedio = 2000;
	private static final long tempoSujeiraEngordurado = 3000;
	
	private final Escorredor escorredor;
	private final PratosSujosFactory factory;

	private boolean done = false;

	public Lavador(Escorredor escorredor, PratosSujosFactory factory) {
		this.escorredor = escorredor;
		this.factory = factory;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public void run() {
		
		log.info("Lavador iniciado.");
		
		while (!done) {
			try {
				Prato prato = factory.gerarPratoSujo();
				log.fine("Lavador pegou " + prato + " da pilha de pratos sujos.");

				long tempoLavagem = tempoParaLavar(prato.getNivel());
				log.fine("Lavador lavando o " + prato + " (vai levar " + tempoLavagem + "ms)");
				Thread.sleep(tempoLavagem);

				escorredor.colocarPrato(prato);

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				log.info("Lavador interrompido. Encerrando");
				break;
			}
		}
		
		log.info("Lavador finalizado.");
	}

	private long tempoParaLavar(NivelSujeira nivel) {
		
		switch (nivel) {
			case BAIXO:        
				return tempoSujeiraBaixa;
			case MEDIO:        
				return tempoSujeiraMedio;
			case ENGORDURADO:  
				return tempoSujeiraEngordurado;
			default:           
				return tempoSujeiraMedio;
		}
	}
}
