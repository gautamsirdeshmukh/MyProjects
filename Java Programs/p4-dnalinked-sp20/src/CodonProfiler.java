import java.util.*;

public class CodonProfiler {
	
	/**
	 * Count how many times each codon in an array of codons occurs
	 * in a strand of DNA. Return int[] such that int[k] is number
	 * of occurrences of codons[k] in strand. Strand codons can start
	 * at all valid indexes that are multiples of 3: 0, 3, 6, 9, 12, 15, ...
	 * @param strand is DNA to be analyzed for codon occurrences.
	 * @param codons is an array of strings, each has three characters
	 * @return int[] such that int[k] is number of occurrences of codons[k] in 
	 * strand. 
	 */
	public int[] getCodonProfile(IDnaStrand strand, String[] codons) {
		HashMap<String,Integer> map = new HashMap<>();
		int[] ret = new int[codons.length];
				
		for(int k=0; k < strand.size() -2; k+=3) {
            String sub = strand.toString().substring(k, k + 3);
            if (!(map.containsKey(sub))) {
                map.put(sub, 0);
            }
            map.put(sub, map.get(sub) + 1);
        }
		/*
        System.out.println(strand);
		System.out.println(map);
		 */

		for (int i=0; i < codons.length; i++)
		{
		    if (map.containsKey(codons[i]))
            {
                ret[i] = map.get(codons[i]);
            }
		    else
            {
                ret[i] = 0;
            }
		}
		/*
		for (int l = 0; l < ret.length; l++)
		{
		    System.out.println(codons[l]);
		    System.out.println(ret[l]);
		}
		 */
		return ret;
	}
}
