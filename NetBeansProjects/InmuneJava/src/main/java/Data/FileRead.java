/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author ever
 */
public class FileRead {
    //Arreglo para ubicar la posicion de las columnas de interés
        List<List<String>> listaM=new ArrayList<>();
    
    public void readFile()
    {
        FileWrite file=new FileWrite();
        ArrayList pos=new ArrayList();
        int nLinea=1;
        try(BufferedReader br=new BufferedReader(new FileReader("3_Nt-sequences_total.txt"));)
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
                    fila.add("Qua-1/0");
                    fila.add("Qua-start");
                    fila.add("Qua-end");
                    fila.add("Qua-seq");
                }
                else
                {
                    //Se agregan los nuevos valores de longitud
                    fila=calLong(fila,pos);
                    //Se buscan los quadruplex en V-D-J Region
                    fila=findQuadruplex(fila, (int) pos.get(pos.size()-1));
                    //Se enumera la linea
                    fila.set(0, nLinea+"");
                    nLinea++;
                }
                
                //Se agrega la lista a la lista de listas
                listaM.add(fila);
            }
            //file.writeSubseq8(listaM, "datosTotales");
            file.writeExcel(listaM, "/home/ever/datosTotales");
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
        int pos1,pos2,cont=2;
        String seq=fila.get(pos),aux;
        Pattern pat = Pattern.compile("(g{3,}.{1,7}){3,}g{3}");
        Matcher mat = pat.matcher(seq);
        if(mat.find())
        {
            pos1=mat.start();
            pos2=mat.end();
            aux=seq.substring(pos1, pos2);
            fila.add("1");
            fila.add(pos1+"");
            fila.add(pos2+"");
            fila.add(aux);
            while(mat.find())
            {
                listaM.get(0).add("Qua-"+cont+"-start");
                listaM.get(0).add("Qua-"+cont+"-end");
                listaM.get(0).add("Qua-"+cont+"-seq");
                pos1=mat.start();
                pos2=mat.end();
                aux=seq.substring(pos1, pos2);
                fila.add(pos1+"");
                fila.add(pos2+"");
                fila.add(aux);
                cont++;
            }
            
        }        
        else
        {
            fila.add("0");
            fila.add("0");
            fila.add("0");
            fila.add("-");
        }
        return fila;
    }
    
    public List<List<String>> leerExcel(String nombre) throws FileNotFoundException, IOException
    {
        List<List<String>> listaT=new ArrayList<>();
        FileInputStream file = new FileInputStream(new File(nombre));
        // Crear el objeto que tendra el libro de Excel
	HSSFWorkbook workbook = new HSSFWorkbook(file);
        //Se carga la hoja 1
        HSSFSheet sheet = workbook.getSheetAt(0);
        //Iterador para recorrer las filas de la hoja
	Iterator<Row> rowIterator = sheet.iterator();
        //Se crea un objeto de fila
        Row row;
	
	// Recorremos todas las filas para guardar el contenido de cada celda	
	while (rowIterator.hasNext())
        {	
	    row = rowIterator.next();	
	    // Obtenemos el iterator que permite recorres todas las celdas de una fila	
	    Iterator<Cell> cellIterator = row.cellIterator();
            //Se crea un objeto de celda
	    Cell celda;
            List<String> fila=new ArrayList<>();
            //Se recorre cada fila
	    while (cellIterator.hasNext())
            {
		celda = cellIterator.next();
                fila.add(celda.getStringCellValue());
            }
            listaT.add(fila);
            System.out.println(listaT.get(0).get(0));
            fila.clear();
        }
        for (int i = 0; i < listaT.size(); i++) 
        {
            for (int j = 0; j < listaT.get(i).size(); j++) 
            {
                System.out.println(listaT.get(i).get(j));
            }
            
        }
        return listaT;
    }
    public void calcularQuaxP() throws IOException
    {
        FileRead read=new FileRead();
        List<List<String>> lista=new ArrayList();
        lista=read.leerExcel("/home/ever/Documentos/datosTotales.xls");
        List<List<String>> QUA1=new ArrayList();
        int posP=lista.get(0).indexOf("Sequence ID");
        int posQUA=lista.get(0).indexOf("Qua-1/0");
        int cont=1, positivo=0;
//        for (int i = 1; i < lista.size(); i++) 
//        {
//            if(lista.get(i).get(posP).contains("00"+cont))
//            {
//                if(lista.get(i).get(posQUA).compareTo("1")==0)
//                {
//                    positivo++;
//                }
//                                
//            }
//            else
//            {
//                List<String> fila=new ArrayList();
//                fila.add(positivo+"");
//                QUA1.add(fila);
//                fila.clear();
//                positivo=0;
//                cont++;
//                if(lista.get(i).get(posP).contains("00"+cont))
//                {
//                    if(lista.get(i).get(posQUA).compareTo("1")==0)
//                    {
//                        positivo++;
//                    }
//                }
//            }
//        }
//        System.out.println(QUA1.size());
    }
    //Función para buscar la posicion del tabulador de interes, retorna la posición
    public void mostrarFila()
    {
        for (int i = 0; i < listaM.size(); i++) 
        {
            for (int j = 0; j < listaM.get(i).size(); j++) 
            {
                System.out.print(listaM.get(i).get(j)+"\t");
            }
            System.out.println();
        }
    }
}
