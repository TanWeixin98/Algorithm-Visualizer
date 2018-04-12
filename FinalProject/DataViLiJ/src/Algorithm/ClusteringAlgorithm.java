package Algorithm;

import java.util.List;

public class ClusteringAlgorithm implements AlgorithmType {
    private List<ClusteringAlgorithm> algorithmList;

    public List<ClusteringAlgorithm> getAlgorithmList(){
        return algorithmList;
    }

    @Override
    public String toString(){
        return "cluster";
    }
}
