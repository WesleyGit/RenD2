/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joost
 */
public class ModifiedRK_GA extends RK_GA {
    
	/**
	 * De enige functie die een override behoeft is de fitnessfunctie
	 */
	@Override
	public double fitness(Specimen s) {
		int Er = s.getIncorrectSamples();
		int Ne = s.getExamplesCount();
		int Na = s.getAttributeCount();
		int Net = s.getSampleCount();
		int Nat = s.getSampleAttributeCount();
		//Double-cast om integerdeling te voorkomen
		double c1 = (double)(Net + 1) / (2 * Net + Nat + 3);
		double c2 = c1;
		double c3 = (double)(Nat + 1) / (2 * Net + Nat + 3);
		return 1 / (c1 * (Net - Er) / Net + c2 * (Net - Ne) / (Net - 1) + c3 * (Nat - Na) / (Nat - 1));
    }
	
}
