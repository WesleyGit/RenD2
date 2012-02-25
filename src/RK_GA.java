/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author Joost
 */
public class RK_GA implements GeneticStrategy {

	private double c1, c2, c3;
	private final int MATINGPAIRS;
	
	public RK_GA() {
		c1 = 0.6;
		c2 = 0.4;
		c3 = 0.7;
		MATINGPAIRS = 10;
	}
	
    public double fitness(Specimen s) {
    	return 1 / (c1 * s.getIncorrectSamples() + c2 * s.getExamplesCount() + c3 * s.getAttributeCount());
    }

    public void iterate(ArrayList<Specimen> population) {
    	for (int i = 0; i < MATINGPAIRS; i++) {
	        Specimen s1 = selectRandomSpecimen(population);
	        Specimen s2 = selectRandomSpecimen(population);
	        
	        //Allereerst gaan we de afstammelingen A en B bepalen door two-point crossover
	        Random r = new Random();
	        	//Deze willekeurige getallen geven de substrings aan binnen de chromosomen van s1 en s2
	        int r1a1 = r.nextInt(s1.getAttributeCount());
	        int r1a2 = r.nextInt(s1.getAttributeCount());
	        int r1e1 = r.nextInt(s1.getAttributeCount());
	        int r1e2 = r.nextInt(s1.getAttributeCount());
	        int r2a1 = r.nextInt(s2.getAttributeCount());
	        int r2a2 = r.nextInt(s2.getAttributeCount());
	        int r2e1 = r.nextInt(s2.getAttributeCount());
	        int r2e2 = r.nextInt(s2.getAttributeCount());
	        
	        ArrayList<Integer> A_att = twoPointCrossover(s1.getAttributes(), s2.getAttributes(), r1a1, r1a2, r2a1, r2a2);
	        ArrayList<Integer> B_att = twoPointCrossover(s2.getAttributes(), s1.getAttributes(), r2a1, r2a2, r1a1, r1a2);
	        ArrayList<Integer> A_exmp = twoPointCrossover(s1.getExamples(), s2.getExamples(), r1e1, r1e2, r2e1, r2e2);
	        ArrayList<Integer> B_exmp = twoPointCrossover(s2.getExamples(), s1.getExamples(), r2e1, r2e2, r1e1, r1e2);
	        
	        population.add(new Specimen(A_att, A_exmp, s1.getSamples()));
	        population.add(new Specimen(B_att, B_exmp, s1.getSamples()));
    	}
    	//sorteren en weggooien van de eerste MATINGPAIRS*2 elementen, met de laagste fitness
    	Collections.sort(population, new FitnessComparator());
    	population.subList(0, population.size()-MATINGPAIRS*2).clear();
    }
    
    private class FitnessComparator implements Comparator<Specimen> {

		public int compare(Specimen o1, Specimen o2) {
			if (fitness(o1) < fitness(o2))
				return -1;
			return (fitness(o1) == fitness(o2) ? 0 : -1);
		}
		
    }
    
    private ArrayList<Integer> twoPointCrossover(ArrayList<Integer> a, ArrayList<Integer> b, int a1, int a2, int b1, int b2) {
    	ArrayList<Integer> r = new ArrayList<Integer>();
        r.addAll(a.subList(0, a1));
        r.addAll(b.subList(b1, b2));
        r.addAll(a.subList(a2, a.size()));
        return r;
    }
    
    /**
     * Deze functie levert een willekeurige Specimen op uit de populatie,
     * volgens de niet-uniforme distributie van fitness
     * @param population de populatie van specimen
     * @return een willekeurige specimen
     */
    private Specimen selectRandomSpecimen(ArrayList<Specimen> population) {
    	double sumOfFitness = 0;
    	for (Specimen s : population) {
    		sumOfFitness += fitness(s);
    	}
    	Random r = new Random();
    	double rd = r.nextDouble();
    	double n = 0;
    	int i = -1;
    	do {
    		i++;
    		n += fitness(population.get(i)) / sumOfFitness;
    	} while (n < rd);
    	return population.get(i);
    }
    
}
