/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import Alignment.Alineamientos;
import FileIO.File_Read;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;

/**
 *
 * @author ever
 */
public class Procesos {
    
    //Lista de mapas para guardar los archivos fasta
    List<LinkedHashMap<String,DNASequence>> list=new ArrayList<>();
    //Lista de listas de grupos formados
    List<List<DNASequence>> listaGrupos=new ArrayList();
    
    
    //Funcion para cargar el directorio con los archivos fasta
    public void cargarDirectorioFasta(String ruta,String cadena) throws IOException, CompoundNotFoundException, SQLException, ClassNotFoundException
    {   
        //Se crea el objeto del tipo File_Read para cargar los archivos fasta
        File_Read archivo=new File_Read();
        //Se crea el objeto tipo file para cargar los nombres de los archivos
        File f = new File(ruta);
        if (f.exists())
        { // Directorio existe
            //Se carga a un arreglo los ficheros del directorio
            File[] ficheros = f.listFiles();
            //Se ordena de menor a mayor los ficheros
            Arrays.sort(ficheros);
            //Ciclo para recorrer los ficheros en el arreglo
            for (int x=0;x<ficheros.length;x++)
            {
                //Se crea un mapa por cada fichero
                //LinkedHashMap<String, DNASequence> a=new LinkedHashMap<>();
                //Se evaluan por tipo de cadena
                String nombreArchivo=ficheros[x].getName();
                if(nombreArchivo.contains(cadena))
                {
                    //Se asigna el contenido del archivo fasta al mapa
                    LinkedHashMap<String, DNASequence> a=archivo.leerDNAfasta(ruta+"/"+ficheros[x].getName());
                    //Se guarda el mapa en la lista de mapas
                    list.add(a);
                }
                
            }            
        }
        else 
        { //Directorio no existe 
            
            System.out.println("El directorio no existe, revisa la ruta de entrada");                
        }
        recorrerListaMapas();
    }
    //Funcion para recorrer la lista de mapas
    public void recorrerListaMapas() throws IOException, CompoundNotFoundException, SQLException, ClassNotFoundException
    {
        boolean grupos=false;
        //Ciclo para recorrer la lista y enviar dos listas a evaluar
        for (int i = 0; i < list.size(); i++) 
        {
            //Si ya esta creada la lista de grupos
            if(i>0)
            {
                //Se cambia a verdadero para evaluar la nueva lista de grupos
                grupos=true;
            }
            //Ciclo para obtener el siguiente elemento de la lista
            for (int j = 0; j < list.size(); j++) 
            {
                //Si el elemento es diferente, se comparan
                if(i<j)
                {
                    //Se envian las posiciones de la lista global y si evaluará la lista de grupos formada
                    recorrerMapas(i,j,grupos);
                }
            }                        
        }
    }
    //Lista para recorrer Mapas a comparar
    public void recorrerMapas(int i,int j,boolean grupos) throws CompoundNotFoundException
    {
        //Se crea el objeto para alinear las secuencias
        Alineamientos alinear=new Alineamientos();
        
        //Bandera para saber si secuencia i se puede eliminar
        boolean eliminarI=false;
        
        //Si ya se evaluó el primer archivo con todos
        if(grupos)
        {
            //Si tiene elementos en la lista de grupos
            if(listaGrupos.size()>0)
            {
                //Se crea el iterador para recorrer el primer mapa i
                Iterator<String> it = list.get(j).keySet().iterator();
                while(it.hasNext())
                {
                    //Obtengo los datos de la secuencia del mapa i
                    String idj=it.next();
                    DNASequence idSeqj=list.get(j).get(idj);
                    String identificadorj= idSeqj.getOriginalHeader();
                    //--------------------------//
                    
                    //Ciclo para recorrer la lista de listas
                    for (int k = 0; k < listaGrupos.size(); k++) 
                    {                        
                        //Se alinean las dos secuencias, la del archivo y solo la primera de cada lista
                        if(alinear.alinearGlobal(idSeqj.getSequenceAsString(),listaGrupos.get(k).get(0).getSequenceAsString()))
                        {
                            //Si pasa el filtro de identidad se agrega a la lista de grupos
                            listaGrupos.get(k).add(idSeqj);
                            //Se elimina la secuencia del archivo
                            it.remove();
                        }
                    }
                    
                }                                                               
            }
        }
        
        //Se crea el iterador para recorrer el primer mapa i
        Iterator<String> iti = list.get(i).keySet().iterator();
        
        //Ciclo para recorrer el mapa i
        while(iti.hasNext())
        {
            //Obtengo los datos de la secuencia del mapa i
            String idi=iti.next();
            DNASequence idSeqi=list.get(i).get(idi);
            String identificadori= idSeqi.getOriginalHeader();
            //--------------------------//
            
            //Se crea el iterador del mapa j
            Iterator<String> itj = list.get(j).keySet().iterator();
            
            //Se crea el mapa para guardarla en el lista de grupos
            List<DNASequence> a=new ArrayList <>();
            //Ciclo para recorrer el mapa j
            while(itj.hasNext())
            {
                //Obtengo los datos de la secuencia del mapa i
                String idj=itj.next();
                DNASequence idSeqj=list.get(j).get(idj);
                String identificadorj= idSeqj.getOriginalHeader();
                //--------------------------//
                
                //Se alinean las dos secuencias
                if(alinear.alinearGlobal(idSeqi.getSequenceAsString(),idSeqj.getSequenceAsString()))
                {
                    eliminarI=true;
                    //Si el mapa esta vacio, agrego las dos secuencias, una del archivo i y la j
                    if(a.isEmpty())
                    {
                        //Agrego las dos secuencias
                        a.add(idSeqi);
                        a.add(idSeqj);
                        
                        //Elimino la secuencia j
                        itj.remove();
                        
                    }
                    else
                    {
                        //Agrego la secuencia del archivo j que cumple con el filtro
                        a.add(idSeqj);
                        
                        //Elimino la secuencia del j
                        itj.remove();
                        
                    }
                }                
            }
            
            //Si la secuencia i pasa el filtro de identidad
            if(eliminarI)
            {
                listaGrupos.add(a);
                System.out.println(listaGrupos.size());
                //Se elimina la secuencia i
                iti.remove();
                //Se reinicia la bandera
                eliminarI=false;
            }
        }        
    }    
}
