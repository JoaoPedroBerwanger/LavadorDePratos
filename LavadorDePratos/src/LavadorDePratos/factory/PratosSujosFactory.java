package LavadorDePratos.factory;

import java.util.Random;

import LavadorDePratos.model.NivelSujeira;
import LavadorDePratos.model.Prato;

public class PratosSujosFactory {

	private int proximoNumeroSerie = 1;

	private final Random random = new Random();

	public synchronized Prato gerarPratoSujo() {
		
		int numAleatorio = random.nextInt(100);
		NivelSujeira nivel;
		
		if (numAleatorio < 30) {
			nivel = NivelSujeira.BAIXO;
		} else if (numAleatorio < 90) {
			nivel = NivelSujeira.MEDIO;
		} else {
			nivel = NivelSujeira.ENGORDURADO;
		}
		
		return new Prato(proximoNumeroSerie++, nivel);
	}
}
