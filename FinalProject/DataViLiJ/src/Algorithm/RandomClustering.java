package Algorithm;

import dataProcessors.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomClustering extends ClusteringAlgorithm{

    private Data data;
    private List<String> randomLabels;

    private static final Random RANDOM =new Random();
    private boolean firstRun;
    public RandomClustering(Data data){
        this.data=data;
        this.configuration=new Configuration();
        this.randomLabels= new ArrayList<>();
        configuration.NumberOfClustering =data.getLabels().size();
        firstRun=true;
        setNumberOfCluster();

    }
    @Override
    public void run() {
        if (firstRun){
            for(int i=1;i<=configuration.NumberOfClustering;i++){
                randomLabels.add(Integer.toString(i));
            }
            firstRun=false;
        }
        assignLabels();

    }

    private String getRandomLabels(){
        int RandomIndex = RANDOM.nextInt(randomLabels.size());
        return randomLabels.get(RandomIndex);
    }

    private void assignLabels(){
        for (String key: data.getDataLabels().keySet()) {
            String newLabel =getRandomLabels();
            data.getDataLabels().put(key,newLabel);
        }
    }

    @Override
    public Data getOutput() {
        return data;
    }
}
