import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.Iterator;

class Point{//点结构
    double x,y;
    public Point(double x,double y){
        this.x=x;
        this.y=y;
    }
}
public class SimpleForceDirect {
    static int MaxNodeCount=Graph.MaxNodeCount;
    double PI =3.1415926;
    double Radius=140;
    Graph graph;
    int matrix[][]=new int[MaxNodeCount][MaxNodeCount];//临时邻接矩阵
    double kr=300000;//斥力常数
    double ks=1;//拉力长数
    double deltaT=0.02;//步长
    double MULTI=2;//图的距离
    int e;//边数
    int degree[]=new int[MaxNodeCount];//节点的度
    int leaf[]=new int[MaxNodeCount];//非叶节点的叶节点数
    public SimpleForceDirect(){

    }
    public SimpleForceDirect(Graph g) {
        this.graph = g;
        e=g.e;
        for (int i = 0; i <= g.n - 1; i++) {
            for (int j = 0; j <= g.n - 1; j++) {
                matrix[i][j] = g.matrix[i][j];
            }
        }
        for(int i=0;i<=graph.n-1;i++){
            degree[i]=graph.GetDegree(i);
        }
        //计算非叶节点的叶节点数量
        for(int i=0;i<=graph.n-1;i++){
            if(degree[i]!=1){
                int count=0;
                for(int j=0;j<=graph.n-1;j++){
                    if(matrix[i][j]==1&&degree[j]==1) count++;
                }
                leaf[i]=count;
            }else{
                leaf[i]=0;
            }
        }
    }
    public ArrayList<Point> getXY(int n,double x,double y){//对非叶节点的叶节点排布进行处理，就是画圆
        ArrayList<Point> a=new ArrayList<Point>();
        for(int i=0;i<=n-1;i++){
            double xx,yy;
            xx=x+Radius*Math.cos(((double)i/n)*2*PI);
            yy=y+Radius*Math.sin(((double)i/n)*2*PI);
            Point p=new Point(xx,yy);
            a.add(p);
        }
        return a;
    }
    public void EliLeaf(){//临时删除叶节点
        int temp=0;
        for(int i=0;i<=graph.n-1;i++){
            if(graph.GetDegree(i)==1){
                for(int j=0;j<=graph.n-1;j++) {
                    graph.matrix[i][j]=0;
                    graph.matrix[j][i]=0;
                }
                e--;
                temp++;
            }
        }
        System.out.println(temp+" 点的个数为 "+graph.n);
    }

    public void updateReplusion(){//更新斥力
        double dx=0,dy=0,f,fx,fy,d,dsq;
        for(int i=0;i<graph.n-1;i++){
            // System.out.println(graph.node[i].x+" ojkjkjkjkjk "+graph.node[i].y);
            for(int j=i+1;j<=graph.n-1;j++) {
                dx=(graph.node[i].cx-graph.node[j].cx)/MULTI;
                dy=(graph.node[i].cy-graph.node[j].cy)/MULTI;
                dsq=dx*dx+dy*dy;
                d=Math.sqrt(dsq);
                //if(i%100==0&&j==1) System.out.println("d+ "+d+" dsp:"+dsq);
                f=kr*degree[i]*degree[j]/10/dsq;
                fx=f*dx/d;
                fy=f*dy/d;
                graph.node[i].force_x+=fx;
                graph.node[i].force_y+=fy;
                graph.node[j].force_x-=fx;
                graph.node[j].force_y-=fy;
            }
        }
    }
    public void updateSpring(){//更新拉力
        double dx,dy,f,fx,fy,d,dsq;
        for(int i=0;i<graph.n-1;i++) {
            for (int j = i+1; j <= graph.n - 1; j++) {
                if(graph.matrix[i][j]==1) {
                    dx = (graph.node[i].cx - graph.node[j].cx)/MULTI;
                    dy = (graph.node[i].cy - graph.node[j].cy)/MULTI;
                    d = Math.sqrt(dx * dx + dy * dy);
                    f = ks * d;
                    fx = f * dx / d;
                    fy = f * dy / d;
                    graph.node[i].force_x -= fx;
                    graph.node[i].force_y -= fy;
                    graph.node[j].force_x += fx;
                    graph.node[j].force_y += fy;
                }
            }
        }
    }
    public void update()//更新距离
    {
        double dx,dy,dsq,s;
        for(int i=0;i<graph.n;i++)
        {

            dx=deltaT*graph.node[i].force_x;
            dy=deltaT*graph.node[i].force_y;
            // dsq=dx*dx+dy*dy;
//            if(dsq>MAX_DISPLACEMENT_SQ)
//            {
//                s=sqrt(MAX_DISPLACEMENT_SQ/dsq);
//                dx*=s;
//                dy*=s;
//            }
            graph.node[i].cx+=dx*MULTI;
            graph.node[i].cy+=dy*MULTI;
        }
    }
    public  void repeatCalc(int n)//迭代
    {
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<graph.n;j++){
                graph.node[j].force_x=0.0;
                graph.node[j].force_y=0.0;
            }
            updateReplusion();
            updateSpring();
            update();
        }
    }
    public void diect(){//本程序入口
        repeatCalc(3000);
        for(int i=0;i<= graph.n-1;i++){//这个循环将删除的叶节点重新排布之后加回来
            if(degree[i]!=1){
                ArrayList<Point> temp=new ArrayList<Point>();
                System.out.println("leaf:"+leaf[i]);
                temp=getXY(leaf[i],graph.node[i].cx,graph.node[i].cy);
                Iterator<Point> it=temp.iterator();
                for(int j=0;j<=graph.n-1;j++){
                    if(matrix[i][j]==1&&degree[j]==1){
                        graph.matrix[i][j]=matrix[i][j];
                        graph.matrix[j][i]=matrix[j][i];
                        Point tt=it.next();
                        graph.node[j].cx=tt.x;
                        graph.node[j].cy=tt.y;

                    }
                }
            }
        }
    }
}
