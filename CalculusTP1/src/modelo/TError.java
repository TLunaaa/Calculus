package modelo;
public class TError extends Exception {
	private int nro;
	private String comentario;
	
	public TError(int nro) {
		super();
		this.nro = nro;
		this.setComentario(nro);
	}
	
	public void setComentario(int nro) {
		switch (nro) {
		case 0: this.comentario = "Comando mal formado.";
			break;
		case 1: this.comentario = "Operacion no conocida.";
			break;
		case 2: this.comentario = "Dispositivo no conocido.";
			break;
		case 3: this.comentario = "Matrices inexistentes.";
			break;
		case 4: this.comentario = "Operacion no realizable.";
			break;
		case 5: this.comentario = "Matriz de salida ya existe.";
			break;
		case 6: this.comentario = "Matriz datos no validos.";
			break;
		}
	}
	
	public String toString() {
		return "Error 00" + this.nro +": "+ this.comentario;
	}
}
