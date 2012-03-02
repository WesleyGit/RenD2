import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Joost
 */
public class Specimen {
    
	public static final int K = 1;
	
    private final ArrayList<Integer> attributes;
    private final ArrayList<Integer> examples;
    private final ArrayList<Sample> samples;
    private final ArrayList<Boolean> errorvector;
    private int correct;

    /**
     * Deze constructor moet een willekeurige lijst met attributes en examples genereren
     * @param samples
     */
    public Specimen (ArrayList<Sample> samples) {
    	this.examples = new ArrayList<Integer>();
    	Random r = new Random();
    	for (int i = 0; i < samples.size(); i++) {
    		if (r.nextBoolean()) {
    			examples.add(i);
    		}
    	}
    	this.attributes = new ArrayList<Integer>();
    	for (int i = 0; i < samples.get(0).getAttributeCount(); i++) {
    		if (r.nextBoolean()) {
    			attributes.add(i);
    		}
    	}
        this.samples = samples;
        this.errorvector = new ArrayList<Boolean>();
        this.correct = findCorrectCount();
    }
    
    public Specimen (ArrayList<Integer> attributes, ArrayList<Integer> examples, ArrayList<Sample> samples) {
        this.attributes = attributes;
        this.examples = examples;
        this.samples = samples;
        this.errorvector = new ArrayList<Boolean>();
        this.correct = findCorrectCount();
    }
    
    private int findCorrectCount() {
    	int correct = 0;
        for (Sample sample:this.samples) {
        	int c = computeClassification(sample);
            if (sample.getClassification() == c) {
                correct++;
                errorvector.add(false);
            }
            else {
            	errorvector.add(true);
            }
        }
        return correct;
    }
    
    public int computeClassification(ArrayList<Double> att) {
    	return computeClassification(new Sample(att, 0, 0));
    }
    
    public int computeClassification(Sample sample) {
    	ArrayList<Sample> referenceSet = new ArrayList<Sample>();
    	/* TODO dit moet efficienter kunnen, want examples en samples zijn allebei gesorteerd,
    	dus dat algoritme van bij Complexiteit met O(k * n) ipv O(n^2) werkt hier.
    	Maar nu even geen zin in.
    	EDIT 29/2: Wes claimt dat dit niet efficienter kan, en ik zie 't eerlijk gezegd ook niet meer..*/
    	for (Integer example:examples) {
    		Sample t = samples.get(example);
    		referenceSet.add(t);
    	}
    	//we gebruiken de hieronder gedefineerde DistanceComparator om te sorteren op afstand tot sample
    	Collections.sort(referenceSet, new DistanceComparator(sample));
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
    			maxfreq = freq;
    			maxclass = s.getClassification();
    		}
    	}
    	return maxclass;
    }
    
    public class DistanceComparator implements Comparator<Sample> {

    	private Sample origin;
    	
    	public DistanceComparator(Sample origin) {
    		this.origin = origin;
    	}
    	
		public int compare(Sample o1, Sample o2) {
			if (origin.distance(o1, attributes) < origin.distance(o2, attributes))
				return -1;
			return (origin.distance(o1, attributes) == origin.distance(o2, attributes) ? 0 : 1);
		}
		
    }
    
    public ArrayList<Integer> getExamples() {
        return examples;
    }
    
    public ArrayList<Integer> getAttributes() {
        return attributes;
    }
    
    public ArrayList<Sample> getSamples() {
        return samples;
    }
    
    public int getExamplesCount() {
    	return examples.size();
    }
    
    public int getAttributeCount() {
    	return attributes.size();
    }
    
    public int getCorrectSamples() {
        return correct;
    }
    
    public int getIncorrectSamples() {
        return samples.size() - correct;
    }

	public int getSampleAttributeCount() {
		return samples.get(0).getAttributeCount();
	}

	public int getSampleCount() {
		return samples.size();
	}
	
	@Override
	public String toString() {
		return "Specimen details:\nAttributes used ["+attributes.size()+"]: "+attributes+"\nexamples used ["+examples.size()+"]: "+examples+"\nCorrectly classified: "+correct;
	}

	public int errorDist(Specimen s) {
		int d = 0;
		for (int i = 0; i < errorvector.size(); i++) {
			if (errorvector.get(i) == true && s.errorvector.get(i) == false) {
				d++;
			}
		}
		return d;
	}
}
