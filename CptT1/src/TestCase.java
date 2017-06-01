import java.util.ArrayList;

public class TestCase{
	private ArrayList<RepresentativeValue> al = new ArrayList<RepresentativeValue>();
	private int blackflag=0;
	private int whiteflag=0;
	private int redflag=1;
	private int ifcheck=0;
	private int ifcheck2=0;
	private ArrayList<String> property= new ArrayList<String>();
	
	private String index=null;
	private float weight;
	
	public ArrayList<RepresentativeValue> getAl() {
		return al;
	}
	public void setAl(ArrayList<RepresentativeValue> al) {
		this.al = al;
	}
	
	
	public void checkTestCase(NodeFactory nf,int categorynum)
	{
		for(int j=0;j<nf.getValueNode()[categorynum].size();j++)//다음 카테고리에 개수만큼 loop돌면서 state 0인걸 찾아준다.
		{
			blackflag=0;
			if(nf.getValueNode()[categorynum].get(j).getState()==0)
			{
				nf.getValueNode()[categorynum].get(j).setState(1);
				blackflag=1;
				if(j+1==nf.getValueNode()[categorynum].size())
				{
					blackflag=0;
				}
				break;
			}	
		}
		
		if(blackflag==0)
		{
			for(int j=0;j<nf.getValueNode()[categorynum].size();j++)//다음 카테고리에 개수만큼 loop돌면서 
			{
				nf.getValueNode()[categorynum].get(j).setState(0);
			}
			if(categorynum-1>0)
			{
				checkTestCase(nf,categorynum-1);
			}
			else
			{
				al.clear();
				whiteflag=1;
				return;
	
			}
			
		}
		
	}
	public boolean makeSingle(NodeFactory nf,File fp)
	{
		for(int i=1;i<=nf.getCategoryMax(fp);i++)
		{
			for(int j=0;j<nf.getValueNode()[i].size();j++)//다음 카테고리에 개수만큼 loop돌면서 state 0인걸 찾아준다.
			{
				RepresentativeValue temp=nf.getValueNode()[i].get(j);
				if(temp.getState()==0)
				{
					if(temp.getConstraints().equals("single")||temp.getConstraints().equals("error"))
					{
						al.add(nf.getValueNode()[i].get(j));
						temp.setState(2);
						return false;
					}
				}
			}
		}
		
		return true;
	}
	public boolean makeTestCase(NodeFactory nf,File fp)
	{
		if(whiteflag==1)
		{
			return true;
		}
		for(int i=1;i<=nf.getCategoryMax(fp);i++)
		{
			for(int j=0;j<nf.getValueNode()[i].size();j++)//다음 카테고리에 개수만큼 loop돌면서 state 0인걸 찾아준다.
			{
				redflag=1;
				if(nf.getValueNode()[i].get(j).getState()==0)
				{
					RepresentativeValue temp = nf.getValueNode()[i].get(j);
					
					if(temp.getConstraints().equals("single")||temp.getConstraints().equals("error"))
					{
						nf.getValueNode()[i].get(j).setState(2);
						
						if(j+1==nf.getValueNode()[i].size())
						{
							checkTestCase(nf,i);
							property.clear();
							al.clear();
							makeTestCase(nf,fp);
							if(whiteflag==1)
							{
								return true;
							}
							return false;
						}
					}
					else
					{
						if(temp.getProperty()[0].equals("null")==false)
						{
							for(int k=0;k<5;k++)
							{
								if(temp.getProperty()[k].equals("null")==false)
								{
									property.add(temp.getProperty()[k]);
								}
							}
						}
						
						if(temp.getIfconst()[0].equals("null")==false)
						{
							for(int l=0;l<5;l++)
							{
								if(temp.getIfconst()[l].equals("null")==false)
								{
									ifcheck+=1;//null이 아닌 if의 개수를 구해준다.
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
											ifcheck2+=1;//일치하는 if의 개수를 구해준다.
											
										}
									}
								}
							}
							
							if(ifcheck!=ifcheck2)
							{
								redflag=0;
								ifcheck=0;
								ifcheck2=0;
								temp.setState(1);
								property.clear();
								al.clear();
								i=1;
								j=0;
								//return false;
							}
						}
						
						if(redflag==1)
						{
							ifcheck=0;
							ifcheck2=0;
							al.add(temp);
							if(i==nf.getCategoryMax(fp))
							{
								temp.setState(1);
								if(j+1==nf.getValueNode()[i].size())
								{
									checkTestCase(nf,i);
								}
							}
							redflag=0;
							break;
						}
					}
					
				}
				
			}
						
		}
		if(whiteflag==1)
		{
			return true;
		}
		return false;		
		
	}
	
	public String getIndex()
	{
		index="";
		for(int i=0;i<al.size();i++)
		{
			if(i+1!=al.size())
			{
				index+=al.get(i).getIndex()+".";
			}
			else
			{
				index+=al.get(i).getIndex();
			}
		}
		return index;
	}
	
	public float getWeight()
	{
		int sum=0;
		for(int i=0;i<al.size();i++)
	    {
			sum=al.get(i).getWeight();
	    }
		if(al.size()>0)
		{
			weight=sum/al.size();
		}
		else
		{
			weight=0;
		}
		return weight;
	}
	
	 public void printNode(){
	      for(int i=0;i<al.size();i++)
	      {
	    	  System.out.print(al.get(i).getIndex()+"  ");
	    	  //System.out.print("상태: "+al.get(i).getConstraints()+"  ");
	    	  /*System.out.print("조건1: "+al.get(i).getProperty()[0]+"  ");
	    	  System.out.print("조건2: "+al.get(i).getProperty()[1]+"  ");
	    	  System.out.print("if1: "+al.get(i).getIfconst()[0]+"  ");
	    	  System.out.print("if2: "+al.get(i).getIfconst()[1]+"  ");*/
	    	  
	      }
	      System.out.println("");
	   }

}
