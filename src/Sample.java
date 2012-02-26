/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Joost
 */
public class Sample {
    
	private ArrayList<Double> attributes;
	private int classification;
	private final int no;
	
    public Sample(ArrayList<Double> attributes, int classification, int no) {
        this.attributes = attributes;
        this.classification = classification;
        this.no = no;
    }
    
    /**
     * Berekent simpelweg de Eucledian distance tussen twee lijsten met attributes
     * @param s het andere punt waartot de afstand moet worden berekend 
     * @param att de attributen die gebruikt mogen worden
     * @return de afstand tussen de twee samples
     */
    public double distance(Sample s, ArrayList<Integer> att) {
    	if (s.attributes.size() != attributes.size()) {
    		System.out.println("Attribute counts do not match");
    		return Double.MAX_VALUE;
    	}
    	double dist = 0;
    	Iterator<Integer> it = att.iterator();
    	while (it.hasNext()) {
    		int a = it.next();
    		dist += Math.pow(Math.abs(attributes.get(a) - s.attributes.get(a)),2); 
    	}
    	return Math.sqrt(dist);
    }

	public int getClassification() {
    	return classification;
    }
    
    public int getAttributeCount() {
    	return attributes.size();
    }
    
    public String toString() {
    	return "Sample " + no;
    }
}
