package Algorithm;

public class Configuration {
    private int MaxInterval, IterationInterval;
    private boolean continous;
    private AlgorithmType algorithm;

    public Configuration(int MaxInterval, int IterationInterval, boolean continous, AlgorithmType algorithm ){
        this.MaxInterval= MaxInterval;
        this.IterationInterval = IterationInterval;
        this.continous=continous;
        this.algorithm=algorithm;

    }
}
