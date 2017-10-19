/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Alignment;

import java.util.List;
import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.SimpleGapPenalty;
import org.biojava.nbio.core.alignment.matrices.SubstitutionMatrixHelper;
import org.biojava.nbio.core.alignment.template.Profile;
import org.biojava.nbio.core.alignment.template.SequencePair;
import org.biojava.nbio.core.alignment.template.SubstitutionMatrix;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.util.ConcurrencyTools;

/**
 *
 * @author ever
 */
public class Alineamientos {
    
    public double alinearGlobal(String query,String target) throws CompoundNotFoundException
    {
        DNASequence queryDNA = new DNASequence(query,  
               AmbiguityDNACompoundSet.getDNACompoundSet());
        
        DNASequence targetDNA = new DNASequence(target,  
                AmbiguityDNACompoundSet.getDNACompoundSet());

        SubstitutionMatrix <NucleotideCompound> matrix = SubstitutionMatrixHelper.getNuc4_4();  
        SimpleGapPenalty gapP = new SimpleGapPenalty();  
        gapP.setOpenPenalty((short)10);  
        gapP.setExtensionPenalty((short)4);
        
        SequencePair <DNASequence, NucleotideCompound> psa =
                Alignments.getPairwiseAlignment(queryDNA, targetDNA,
                Alignments.PairwiseSequenceAlignerType.GLOBAL, gapP, matrix);
        
        return (((double)psa.getNumIdenticals()/(double)query.length())*100);
    }

    public int alignmentM (List<DNASequence> lista) throws CompoundNotFoundException
    {
       
        Profile<DNASequence, NucleotideCompound> profile = null;
        profile = Alignments.getMultipleSequenceAlignment(lista);
        String identificador= lista.get(0).getOriginalHeader();
        //Corto de la primera coma hacia adelante
        String grupo=identificador.substring(0,identificador.indexOf(";"));
        //Corto de la segunda coma hacia delante y obtengo genero--dominio
        //String coma2=coma1.substring(coma1.indexOf(",")+1);    
        ConcurrencyTools.shutdown();
        return 0;
    }
    
}
