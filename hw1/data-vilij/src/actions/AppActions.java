package actions;

import javafx.stage.FileChooser;
import settings.AppPropertyTypes;
import ui.AppUI;
import vilij.components.ActionComponent;
import vilij.components.ConfirmationDialog;
import vilij.components.Dialog;
import vilij.propertymanager.PropertyManager;
import vilij.settings.PropertyTypes;
import vilij.templates.ApplicationTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This is the concrete implementation of the action handlers required by the application.
 *
 * @author Ritwik Banerjee
 */
public final class AppActions implements ActionComponent {

    /** The application to which this class of actions belongs. */
    private ApplicationTemplate applicationTemplate;

    /** Path to the data file currently active. */
    Path dataFilePath;

    public AppActions(ApplicationTemplate applicationTemplate) {
        this.applicationTemplate = applicationTemplate;
    }

    @Override
    public void handleNewRequest() {
        // TODO for homework 1
        try{
            if(promptToSave()){

               applicationTemplate.getDataComponent().clear();
               applicationTemplate.getUIComponent().clear();
                ((AppUI)applicationTemplate.getUIComponent()).clearTextArea();
            }
        }catch(IOException io){
            Dialog dialog = applicationTemplate.getDialog(Dialog.DialogType.ERROR);
            dialog.show(applicationTemplate.manager.getPropertyValue(PropertyTypes.SAVE_ERROR_TITLE.name()),
                        applicationTemplate.manager.getPropertyValue(PropertyTypes.SAVE_ERROR_MSG.name())+dataFilePath);
        }



    }

    @Override
    public void handleSaveRequest() {
        // TODO: NOT A PART OF HW 1
    }

    @Override
    public void handleLoadRequest() {
        // TODO: NOT A PART OF HW 1
    }

    @Override
    public void handleExitRequest() {
        // TODO for homework 1
        //only exist is implemented
        //there should be asking user if they want to save
        /*
        Dialog dialog = applicationTemplate.getDialog(Dialog.DialogType.CONFIRMATION);
        dialog.show(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.UnSave_Work.name()),
                    applicationTemplate.manager.getPropertyValue(AppPropertyTypes.EXIT_WHILE_RUNNING_WARNING.name()));
        if(((ConfirmationDialog)dialog).getSelectedOption()==ConfirmationDialog.Option.YES)
        */
            applicationTemplate.getUIComponent().getPrimaryWindow().close();
    }

    @Override
    public void handlePrintRequest() {
        // TODO: NOT A PART OF HW 1
    }

    public void handleScreenshotRequest() throws IOException {
        // TODO: NOT A PART OF HW 1
    }

    /**
     * This helper method verifies that the user really wants to save their unsaved work, which they might not want to
     * do. The user will be presented with three options:
     * <ol>
     * <li><code>yes</code>, indicating that the user wants to save the work and continue with the action,</li>
     * <li><code>no</code>, indicating that the user wants to continue with the action without saving the work, and</li>
     * <li><code>cancel</code>, to indicate that the user does not want to continue with the action, but also does not
     * want to save the work at this point.</li>
     * </ol>
     *
     * @return <code>false</code> if the user presses the <i>cancel</i>, and <code>true</code> otherwise.
     */
    private boolean promptToSave() throws IOException {
        // TODO for homework 1
        // TODO remove the placeholder line below after you have implemented this method
        PropertyManager manager = applicationTemplate.manager;
        Dialog dialog= applicationTemplate.getDialog(Dialog.DialogType.CONFIRMATION);
        dialog.show(manager.getPropertyValue(AppPropertyTypes.SAVE_UNSAVED_WORK_TITLE.name()),manager.getPropertyValue(AppPropertyTypes.SAVE_UNSAVED_WORK.name()));
        if(((ConfirmationDialog)dialog).getSelectedOption()==ConfirmationDialog.Option.YES){
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(manager.getPropertyValue(AppPropertyTypes.DATA_FILE_EXT_DESC.name())
                                                                                ,manager.getPropertyValue(AppPropertyTypes.DATA_FILE_EXT.name()));
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showSaveDialog(applicationTemplate.getUIComponent().getPrimaryWindow());
            if (file != null) {
                dataFilePath = file.toPath();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(((AppUI)applicationTemplate.getUIComponent()).getTextFieldContent());
                fileWriter.close();
            }
        }
        //if user click cancel on save it will also return false
        return (dataFilePath!=null)&& ((ConfirmationDialog)dialog).getSelectedOption()!=ConfirmationDialog.Option.CANCEL;
    }
}
