public class FDofNewDoubleSide {
    double MULTI=3;//ͼ�ľ������
    double kr=200000;//��������
    double ks=1.1;//��������
    double deltaT=0.02;//����

    Graph graph;//ԭͼ
    //int MAX_DISPLACEMENT_SQ=10;
    public FDofNewDoubleSide(Graph g){
        graph=g;
    }
    public void updateReplusion(){//���³���
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
    public void updateSpring(){//��������
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
    public void update()//���¾���
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
    public  void repeatCalc(int n)//�ظ�����
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
    public void diect(){//���ļ��������
        repeatCalc(2000);
    }
}
