package actions;

import javafx.stage.FileChooser;
import ui.AppUI;
import vilij.components.ActionComponent;
import vilij.components.ConfirmationDialog;
import vilij.components.Dialog;
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

        Dialog dialog= applicationTemplate.getDialog(Dialog.DialogType.CONFIRMATION);
        dialog.show("Hello","IDK");
        ConfirmationDialog.Option option = ((ConfirmationDialog)dialog).getSelectedOption();
        if(option ==option.YES){
            //promptToSave in .tsd file
            try {
                promptToSave();
            }catch (IOException ex) {
                System.out.print("IO Error");
            }
        }else if(option==option.NO){
            //restart application without saving
            try {
                applicationTemplate.getUIComponent().clear();
                applicationTemplate.getDataComponent().clear();
                ((AppUI)applicationTemplate.getUIComponent()).clearTextArea();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            //cancel does nothing
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
        try {
            promptToSave();
        }catch (IOException e){
            System.out.print("need to fix");
        }
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
    private void promptToSave() throws IOException {
        // TODO for homework 1
        // TODO remove the placeholder line below after you have implemented this method
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Tab-Separated Data File(.*.tsd)","*.tsd");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showSaveDialog(applicationTemplate.getUIComponent().getPrimaryWindow());
        if (file != null) {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("hello");
            fileWriter.close();
        }


    }
}
