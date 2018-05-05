package dataProcessors;

import Algorithm.AlgorithmType;
import Algorithm.ClusteringAlgorithm;
import Algorithm.Configuration;
import javafx.scene.chart.XYChart;
import ui.AppUI;
import vilij.templates.ApplicationTemplate;

public class ClusteringProcessor extends Thread implements DataProcessor {
    ApplicationTemplate applicationTemplate;

    private ClusteringAlgorithm clusteringAlgorithm;
    private Configuration configuration;
    private int currentIteration;
    private boolean running;
    private XYChart<Number, Number> chart;
    private Data data;
    public ClusteringProcessor(AlgorithmType clusteringAlgorithm, XYChart<Number,Number> chart,
                               Data data, ApplicationTemplate applicationTemplate){
        this.clusteringAlgorithm=(ClusteringAlgorithm)clusteringAlgorithm;
        this.configuration=clusteringAlgorithm.getConfiguration();
        this.chart=chart;
        this.applicationTemplate=applicationTemplate;
        running=true;
        currentIteration=0;
        start();
    }
    @Override
    public void run(){
        update();
        ((AppUI)applicationTemplate.getUIComponent()).disableScrnShotButton(false);
    }

    @Override
    public void update() {
        if(configuration.continous){
            for(int i=1;i<=configuration.MaxInterval;i++){
                try{
                    sleep(2000);
                }catch (InterruptedException e){
                    //Do Nothing
                }
            }
        }else {

        }
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
