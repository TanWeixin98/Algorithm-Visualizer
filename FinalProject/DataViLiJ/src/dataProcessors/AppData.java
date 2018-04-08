package dataProcessors;

import Algorithm.Configuration;
import vilij.components.DataComponent;
import vilij.templates.ApplicationTemplate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class AppData implements DataComponent {

    private ApplicationTemplate applicationTemplate;
    private DataProcessor processor;
    private Configuration configuration;
    private Data originalData;
    private Data modifiedData;

    public AppData(ApplicationTemplate applicationTemplate){
        this.applicationTemplate=applicationTemplate;
    }

    @Override
    public void loadData(Path dataFilePath){

    }

    @Override
    public void saveData(Path dataFilePath){
        try{
            if(dataFilePath.toFile()!=null && CheckDataValidity()){
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dataFilePath.toFile()));
                bufferedWriter.write(originalData.toString());
                bufferedWriter.close();
            }
        }catch (IOException io){

        }catch (Exception e){

        }
    }

    @Override
    public void clear() {
    }
    //check if data is valid for TSD format saving
    //return true if data is valid
    //return false if data is invalid
    private boolean CheckDataValidity() throws Exception{
        return true;
    }
}
