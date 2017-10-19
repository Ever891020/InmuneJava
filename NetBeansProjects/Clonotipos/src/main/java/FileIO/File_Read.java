/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIO;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

/**
 *
 * @author ever
 */
public class File_Read {
    
    //Funcion para leer cargar las secuencias del archivo fasta a una lista del tipo DNASequence
    public LinkedHashMap<String, DNASequence> leerDNAfasta(String ruta) throws IOException, CompoundNotFoundException, SQLException, ClassNotFoundException 
    {                 
        LinkedHashMap<String, DNASequence> a = new LinkedHashMap<>();
    try {
            //Se crea el objeto tipo file, recibe la ruta del archivo fasta
            File file = new File(ruta);
            //Se carga el contenido del archivo en la lista LinkedHashMap a
            a = FastaReaderHelper.readFastaDNASequence(file);
                     
        } catch (IOException e) {
            // TODO Auto-generated catch block

        }
            //proceso.leerMapDNA(a);
            return a;
    }
    
}
