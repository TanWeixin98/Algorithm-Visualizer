package dataProcessors;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    private HashMap<String, String> dataLabels;
    private HashMap<String, Point2D> dataPoints;
    private ArrayList<String> dataOrder;



    public String toString() {
        StringBuilder data = new StringBuilder();
        for(int i =0;i<dataOrder.size();i++){
            String temp= dataOrder.get(i);
            data.append(temp)
                    .append("\t")
                    .append(dataLabels.get(temp))
                    .append("\t")
                    .append(dataPoints.get(temp))
                    .append("\n");
        }
        return data.toString();
    }
    public String getFirstTenLines(){
        StringBuilder firstTenLines= new StringBuilder();
        for(int i=0;i<10;i++){
            if(i<dataOrder.size()) {
                String temp = dataOrder.get(i);
                firstTenLines.append(temp)
                        .append("\t")
                        .append(dataLabels.get(temp))
                        .append("\t")
                        .append(dataPoints.get(temp))
                        .append("\n");
            }
        }
        return firstTenLines.toString();
    }

    public void setData(){}
    public void addData(){}




}
