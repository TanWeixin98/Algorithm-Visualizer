package Algorithm;

import dataProcessors.Data;

import java.util.Arrays;
import java.util.Random;

public class RandomClassification extends ClassificationAlgorithm{
    private static final Random RAND = new Random();

    @SuppressWarnings("FieldCanBeLocal")
    // this mock classifier doesn't actually use the data, but a real classifier will
    private Data data;

    public RandomClassification(Data data) {
        this.data = data;
        this.configuration=new Configuration();
    }
    public String toString(){
        return "Random Classification"; //TODO
    }

    public void run() {
        int xCoefficient = new Double(RAND.nextDouble() * 100).intValue();
        int yCoefficient = new Double(RAND.nextDouble() * 100).intValue();
        int constant     = new Double(RAND.nextDouble() * 100).intValue();
        // this is the real output of the classifier
        output = Arrays.asList(xCoefficient, yCoefficient, constant);
    }


}


