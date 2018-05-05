package Algorithm;




import dataProcessors.Data;
import settings.AppPropertyTypes;
import sun.security.krb5.Config;
import vilij.propertymanager.PropertyManager;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassificationAlgorithm implements AlgorithmType {
    enum classifer{
        RandomClassification
    }
    protected List<Integer> output;
    protected Configuration configuration;

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    public List<Integer> getOutput() {
        return output;
    }

}
