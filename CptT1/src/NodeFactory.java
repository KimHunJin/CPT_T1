import java.util.ArrayList;

public class NodeFactory {
	//private RepresentativeValue valuenode[][];
	private ArrayList<RepresentativeValue> valuenode[];
	private int categorynum;
	private int valuenum;
	
	public NodeFactory(File fp)
	{
		valuenode=new ArrayList[fp.getCategoryMax()+1];
		for(int i=0;i<fp.getCategoryMax()+1;i++)
		{
			valuenode[i]=new ArrayList<RepresentativeValue>();
		}
		
	}
	
	public ArrayList<RepresentativeValue>[] getValueNode()
	{
		return valuenode;
	}
	
	public void makeValue(File fp)
	{
		for(int i=0;i<fp.getMax();i++)
		{
			categorynum=((int) fp.getIndex().get(i))/100;
			valuenum=((int) fp.getIndex().get(i))%100;
			valuenode[categorynum].add(new RepresentativeValue(fp.getIndex().get(i),fp.getWeight().get(i),fp.getName().get(i),fp.getConstraints().get(i)));
			valuenode[categorynum].get(valuenum).setProperty(0, fp.getProperty1().get(i));
			valuenode[categorynum].get(valuenum).setProperty(1, fp.getProperty2().get(i));
			valuenode[categorynum].get(valuenum).setProperty(2, fp.getProperty3().get(i));
			valuenode[categorynum].get(valuenum).setProperty(3, fp.getProperty4().get(i));
			valuenode[categorynum].get(valuenum).setProperty(4, fp.getProperty5().get(i));
			valuenode[categorynum].get(valuenum).setIfconst(0, fp.getIfconst1().get(i));
			valuenode[categorynum].get(valuenum).setIfconst(1, fp.getIfconst2().get(i));
			valuenode[categorynum].get(valuenum).setIfconst(2, fp.getIfconst3().get(i));
			valuenode[categorynum].get(valuenum).setIfconst(3, fp.getIfconst4().get(i));
			valuenode[categorynum].get(valuenum).setIfconst(4, fp.getIfconst5().get(i));
		}
		
	}
	
	public void print(File fp)
	{
		for(int i=0;i<fp.getMax();i++)
		{
			categorynum=((int) fp.getIndex().get(i))/100;
			valuenum=((int) fp.getIndex().get(i))%100;
			System.out.println(valuenode[categorynum].get(valuenum).getIndex());
			System.out.println(valuenode[categorynum].get(valuenum).getWeight());
			System.out.println(valuenode[categorynum].get(valuenum).getName());
			System.out.println(valuenode[categorynum].get(valuenum).getConstraints());
			System.out.println(valuenode[categorynum].get(valuenum).getProperty()[0]);
			System.out.println(valuenode[categorynum].get(valuenum).getIfconst()[0]);
		}
	}
	
	public int getCategoryMax(File fp)
	{
		return fp.getCategorymax();
	}

}
