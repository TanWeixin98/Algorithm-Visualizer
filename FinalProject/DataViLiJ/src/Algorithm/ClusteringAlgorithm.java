package Algorithm;

import java.util.HashSet;

public class ClusteringAlgorithm implements AlgorithmType {
    private HashSet<ClusteringAlgorithm> algorithmList;
    public ClusteringAlgorithm(){
        algorithmList=new HashSet<>();
    }
    public HashSet<ClusteringAlgorithm> getAlgorithmList(){
        return algorithmList;
    }

    @Override
    public String toString(){
        return "cluster";
    }
}
