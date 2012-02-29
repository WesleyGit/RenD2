import java.util.ArrayList;

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
	protected Specimen[] selectSpecimen(ArrayList<Specimen> population) {
		Specimen[] specs = new Specimen[2];
		specs[0] = selectRandomSpecimen(population);
		specs[1] = selectRandomSpecimen(population);
		return specs;
	}
	
}
