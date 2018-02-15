package ui;

import actions.AppActions;
import dataprocessors.AppData;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import settings.AppPropertyTypes;
import vilij.propertymanager.PropertyManager;
import vilij.settings.PropertyTypes;
import vilij.templates.ApplicationTemplate;
import vilij.templates.UITemplate;




/**
 * This is the application's user interface implementation.
 *
 * @author Ritwik Banerjee
 */
public final class AppUI extends UITemplate {

    /** The application to which this class of actions belongs. */
    ApplicationTemplate applicationTemplate;

    @SuppressWarnings("FieldCanBeLocal")
    private Button                       scrnshotButton; // toolbar button to take a screenshot of the data
    private ScatterChart<Number, Number> chart;          // the chart where data will be displayed
    private Button                       displayButton;  // workspace button to display data on the chart
    private TextArea                     textArea;       // text area for new data input
    private boolean                      hasNewText;     // whether or not the text area has any new data since last display

    public ScatterChart<Number, Number> getChart() { return chart; }

    public AppUI(Stage primaryStage, ApplicationTemplate applicationTemplate) {
        super(primaryStage, applicationTemplate);
        this.applicationTemplate = applicationTemplate;
    }

    @Override
    protected void setResourcePaths(ApplicationTemplate applicationTemplate) {
        super.setResourcePaths(applicationTemplate);

    }

    @Override
    protected void setToolBar(ApplicationTemplate applicationTemplate) {
        // TODO for homework 1
        //added new button for screenshot
        PropertyManager manager = applicationTemplate.manager;

        super.setToolBar(applicationTemplate);
        String scrnShotPath =manager.getPropertyValue(AppPropertyTypes.Separator.name())+String.join(manager.getPropertyValue(AppPropertyTypes.Separator.name()),
                                                    manager.getPropertyValue(PropertyTypes.GUI_RESOURCE_PATH.name()),
                                                    manager.getPropertyValue(PropertyTypes.ICONS_RESOURCE_PATH.name()),
                                                    manager.getPropertyValue(AppPropertyTypes.SCREENSHOT_ICON.name()));
        scrnshotButton = setToolbarButton(scrnShotPath,manager.getPropertyValue(AppPropertyTypes.SCREENSHOT_TOOLTIP.name()),true);
        toolBar = new ToolBar(newButton,saveButton,loadButton,printButton,exitButton,scrnshotButton);
    }

    @Override
    protected void setToolbarHandlers(ApplicationTemplate applicationTemplate) {
        applicationTemplate.setActionComponent(new AppActions(applicationTemplate));
        newButton.setOnAction(e -> applicationTemplate.getActionComponent().handleNewRequest());
        saveButton.setOnAction(e -> applicationTemplate.getActionComponent().handleSaveRequest());
        loadButton.setOnAction(e -> applicationTemplate.getActionComponent().handleLoadRequest());
        exitButton.setOnAction(e -> applicationTemplate.getActionComponent().handleExitRequest());
        printButton.setOnAction(e -> applicationTemplate.getActionComponent().handlePrintRequest());
    }

    @Override
    public void initialize() {
        layout();
        setWorkspaceActions();
    }

    @Override
    public void clear() {
        // TODO for homework 1
        //clears all data
        while(!chart.getData().isEmpty())
            chart.getData().remove((int)(Math.random()*(chart.getData().size()-1)));

    }
    //empty text field
    public void clearTextArea(){textArea.clear();}
    public boolean getHasNewText(){return hasNewText;}

    private void layout() {
        // TODO for homework 1
        //initialize the gridPane
        workspace= new GridPane();
        PropertyManager manager= applicationTemplate.manager;
        //initialize the UI components needed
        NumberAxis x_axis = new NumberAxis();
        NumberAxis y_axis = new NumberAxis();
        chart= new ScatterChart<>(x_axis,y_axis);
        chart.setTitle(manager.getPropertyValue(AppPropertyTypes.chart_Title.name()));
        displayButton= new Button();
        displayButton.setText(manager.getPropertyValue(AppPropertyTypes.Display_Label.name()));
        textArea = new TextArea();
        textArea.setPromptText(manager.getPropertyValue(AppPropertyTypes.TEXT_AREA.name()));
        Label label = new Label();
        label.setText(manager.getPropertyValue(AppPropertyTypes.Text_Field_Title.name()));
        label.setFont(new Font(applicationTemplate.manager.getPropertyValueAsInt(AppPropertyTypes.Data_Label_Title_Font.name())));//label font size
        //formatting the GridPane
        workspace.getChildren().addAll(displayButton,chart,textArea,label);
        GridPane.setConstraints(label,0,0,1,1,HPos.CENTER,VPos.CENTER);
        GridPane.setConstraints(displayButton,0,2,1,1,HPos.CENTER,VPos.CENTER);
        GridPane.setConstraints(textArea,0,1);
        GridPane.setConstraints(chart,1,1);
        appPane.getChildren().add(workspace);
    }

    public String getTextFieldContent(){return textArea.getText();}

    private void setWorkspaceActions(){
        // TODO for homework 1
        textArea.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) ->{
                if(!textArea.getText().isEmpty()) {
                    hasNewText=true;
                    newButton.setDisable(false);
                    saveButton.setDisable(false);
                }
                else {
                    hasNewText=false;
                    saveButton.setDisable(true);
                    newButton.setDisable(true);
                }
            }
        );
        displayButton.setOnAction(e -> ((AppData) applicationTemplate.getDataComponent()).loadData(textArea.getText()));
    }

}
