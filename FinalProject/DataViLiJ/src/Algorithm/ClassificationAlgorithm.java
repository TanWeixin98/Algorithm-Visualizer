package Algorithm;


import java.util.List;

public class ClassificationAlgorithm implements AlgorithmType {
    private List<ClassificationAlgorithm> algorithmList;

    public ClassificationAlgorithm(List<ClassificationAlgorithm> algorithmList) {
        this.algorithmList = algorithmList;
    }
    public List<ClassificationAlgorithm> getAlgorithmList(){
        return algorithmList;
    }
}
