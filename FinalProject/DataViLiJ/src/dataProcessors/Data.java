package dataProcessors;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Data {
    public static class InvalidDataNameException extends Exception {

        private static final String NAME_ERROR_MSG = "All data instance names must start with the @ character. ";

        public InvalidDataNameException(String name, int lineNum) {
            super(String.format("Invalid name '%s' at line %d. " + NAME_ERROR_MSG,name,lineNum));
        }
    }
    public static  class RepeatingIDException extends Exception{
        private  static final String REPEATING_NAME_ERROR_MSG ="All data instance names must be use only once. ";

        public RepeatingIDException(String name,int lineNum){
            super(String.format("Instance name '%s' cannot be use at line %d because it already exist. "+ REPEATING_NAME_ERROR_MSG,name,lineNum));
        }
    }

    private HashMap<String, String>     dataLabels;
    private HashMap<String, Point2D>    dataPoints;
    private ArrayList<String>           dataOrder;
    private AtomicInteger               lineNum;
    private HashSet<String>             labels;
    private static final String UNIVERSAL_ERROR_MESSAGE = "Invalid data format ar line %d.";
    public Data(){
        dataLabels= new HashMap<>();
        dataOrder = new ArrayList<>();
        dataPoints = new HashMap<>();
        lineNum= new AtomicInteger();
        labels = new HashSet<>();
    }
    @Override
    public String toString() {
        StringBuilder data = new StringBuilder();
        for(int i =0;i<dataOrder.size();i++){
            String temp= dataOrder.get(i);
            data.append(temp)
                    .append("\t")
                    .append(dataLabels.get(temp))
                    .append("\t")
                    .append(dataPoints.get(temp).getX())
                    .append(",")
                    .append(dataPoints.get(temp).getY())
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
                        .append(dataPoints.get(temp).getX())
                        .append(",")
                        .append(dataPoints.get(temp).getY())
                        .append("\n");
            }
        }
        return firstTenLines.toString();
    }

    public void setData(String data) throws Exception{
        clear();
        AtomicBoolean hadAnError   = new AtomicBoolean(false);
        StringBuilder errorMessage = new StringBuilder();
        lineNum.set(0);
        Stream.of(data.split("\n"))
                .map(line -> Arrays.asList(line.split("\t")))
                .forEach(list -> {
                    if(!hadAnError.get()){
                        try {
                            lineNum.getAndIncrement();
                            String   name  = checkName(list.get(0));
                            dataOrder.add(name);
                            checkInstanceNameRepetition(name);
                            String   label = list.get(1);
                            String[] pair  = list.get(2).split(",");
                            Point2D  point = new Point2D(Double.parseDouble(pair[0]), Double.parseDouble(pair[1]));
                            if(list.size()>3)
                                throw new Exception();
                            dataLabels.put(name, label);
                            dataPoints.put(name, point);
                        } catch (InvalidDataNameException|RepeatingIDException e) {
                            errorMessage.setLength(0);
                            errorMessage.append(e.getMessage());
                            hadAnError.set(true);
                        }catch(Exception e){
                            errorMessage.setLength(0);
                            errorMessage.append(String.format(UNIVERSAL_ERROR_MESSAGE,lineNum.intValue()));
                            hadAnError.set(true);
                        }
                    }
                });
        if (errorMessage.length() > 0)
            throw new Exception(errorMessage.toString());
    }

    private void checkInstanceNameRepetition(String name) throws  RepeatingIDException{
        if(dataLabels.containsKey(name))
            throw new RepeatingIDException(name,lineNum.intValue());
    }
    private String checkName(String name) throws InvalidDataNameException{
        if (!name.startsWith("@"))
            throw new InvalidDataNameException(name,lineNum.intValue());
        return name;
    }
    public void clear(){
        dataOrder.clear();
        dataLabels.clear();
        dataPoints.clear();
    }




}
