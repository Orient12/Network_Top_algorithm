public class FDofNewDoubleSide {
    double MULTI=3;//图的距离参数
    double kr=200000;//斥力常数
    double ks=1.1;//拉力常数
    double deltaT=0.02;//步长

    Graph graph;//原图
    //int MAX_DISPLACEMENT_SQ=10;
    public FDofNewDoubleSide(Graph g){
        graph=g;
    }
    public void updateReplusion(){//更新斥力
        double dx=0,dy=0,f,fx,fy,d,dsq;
        for(int i=0;i<graph.n-1;i++){
            // System.out.println(graph.node[i].x+" ojkjkjkjkjk "+graph.node[i].y);
            for(int j=i+1;j<=graph.n-1;j++) {
                if(graph.node[j].tag==graph.node[i].tag) {
                    dx = (graph.node[i].cx - graph.node[j].cx) / MULTI;
                    dy = (graph.node[i].cy - graph.node[j].cy) / MULTI;
                    dsq = dx * dx + dy * dy;
                    d = Math.sqrt(dsq);
                    //if (i == 0 && j == 1) System.out.println("d+ " + d + " dsp:" + dsq);
                    f = kr / dsq;
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
                if(graph.matrix[i][j]==1) {
                    if(graph.node[j].tag==graph.node[i].tag) {
                        dx = (graph.node[i].cx - graph.node[j].cx) / MULTI;
                        dy = (graph.node[i].cy - graph.node[j].cy) / MULTI;
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
    }
    public void update()//更新距离
    {
        double dx,dy,dsq,s;
        for(int i=0;i<graph.n;i++)
        {

            dx=deltaT*graph.node[i].force_x;
            dy=deltaT*graph.node[i].force_y;
            //if(i==0) System.out.println(graph.node[i].force_x);

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
    {   double shadow_variable=deltaT;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<graph.n;j++){
                graph.node[j].force_x=0.0;
                graph.node[j].force_y=0.0;
            }
            //deltaT=deltaT;
            updateReplusion();
            updateSpring();
            update();
        }
    }
    public void diect(){//本文件程序入口
        repeatCalc(2000);
    }
}
