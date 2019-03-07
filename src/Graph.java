import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
class Node {
		String ID;//�ڵ��ID�������ļ��е�����
		String name;//�ڵ������
		String nodetype;//�ļ��еĽڵ�����
		String graphid;//�ļ���ͼ��ID
		int tag;//���ű��
		double cx, cy;//�ڵ�����
		double rx = 10, ry = 10;//�뾶
		double force_x,force_y;//�ڵ㵱ǰ��������������
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
	public Hashtable<String, Integer> NodeIDtransfer = new Hashtable<String, Integer>();//ID�ͱ�Ŷ�Ӧ��Hash��
	public int n = 0;//�ڵ���
	public int e = 0;//����
    public Node []node=new Node[MaxNodeCount];//�ڵ���������
	int[][] matrix = new int[MaxNodeCount][MaxNodeCount];//ͼ���ڽӾ���
	public int []visit=new int[MaxNodeCount];//�ڵ�ķ��ʱ������
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
	public void PreProcess() {//���ڵ�һ�����λ��
		for(int i=0;i<=n-1;i++) {
			node[i].cx=(Math.random()*8000+2000);
			node[i].cy=(Math.random()*8000+1200);
		}
	}
	public int GetDegree(int v) {//�ڵ�v�Ķ�
		int degree=0;
		for(int i=0;i<=this.n-1;i++) {
			if(this.matrix[v][i]==1) degree++;
		}
		return degree;
	}
}
