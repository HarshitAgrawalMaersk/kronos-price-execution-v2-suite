package kronos.price.exe.regression.suite.requestMapper;




import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import kronos.price.exe.regression.suite.constant.ColumnConstants;
import kronos.price.exe.regression.suite.constant.Constants;
import kronos.price.exe.regression.suite.helper.ConfigProfileJsonTransformation;
import kronos.price.exe.regression.suite.model.PriceExceution.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class PriceSearchRequestExecutionMapper {

    public static TreeMap<String, String> map = new TreeMap<String, String>();

    public static TreeMap priceSearchRequestToJSON(int rowCount) {

        try {
            System.out.println("Row number currently fetching is " +rowCount);
            String excelFilePath=System.getProperty("PRICE.EXECUTION.V2.FOLDER.EXCEL.PATH");
            FileInputStream file = new FileInputStream(excelFilePath);
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sh = wb.getSheet("sheet1");

            int startRow= Constants.START_ROW;
            String testcase=null;
            for(int r=startRow;r<= sh.getLastRowNum();r++) {
                System.out.println("Running TESTCASE : ****** " +r);
                try {
                     testcase = sh.getRow(r).getCell(ColumnConstants.TESTCASE).getStringCellValue().trim();
                    System.out.println("***************EXCEL****************");
                    System.out.println(sh.getRow(r).getCell(ColumnConstants.TESTCASE).getStringCellValue().trim());
                    String pcd = sh.getRow(r).getCell(ColumnConstants.PCD).getStringCellValue().trim();
                    String brandCode = sh.getRow(r).getCell(ColumnConstants.BRAND_CODE).getStringCellValue().trim();
                    String transportActivity = sh.getRow(r).getCell(ColumnConstants.TRANSPORT_ACTIVITY).getStringCellValue().trim();
                    String agreementType = sh.getRow(r).getCell(ColumnConstants.AGREEMENT_TYPE).getStringCellValue().trim();
                    String agreementlineTypeCodes = sh.getRow(r).getCell(ColumnConstants.AGREEMENT_LINE_TYPE_CODES).getStringCellValue().trim();
                    String chargeTypes = sh.getRow(r).getCell(ColumnConstants.CHARGE_TYPES).getStringCellValue().trim();
                    String cityCodeDestination = sh.getRow(r).getCell(ColumnConstants.CITY_CODE_DESTINATION).getStringCellValue().trim();
                    String countryCodeDestination = sh.getRow(r).getCell(ColumnConstants.COUNTRY_CODE_DESTINATION).getStringCellValue().trim();
                    String facilityCodeDestination = sh.getRow(r).getCell(ColumnConstants.FACILITY_CODE_DESTINATION).getStringCellValue().trim();
                    String cityCodePOD = sh.getRow(r).getCell(ColumnConstants.CITY_CODE_POD).getStringCellValue().trim();
                    String countryCodePOD = sh.getRow(r).getCell(ColumnConstants.COUNTRY_CODE_POD).getStringCellValue().trim();
                    String facilityCodePOD = sh.getRow(r).getCell(ColumnConstants.FACILITY_CODE_POD).getStringCellValue().trim();
                    String cityCodePODLY = sh.getRow(r).getCell(ColumnConstants.CITY_CODE_PODLY).getStringCellValue().trim();
                    String countryCodePODLY = sh.getRow(r).getCell(ColumnConstants.COUNTRY_CODE_PODLY).getStringCellValue().trim();
                    String facilityCodePODLY = sh.getRow(r).getCell(ColumnConstants.FACILITY_CODE_PODLY).getStringCellValue().trim();
                    String cityCodePOR = sh.getRow(r).getCell(ColumnConstants.CITY_CODE_POR).getStringCellValue().trim();
                    String countryCodePOR = sh.getRow(r).getCell(ColumnConstants.COUNTRY_CODE_POR).getStringCellValue().trim();
                    String facilityCodePOR = sh.getRow(r).getCell(ColumnConstants.FACILITY_CODE_POR).getStringCellValue().trim();
                    String equipmentSizeTypeCode = sh.getRow(r).getCell(ColumnConstants.EQUIPMENT_SIZE_TYPE_CODE).getStringCellValue().trim();
                    String commodityCode = sh.getRow(r).getCell(ColumnConstants.COMMODITY_CODE).getStringCellValue().trim();
                    String serviceContract = sh.getRow(r).getCell(ColumnConstants.SERVICE_CONTRACT).getStringCellValue().trim();
                    String routeCode = sh.getRow(r).getCell(ColumnConstants.ROUTE_CODE).getStringCellValue().trim();
                    String finalDestination = sh.getRow(r).getCell(ColumnConstants.FINAL_DESTINATION).getStringCellValue().trim();
                    String service = sh.getRow(r).getCell(ColumnConstants.SERVICE).getStringCellValue().trim();
                    String option1 = sh.getRow(r).getCell(ColumnConstants.OPTION1).getStringCellValue().trim();
                    String shipper = sh.getRow(r).getCell(ColumnConstants.SHIPPER).getStringCellValue().trim();
                    String consignee = sh.getRow(r).getCell(ColumnConstants.CONSIGNEE).getStringCellValue().trim();
                    String IMO = sh.getRow(r).getCell(ColumnConstants.IMO).getStringCellValue().trim();
                    String haulage = sh.getRow(r).getCell(ColumnConstants.HAULAGE).getStringCellValue().trim();
                    String cargoType = sh.getRow(r).getCell(ColumnConstants.CARGO_TYPE).getStringCellValue().trim();
                    String searchContext = sh.getRow(r).getCell(ColumnConstants.SEARCH_CONTEXT).getStringCellValue().trim();
                    String searchConfigurations = sh.getRow(r).getCell(ColumnConstants.SEARCH_CONFIGURATIONS).getStringCellValue().trim();
                    String equipmentEventTransportModes = sh.getRow(r).getCell(ColumnConstants.EQUIPMENT_EVENT_TRANSPORT_MODES).getStringCellValue().trim();


                    List<Locations> locations = excelDataToJavaObject(cityCodeDestination, cityCodePODLY, cityCodePOR, cityCodePOD, countryCodeDestination, countryCodePODLY, countryCodePOR, countryCodePOD, facilityCodeDestination, facilityCodePODLY, facilityCodePOD, facilityCodePOR);
                    EquipmentTypes equipmentTypes=equipmentTypesMapper(commodityCode,equipmentSizeTypeCode);

                    SearchContext searchContextObject =searchContextMapper(searchContext);


                    List<EquipmentEventTransportModes> equipmentEventTransportModesList = null;
                    equipmentEventTransportModesList= equipmentEventTransportModesMapper(equipmentEventTransportModes);

                    List<String> agreementTypeCodeList = Arrays.asList(agreementlineTypeCodes.replaceAll("[{}]", "").split(","));

                    List<String> chargeTypeCodesList = Arrays.asList(chargeTypes.replaceAll("[{}]", "").split(","));

                    List<PricingParameters> pricingParametersList=PricingParametersMapper.mapAndFilterParameters(serviceContract, routeCode, finalDestination,
                            shipper, consignee, IMO, cargoType,
                            haulage, service, option1);
                    PriceExecutionRequest priceExecutionRequest = PriceExecutionRequest
                            .builder()
                            .priceCalculationBaseDatetime(pcd)
                            .brandCode(brandCode)
                            .transportActivity(transportActivity)
                            .agreementType(agreementType)
                            .agreementLineTypeCodes(agreementTypeCodeList)
                            .chargeTypes(chargeTypeCodesList)
                            .locations(locations)
                            .equipmentTypes(List.of(equipmentTypes))
                            .pricingParameters(pricingParametersList)
                            .searchContexts(List.of(searchContextObject))
                            .searchConfigurations(List.of())
                            .equipmentEventTransportModes(equipmentEventTransportModesList)
                            .build();
                     if(priceExecutionRequest != null) {
                         attachConfigProfileTransformation(priceExecutionRequest, testcase);
                     }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            wb.close();




        } catch (IOException e) {
            e.printStackTrace();
        }

        return  map;
    }

    private static void attachConfigProfileTransformation(PriceExecutionRequest priceExecutionRequest,String testcase) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String json = objectMapper.writeValueAsString(priceExecutionRequest);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode newSearchConfigurations = mapper.readTree(ConfigProfileJsonTransformation.configProfileConvertedResponse.get(testcase));


            JsonNode originalJson = mapper.readTree(json);

            ((ArrayNode) originalJson.get("searchConfigurations")).add(newSearchConfigurations);


            String modifiedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(originalJson);
            map.put(testcase,modifiedJson);
            String resourcesFolder =System.getProperty("PRICE.EXECUTION.V2.RESOURCES.FOLDER.PATH");

            writeJsonToFile(originalJson, resourcesFolder+"priceExecutionRequest\\", testcase);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static List<EquipmentEventTransportModes> equipmentEventTransportModesMapper(String equipmentEventTransportModesString) {
        List<EquipmentEventTransportModes> modes = new ArrayList<>();

        String[] parts = equipmentEventTransportModesString.split(",");
        for (String part : parts) {
            EquipmentEventTransportModes mode = new EquipmentEventTransportModes();
            String[] keyValue = part.split(":");
            if (keyValue.length == 1) {
                mode.setStartEvent(keyValue[0]);
            } else if (keyValue.length == 2) {
                mode.setStartEvent(keyValue[0]);
                mode.setTransportModeCode(keyValue[1]);
            }
            modes.add(mode);
        }
        return modes;
    }


    private static SearchContext searchContextMapper(String searchContext) {
      SearchContext searchContext1=  SearchContext.builder()
                .contextName("STRATEGY_HINT")
                .contextValue(searchContext)
                .build();

     return searchContext1;
    }


    public static EquipmentTypes equipmentTypesMapper(String commodityCode,String equipmentSizeTypeCode) {
        CommodityClassification commodityClassification = new CommodityClassification();
        commodityClassification.setCommodityCode(commodityCode);

        EquipmentTypes equipmentTypes = EquipmentTypes.builder()
                .equipmentSizeTypeCode(equipmentSizeTypeCode)
                .commodityClassification(List.of(commodityClassification))
                .build();
        return equipmentTypes;
    }

    public static List<Locations> excelDataToJavaObject(
            String cityCodeDestination,String cityCodePODLY,String cityCodePOR,String cityCodePOD,String countryCodeDestination,String countryCodePODLY,String countryCodePOR,String countryCodePOD,String facilityCodeDestination,String facilityCodePODLY,String facilityCodePOD,String facilityCodePOR
    )
    {

        List<Locations> locations = new ArrayList<>();
        Locations destination = Locations.builder()
                .locationFunction("DESTINATION")
                .cityCode(cityCodeDestination)
                .countryCode(countryCodeDestination)
                .facilityCode(facilityCodeDestination)
                .build();

        Locations portOfDischarge = Locations.builder()
                .locationFunction("PORT_OF_DISCHARGE")
                .cityCode(cityCodePOD)
                .countryCode(countryCodePOD)
                .facilityCode(facilityCodePOD)
                .build();

        Locations placeOfDelivery = Locations.builder()
                .locationFunction("PLACE_OF_DELIVERY")
                .cityCode(cityCodePODLY)
                .countryCode(countryCodePODLY)
                .facilityCode(facilityCodePODLY)
                .build();
        locations.add(destination);
        locations.add(portOfDischarge);
        locations.add(placeOfDelivery);
        Locations placeOfReceipt = null;
        if (!cityCodePOR.equals("NA") && !countryCodePOR.equals("NA") && !facilityCodePOR.equals("NA") )
        {
            System.out.println("cityCodePOR :" + cityCodePOR);
            System.out.println("countryCodePOR :" + countryCodePOR);
            System.out.println("facilityCodePOR :" + facilityCodePOR);
            placeOfReceipt = Locations.builder()
                    .locationFunction("PLACE_OF_RECEIPT")
                    .cityCode(cityCodePOR)
                    .countryCode(countryCodePOR)
                    .facilityCode(facilityCodePOR)
                    .build();
            locations.add(placeOfReceipt);
        } else {
            System.out.println("PLACE_OF_RECEIPT : is not available");
        }



        System.out.println("Locations Array :" + locations);

        return locations;

    }

    private static void writeJsonToFile(JsonNode json, String filePath,String testcase){
        try {
            String extentionType=Constants.TESTDATA_EXTENTION_TYPE;
            String filenameInitial=Constants.FILE_NAME_INITIALS_PRICE_EXE_REQUEST;
            String fileName = filenameInitial + testcase + extentionType;
            ObjectMapper mapper = new ObjectMapper();

            // Create the file path
            Path filePathWithFileName = Paths.get(filePath, fileName);
            File file = new File(filePathWithFileName.toString());

            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
                FileChannel channel = randomAccessFile.getChannel();
                channel.truncate(0);
            }

            mapper.writeValue(new FileWriter(filePathWithFileName.toString()),
                    json);
            System.out.println("JSON written to file successfully.");
        }
        catch (Exception e){
            System.out.println("Error while creating new json " +e );
        }

    }


}

