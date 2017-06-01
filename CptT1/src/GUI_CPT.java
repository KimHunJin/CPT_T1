import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.PanelUI;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

public class GUI_CPT extends JFrame implements TableModelListener {

	private JPanel contentbackground;
	private JPanel left;
	private JPanel right;
	private JPanel bottom;
	private JTable table;
	private JTextField txtInputecase;
	private JTable table_1;
	private JButton generate;
	private JButton openFile;
	private JButton export;
	private String inputEssential ;
	private DefaultTableModel Edm;
	private JScrollPane scrollPane_1;
	private Color background;
	
	private JLabel frameback = new JLabel(new ImageIcon("Image//background.png"));
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_4;
	
	private ManagementSystem manager= new ManagementSystem();
	
	private String opendir="Filenametext";
	
	private Vector<String> rows;
	private Vector<String> erows;
	DefaultTableModel dm;
	JScrollPane scrollPane;
	private int blueflag=0;
	private int generateflag=0;
	/**
	 * Launch the application.
	 */
	//frame.setTestCaseColor(); 요게 왼쪽 JTable에서 특정 row 색 바꿔주는 메소드인데.. Ecase input될때마다 실행되게 껴넣으면 안칠해지고 
	//꼭 main에 써놔야만 실행되더라구요. 
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_CPT frame = new GUI_CPT();
					//frame.printFileName("Filenametext");
					frame.generatePrintButton();
					frame.printButton();
					frame.exportButton();
					frame.setVisible(true);
					//frame.addTestCaselist("1012");
					//frame.setTestCaseColor();
					frame.EcaseEditText();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI_CPT() {
		setTitle("CPT_T1");
		ImageIcon img = new ImageIcon("Image//teamicon.png");
		setIconImage(img.getImage());
		setFont(new Font("210 맨발의청춘 R", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 700);
		contentbackground = new JPanel();
		//contentbackground.setUI(frameback);

		contentbackground.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentbackground);
		this.setVisible(true);
		contentbackground.setLayout(null);
	
		left = new JPanel();
		left.setOpaque(false);
		left.setLayout(null);
		left.setBounds(32, 20, 780, 598);
		contentbackground.add(left);
		
		right = new JPanel();
		right.setOpaque(false);
		right.setLayout(null);
		right.setBounds(835, 20, 619, 523);
		
		//right.setBackground(Color.PINK);
		contentbackground.add(right);
		
		bottom = new JPanel();
		bottom.setOpaque(false);
		bottom.setLayout(null);
		bottom.setBackground(Color.YELLOW);
		bottom.setBounds(835, 562, 619, 56);
		contentbackground.add(bottom);
		
		//사용자의 input 이들어갈 inputcase TestField(스펠링맞나?) 이부분도 인풋에 대한 parsing이 필요하니까..
		LineBorder brd3 = new LineBorder(Color.WHITE,2);
		txtInputecase = new JTextField();
		txtInputecase.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		//txtInputecase.setText("InputEcase");
		txtInputecase.setOpaque(false);
		txtInputecase.setBorder(brd3);
		txtInputecase.setBounds(0, 0, 619, 56);
		bottom.add(txtInputecase);
		txtInputecase.setColumns(10);
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("Image//window3.png"));
		lblNewLabel_4.setBounds(0, 0, 619, 56);
		bottom.add(lblNewLabel_4);
		
		//기본 제목 .. 바뀌지 않는 Field
		JTextArea textField = new JTextArea();
		textField.setForeground(new Color(255, 255, 255));
		textField.setOpaque(false);
		textField.setFont(new Font("맑은 고딕", Font.BOLD, 38));
		textField.setText("Essential Test Case");
		textField.setBounds(10, 10, 418, 53);
		right.add(textField);
		
		//미리 생성되어야 할 부분. 
		scrollPane_1 = new JScrollPane();
		getEssentialscroll().setBounds(20, 78, 582, 417);
		getEssentialscroll().setOpaque(false);
		getEssentialscroll().getViewport().setOpaque(false);
		getEssentialscroll().setBorder(null);
		getrightPanel().add(scrollPane_1);

			lblNewLabel_3 = new JLabel("");
			lblNewLabel_3.setIcon(new ImageIcon("Image//window2.png"));
			lblNewLabel_3.setBounds(0, 0, 619, 523);
			right.add(lblNewLabel_3);
			//generateflag=1;
		
		//Essential TestCase 쪽 JTable
		table_1 = new JTable();
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		String[] essentialHead = {
				"check", "Index", "Weight"
		};
		Edm = new DefaultTableModel(null, essentialHead);
		table_1.setModel(Edm);
		table_1.setOpaque(false);
		table_1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{
            setOpaque(false);
        }});
		table_1.getTableHeader().setFont(new Font("나눔바른고딕",Font.PLAIN,12));
		table_1.getTableHeader().setForeground(Color.DARK_GRAY);
		table_1.getTableHeader().setBackground(new Color(198,221,230));
		table_1.setForeground(Color.WHITE);
		
		//Generate버튼
		
		generate =  new JButton("Generate");
		generate.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 20));
		LineBorder brd2 = new LineBorder(Color.WHITE, 3);
		generate.setBorder(brd2);
		generate.setOpaque(false);
		generate.setContentAreaFilled(false);
		generate.setBackground(UIManager.getColor("Button.disabledShadow"));
		generate.setForeground(Color.WHITE);
		generate.setBounds(522, 27, 159, 37);
		left.add(generate);
		
		openFile = new JButton("");
		openFile.setBorderPainted(false);
		openFile.setOpaque(false);
		openFile.setContentAreaFilled(false);
		openFile.setBounds(0, 15, 67, 58);
		getleftPanel().add(openFile);

		JLabel lblNewLabel_1 = new JLabel("Foldericon");
		lblNewLabel_1.setIcon(new ImageIcon("Image//open.png"));
		lblNewLabel_1.setBounds(17, 15, 67, 58);
		left.add(lblNewLabel_1);
		
		//그냥 이미지. 
		export = new JButton("");
		export.setIcon(new ImageIcon("Image//export.png"));
		export.setBorder(null);
		export.setOpaque(false);
		export.setContentAreaFilled(false);
		export.setBounds(692, 16, 71, 58);
		left.add(export);

			lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon("Image//background.png"));
			lblNewLabel.setBounds(0, 0, 1478, 644);
			lblNewLabel.setVisible(true);
			contentbackground.add(lblNewLabel);
			
		
		//여기까지 jtable 관련
	}
	   //JTable 에서 특정한 열이 선택되었을때 발생하는 이벤트 처리 
	   @Override
	   public void tableChanged(TableModelEvent e) {
	      // TODO Auto-generated method stub
	      System.out.println("들어오니 여기는"+e.getColumn());
	      System.out.println(e.getFirstRow());
	      Object data =table.getValueAt(e.getFirstRow(), 
	    		  e.getColumn());
	      //setTestCaseColor();
	      //열을 계산한 결과 Constraints가 있는 열들. 
	      if(e.getColumn()>1 && e.getColumn()%3==1)
	      {
	         //Manage.reqConstraints(data);
	      }
	      //Weight 가 있는 열들.
	      else if(e.getColumn()>0 && e.getColumn()%3==0)
	      {
	         //Manage.reqModifyWeight(data);
	      }
	      
	      System.out.println(data);
	      manager.reqModify(e.getFirstRow(), e.getColumn(), data);
	      
	   }
	   public void printFileName(String filename)
	   {
	      JTextField txtrFilexlsx = new JTextField();
	      txtrFilexlsx.setForeground(Color.WHITE);
	      txtrFilexlsx.setFont(new Font("맑은 고딕", Font.BOLD, 18));
	      //must be inputed
	      txtrFilexlsx.setBorder(null);
	      txtrFilexlsx.setText(filename);
	      txtrFilexlsx.setOpaque(false);
	      txtrFilexlsx.setBounds(136, 24, 379, 44);
	      getleftPanel().add(txtrFilexlsx);
	   }
	   
	   //Node들로부터 받을 정보를 Object o 로 처리 .
	   public void addTestCaselist(CaseController control)
	   {
		  //generateTable();
	      table.setFont(new Font("나눔바른고딕", Font.PLAIN, 12));
	      table.getTableHeader().setFont(new Font("나눔바른고딕", Font.PLAIN, 12));
	      table.getTableHeader().setBackground(new Color(198,221,230));
	      table.getTableHeader().setForeground(Color.DARK_GRAY);
	   
	      table.setShowGrid(false);
	      table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	      
	      table.setModel(dm);
	      table.setOpaque(false);
	      background = new Color(0, 0, 0, 0);
	      table.setBackground(background);
	      
	      for(int i=0;i<control.getTestcase()[0].getAl().size();i++)
	      {
	         int num=i+1;
	         dm.addColumn("Value"+num);
	         dm.addColumn("Value"+num+" Weight");
	         dm.addColumn("Value"+num+" Constraints"); 
	         dm.addColumn("Value"+num+" Property1");
	         dm.addColumn("Value"+num+" Property2");
	         dm.addColumn("Value"+num+" Property3");
	         dm.addColumn("Value"+num+" Property4");
	         dm.addColumn("Value"+num+" Property5");
	         dm.addColumn("Value"+num+" IfConstraints1");
	         dm.addColumn("Value"+num+" IfConstraints2");
	         dm.addColumn("Value"+num+" IfConstraints3");
	         dm.addColumn("Value"+num+" IfConstraints4");
	         dm.addColumn("Value"+num+" IfConstraints5");
	      }
	      
	      for(int i=0;i<control.getNum();i++)
	      {
	         rows=new Vector<String>();
	         rows.addElement(control.getTestcase()[i].getIndex());
	         rows.addElement(Float.toString(control.getTestcase()[i].getWeight()));
	         
	         for(int j=0;j<control.getTestcase()[i].getAl().size();j++)
	         {
	            rows.addElement(Integer.toString(control.getTestcase()[i].getAl().get(j).getIndex()));
	            rows.addElement(Integer.toString(control.getTestcase()[i].getAl().get(j).getWeight()));
	            rows.addElement(control.getTestcase()[i].getAl().get(j).getConstraints());
	            for(int k=0;k<5;k++)
	            {
	               rows.addElement(control.getTestcase()[i].getAl().get(j).getProperty()[k]);
	            }
	            
	            for(int k=0;k<5;k++)
	            {
	               rows.addElement(control.getTestcase()[i].getAl().get(j).getIfconst()[k]);
	            }
	         }
	         
	         dm.addRow(rows);
	      }
	      
	      table.getColumnModel().getColumn(0).setPreferredWidth(76);
	      table.getColumnModel().getColumn(0).setMinWidth(30);
	      table.getColumnModel().getColumn(0).setPreferredWidth(230);;
	      table.setRowHeight(50);
	      table.getColumnModel().getColumn(1).setPreferredWidth(137);
	      table.getColumnModel().getColumn(2).setPreferredWidth(145);
	      table.getColumnModel().getColumn(3).setPreferredWidth(215);
	      table.getColumnModel().getColumn(4).setPreferredWidth(186);
	      table.setForeground(Color.WHITE);
	      table.getModel().addTableModelListener(this);
	     /*
	      table.setAutoCreateRowSorter(true);
	      TableRowSorter tablesorter = new TableRowSorter(table.getModel());
	      table.setRowSorter(tablesorter);
	      */
	      scrollPane = new JScrollPane();
	      scrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
	      scrollPane.setBounds(0, 88, 749, 496);
	      scrollPane.setOpaque(false);
	      scrollPane.getViewport().setOpaque(false);
	      //table.getTableHeader().setOpaque(false);
	      getleftPanel().add(scrollPane);
	      scrollPane.setViewportView(table);
	      scrollPane.setBorder(null);
		if(generateflag==0)
		{
	      lblNewLabel_2 = new JLabel("");
	      lblNewLabel_2.setIcon(new ImageIcon("Image//window1.png"));
	      lblNewLabel_2.setBounds(0, 0, 780, 598);
	      left.add(lblNewLabel_2);
	      generateflag=1;
		}
	      /*
	       * label 에 이미지 넣는 방식. 
	      JLabel lblNewLabel = new JLabel("New label");
	      lblNewLabel.setIcon(new ImageIcon("C:\\Users\\user\\Downloads\\\uCF54\uD074 \uB514\uC790\uC778\\images\\_\uB933\uB033_\uACE2\uB038__\uB933\uB02C_\uB077\uB03F_\u24E4\uAF38__08.png"));
	      lblNewLabel.setBounds(346, 37, 78, 21);
	      left.add(lblNewLabel);
	      */
		control.setNum(control.getNum()+1);
	   }
	   
	   public void addEssentialTestCaselist(CaseController control)
	   {
	      table_1.setShowGrid(false);
	      table_1.setRowHeight(50);
	      
	      if(blueflag==0)
	      {
	         for(int i=0;i<control.getEcase()[0].getEssentialtestcase().size();i++)
	         {
	            int num=i+1;
	            getEdm().addColumn("Value"+num);
	            getEdm().addColumn("Value"+num+" Weight");
	            getEdm().addColumn("Value"+num+" Constraints"); 
	            getEdm().addColumn("Value"+num+" Property1");
	            getEdm().addColumn("Value"+num+" Property2");
	            getEdm().addColumn("Value"+num+" Property3");
	            getEdm().addColumn("Value"+num+" Property4");
	            getEdm().addColumn("Value"+num+" Property5");
	            getEdm().addColumn("Value"+num+" IfConstraints1");
	            getEdm().addColumn("Value"+num+" IfConstraints2");
	            getEdm().addColumn("Value"+num+" IfConstraints3");
	            getEdm().addColumn("Value"+num+" IfConstraints4");
	            getEdm().addColumn("Value"+num+" IfConstraints5");
	         }
	         blueflag=1;
	      }
	      
	      
	      erows=new Vector<String>();
	      erows.addElement(control.getEcase()[control.getEssentialnum()-1].getCause());
	      erows.addElement(control.getEcase()[control.getEssentialnum()-1].getIndex());
	      erows.addElement(Float.toString(control.getEcase()[control.getEssentialnum()-1].getWeight()));
	      for(int i=0;i<control.getEcase()[control.getEssentialnum()-1].getEssentialtestcase().size();i++)
	      {
	         
	         erows.addElement(Integer.toString(control.getEcase()[control.getEssentialnum()-1].getEssentialtestcase().get(i).getIndex()));
	         erows.addElement(Integer.toString(control.getEcase()[control.getEssentialnum()-1].getEssentialtestcase().get(i).getWeight()));
	         erows.addElement(control.getEcase()[control.getEssentialnum()-1].getEssentialtestcase().get(i).getConstraints());
	         for(int k=0;k<5;k++)
	         {
	            erows.addElement(control.getEcase()[control.getEssentialnum()-1].getEssentialtestcase().get(i).getProperty()[k]);
	         }
	         
	         for(int k=0;k<5;k++)
	         {
	            erows.addElement(control.getEcase()[control.getEssentialnum()-1].getEssentialtestcase().get(i).getIfconst()[k]);
	         }
	         
	      }
	      
	      getEdm().addRow(erows);
	      table_1.getColumnModel().getColumn(0).setPreferredWidth(230);
	      table_1.getColumnModel().getColumn(1).setPreferredWidth(108);
	      table_1.getColumnModel().getColumn(2).setPreferredWidth(178);
	      table_1.getColumnModel().getColumn(3).setPreferredWidth(176);
	      table_1.getColumnModel().getColumn(4).setPreferredWidth(185);
	      TableRowSorter tablesorter_1 = new TableRowSorter(table_1.getModel());
	      table_1.setRowSorter(tablesorter_1);
	      getEssentialscroll().setViewportView(table_1);
	      getEssentialscroll().setBorder(null);
	   }
	   
	   public void printButton()
	   {
	      getOpenFileBtn().addActionListener(new ActionListener()
	      {
	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	            System.out.println("OpenFile Btn Pressed");
	            opendir=manager.reqOpenfile();
	            System.out.println(opendir);
	            printFileName(opendir);
	         }
	         
	      });
	   }
	   public void exportButton()
	   {
	      getExportBtn().addActionListener(new ActionListener()
	      {
	         public void actionPerformed(ActionEvent e) 
	         {
	            System.out.println("Export Btn Pressed");
	            manager.reqExport();
	         }
	         
	      });
	   }
	   public void openDialoguebox()
	   {
	      //강욱씨에게 로직 받아서 채워넣기.
	      //reqOpenfile();
	   }
	   
	   //essential test case 입력하고 표로 그려주는 것 엔터키에 대한 이벤트핸들러필요
	   public void EcaseEditText()
	   {
	      txtInputecase.addActionListener(new ActionListener(){
	         
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            //inputEssential = txtInputecase.getText();
	            setEssentialInfo(txtInputecase.getText());
	            //System.out.println(getEssentialInfo());
	            CaseController control=manager.reqEssential(getEssentialInfo());
	            Object obj = e.getSource();
	            
	            if(obj == txtInputecase)
	            {
	               addEssentialTestCaselist(control);
	               //setTestCaseColor();
	               txtInputecase.setText("");

	            }
	         }
	         
	      });
	   }
	   public void generatePrintButton()
	   {
	      getGenerateBtn().addActionListener(new ActionListener(){

	         @Override
	         public void actionPerformed(ActionEvent generateeEvent) {
	            // TODO Auto-generated method stub
	            //ManagementSystem.reqMakeCase();
	            System.out.println("generate Btn Pressed");
	            System.out.println("generateflag :"+generateflag);
	            if(generateflag==0)
	            {
	            	generateTable();
	            	addTestCaselist(manager.reqMakeCase());
	            	generateflag=2;
	            }
	            else if(generateflag==2)
	            {
	            	System.out.println("그려져라 얍!");
	            	addTestCaselist(manager.reqMakeCase());
	            }
	            	//table.repaint();
	         }
	         
	      });
	   }
	   public void generateTable()
	   {
		   String[] cols={"Index", "Weight"};
		     dm = new DefaultTableModel(null,cols);
		     table = new JTable();   
		    scrollPane = new JScrollPane();
	   }
	   public void setTestCaseColor()
	   {
	      getleftJTable().setDefaultRenderer(Object.class,new  leftTableCellRenderer() );
	   }
	   public void setEssentialCaseColor()
	   {
	      getrighttJTable().setDefaultRenderer(Object.class, new  rightTableCellRenderer());
	   }
	   public JPanel getleftPanel()
	   {
	      return this.left;
	   }
	   public JPanel getrightPanel()
	   {
	      return this.right;
	   }
	   public JTable getleftJTable()
	   {
	      return this.table;
	   }
	   public JTable getrighttJTable()
	   {
	      return this.table_1;
	   }
	   public JButton getGenerateBtn()
	   {
	      return generate;
	   }
	   public JButton getOpenFileBtn()
	   {
	      return openFile;
	   }
	   public JButton getExportBtn()
	   {
	      return export;
	   }
	   public String getEssentialInfo()
	   {
	      return inputEssential;
	   }
	   public void setEssentialInfo(String str)
	   {
	      this.inputEssential = str;
	   }
	   public DefaultTableModel getEdm()
	   {
	      return Edm;
	   }
	   public JScrollPane getEssentialscroll()
	   {
	      return scrollPane_1;
	   }
	   public Color gettransparentColor()
	   {
	      return background;
	   }
	}

	class leftTableCellRenderer extends DefaultTableCellRenderer 
	{

	   Color back = new Color(0,0,0,0);
	   Color match = new Color(208,104,147);
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        super.getTableCellRendererComponent(table, value,
	                 isSelected, hasFocus, row, column);
	        /* if 안에는 다른 클래스로부터 받아온 정보가 있어야 하니, if(row==1)부분 대신 정보를 받아올 클래스를 이 leftTableCellRender에
	    인스턴스로 생성하고, a.getvalue().equals 이런식으로 ... 해야할듯 합니다. 
	      */
	       if(row==1)
	       {
	          setBackground(match);
	       }
	       else
	       {
	          setBackground(back);
	       }
	        return this;
	    }
	}

	class rightTableCellRenderer extends DefaultTableCellRenderer 
	{

	   Color back = new Color(0,0,0,0);
	   Color miss = new Color(165,187,129);
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        super.getTableCellRendererComponent(table, value,
	                 isSelected, hasFocus, row, column);
	        /* if 안에는 다른 클래스로부터 받아온 정보가 있어야 하니, if(row==1)부분 대신 정보를 받아올 클래스를 이 leftTableCellRender에
	    인스턴스로 생성하고, a.getvalue().equals 이런식으로 ... 해야할듯 합니다. 
	      */
	        
	       if(row==1)
	       {
	          setBackground(miss);
	       }
	       else
	       {
	          setBackground(back);
	       }
	        return this;
	    }
	}
