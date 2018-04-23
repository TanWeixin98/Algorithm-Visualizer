package dataProcessors;

import Algorithm.ClusteringAlgorithm;
import Algorithm.Configuration;
import javafx.scene.chart.XYChart;

public class ClusteringProcessor extends Thread implements DataProcessor {
    private ClusteringAlgorithm clusteringAlgorithm;
    private Configuration configuration;
    private int currentIteration;
    private XYChart<Number, Number> chart;
    public ClusteringProcessor(ClusteringAlgorithm clusteringAlgorithm, XYChart<Number,Number> chart){
        this.clusteringAlgorithm=clusteringAlgorithm;
        this.configuration=clusteringAlgorithm.getConfiguration();
        this.chart=chart;
        currentIteration=0;
        start();
    }
    @Override
    public void run(){

    }
    @Override
    public void update() {

    }
}
