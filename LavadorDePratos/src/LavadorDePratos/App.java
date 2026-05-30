package LavadorDePratos;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import LavadorDePratos.concorrencia.Enxugador;
import LavadorDePratos.concorrencia.Escorredor;
import LavadorDePratos.concorrencia.Lavador;
import LavadorDePratos.factory.PratosSujosFactory;

public class App {

	private static final int tamMaximoEscorredor = 10;
	private static final long duracaoAPP = 2L * 60L * 1000L;
	private static final Level nivelLog = Level.FINE;

	private static final Logger log = Logger.getLogger(App.class.getName());

	private final Escorredor escorredor;
	private final PratosSujosFactory factory;

	private Lavador lavador;
	private Enxugador enxugador;
	private Thread threadLavador;
	private Thread threadEnxugador;

	public App() {
		this.escorredor = new Escorredor(tamMaximoEscorredor);
		this.factory = new PratosSujosFactory();
	}

	public void work() {
		
		log.info("Iniciando lavador e enxugador. Capacidade do escorredor=" + tamMaximoEscorredor);
		
		lavador = new Lavador(escorredor, factory);
		enxugador = new Enxugador(escorredor);

		threadLavador = new Thread(lavador, "Lavador");
		threadEnxugador = new Thread(enxugador, "Enxugador");

		threadLavador.start();
		threadEnxugador.start();
	}

	public void stop() {
		
		log.info("Solicitando o encerramento das threads.");
		
		lavador.setDone(true);
		enxugador.setDone(true);
		threadLavador.interrupt();
		threadEnxugador.interrupt();
	}

	public static void main(String[] args) throws InterruptedException {
		
		configurarLogger();

		App app = new App();
		app.work();

		Thread.sleep(duracaoAPP);

		app.stop();

		app.threadLavador.join();
		app.threadEnxugador.join();

		log.info("Aplicacao encerrada com sucesso.");
	}

	private static void configurarLogger() {
		
		Logger root = Logger.getLogger("");
		root.setLevel(nivelLog);
		
		for (Handler h : root.getHandlers()) {
			h.setLevel(nivelLog);
			if (h instanceof ConsoleHandler) {
				h.setLevel(nivelLog);
			}
		}
	}
}
