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
public class Sample implements Comparable<Sample> {
    
	private ArrayList<Double> attributes;
	private int classification;
	private double tmpdistance = 0;
	
    public Sample(ArrayList<Double> attributes, int classification) {
        this.attributes = attributes;
        this.classification = classification;
    }
    
    /**
     * Berekent simpelweg de Eucledian distance tussen twee lijsten met attributes
     * @param secondatts Een lijst met attributes evenlang als die van Sample
     * @return de distance als double
     * @throws Exception wordt gegooid als de lijsten geen overeenkomstige lengte hebben
     */
    public double distance(Sample s) throws Exception {
    	if (s.attributes.size() != attributes.size()) {
    		throw new Exception("Attribute counts do not match");
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
    
    public double getTmpDistance() {
    	return tmpdistance;
    }
    
    public void setTmpDistance(double d) {
    	tmpdistance = d;
    }

	public int compareTo(Sample arg0) {
		if (tmpdistance < arg0.getTmpDistance())
			return -1;
		return (tmpdistance == arg0.getTmpDistance() ? 0 : -1);
	}
    
}
