package Algorithm;




import vilij.propertymanager.PropertyManager;

import java.util.HashSet;

public class ClassificationAlgorithm implements AlgorithmType {
    private HashSet<ClassificationAlgorithm> algorithmList;
    public ClassificationAlgorithm(){
        algorithmList= new HashSet<>();
    }

    public HashSet<ClassificationAlgorithm> getAlgorithmList(){
        return algorithmList;
    }
    @Override
    public String toString(){
        return "Classficiation";
        //return PropertyManager.getManager().getPropertyValue();
    }
}
