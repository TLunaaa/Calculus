package modelo;
public class Matriz {
        private String nombre="";
        private String descripcion;
        private int filas,columnas;
        private int[][] m;
    
        public Matriz(int filas, int columnas) {
            this.filas = filas;
            this.columnas = columnas;
            m = new int[filas][columnas];
        }
    
        public String getNombre() {
            return nombre;
        }
    
        public String getDescripcion() {
            return descripcion;
        }
    
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    
        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    
        public int[][] getM() {
                return m;
        }
    
        public int getFilas() {
                return filas;
        }
    
        public void setFilas(int filas) {
                this.filas = filas;
        }
    
        public int getColumnas() {
                return columnas;
        }
    
        public void setColumnas(int columnas) {
                this.columnas = columnas;
        }
        
        public void setCelda(int i, int j, int valor) {
                this.m[i][j] = valor;
        }
        
        public int getCelda(int i, int j) {
                return this.m[i][j];
        }
    
        public void inicializar() {
                for(int i = 0; i < this.getFilas(); i++) {
                        for(int j = 0; j < this.getColumnas(); j++) {
                                this.setCelda(i, j, 0);
                        }
                }
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
}
