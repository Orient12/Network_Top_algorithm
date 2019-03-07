import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class ToJsonWeb {
    Graph graph=new Graph();

    String jss,jss1;
    public ToJsonWeb(Graph g){
        graph=g;
    }
    public String saveNode() {

        ArrayList<String> str=new ArrayList<String>();
        ArrayList<String> str1=new ArrayList<String>();
        for(int i=0;i<=graph.n-1;i++){
            String ss="{\"cx\":"+graph.node[i].cx+",\"cy\":"+graph.node[i].cy+",\"tag\":"+graph.node[i].tag+"}";

            str.add(ss);
            for(int j=i+1;j<=graph.n-1;j++){
                if(graph.matrix[i][j]!=0) {
                   String ss1="{\"source\":"+i+",\"target\":"+j+"}";
                   str1.add(ss1);
                }

            }
        }
        JSONObject js=new JSONObject();
        JSONObject js1=new JSONObject();
        js.put("node",str);
        js1.put("node",str1);
        String temp=js.toString();
        String temp1=js1.toString();
        temp=temp.substring(8,temp.length()-1);
        temp1=temp1.substring(8,temp1.length()-1);
        System.out.println(temp);
        System.out.println(temp1);
        jss=temp;
        jss1=temp1;
        return jss;
    }
}
