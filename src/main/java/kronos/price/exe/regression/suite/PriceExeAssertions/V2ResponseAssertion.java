package kronos.price.exe.regression.suite.PriceExeAssertions;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import kronos.price.exe.regression.suite.testSuite.TestExecution;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import java.io.FileInputStream;
import java.io.IOException;

public class V2ResponseAssertion {
     public static void responseValidator(String testCase,String description,String response) throws IOException {

         try {
             JsonPath responseJsonPath = new JsonPath(response);
             System.out.println(responseJsonPath);
             String sourceSystemIdentifierExpected=responseJsonPath.get("demurrageAndDetentionAgreementLines[0].demurrageAndDetentionAgreement.references[1].referenceTypeEnum");
             String calculationEventTriggerResponse = responseJsonPath.get("demurrageAndDetentionAgreementLines[0].calculationEventTriggers[0]");
             String chargeTypeResponse=responseJsonPath.get("demurrageAndDetentionAgreementLines[0].demurrageAndDetentionAgreement.pricingParameters[1].pricingParameterValue") ;
             String agreementTypeResponse= responseJsonPath.get("demurrageAndDetentionAgreementLines[0].demurrageAndDetentionAgreement.agreementType.agreementTypeCode");
             String freeTimeResponse=responseJsonPath.get("demurrageAndDetentionAgreementLines[0].freeTime");
             //String rateTimeResponse = ; //TODO
             String currencyResponse= responseJsonPath.get("demurrageAndDetentionAgreementLines[1].charges[0].unitPrice.unit");
             String agreementEffectiveDateTime=responseJsonPath.get("demurrageAndDetentionAgreementLines[0].demurrageAndDetentionAgreement.agreementEffectiveDatetime") ;
             String agreementExpirationDateTime=responseJsonPath.get("demurrageAndDetentionAgreementLines[0].demurrageAndDetentionAgreement.agreementExpirationDatetime");





             String excelFilePath = System.getProperty("PRICE.EXECUTION.V2.FOLDER.EXCEL.PATH");
             FileInputStream file = new FileInputStream(excelFilePath);
             XSSFWorkbook wb = new XSSFWorkbook(file);
             XSSFSheet sh = wb.getSheet("sheet1");
             System.out.println("sourceSystemIdentifier   :" + sourceSystemIdentifierExpected);
             System.out.println("calculationEventTriggerResponse   :" + calculationEventTriggerResponse);
             System.out.println("chargeTypeResponse   :" + chargeTypeResponse);
             System.out.println("agreementTypeResponse   :" + agreementTypeResponse);

             System.out.println("freeTimeResponse  :" + freeTimeResponse);
             //System.out.println("rateTimeResponse  :" + rateTimeResponse);
             System.out.println("currencyResponse  :" + currencyResponse);
             System.out.println("agreementEffectiveDateTime  :" + agreementEffectiveDateTime);
             System.out.println("agreementExpirationDateTime  :" + agreementExpirationDateTime);
             String sourceSystemIdentifierActual =sh.getRow(2).getCell(37).getStringCellValue().trim();

             SourceSystemIdentifier.sourceSystemIdentifierAssertion(sourceSystemIdentifierActual,sourceSystemIdentifierExpected);


             System.out.println("EXCEL sourceSystemIdentifier    :" + sh.getRow(2).getCell(37));
//             System.out.println("EXCEL calculationEventTriggerResponse   : " + sh.getRow(2).getCell(37));
//             System.out.println("EXCEL freeTimeResponse      : " + sh.getRow(2).getCell(38));
//             System.out.println("EXCEL     : " + sh.getRow(2).getCell(39));
//             System.out.println("EXCEL     : " + sh.getRow(2).getCell(40));
//             System.out.println("EXCEL     :" + sh.getRow(2).getCell(41));
//             System.out.println("EXCEL    : " + sh.getRow(2).getCell(41));
//             System.out.println("EXCEL       : " + sh.getRow(2).getCell(43));
//             System.out.println("EXCEL     : " + sh.getRow(2).getCell(44));

             System.out.println("Total number of calls to responseValidator is " + TestExecution.rowCount++);

         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

     }


}
/**
 * Asserts that the source system identifier matches the expected value.
 * If the assertion fails, it adds the result to the Allure report.
 *
 *  sourceSystemIdentifierActual The actual source system identifier value.
 *  sourceSystemIdentifierExpected The expected source system identifier value.
 */
  class SourceSystemIdentifier {
      @Step("Verify sourceSystemIdentifier")
      public static void sourceSystemIdentifierAssertion(String sourceSystemIdentifierActual,String sourceSystemIdentifierExpected) {
            try {


                Assert.assertEquals(sourceSystemIdentifierActual , sourceSystemIdentifierExpected);
                Allure.addAttachment("Assertion Result sourceSystemIdentifierActual ", "Expected: " + sourceSystemIdentifierActual + "\nActual: " + sourceSystemIdentifierExpected + "\nColumn: AL");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
      }
  }
