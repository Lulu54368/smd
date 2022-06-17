package properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PropertiesLoader {

    public static final String DEFAULT_DIRECTORY_PATH = "properties/";

    public static java.util.Properties loadPropertiesFile(String propertiesFile) {
        if (propertiesFile == null) {
            try (InputStream input = new FileInputStream(DEFAULT_DIRECTORY_PATH + "runmode.properties")) {
                java.util.Properties prop = new java.util.Properties();
                // load a properties file
                prop.load(input);
                propertiesFile = DEFAULT_DIRECTORY_PATH + prop.getProperty("current_mode");
                System.out.println(propertiesFile);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try (InputStream input = new FileInputStream(propertiesFile)) {
            java.util.Properties prop = new java.util.Properties();
            // load a properties file
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
