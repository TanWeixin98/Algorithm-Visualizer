package Algorithm;

import dataProcessors.Data;

public class RandomClustering extends ClusteringAlgorithm{

    private Data data;

    public RandomClustering(Data data){
        super();
        this.data=data;
        this.configuration=new Configuration();
        setNumberOfCluster();
    }
    @Override
    public void run() {

    }

    @Override
    Data getOutput() {
        return null;
    }
}
