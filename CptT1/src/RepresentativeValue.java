
public class RepresentativeValue {
	private int index;
	private int weight;
	private String name;
	private String constraints;
	private String property[]=new String[5];
	private String ifconst[]=new String[5];
	private int state;
	private RepresentativeValue prev;
	private RepresentativeValue next;
	
	public RepresentativeValue()
	{
	      this.index = 0;
	      this.weight = 0;
	      this.name = null;
	      this.constraints=null;
	}
	
	public RepresentativeValue(int index, int weight, String name, String constraints)
	{
	      this.index = index;
	      this.weight = weight;
	      this.name = name;
	      this.constraints=constraints;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void setIndex(int num)
	{
		index=num;
	}
	
	public int getWeight()
	{
		return weight;
	}
	
	public void setWeight(int num)
	{
		weight=num;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String text)
	{
		name=text;
	}
	public String getConstraints()
	{
		return constraints;
	}
	
	public void setConstraints(String text)
	{
		constraints= text;
	}
	
	public String[] getProperty()
	{
		return property;
	}
	
	public void setProperty(int num, String text)
	{
		property[num]=text;
	}
	
	public String[] getIfconst()
	{
		return ifconst;
	}
	
	public void setIfconst(int num, String text)
	{
		ifconst[num]=text;
	}
	
	public int getState()
	{
		return state;
	}
	
	public void setState(int num)
	{
		state=num;
	}
	
	public RepresentativeValue getPrev()
	{
		return prev;
	}
	
	public void setPrev(RepresentativeValue node)
	{
		prev=node;
	}
	
	public RepresentativeValue getNext()
	{
		return next;
	}
	
	public void setNext(RepresentativeValue node)
	{
		next=node;
	}

}
