package ui;

import actions.AppActions;
import dataprocessors.AppData;
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
    private Label                        label;          // text label for "Data File"

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
        super.setToolBar(applicationTemplate);
        scrnshotButton = setToolbarButton("/gui/icons/screenshot.png","Screenshot" ,true);
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
        while(!chart.getData().isEmpty())
            chart.getData().remove((int)(Math.random()*(chart.getData().size()-1)));

    }

    private void layout() {
        // TODO for homework 1
        //initialize the gridPane
        workspace= new GridPane();
        //initialize the UI components needed
        NumberAxis x_axis = new NumberAxis();
        NumberAxis y_axis = new NumberAxis();
        chart= new ScatterChart<>(x_axis,y_axis);
        chart.setTitle("Data Visualization");
        displayButton= new Button();
        displayButton.setText("Display");
        textArea = new TextArea();
        textArea.setPromptText("Enter The Data");
        label = new Label();
        label.setText("Data File");
        label.setFont(new Font(25));//label font size
        //formatting the GridPane
        workspace.getChildren().addAll(displayButton,chart,textArea,label);
        GridPane.setConstraints(label,0,0,1,1,HPos.CENTER,VPos.CENTER);
        GridPane.setConstraints(displayButton,0,2,1,1,HPos.CENTER,VPos.CENTER);
        GridPane.setConstraints(textArea,0,1);
        GridPane.setConstraints(chart,1,1);
        appPane.getChildren().add(workspace);
    }

    private void setWorkspaceActions(){
        // TODO for homework 1
        // test
        displayButton.setOnAction(e -> ((AppData) applicationTemplate.getDataComponent()).loadData(textArea.getText()));
        //displayButton.setOnAction(e -> ((AppData) this.applicationTemplate.getDataComponent()).loadData(textArea.getText()));



        //if(hasNewText=true){
            //displayButton.setOnAction(e->applicationTemplate.getDataComponent().);
        //}
    }

}
