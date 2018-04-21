package Algorithm;

import dataProcessors.Data;
import settings.AppPropertyTypes;
import vilij.propertymanager.PropertyManager;

import java.util.ArrayList;

public class ClusteringAlgorithm implements AlgorithmType {
//    private class Cluster1 extends ClusteringAlgorithm{
//        Configuration configuration;
//        private Cluster1(){
//            configuration=new Configuration();
//        }
//
//        public Configuration getConfiguration() {
//            return configuration;
//        }
//
//        public String toString(){
//            return " Cluster Algorithm 1";
//        }
//    }
//    private class Cluster2 extends ClusteringAlgorithm{
//        Configuration configuration;
//        private Cluster2(){
//            configuration=new Configuration();
//        }
//
//        public Configuration getConfiguration() {
//            return configuration;
//        }
//
//        public String toString(){
//            return " Cluster Algorithm 2";
//        }
//    }
    private ArrayList<ClusteringAlgorithm> algorithmList;
    protected Configuration configuration;

    public ClusteringAlgorithm() {
        algorithmList= new ArrayList<>();
    }

    @Override
    public void setAlgorithmList(Data data){
    }

    @Override
    public Configuration getConfiguration(){return configuration;}

    public ArrayList<ClusteringAlgorithm> getAlgorithmList(){
        return algorithmList;
    }

    @Override
    public String toString(){
        return PropertyManager.getManager().getPropertyValue(AppPropertyTypes.CLUSTERING_TYPE.name());
    }
}
