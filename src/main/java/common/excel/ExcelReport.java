package common.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelReport {
    /**
     * @param xlsxFile: Test report file
     * @param result: Test result after running each test (Passed, Failed, Ignored)
     * @param sheetID: The sheet where test report has been created (sheetID start from 0)
     * @param rowCount: The row where testcase name, test result and exception has been written
     */
    public void generateExcelReportAfterRunTest(File xlsxFile, ITestResult result, int sheetID, int rowCount) throws IOException {
        FileInputStream inputStream = new FileInputStream(xlsxFile);

        //Creating workbook from input stream
        Workbook workbook = WorkbookFactory.create(inputStream);

        //Reading first sheet of Excel file
        Sheet sheet = workbook.getSheetAt(sheetID);

        //Getting the count of existing record
//        int rowCount = sheet.getLastRowNum();

        //Creating new row from the next row count
        Row row = sheet.createRow(++rowCount);

        // Write testcase name to Excel file
        row.createCell(0).setCellValue(result.getName());

        //Write test result to Excel file
        switch (result.getStatus()) {
            case 1 -> row.createCell(1).setCellValue("Passed");
            case 2 -> row.createCell(1).setCellValue("Failed");
            case 3 -> row.createCell(1).setCellValue("Ignored");
        }

        //Close input stream
        inputStream.close();

        //Crating output stream and writing the updated workbook
        FileOutputStream os = new FileOutputStream(xlsxFile);
        workbook.write(os);

        //Close the workbook and output stream
        workbook.close();
        os.close();
    }
}
