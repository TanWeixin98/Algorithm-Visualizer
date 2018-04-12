package Algorithm;




import vilij.propertymanager.PropertyManager;

import java.util.List;

public class ClassificationAlgorithm implements AlgorithmType {
    private List<ClassificationAlgorithm> algorithmList;

    public List<ClassificationAlgorithm> getAlgorithmList(){
        return algorithmList;
    }
    @Override
    public String toString(){
        return "Classficiation";
        //return PropertyManager.getManager().getPropertyValue();
    }
}
