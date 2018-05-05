package ui;

import javafx.stage.Stage;
import org.junit.Test;
import vilij.templates.ApplicationTemplate;

import static org.junit.Assert.*;

public class AppUITest {
    @Test
    public void ReadingValidLineTest(){
        AppUI ui= new AppUI(new Stage(),new ApplicationTemplate());
    }

}