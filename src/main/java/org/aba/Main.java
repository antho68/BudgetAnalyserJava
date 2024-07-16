package org.aba;

import org.aba.dto.BudgetDataDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello world!");

        try
        {
            FileInputStream file = new FileInputStream("/Users/aba/Downloads/comptes.xlsx");

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet ws = wb.getSheetAt(1);

            Collection<BudgetDataDto> budgetDataDtos = new ArrayList<>();

            Iterator<Row> rowIterator = ws.iterator();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                if (row.getRowNum() <= 4)
                {
                    continue;
                }

                System.out.print("Row:" + row.getRowNum() + " - ");

                BudgetDataDto budgetDataDto = new BudgetDataDto();
                budgetDataDtos.add(budgetDataDto);

                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    //System.out.print("Cell" + cell.getAddress() + " - ");
                    //Check the cell type and format accordingly
                    switch (cell.getCellType())
                    {
                        case NUMERIC:
                            System.out.println("Cell" + cell.getAddress() + ", Type: " + cell.getCellType()
                                                       + ", Value:" + cell.getNumericCellValue());
                            break;
                        case STRING:
                            budgetDataDto.setDescription(cell.getStringCellValue());
                            System.out.println("Cell" + cell.getAddress() + ", Type: " + cell.getCellType()
                                                       + ", Value:" + cell.getStringCellValue());
                            break;
                        case BLANK:
                            System.out.println("Cell" + cell.getAddress() + ", Type: " + cell.getCellType()
                                                     + ", Value: BLANK");
                            break;
                        case FORMULA:
                            System.out.println("Cell" + cell.getAddress() + ", Type: " + cell.getCellType()
                                                     + ", Value:" + cell.getCellFormula());
                            break;
                        default:
                            System.out.println("Cell" + cell.getAddress() + ", Type: " + cell.getCellType());
                            break;
                    }
                }

            }

            file.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}