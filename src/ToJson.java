import net.sf.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ToJson {
    Graph graph=new Graph();
    GN gn=new GN();
    String jss;
    public ToJson(Graph g,GN gn1){
        graph=g;
        gn=gn1;
    }
    public void saveNode(){
        JSONObject js=new JSONObject();
        FileWriter fw=null;

            try {
                fw=new FileWriter("C:\\Users\\zhp13\\Desktop\\node.json");
                ArrayList<String> ss=new ArrayList<String>();
                ArrayList<String> ss1=new ArrayList<String>();
                for(int i=0;i<=graph.n-1;i++) {
                    String temp = "";
                    temp = "{" + "\"id\": " + "\"" + i + "\"," + "\"group\": " + graph.node[i].tag + "}";
                    ss.add(temp);
                    System.out.println(temp);
                }
                for(int i=0;i<graph.n-1;i++){
                    for(int j=i+1;j<graph.n;j++){
                        if(graph.matrix[i][j]==1){
                         String temp1="";
                         temp1="{"+"\""+"source"+"\""+": "+"\""+i+"\", "+"\"target\": \""+j+"\", \"value\":1}";
                            ss1.add(temp1);
                            System.out.println(temp1);
                        }
                    }
                }
                js.put("nodes",ss);
                js.put("links",ss1);
               jss=js.toString();
               fw.write(js.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
            try {
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
