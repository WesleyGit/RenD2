import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joost
 */
public class RK_GA_CorrectMyWrongs extends ModifiedRK_GA implements GeneticStrategy {
    
	@Override
	public void iterate(ArrayList<Specimen> population) {
		Collections.sort(population, new FitnessComparator());
		ArrayList<Specimen> toptier = (ArrayList<Specimen>) population.clone();
		toptier.subList(0, toptier.size()/2).clear();
		ArrayList<Specimen> popclone = (ArrayList<Specimen>) population.clone();
		for (Specimen s:toptier) {
			double sumOfCMWDist = 0;
			int[] distlist = new int[popclone.size()]; 
			int i = 0;
			for (Specimen p:popclone) {
				distlist[i] = s.errorDist(p);
				sumOfCMWDist += distlist[i];
				i++;
			}
			Random r = new Random();
	    	double rd = r.nextDouble();
	    	double n = 0;
	    	i = -1;
	    	do {
	    		i++;
	    		n += distlist[i] / sumOfCMWDist;
	    	} while (n < rd);
	    	Specimen s2 = population.get(i);
	        Specimen[] specs = twoPointCrossOver(s, s2);
	        population.add(mutate(specs[0]));
	        population.add(mutate(specs[1]));
		}
    	//sorteren en weggooien van de eerste MATINGPAIRS*2 elementen, met de laagste fitness
    	Collections.sort(population, new FitnessComparator());
    	population.subList(0, toptier.size()*2).clear();
    }
	
	
}
