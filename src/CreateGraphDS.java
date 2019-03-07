import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
public class CreateGraphDS {
       Hashtable<String,Integer> nodeIDtransfer=new Hashtable();
       Graph graph=new Graph();
      public void getHashtable() {
    	nodeIDtransfer.clear();
       ReadFile rf=new ReadFile();
       BufferedReader br=rf.ReadNode();
       String line="";
       String everyline="";
       int count=0;
       try {
    	line=br.readLine();
		while((line=br.readLine())!=null) {
			   everyline=line;			  
		       String ss[]=everyline.split(","); 
		       for(int i=0;i<=3;i++) {
					ss[i]=ss[i].replaceAll("[^0-9a-zA-Z]+","");
				}
		       nodeIDtransfer.put(ss[0], count);
		       graph.node[count]=new Node(ss);
		       count++;
		   }
		graph.n=count;
		graph.NodeIDtransfer=nodeIDtransfer;
	     } catch (IOException e) {
		// TODO 自动生成的 catch 块
		    e.printStackTrace();
	       }
       }
       public void setEdge() {
    	   ReadFile rf=new ReadFile();
    	   BufferedReader br=rf.ReadEdge();
    	   String line="";
    	   String everyline="";
    	   int count=0;
    	   try {
    		line=br.readLine();
			while((line=br.readLine())!=null) {
				everyline=line;
				String ss[]=everyline.split(",");
				for(int i=0;i<=2;i++) {
					ss[i]=ss[i].replaceAll("[^0-9a-zA-Z]+","");
				}

				graph.matrix[graph.NodeIDtransfer.get(ss[1])][graph.NodeIDtransfer.get(ss[2])]=1;
				graph.matrix[graph.NodeIDtransfer.get(ss[2])][graph.NodeIDtransfer.get(ss[1])]=1;
				count++;
			   }
			graph.e=count;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
       }
       public Graph getGraph() {
    	   getHashtable();
    	   setEdge();
    	   return graph;
       }
	public void setType(){
		ReadFile rf=new ReadFile();
		BufferedReader br=rf.ReadType();
		String line="";
		String everyline="";
		int count=0;
		try {
			line=br.readLine();
			while((line=br.readLine())!=null) {
				everyline=line;
				String ss[]=everyline.split(",");
				graph.TypeToLevel.put(ss[0],Integer.parseInt(ss[1]));
				count++;
			}
			for(int i=0;i<=graph.n-1;i++){
				int x=0;
				if(graph.TypeToLevel.containsKey(graph.node[i].nodetype))  x=graph.TypeToLevel.get(graph.node[i].nodetype);
				graph.node[i].level=x;
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	   public void prt(){//找到度最大的两个点
		   int x=0,y=0;
		   int max1=-1,max2=-1;
		   int temp;
		   for(int i=0;i<=graph.n-1;i++){
			   temp=graph.GetDegree(i);
			   if(temp>=max1){
				   max2=max1;
				   y=x;
				   max1=temp;
				   x=i;
			   }
		   }
		   System.out.println(graph.node[x].ID+" "+graph.GetDegree(x));
		   System.out.println(graph.node[y].ID+" "+graph.GetDegree(y));
	   }
	   public void EliLeaf(){
      	for(int i=0;i<=graph.n-1;i++){
      		if(graph.GetDegree(i)==1){
      			for(int j=0;j<=graph.n-1;j++) {
      				graph.matrix[i][j]=0;
      			    graph.matrix[j][i]=0;

      			}
      			graph.e--;
			}
		}
		System.out.println("边的个数为"+graph.e);
	   }
}
