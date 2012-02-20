import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Joost
 */
public class Specimen {
    
	public static final int K = 3;
	
    private final ArrayList<Integer> attributes;
    private final ArrayList<Integer> examples;
    private final ArrayList<Sample> samples;
    private int correct;

    public Specimen (ArrayList<Integer> attributes, ArrayList<Integer> examples, ArrayList<Sample> samples) {
        this.attributes = attributes;
        this.examples = examples;
        this.samples = samples;
        correct = 0;
        try {
	        for (Sample sample:this.samples) {
	        	ArrayList<Sample> referenceSet = new ArrayList<Sample>();
	        	/* TODO dit moet efficienter kunnen, want examples en samples zijn allebei gesorteerd,
	        	dus dat algoritme van bij Complexiteit met O(k * n) ipv O(n^2) werkt hier.
	        	Maar nu even geen zin in.*/
	        	for (Integer example:examples) {
	        		Sample t = samples.get(example);
	        		t.setTmpDistance(sample.distance(t));
	        		referenceSet.add(t);
	        	}
	        	//worden dus gesorteerd op distance, want Sample implementeert het Comparable-interface.
	        	Collections.sort(referenceSet);
	        	//alleen de eerste K elementen overhouden (door sublist te maken en die te clearen)
	        	referenceSet.subList(K, referenceSet.size()).clear();
	        	int maxfreq = 0;
	        	int maxclass = -1;
	        	//om alle classes af te lopen
	        	for (Sample s:referenceSet) {
	        		int freq = 0;
	        		for (Sample s2:referenceSet) {
	        			if (s.getClassification() == s2.getClassification()) {
	        				freq++;
	        			}
	        		}
	        		if (freq > maxfreq) {
	        			maxclass = s.getClassification();
	        		}
	        	}
	            if (sample.getClassification() == maxclass) {
	                correct++;
	            }
	        }
	    }
        catch (Exception e1) {
			e1.printStackTrace();
		}
    }
    
    public ArrayList<Integer> getExamples() {
        return examples;
    }
    
    public ArrayList<Integer> getAttributes() {
        return attributes;
    }
    
    public int getSampleCount() {
    	return samples.size();
    }
    
    public int getAttributeCount() {
    	return attributes.size();
    }
    
    public int getCorrectSamples() {
        return correct;
    }
    

}
