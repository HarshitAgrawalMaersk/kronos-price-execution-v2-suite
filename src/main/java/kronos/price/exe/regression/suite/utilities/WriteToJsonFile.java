package kronos.price.exe.regression.suite.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriteToJsonFile {

    public static void writeJsonToFile(String json, String filePath, String fileName) {
        System.out.println("Entered into the writeJsonFile method for configProfile Response");
        try {
            // Convert JSON string to JSON object
            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(json, Object.class);

            // Create the file path
            Path filePathWithFileName = Paths.get(filePath, fileName);
            File file = new File(filePathWithFileName.toString());

            // Write JSON object to file
            mapper.writeValue(file, jsonObject);
            System.out.println("JSON written to file successfully.");
        } catch (Exception e) {
            System.out.println("Error while writing JSON to file: " + e.getMessage());
        }
    }
}
