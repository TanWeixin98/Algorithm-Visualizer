package ui;

import Algorithm.AlgorithmType;
import Algorithm.Configuration;
import actions.AppActions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import settings.AppPropertyTypes;
import vilij.propertymanager.PropertyManager;
import vilij.settings.PropertyTypes;
import vilij.templates.ApplicationTemplate;
import vilij.templates.UITemplate;

public class AppUI extends UITemplate {
    ApplicationTemplate applicationTemplate;

    private Button                              scrnShootButton;
    private Button                              display;
    private LineChart<Number,Number>            chart;
    private TextArea                            textArea;
    private Label                               InfoText;
    private AlgorithmType                       selectedAlgorithm;
    private Configuration                       configuration;
    private CheckBox                            continousRunCheckBox;

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
        PropertyManager manager = applicationTemplate.manager;
        super.setToolBar(applicationTemplate);
        String scrnShotPath =manager.getPropertyValue(AppPropertyTypes.Separator.name())+String.join(manager.getPropertyValue(AppPropertyTypes.Separator.name()),
                manager.getPropertyValue(PropertyTypes.GUI_RESOURCE_PATH.name()),
                manager.getPropertyValue(PropertyTypes.ICONS_RESOURCE_PATH.name()),
                manager.getPropertyValue(AppPropertyTypes.SCREENSHOT_ICON.name()));
        scrnShootButton = setToolbarButton(scrnShotPath,manager.getPropertyValue(AppPropertyTypes.SCREENSHOT_TOOLTIP.name()),true);
        toolBar = new ToolBar(newButton,saveButton,loadButton,exitButton,scrnShootButton);
    }
    @Override
    protected void setToolbarHandlers(ApplicationTemplate applicationTemplate) {
        applicationTemplate.setActionComponent(new AppActions(applicationTemplate));
        newButton.setOnAction(e -> applicationTemplate.getActionComponent().handleNewRequest());
        saveButton.setOnAction(e -> applicationTemplate.getActionComponent().handleSaveRequest());
        loadButton.setOnAction(e -> applicationTemplate.getActionComponent().handleLoadRequest());
        exitButton.setOnAction(e -> applicationTemplate.getActionComponent().handleExitRequest());
    }
    @Override
    public void initialize(){
        layout();
        setWorkSpaceActions();
    }

    public void layout(){
        PropertyManager manager = applicationTemplate.manager;

        NumberAxis x_axis = new NumberAxis();
        NumberAxis y_axis = new NumberAxis();
        chart= new LineChart<>(x_axis,y_axis);

        VBox leftPanel= new VBox(8);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPadding(new Insets(10));

        VBox.setVgrow(leftPanel,Priority.ALWAYS);
        leftPanel.setMaxSize(windowWidth * 0.29, windowHeight * 0.69);
        leftPanel.setMinSize(windowWidth * 0.29, windowHeight * 0.69);

        InfoText = new Label();
        textArea = new TextArea();
        display = new Button(manager.getPropertyValue(AppPropertyTypes.DISPLAY_BUTTON_TEXT.name()));
        leftPanel.getChildren().addAll( textArea,InfoText);

        VBox rightPanel = new VBox(chart);
        rightPanel.setMaxSize(windowWidth * .69, windowHeight * 0.69);
        rightPanel.setMinSize(windowWidth * .69, windowHeight * 0.69);
        VBox.setVgrow(chart,Priority.ALWAYS);

        workspace= new HBox(leftPanel,rightPanel);
        HBox.setHgrow(workspace, Priority.ALWAYS);
        appPane.getChildren().add(workspace);
        VBox.setVgrow(appPane,Priority.ALWAYS);
    }
    public void setWorkSpaceActions(){
        textArea.textProperty().addListener((observable, oldValue, newValue)->{
            if(textArea.getText().isEmpty())
                saveButton.setDisable(true);
            else
                saveButton.setDisable(false);
        });

        display.setOnAction(e->{

        });
    }

    public void showDataInformation(){}

    public void disableSaveButton(Boolean disable){
        saveButton.setDisable(disable);
    }
    public void disableTextArea(Boolean disable){
        textArea.setDisable(disable);
    }
    public void disableScrnShotButton(Boolean disable){
        scrnShootButton.setDisable(disable);
    }
    public void disableRunButton(Boolean disable){
        display.setDisable(disable);
    }

    @Override
    public void clear(){
        clearChart();
        clearTextArea();
    }

    private void clearTextArea(){textArea.clear();}
    private void clearChart(){
        while(!chart.getData().isEmpty())
            chart.getData().remove((int)(Math.random()*(chart.getData().size()-1)));
    }

    private void showAlgorithmTypeSelection(){}
    private void initConfiguration(Stage owner){
        owner.initModality(Modality.WINDOW_MODAL);

    }
    private void countTextAreaLine(){}



}
