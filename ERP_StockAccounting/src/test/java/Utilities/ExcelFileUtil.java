package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	Workbook wb;
	public  ExcelFileUtil(String ExcelPath) throws Throwable
	{
		FileInputStream fi=new FileInputStream(ExcelPath);
		wb=WorkbookFactory.create(fi);
		
	}
	// count no.of rows in sheet
	public int rowCount(String sheetName)
	{
		return wb.getSheet(sheetName).getLastRowNum();
	}
	public String getCellData(String sheetName,int row,int column)
	{
		String data="";
		if(wb.getSheet(sheetName).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
		{
			int celldata=(int) wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
			data=String.valueOf(celldata);
			
		}
		else
		{
			data=wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
		
		
	}
	//method for writing data
	public void setCellData(String sheetName,int row,int column,String status,String WriteExcel) throws Throwable
	{
		//get sheet from wb
		Sheet ws=wb.getSheet(sheetName);
		Row rowNum=ws.getRow(row);
		Cell cell=rowNum.createCell(column);
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("pass"))
		{
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			font.setBoldweight(font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
			
			
		}
		else if(status.equalsIgnoreCase("blocked"))
		{
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			font.setBoldweight(font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
			
		}
		else if(status.equalsIgnoreCase("fail"))
		{
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			font.setBoldweight(font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
			
	
		}
		FileOutputStream fo=new FileOutputStream(WriteExcel);
		wb.write(fo);
	}
	public static void main(String[] args) throws Throwable {
		ExcelFileUtil xl=new ExcelFileUtil("D:\\uday\\seleniumPractice\\ERP_StockAccounting\\uday1.xlsx");
		int rc=xl.rowCount("Emp");
		for(int i=1;i<=rc;i++)
		{
			String Fname=xl.getCellData("Emp", i, 0);
			String Mname=xl.getCellData("Emp", i, 1);
			String Lname=xl.getCellData("Emp", i, 2);
			String Eid=xl.getCellData("Emp", i, 3);
			System.out.println(Fname+" "+Mname+" "+Lname+" "+Eid);
			xl.setCellData("Emp", i, 4, "pass", "D:\\uday\\seleniumPractice\\ERP_StockAccounting\\Results.xlsx");
			
		}
	}

}             
