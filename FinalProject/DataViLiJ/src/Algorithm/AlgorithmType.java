package Algorithm;


import dataProcessors.Data;

import java.util.ArrayList;

public interface AlgorithmType {
    ArrayList<? extends AlgorithmType> getAlgorithmList();
    void setAlgorithmList(Data data);
    Configuration getConfiguration();
}
