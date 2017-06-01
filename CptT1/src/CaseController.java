
public class CaseController {
	private TestCase[] testcase= new TestCase[1000];
	private EssentialTestCase[] ecase = new EssentialTestCase[1000];
	private boolean flag=false;
	private int num=0;

	private int essentialnum=0;
	private NodeFactory nodefactory;
	
	
	public CaseController(File fp)
	{
		nodefactory=new NodeFactory(fp);
		nodefactory.makeValue(fp);
		for(int i=0;i<1000;i++)
		{
			testcase[i]=new TestCase();
			ecase[i]= new EssentialTestCase();
		}
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public TestCase[] getTestcase() {
		return testcase;
	}

	public void setTestcase(TestCase[] testcase) {
		this.testcase = testcase;
	}

	public EssentialTestCase[] getEcase() {
		return ecase;
	}

	public void setEcase(EssentialTestCase[] ecase) {
		this.ecase = ecase;
	}

	public void insertTestCase(File fp)
	{
		while(flag==false)
		{
			flag=testcase[num].makeTestCase(nodefactory,fp);
			num++;
		}
		
		num=num-1;
		flag=false;
		
		while(flag==false)
		{
			flag=testcase[num].makeSingle(nodefactory,fp);
			num++;
		}
		num=num-1;
		print();
		
	}
	
	public void insertEssentialTestCase(String index)
	{
		ecase[essentialnum].makeEssentialCase(ecase[essentialnum].parsingInput(index), nodefactory);
		essentialnum++;
		for(int i=0;i<num-1;i++)
		{
			if(index.equals(testcase[i].getIndex()))
			{
				System.out.println(testcase[i].getIndex()+"index");
			}
		}
	}
	
	public void compareTestCase()
	{
		System.out.println(testcase[0].getIndex()+"index");
	}
	
	public int getEssentialnum() {
		return essentialnum;
	}

	public void setEssentialnum(int essentialnum) {
		this.essentialnum = essentialnum;
	}

	public void print()
	{
		for(int i=0;i<num;i++)
		{
			testcase[i].printNode();
		}
		
		System.out.println("테스케이스 총 개수: "+num);
	}
	
	public void modifyWeightData(int testcasenum, int valuenum, int weight)
	{
		testcase[testcasenum].getAl().get(valuenum).setWeight(weight);
	}

}
