package Algorithm;

import dataProcessors.Data;

public class RandomClustering extends ClusteringAlgorithm{

    private Data data;

    public RandomClustering(Data data){
        this.data=data;
        this.configuration=new Configuration();
    }
    @Override
    public void run() {

    }
}
