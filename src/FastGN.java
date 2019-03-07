import java.util.ArrayList;
import java.util.HashSet;

public class FastGN {
    private static int MaxNodeCount= Graph.MaxNodeCount;
    Graph graph;
    ArrayList<Integer> tag=new ArrayList<>();//���ű��
    int maxtag[]=new int[MaxNodeCount];//���Qֵ��Ӧ�Ľڵ������
    int degree[]=new int[MaxNodeCount];//ÿ���ڵ�Ķ���
    int EdgeBetweenConn[][]=new int[MaxNodeCount][MaxNodeCount];//����֮��ı���
    int ConnA[]=new int[MaxNodeCount];//ÿ����������������֮��ı�����
    public FastGN(){}
    public FastGN(Graph g) {
        this.graph = g;
        for(int i=0;i<=graph.n-1;i++){
            graph.node[i].tag=i;
            tag.add(i);
            degree[i]=graph.GetDegree(i);
        }
        for (int i=0;i<=graph.n-1;i++){
            for(int j=0;j<=graph.n-1;j++){
                EdgeBetweenConn[i][j]=graph.matrix[i][j];
            }
            ConnA[i]=degree[i];
        }

    }
    public void converge(int a,int b){//EdgeBetweenConn�����ConnA������и���
        int temp=EdgeBetweenConn[a][b];
        for(int i=0;i<=graph.n-1;i++){
            EdgeBetweenConn[a][i]=EdgeBetweenConn[a][i]+EdgeBetweenConn[b][i];
            EdgeBetweenConn[i][a]=EdgeBetweenConn[i][a]+EdgeBetweenConn[i][b];
        }
        EdgeBetweenConn[a][a]-=temp;
        EdgeBetweenConn[a][a]=EdgeBetweenConn[a][a]+EdgeBetweenConn[b][b];
        ConnA[a]=ConnA[a]+ConnA[b]-temp;
        EdgeBetweenConn[b][b]=0;
    }
    public double IncreaseQ(int a,int b){//����Qֵ����
        double Eij=0;
        double Ai=0,Aj=0;
        Eij=EdgeBetweenConn[a][b];
        Ai=ConnA[a];
        Aj=ConnA[b];
        Ai=Ai/(2*graph.e);
        Aj=Aj/(2*graph.e);
        return 2*(Eij-Ai*Aj);
    }
    public double GetQ(){//����Qֵ
        double first=0,second=0;
        for(int i=0;i<=graph.n-1;i++){
            first+=EdgeBetweenConn[i][i];
        }
        first=first/graph.e;
        for (int i=0;i<=tag.size()-1;i++){
            int loc=tag.get(i);
            double temp=ConnA[loc]+EdgeBetweenConn[loc][loc];
            temp=temp/(2*graph.e);
            second+=temp*temp;
        }
        return first-second;
    }
    public int Fast(){//�������
        long start=System.currentTimeMillis()/1000;
        int xx=0,yy=0;
        double maxQ=-99999;
        while(tag.size()!=1) {
            double max=-99999;
            for (int i = 0; i <= tag.size() - 1; i++) {
                for (int j = i+1; j <= tag.size() - 1; j++) {
                        double deltaQ = IncreaseQ(tag.get(i), tag.get(j));
                        if (deltaQ > max) {
                            max = deltaQ;
                            xx = tag.get(i);
                            yy = tag.get(j);
                        }
                }
            }

            for (int i = 0; i <= graph.n - 1; i++) {
                if (graph.node[i].tag == yy) {
                    graph.node[i].tag = xx;
                }
            }
            converge(xx,yy);
            tag.remove(tag.indexOf(yy));
            double Q = GetQ();
            if (Q > maxQ) {
                maxQ = Q;
                for (int i = 0; i <= graph.n - 1; i++) {
                    maxtag[i] = graph.node[i].tag;
                }
            }
        }
        System.out.println("����Q:"+maxQ);
        HashSet<Integer> sh=new HashSet();
        for(int i=0;i<=graph.n-1;i++){
            graph.node[i].tag=maxtag[i];
            sh.add(graph.node[i].tag);
        }
        long end=System.currentTimeMillis()/1000;
        System.out.println("FastGN����ʱ��"+(end-start));
    return sh.size();
    }
}

