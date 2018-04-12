package Algorithm;

import java.util.ArrayList;

public class ClusteringAlgorithm implements AlgorithmType {
    private class RandomCluster extends ClusteringAlgorithm{
        private RandomCluster(){}
        public String toString(){
            return "random";
        }
    }
    private ArrayList<ClusteringAlgorithm> algorithmList;
    private ArrayList<Configuration>        configurationListForAlgorithm;
    public ClusteringAlgorithm() {
        algorithmList= new ArrayList<>();
//        for (Configuration configuration : configurationListForAlgorithm) {
//            configuration= new Configuration(1,1,false);
//        }
    }
    public void testAdd(){
        while(algorithmList.size()<4)
        algorithmList.add(new RandomCluster());}


    public ArrayList<ClusteringAlgorithm> getAlgorithmList(){
        return algorithmList;
    }

    @Override
    public String toString(){
        return "cluster";
    }
}
