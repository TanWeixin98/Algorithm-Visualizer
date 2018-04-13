package ui;

import Algorithm.AlgorithmType;
import Algorithm.ClassificationAlgorithm;
import Algorithm.ClusteringAlgorithm;
import Algorithm.Configuration;
import actions.AppActions;
import dataProcessors.AppData;
import dataProcessors.Data;
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
import vilij.components.Dialog;
import vilij.components.ErrorDialog;
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
    private Pane                                selectionPane;
    private Pane                                leftTopPane;
    private Set<AlgorithmType>                  algorithmTypeSet;
    private ToggleButton                        edit;
    private ToggleButton                        complete;
    private String                              configIconPath;
    private String                              backIconPath;
    private String                              startIconPath;

    public Pane getLeftTopPane(){return leftTopPane;}

    public LineChart<Number, Number> getChart() {
        return chart;
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
        PropertyManager manager=applicationTemplate.manager;
        String iconPath = manager.getPropertyValue(AppPropertyTypes.GUI_ICON_PATH.name());
        configIconPath= iconPath +manager.getPropertyValue(AppPropertyTypes.CONFIGURATION_ICON.name());
        backIconPath= iconPath + manager.getPropertyValue(AppPropertyTypes.BACK_ICON.name());
        startIconPath= iconPath + manager.getPropertyValue(AppPropertyTypes.START_ICON.name());
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
        newButton.setDisable(false);
    }
    @Override
    protected void setToolbarHandlers(ApplicationTemplate applicationTemplate) {
        applicationTemplate.setActionComponent(new AppActions(applicationTemplate));
        newButton.setOnAction(e ->  applicationTemplate.getActionComponent().handleNewRequest());
        saveButton.setOnAction(e -> applicationTemplate.getActionComponent().handleSaveRequest());
        loadButton.setOnAction(e -> applicationTemplate.getActionComponent().handleLoadRequest());
        exitButton.setOnAction(e -> applicationTemplate.getActionComponent().handleExitRequest());
    }
    @Override
    public void initialize(){
        layout();
        setWorkSpaceActions();
        showAlgorithmTypeSelection(new Data());
    }

    private void ToggleSwitchActions(ToggleButton edit, ToggleButton complete){
        Dialog errorMessage= applicationTemplate.getDialog(Dialog.DialogType.ERROR);
        edit.setOnAction((ActionEvent e) ->{
            if(edit.isSelected()) {
                textArea.setDisable(false);
                complete.setSelected(false);
            }
        });
        complete.setOnAction(e->{
            if(!complete.isSelected()){
                try {
                    ((AppData) applicationTemplate.getDataComponent()).CheckDataValidity(textArea.getText());
                    textArea.setDisable(true);
                    edit.setSelected(false);
                } catch (Exception error) {
                    edit.setSelected(true);
                    complete.setSelected(false);
                    if (textArea.getText().length() > 0)
                        errorMessage.show("Invalid Input", error.getMessage());
                    else
                        errorMessage.show("Invalid Input", "Empty input");
                }
            }
        });
    }
    public void layout(){
        PropertyManager manager = applicationTemplate.manager;

        chart= new LineChart<>(new NumberAxis(),new NumberAxis());
        InfoText = new Label();
        textArea = new TextArea();
        display = new Button(null, new ImageView(new Image(getClass().getResourceAsStream(startIconPath))));

        edit= new ToggleButton("Edit");
        complete = new ToggleButton("Complete");
        edit.setPrefWidth(windowWidth*0.29*.3);
        complete.setPrefWidth(windowWidth*0.29*.3);
        HBox ToggleSwitch = new HBox(edit,complete);
        ToggleSwitch.setAlignment(Pos.TOP_LEFT);
        ToggleSwitchActions(edit,complete);


        InfoText.setWrapText(true);

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

        leftTopPane = new VBox(10);
        leftTopPane.getChildren().addAll(textArea,ToggleSwitch,InfoText);
        VBox.setVgrow(leftTopPane,Priority.ALWAYS);

        leftPanel.getChildren().addAll(leftTopPane,selectionPane);
        selectionPane.setVisible(false);
        leftTopPane.setVisible(false);

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
    public void clearDataInofrmation(){
        InfoText.setText("");
    }

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
    public TextArea getTextArea(){return textArea;}

    private void clearChart(){
        while(!chart.getData().isEmpty())
            chart.getData().remove((int)(Math.random()*(chart.getData().size()-1)));
    }

    private void showAlgorithmTypeSelection(Data data){
        clearSelectionPane();
        Label selectionLabel =
                new Label(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ALGORITHM_TYPES.name()));
        selectionPane.getChildren().add(selectionLabel);
        for (AlgorithmType algorithmType : algorithmTypeSet) {
            RadioButton algorithm = new RadioButton(algorithmType.toString());

            algorithm.setWrapText(true);
            algorithm.setMinWidth(windowWidth * 0.29-30);

            if(algorithmType.getClass().equals(ClassificationAlgorithm.class)){
                if(data.getLabelNumber()==2)
                    selectionPane.getChildren().add(algorithm);
            }else
                selectionPane.getChildren().add(algorithm);
            algorithm.setOnAction((ActionEvent e) -> {
                selectedAlgorithm = algorithmType;
                showAlgorithmSelection(algorithmType,data);
            });
        }
        selectionPane.setVisible(true);
    }
    private void clearSelectionPane(){
        selectionPane.getChildren().clear();
    }
    private void showAlgorithmSelection(AlgorithmType algorithmType, Data data){
        clearSelectionPane();
        selectionPane.getChildren().add(
                new Label(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.ALGORITHMS.name())));
        algorithmType.testAdd();
        ToggleGroup group = new ToggleGroup();
        for (AlgorithmType algorithm : algorithmType.getAlgorithmList()) {
            HBox algorithmsAndConfiguration = new HBox();
            HBox.setHgrow(algorithmsAndConfiguration,Priority.ALWAYS);

            RadioButton algorithmButton = new RadioButton(algorithm.toString());

            algorithmButton.setWrapText(true);
            algorithmButton.setToggleGroup(group);
            algorithmButton.setTooltip(
                    new Tooltip(applicationTemplate.manager.getPropertyValue(AppPropertyTypes.CONFIGURATION_TOOLTIP.name())));

            Button config =
                    new Button(null, new ImageView(new Image(getClass().getResourceAsStream(configIconPath))));

            algorithmsAndConfiguration.setMinWidth(windowWidth * 0.29 - 30);
            algorithmsAndConfiguration.getChildren().addAll(algorithmButton,
                    config);
            algorithmsAndConfiguration.setSpacing(10);

            selectionPane.getChildren().add(algorithmsAndConfiguration);
            config.setOnAction(e->initConfiguration(primaryStage,new Configuration()));
        }
        group.getSelectedToggle();

        Button back =new Button(null, new ImageView(new Image(getClass().getResourceAsStream(backIconPath))));
        HBox buttonPane = new HBox(back,display);
        buttonPane.setAlignment(Pos.CENTER);
        HBox.setHgrow(back,Priority.ALWAYS);
        HBox.setHgrow(display,Priority.ALWAYS);

        selectionPane.getChildren().addAll(buttonPane);
        back.setOnAction(e->showAlgorithmTypeSelection(data));
    }
    public void initConfiguration(Stage owner, Configuration configuration){
        Stage configurationStage = new Stage();

        configurationStage.initModality(Modality.WINDOW_MODAL);
        configurationStage.initOwner(owner);

        PropertyManager manager = applicationTemplate.manager;

        CheckBox continous =
                new CheckBox(manager.getPropertyValue(AppPropertyTypes.CONTINUOUS_RUN_TEXT.name()));
        if(configuration.continous)
            continous.setSelected(true);
        else
            continous.setSelected(false);
        VBox configurationPanel= new VBox();
        TextField maxIntervalInput = new TextField(Integer.toString(configuration.MaxInterval));
        TextField iterationInterval = new TextField(Integer.toString(configuration.IterationInterval));

        Button setButton =
                new Button(manager.getPropertyValue(AppPropertyTypes.CONFIRM_TEXT.name()));
        Button cancelButton =
                new Button(manager.getPropertyValue(AppPropertyTypes.CANCEL_TEXT.name()));

        HBox buttonsPane = new HBox(setButton,cancelButton);
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.setSpacing(5);
        configurationPanel.getChildren().addAll(
                new Label(manager.getPropertyValue(AppPropertyTypes.MAX_INTERVAL_TEXT.name())),
                maxIntervalInput,
                new Label(manager.getPropertyValue(AppPropertyTypes.ITERATION_INTERVAL_TEXT.name())),
                iterationInterval,continous,buttonsPane);
        configurationPanel.setAlignment(Pos.TOP_LEFT);
        configurationPanel.setSpacing(10);
        configurationPanel.setPadding(new Insets(10,10,10,10));
        Scene configurationScene = new Scene(configurationPanel);
        configurationStage.setScene(configurationScene);
        configurationStage.show();
        cancelButton.setOnAction((e->configurationStage.close()));
        setButton.setOnAction(e->
            ConfigurationAction(configuration ,maxIntervalInput.getText(),
                    iterationInterval.getText(), continous.isSelected(), configurationStage)
        );
    }

    private void ConfigurationAction(Configuration configuration, String maxInterval,
                                     String IterationInterval,Boolean iscontinousRun, Stage configurationStage){
        try{
            Integer maxInt = new Integer(maxInterval);
            Integer iterationInt = new Integer(IterationInterval);
            configuration = new Configuration(maxInt,iterationInt,iscontinousRun);
            configurationStage.close();
        }catch (NumberFormatException error){
            //do nothing
        }
    }
}
