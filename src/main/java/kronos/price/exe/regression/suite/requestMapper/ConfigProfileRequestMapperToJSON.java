package kronos.price.exe.regression.suite.requestMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kronos.price.exe.regression.suite.model.configProfile.ConfigProfileIdentifier;
import kronos.price.exe.regression.suite.model.configProfile.ConfigProfileRequest;
import kronos.price.exe.regression.suite.model.configProfile.Location;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigProfileRequestMapperToJSON {

    public static String excelDataToJSON(String facilityCode,String chargeTypes) {
        String json = null;
         try {
             Location location = Location.builder()
                     .facilityCode(facilityCode)
                     .build();
             List<String> mappedList = new ArrayList<>();

             System.out.println("Charge Types from the Excel :" +chargeTypes);

             // Split the chargeTypes string by comma
             String[] types = chargeTypes.split(",");

             for (String type : types) {
                 switch (type.trim()) {
                     case "DETENTION":
                         mappedList.add("DET");
                         break;
                     case "DEMURRAGE":
                         mappedList.add("DMR");
                         break;
                     default:
                         break;
                 }
             }

             System.out.println("ChargeType List" +chargeTypes);
             List<ConfigProfileIdentifier> configurationProfileIdentifiers = new ArrayList<>();
             configurationProfileIdentifiers.add(ConfigProfileIdentifier.builder()
                     .configurationProfileIdentifierName("status")
                     .configurationProfileIdentifierValues(List.of("ACTIVE"))
                     .build());
             configurationProfileIdentifiers.add(ConfigProfileIdentifier.builder()
                     .configurationProfileIdentifierName("chargeTypes")
                     .configurationProfileIdentifierValues(mappedList)
                     .build());


             ConfigProfileRequest configProfileRequest = ConfigProfileRequest.builder()
                     .configurationProfileType("PRICING_PARAMETERS_CONFIG")
                     .location(location)
                     .transportActivity("IMP")
                     .configurationProfileIdentifiers(configurationProfileIdentifiers)
                     .build();


             ObjectMapper mapper = new ObjectMapper();

             try {
                 if (configProfileRequest != null) {
                     json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configProfileRequest);
                     // System.out.println("*************Config Profile json for facility"+location.getFacilityCode() +"---- *************" + json);
                 } else {
                     System.out.println("configProfileRequest is null");
                 }
             } catch (JsonProcessingException e) {
                 e.printStackTrace();
             }

         }
         catch (Exception e)
         {
             System.out.println("Error in creating config profile request");
         }
        return json;
    }
}