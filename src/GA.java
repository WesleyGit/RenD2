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
        }
        catch ( IOException ioe )
        {
            System.out.println("Het bestand train.txt kon niet worden gelezen. Error: " + ioe.getMessage() );
        }
    }
}
