
public class ManagementSystem {
	private File fp;
	private CaseController control;
	
	private String opendir;
	
	public ManagementSystem()
	{
		fp= new File();
	}
	
	public String reqOpenfile()
	{
		opendir=fp.openDialoguebox();
		control=new CaseController(fp);
		return opendir;
	}
	
	public void reqExport()
	{
		fp.saveDialoguebox(control);
	}
	
	public CaseController reqMakeCase()
	{
		control.insertTestCase(fp);
		return control;
	}
	
	public CaseController reqEssential(String index)
	{
		control.insertEssentialTestCase(index);
		return control;
	}
	
	public void reqModify(int row, int col, Object data)
	{
		int modnum=0;
		int modnum2=0;
		col=col-2;
		if(col>0)
		{
			modnum=col/13;
			modnum2=col%13;
		}
		
		else if(col==0)
		{
			modnum=0;
			modnum2=0;
		}
		
		if(modnum2==1)
		{
			control.modifyWeightData(row, modnum, Integer.parseInt(data.toString()));
			System.out.println("Tlqkf");
		}
	}

}
