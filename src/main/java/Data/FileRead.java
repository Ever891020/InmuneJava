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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ever
 */
public class FileRead {
    
    public void leer_alineadas()
    {
        int cont;
        //Arreglo para ubicar la posicion de las columnas de interés
        ArrayList arregloTab=new ArrayList();
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
                //Si se lee la linea del titulo de columnas
                if(titulo==0)
                {
                    arregloTab.add(buscarTab("Sequence ID",linea));
                    arregloTab.add(buscarTab("N1-REGION start",linea));
                    arregloTab.add(buscarTab("N1-REGION end",linea));
                    //jijitl
                    
                    //Se cambia el valor para solo hacerlo una vez
                    titulo=1;
                }
                
                
            }
        }catch(IOException e){
            System.out.println("Error E/S: "+e);
        }        
    }
    
    //Función para buscar la posicion del tabulador de interes, retorna la posición
    public int buscarTab(String columna,String linea)
    {
        
        return 0;
    }
}
