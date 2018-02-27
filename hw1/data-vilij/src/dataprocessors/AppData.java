package dataprocessors;


import javafx.stage.FileChooser;
import settings.AppPropertyTypes;
import ui.AppUI;
import vilij.components.DataComponent;
import vilij.components.Dialog;
import vilij.settings.PropertyTypes;
import vilij.templates.ApplicationTemplate;

import java.io.*;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * This is the concrete application-specific implementation of the data component defined by the Vilij framework.
 *
 * @author Ritwik Banerjee
 * @see DataComponent
 */
public class AppData implements DataComponent {

    private TSDProcessor        processor;
    private ApplicationTemplate applicationTemplate;

    public AppData(ApplicationTemplate applicationTemplate) {
        this.processor = new TSDProcessor();
        this.applicationTemplate = applicationTemplate;
    }

    @Override
    public void loadData(Path dataFilePath) {
        StringBuilder data = new StringBuilder();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(applicationTemplate.getUIComponent().getPrimaryWindow());
        Dialog errorDialog = applicationTemplate.getDialog(Dialog.DialogType.ERROR);
        if(selectedFile!=null
                &&selectedFile.toString().endsWith(applicationTemplate.manager
                .getPropertyValue(AppPropertyTypes.DATA_FILE_EXT.name()).substring(1))){
            try {
                String temp;
                BufferedReader fileReader = new BufferedReader(new FileReader(selectedFile));
                while((temp=fileReader.readLine())!=null){
                    data.append(temp);
                    data.append("\n");
                }
                fileReader.close();
                checkDataFormatInTSDFile(data.toString());
                loadData(data.toString());
                ((AppUI)applicationTemplate.getUIComponent()).setTextFild(processor.getInitialFirstTenLines());
            }catch (IOException e){
                errorDialog.show(applicationTemplate.manager.getPropertyValue(PropertyTypes.LOAD_ERROR_TITLE.name()),
                        applicationTemplate.manager.getPropertyValue(PropertyTypes.LOAD_ERROR_MSG.name())+selectedFile.getName());
            }catch (Exception e){
                errorDialog.show(applicationTemplate.manager.getPropertyValue(PropertyTypes.LOAD_ERROR_TITLE.name()),
                        e.getMessage()
                                +applicationTemplate.manager.getPropertyValue(PropertyTypes.LOAD_ERROR_MSG.name())
                                +selectedFile.getName());
            }
        }else{
            errorDialog.show(applicationTemplate.manager.getPropertyValue(PropertyTypes.LOAD_ERROR_TITLE.name()),
                    applicationTemplate.manager.getPropertyValue(AppPropertyTypes.Load_Error_Message.name()));
        }
    }
    private String deleteEmptyLines (String string){
        List<String> lines = new LinkedList<>();
        Stream.of(string.split("\n")).forEach(line->{
            if(line.length()>0)
                lines.add(line);
        });
        return String.join("\n",lines);

    }
    public void setTextAreaAtTenLines(){
        AppUI appUI =((AppUI)applicationTemplate.getUIComponent());
        int NumberOfLinesNeed = 10-countTextAreaLine(appUI.getTextFieldContent());
        if(NumberOfLinesNeed>0){
            String missingLines ="\n"+processor.addMissingLinesToTextArea(NumberOfLinesNeed);
            if(missingLines.length()>2) {
                appUI.addToExistingText(missingLines);
                appUI.setTextFild(deleteEmptyLines(appUI.getTextFieldContent()));
            }
        }
    }
    private int countTextAreaLine(String TextAreaContent){
        AtomicInteger TextAreaLine=new AtomicInteger(0);
        Stream.of(TextAreaContent.split("\n")).forEach(string ->{
            if(string.length()>0)
                TextAreaLine.getAndIncrement();
        });
        return TextAreaLine.get();
    }
    public void loadData(String dataString){
        Dialog errorDialog = applicationTemplate.getDialog(Dialog.DialogType.ERROR);
        try {
            clear();
            applicationTemplate.getUIComponent().clear();
            processor.processString(dataString);
            displayData();
        } catch(Exception e){
            errorDialog.show(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.Display_Error_Title.name()),
                    e.getMessage());
        }
    }
    //helper method to check dataformat that is implemented in TSDProcessor class
    public void checkDataFormatInTextField() throws Exception{
        String data=((AppUI)applicationTemplate.getUIComponent()).getTextFieldContent();
        processor.clear();
        processor.processString(data);
    }
    private void checkDataFormatInTSDFile(String data) throws Exception{
        processor.clear();
        processor.processString(data);
    }

    @Override
    public void saveData(Path dataFilePath){
        try {
            File file = dataFilePath.toFile();
            if (file != null) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(((AppUI) applicationTemplate.getUIComponent()).getTextFieldContent());
                fileWriter.close();
            }
        }catch (IOException e){
            Dialog errorDialog = applicationTemplate.getDialog(Dialog.DialogType.ERROR);
            errorDialog.show(applicationTemplate.manager.getPropertyValue(PropertyTypes.SAVE_ERROR_TITLE.name()),
                    applicationTemplate.manager.getPropertyValue(PropertyTypes.SAVE_ERROR_MSG.name())+dataFilePath);
        }
    }

    @Override
    public void clear() {
        processor.clear();
    }

    private void displayData() {
        processor.toChartData(((AppUI) applicationTemplate.getUIComponent()).getChart());
    }
}
