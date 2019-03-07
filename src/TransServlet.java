import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import net.sf.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

public class TransServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    }
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
        response.setContentType("text/html;charset=GBK");
        request.setCharacterEncoding("UTF-8");
        CreateGraphDS createGraph=new CreateGraphDS();
        Graph graph=createGraph.getGraph();
        graph.PreProcess();
        long start,end,time;
        createGraph.prt();
            int a = 3;
            switch (a) {
                case 0:
                    start=System.currentTimeMillis();
                    DoubleSide ds = new DoubleSide(graph);
                    ds.summary();
                    end=System.currentTimeMillis();time=end-start;
                    System.out.println("\n用时："+time+"ms");
                    break;
                case 1:
                    start=System.currentTimeMillis();
                    ForceDirect fd = new ForceDirect(graph);
                    fd.diect();
                    end=System.currentTimeMillis();time=end-start;
                    System.out.println("\n用时："+time+"ms");
                    break;
                case 2:
                    start=System.currentTimeMillis();
//                GN gn=new GN(graph);
//                gn.G(graph);
//                gn.PreProcessForce();
                    FastGN fgn = new FastGN(graph);
                    fgn.Fast();
                    ForceDirectG fd1 = new ForceDirectG(graph);
                    fd1.diect();
                    end=System.currentTimeMillis();time=end-start;
                    System.out.println("\n用时："+time+"ms");
                    break;
                case 3:
                    start=System.currentTimeMillis();
                    SimpleForceDirect sfd = new SimpleForceDirect(graph);
                    sfd.EliLeaf();
                    sfd.diect();
                    end=System.currentTimeMillis();time=end-start;
                    System.out.println("\n用时："+time+"ms");
                    break;
                case 4:
                    start=System.currentTimeMillis();
                    NewDoubleSide nds = new NewDoubleSide(graph);
                    nds.start();
                    end=System.currentTimeMillis();time=end-start;
                    System.out.println("\n用时："+time+"ms");
                    break;
            }

        ToJsonWeb tj=new ToJsonWeb(graph);
        tj.saveNode();
        request.setAttribute("circle", tj.jss);
        request.setAttribute("line", tj.jss1);
        System.out.println("_____________________________________");
        RequestDispatcher rd=null;
        rd=request.getRequestDispatcher("/1.jsp");
        rd.forward(request,response);
 }
}
