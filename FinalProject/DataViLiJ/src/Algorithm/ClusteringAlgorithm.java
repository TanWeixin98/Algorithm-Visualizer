package Algorithm;

import dataProcessors.Data;
import settings.AppPropertyTypes;
import vilij.propertymanager.PropertyManager;

import java.util.ArrayList;
import java.util.List;

public abstract class ClusteringAlgorithm implements AlgorithmType {
    public enum Clusters{
        RandomClustering, KMeansClusterer
    }

    protected Configuration configuration;

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

}
