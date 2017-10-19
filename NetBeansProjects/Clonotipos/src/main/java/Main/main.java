/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import Alignment.Alineamientos;
import Process.Procesos;
import java.io.IOException;
import java.sql.SQLException;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
/**
 *
 * @author ever
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CompoundNotFoundException, IOException, SQLException, ClassNotFoundException 
    {
        // TODO code application logic here
//        Alineamientos alinear=new Alineamientos();
//        String query="CAGGTGCAGCTTCAGGAGTGGGGCGCAGGACTGTTGAAGCCCTCGGAGACCCTGTCCCTCACCTGCGCTGTCTCTGGTGGGTCTTTCAGTGGTTATTATTGGACCTGGATCCGCCAGCCCCCAGGAAAGGGGCTGGAGTGGATTGGGCAGATCAATGATAGTGGAAGTACCCGGTCCAATCCGTCCCTGAAGAGTCGCGTCGCCATATTCGTCGCCACGTCGAAGAATCAAATCTCCCTGAGTCTGAGCTCTGTGACCGCCGCGGACACGGCTGTGTATTACTGTGCGCGAGGGACATATTCCTATGATAATAGTGATTCTTCCTACCGTGGTGGGTTTTTCTACTTTGACTACTGGGGCCAGGGAAATCTGGTCACCGTCTCCTCAGCCTCCACCAAGGGCCCAT";
//        
//        String target="CAGGTGCAGCTTCAGGAGTGGGGCGCAGGACTGTTGAAGCCCTCGGAGACCCTGTCCCTCACCTGCGCTGTCTCTGGTGGGTCTTTCAGTGGTTATTATTGGACCTGGATCCGCCAGCCCCCAGGAAAGGGGCTGGAGTGGATTGGGCAGATCAATGATAGTGGAAGTACCCGGTCCAATCCGTCCCTGAAGAGTCGCGTCGCCATATTCGTCGCCACGTCGAAGAATCAAATCTCCCTGAGTCTGAGCTCTGTGACCGCCGCGGACACGGCTGTGTATTACTGTGCGCGAGGGACATATTCCTATGATAATAGTGATTCTTCCTACCGTGGTGGGTTTTTCTACTTTGACTACTGGGGCCAGGGAAATCTGGTCACCGTCTCCTCAGCCTCCACCAAGGGCCCA";
//        alinear.alinearGlobal(query, target);
        
        Procesos proceso=new Procesos();
        proceso.cargarDirectorioFasta("archivos", "G");
        
        
    }
    
}
