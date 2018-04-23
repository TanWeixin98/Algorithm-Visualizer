package dataProcessors;


import Algorithm.ClassificationAlgorithm;
import Algorithm.Configuration;
import Algorithm.RandomClassification;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.chart.XYChart;
import java.util.List;

public class ClassificationProcessor extends Thread implements DataProcessor{
    private ClassificationAlgorithm classificationAlgorithm;
    private Configuration configuration;
    private int currentIteration;
    private XYChart<Number, Number> chart;
    private int chartOriginalDataSize;
    private int MaxX=5, MinX=2;
    public ClassificationProcessor(ClassificationAlgorithm classificationAlgorithm, XYChart<Number,Number> chart){
        this.classificationAlgorithm=classificationAlgorithm;
        this.configuration=classificationAlgorithm.getConfiguration();
        this.chart=chart;
        currentIteration=0;
    }

    public void setChartOriginalDataSize(){
        chartOriginalDataSize=chart.getData().size();
    }
    @Override
    public void run() {
        update();
    }

    @Override
    public void update() {
        if(configuration.continous){
            for(int i=1;i<=configuration.MaxInterval;i++){
                ((RandomClassification)classificationAlgorithm).run();
                if(i%configuration.IterationInterval==0) {
                    List<Integer> list=classificationAlgorithm.getOutput();
                    display(getLinePoints(list));
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        //do something
                    }
                }
            }
        }else{
            if(currentIteration<=configuration.MaxInterval) {
                currentIteration += configuration.IterationInterval;
            }
        }
    }
    private Point2D[] getLinePoints(List<Integer> list){
        Point2D[] point2DS = new Point2D[2];
        point2DS[0]=new Point2D(MinX,(MinX*list.get(0)+list.get(2)/(-list.get(1))));
        point2DS[1]=new Point2D(MaxX,(MaxX*list.get(0)+list.get(2)/(-list.get(1))));
        return point2DS;
    }
    private void display(Point2D[] point2DS){
        XYChart.Series<Number,Number> series = new XYChart.Series<>();

        Platform.runLater(()->{
            if(chartOriginalDataSize!=chart.getData().size())
                chart.getData().remove(chart.getData().size()-1);
            series.getData().add(new XYChart.Data<>(point2DS[0].getX(),point2DS[0].getY()));
            series.getData().add(new XYChart.Data<>(point2DS[1].getX(),point2DS[1].getY()));
            chart.getData().add(series);
        });
    }

}
