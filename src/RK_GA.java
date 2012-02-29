import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Joost
 */
public class RK_GA implements GeneticStrategy {

	private double c1, c2, c3;
	private final int MATINGPAIRS;
	private final double MUTATIONRATE;
	
	public RK_GA() {
		c1 = 1; //correctness
		c2 = 1; //examplecount
		c3 = 1; //attributecount
		MATINGPAIRS = 20;
		MUTATIONRATE = 0.02;
	}
	
    public double fitness(Specimen s) {
		int Er = s.getIncorrectSamples();
		int Ne = s.getExamplesCount();
		int Na = s.getAttributeCount();
    	return 1 / (c1 * Er + c2 * Ne + c3 * Na);
    }

    public void iterate(ArrayList<Specimen> population) {
    	for (int i = 0; i < MATINGPAIRS; i++) {
	        Specimen s1 = selectRandomSpecimen(population);
	        Specimen s2 = selectRandomSpecimen(population);
	        
	        //Allereerst gaan we de afstammelingen A en B bepalen door two-point crossover
	        Random r = new Random();
	        //Deze willekeurige getallen geven de bounds van substrings aan binnen de chromosomen van s1 en s2
	        int r1a1 = 0, r1a2 = 0, r2a1 = 0, r2a2 = 0, r1e1 = 0, r1e2 = 0, r2e1 = 0, r2e2 = 0;
	        if (s1.getAttributeCount() > 0) {
				r1a1 = r.nextInt(s1.getAttributeCount());
				r1a2 = r.nextInt(s1.getAttributeCount());
				r1e1 = r.nextInt(s1.getAttributeCount());
				r1e2 = r.nextInt(s1.getAttributeCount());
	        }
	        if (s2.getAttributeCount() > 0) {
				r2a1 = r.nextInt(s2.getAttributeCount());
				r2a2 = r.nextInt(s2.getAttributeCount());
				r2e1 = r.nextInt(s2.getAttributeCount());
				r2e2 = r.nextInt(s2.getAttributeCount());
	        }
	        ArrayList<Integer> A_att = twoPointCrossover(s1.getAttributes(), s2.getAttributes(), Math.min(r1a1, r1a2), Math.max(r1a1, r1a2), Math.min(r2a1, r2a2), Math.max(r2a1, r2a2));
	        ArrayList<Integer> B_att = twoPointCrossover(s2.getAttributes(), s1.getAttributes(), Math.min(r2a1, r2a2), Math.max(r2a1, r2a2), Math.min(r1a1, r1a2), Math.max(r1a1, r1a2));
	        ArrayList<Integer> A_exmp = twoPointCrossover(s1.getExamples(), s2.getExamples(), Math.min(r1e1, r1e2), Math.max(r1e1, r1e2), Math.min(r2e1, r2e2), Math.max(r2e1, r2e2));
	        ArrayList<Integer> B_exmp = twoPointCrossover(s2.getExamples(), s1.getExamples(), Math.min(r2e1, r2e2), Math.max(r2e1, r2e2), Math.min(r1e1, r1e2), Math.max(r1e1, r1e2));
	        
	        population.add(mutate(new Specimen(A_att, A_exmp, s1.getSamples())));
	        population.add(mutate(new Specimen(B_att, B_exmp, s1.getSamples())));
    	}
    	//sorteren en weggooien van de eerste MATINGPAIRS*2 elementen, met de laagste fitness
    	Collections.sort(population, new FitnessComparator());
    	population.subList(0, MATINGPAIRS*2).clear();
    }
    
    public Specimen getFittestSpecimen(ArrayList<Specimen> population) {
    	Collections.sort(population, new FitnessComparator());
    	return population.get(population.size()-1);
    }

	protected class FitnessComparator implements Comparator<Specimen> {

		public int compare(Specimen o1, Specimen o2) {
			if (fitness(o1) < fitness(o2))
				return -1;
			return (fitness(o1) == fitness(o2) ? 0 : -1);
		}
		
    }
	
	protected Specimen mutate(Specimen s) {
    	mutateChromosome(s.getAttributes(), s.getSampleAttributeCount());
    	mutateChromosome(s.getExamples(), s.getSampleCount());
		return s;
	}
    
	protected void mutateChromosome(ArrayList<Integer> c, int total) {
    	Random r = new Random();
		for (int i = 0; i < Math.ceil(c.size() * MUTATIONRATE); i++) {
			int r2 = r.nextInt(c.size());
			int n = r.nextInt(c.size());
			c.set(r2, (c.get(r2) + n) % total);
		}
		sortAndUnique(c);
    }
	
	protected void sortAndUnique(ArrayList<Integer> c) {
		Collections.sort(c);
		Iterator<Integer> it = c.iterator();
		int prev = -1, curr = -1;
		while (it.hasNext()) {
			curr = it.next();
			if (curr == prev) {
				it.remove();
			}
			prev = curr;
		}
	}
	
	protected ArrayList<Integer> twoPointCrossover(ArrayList<Integer> a, ArrayList<Integer> b, int a1, int a2, int b1, int b2) {
    	ArrayList<Integer> r = new ArrayList<Integer>();
        r.addAll(a.subList(0, a1));
        r.addAll(b.subList(b1, b2));
        r.addAll(a.subList(a2, a.size()));
        sortAndUnique(r);
        return r;
    }
    
    /**
     * Deze functie levert een willekeurige Specimen op uit de populatie,
     * volgens de niet-uniforme distributie van fitness
     * @param population de populatie van specimen
     * @return een willekeurige specimen
     */
	protected Specimen selectRandomSpecimen(ArrayList<Specimen> population) {
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
