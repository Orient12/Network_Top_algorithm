import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
class Node {
		String ID;//节点的ID，就是文件中的名字
		String name;//节点的名字
		String nodetype;//文件中的节点类型
		String graphid;//文件中图的ID
		int tag;//社团标号
		double cx, cy;//节点坐标
		double rx = 10, ry = 10;//半径
		double force_x,force_y;//节点当前的受力方向向量
	    int level=0;
		public Node(String ss[]) {
			this.ID = ss[0];
			this.name = ss[1];
			this.nodetype = ss[2];
			this.graphid = ss[3];
		}
		public Node(Node n){
			this.ID=n.ID;
			this.name=n.name;
			this.nodetype=n.nodetype;
			this.graphid=n.graphid;
		}
		public Node() {
		}
	}
public class Graph {
	static int MaxNodeCount=1500;
	public Hashtable<String, Integer> NodeIDtransfer = new Hashtable<String, Integer>();//ID和标号对应的Hash表
	public int n = 0;//节点数
	public int e = 0;//边数
    public Node []node=new Node[MaxNodeCount];//节点类型数组
	int[][] matrix = new int[MaxNodeCount][MaxNodeCount];//图的邻接矩阵
	public int []visit=new int[MaxNodeCount];//节点的访问标记数组
	public Hashtable<String,Integer> TypeToLevel=new Hashtable<>();
	int count=0;
	public Graph() {
		for(int i=0;i<=MaxNodeCount-1;i++) {
			visit[i]=0;
			node[i]=new Node();
		}
		for(int i=0;i<=MaxNodeCount-1;i++) {
			for(int j=0;j<=MaxNodeCount-1;j++) {
				matrix[i][j]=0;
			}
		}

	}
	public void initVisit(){
		for(int i=0;i<=MaxNodeCount-1;i++) {
			visit[i]=0;
			
		}
	}
	public void PreProcess() {//给节点一个随机位置
		for(int i=0;i<=n-1;i++) {
			node[i].cx=(Math.random()*8000+2000);
			node[i].cy=(Math.random()*8000+1200);
		}
	}
	public int GetDegree(int v) {//节点v的度
		int degree=0;
		for(int i=0;i<=this.n-1;i++) {
			if(this.matrix[v][i]==1) degree++;
		}
		return degree;
	}
}
