package modelo;

import java.util.ArrayList;

import vista.IFrontEnd;

public class Calculadora {
    
    IFrontEnd ventana;


    /**
     * Ejecuta las instrucciones enviadas en un arreglo de strings. <br>
     * <b> post: </b> el metodo se detiene al encontrar un error.
     * @param s es el array de strings - s[] != null
     */
    public void ejecutar(String[] s){
        try {
            for (int i=0;i<s.length;i++){
                interpretarComandos(s[i]);   
            }
            ventana.mostrarInfo("Ejecucion Exitosa", "Se han completado todas las instrucciones");

        } catch (TError e) {
                ventana.mostrarError("Error Detectado", e.toString()+"\nSecuencia Interrumpida");
                ventana.imprimeError("Error Detectado: " + e.toString());
        }
    }
    /**
     * Determina los comandos dentro de una cadena y los ejecuta, si es posible <br>
     * <b> post: </b> el metodo se detiene al encontrar un error.
     * @param cadena es el string que contiene los comandos - cadena != null y cadena != ""
     * @return devuelve la matriz resultado
     * @throws TError excepcion que se produce al detectar un error
     */
    public Matriz interpretarComandos(String cadena) throws TError{
        //Comandos binarios (entre dos matrices): <operacion> <matriz> <matriz> [<matriz>] <dispositivo>
        //Comandos unarios (aplicados a una matriz): <operacion> <matriz> [ <matriz> ] <dispositivo>
        ArrayList<String> comandos = this.validaComando(cadena);
        int cantMat=comandos.size()-2;
        try{
            if (cantMat==0)
                throw new TError(0);
            Matriz res = null;
            Matriz A = AdministradorDeArchivos.cargaMatriz(comandos.get(1));
            
            if(comandos.get(0).equalsIgnoreCase("determinante")){
                if (cantMat > 2)
                    throw new TError(0);
                if ((cantMat==1 && comandos.get(comandos.size()-1).equalsIgnoreCase("archivo")) || (cantMat==1 && comandos.get(comandos.size()-1).equalsIgnoreCase("pa") ))
                    throw new TError(0);
                res = new Matriz(1,1);
                if (cantMat == 2) 
                    res.setNombre(comandos.get(comandos.size()-2));
                res.setDescripcion("determinante de"+" "+A.getNombre());
                res.setCelda(0, 0, this.determinante(A));
            }else if(comandos.get(0).equalsIgnoreCase("transpuesta") || comandos.get(0).equalsIgnoreCase("traspuesta")){
                    if (cantMat > 2)
                        throw new TError(0);
                    if ((cantMat==1 && comandos.get(comandos.size()-1).equalsIgnoreCase("archivo")) || (cantMat==1 && comandos.get(comandos.size()-1).equalsIgnoreCase("pa") ))
                        throw new TError(0);
                    res = this.transpuesta(A);
                    if (cantMat == 2) 
                        res.setNombre(comandos.get(comandos.size()-2));
                    res.setDescripcion("transpuesta de"+" "+A.getNombre());
            }else{
                Matriz B = AdministradorDeArchivos.cargaMatriz(comandos.get(2));
                if ((cantMat > 3) || (cantMat==3 && comandos.get(comandos.size()-1).equalsIgnoreCase("pantalla")))
                    throw new TError(0);
                if ((cantMat==2 && comandos.get(comandos.size()-1).equalsIgnoreCase("archivo")) || (cantMat==2 && comandos.get(comandos.size()-1).equalsIgnoreCase("pa") ))
                    throw new TError(0);
                if(comandos.get(0).equalsIgnoreCase("sumar")){
                    res = this.suma(A, B);
                    res.setDescripcion("suma entre"+" "+A.getNombre()+" y "+B.getNombre());
                }else if(comandos.get(0).equalsIgnoreCase("restar")){
                    res = this.resta(A, B);
                    res.setDescripcion("resta entre"+" "+A.getNombre()+" y "+B.getNombre());
                }else if(comandos.get(0).equalsIgnoreCase("multiplicar")){
                    res = this.multiplicar(A, B);
                    res.setDescripcion("multiplicacion entre"+" "+A.getNombre()+" y "+B.getNombre());
                }
                if (cantMat == 3) 
                    res.setNombre(comandos.get(comandos.size()-2)); //Copia el valor del anteultimo elemento en res->nombre
            }
            
            switch(comandos.get(comandos.size()-1)){
                case "pantalla": ventana.imprimeMatriz(res, res.getNombre()); break;
                case "archivo":  AdministradorDeArchivos.guardaMatriz(res); break;
                case "pa":{
                    AdministradorDeArchivos.guardaMatriz(res);
                    ventana.imprimeMatriz(res, res.getNombre());
                }break;
                default: throw new TError(2);
            }
            return res;
        }catch(TError e){
            throw e;
        }
    }

    /**
     * Recibe una linea comando y devuelve los argumentos en un ArrayList de strings <b>.
     * @param cadena es el string que contiene los comandos - cadena != null y cadena != ""
     * @return Devuelve un ArrayList de strings con los argumentos
     * @throws TError excepcion que se produce al detectar un error
     */
    public ArrayList<String> validaComando(String cadena) throws TError{
        ArrayList<String> comandos = new ArrayList<String>();
        String[] subcadenas = cadena.split("<");
        for(int i=0;i<subcadenas.length;i++){
            if(subcadenas[i].length()!= 0){
                subcadenas[i] = subcadenas[i].replaceAll(">", "");
                subcadenas[i] = subcadenas[i].replaceAll(" ", "");
                comandos.add(subcadenas[i]);
            }
        }
        switch(comandos.get(0)){
            case "determinante" : break;
            case "transpuesta" : break;
            case "traspuesta" : break;
            case "sumar" : break;
            case "restar" : break;
            case "multiplicar" : break;
            default : throw new TError(1);
        }
        switch(comandos.get(comandos.size()-1)){
            case "pantalla": break;
            case "archivo": break;
            case "pa":break;
            default: throw new TError(2);
        }
        
        return comandos;
    }

    /**
     * Establece el atributo ventana de this como el parametro ventana.
     * <b> pre: </b> la ventana no debe ser nula
     * @param ventana es el parametro que se asignar� - ventana != null
     */
    public void setVentana(IFrontEnd ventana) {
        this.ventana = ventana;
    }

    /**
         * Realiza el determinante de una matriz.
         * <b> pre: </b> las matriz no debe estar vacia y debe ser cuadrada.<br>
         * <b> post: </b> devuelve el determinante de una matriz.<br>
         * @param A primera matriz - A != null.
         * @return Devuelve el resultado
         * @throws TError excepcion que se produce al detectar un error
         */ 
    public int determinante(Matriz A) throws TError{
        int res=0;
        int j=0;
        int[] sign = new int[A.getFilas()];
        if(A.getFilas() == A.getColumnas()){  //es una matriz cuadrada
            if(A.getFilas()==3){
                return this.determinante3x3(A);
            }else
                j = colmayCantCeros(A);
                this.cargaSig(sign, j);
                for(int i=0;i<A.getFilas();i++){
                    res += A.getM()[i][j]* this.determinante(quitafilacolumna(i, j, A))*sign[i]; 
                }
                return res;
        }else{
            throw new TError(4);
        }
    }

    /**
     * Carga un vector de signos dependiendo de la posicion del numero de la columna. <br>
     * <b>post </b> El vector pasado como parametro sale con los valores 1 y -1 dependiendo la columna.
     * @param arraysign vector que recibe los valores 1 y -1.
     * @param j columna que contenia la mayor cantidad de 0 de la Matriz.
     */
    public void cargaSig(int[] arraysign,int j){
        if(j%2==0){
            for(int i=0;i<arraysign.length;i=i+2){
                arraysign[i]= 1;
                if(i+1<arraysign.length)
                    arraysign[i+1]=-1;
            }
        }else {
            for(int i=0;i<arraysign.length;i=i+2){
                arraysign[i]= -1;
                if(i+1<arraysign.length)
                    arraysign[i+1]=1;
            }
        }
    }

    /**
     * Realiza un determinante de 3x3 de una matriz
     * <b> pre: </b> La matriz no es nula y es cuadrada de 3x3
     * <b> post: </b> devuelve el determinante de una matriz 3x3
     * @param A matriz a procesar
     * @return devuelve le resultado
     * @throws TError excepcion que se produce al detectar un error
     */
    public int determinante3x3(Matriz A) throws TError{
        int i,j=0;
        int res;
        res = A.getCelda(0, 0)*((A.getCelda(1, 1)*A.getCelda(2, 2))-(A.getCelda(1, 2)*A.getCelda(2, 1))) +
               A.getCelda(0, 1)*((A.getCelda(1, 2)*A.getCelda(2, 0))-(A.getCelda(1, 0)*A.getCelda(2, 2))) +
               A.getCelda(0, 2)*((A.getCelda(1, 0)*A.getCelda(2, 1))-(A.getCelda(1, 1)*A.getCelda(2, 0)));
        return res;
    }

    /**
     * Quita la fila y la columna creando una nueva Matriz de dimensiones menores a la original. <br>
     * @param fila es la fila que se va a quitar.- fila!= null.
     * @param columna es la columna que se va a quitar.- columna != null.
     * @param A Matriz que se va a reducir
     * @return Una matriz con dimensiones menores a la original y sin las filas y columnas pasadas como parametro.
     */
    public Matriz quitafilacolumna(int fila,int columna,Matriz A){
        Matriz aux = new Matriz(A.getFilas()-1,A.getColumnas()-1);
        aux.inicializar();
        int p=0,q=0;
        for(int i = 0; i < A.getFilas(); i++) {
            q=0;
            for(int j = 0; j < A.getColumnas(); j++) {
                if(i != fila && j!= columna){
                    aux.setCelda(p, q, A.getCelda(i, j));
                    q++;
                }
            }
            if(i != fila)
                p++;
        }
        return aux;
    }

    /**
     * Busca dentro de la Matriz la columna con que posee mayor cantidad de ceros. <br>
     * @param A Matriz donde se busca la columna.- A!=null
     * @return El indice de la columna con mayor cantidad de ceros.
     */
    public int colmayCantCeros(Matriz A){
        int i,j;
        int cant=0,mayorj=0,mayorcant=0;
        for(j=0;j<A.getColumnas();j++){
            for(i=0;i<A.getFilas();i++){
                if(A.getM()[i][j]==0){
                    cant++;
                }
            } 
            if(cant>mayorcant){
                mayorj = j;
                mayorcant = cant;
            }
        }
        return mayorj;
    }    
    /**
         * Realiza la suma entre dos matrices.
         * <b> pre: </b> las matrices no deben estar vacias.<br>
         * <b> post: </b> devuelve la suma entre las dos matrices.<br>
         * @param A - A != null.
         * @param B - B != null.
         * @return
         * @throws TError excepcion que se produce al detectar un error
         */    

    public  Matriz suma(Matriz A, Matriz B) throws TError{
        assert A != null:"Matriz de entrada invalida";
        assert B != null:"Matriz de entrada invalida";
            if(mismasDim(A, B)) {
                    Matriz suma = new Matriz(A.getFilas(), A.getColumnas());
                    for(int i = 0; i < suma.getFilas(); i++) {
                            for(int j = 0; j < suma.getColumnas(); j++) {
                                    suma.setCelda(i, j, (A.getCelda(i, j) + B.getCelda(i, j)));
                            }
                    }
                    return suma;
            } else {
                    throw new TError(4);
            }		
    }

    /**
         * Realiza la resta entre dos matrices.
         * <b> pre: </b> las matrices no deben estar vacias. <br>
         * <b> post: </b> devuelve la resta entre las dos matrices.<br>
         * @param A - A != null;
         * @param B - B != null;
         * @return
         * @throws TError excepcion que se produce al detectar un error
         */
    public  Matriz resta(Matriz A, Matriz B) throws TError{
        assert A != null:"Matriz de entrada invalida";
        assert B != null:"Matriz de entrada invalida";
            if(mismasDim(A, B)) {
                    Matriz resta = new Matriz(A.getFilas(), B.getColumnas());
                    for(int i = 0; i < resta.getFilas(); i++) {
                            for(int j = 0; j < resta.getColumnas(); j++) {
                                    resta.setCelda(i, j, (A.getCelda(i, j) - B.getCelda(i, j)));
                            }
                    }
                    return resta;
            } else {
                    throw new TError(4);
            }
    }
    /**
         * Multiplica dos matrices.
         * <b> pre: </b> las dos matrices no deben estar vacias.<br>
         * <b> post: </b> devuelve en otra matriz el resultado de la multiplicacion.<br>
         * @param A - A != null.<br>
         * @param B - B != null.<br>
         * @return
         * @throws TError excepcion que se produce al detectar un error
         */
    public  Matriz multiplicar(Matriz A, Matriz B) throws TError{
        
        assert A != null: "Matriz de entrada vacia";
        assert B != null: "Matriz de entrada vacia";
            if(multiplicable(A, B)) {
                    Matriz multip = new Matriz(A.getFilas(), B.getColumnas());
                    multip.inicializar();
                    for(int i = 0; i < A.getFilas(); i++) {
                            for(int j = 0; j < B.getColumnas(); j++) {
                                    for(int k = 0; k < A.getColumnas(); k++) {
                                            multip.getM()[i][j] += A.getCelda(i, k) * B.getCelda(k, j);
                                    }
                            }
                    }
                    return multip;
            } else {
                    throw new TError(4);
            }
    }
    /**
         * Realiza la transtpuesta de una matriz dada.
         * <b> pre: </b> la matriz de entrada no debe estar vacia.<br>
         * <b> post: </b> devuelve como resultado la transpuesta de la matriz.<br>
         * @param A - A != null.<br>
         * @return
         */
    public  Matriz transpuesta(Matriz A) {
        assert A != null: "Matriz de entrad invalida";
            Matriz transpuesta = new Matriz(A.getColumnas(), A.getFilas());
            for(int i = 0; i < transpuesta.getFilas(); i++) {
                    for(int j = 0; j < transpuesta.getColumnas(); j++) {
                            transpuesta.setCelda(i, j, A.getM()[j][i]);
                    }
            }
            return transpuesta;
    }

    /**
     * Compara las dimensiones de dos Matrices. <br>
     * @param A Matriz a comparar.
     * @param B Matriz a comparar.
     * @return si estas matrices poseian las mismas dimensiones o no.
     */
    public boolean mismasDim(Matriz A, Matriz B) {
            return (A.getFilas() == B.getFilas() && A.getColumnas() == B.getColumnas());
    }
    
    /**
     * Compara las matrices para evaluar si son multiplicables o no. <br>
     * @param A Matriz a comparar.
     * @param B Matriz a comparar.
     * @return si estas matrices son multiplicables o no.
     */
    public  boolean multiplicable(Matriz A, Matriz B) {
            return (A.getFilas() == B.getColumnas() && A.getColumnas() == B.getFilas());
    }

}