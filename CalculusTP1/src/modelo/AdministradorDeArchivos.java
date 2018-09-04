package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;


public class AdministradorDeArchivos {
    public AdministradorDeArchivos() {
        super();
    }
    
    public static Matriz cargaMatriz(String nombremat) throws TError {
        String nombrearch = null;
        String descripcion = null;
        int i,j,filas,columnas;
        String fichero = "Datos/"+ nombremat + ".mat";        
        Scanner s,sl;
        try {
            String linea;
            File f = new File(fichero);
            s = new Scanner(f);
            sl = null;
            //
            if(s.hasNextLine()){
                nombrearch = s.nextLine();                
                if(s.hasNextLine()){
                    descripcion = s.nextLine();
                }
            }
            sl = new Scanner(s.nextLine());
            sl.useDelimiter(",");
            filas = sl.nextInt();
            columnas = sl.nextInt();
            Matriz mcargada = new Matriz(filas,columnas);
            mcargada.setDescripcion(descripcion);
            mcargada.setNombre(nombrearch);
            for(i=0;i<filas;i++){
                if(s.hasNextLine()){
                    linea = s.nextLine();
                    sl = new Scanner(linea);
                    sl.useDelimiter(",");
                    for(j=0;j<columnas;j++){
                        if(sl.hasNextInt()){
                            mcargada.setCelda(i, j, sl.nextInt());
                        }else{
                            throw new TError(6);
                        }
                    }  
                }
                    //throw new IOException();
            }
            sl.close();
            s.close();
            return mcargada;
        } catch (FileNotFoundException e) {
            throw new TError(3);
        }
    }
    
    public static void guardaMatriz(Matriz mat) throws TError{
        FileWriter fwArchivo;
        File flArchivo;
        BufferedWriter bwEscritor;
        flArchivo=new File("Datos/"+ mat.getNombre() + ".mat");
        int j;
        if(flArchivo.exists())
            throw new TError(5);
        try{
            fwArchivo=new FileWriter(flArchivo);
            //Se va escribiendo cada linea en el archivo de texto.
            fwArchivo.write(mat.getNombre() + "\r\n" );
            fwArchivo.write(mat.getDescripcion() + "\r\n" );
            fwArchivo.write(mat.getFilas()+","+mat.getColumnas() + "\r\n" );
            for(int i=0;i<mat.getFilas();i++){
                for(j=0;j<mat.getColumnas()-1;j++){
                    fwArchivo.write(mat.getCelda(i, j) + "," );
                }
                fwArchivo.write(mat.getCelda(i, j) + "\r\n" );
            }
            //Este metodo escribe el archivo en el disco duro.
            bwEscritor=new BufferedWriter(fwArchivo);
            bwEscritor.close();//Se cierra el archivo.              
        }catch(IOException ex){
            
        }
    }
}
