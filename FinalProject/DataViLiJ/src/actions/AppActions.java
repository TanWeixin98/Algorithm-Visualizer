package actions;

import Algorithm.Configuration;
import javafx.stage.FileChooser;
import settings.AppPropertyTypes;
import ui.AppUI;
import vilij.components.ActionComponent;
import vilij.components.ConfirmationDialog;
import vilij.components.Dialog;
import vilij.propertymanager.PropertyManager;
import vilij.templates.ApplicationTemplate;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

public class AppActions implements ActionComponent{

    private ApplicationTemplate applicationTemplate;
    private Path dataPath;

    public AppActions(ApplicationTemplate applicationTemplate){
        this.applicationTemplate= applicationTemplate;
    }

    @Override
    public void handleNewRequest() {
        AppUI ui = (AppUI) applicationTemplate.getUIComponent();
        if(!ui.getLeftTopPane().isVisible())
            ui.getLeftTopPane().setVisible(true);
        else {
            PropertyManager manager = applicationTemplate.manager;
            Dialog dialog = applicationTemplate.getDialog(Dialog.DialogType.CONFIRMATION);
            dialog.show(manager.getPropertyValue(AppPropertyTypes.SAVE_UNSAVED_WORK_TITLE.name()),
                    manager.getPropertyValue(AppPropertyTypes.SAVE_UNSAVED_WORK.name()));
            if (((ConfirmationDialog) dialog).getSelectedOption() == ConfirmationDialog.Option.YES) {
                handleSaveRequest();
                ui.getTextArea().clear();
                ui.clearDataInofrmation();
            } else if (((ConfirmationDialog) dialog).getSelectedOption() == ConfirmationDialog.Option.NO) {
                //clear
                ui.clearDataInofrmation();
                ui.getTextArea().clear();

            }
        }
    }

    @Override
    public void handleSaveRequest() {
        try {
            if(dataPath==null)
                promptToSave();
            applicationTemplate.getDataComponent().saveData(dataPath);
            ((AppUI) applicationTemplate.getUIComponent()).disableSaveButton(true);
        } catch (NullPointerException e) {
            //do nothing if user cancel saving
        }
    }

    @Override
    public void handleLoadRequest() {
        FileChooser fileChooser =new FileChooser();
        try{
            dataPath=fileChooser.showOpenDialog(applicationTemplate.getUIComponent().getPrimaryWindow()).toPath();
            applicationTemplate.getDataComponent().loadData(dataPath);
        }catch (NullPointerException e){
            //do nothing if user cancel loading
        }
    }

    @Override
    public void handleExitRequest() {

    }

    @Override
    public void handlePrintRequest() {

    }

    public void handleDisplayRequest(Configuration configuration){
        if(configuration!=null){

        }else{

        }
    }


    private void promptToSave() throws NullPointerException{
        PropertyManager manager = applicationTemplate.manager;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(manager.getPropertyValue(AppPropertyTypes.DATA_FILE_EXT_DESC.name())
                ,manager.getPropertyValue(AppPropertyTypes.DATA_FILE_EXT.name()));
        fileChooser.getExtensionFilters().add(filter);
        //set initial directory
        String directory_Path=applicationTemplate.manager.getPropertyValue(AppPropertyTypes.Separator.name()) +
                applicationTemplate.manager.getPropertyValue(AppPropertyTypes.DATA_RESOURCE_PATH.name());
        URL url=getClass().getResource(directory_Path);
        File temp = new File(url.getFile());
        fileChooser.setInitialDirectory(temp);

        dataPath = fileChooser.showSaveDialog(applicationTemplate.getUIComponent().getPrimaryWindow()).toPath();
    }
}
