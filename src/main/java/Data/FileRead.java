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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                //String[] fila_tab=linea.split("\t");
                //Se crea la lista
                List<String> fila=new ArrayList<>();
                //Se convierte el arreglo a lista
                fila.addAll(Arrays.asList(linea.split("\t")));
                
                //Se buscan las columnas por nombre en la primera linea
                if(titulo==0)
                {
                    //Se obtiene el arreglo con las posiciones de las columnas de interes
                    pos=posColumnas(pos,fila);
                    //Se cambia el valor de la bandera para solo evaluar el encabezado
                    titulo=1;
                    //Se agregan las nuevas columnas
                    fila.add("N1-length");
                    fila.add("N2-length");
                    fila.add("P3'V-length");
                    fila.add("P5'D-length");
                    fila.add("P3'D-length");
                    fila.add("P5'J-length");           
                }
                else
                {
                    //Se agregan los nuevos valores de longitud
                    fila=calLong(fila,pos);
                    //Se buscan los quadruplex en V-D-J Region
                    fila=findQuadruplex(fila, (int) pos.get(pos.size()-1));
                }
                //Se agrega la lista a la lista de listas
                listaM.add(fila);
            }
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
        aux=fila.indexOf("V-D-J-REGION");
        pos.add(aux);
        return pos;
    }
    
    //Funcion para calcular la longitud de regiones
    public List<String> calLong(List<String> fila,ArrayList pos)
    {
        int val1=0,val2=0,pos1,pos2,aux=0;
        String v1,v2;
        for (int i = 0; i < pos.size()-1; i++) 
        {
            pos1=(int) pos.get(i);
            pos2=(int) pos.get(i+1);
            i++;
            v1=fila.get(pos1);
            v2=fila.get(pos2);
            if(v1.compareTo("")!=0 || v2.compareTo("")!=0)
            {
                val1=Integer.parseInt(v1);
                val2=Integer.parseInt(v2);
                aux=(val2-val1)+1;
                fila.add(aux+"");
            }
            else
            {
                fila.add(0+"");
            }
            
        }
        return fila;
    }
    
    //Funcion para buscar quadruplex
    public List<String> findQuadruplex(List<String> fila,int pos)
    {
        int i=1;
        String seq=fila.get(pos);
        Pattern pat = Pattern.compile("(g{3,}.{1,7}){3,}g{3}");
        Matcher mat = pat.matcher(seq);
        while(mat.find())
        {
            System.out.print(mat.start()+" ");
                        System.out.println(mat.end());
                        System.out.println("");
        }
                        
        if (mat.matches()) 
        {   
            System.out.println("SI");
            System.out.println(seq);
        }
        return fila;
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
