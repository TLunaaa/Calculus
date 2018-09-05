package modelo;
/**
 * Representa una matriz. <br>
 * filas >= 0 <br>
 * columnas >= 0 <br>
 */
public class Matriz {
    /**
     * nombre de la Matriz.
     */ 
    private String nombre="";

    /**
     * breve descripcion de la Matriz.
     */
    private String descripcion;

    /**
     * dimensiones de la Matriz (filas x columnas).
     */
    private int filas,columnas;

    /**
     * matriz donde se almacenan los datos enteros.
     */
    private int[][] m;

    /**
     * Crea una matriz. <br>
     * <b>post </b> Se crea la Matriz de dimension filas x columnas.
     * @param filas cantidad de filas de la Matriz. - filas!=null y filas!=0.
     * @param columnas cantidad de columnas de la Matriz. columnas!=null y columnas!=0
     */
    public Matriz(int filas, int columnas) {
            this.filas = filas;
            this.columnas = columnas;
            m = new int[filas][columnas];
            verificarInvariante();
        }

    /**
     * @return el nombre de la Matriz.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return la descripcion de la Matriz.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece el nombre de la Matriz.
     * @param nombre que se quiere asignar.- nombre !="" y nombre!=null
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
        verificarInvariante();
    }
    
    /**
     * Establece la descripcion de la Matriz.
     * @param descripcion que se quiere asignar.- descripcion !="" y descripcion!=null
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        verificarInvariante();
    }

    /**
     * @return los datos de la matriz.
     */
    public int[][] getM() {
        return m;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
        verificarInvariante();
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
        verificarInvariante();
    }
    
    public void setCelda(int i, int j, int valor) {
        this.m[i][j] = valor;
        verificarInvariante();
    }
    
    public int getCelda(int i, int j) {
        return this.m[i][j];
    }
    
    /**
     * Inicializa la matriz en ceros.
     */
    public void inicializar() {
            for(int i = 0; i < this.getFilas(); i++) {
                    for(int j = 0; j < this.getColumnas(); j++) {
                            this.setCelda(i, j, 0);
                    }
            }
        verificarInvariante();
    }
    
    @Override
    public String toString() {
        int i,j;
        String aux = "" ;
        for(i=0;i<filas;i++){
            for(j=0;j<columnas;j++){
                aux += " " + m[i][j];
            }  
            aux += "\n";
        }
        return aux;
    }
    
    private void verificarInvariante(){
        assert this.filas >= 0: "Cantidad de filas incorrecta.";
        assert this.columnas >= 0: "Cantidad de columnas incorrecta.";
        //assert !this.nombre.equals(""): "Nombre invalido";
        //assert !this.descripcion.equals(""): "Descripcion invalida";
    }
}
