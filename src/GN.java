import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Vector;
public class GN {
    int MAX=999999;//��ʾ�������
	int count=0;
	int G[][]=new int[1000][1000];//����ͼ������ߵ�Gֵ�ľ���
	int matrix[][]=new int[1000][1000];//GNִ�й������õ����ڽӾ�����BFS���õ�
	int MATRIX[][]=new int[1000][1000];//GNִ�н�����̽����ͨ�����������õ��ľ�����BFS2���õ���
	ArrayList<ArrayList<Integer>> record=new ArrayList<ArrayList<Integer>>();//��¼ÿһ��ͨ�����Ľڵ�
	ArrayList<Integer> mid=new ArrayList<Integer>();//�����������ʱ����ʱ�õ�������
	ArrayList<Integer> mid1=new ArrayList<Integer>();//�����������ʱ����ʱ�õ�������
    int NumOfCon=0;//��ͨ��������
    Graph graph; //ͼ���ݽṹ
    Vector<Redge> RecordEdge=new Vector<Redge>();//��¼GNɾ���ߵ�˳�򣬴洢�ߵ���Ϣ�����顣
    double MaxQ=-99999999;//��¼����Qֵ
    int NumOfConMaxQ;//��¼����Qֵ��Ӧ����ͨ�����ĸ���
    int eee;//��¼����Qֵ��Ӧ��ɾ���ߵ������Ƕ��١�
    int FloydA[][]=new int[1000][1000];//FLoyd��¼�����������̾���ľ���
    int FloydPath[][]=new int[1000][1000];//Floyd��¼���������·���ľ���
    int flag=0;//��ͨ�������ӱ�־������Ϊ1������Ϊ0
	int outside[]=new int[1000];//���ŵ��ⲿ����Ŀ
	int degree[]=new int[1000];
    class Redge{//�Զ������ͣ����ڱ���ߺͶ�Ӧ��ɾ���ñ�֮ǰ��ͼ��Qֵ
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
	public void init(Graph g) {// ��ʼ����Ҫ�õ��ľ��������
		for (int i = 0; i <= 999; i++) {
			for (int j = 0; j <= 999; j++) {
				G[i][j] = 0;
				matrix[i][j] = g.matrix[i][j];
				MATRIX[i][j] = g.matrix[i][j];
			}
			outside[i]=0;
		}
	}
	public void initG() {//ִ��ÿһ��GN��ɾ���߲���֮ǰ����Ҫִ�еĺ��������ڳ�ʼ��Gֵ����
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
		//System.out.println("��ǰ�У�" + mid.size());
		mid1 = (ArrayList<Integer>) mid.clone();
		//System.out.println("�������Ϊ"+mid1.size());
		record.add(mid1);
	}
	
      public int BFSS(Graph g) {//BFSS�Լ�BFS����GN�㷨ִ�й����б�����ͨ�����Լ����ÿ����������һ����ͨ����tag��
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
  		//System.out.println("��ǰ�У�" + mid.size());
  		mid1 = (ArrayList<Integer>) mid.clone();
  		//System.out.println("�������Ϊ"+mid1.size());
  		record.add(mid1);
  	}
      public int BFSS2(Graph g) {//GN�㷨����������ͳ����ͨ�����Լ������ͨ�����ĵ�tag
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

      public void DelEdge(Graph g,double Q) {//�ҵ�Gֵ���ı߲�ɾ�����㷨������ɾ���ıߺ�ɾ����֮ǰ��Qֵ������RecordEdge�����С�
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
    	  System.out.println("ɾ���ߣ�"+g.node[x].ID+" "+g.node[y].ID+" G����"+max);
    	  Redge r=new Redge(x,y,Q);
    	  RecordEdge.addElement(r);
      }
      public double GetQ() {//���㵱ǰ�����ͼ��Qֵ��
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
//    	  System.out.println("first���ڣ�"+first);
//    	  System.out.println("Second���ڣ�"+second);
    	  Q=first-second;
    	  return Q;
      }
      public void InitFloyd() {//��Floyd�㷨��Ҫ�õ���FloydA[][],FloydPath[][]������г�ʼ����
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
      public void Floyd(Graph g){//Floyd �㷨
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
      public void GetPath(int x,int y) { //�������������·����ͬʱ�������ı߶���Gֵ��1��
    	  if(FloydPath[x][y]==-1) return ;
    	  if(FloydPath[x][y]==MAX) {G[x][y]++;return ;}	 
    	  GetPath(x,FloydPath[x][y]);
    	  GetPath(FloydPath[x][y],y);
      }
      public void GetGFloyd() {//����FloydPath���󣬱����������������·��
    	  initG();
    	  for(int i=0;i<=graph.n-1;i++) {
    		  for(int j=i+1;j<=graph.n-1;j++) {
    			  GetPath(i,j);
    		  }
    	  }
      }
      public void G(Graph g) {//GN�㷨���
    	  int n=g.e;//ɾ���ߵĴ���
    	  while(n!=0) {
    	  System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
    	  BFSS(g);
    	  Double Q;
    	  Q=GetQ();
    	  if(Q>MaxQ) {MaxQ=Q;NumOfConMaxQ=NumOfCon;eee=RecordEdge.size();}
    	  System.out.println("Q���ڣ�"+Q);
    	  //getG(g);
    	  InitFloyd();
    	  Floyd(g);
    	  GetGFloyd();
    	  DelEdge(g,Q);
    	  n--;
    	  }
    	  System.out.println("���QΪ"+MaxQ+"  ��ͨ����������"+NumOfConMaxQ+" ɾ���ߵĸ���Ϊ��"+eee);
      }
     public void PreProcess() {//Ŀǰû�õ�
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
		//��ʾ����
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
	public void PreProcessForce() {//������ForceDirectGר��
		NumOfCon=0;
		Enumeration<Redge> e=RecordEdge.elements();
		int x=0;
		//���ѭ�����ڽ�ͼ��״̬��ԭ������Qֵ��Ӧ��ͼ��״̬������RecordEdge���������л�ԭ��
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
