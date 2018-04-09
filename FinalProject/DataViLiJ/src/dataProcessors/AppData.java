package dataProcessors;

import Algorithm.Configuration;
import settings.AppPropertyTypes;
import vilij.components.DataComponent;
import vilij.components.Dialog;
import vilij.components.ErrorDialog;
import vilij.propertymanager.PropertyManager;
import vilij.templates.ApplicationTemplate;

import java.io.*;
import java.nio.file.Path;

public class AppData implements DataComponent {

    private  final String new_Line_Char ="\n";
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
        StringBuilder stringbuilder = new StringBuilder();
        PropertyManager manager = applicationTemplate.manager;
        Dialog error = applicationTemplate.getDialog(Dialog.DialogType.ERROR);
        if(dataFilePath.toString().endsWith(manager.getPropertyValue(AppPropertyTypes.DATA_FILE_EXT.name())
                .substring(1))){
            try{
                String temp;
                BufferedReader fileReader = new BufferedReader(new FileReader(dataFilePath.toFile()));
                while((temp=fileReader.readLine())!=null){
                    stringbuilder.append(temp);
                    stringbuilder.append(new_Line_Char);
                }
            }catch (IOException io){

            }catch (Exception e){

            }

        }else{
            //incorrect data format error
        }
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
        originalData.clear();
        modifiedData.clear();
    }
    //check if data is valid for TSD format saving
    //return true if data is valid
    //return false if data is invalid
    private boolean CheckDataValidity() throws Exception{
        return true;
    }
}
