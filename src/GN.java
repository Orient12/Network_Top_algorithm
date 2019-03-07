import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Vector;
public class GN {
    int MAX=999999;//表示正无穷大。
	int count=0;
	int G[][]=new int[1000][1000];//保存图中任意边的G值的矩阵。
	int matrix[][]=new int[1000][1000];//GN执行过程中用到的邻接矩阵，在BFS中用到
	int MATRIX[][]=new int[1000][1000];//GN执行结束后，探测连通分量个数是用到的矩阵，在BFS2中用到。
	ArrayList<ArrayList<Integer>> record=new ArrayList<ArrayList<Integer>>();//记录每一连通分量的节点
	ArrayList<Integer> mid=new ArrayList<Integer>();//广度优先搜索时的临时用到的数组
	ArrayList<Integer> mid1=new ArrayList<Integer>();//广度优先搜索时的临时用到的数组
    int NumOfCon=0;//连通分量个数
    Graph graph; //图数据结构
    Vector<Redge> RecordEdge=new Vector<Redge>();//记录GN删除边的顺序，存储边的信息的数组。
    double MaxQ=-99999999;//记录最大的Q值
    int NumOfConMaxQ;//记录最大的Q值对应的连通分量的个数
    int eee;//记录最大的Q值对应的删除边的数量是多少。
    int FloydA[][]=new int[1000][1000];//FLoyd记录任意两点间最短距离的矩阵
    int FloydPath[][]=new int[1000][1000];//Floyd记录任意两点间路径的矩阵
    int flag=0;//连通分量增加标志，增加为1，不变为0
	int outside[]=new int[1000];//社团的外部边数目
	int degree[]=new int[1000];
    class Redge{//自定义类型，用于保存边和对应的删除该边之前的图的Q值
    	int x,y;
    	double Q;
    	public Redge() {}
    	public Redge(int i,int j,double Q) {
    		x=i;y=j;this.Q=Q;
    	}
    }
    public GN() {
    	
    }
    public GN(Graph g) {
    	init(g);
    	graph=g;
    	for(int i=0;i<=graph.n-1;i++){
    		degree[i]=graph.GetDegree(i);
		}
    }
	public void init(Graph g) {// 初始化将要用到的矩阵和数组
		for (int i = 0; i <= 999; i++) {
			for (int j = 0; j <= 999; j++) {
				G[i][j] = 0;
				matrix[i][j] = g.matrix[i][j];
				MATRIX[i][j] = g.matrix[i][j];
			}
			outside[i]=0;
		}
	}
	public void initG() {//执行每一次GN的删除边操作之前都将要执行的函数，用于初始化G值矩阵。
		for (int i = 0; i <= 999; i++) {
			for (int j = 0; j <= 999; j++) {
				G[i][j] = 0;
			}
		}
	}
	public void BFS(Graph g, int v,int tag) {
		mid.clear();
		mid.add(v);
		g.visit[v] = 1;
		//System.out.println(g.node[v].ID);
		count++;
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.addLast(v);
		g.node[v].tag=tag;
		while (!queue.isEmpty()) {
			int temp = queue.pollFirst();
			for (int i = 0; i <= g.n - 1; i++) {
				if (matrix[temp][i] == 1 && g.visit[i] != 1) {
					count++;
					mid.add(i);
					//System.out.println(i+" "+g.node[i].tag);
					g.node[i].tag=tag;
				    //System.out.println(g.node[i].ID);
					queue.addLast(i);
					g.visit[i] = 1;
				}
			}
		}
		//System.out.println("当前有；" + mid.size());
		mid1 = (ArrayList<Integer>) mid.clone();
		//System.out.println("点的数量为"+mid1.size());
		record.add(mid1);
	}
	
      public int BFSS(Graph g) {//BFSS以及BFS用于GN算法执行过程中遍历连通分量以及标记每个点属于哪一个连通分量tag。
          record.clear();
    	  g.initVisit();
    	  int num=0;
         for(int j=0;j<=g.n-1;j++) {
    		    if(g.visit[j]!=1) {
    		    	//System.out.println(g.node[j].ID);
    		    	BFS(g,j,NumOfCon);
    		    	num++;
    		    	if(num>NumOfCon) {
    		    		flag=1;
        	            NumOfCon++; 
                    }
    		    }  
    	 }    	  

    	 return count;
      }
      public void BFS2(Graph g, int v,int tag) {
  		mid.clear();
  		mid.add(v);
  		g.visit[v] = 1;
  		//System.out.println(g.node[v].ID);
  		LinkedList<Integer> queue = new LinkedList<Integer>();
  		queue.addLast(v);
  		g.node[v].tag=tag;
  		while (!queue.isEmpty()) {
  			int temp = queue.pollFirst();
  			for (int i = 0; i <= g.n - 1; i++) {
  				if (MATRIX[temp][i] == 1 && g.visit[i] != 1) {
  					count++;
  					mid.add(i);
  					g.node[i].tag=tag;
  				    //System.out.println(g.node[i].ID);
  					queue.addLast(i);
  					g.visit[i] = 1;
  				}
  			}
  		}
  		//System.out.println("当前有；" + mid.size());
  		mid1 = (ArrayList<Integer>) mid.clone();
  		//System.out.println("点的数量为"+mid1.size());
  		record.add(mid1);
  	}
      public int BFSS2(Graph g) {//GN算法结束后用于统计连通分量以及标记连通分量的点tag
          record.clear();
    	  g.initVisit();
    	  int ta=0;
         for(int j=0;j<=g.n-1;j++) {
    		    if(g.visit[j]!=1) {
    		    	//System.out.println(g.node[j].ID);
    		    	BFS2(g,j,ta);
    		    	ta++;
    		    }  
    	 }    	  
    	 return ta;
      }

      public void DelEdge(Graph g,double Q) {//找到G值最大的边并删除的算法，并将删除的边和删除边之前的Q值保存在RecordEdge数组中。
    	  int max=-9999;
    	  int x=0,y=0;
    	  for(int i=0;i<=g.n-1;i++) {
    		  for(int j=0;j<=g.n-1;j++) {
    			  if(matrix[i][j]==1&&G[i][j]>max) {
    				  max=G[i][j];
    				  x=i;y=j;
    			  }
    		  }
    	  }
    	  matrix[x][y]=0;
    	  matrix[y][x]=0;
    	  System.out.println("删除边："+g.node[x].ID+" "+g.node[y].ID+" G等于"+max);
    	  Redge r=new Redge(x,y,Q);
    	  RecordEdge.addElement(r);
      }
      public double GetQ() {//计算当前情况下图的Q值。
    	  double Q=0;
    	  ArrayList<Integer> edge=new ArrayList<Integer>();
    	  double sum1=0;
    	  double sum2=0;
    	  double first=0,second=0;
    	  for(int i=0;i<=record.size()-1;i++) {
    		  edge=record.get(i);
    		  int sumtemp1=0;
    		  int sumtemp2=0;
    		  for(int j=0;j<=edge.size()-1;j++) {
    			    for(int k=0;k<=edge.size()-1;k++) {
    			    	if(matrix[edge.get(j)][edge.get(k)]==1) sumtemp1++;
    			    }
    			    sumtemp2+=degree[edge.get(j)];
    		  }
    		  //System.out.println(sumtemp1);
    		  sum1+=sumtemp1/2;
    		  double tt=sumtemp2/(2.0*graph.e);
    		  sum2+=(tt*tt);
    	  }
    	  first=(double)sum1/graph.e;
    	  second=sum2;
//    	  System.out.println("first等于："+first);
//    	  System.out.println("Second等于："+second);
    	  Q=first-second;
    	  return Q;
      }
      public void InitFloyd() {//对Floyd算法将要用到的FloydA[][],FloydPath[][]矩阵进行初始化。
    	  for(int i=0;i<=graph.n-1;i++) {
    		  for(int j=0;j<=graph.n-1;j++) {
    			  FloydPath[i][j]=-1;
    			  if(matrix[i][j]==1&&i!=j) {
    				  FloydA[i][j]=1;
    				  FloydPath[i][j]=MAX;
    			  }
    			  if(matrix[i][j]==0) FloydA[i][j]=MAX;
    		  }
    	  }
      }
      public void Floyd(Graph g){//Floyd 算法
    	  for(int k=0;k<=g.n-1;k++) {
    		 for(int i=0;i<=g.n-1;i++){
    			   for(int j=0;j<=g.n-1;j++) {
    				  if(FloydA[i][k]+FloydA[k][j]<FloydA[i][j]&&i!=j) {
    					  FloydA[i][j]=FloydA[i][k]+FloydA[k][j];
    					  FloydPath[i][j]=k;
    				  }
    			  }
    		  }
    	  }
      }
      public void GetPath(int x,int y) { //遍历两点间的最短路径的同时，经过的边都将G值加1。
    	  if(FloydPath[x][y]==-1) return ;
    	  if(FloydPath[x][y]==MAX) {G[x][y]++;return ;}	 
    	  GetPath(x,FloydPath[x][y]);
    	  GetPath(FloydPath[x][y],y);
      }
      public void GetGFloyd() {//利用FloydPath矩阵，遍历任意两点间的最短路径
    	  initG();
    	  for(int i=0;i<=graph.n-1;i++) {
    		  for(int j=i+1;j<=graph.n-1;j++) {
    			  GetPath(i,j);
    		  }
    	  }
      }
      public void G(Graph g) {//GN算法入口
    	  int n=g.e;//删除边的次数
    	  while(n!=0) {
    	  System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
    	  BFSS(g);
    	  Double Q;
    	  Q=GetQ();
    	  if(Q>MaxQ) {MaxQ=Q;NumOfConMaxQ=NumOfCon;eee=RecordEdge.size();}
    	  System.out.println("Q等于："+Q);
    	  //getG(g);
    	  InitFloyd();
    	  Floyd(g);
    	  GetGFloyd();
    	  DelEdge(g,Q);
    	  n--;
    	  }
    	  System.out.println("最大Q为"+MaxQ+"  连通分量个数："+NumOfConMaxQ+" 删除边的个数为："+eee);
      }
     public void PreProcess() {//目前没用到
    	 NumOfCon=0;
    	Enumeration<Redge> e=RecordEdge.elements();
    	int x=0;
    	while(x<eee) {
    		Redge temp=e.nextElement();
    		MATRIX[temp.x][temp.y]=0;
    		MATRIX[temp.y][temp.x]=0;
    		x++;
    	}
    	BFSS2(graph);
    	for(int i=0;i<=eee-1;i++){
    		outside[graph.node[RecordEdge.get(i).x].tag]++;
			outside[graph.node[RecordEdge.get(i).y].tag]++;
		}
		for(int i=0;i<=record.size()-1;i++){
			System.out.print(outside[i]+" ");
		}
		//显示坐标
    	int assignx[]=new int[200];
    	int assigny[]=new int[200];
        int level=-1;
        for(int i=0;i<=NumOfConMaxQ-1;i++) { 
        	if(i%7==0) level++;
        	assignx[i]=(i%7)*160;
        	assigny[i]=level*150;
        	assigny[i]+=level*10;
        }
        int a1[]=new int[NumOfConMaxQ];
        int a2[]=new int[NumOfConMaxQ];
        for(int i=0;i<=NumOfConMaxQ-1;i++) {
        	a1[i]=0;
        	a2[i]=0;
        }
//        for(int i=0;i<=graph.n-1;i++) {
//
//        	int xx=assignx[graph.node[i].tag];xx+=20;
//        	int yy=assigny[graph.node[i].tag];yy+=20;
//        	int mid=(int) (Math.random()*160+xx);
//        	int mid1=(int) (Math.random()*170+yy);
////        	int mid=xx;
////        	int mid1=yy;
//        	graph.node[i].cx=(mid);
//        	graph.node[i].cy=(mid1);
//        }
		 for(int i=0;i<=record.size()-1;i++){
		 	ArrayList<Integer> temp=new ArrayList<>();
		 	temp=record.get(i);
		 	int ll=-1;
		 	for(int j=0;j<=temp.size()-1;j++){
		 		if(j%5==0)ll++;
		 		graph.node[temp.get(j)].cx=assignx[graph.node[temp.get(j)].tag]+(j%5)*30+30;
				graph.node[temp.get(j)].cy=assigny[graph.node[temp.get(j)].tag]+ll*30+20;
			}
		 }
     }
	public void PreProcessForce() {//力导向ForceDirectG专用
		NumOfCon=0;
		Enumeration<Redge> e=RecordEdge.elements();
		int x=0;
		//这个循环用于将图的状态还原到最大的Q值对应的图的状态，根据RecordEdge这个数组进行还原。
		while(x<eee) {
			Redge temp=e.nextElement();
			MATRIX[temp.x][temp.y]=0;
			MATRIX[temp.y][temp.x]=0;
			x++;
		}
		BFSS2(graph);
		for(int i=0;i<=eee-1;i++){
			outside[graph.node[RecordEdge.get(i).x].tag]++;
			outside[graph.node[RecordEdge.get(i).y].tag]++;
		}
		for(int i=0;i<=record.size()-1;i++){
			System.out.print(outside[i]+" ");
		}
    }
}
