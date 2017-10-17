/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
    
    public void writeExcel(List<List<String>> listaM,String nombre) throws FileNotFoundException, IOException
    {
        //Se crea el libro de trabajo
        HSSFWorkbook workbook = new HSSFWorkbook();
        //Se crea la hoja de trabajo
        HSSFSheet sheet = workbook.createSheet("3_NT");
        //Se recorre la lista para escribir el archivo de excel
        for (int i = 0; i < listaM.size(); i++) 
        {
            //Se crea una fila
            HSSFRow row = sheet.createRow(i);
            for (int j = 0; j < listaM.get(i).size(); j++) 
            {
                //Se crea una celda
                HSSFCell cell = row.createCell(j);
                //Se asigna el valor a la celda creada
                cell.setCellValue(listaM.get(i).get(j));                
            }                                    
        }        
        //Se guarda el excel creado
        OutputStream out = new FileOutputStream(nombre+".xls");
        workbook.write(out);
    }
    
}
