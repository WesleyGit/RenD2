/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Joost
 */
public class GA {

	public static void main(String[] args)
    {
        ArrayList<Sample> trainset = new ArrayList<Sample>();
		//Proberen het bestand in te lezen..
        try
        {
            //FileReader file = new FileReader("trainmnist.txt");
        	FileReader file = new FileReader("train.txt");
            Scanner scan = new Scanner( file );
            String line;
            int no = 0;
            System.out.println("Start reading");
            while (scan.hasNextLine()) {
            	line = scan.nextLine();
            	String[] input = line.split(" ");
            	ArrayList<Double> att = new ArrayList<Double>();
            	for (int i = 0; i < input.length - 1; i++) {
            		att.add(Double.valueOf(input[i]));
            	}
                trainset.add(new Sample(att, Integer.valueOf(input[input.length-1]), no++));
            }
            file.close();
            System.out.println("Done reading");
            GA ga = new GA(trainset, new ModifiedRK_GA());
            ga.findFittestClassifier();
        }
        catch ( IOException ioe )
        {
            System.out.println("Het bestand train.txt kon niet worden gelezen. Error: " + ioe.getMessage() );
        }
    }
	
	private ArrayList<Specimen> population;
	private GeneticStrategy strategy;
	private static final int ITERATIONS = 50;
	private static final int POPSIZE = 100;
	
	public GA(ArrayList<Sample> trainset, GeneticStrategy strategy) {
		this.strategy = strategy;
		population = new ArrayList<Specimen>();
		System.out.println("Constructing");
		for (int i = 0; i < POPSIZE; i++) {
			//System.out.println(i);
			population.add(new Specimen(trainset));
		}
		System.out.println("Done constructing");
	}
	
    private void findFittestClassifier() {
    	System.out.println("Start iterating..");
    	for (int i = 0; i < ITERATIONS; i++) {
    		//System.out.println(i);
    		strategy.iterate(population);
    		// test om te kijken of er wel elementen met 2 attributes in de pop zitten
    		/*int n = 0;
    		for (int j = 0; j < population.size(); j++)
    			if (population.get(j).getAttributes().size() == 2)
    				n++;
    		System.out.println(n);*/
    	}
    	System.out.println("The fittest classifier is:\n"+strategy.getFittestSpecimen(population));
    	System.out.println("With a fitness of: "+strategy.fitness(strategy.getFittestSpecimen(population)));
	}
    
}
