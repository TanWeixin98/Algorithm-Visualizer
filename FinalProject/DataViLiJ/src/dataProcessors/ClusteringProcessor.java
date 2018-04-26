package dataProcessors;

import Algorithm.ClusteringAlgorithm;
import Algorithm.Configuration;
import javafx.scene.chart.XYChart;

public class ClusteringProcessor extends Thread implements DataProcessor {
    private ClusteringAlgorithm clusteringAlgorithm;
    private Configuration configuration;
    private int currentIteration;
    private boolean running;
    private XYChart<Number, Number> chart;
    public ClusteringProcessor(ClusteringAlgorithm clusteringAlgorithm, XYChart<Number,Number> chart){
        this.clusteringAlgorithm=clusteringAlgorithm;
        this.configuration=clusteringAlgorithm.getConfiguration();
        this.chart=chart;
        running=true;
        currentIteration=0;
        start();
    }
    @Override
    public void run(){

    }
    @Override
    public void update() {
    }

    @Override
    public boolean CheckState() {
        return isAlive();
    }

    @Override
    public void terminate(){
        running=false;
    }
}
