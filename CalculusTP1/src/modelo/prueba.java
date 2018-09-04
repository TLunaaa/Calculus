package modelo;

import vista.FrontEnd;
import vista.IFrontEnd;

public class prueba {

	public static void main(String[] args) {
            System.out.println("hola");
	    IFrontEnd ventana = new FrontEnd();
            Calculadora calc = new Calculadora();
	    ventana.setCalc(calc);
            calc.setVentana(ventana);
	    ventana.arranca();
	}

}
