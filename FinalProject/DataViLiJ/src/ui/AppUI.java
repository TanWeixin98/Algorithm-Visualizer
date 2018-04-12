package ui;

import Algorithm.AlgorithmType;
import Algorithm.ClassificationAlgorithm;
import Algorithm.ClusteringAlgorithm;
import Algorithm.Configuration;
import actions.AppActions;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import settings.AppPropertyTypes;
import vilij.propertymanager.PropertyManager;
import vilij.settings.PropertyTypes;
import vilij.templates.ApplicationTemplate;
import vilij.templates.UITemplate;

import java.util.HashSet;
import java.util.Set;

public class AppUI extends UITemplate {
    ApplicationTemplate applicationTemplate;

    private Button                              scrnShootButton;
    private Button                              display;
    private LineChart<Number,Number>            chart;
    private TextArea                            textArea;
    private Label                               InfoText;
    private AlgorithmType                       selectedAlgorithm;
    private Configuration                       configuration;
    private Pane                                selectionPane;
    private Set<AlgorithmType>                  algorithmTypeSet;
    private ToggleButton                        isComplete;
    private String                              configIconPath;

    public Configuration getConfiguration() {
        return configuration;
    }

    public AppUI(Stage primaryStage, ApplicationTemplate applicationTemplate) {
        super(primaryStage, applicationTemplate);
        this.applicationTemplate = applicationTemplate;
        algorithmTypeSet= new HashSet<>();
        algorithmTypeSet.add(new ClassificationAlgorithm());
        algorithmTypeSet.add(new ClusteringAlgorithm());
    }
    @Override
    protected void setResourcePaths(ApplicationTemplate applicationTemplate) {
        super.setResourcePaths(applicationTemplate);
        configIconPath= "/" + String.join("/", "gui/icons","configuration.png");
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

        chart= new LineChart<>(new NumberAxis(),new NumberAxis());
        InfoText = new Label();
        textArea = new TextArea();
        display = new Button(manager.getPropertyValue(AppPropertyTypes.DISPLAY_BUTTON_TEXT.name()));
        isComplete = new ToggleButton("Compete");

        VBox leftPanel= new VBox(10);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setMaxWidth(windowWidth * 0.29);
        leftPanel.setMinWidth(windowWidth * 0.29);
        VBox.setVgrow(leftPanel,Priority.ALWAYS);

        selectionPane=new VBox(10);
        selectionPane.setPadding(new Insets(10,10,10,10));
        VBox.setVgrow(selectionPane,Priority.ALWAYS);

        selectionPane.setVisible(false);
        //showAlgorithmTypeSelection();

        leftPanel.getChildren().addAll( textArea,isComplete,InfoText,selectionPane);

        VBox rightPanel = new VBox(chart);
        rightPanel.setMaxSize(windowWidth*.69,windowHeight*.69);
        rightPanel.setMinSize(windowWidth*.69,windowHeight*.69);
        VBox.setVgrow(chart,Priority.ALWAYS);

        workspace= new HBox(leftPanel,rightPanel);
        HBox.setHgrow(workspace, Priority.ALWAYS);
        appPane.getChildren().add(workspace);
        VBox.setVgrow(appPane,Priority.ALWAYS);
        primaryScene.getStylesheets().add(getClass().getResource(manager.getPropertyValue(AppPropertyTypes.CSS_Path.name())).toExternalForm());
    }
    public void setWorkSpaceActions(){
        textArea.textProperty().addListener((observable, oldValue, newValue)->{
            if(textArea.getText().isEmpty()) {
                saveButton.setDisable(true);
                newButton.setDisable(true);
            }
            else {
                saveButton.setDisable(false);
                newButton.setDisable(false);
            }
        });
        display.setOnAction(e->{
        });
    }

    public void showDataInformation(String dataInfo){InfoText.setText(dataInfo);}

    public void disableSaveButton(Boolean disable){
        saveButton.setDisable(disable);
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
    public TextArea getTextArea(){
        return textArea;
    }
    private void clearChart(){
        while(!chart.getData().isEmpty())
            chart.getData().remove((int)(Math.random()*(chart.getData().size()-1)));
    }

    private void showAlgorithmTypeSelection(){
        clearSelectionPane();
        Label selectionLabel = new Label("Algorithm Type");
        selectionPane.getChildren().add(selectionLabel);
        for (AlgorithmType algorithmType : algorithmTypeSet) {
            RadioButton algorithm = new RadioButton(algorithmType.toString());
            algorithm.setWrapText(true);
            algorithm.setMinWidth(windowWidth * 0.29-30);
            selectionPane.getChildren().add(algorithm);
            algorithm.setOnAction((ActionEvent e) -> {
                selectedAlgorithm = algorithmType;
                showAlgorithmSelection(algorithmType);
            });
        }
        selectionPane.setVisible(true);
    }
    private void clearSelectionPane(){
        selectionPane.getChildren().clear();
    }
    private void showAlgorithmSelection(AlgorithmType algorithmType){
        clearSelectionPane();
        selectionPane.getChildren().add(new Label("Algorithm"));
        algorithmType.testAdd();
        ToggleGroup group = new ToggleGroup();
        for (AlgorithmType algorithm : algorithmType.getAlgorithmList()) {
            RadioButton algorithmButton = new RadioButton(algorithm.toString());
            algorithmButton.setWrapText(true);
            algorithmButton.setToggleGroup(group);
            algorithmButton.setMinWidth(windowWidth * 0.29 - 30);
            selectionPane.getChildren().add(algorithmButton);
        }
        group.getSelectedToggle();

        Button config = new Button(null,new ImageView(new Image(getClass().getResourceAsStream(configIconPath))));
        Button back =new Button("Back");
        HBox buttonPane = new HBox(back,display);
        buttonPane.setAlignment(Pos.CENTER);
        HBox.setHgrow(back,Priority.ALWAYS);
        HBox.setHgrow(display,Priority.ALWAYS);

        selectionPane.getChildren().addAll(config,buttonPane);
        config.setOnAction(e->initConfiguration(primaryStage));
        back.setOnAction(e->showAlgorithmTypeSelection());
    }
    public void initConfiguration(Stage owner){
        Stage configurationStage = new Stage();

        configurationStage.initModality(Modality.WINDOW_MODAL);
        configurationStage.initOwner(owner);

        CheckBox continous = new CheckBox("Continous Run");
        VBox configurationPanel= new VBox();
        TextField maxIntervalInput = new TextField();
        TextField iterationInterval = new TextField();
        Button setButton = new Button("Confirm");
        Button cancelButton = new Button("cancel");

        HBox buttonsPane = new HBox(setButton,cancelButton);
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.setSpacing(5);
        configurationPanel.getChildren().addAll(new Label("Max Interval"),
                maxIntervalInput,new Label("Iteration Interval"),
                iterationInterval,continous,buttonsPane);
        configurationPanel.setAlignment(Pos.TOP_LEFT);
        configurationPanel.setSpacing(10);
        configurationPanel.setPadding(new Insets(10,10,10,10));
        Scene configurationScene = new Scene(configurationPanel);
        configurationStage.setScene(configurationScene);
        configurationStage.show();
        cancelButton.setOnAction((e->configurationStage.close()));
        setButton.setOnAction(e->ConfigurationAction(maxIntervalInput.getText(), iterationInterval.getText(),continous.isSelected(),configurationStage));
    }

    private void ConfigurationAction(String maxInterval,String IterationInterval,Boolean iscontinousRun, Stage configurationStage){
        try{
            Integer maxInt = new Integer(maxInterval);
            Integer iterationInt = new Integer(IterationInterval);
            configuration = new Configuration(maxInt,iterationInt,iscontinousRun,selectedAlgorithm);
            configurationStage.close();
        }catch (NumberFormatException error){

        }
    }
}
