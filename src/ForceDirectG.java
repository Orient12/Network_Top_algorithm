public class ForceDirectG {
    private static int MaxNodeCount=Graph.MaxNodeCount;
     double MULTI=2;//图的距离参数
     double kr=2000000;//社团外部斥力常数
     double ks=1;//社团外部部拉力常数
     double kr1=1500000;//社团内部斥力常数
     double ks1=1.5;//社团内部拉力常数
     double deltaT=0.006;//步长
    //因为EF图中心化比较明显，所以找出两个度比较高的点，将他们标记成不属于任何社团
     int nodem1;
     int nodem2;
     Graph graph;//原图
     int degree[]=new int[MaxNodeCount];//保存节点度的数组。
    //int MAX_DISPLACEMENT_SQ=10;
    public ForceDirectG(Graph g){
       graph=g;
    }
     public void updateReplusion(){//更新拉力
         double dx=0,dy=0,f,fx,fy,d,dsq;
         for(int i=0;i<graph.n-1;i++){
            // System.out.println(graph.node[i].x+" ojkjkjkjkjk "+graph.node[i].y);
             for(int j=i+1;j<=graph.n-1;j++) {
                 dx=(graph.node[i].cx-graph.node[j].cx)/MULTI;
                 dy=(graph.node[i].cy-graph.node[j].cy)/MULTI;
                 dsq=dx*dx+dy*dy;
                 d=Math.sqrt(dsq);
                 if(i==0&&j==1) System.out.println("d+ "+d+" dsp:"+dsq);
                 if(graph.node[i].tag!=graph.node[j].tag) f=kr*degree[i]*degree[j]/10/dsq;
                 else f=kr1*degree[i]*degree[j]/10/dsq;
                 fx=f*dx/d;
                 fy=f*dy/d;
                 graph.node[i].force_x+=fx;
                 graph.node[i].force_y+=fy;
                 graph.node[j].force_x-=fx;
                 graph.node[j].force_y-=fy;
             }
         }
     }
     public void updateSpring(){//更新斥力
         double dx,dy,f,fx,fy,d,dsq;
             for(int i=0;i<graph.n-1;i++) {
                 for (int j = i+1; j <= graph.n - 1; j++) {
                     if(graph.matrix[i][j]==1) {
                         dx = (graph.node[i].cx - graph.node[j].cx)/MULTI;
                         dy = (graph.node[i].cy - graph.node[j].cy)/MULTI;
                         d = Math.sqrt(dx * dx + dy * dy);
                         if(graph.node[i].tag!=graph.node[j].tag) f = ks * d;
                         else f=ks1*d;
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
   public  void repeatCalc(int n)//重复迭代
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
        nodem1=node1;nodem2=node2;
    }
    public void diect(){//本程序入口
        //保存节点的度数组
        for(int i=0;i<=graph.n-1;i++){
            degree[i]=graph.GetDegree(i);
        }
        getMax();
        //标记两个中心点，使其不属于任何社团。
        graph.node[nodem1].tag=1000;
        graph.node[nodem2].tag=9999;
        repeatCalc(2000);
    }
}
