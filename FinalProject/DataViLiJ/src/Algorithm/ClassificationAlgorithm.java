package Algorithm;




import vilij.propertymanager.PropertyManager;

import java.util.ArrayList;
import java.util.HashSet;

public class ClassificationAlgorithm implements AlgorithmType {
    private ArrayList<ClassificationAlgorithm> algorithmList;
    private ArrayList<Configuration>            configurationListForAlgorithm;
    public ClassificationAlgorithm(){
        algorithmList= new ArrayList<>();
    }

    public ArrayList<ClassificationAlgorithm> getAlgorithmList(){
        return algorithmList;
    }

    @Override
    public void testAdd() {

    }

    @Override
    public String toString(){
        return "randomClassification";
        //return PropertyManager.getManager().getPropertyValue();
    }
}
