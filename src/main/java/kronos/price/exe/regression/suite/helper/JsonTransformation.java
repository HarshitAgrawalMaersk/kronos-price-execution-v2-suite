package kronos.price.exe.regression.suite.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kronos.price.exe.regression.suite.constant.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JsonTransformation {

    public static Map<String, String> configProfileConvertedResponse = new HashMap<>();

    public static String jsonTransformation(String inputJson,int count,String facility) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputJson = mapper.createObjectNode();
        try {

            ArrayNode configurationInput = mapper.createArrayNode();

            mapper.readTree(inputJson).get("configurationProfiles").forEach(profile -> {
                ((ObjectNode) profile).get("configurationProfileElements").forEach(element -> {
                    ObjectNode configuration = mapper.createObjectNode();
                    configuration.put("configurationType", "PRICING_PARAM_CONFIG_PROFILE");

                    ObjectNode configurationInputItem = mapper.createObjectNode();
                    configurationInputItem.put("configurationInputName", "PRICING_PARAM_CONFIG_PROFILE_" + ((ObjectNode) element).get("priority").asText());
                    configurationInputItem.put("configurationInputDescription", "");

                    ArrayNode configurationInputParameters = mapper.createArrayNode();
                    configurationInputParameters.add(createParameterNode("status", ((ObjectNode) element).get("status").asText(), "SINGLE"));
                    configurationInputParameters.add(createParameterNode("priority", ((ObjectNode) element).get("priority").asText(), "SINGLE"));
                    configurationInputParameters.add(createParameterNode("agreementLineTypeCode", ((ObjectNode) profile).get("lineType").asText(), "SINGLE"));
                    configurationInputParameters.add(createParameterNode("chargeCode", ((ObjectNode) profile).get("chargeType").asText(), "SINGLE"));

                    ArrayNode pricingParameters = mapper.createArrayNode();
                    ((ObjectNode) element).get("pricingParameters").elements().forEachRemaining(pricingParameter -> {
                        pricingParameters.add(pricingParameter.asText());
                    });
                    configurationInputParameters.add(createParameterNode("pricingParameters", pricingParameters, "MULTIPLE"));
                    configurationInputItem.putArray("configurationInputParameters").addAll(configurationInputParameters);
                    outputJson.put("configurationType", "PRICING_PARAM_CONFIG_PROFILE");
                    configurationInput.add(configurationInputItem);
                    outputJson.set("configurationInput", configurationInput);

                });
            });

            String resourcesFolder =System.getProperty("PRICE.EXECUTION.V2.RESOURCES.FOLDER.PATH");

            writeJsonToFile(outputJson, resourcesFolder+"configProfileConvertedResponse\\",count-1,facility);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = null;
        try {
            json = mapper.writeValueAsString(outputJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("priceExecution dummy request " +json);
      return json;
    }

    private static ObjectNode createParameterNode(String name, Object value, String type) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode parameterNode = mapper.createObjectNode();
        parameterNode.put("parameterName", name);
        if (value instanceof String) {
            parameterNode.putArray("parameterValue").add((String) value);
        } else if (value instanceof ArrayNode) {
            parameterNode.set("parameterValue", (ArrayNode) value);
        }
        parameterNode.put("parameterType", type);
        return parameterNode;
    }

    private static void writeJsonToFile(ObjectNode json, String filePath,int count,String facilityCode) throws IOException {




        String extentionType= Constants.TESTDATA_EXTENTION_TYPE;
        String testcase="TC"+count;
        String fileInitialName=Constants.FILE_NAME_INITIALS_CONFIG_PROFILE_TRANSFORMED_RESPONSE;
        try {
            String fileName = fileInitialName + testcase +facilityCode  + extentionType;
            ObjectMapper mapper = new ObjectMapper();

            // Create the file path
            Path filePathWithFileName = Paths.get(filePath, fileName);
            File file = new File(filePathWithFileName.toString());

            if (!file.exists()) {
                file.createNewFile();
            }
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
                FileChannel channel = randomAccessFile.getChannel();
                channel.truncate(0);
            }

            ObjectMapper objectMapper = new ObjectMapper();

            configProfileConvertedResponse.put(testcase,objectMapper.writeValueAsString(json));
            mapper.writeValue(new FileWriter(filePathWithFileName.toString()),
                    json);
            System.out.println("JSON written to file successfully.");
        }
        catch (Exception e){
            System.out.println("Error while creating new json " +e );
        }

    }

}
