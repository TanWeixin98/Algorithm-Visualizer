package Algorithm;

public class Configuration {
    public int MaxInterval, IterationInterval, ConfigurationLabel;
    public boolean continous;
    private AlgorithmType algorithm;
    public Configuration(){
        MaxInterval=10;
        IterationInterval=2;
        continous=false;

    }
    public Configuration(int MaxInterval, int IterationInterval, boolean continous){
        this.MaxInterval= MaxInterval;
        this.IterationInterval = IterationInterval;
        this.continous=continous;
    }
    public void setAlgorithm(AlgorithmType algorithm){
        this.algorithm = algorithm;
    }

}
