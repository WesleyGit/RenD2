/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author Joost
 */
public class Specimen {
    
    private ArrayList<Integer> attributes;
    private ArrayList<Integer> examples;
    private ArrayList<Sample> samples;
    
    public Specimen (ArrayList<Integer> a, ArrayList<Integer> e, ArrayList<Sample> s) {
        attributes = a;
        examples = e;
        samples = s;
    }
    
    public ArrayList<Integer> getExamples() {
        return examples;
    }
    
    public ArrayList<Integer> getAttributes() {
        return attributes;
    }
    
    public int getCorrectSamples() {
        int total = 0;
        for (Sample sample:samples) {
            if (sample.isCorrect()) {
                total++;
            }
        }
        return total;
    }
    

}
