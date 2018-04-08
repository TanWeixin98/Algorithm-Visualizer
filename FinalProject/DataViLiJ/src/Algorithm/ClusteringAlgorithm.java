package Algorithm;

import java.util.List;

public class ClusteringAlgorithm implements AlgorithmType {
    private List<ClusteringAlgorithm> algorithmList;

    public ClusteringAlgorithm(List<ClusteringAlgorithm> algorithmList) {
        this.algorithmList = algorithmList;
    }

    public List<ClusteringAlgorithm> getAlgorithmList(){
        return algorithmList;
    }
}
