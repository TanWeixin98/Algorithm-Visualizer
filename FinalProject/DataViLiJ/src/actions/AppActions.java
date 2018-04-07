package actions;

import javafx.stage.FileChooser;
import settings.AppPropertyTypes;
import vilij.components.ActionComponent;
import vilij.propertymanager.PropertyManager;
import vilij.templates.ApplicationTemplate;

import java.io.IOException;
import java.nio.file.Path;

public class AppActions implements ActionComponent{

    private ApplicationTemplate applicationTemplate;
    private Path dataPath;

    public AppActions(ApplicationTemplate applicationTemplate){
        this.applicationTemplate= applicationTemplate;
    }

    @Override
    public void handleNewRequest() {

    }

    @Override
    public void handleSaveRequest() {

    }

    @Override
    public void handleLoadRequest() {

    }

    @Override
    public void handleExitRequest() {

    }

    @Override
    public void handlePrintRequest() {

    }

    private void promptToSave() throws IOException{
        PropertyManager manager = applicationTemplate.manager;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(manager.getPropertyValue(AppPropertyTypes.DATA_FILE_EXT_DESC.name())
                ,manager.getPropertyValue(AppPropertyTypes.DATA_FILE_EXT.name()));
        fileChooser.getExtensionFilters().add(filter);

    }
}
