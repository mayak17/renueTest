package services;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigImporter {
    private String fileName;
    private int indexedColumn;

    public ConfigImporter(String[] args) throws Exception {
        FileInputStream stream = new FileInputStream("src/main/resources/config.properties");

        Properties properties = new Properties();
        properties.load(stream);

        this.fileName = properties.getProperty("filename");
        if (this.fileName == null) throw new Exception("Не указан fileName в настройках!");
        if (args.length > 0) {
            this.indexedColumn = Integer.parseInt(args[0]);
        }
        else {
            this.indexedColumn = Integer.parseInt(properties.getProperty("column"));
        }
    }

    public String getFileName() {
        return fileName;
    }

    public int getIndexedColumn() {
        return indexedColumn;
    }
}
