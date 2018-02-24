package dataprocessors;

import settings.AppPropertyTypes;
import ui.AppUI;
import vilij.components.DataComponent;
import vilij.components.Dialog;
import vilij.templates.ApplicationTemplate;

import java.nio.file.Path;

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
        // TODO: NOT A PART OF HW 1
    }

    public void loadData(String dataString){
        // TODO for homework 1
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
    public void checkDataFormat() throws Exception{
        String data=((AppUI)applicationTemplate.getUIComponent()).getTextFieldContent();
        processor.processString(data);
    }

    @Override
    public void saveData(Path dataFilePath) {
        // TODO: NOT A PART OF HW 1
    }

    @Override
    public void clear() {
        processor.clear();
    }

    private void displayData() {
        processor.toChartData(((AppUI) applicationTemplate.getUIComponent()).getChart());
    }
}
