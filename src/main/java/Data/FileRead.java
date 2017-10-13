/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ever
 */
public class FileRead {
    
    public void readFile()
    {
        //Arreglo para ubicar la posicion de las columnas de interés
        List<List<String>> listaM=new ArrayList();
        ArrayList pos=new ArrayList();
        try(BufferedReader br=new BufferedReader(new FileReader("3_Nt-sequences.txt"));)
        {
            //Bandera para solo identificar los titulos de columnas
            int titulo=0;
            //Variable para guardar la linea leida
            String linea;
            //Ciclo para recorrrer el archivo a leer
            while(br.ready())
            {
                //Se lee la linea
                linea=br.readLine();
                //Se convierte a arreglo la linea leida
                String[] fila_tab=linea.split("\t");
                //Se crea la lista
                List<String> fila=new ArrayList();
                //Se convierte el arreglo a lista
                fila=Arrays.asList(fila_tab);
                //Se buscan las columnas por nombre en la primera linea
                if(titulo==0)
                {
                  //Se obtiene el arreglo con las posiciones de las columnas de interes
                  pos=posColumnas(pos,fila);
                  //Se cambia el valor de la bandera para solo evaluar el encabezado
                  titulo=1;
                }
                //Se agrega la lista a la lista de listas
                listaM.add(fila);
            }
            System.out.println(listaM.size());
        }catch(IOException e){
            System.out.println("Error E/S: "+e);
        }        
    }
    
    //Funcion para obtener la posicion de las columnas de interes
    public ArrayList posColumnas(ArrayList pos,List<String> fila)
    {
        int aux = fila.indexOf("N1-REGION start");
        pos.add(aux);
        aux=fila.indexOf("N1-REGION end");
        pos.add(aux);
        aux = fila.indexOf("N2-REGION start");
        pos.add(aux);
        aux=fila.indexOf("N2-REGION end");
        pos.add(aux);
        aux=fila.indexOf("P3'V start");
        pos.add(aux);
        aux=fila.indexOf("P3'V end");
        pos.add(aux);
        aux=fila.indexOf("P5'D start");
        pos.add(aux);
        aux=fila.indexOf("P5'D end");
        pos.add(aux);
        aux=fila.indexOf("P3'D start");
        pos.add(aux);
        aux=fila.indexOf("P3'D end");
        pos.add(aux);
        aux=fila.indexOf("P5'J start");
        pos.add(aux);
        aux=fila.indexOf("P5'J end");
        pos.add(aux);
        return pos;
    }
    
    //Función para buscar la posicion del tabulador de interes, retorna la posición
    public void mostrarFila(List<String> fila)
    {
        for (int i = 0; i < fila.size(); i++) 
        {          
            System.out.print(fila.get(i)+"\t");
        }
        System.out.println();
    }
}
