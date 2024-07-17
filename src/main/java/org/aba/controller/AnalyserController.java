package org.aba.controller;

import org.aba.BaseUtils;
import org.aba.dto.BudgetDataDto;
import org.aba.dto.GlobalBudgetDataDto;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class AnalyserController
{
    private String path = "";

    private XSSFFont titleFont;
    private XSSFFont boldFont;
    private XSSFFont redFont;
    private XSSFFont greenFont;
    private XSSFCellStyle cellStyleTitle;
    private XSSFCellStyle cellStyleBold;
    private XSSFCellStyle cellStyleRed;
    private XSSFCellStyle cellStyleGreen;

    public AnalyserController(String path)
    {
        setPath(path);
    }

    public void doWork() throws IOException, InvalidFormatException
    {
        FileInputStream file = new FileInputStream(getPath() + "comptes.xlsx");
        GlobalBudgetDataDto globalBudgetDataDto = analyseData(file);
        file.close();
        processData(globalBudgetDataDto);
    }

    private GlobalBudgetDataDto analyseData(FileInputStream file) throws IOException, InvalidFormatException
    {
        XSSFWorkbook wbToAnalyse = new XSSFWorkbook(file);
        XSSFSheet ws = wbToAnalyse.getSheetAt(1);

        GlobalBudgetDataDto globalBudgetDataDto = new GlobalBudgetDataDto();
        globalBudgetDataDto.setBudgetDataDtos(new ArrayList<>());

        Iterator<Row> rowIterator = ws.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            if (row.getRowNum() <= 4)
            {
                continue;
            }

            Cell dateCell = row.getCell(0);
            Cell descriptionCell = row.getCell(2);
            Cell debitCell = row.getCell(3);
            Cell creditCell = row.getCell(4);

            if (CellType.NUMERIC.equals(dateCell.getCellType()))
            {
                //System.out.print("Row:" + row.getRowNum() + " - ");

                BudgetDataDto budgetDataDto = new BudgetDataDto();
                globalBudgetDataDto.getBudgetDataDtos().add(budgetDataDto);

                budgetDataDto.setDate(dateCell.getDateCellValue());
                budgetDataDto.setDescription(descriptionCell.getStringCellValue());

                if (!CellType.BLANK.equals(debitCell.getCellType()))
                {
                    budgetDataDto.setAmount(debitCell.getNumericCellValue());
                }
                else
                {
                    budgetDataDto.setAmount(creditCell.getNumericCellValue());
                }

                //System.out.println("BudgetDataDto: " + budgetDataDto.getInfos());
            }

        }

        return globalBudgetDataDto;
    }

    private void processData(GlobalBudgetDataDto globalBudgetDataDto) throws IOException
    {
        Boolean isFirst = true;

        for (BudgetDataDto budgetDataDto : globalBudgetDataDto.getBudgetDataDtos())
        {
            if (isFirst)
            {
                globalBudgetDataDto.setFrom(budgetDataDto.getDate());
                globalBudgetDataDto.setTo(budgetDataDto.getDate());
                isFirst = false;
            }

            if (globalBudgetDataDto.getTo().before(budgetDataDto.getDate()))
            {
                globalBudgetDataDto.setTo(budgetDataDto.getDate());
            }

            if (budgetDataDto.getAmount() < 0.0)
            {
                globalBudgetDataDto.setTotalOutput(globalBudgetDataDto.getTotalOutput() - budgetDataDto.getAmount());
            }
            else
            {
                globalBudgetDataDto.setTotalInput(globalBudgetDataDto.getTotalInput() + budgetDataDto.getAmount());
            }

            budgetDataDto.initPaymentType();
            budgetDataDto.initUsedFor();


        }

        printData(globalBudgetDataDto);
    }

    private void printData(GlobalBudgetDataDto globalBudgetDataDto) throws IOException
    {
        XSSFWorkbook wb = new XSSFWorkbook();
        initStyles(wb);

        Sheet recapSheet = wb.createSheet("Analyse principale");
        printRecapSheet(recapSheet, globalBudgetDataDto);

        Sheet detailSheet = wb.createSheet("Detail");
        printDetailSheet(detailSheet, globalBudgetDataDto);

        try
        {
            FileOutputStream out = new FileOutputStream(
                    new File(getPath() + "compteAnaliser_" + BaseUtils.getDateFormatteddd_MM_YYYY(new Date()) + ".xlsx"));

            wb.write(out);
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void printRecapSheet(Sheet sheet, GlobalBudgetDataDto globalBudgetDataDto)
    {
        int rowNb = 0;
        Row row = sheet.createRow(rowNb++);

        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 5));
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyleTitle);
        cell.setCellValue("Analyse principale du "
                + BaseUtils.getDateFormattedddPointMMPointYYYY(globalBudgetDataDto.getFrom())
                + " au " + BaseUtils.getDateFormattedddPointMMPointYYYY(globalBudgetDataDto.getTo()));

        row = sheet.createRow(rowNb++);
        row = sheet.createRow(rowNb++);

        int cellNb = 0;
        cell = row.createCell(cellNb);
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), cellNb, cellNb + 2));
        cellNb += 3;
        cell.setCellValue("Total entré d'argent: ");

        cell = row.createCell(cellNb);
        cell.setCellValue(globalBudgetDataDto.getTotalInput());
        cell.setCellStyle(cellStyleGreen);

        row = sheet.createRow(rowNb++);
        cellNb = 0;
        cell = row.createCell(cellNb);
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), cellNb, cellNb + 2));
        cellNb += 3;
        cell.setCellValue("Total sortie d'argent: ");

        cell = row.createCell(cellNb);
        cell.setCellValue(globalBudgetDataDto.getTotalOutput() * -1);
        cell.setCellStyle(cellStyleRed);
    }

    private void printDetailSheet(Sheet sheet, GlobalBudgetDataDto globalBudgetDataDto)
    {
        int rowNb = 0;
        int cellNb = 0;

        initDetailSheetWidths(sheet);

        Row row = sheet.createRow(rowNb++);
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 5));
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyleTitle);
        cell.setCellValue("Detail du " + BaseUtils.getDateFormattedddPointMMPointYYYY(globalBudgetDataDto.getFrom())
                + " au " + BaseUtils.getDateFormattedddPointMMPointYYYY(globalBudgetDataDto.getTo()));

        cellNb = 0;
        row = sheet.createRow(rowNb++);

        cell = row.createCell(cellNb++);
        cell.setCellValue("Date");
        cell.setCellStyle(cellStyleBold);

        cell = row.createCell(cellNb++);
        cell.setCellValue("Description rapide");
        cell.setCellStyle(cellStyleBold);

        cell = row.createCell(cellNb++);
        cell.setCellValue("Valeur");
        cell.setCellStyle(cellStyleBold);

        cell = row.createCell(cellNb++);
        cell.setCellValue("Type");
        cell.setCellStyle(cellStyleBold);

        cell = row.createCell(cellNb++);
        cell.setCellValue("Pour");
        cell.setCellStyle(cellStyleBold);

        cell = row.createCell(cellNb++);
        cell.setCellValue("Desciption complete");
        cell.setCellStyle(cellStyleBold);

        for (BudgetDataDto budgetDataDto : globalBudgetDataDto.getBudgetDataDtos())
        {
            cellNb = 0;
            row = sheet.createRow(rowNb++);

            cell = row.createCell(cellNb++);
            cell.setCellValue(BaseUtils.getDateFormattedddPointMMPointYYYY(budgetDataDto.getDate()));

            cell = row.createCell(cellNb++);
            cell.setCellValue(budgetDataDto.getDescriptionRecap());

            cell = row.createCell(cellNb++);
            cell.setCellValue(budgetDataDto.getAmount());
            if (budgetDataDto.getAmount() < 0.0)
            {
                cell.setCellStyle(cellStyleRed);
            }
            else
            {
                cell.setCellStyle(cellStyleGreen);
            }

            cell = row.createCell(cellNb++);
            cell.setCellValue(budgetDataDto.getPaymentType());

            cell = row.createCell(cellNb++);
            cell.setCellValue(budgetDataDto.getUsedFor());

            cell = row.createCell(cellNb++);
            cell.setCellValue(budgetDataDto.getDescription());
        }

    }

    private void initStyles(XSSFWorkbook wb)
    {
        short FONT_HEIGHT_TITLE = 20;
        titleFont = wb.createFont();
        titleFont.setFontName("Calibri");
        titleFont.setFontHeightInPoints(FONT_HEIGHT_TITLE);
        titleFont.setBold(true);

        boldFont = wb.createFont();
        boldFont.setFontName("Calibri");
        boldFont.setBold(true);

        redFont = wb.createFont();
        redFont.setFontName("Calibri");
        redFont.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());

        greenFont = wb.createFont();
        greenFont.setFontName("Calibri");
        greenFont.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());

        cellStyleTitle = wb.createCellStyle();
        cellStyleTitle.setFont(titleFont);

        cellStyleBold = wb.createCellStyle();
        cellStyleBold.setFont(boldFont);

        cellStyleRed = wb.createCellStyle();
        cellStyleRed.setFont(redFont);

        cellStyleGreen = wb.createCellStyle();
        cellStyleGreen.setFont(greenFont);
    }

    private void initDetailSheetWidths(Sheet sheet)
    {
        int columnIndex = 0;
        sheet.setColumnWidth(columnIndex, 10 * 256);
        columnIndex++;

        sheet.setColumnWidth(columnIndex, 40 * 256);
        columnIndex++;

        sheet.setColumnWidth(columnIndex, 10 * 256);
        columnIndex++;

        sheet.setColumnWidth(columnIndex, 15 * 256);
        columnIndex++;

        sheet.setColumnWidth(columnIndex, 15 * 256);
        columnIndex++;

        sheet.setColumnWidth(columnIndex, 120 * 256);
        columnIndex++;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }
}
