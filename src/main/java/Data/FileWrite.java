/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ever
 */
public class FileWrite {
    
    public void writeSubseq8(List<List<String>> listaM,String nombre) {
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(nombre+".txt",true));)
        {
            for (int i = 0; i < listaM.size(); i++) 
            {
                for (int j = 0; j < listaM.get(i).size(); j++) 
                {
                    bw.write(listaM.get(i).get(j)+"\t");
                }
                bw.newLine();
            }
            //Guardamos los cambios del fichero
            bw.flush();
                      
        }catch(IOException e){
            System.out.println("Error E/S: "+e);
        }
    }
    
}
