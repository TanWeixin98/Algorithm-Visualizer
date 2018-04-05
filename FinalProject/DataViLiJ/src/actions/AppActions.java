package actions;

import vilij.components.ActionComponent;
import vilij.templates.ApplicationTemplate;

public class AppActions implements ActionComponent{

    private ApplicationTemplate applicationTemplate;

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
}
