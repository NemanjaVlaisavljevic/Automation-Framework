package helpers.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ExcelHelper {

    private static final Logger log = LogManager.getLogger(ExcelHelper.class);

    /**
     * Method for parsing Excel file into java collections
     * @param filepath path to testParameters.xlsx file
     * @param sheetName sheet name which the data will be loaded from
     * @return ArrayList containing HashMap of test parameters
     */
    public static ArrayList<HashMap<String, String>> getDataFromExcel(String filepath, String sheetName) {

        ArrayList<HashMap<String, String>> excelFile = new ArrayList<>();
        try {
            log.info("Acquiring test parameters from {} with sheet name {}", filepath, sheetName);
            FileInputStream file = new FileInputStream(filepath);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row currentRow = sheet.getRow(i);
                HashMap<String, String> currentHash = new LinkedHashMap<>();
                for (int j = 0; j < currentRow.getLastCellNum(); j++) {
                    String cell = new DataFormatter().formatCellValue(currentRow.getCell(j));
                    currentHash.put(headerRow.getCell(j).getStringCellValue(), cell);
                }
                excelFile.add(currentHash);
            }
            file.close();
        } catch (IOException e) {
            log.error("Exception occurred while parsing the excel file into java collections.");
            e.printStackTrace();
        }
        return excelFile;
    }
}