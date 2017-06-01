import java.util.ArrayList;

public class EssentialTestCase {
	private int existence;
	private int causevalue;
	private String[] split;
	private ArrayList<RepresentativeValue> essentialtestcase = new ArrayList<RepresentativeValue>();

	private ArrayList<Integer> ind = new ArrayList<Integer>();
	
	private ArrayList<String> eTestCase = new ArrayList<String>();
	
	private ArrayList<String> property = new ArrayList<String>(); 
	private int ifcheck=0;
	private int ifcheck2=0;
	
	private String index;
	private float weight;

	private String cause="����";
	
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
	
	public String getIndex()
	{
		index="";
		for(int i=0;i<essentialtestcase.size();i++)
		{
			if(i+1!=essentialtestcase.size())
			{
				index+=essentialtestcase.get(i).getIndex()+".";
			}
			else
			{
				index+=essentialtestcase.get(i).getIndex();
			}
		}
		return index;
	}
	
	public float getWeight()
	{
		int sum=0;
		for(int i=0;i<essentialtestcase.size();i++)
	    {
			sum=essentialtestcase.get(i).getWeight();
	    }
		if(essentialtestcase.size()>0)
		{
			weight=sum/essentialtestcase.size();
		}
		else
		{
			weight=0;
		}
		return weight;
	}
	
	public ArrayList<RepresentativeValue> getEssentialtestcase() {
		return essentialtestcase;
	}

	public void setEssentialtestcase(ArrayList<RepresentativeValue> essentialtestcase) {
		this.essentialtestcase = essentialtestcase;
	}
	
	public void makeEssentialCase(ArrayList<Integer> ind, NodeFactory nf) {
		for (int i = 0; i<ind.size(); i++) {
			essentialtestcase.add(nf.getValueNode()[ind.get(i)/100].get(ind.get(i)%100));
			//ǥ��
			System.out.println(essentialtestcase.get(i).getIndex());
		}

		checkConstraints();
	}
	
	public ArrayList<Integer> parsingInput(String index) {
		//str = "100.200.300";
		split = index.split("\\.");
		for (int i = 0; i<split.length; i++) {
			ind.add(Integer.parseInt(split[i]));
		}
		return ind;
	}
	
	public void checkConstraints() {
		for(int i=0; i<essentialtestcase.size(); i++) 
		{
			if (essentialtestcase.get(i).getConstraints().equals("error")||essentialtestcase.get(i).getConstraints().equals("single"))
			{
				System.out.println(essentialtestcase.get(i).getIndex()+":"+essentialtestcase.get(i).getConstraints());
				cause=essentialtestcase.get(i).getIndex()+":"+essentialtestcase.get(i).getConstraints();
			}
			else
			{
				RepresentativeValue temp=essentialtestcase.get(i);
				if(temp.getProperty()[0].equals("null")==false)//property�� �ִٸ�
				{
					for(int k=0;k<5;k++)
					{
						if(temp.getProperty()[k].equals("null")==false)//null�� �ƴ� property�� list�� �־��ش�.
						{
							property.add(temp.getProperty()[k]);
						}
					}
				}
				
				if(temp.getIfconst()[0].equals("null")==false)//if const�� �ִٸ�
				{
					for(int l=0;l<5;l++)
					{
						if(temp.getIfconst()[l].equals("null")==false)
						{
							ifcheck+=1;//null�� �ƴ� if�� ������ �����ش�.
						}
					}
					for(int k=0;k<property.size();k++)
					{
						for(int l=0;l<5;l++)
						{
							if(temp.getIfconst()[l].equals("null")==false)
							{
								if(property.get(k).equals(temp.getIfconst()[l])==true)
								{
									ifcheck2+=1;//��ġ�ϴ� if�� ������ �����ش�.
									
								}
							}
						}
					}
					
					if(ifcheck!=ifcheck2)//if ������ ��ġ���� �ʴ´ٸ�
					{
						ifcheck=0;
						ifcheck2=0;
						property.clear();
						System.out.println("ifindex  "+temp.getIndex());
						cause="If ����ġ";
					}
				}
			}
		}
		
	}
	
	public int getExistence() {
		return existence;
	}

	public void setExistence(int existence) {
		this.existence = existence;
	}

	public int getCausevalue() {
		return causevalue;
	}

	public void setCausevalue(int causevalue) {
		this.causevalue = causevalue;
	}
}
