package kronos.price.exe.regression.suite.dataProvider;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelDataReader {
    Object[][] arrObj;

    @DataProvider(name = "priceExecutionV2",parallel = true)
    public static Object[][] excelDP()
    {

        String excelFilePath =System.getProperty("PRICE.EXECUTION.V2.FOLDER.EXCEL.PATH");


        Object[][] arrObj = getExcelData(excelFilePath,"sheet1");

        return arrObj;

    }
    public static Object[][] getExcelData(String fileName, String sheetName) {
        try (FileInputStream fis = new FileInputStream(fileName);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
           /// int noOfRows = sheet.getPhysicalNumberOfRows(); // Exclude header row
            int startingRow=2;
            int endRow=sheet.getPhysicalNumberOfRows();
            Object[][] data = new Object[endRow-startingRow][2];


            for (int i = startingRow; i <endRow; i++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(1);
                String cellValue = cell.getStringCellValue();
                String description=row.getCell(0).getStringCellValue();
                data[i - startingRow][0] = cellValue;
                data[i - startingRow][1] = description;

            }

            return data;
        } catch (Exception e) {
            System.out.println("Exception: {}" + e.getMessage());
            return new Object[0][0];
        }
    }
}

