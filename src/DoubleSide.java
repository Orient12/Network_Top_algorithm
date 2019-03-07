import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

class Dir{
    double x,y;
}
public class DoubleSide {
        Graph graph;
        ArrayList<ArrayList<Integer>>  sub=new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> pathTemp=new ArrayList<Integer>();
        ArrayList<Integer> path=new ArrayList<Integer>();
        Dir dir[]=new Dir[6];
        int MinLengthTemp=99999;
        int LengthTemp=0;
        int []visit=new int[1000];
        int nodei,nodej;//度数最大的两个点
        Comparator c1=new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> a,ArrayList<Integer> b) {
                    if(a.size()>b.size()) return 1;
                    else return -1;
            }
        };
        public DoubleSide(Graph g){
            this.graph=g;
            for(int i=0;i<=999;i++){
                visit[i]=0;
            }
            for(int i=0;i<=5;i++){
                dir[i]=new Dir();
            }
            double radius=150;
            double PI=3.1415926;
            dir[5].x=radius*Math.cos(-PI/4);
            dir[5].y=radius*Math.sin(-PI/4);
            dir[4].x=radius*Math.cos(-3*PI/4);
            dir[4].y=radius*Math.sin(-3*PI/4);
            dir[3].x=radius*Math.cos(0*PI/4);
            dir[3].y=radius*Math.sin(0*PI/4);
            dir[2].x=radius*Math.cos(4*PI/4);
            dir[2].y=radius*Math.sin(4*PI/4);
            dir[1].x=radius*Math.cos(1*PI/4);
            dir[1].y=radius*Math.sin(1*PI/4);
            dir[0].x=radius*Math.cos(3*PI/4);
            dir[0].y=radius*Math.sin(3*PI/4);
        }
        public void getMax(){//得到最大的度数的两个点
            int max1=-1,max2=-1;
            int node1=-1,node2=-1;
            for(int i=0;i<=graph.n-1;i++){
                int temp=graph.GetDegree(i);

                if(temp>max1){
                    if(max1>max2){
                        max2=max1;
                        node2=node1;
                    }
                    max1=temp;
                    node1=i;
                }else{
                    if(temp>max2){
                        max2=temp;
                        node2=i;
                    }
                }
            }
            nodei=node1;nodej=node2;
        }
        public void DFS(int v){//深度优先搜索得到主干
            visit[v]=1;
            if(v==nodej){
                visit[v]=0;
                if(LengthTemp<MinLengthTemp){System.out.println("长度："+LengthTemp);
                    MinLengthTemp=LengthTemp;
                    path=(ArrayList<Integer>) pathTemp.clone();
                }
                return;
            }
            pathTemp.add(v);
            LengthTemp++;
            for(int i=0;i<=graph.n-1;i++){
                if(graph.matrix[v][i]==1&&visit[i]==0&&i!=nodei){
                    DFS(i);
                }
            }
            LengthTemp--;
            pathTemp.remove(pathTemp.size()-1);
            visit[v]=0;
        }
        public void DFSS(){//深度优先搜索的开始函数
            for(int i=0;i<=graph.n-1;i++) {
                MinLengthTemp = 9999999;
                LengthTemp = 0;
                pathTemp.clear();
                path.clear();
                ArrayList<Integer> temp=new ArrayList<Integer>();
                if(graph.matrix[nodei][i]==1){
                     DFS(i);
                    temp=(ArrayList<Integer>) path.clone();
                    System.out.println("子连通分量的大小：" +temp.size());
                    sub.add(temp);
                }
            }


        }
        public void init(){//初始化visit数组
            for(int i=0;i<=999;i++){
                visit[i]=0;
            }
        }


        public void process(){//主干节点坐标处理
            int distance;
            distance=20000/sub.size();
            int x=400,y=200;
            for(int i=0;i<=sub.size()-1;i++){
                ArrayList<Integer> temp1;
                temp1=(ArrayList<Integer>) sub.get(i).clone();
                y=200;
                for(int j=0;j<=temp1.size()-1;j++){
                    int temp2=temp1.get(j);
                    graph.node[temp2].cx=x;
                    graph.node[temp2].cy=y;
                            y+=500;
                }
                x+=distance;
            }
            graph.node[nodei].cx=10000;
            graph.node[nodei].cy=20;
            graph.node[nodej].cx=10000;
            graph.node[nodej].cy=y+200;

        }
        public void sort(){//对各个主干按长度进行排序
            ArrayList<ArrayList<Integer>> temp=new ArrayList<ArrayList<Integer>>();
            temp=(ArrayList<ArrayList<Integer>>) sub.clone();
            int count=sub.size();
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
        public void bfs(int v,ArrayList<Integer> a){//广度优先搜索对非主干节点进行排布
            LinkedList<Integer> queue=new LinkedList<>();
            queue.push(v);
            while(!queue.isEmpty()){
                   int temp=queue.getFirst();
                   queue.poll();
                   int count=0;
                   for(int i=0;i<=graph.n-1;i++){
                       if(graph.matrix[temp][i]==1&&visit[i]!=1){
                           queue.push(i);
                           //配置坐标
                           graph.node[i].cx=dir[count%6].x+graph.node[temp].cx;
                           graph.node[i].cy=dir[count%6].y+graph.node[temp].cy;
                           visit[i]=1;
                           count++;
                   }
                   }
            }
        }
        public void Unside(){//遍历各个主干的节点，对非主干节点进行排布
            init();
            visit[nodei]=1;
            visit[nodej]=1;
            for(int i=0;i<=sub.size()-1;i++){
                ArrayList<Integer> temp=new ArrayList<>();
                temp=sub.get(i);
                for(int j=0;j<=temp.size()-1;j++){
                    visit[temp.get(j)]=1;
                }
            }
            for(int i=0;i<=sub.size()-1;i++){
                ArrayList<Integer> temp=new ArrayList<>();
                temp=sub.get(i);
                for(int j=0;j<=temp.size()-1;j++){
                    bfs(temp.get(j),temp);
                }
            }
        }
        public void summary(){//入口函数
            for(int i=0;i<=graph.n-1;i++){//不连通节点统一处理坐标为零
                graph.node[i].cx=0;
                graph.node[i].cy=0;
            }
            sub.clear();
            getMax();
            DFSS();
            sub.sort(c1);
            sort();
            process();
            System.out.print(nodei+"node"+nodej);
            Unside();
        }
}
