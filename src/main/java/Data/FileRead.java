/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ever
 */
public class FileRead {
    
    public void leer_alineadas()
    {
        String aux="";
        int cont=1,tam=0,id=1;
        Map<String, String> grupo = new HashMap<String, String>();
        try(BufferedReader br=new BufferedReader(new FileReader("3_Nt-sequences.txt"));)
        {  
            String etiqueta = null;
            String linea;
            while(br.ready())
            {
                
            }
        }catch(IOException e){
            System.out.println("Error E/S: "+e);
        }        
    }
}
