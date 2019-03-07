public class FDofDS {
    private static int MaxNodeCount=Graph.MaxNodeCount;
        double MULTI=4;//图的距离参数
        double kr=200000;//斥力常数
        double kr1=300000;//球范围内斥力常数
        double ks=1.2;//拉力常数
        double deltaT=0.003;//步长
        Graph graph;//原图
    int degree[]=new int[MaxNodeCount];//保存节点度的数组。
        //int MAX_DISPLACEMENT_SQ=10;
        public FDofDS(Graph g){
            graph=g;
            for(int i=0;i<=graph.n-1;i++){
                degree[i]=graph.GetDegree(i);
            }
        }
        public void updateReplusion(){//更新斥力
            double dx=0,dy=0,f,fx,fy,d,dsq;
            for(int i=0;i<graph.n-1;i++){
                // System.out.println(graph.node[i].x+" ojkjkjkjkjk "+graph.node[i].y);
                for(int j=i+1;j<=graph.n-1;j++) {
                    if(degree[i]>2&&degree[j]>2&&graph.node[j].tag==graph.node[i].tag) {
                        dx = (graph.node[i].cx - graph.node[j].cx) / MULTI;
                        dy = (graph.node[i].cy - graph.node[j].cy) / MULTI;
                        dsq = dx * dx + dy * dy;
                        d = Math.sqrt(dsq);

                        if (i == 0 && j == 1) System.out.println("d+ " + d + " dsp:" + dsq);
                        if(d<500) f = kr1*(degree[i]*degree[j]/3) / dsq;
                        else f = kr*(degree[i]*degree[j]/3) / dsq;
                        fx = f * dx / d;
                        fy = f * dy / d;
                        graph.node[i].force_x += fx;
                        graph.node[i].force_y += fy;
                        graph.node[j].force_x -= fx;
                        graph.node[j].force_y -= fy;
                    }
                }
            }
        }
        public void updateSpring(){//更新拉力
            double dx,dy,f,fx,fy,d,dsq;
            for(int i=0;i<graph.n-1;i++) {
                for (int j = i+1; j <= graph.n - 1; j++) {
                    if(degree[i]>2&&degree[j]>2&&graph.node[i].tag==graph.node[j].tag) {
                    if(graph.matrix[i][j]==1) {
                        dx = (graph.node[i].cx - graph.node[j].cx) / MULTI;
                        dy = (graph.node[i].cy - graph.node[j].cy) / MULTI;
                        d = Math.sqrt(dx * dx + dy * dy);
                        f = ks*(20.0/degree[i])*(20.0/degree[j])*d;
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
        }
        public void update()//更新距离
        {
            double dx,dy,dsq,s;
            for(int i=0;i<graph.n;i++)
            {
                if(degree[i]>2) {
                    dx = deltaT * graph.node[i].force_x;
                    dy = deltaT * graph.node[i].force_y;
                    // dsq=dx*dx+dy*dy;
//            if(dsq>MAX_DISPLACEMENT_SQ)
//            {
//                s=sqrt(MAX_DISPLACEMENT_SQ/dsq);
//                dx*=s;
//                dy*=s;
//            }
                    graph.node[i].cx += dx * MULTI;
                    graph.node[i].cy += dy * MULTI;
                }
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
        public void diect(){//本文件程序入口
            repeatCalc(10000);
        }

}
