package dataProcessors;

import Algorithm.AlgorithmType;
import Algorithm.ClassificationAlgorithm;
import Algorithm.ClusteringAlgorithm;
import Algorithm.Configuration;
import settings.AppPropertyTypes;
import ui.AppUI;
import vilij.components.DataComponent;
import vilij.components.Dialog;
import vilij.propertymanager.PropertyManager;
import vilij.templates.ApplicationTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppData implements DataComponent {

    private static final String NEW_LINE_CHAR ="\n";
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
        AppUI ui= (AppUI)applicationTemplate.getUIComponent();
        Dialog error = applicationTemplate.getDialog(Dialog.DialogType.ERROR);
        if(dataFilePath.toString().endsWith(manager.getPropertyValue(AppPropertyTypes.DATA_FILE_EXT.name())
                .substring(1))){
            try{
                String temp;
                BufferedReader fileReader = new BufferedReader(new FileReader(dataFilePath.toFile()));
                while((temp=fileReader.readLine())!=null){
                    stringbuilder.append(temp);
                    stringbuilder.append(NEW_LINE_CHAR);
                }
                fileReader.close();
                CheckDataValidity(stringbuilder.toString());
                ui.getTextArea().setText(originalData.getFirstTenLines());
                ui.showDataInformation(
                        originalData.getDataInfo(manager.getPropertyValue(AppPropertyTypes.LOADED_DATA_INFO_TEXT.name()),
                                manager.getPropertyValue(AppPropertyTypes.LOADED_FILE_LOCATION_TEXT.name()),
                                dataFilePath.getFileName().toString(),dataFilePath.toAbsolutePath().toString()));
            }catch (IOException io){
                error.show(manager.getPropertyValue(AppPropertyTypes.LOAD_ERROR_TITLE.name()),
                        manager.getPropertyValue(AppPropertyTypes.LOAD_IO_ERROR_MESSAGE.name()));
            }catch (Exception e){
                error.show(manager.getPropertyValue(AppPropertyTypes.LOAD_ERROR_TITLE.name()),
                        e.getMessage());
            }
        }else{
            //incorrect data format error
            String filePath = dataFilePath.toString();
            String fileExtension = filePath.substring(filePath.lastIndexOf('.')+1);
            error.show(manager.getPropertyValue(AppPropertyTypes.LOAD_ERROR_TITLE.name()),
                    String.format(manager.getPropertyValue(AppPropertyTypes.LOAD_WRONG_FORMAT_MESSAGE.name())
                            , fileExtension));
        }
    }
    public void loadDataToChart(Configuration configuration, AlgorithmType algorithmType){
        if(algorithmType.getClass().equals(ClusteringAlgorithm.class)){
            processor= new ClusteringProcessor();
        }else if(algorithmType.getClass().equals(ClassificationAlgorithm.class)){
            processor=new ClassificationProcessor();
        }
        processor.toChartData(originalData,((AppUI)applicationTemplate.getUIComponent()).getChart());
    }
    @Override
    public void saveData(Path dataFilePath){
        Dialog error = applicationTemplate.getDialog(Dialog.DialogType.ERROR);
        PropertyManager manager= applicationTemplate.manager;
        try{
            if(originalData==null)
                CheckDataValidity(((AppUI)applicationTemplate.getUIComponent()).getTextArea().getText());
            if(dataFilePath.toFile()!=null){
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dataFilePath.toFile()));
                bufferedWriter.write(originalData.toString());
                bufferedWriter.close();
            }
        }catch (IOException io){
            error.show(manager.getPropertyValue(AppPropertyTypes.SAVE_ERROR_TITLE.name()),
                    manager.getPropertyValue(AppPropertyTypes.SAVE_IO_ERROR_MESSAGE.name()));

        }catch (Exception e){
            error.show(manager.getPropertyValue(AppPropertyTypes.SAVE_ERROR_TITLE.name()),
                    e.getMessage());
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
    private void CheckDataValidity(String data) throws Exception{
        originalData = new Data();
        originalData.setData(data);
    }
}
