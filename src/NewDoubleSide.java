import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import javafx.geometry.Side;

import java.util.*;

class Terminal{//双端端点类
    int a,b;
    public Terminal(Terminal  t){
        this.a=t.a;
        this.b=t.b;
    }
    public Terminal(int a,int b){
        this.a=a;
        this.b=b;
    }
    public Terminal(){

    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Terminal){
            Terminal t=(Terminal) obj;
            if(t.a==this.a&&t.b==this.b) return true;
            else return false;
        }
        return false;
    }
}
class Dis{
    int a;
    double distance;
    public Dis(){}
    public Dis(int x,double y){this.a=x;this.distance=y;}
}
public class NewDoubleSide {
    Graph graph=new Graph();
    double MULTI=2;
    ArrayList<ArrayList<Integer>> conn=new ArrayList<>();//连通分量
    ArrayList<Integer> temp=new ArrayList<>();//Subsidiary
    ArrayList<Integer> path=new ArrayList<>();//Subsidiary
    ArrayList<ArrayList<Terminal>> side=new ArrayList<>();//每个连通分量的所有双端端点
    int degree[]=new int[Graph.MaxNodeCount];//节点度数
    ArrayList<ArrayList<Integer>>  sub=new ArrayList<ArrayList<Integer>>();//每个双端结构的每个主干
    ArrayList<Integer> pathTemp=new ArrayList<Integer>();//Subsidiary
    ArrayList<Integer> subPath=new ArrayList<Integer>();//Subsidiary
    Dir dir[]=new Dir[6];
    int LengthTemp=0;
    int MinLengthTemp=9999;
    double Radius=150;
    double PI=3.1415926;
    Comparator c1=new Comparator<ArrayList<Integer>>() {//比较规则
        @Override
        public int compare(ArrayList<Integer> a,ArrayList<Integer> b) {
            if(a.size()>b.size()) return 1;
            else return -1;
        }
    };
    public NewDoubleSide(){}
    public NewDoubleSide(Graph g){
        this.graph=g;
        for (int i=0;i<=graph.n-1;i++){
            degree[i]=graph.GetDegree(i);
        }
        for(int i=0;i<=5;i++){
            dir[i]=new Dir();
        }
    }

    public void DFS(int v,double x,double y,int num) {//保存每个连通分量
        graph.node[v].cx=(Math.random()*(x+3000)+x);
        graph.node[v].cy=(Math.random()*(y+3000)+y);
        temp.add(v);
        graph.node[v].tag=num;
        graph.visit[v]=1;
        for(int i=0;i<=graph.n-1;i++) {
            if(graph.matrix[v][i]==1&&graph.visit[i]!=1) DFS(i,x,y,num);
        }
    }
    public int DFSS() {//深度优先搜索保存连通分量，并为每个连通分量分配位置
        int num=0;
        double x=1000,y=1500;
        for(int i=0;i<=graph.n-1;i++) {
            if(graph.visit[i]!=1) {
                DFS(i,x+=5000,y,num);
                num++;
                path=(ArrayList<Integer>) temp.clone();
                temp.clear();
                conn.add(path);
            }
        }
        return num;
    }
    //对双端端点进行配对
    int BB;
    int vi[]=new int[Graph.MaxNodeCount];
    HashMap<Integer,Integer> op=new HashMap<>();
    public void init(){
        for (int i=0;i<=graph.n-1;i++)
            vi[i]=0;
    }
    public void searchAnother(int a,int pre){//从二度节点a搜索另一端的端点
        vi[a]=1;
        for(int i=0;i<=graph.n-1;i++){
            if(graph.matrix[a][i]==1&&vi[i]!=1&&i!=pre){
                if(degree[i]==2) searchAnother(i,pre);
                else {BB=i;return;}
            }
        }
    }
    public int find(int a){//查找双端的另一端
        int b=-1;
        int deg=degree[a];
        int min=999999;
        op.clear();
        //正方向判断
        for(int i=0;i<=graph.n-1;i++){//找到本节点通过二度节点可以搜索到另一端的非二度节点
            if(graph.matrix[a][i]==1&&degree[i]==2){
                searchAnother(i,a);
                System.out.print(BB);
                if(op.containsKey(BB)){
                    int count=op.get(BB);
                    count+=1;
                    op.remove(BB);
                    op.put(BB,count);
                }else {
                    op.put(BB,1);
                }
            }
        }
        System.out.println("op.size:"+op.size());
        int mm=-1;
        for(int ss:op.keySet()){//找到最多连接的那个节点
            if(op.get(ss)>mm){
                b=ss;
                mm=op.get(ss);

            }System.out.print(mm+":"+ss+" ");
        }
        System.out.println();
        op.clear();
        //反方向判断
        init();
        for(int i=0;i<=graph.n-1;i++){//找到本节点通过二度节点可以搜索到的另一端的非二度节点
            if(graph.matrix[b][i]==1&&degree[i]==2){
                searchAnother(i,b);
                System.out.print(BB);
                if(op.containsKey(BB)){
                    int count=op.get(BB);
                    count+=1;
                    op.remove(BB);
                    op.put(BB,count);
                }else {
                    op.put(BB,1);
                }
            }
        }
        mm=-1;
        int n=-1;
        System.out.println("op.size:"+op.size());
        for(int ss:op.keySet()){//找到主干连接最多的那个节点
            if(op.get(ss)>mm){
                n=ss;
                mm=op.get(ss);

            }System.out.print(mm+":"+ss+" ");
        }
        System.out.println();
        System.out.println(n+" aasssssssssssssssssssss "+a);
        if(n!=a){b=-1;}//正方向和反方向相等则将搜索到的b返回作为a对应的双端结构的另一端点
        System.out.println(b);

        return b;
    }
    public void oppo(){//保存每个双端的端点
        graph.initVisit();
        ArrayList<Terminal> temp=new ArrayList<>();
        for(int i=0;i<=conn.size()-1;i++){
            ArrayList<Integer> temp1=new ArrayList<>();
            temp1=conn.get(i);
            for(int j=0;j<=temp1.size()-1;j++){
                Terminal t=new Terminal();
                int temp2=temp1.get(j);
                if(degree[temp2]>2&&graph.visit[temp2]!=1){
                    int count=0;
                    for(int p=0;p<=graph.n-1;p++){
                        if(graph.matrix[temp2][p]==1){
                            if(degree[p]==2) count++;
                        }
                    }
                    if(count>2) {

                        int another = find(temp2);
                        if (another != -1) {
                            t.a = temp2;
                            t.b = another;
                            temp.add(t);
                            graph.visit[temp2] = 1;
                            graph.visit[another]=1;
                        } else {
                            //力导向时注释掉
//                         graph.node[temp2].cx = 0;
//                         graph.node[temp2].cy = 0;
                        }

                    }
                }
            }
            side.add((ArrayList<Terminal>) temp.clone());
            temp.clear();
        }
    }


    public void subDFS(int v,int nodei,int nodej,int flag){//搜索双端结构的主干
        if(flag==1) vi[v]=1;
        else graph.visit[v]=1;
        if(v==nodej){
            subPath=(ArrayList<Integer>)pathTemp;
        }
        if(degree[v]>2) return ;
        pathTemp.add(v);
        for(int i=0;i<=graph.n-1;i++){
            if(graph.matrix[v][i]==1&&vi[i]!=1&&flag==1){
                subDFS(i,nodei,nodej,flag);
            }
            if(graph.matrix[v][i]==1&&graph.visit[i]!=1&&flag!=1){
                subDFS(i,nodei,nodej,flag);
            }
        }
    }
    public void subDFSS(int x,int y,int flag){//搜索出双端结构的每一个主干
        //System.out.println(x+" 端点 "+y);
        sub.clear();
        for(int i=0;i<=graph.n-1;i++) {
            MinLengthTemp = 9999999;
            LengthTemp = 0;
            pathTemp.clear();
            subPath.clear();
            ArrayList<Integer> mid=new ArrayList<Integer>();
            if(flag==1){
                vi[x]=1;
                vi[y]=1;
            }else{
//                graph.visit[x]=1;
//                graph.visit[y]=1;
            }
            if(graph.matrix[x][i]==1&&graph.node[i].level<=20){
                init();
                subDFS(i,x,y,flag);
                mid=(ArrayList<Integer>) subPath.clone();
                //System.out.println("子连通分量的大小：" +mid.size());
                sub.add(mid);
            }
        }
    }
    public void sort(){//对双端结构的主干数组sub按照比较规则c1进行排序
        ArrayList<ArrayList<Integer>> temp=new ArrayList<ArrayList<Integer>>();
        temp=(ArrayList<ArrayList<Integer>>) sub.clone();
        int count=sub.size();
        //System.out.println("------------+"+count);
        int x=0;
        if(count%2==0){
            for(int i=0;i<count/2;i++){
                sub.set((count+1)/2+i,temp.get(x++));
                sub.set(((count+1)/2-1)-i,temp.get(x++));
            }
        }else{
            sub.set(count/2,temp.get(0));
            x++;
            System.out.println("dddddddddddddddddd");
            for(int i=1;i<=count/2;i++){
                sub.set(count/2-i,temp.get(x++));
                sub.set(count/2+i,temp.get(x++));
                System.out.print((count/2-i)+" "+(count/2+i)+" ");
            }
        }
    }
    public void draw(Terminal t,double x,double y,int flag){//为双端中的每个主干分配坐标，其中参数x,y是双端上端点的坐标

        subDFSS(t.a,t.b,flag);
        sub.sort(c1);//对双端结构的主干数组sub按照从小到大排序
        sort();//交换奇数和偶数索引对应元素的位置
        double m=0,n=0;
        int count=0;
        for(int i=0;i<=sub.size()-1;i++){
            ArrayList<Integer> p=new ArrayList<>();
            p=sub.get(i);
            for(int j=0;j<=p.size()-1;j++){
                m+=graph.node[p.get(j)].cx;
                n+=graph.node[p.get(j)].cy;
                count++;
            }
        }
        m=m/count;
        n=n/count;
        //选择使用：
        m=x;n=y;

        double mm=m,nn=n;
        for(int i=0;i<=sub.size()-1;i++){
            ArrayList<Integer> p=new ArrayList<>();
            p=sub.get(i);
            nn=n;
            for(int j=0;j<=p.size()-1;j++){
                graph.visit[p.get(j)]=1;
                graph.node[p.get(j)].cx=mm;
                graph.node[p.get(j)].cy=nn;
                nn+=25*MULTI;
            }
            mm+=25*MULTI;
        }
        if(flag==1){
            vi[t.a]=1;
            vi[t.b]=1;
        }else{
            graph.visit[t.a]=1;
            graph.visit[t.b]=1;
        }
        graph.node[t.a].cx=m+sub.size()*12.5*MULTI;
        graph.node[t.a].cy=n-50*MULTI;
        graph.node[t.b].cx=m+sub.size()*12.5*MULTI;
        graph.node[t.b].cy=nn+75*MULTI;
    }

    public void dispose(){//为每个双端结构的上端点分配坐标，其实就是双端结构的位置
        int count=0;
        graph.initVisit();
        for(int i=0;i<=side.size()-1;i++){
            ArrayList<Terminal> temp=new ArrayList<>();
            temp=side.get(i);
            for(int j=0;j<=temp.size()-1;j++){
                Terminal temp2=temp.get(j);
                if(temp2.a!=-1||temp2.b!=-1) draw(temp2,graph.node[temp2.a].cx,graph.node[temp2.a].cy,0);//draw(temp2,x+count*1000,y);
                count++;
            }
            count=0;
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
    public void BFS(int v){//对所有不属于任何双端结构的点按照广度优先搜索分配坐标
        LinkedList<Integer> queue=new LinkedList<>();
        queue.push(v);
        while(!queue.isEmpty()){
            int temp=queue.getFirst();
            queue.poll();
            int count=0;
            ArrayList<Integer> neighbor=new ArrayList<Integer>();
            for(int i=0;i<=graph.n-1;i++){
                if(graph.matrix[temp][i]==1&&vi[i]!=1) {
                    queue.push(i);
                    if (graph.visit[i] != 1) {
                        neighbor.add(i);
                        //配置坐标
//                        graph.node[i].cx = dir[count % 6].x + graph.node[temp].cx;
//                        graph.node[i].cy = dir[count % 6].y + graph.node[temp].cy;
                        graph.visit[i] = 1;
                        count++;
                    }
                    vi[i] = 1;
                }
            }
            ArrayList<Point> zuobiao=new ArrayList<Point>();
            zuobiao=getXY(count,graph.node[temp].cx,graph.node[temp].cy);
            Iterator<Point> it=zuobiao.iterator();
            for(int j=0;j<=count-1;j++){
                int x=neighbor.get(j);
                Point tt=it.next();
                graph.node[x].cx=tt.x;
                graph.node[x].cy=tt.y;
            }
            neighbor.clear();
        }
    }
    public boolean JudgeCross(Terminal x,Terminal y){
        double x0,x1,x2,x3;
        double y0,y1,y2,y3;
        x0=graph.node[x.a].cx;
        x1=graph.node[x.b].cx;
        x2=graph.node[y.a].cx;
        x3=graph.node[y.b].cx;
        y0=graph.node[x.a].cy;
        y1=graph.node[x.b].cy;
        y2=graph.node[y.a].cy;
        y3=graph.node[y.b].cy;
        double k1=(y3-y2)/(x3-x2);
        double k2=(y1-y0)/(x1-x0);
        double x_c=(k1*x2-k2*x0+y0-y2)/(k1-k2);
        double y_c=k1*(x_c-x2)+y2;
        double bigger23,lower23;
        double bigger01,lower01;
        if(x2>x3){bigger23=x2;lower23=x3;}
        else {bigger23=x3;lower23=x2;}
        if(x0>x1){bigger01=x0;lower01=x1;}
        else {bigger01=x1;lower01=x0;}
        //System.out.println(x_c+" "+lower01+" "+lower23+" "+bigger01+" "+bigger23);
        if(x_c>lower01&&x_c>lower23&&x_c<bigger01&&x_c<bigger23) return true;
        else return false;
    }
    public double GetDistance(int a,int b){
        double xa=graph.node[a].cx;
        double ya=graph.node[a].cy;
        double xb=graph.node[b].cx;
        double yb=graph.node[b].cy;
        return Math.sqrt((xa-xb)*(xa-xb)+(ya-yb)*(ya-yb));
    }
    public int cmp(Dis a,Dis b){
        if(a.distance>b.distance) return 1;
        else return -1;
    }
    public void sort(Dis EdgeA[],int count){
        for(int i=0;i<=count-1;i++){
            for(int j=i+1;j<=count-1;j++){
                if(EdgeA[i].distance<EdgeA[j].distance){
                    Dis temp=new Dis();
                    temp=EdgeA[i];
                    EdgeA[i]=EdgeA[j];
                    EdgeA[j]=temp;
                }
            }
        }
    }
    public Terminal FindEdge(Terminal t){
        int a=t.a;
        int b=t.b;
        //System.out.println(a+" dd "+b);
        int counta=0,countb=0;
        Dis EdgeA[]=new Dis[500];
        Dis EdgeB[]=new Dis[500];
        for(int i=0;i<=graph.n-1;i++){
            double ix=graph.node[i].cx;
            double iy=graph.node[i].cy;
            if(graph.matrix[a][i]==1&&i!=b){
                EdgeA[counta]=new Dis(i,GetDistance(a,i));
                counta++;
            }
            if(graph.matrix[b][i]==1&&i!=a){
                EdgeB[countb]=new Dis(i,GetDistance(b,i));
                countb++;
            }
        }
        sort(EdgeA,counta);
        sort(EdgeB,countb);
        //System.out.println(EdgeA[0].distance+" "+EdgeA[1].distance);
        if(EdgeA[0].distance>EdgeA[1].distance*3||EdgeB[0].distance>EdgeB[1].distance*3){
            return new Terminal(EdgeA[0].a,EdgeB[0].a);
        }
        return null;
    }
    public void Adjust(){
        for(int i=0;i<=side.size()-1;i++){
            ArrayList<Terminal> temp=new ArrayList<>();
            temp=side.get(i);
            for(int j=0;j<=temp.size()-1;j++){
                Terminal temp1=new Terminal();
                temp1=temp.get(j);
                Terminal re=new Terminal();
                re=FindEdge(temp1);
                if(re!=null){
                    boolean cross=JudgeCross(new Terminal(temp1.a,re.a),new Terminal(temp1.b,re.b));
                    if(cross==true){
                        //重新对该联通分量进行布局
                        System.out.println("调换："+i);
                        int t=temp1.a;
                        temp1.a=temp1.b;
                        temp1.b=t;
                        draw(temp1,graph.node[temp1.a].cx,graph.node[temp1.a].cy,1);
                    }
                }
            }
        }
    }
    public void start() {//程序入口
        DFSS();
        oppo();
        for(int m=0;m<=side.size()-1;m++){
            ArrayList<Terminal> tt=new ArrayList<>();
            tt=side.get(m);
            for(int n=0;n<=tt.size()-1;n++){
                Terminal tt1=new Terminal();
                System.out.println(tt.get(n).a+" "+tt.get(n).b);
            }
        }
//        FDofNewDoubleSide fd=new FDofNewDoubleSide(graph);
//        fd.diect();
        FDofDS dod=new FDofDS(graph);
        dod.diect();
        dispose();
        Adjust();
        init();
        int count1=0;
        int count2=0;
        for(int i=0;i<=graph.n-1;i++){
            if(graph.visit[i]==1) count1++;
            else count2++;
            if(vi[i]!=1){
               BFS(i);
            }
        }
        System.out.println(count1+" "+count2);
    }
}
