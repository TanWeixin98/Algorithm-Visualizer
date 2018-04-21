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
        for (int i = 1; i <= configuration.MaxInterval && configuration.continous; i++) {
            int xCoefficient = new Double(RAND.nextDouble() * 100).intValue();
            int yCoefficient = new Double(RAND.nextDouble() * 100).intValue();
            int constant     = new Double(RAND.nextDouble() * 100).intValue();

            // this is the real output of the classifier
            output = Arrays.asList(xCoefficient, yCoefficient, constant);

            // everything below is just for internal viewing of how the output is changing
            // in the final project, such changes will be dynamically visible in the UI
            if (i % configuration.IterationInterval == 0) {
                System.out.printf("Iteration number %d: ", i); //
                flush();
            }
            if (i > configuration.MaxInterval * .6 && RAND.nextDouble() < 0.05) {
                System.out.printf("Iteration number %d: ", i);
                flush();
                break;
            }
        }
    }

    // for internal viewing only
    protected void flush() {
        System.out.printf("%d\t%d\t%d%n", output.get(0), output.get(1), output.get(2));
    }

}


