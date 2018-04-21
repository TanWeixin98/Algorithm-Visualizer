package Algorithm;




import dataProcessors.Data;
import settings.AppPropertyTypes;
import sun.security.krb5.Config;
import vilij.propertymanager.PropertyManager;

import java.util.ArrayList;
import java.util.List;

public class ClassificationAlgorithm implements AlgorithmType {
    protected List<Integer> output;
    protected Configuration configuration;
    private ArrayList<ClassificationAlgorithm> algorithmList;
    public ClassificationAlgorithm(){
        algorithmList= new ArrayList<>();
    }


    public ArrayList<ClassificationAlgorithm> getAlgorithmList(){
        return algorithmList;
    }

    @Override
    public Configuration getConfiguration(){return configuration;}

    @Override
    public void setAlgorithmList(Data data) {
        algorithmList.add(new RandomClassification(data));
    }


    @Override
    public String toString(){
        return PropertyManager.getManager().getPropertyValue(AppPropertyTypes.CLASSIFICATION_TYPE.name());
    }
}
