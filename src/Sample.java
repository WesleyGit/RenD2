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
     * @param secondatts Een lijst met attributes evenlang als die van Sample
     * @return de distance als double
     * @throws Exception wordt gegooid als de lijsten geen overeenkomstige lengte hebben
     */
    public double distance(Sample s) {
    	if (s.attributes.size() != attributes.size()) {
    		System.out.println("Attribute counts do not match");
    		return Double.MAX_VALUE;
    	}
    	double dist = 0;
    	Iterator<Double> it1 = s.attributes.iterator();
    	Iterator<Double> it2 = attributes.iterator();
    	while (it1.hasNext()) {
    		dist += Math.pow(Math.abs(it1.next() - it2.next()),2);
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
