/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
            createResults(ga.findFittestClassifier(), trainset);
        }
        catch ( IOException ioe )
        {
            System.out.println("Het bestand train.txt kon niet worden gelezen. Error: " + ioe.getMessage() );
        }
    }

	private ArrayList<Specimen> population;
	private GeneticStrategy strategy;
	private static final int ITERATIONS = 10;
	private static final int POPSIZE = 50;
	
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
	
    private Specimen findFittestClassifier() {
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
    	Specimen s = strategy.getFittestSpecimen(population);
    	System.out.println("The fittest classifier is:\n"+s);
    	System.out.println("With a fitness of: "+strategy.fitness(s));
    	return s;
	}
    
	private static void createResults(Specimen classifier, ArrayList<Sample> trainset) {
		try {
			 	// Maken van prototype-file
				  FileWriter ofstream = new FileWriter("prototypes.txt");
				  BufferedWriter out = new BufferedWriter(ofstream);
				  for (int e : classifier.getExamples()) {
					  Sample s = trainset.get(e);
					  Iterator<Integer> iteratorA = classifier.getAttributes().iterator();
					  int a = iteratorA.hasNext() ? iteratorA.next() : -1;
					  for (int i = 0; i < s.getAttributeCount(); i++) {
						  if (a > i) {
							  continue;
						  }
						  else if (a == i) {
							  out.write(s.getAttributeValue(i)+" ");
							  a = iteratorA.hasNext() ? iteratorA.next() : -1;
						  }
					  }
					  out.write(s.getClassification()+"\n");
				  }
				  //Sluiten van schrijven naar prototype-file
				  out.close();
		 }
		 catch (Exception e) {//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		 }
		 
		ArrayList<Sample> testset = new ArrayList<Sample>();
		try {
			  FileReader ifstream = new FileReader("test.txt");
			  Scanner scan = new Scanner( ifstream );
			  String line;
				int no = 0;
				System.out.println("Start reading testset");
				while (scan.hasNextLine()) {
					line = scan.nextLine();
					String[] input = line.split(" ");
					ArrayList<Double> att = new ArrayList<Double>();
					for (int i = 0; i < input.length; i++) {
						att.add(Double.valueOf(input[i]));
					}
				    testset.add(new Sample(att, classifier.computeClassification(att), no++));
				}
				ifstream.close();
		}
		catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		 
		System.out.println("Start writing predictions");
		try {
			// Maken van prediction-file
			FileWriter ofstream = new FileWriter("prediction.txt");
			BufferedWriter out = new BufferedWriter(ofstream);
			for (Sample s : testset) {
				out.write(s.getClassification()+"\n");
			}
			//Sluiten van schrijven naar prediction-file
			out.close();
		}
		catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
		System.out.println("Done!");
	}
    
}
