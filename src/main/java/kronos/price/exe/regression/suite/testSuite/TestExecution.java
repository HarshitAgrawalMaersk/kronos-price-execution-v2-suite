package kronos.price.exe.regression.suite.testSuite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import groovy.util.logging.Slf4j;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import kronos.price.exe.regression.suite.PriceExeAssertions.V2ResponseAssertion;
import kronos.price.exe.regression.suite.constant.Constants;
import kronos.price.exe.regression.suite.dataProvider.ExcelDataReader;
import kronos.price.exe.regression.suite.helper.JsonTransformation;
import kronos.price.exe.regression.suite.requestMapper.ConfigProfileRequestMapperToJSON;
import kronos.price.exe.regression.suite.requestMapper.PriceSearchExecutionMapper;
import kronos.price.exe.regression.suite.utilities.WriteToJsonFile;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.io.*;
import java.util.TreeMap;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


@Slf4j

public class TestExecution {
    static TreeMap map = new TreeMap<>();
    public static int rowCount=2;
    static int testCaseCount=2;

    public static String configProfileSetup(String facilityCode,String chargeTypes)
    {

      String request=  ConfigProfileRequestMapperToJSON.excelDataToJSON(facilityCode,chargeTypes);

      return request;
    }



    public static TreeMap getMapDataForAssertion(String transformedResponse) throws IOException {

       map= PriceSearchExecutionMapper.priceSearchRequestToJSON(testCaseCount);

        return map;
    }


     @BeforeClass
     //DELETE the dump data
    public static void cleanUpTestDataFolders(ITestContext context){
        String resourcesFolder =System.getProperty("PRICE.EXECUTION.V2.RESOURCES.FOLDER.PATH");
        try {

            FileUtils.deleteDirectory(new File(resourcesFolder + "/priceExecutionResponse"));
            FileUtils.deleteDirectory(new File(resourcesFolder + "/configProfileConvertedResponse"));
            FileUtils.deleteDirectory(new File(resourcesFolder + "/priceExecutionRequest"));
            FileUtils.deleteDirectory(new File(resourcesFolder + "/configProfileResponses"));
            FileUtils.forceMkdir(new File(resourcesFolder + "/priceExecutionResponse"));
            FileUtils.forceMkdir(new File(resourcesFolder + "/configProfileConvertedResponse"));
            FileUtils.forceMkdir(new File(resourcesFolder + "/priceExecutionRequest"));
            FileUtils.forceMkdir(new File(resourcesFolder + "/configProfileResponses"));

            System.out.println("Test data folders cleaned up successfully.");
        } catch (IOException e) {
            System.out.println("Error while cleaning up test data folders: " + e.getMessage());
        }
    }




    @BeforeClass
    public static void setup(ITestContext context) throws IOException, InterruptedException {
        String facilityCode="";
        String excelFilePath =System.getProperty("PRICE.EXECUTION.V2.FOLDER.EXCEL.PATH");
            FileInputStream file = new FileInputStream(excelFilePath);
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sh = wb.getSheet("sheet1");


        String tokenPath = System.getProperty("CONFIG.PROFILE.TOKEN.ENDPOINT");

        String configProfileBasicAuthClientID = System.getProperty("CONFIG.PROFILE.TOKEN.ENDPOINT.BASICAUTH.CLIENT.ID");
        String configProfileBasicAuthClientSecret = System.getProperty("CONFIG.PROFILE.TOKEN.ENDPOINT.BASICAUTH.CLIENT.SECRET");

        RestAssured.filters(new AllureRestAssured(), new RequestLoggingFilter(), new ResponseLoggingFilter());

        String accessToken = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", configProfileBasicAuthClientID)
                .formParam("client_secret", configProfileBasicAuthClientSecret)
                .post(tokenPath)
                .then().log().all().extract().response()
                .jsonPath().getString("access_token");


         int startRow= Constants.START_ROW;
         for(int r=startRow;r<=sh.getLastRowNum();r++){

            facilityCode = sh.getRow(r).getCell(11).getStringCellValue().trim();
            String chargeTypes = sh.getRow(r).getCell(8).getStringCellValue().trim();
            System.out.println("Running Config profile get request method");


            context.setAttribute("C13_PRICE_SEARCH_ACCESS_TOKEN", accessToken);

             String configProfileUrl = System.getProperty("CONFIG.PROFILE.REQUEST.ENDPOINT");






            System.out.println("for testCAse count" + testCaseCount + "Facility location is" + facilityCode);
            String request = configProfileSetup(facilityCode,chargeTypes);



            String response = given()
                    .auth().oauth2(accessToken)
                    .contentType(JSON)
                    .body(request).log().all()
                    .header("API-Version", 1.0)
                    .header("max-traversal-level", "COUNTRY_GROUP")
                    .header("show-result", "ALL")
                    .when().post(configProfileUrl).then()
                    .assertThat().statusCode(200)
                    .extract().response().body().asString();
            String testCaseNumber= "TC"+(r-1);
            String extentionType=Constants.TESTDATA_EXTENTION_TYPE;
            String fileNameInitial=Constants.FILE_NAME_INITIALS_CONFIG_PROFILE_RESPONSE;
            String fileName = fileNameInitial + testCaseNumber + facilityCode+extentionType;
            String resourcesFolder =System.getProperty("PRICE.EXECUTION.V2.RESOURCES.FOLDER.PATH");

            WriteToJsonFile.writeJsonToFile(response,resourcesFolder+"configProfileResponses\\",fileName);
            String transformedResponse = JsonTransformation.jsonTransformation(response, r,facilityCode);


            getMapDataForAssertion(transformedResponse);

            try {

                System.out.println("*****************************");

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @Step("Testing important values of price execution like freeTime,RateTime etc.")
    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "priceExecutionV2", dataProviderClass = ExcelDataReader.class, priority = 1)
    public void testMethod(String testcase,String description, ITestContext context) throws IOException, InterruptedException {

        System.out.println("this is setup class " + testcase);

           String  accessToken = generateAccessToken();

        context.setAttribute("C13_PRICE_SEARCH_ACCESS_TOKEN", accessToken);
        String priceExecutionV2Request = getPriceExeRequestData(testcase);
        String priceExeRequestV2Url = System.getProperty("PRICE.EXECUTION.V2.REQUEST.ENDPOINT");

        long startTime = System.currentTimeMillis();
         Response responseTime = given()
                .auth().oauth2(accessToken)
                .contentType(JSON)
                .body(priceExecutionV2Request).log().all()
                .header("API-Version", 1.0)
                .header("API-Version", 1.0)
                .header("API-Version", 1.0)
                .when().post(priceExeRequestV2Url).then()
                .extract().response();
        String response = given()
                .auth().oauth2(accessToken)
                .contentType(JSON)
                .body(priceExecutionV2Request).log().all()
                .header("API-Version", 1.0)
                .header("API-Version", 1.0)
                .header("API-Version", 1.0)
                .when().post(priceExeRequestV2Url).then()
                .extract().response().body().asString();

        Allure.addAttachment("Response time is " , String.valueOf(responseTime.time()));
        String jsonString=JsonToStringConvertor(response);

        String filename =fileNameCreator(testcase);
        String resourcesFolder = System.getProperty("PRICE.EXECUTION.V2.RESOURCES.FOLDER.PATH");
        String filePath = resourcesFolder + "priceExecutionResponse\\";
        WriteToJsonFile.writeJsonToFile(jsonString, filePath, filename);
        V2ResponseAssertion.responseValidator(testcase,description,response);
    }


    private String fileNameCreator(String testcase) {
        String extentionType = Constants.TESTDATA_EXTENTION_TYPE;
        String fileNameInitials = Constants.FILE_NAME_INITIALS_PRICE_EXE_RESPONSE;
        String filename = fileNameInitials + testcase + extentionType;
        return  filename;
    }

    private String JsonToStringConvertor(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing
        JsonNode jsonResponse = mapper.readTree(response);
        String jsonString = mapper.writeValueAsString(jsonResponse);
        return jsonString;
    }

    public static String getPriceExeRequestData(String key) {
        return PriceSearchExecutionMapper.map.get(key);
    }
    private String generateAccessToken() {
        String tokenPath = System.getProperty("PRICE.EXECUTION.V2.TOKEN.ENDPOINT");

        String priceRequestV2eBasicAuthClientID = System.getProperty("PRICE.EXECUTION.V2.ENDPOINT.BASICAUTH.CLIENT.ID");
        String priceRequestV2BasicAuthClientSecret = System.getProperty("PRICE.EXECUTION.V2ENDPOINT.BASICAUTH.CLIENT.SECRET");
        String accessToken = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", priceRequestV2eBasicAuthClientID)
                .formParam("client_secret", priceRequestV2BasicAuthClientSecret)
                .post(tokenPath)
                .then().extract().response()
                .jsonPath().getString("access_token");

        return accessToken;
    }




}

