package dataProcessors;

import vilij.components.DataComponent;
import vilij.templates.ApplicationTemplate;

import java.nio.file.Path;

public class AppData implements DataComponent {

    private ApplicationTemplate applicationTemplate;

    public AppData(ApplicationTemplate applicationTemplate){
        this.applicationTemplate=applicationTemplate;
    }

    @Override
    public void loadData(Path dataFilePath) {

    }

    @Override
    public void saveData(Path dataFilePath) {

    }

    @Override
    public void clear() {

    }
}
