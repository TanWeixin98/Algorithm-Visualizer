package Algorithm;

public class Configuration {
    public int MaxInterval, IterationInterval, NumberOfClustering;
    public boolean continous;
    public Configuration(){}

    public Configuration(int MaxInterval, int IterationInterval, boolean continous){
        this.MaxInterval= MaxInterval;
        this.IterationInterval = IterationInterval;
        this.continous=continous;
    }


}
