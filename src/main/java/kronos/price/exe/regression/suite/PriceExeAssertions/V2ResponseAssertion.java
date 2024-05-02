package kronos.price.exe.regression.suite.PriceExeAssertions;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import kronos.price.exe.regression.suite.constant.ColumnConstants;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class V2ResponseAssertion {
    public static void responseValidator(String testCase, String description, String response) throws IOException {

        String excelFilePath = System.getProperty("PRICE.EXECUTION.V2.FOLDER.EXCEL.PATH");
        FileInputStream file = new FileInputStream(excelFilePath);
        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet sh = wb.getSheet("sheet1");
        int rowCount=0;
        for (int i = 2; i <= sh.getLastRowNum(); i++) {
            String cellValue = sh.getRow(i).getCell(ColumnConstants.TESTCASE).getStringCellValue();
            if (cellValue.equals(testCase)) {
                rowCount=i;
                break;
            }
        }


         Allure.addAttachment("TESTCOUNT is :" , String.valueOf(rowCount));



        try {
            JsonPath responseJsonPath = new JsonPath(response);
            System.out.println(responseJsonPath);
            String sourceSystemIdentifierActual = responseJsonPath.get("demurrageAndDetentionAgreementLines[0].demurrageAndDetentionAgreement.references[1].referenceTypeEnum");
            String calculationEventTriggerActual = responseJsonPath.get("demurrageAndDetentionAgreementLines[0].calculationEventTriggers[0]");
            String chargeTypeActual = responseJsonPath.get("demurrageAndDetentionAgreementLines[0].demurrageAndDetentionAgreement.pricingParameters[1].pricingParameterValue");
            String agreementTypeActual = responseJsonPath.get("demurrageAndDetentionAgreementLines[0].demurrageAndDetentionAgreement.agreementType.agreementTypeCode");
            String freeTimeActual = responseJsonPath.get("demurrageAndDetentionAgreementLines[0].freeTime");
            List<Object> charges = responseJsonPath.getList("demurrageAndDetentionAgreementLines[1].charges");


            String currencyActual = responseJsonPath.get("demurrageAndDetentionAgreementLines[1].charges[0].unitPrice.unit");
            String agreementEffectiveDateTimeActual = responseJsonPath.get("demurrageAndDetentionAgreementLines[0].demurrageAndDetentionAgreement.agreementEffectiveDatetime");
            String agreementExpirationDateTimeActual = responseJsonPath.get("demurrageAndDetentionAgreementLines[0].demurrageAndDetentionAgreement.agreementExpirationDatetime");



            System.out.println("sourceSystemIdentifier   :" + sourceSystemIdentifierActual);
            System.out.println("calculationEventTriggerResponse   :" + calculationEventTriggerActual);
            System.out.println("chargeTypeResponse   :" + chargeTypeActual);
            System.out.println("agreementTypeResponse   :" + agreementTypeActual);

            System.out.println("freeTimeResponse  :" + freeTimeActual);
            //System.out.println("rateTimeResponse  :" + rateTimeResponse);
            System.out.println("currencyResponse  :" + currencyActual);
            System.out.println("agreementEffectiveDateTime  :" + agreementEffectiveDateTimeActual);
            System.out.println("agreementExpirationDateTime  :" + agreementExpirationDateTimeActual);

            System.out.println("ROW COUNT is ***********" +rowCount);
            String sourceSystemIdentifierExpected = sh.getRow(rowCount).getCell(37).getStringCellValue().trim();
            String calculationEventTriggerExpected = sh.getRow(rowCount).getCell(38).getStringCellValue().trim();
            String chargeTypeExpected = sh.getRow(rowCount).getCell(39).getStringCellValue().trim();
            String agreementTypeExpected = sh.getRow(rowCount).getCell(40).getStringCellValue().trim();
            String freeTimeExpected = sh.getRow(rowCount).getCell(41).getStringCellValue().trim();
            String rateTimeExpected = sh.getRow(rowCount).getCell(42).getStringCellValue().trim();
            String currencyExpected = sh.getRow(rowCount).getCell(43).getStringCellValue().trim();
            String agreementEffectiveDateTimeExpected = sh.getRow(rowCount).getCell(44).getStringCellValue().trim();
            String agreementExpirationDateTimeExpected = sh.getRow(rowCount).getCell(45).getStringCellValue().trim();

            SourceSystemIdentifier.sourceSystemIdentifierAssertion(sourceSystemIdentifierExpected, sourceSystemIdentifierActual);
            CalculationEventTrigger.calculationEventTriggerAssertion(calculationEventTriggerExpected, calculationEventTriggerActual);
            ChargeType.chargeTypeAssertion(chargeTypeExpected, chargeTypeActual);
            AgreementType.agreementTypeAssertion(agreementTypeExpected, agreementTypeActual);
            FreeTime.freeTimeAssertion(freeTimeExpected, freeTimeActual);
            RateTime.rateTimeAssertion(rateTimeExpected,charges,responseJsonPath);
            Currency.currencyAssertion(currencyExpected, currencyActual);
            AgreementEffectiveDate.agreementEffectiveDateAssertion(agreementEffectiveDateTimeExpected, agreementEffectiveDateTimeActual);
            AgreementExpirationDate.agreementExpirationDateAssertion(agreementExpirationDateTimeExpected, agreementExpirationDateTimeActual);

             rowCount=rowCount+1;
            System.out.println("Total number of calls to responseValidator is " + rowCount);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static class AgreementType {
        public static void agreementTypeAssertion(String expected, String actual) {
            AssertionHelper.assertEqual(expected, actual, "Agreement Type");
        }
    }

    private static class FreeTime {
        public static void freeTimeAssertion(String expected, String actual) {
            AssertionHelper.assertEqual(expected, actual, "Free Time");
        }
    }

    private static class Currency {
        public static void currencyAssertion(String expected, String actual) {
            AssertionHelper.assertEqual(expected, actual, "Currency");
        }
    }

    private static class AgreementEffectiveDate {
        public static void agreementEffectiveDateAssertion(String expected, String actual) {
            AssertionHelper.assertEqual(expected, actual, "Agreement Effective Date");
        }
    }

    private static class AgreementExpirationDate {
        public static void agreementExpirationDateAssertion(String expected, String actual) {
            AssertionHelper.assertEqual(actual, expected, "Agreement Expiration Date");
        }
    }

    private static class AssertionHelper {

        public static void assertEqual(String expected, String actual, String columnName) {
            Assert.assertEquals(actual, expected);
            Allure.addAttachment("Assertion Result for " + columnName, "Expected: " + expected + "\nActual: " + actual + "\nColumn: " + columnName);
        }
    }

    /**
     * Asserts that the source system identifier matches the expected value.
     * If the assertion fails, it adds the result to the Allure report.
     * <p>
     * sourceSystemIdentifierActual The actual source system identifier value.
     * sourceSystemIdentifierExpected The expected source system identifier value.
     */
    private  static class SourceSystemIdentifier {
        @Step("Verify sourceSystemIdentifier")
        public static void sourceSystemIdentifierAssertion(String sourceSystemIdentifierActual, String sourceSystemIdentifierExpected) {
            try {


                AssertionHelper.assertEqual(sourceSystemIdentifierActual, sourceSystemIdentifierExpected, "Source system identifier");
                Allure.addAttachment("Assertion Result sourceSystemIdentifierActual ", "Expected: " + sourceSystemIdentifierActual + "\nActual: " + sourceSystemIdentifierExpected + "\nColumn: AL");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static class CalculationEventTrigger {
        public static void calculationEventTriggerAssertion(String calculationEventTriggerExpected, String calculationEventTriggerActual) {
            try {


                AssertionHelper.assertEqual(calculationEventTriggerActual, calculationEventTriggerExpected, "Calculation event trigger");
                Allure.addAttachment("Assertion Result CalculationEventTrigger ", "Expected: " + calculationEventTriggerExpected + "\nActual: " + calculationEventTriggerActual + "\nColumn: AL");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

   private static class ChargeType {
        public static void chargeTypeAssertion(String calculationEventTriggerExpected, String calculationEventTriggerActual) {
            try {


                AssertionHelper.assertEqual(calculationEventTriggerActual, calculationEventTriggerExpected, "Charge type assertion");
                Allure.addAttachment("Assertion Result calculationEventTrigger ", "Expected: " + calculationEventTriggerExpected + "\nActual: " + calculationEventTriggerActual + "\nColumn: AL");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static class RateTime {
        public static void rateTimeAssertion(String rateTimeExpected, List<Object> charges,JsonPath responseJsonPath) {
            String rateTimeActual= getRates(charges,responseJsonPath);

            //AssertionHelper.assertEqual(rateTimeActual, rateTimeExpected, "Charge type assertion");
            Allure.addAttachment("Assertion Result rateTimeExpected ", "Expected: " + rateTimeExpected + "\nActual: " + rateTimeActual + "\nColumn: AL");


        }
        private static String getRates(List<Object> charges,JsonPath jsonPath ) {
            StringBuilder sb = new StringBuilder();

            try {


               for (int i = 0; i < charges.size(); i++) {
                   Map<String, Object> charge = (Map<String, Object>) charges.get(i);
                   int rateBasisLowerValue = (int) charge.get("rateBasisLowerValue");
                   int rateBasisUpperValue = (int) charge.get("rateBasisUpperValue");
                   Map<String, Object> unitPrice = (Map<String, Object>) charge.get("unitPrice");
                   String unitPriceValue = (String) unitPrice.get("value");
                   String unitPriceUnit = (String) unitPrice.get("unit");

                   System.out.println("Charge " + (i + 1) + ":");
                   System.out.println("Rate Basis Lower Value: " + rateBasisLowerValue);
                   System.out.println("Rate Basis Upper Value: " + rateBasisUpperValue);
                   System.out.println("Unit Price Value: " + unitPriceValue);
                   System.out.println("Unit Price Unit: " + unitPriceUnit);


                   sb.append(rateBasisLowerValue)
                           .append("-")
                           .append(rateBasisUpperValue)
                           .append(":")
                           .append(unitPriceValue);

                   if (i < charges.size() - 1) {
                       sb.append(",");
                   }
               }
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }
            return sb.toString();
        }


    }



}

