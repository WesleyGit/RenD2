/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package ga;

import java.util.ArrayList;

/**
 *
 * @author Joost
 */
public interface GeneticStrategy {
    
    public double fitness(Specimen s);
    public ArrayList<Specimen> iterate(ArrayList<Specimen> population);
    
}
