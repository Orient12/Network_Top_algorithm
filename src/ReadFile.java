import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadFile {
       
	public BufferedReader ReadNode() {
    	   File csv=new File("C:\\Users\\zhp13\\Desktop\\Work\\��Ϊ���˲�������\\HW_GG\\node.csv");
    	  BufferedReader br=null; 
    	 try {
		    br=new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
    	   return br;
    }
	public BufferedReader ReadEdge() {
 	  File csv=new File("C:\\Users\\zhp13\\Desktop\\Work\\��Ϊ���˲�������\\HW_GG\\edge.csv");
 	  BufferedReader br=null; 
 	 try {
		    br=new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
 	   return br;
	}
	public BufferedReader ReadType() {
		File csv=new File("C:\\Users\\zhp13\\Desktop\\Work\\��Ϊ���˲�������\\HW_GI\\type.csv");
		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return br;
	}
}
