package kronos.price.exe.regression.suite.testSuite;

import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class BootstrapWork {

    @BeforeSuite
    public void bootstrap() throws Exception {
        String driverFile = System.getProperty("testDriverFile");
        System.out.println("I am at bootstrap with property = " + driverFile);

        try (InputStream input = new FileInputStream(driverFile)) {
            Properties prop = new Properties();
            prop.load(input);
            Set<String> keys = prop.stringPropertyNames();
            for (String key : keys) {
                String value = System.getenv(key);
                if (value == null) {
                    value = prop.getProperty(key);
                    System.setProperty(key, value);
                    System.out.println("Taking the property from properties file setup for key = " + key);
                } else {
                    System.setProperty(key, value);
                    System.out.println("Taking the property from upstream setup for key = " + key);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Properties file not found: " + e.getMessage());
            throw e;
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
            throw e;

        }

    }
}