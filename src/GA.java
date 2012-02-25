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
        try
        {
            FileReader file = new FileReader("train.txt");
            Scanner scan = new Scanner( file );
            String line;
            while (scan.hasNextLine()) {
            	line = scan.nextLine();
            	String[] input = line.split(" ");
            	ArrayList<Double> att = new ArrayList<Double>();
            	for (int i = 0; i < input.length - 1; i++) {
            		att.add(Double.valueOf(input[i]));
            	}
                trainset.add(new Sample(att, Integer.getInteger(input[input.length])));
            }
            file.close();
            GA ga = new GA(trainset, new RK_GA());
            ga.findFittestClassifier();
        }
        catch ( IOException ioe )
        {
            System.out.println("Het bestand train.txt kon niet worden gelezen. Error: " + ioe.getMessage() );
        }
    }
	
	private ArrayList<Sample> trainset;
	private ArrayList<Specimen> population;
	private GeneticStrategy strategy;
	private static final double THRESHOLD = 0.9;
	private static final int POPSIZE = 100;
	
	public GA(ArrayList<Sample> trainset, GeneticStrategy strategy) {
		this.trainset = trainset;
		this.strategy = strategy;
		population = new ArrayList<Specimen>();
		for (int i = 0; i < POPSIZE; i++) {
			population.add(new Specimen(trainset));
		}
	}
	
    private void findFittestClassifier() {
	
	}
    
}
