package LavadorDePratos.model;

public class Prato {

	private final int numeroSerie;
	private final NivelSujeira nivel;

	public Prato(int numeroSerie, NivelSujeira nivel) {
		this.numeroSerie = numeroSerie;
		this.nivel = nivel;
	}

	public int getNumeroSerie() {
		return numeroSerie;
	}

	public NivelSujeira getNivel() {
		return nivel;
	}

	@Override
	public String toString() {
		return "Prato #" + numeroSerie + " (" + nivel + ")";
	}
}
