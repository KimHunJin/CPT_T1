import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFileChooser;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class File {
	JFileChooser jfc = new JFileChooser();
	
	private String opendir;
	private String closedir;
	private ArrayList<Integer> index;
	private ArrayList<Integer> weight;
	private ArrayList<String> name;
	private ArrayList<String> constraints;
	private ArrayList<String> property1;
	private ArrayList<String> property2;
	private ArrayList<String> property3;
	private ArrayList<String> property4;
	private ArrayList<String> property5;
	private ArrayList<String> ifconst1;
	private ArrayList<String> ifconst2;
	private ArrayList<String> ifconst3;
	private ArrayList<String> ifconst4;
	private ArrayList<String> ifconst5;
	private int f;
	private int f2;
	private int max; //index 최대 개수
	private int categorymax=0;//총 카테고리 개수
	private int categorysupport=0;//카테고리 개수 구하는 걸 도와주기 위한 변수
	private int rows;
	private int cells;
	private int file;
	private XSSFRow row;
	private XSSFCell cell;
	
	private int rownum=0;
	
	public File() {
		index = new ArrayList<Integer>();
		weight = new ArrayList<Integer>();
		name = new ArrayList<String>();
		constraints = new ArrayList<String>();
		property1 = new ArrayList<String>();
		property2 = new ArrayList<String>();
		property3 = new ArrayList<String>();
		property4 = new ArrayList<String>();
		property5 = new ArrayList<String>();
		ifconst1 = new ArrayList<String>();
		ifconst2 = new ArrayList<String>();
		ifconst3 = new ArrayList<String>();
		ifconst4 = new ArrayList<String>();
		ifconst5 = new ArrayList<String>();
	}
	
	public String openDialoguebox() {
		int yn = jfc.showOpenDialog(jfc);
		
		if (yn == JFileChooser.APPROVE_OPTION) {
			f = 1;
			opendir = jfc.getSelectedFile().getAbsolutePath();
			if (opendir.contains(".xlsx")==false) {
				f = 0;
				System.out.println("엑셀 파일이 아닙니다");
			}
		}
		else if (yn == JFileChooser.CANCEL_OPTION) {
			f = 0;
		}
		
		openFile();
		return opendir;
	}
	
	public void saveDialoguebox(CaseController control) {
		if (f==1) {
			int yn2 = jfc.showSaveDialog(jfc);
		
		if (yn2 == JFileChooser.APPROVE_OPTION) {
			f2 = 1;
			closedir = jfc.getSelectedFile().getAbsolutePath();		
		}
		else if (yn2 == JFileChooser.CANCEL_OPTION) {
			f2 = 0;
		}
		}
		
		else if (f==0) {
			System.out.println("파일을 불러오지 않았습니다.");
		}
		exportFile(control);
	}
	
	public void openFile() {
		
		if (f == 1) {
			try {
				FileInputStream inputStream = new FileInputStream(opendir);
				XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

				//sheet수 취득
				int sheetCn = workbook.getNumberOfSheets();
				
				for(int cn = 0; cn < sheetCn; cn++){
					
					//0번째 sheet 정보 취득
					XSSFSheet sheet = workbook.getSheetAt(cn);
					
					//취득된 sheet에서 rows수 취득
					rows = sheet.getPhysicalNumberOfRows();
					
					//취득된 row에서 취득대상 cell수 취득
					cells = sheet.getRow(cn).getPhysicalNumberOfCells(); //
					
					for (int r = 1; r <= rows; r++) {
						row = sheet.getRow(r); // row 가져오기
						if (row != null) {
							for (int c = 1; c <= cells; c++) {
								cell = row.getCell(c);
								if (cell != null) {
									String value = null;
									switch (cell.getCellType()) {
									case XSSFCell.CELL_TYPE_FORMULA:
										value = cell.getCellFormula();
										break;
									case XSSFCell.CELL_TYPE_NUMERIC:
										value = "" + cell.getNumericCellValue();
										break;
									case XSSFCell.CELL_TYPE_STRING:
										value = "" + cell.getStringCellValue();
										break;
									case XSSFCell.CELL_TYPE_BLANK:
										value = "[null 아닌 공백]";
										break;
									case XSSFCell.CELL_TYPE_ERROR:
										value = "" + cell.getErrorCellValue();
										break;
									default:
									}
									if (c==1) {
				                           int num=(int)(Float.parseFloat(value));
				                           index.add(num);
				                           if((num/100)!=categorysupport)
				                           {
				                              categorymax+=1;
				                           }
				                           categorysupport=num/100;
				                           
				                           max += 1;
				                           
				                        }
									
									else if (c==2) {
										weight.add((int)Float.parseFloat(value));
									}
									else if (c==3) {
										name.add(value);
									}
									else if (c==4) {
										constraints.add(value);
									}
									else if (c==5) {
										property1.add(value);
									}
									else if (c==6) {
										property2.add(value);
									}
									else if (c==7) {
										property3.add(value);
									}
									else if (c==8) {
										property4.add(value);
									}
									else if (c==9) {
										property5.add(value);
									}
									else if (c==10) {
										ifconst1.add(value);
									}
									else if (c==11) {
										ifconst2.add(value);
									}
									else if (c==12) {
										ifconst3.add(value);
									}
									else if (c==13) {
										ifconst4.add(value);
									}
									else if (c==14) {
										ifconst5.add(value);
									}
									
								}
							} 
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}
	
	public void exportFile(CaseController control) {
		if (f == 1 && f2 == 1) {
		//xls 파일 출력시 선언
		//HSSFWorkbook workbook = new HSSFWorkbook();
		//Sheet명 설정
		//HSSFSheet sheet = workbook.createSheet("mySheet");
		
		//xlsx 파일 출력시 선언
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("mySheet");

		//출력 row 생성
		row = sheet.createRow(0);
		row.createCell(1).setCellValue("Index");
		row.createCell(2).setCellValue("Weight");
		
		
		for(int i=0;i<control.getTestcase()[0].getAl().size();i++)
		{
			int num=i+1;
			row.createCell(3+(i*13)).setCellValue("Value"+num);
			row.createCell(4+(i*13)).setCellValue("Value"+num+" Weight");
			row.createCell(5+(i*13)).setCellValue("Value"+num+" Constraints"); 
			row.createCell(6+(i*13)).setCellValue("Value"+num+" Property1");
			row.createCell(7+(i*13)).setCellValue("Value"+num+" Property2");
			row.createCell(8+(i*13)).setCellValue("Value"+num+" Property3");
			row.createCell(9+(i*13)).setCellValue("Value"+num+" Property4");
			row.createCell(10+(i*13)).setCellValue("Value"+num+" Property5");
			row.createCell(11+(i*13)).setCellValue("Value"+num+" IfConstraints1");
			row.createCell(12+(i*13)).setCellValue("Value"+num+" IfConstraints2");
			row.createCell(13+(i*13)).setCellValue("Value"+num+" IfConstraints3");
			row.createCell(14+(i*13)).setCellValue("Value"+num+" IfConstraints4");
			row.createCell(15+(i*13)).setCellValue("Value"+num+" IfConstraints5");
		}
		
		for(int i=0;i<control.getNum();i++)
		{
			row = sheet.createRow(i+1);
			row.createCell(1).setCellValue(control.getTestcase()[i].getIndex());
			row.createCell(2).setCellValue(control.getTestcase()[i].getWeight());
			
			for(int j=0;j<control.getTestcase()[i].getAl().size();j++)
			{
				row.createCell(3+(j*13)).setCellValue(control.getTestcase()[i].getAl().get(j).getIndex());
				row.createCell(4+(j*13)).setCellValue(control.getTestcase()[i].getAl().get(j).getWeight());
				row.createCell(5+(j*13)).setCellValue(control.getTestcase()[i].getAl().get(j).getConstraints());
				for(int k=0;k<5;k++)
				{
					row.createCell((6+k)+(j*13)).setCellValue(control.getTestcase()[i].getAl().get(j).getProperty()[k]);
				}
				
				for(int k=0;k<5;k++)
				{
					row.createCell((11+k)+(j*13)).setCellValue(control.getTestcase()[i].getAl().get(j).getIfconst()[k]);
				}
			}
		}
		
		rownum= control.getNum()+2;
		row = sheet.createRow(rownum);
		row.createCell(1).setCellValue("EssentialTestCase");
		
		rownum++;
		row = sheet.createRow(rownum);
		
		row.createCell(1).setCellValue("Check");
		row.createCell(2).setCellValue("Index");
		row.createCell(3).setCellValue("Weight");
		
		
		for(int i=0;i<control.getTestcase()[0].getAl().size();i++)
		{
			int num=i+1;
			row.createCell(4+(i*13)).setCellValue("Value"+num);
			row.createCell(5+(i*13)).setCellValue("Value"+num+" Weight");
			row.createCell(6+(i*13)).setCellValue("Value"+num+" Constraints"); 
			row.createCell(7+(i*13)).setCellValue("Value"+num+" Property1");
			row.createCell(8+(i*13)).setCellValue("Value"+num+" Property2");
			row.createCell(9+(i*13)).setCellValue("Value"+num+" Property3");
			row.createCell(10+(i*13)).setCellValue("Value"+num+" Property4");
			row.createCell(11+(i*13)).setCellValue("Value"+num+" Property5");
			row.createCell(12+(i*13)).setCellValue("Value"+num+" IfConstraints1");
			row.createCell(13+(i*13)).setCellValue("Value"+num+" IfConstraints2");
			row.createCell(14+(i*13)).setCellValue("Value"+num+" IfConstraints3");
			row.createCell(15+(i*13)).setCellValue("Value"+num+" IfConstraints4");
			row.createCell(16+(i*13)).setCellValue("Value"+num+" IfConstraints5");
		}
		
		for(int i=0;i<control.getEssentialnum();i++)
		{
			rownum++;
			row = sheet.createRow(rownum);
			row.createCell(1).setCellValue(control.getEcase()[i].getCause());
			row.createCell(2).setCellValue(control.getEcase()[i].getIndex());
			row.createCell(3).setCellValue(control.getEcase()[i].getWeight());
			
			for(int j=0;j<control.getEcase()[i].getEssentialtestcase().size();j++)
			{
				row.createCell(4+(j*13)).setCellValue(control.getEcase()[i].getEssentialtestcase().get(j).getIndex());
				row.createCell(5+(j*13)).setCellValue(control.getEcase()[i].getEssentialtestcase().get(j).getWeight());
				row.createCell(6+(j*13)).setCellValue(control.getEcase()[i].getEssentialtestcase().get(j).getConstraints());
				for(int k=0;k<5;k++)
				{
					row.createCell((7+k)+(j*13)).setCellValue(control.getEcase()[i].getEssentialtestcase().get(j).getProperty()[k]);
				}
				
				for(int k=0;k<5;k++)
				{
					row.createCell((12+k)+(j*13)).setCellValue(control.getEcase()[i].getEssentialtestcase().get(j).getIfconst()[k]);
				}
			}
		}
		
		
		// 출력 파일 위치및 파일명 설정
		FileOutputStream outFile;
		try {
			outFile = new FileOutputStream(closedir);
			workbook.write(outFile);
			outFile.close();
			
			System.out.println("파일생성 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
		
		
	}
	
	public void print() {
		for (int i=0; i<max; i++) {
		System.out.println(index.get(i));
		System.out.println(weight.get(i));
		System.out.println(name.get(i));
		System.out.println(constraints.get(i));
		System.out.println(property1.get(i));
		System.out.println(property2.get(i));
		System.out.println(property3.get(i));
		System.out.println(property4.get(i));
		System.out.println(property5.get(i));
		System.out.println(ifconst1.get(i));
		System.out.println(ifconst2.get(i));
		System.out.println(ifconst3.get(i));
		System.out.println(ifconst4.get(i));
		System.out.println(ifconst5.get(i));
		}
	}

	public ArrayList<Integer> getIndex() {
		return index;
	}

	public ArrayList<Integer> getWeight() {
		return weight;
	}
	
	public ArrayList<String> getName() {
		return name;
	}
	
	public ArrayList<String> getConstraints() {
		return constraints;
	}
	
	public int getMax()
	{
		return max;
	}
	
	public int getCategoryMax()
	{
		return categorymax;
	}
	
	public ArrayList<String> getProperty1() {
		return property1;
	}

	public void setProperty1(ArrayList<String> property1) {
		this.property1 = property1;
	}

	public ArrayList<String> getProperty2() {
		return property2;
	}

	public void setProperty2(ArrayList<String> property2) {
		this.property2 = property2;
	}

	public ArrayList<String> getProperty3() {
		return property3;
	}

	public void setProperty3(ArrayList<String> property3) {
		this.property3 = property3;
	}

	public ArrayList<String> getProperty4() {
		return property4;
	}

	public void setProperty4(ArrayList<String> property4) {
		this.property4 = property4;
	}

	public ArrayList<String> getProperty5() {
		return property5;
	}

	public void setProperty5(ArrayList<String> property5) {
		this.property5 = property5;
	}

	public ArrayList<String> getIfconst1() {
		return ifconst1;
	}

	public void setIfconst1(ArrayList<String> ifconst1) {
		this.ifconst1 = ifconst1;
	}

	public ArrayList<String> getIfconst2() {
		return ifconst2;
	}

	public void setIfconst2(ArrayList<String> ifconst2) {
		this.ifconst2 = ifconst2;
	}

	public ArrayList<String> getIfconst3() {
		return ifconst3;
	}

	public void setIfconst3(ArrayList<String> ifconst3) {
		this.ifconst3 = ifconst3;
	}

	public ArrayList<String> getIfconst4() {
		return ifconst4;
	}

	public void setIfconst4(ArrayList<String> ifconst4) {
		this.ifconst4 = ifconst4;
	}

	public ArrayList<String> getIfconst5() {
		return ifconst5;
	}

	public void setIfconst5(ArrayList<String> ifconst5) {
		this.ifconst5 = ifconst5;
	}

	public int getCategorymax() {
		return categorymax;
	}

	public void setCategorymax(int categorymax) {
		this.categorymax = categorymax;
	}

	public void setIndex(ArrayList<Integer> index) {
		this.index = index;
	}

	public void setWeight(ArrayList<Integer> weight) {
		this.weight = weight;
	}

	public void setName(ArrayList<String> name) {
		this.name = name;
	}

	public void setConstraints(ArrayList<String> constraints) {
		this.constraints = constraints;
	}

	public void setMax(int max) {
		this.max = max;
	}

}
