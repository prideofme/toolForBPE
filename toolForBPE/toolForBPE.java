package toolForBPE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * 分解type，剔除重复的type类型
 */
public class toolForBPE {
	private String string = "";
	private Boolean flag = true;
	public static void main(String[] args) throws IOException {
		toolForBPE tfb = new toolForBPE();
		tfb.name();
	}
	public void name() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("D:\\in.txt")));
		String line;

		while((line = br.readLine()) != null) {
			String str = line.trim();
			if(str.startsWith("<type")) {				
				
				getFeild(line,true);
				
			}else if(str.startsWith("<field") || str.contains("</type>")) {
				getFeild(line,false);
			}
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("D:\\out.txt")));
		bw.write(string);
		br.close();
		bw.close();
		System.out.println("done");
	}
	
	public void getFeild(String line,Boolean flag) {
		String str = line.trim();
		int idx_class= 0;
		int idx_code = 0;
		int len = 0;
		int idx_name = str.indexOf("name");
		if(flag) {
			idx_class = str.indexOf("class");		
			if(str.indexOf("code") > 0) {
				idx_code = str.indexOf("code");
			}else {
				idx_code = str.indexOf("itemType");
			}
			len = 6;
		}else {
			idx_class = str.indexOf("type");
			idx_code = str.indexOf("/>");
			len = 5;
		}

		String name = str.substring(idx_name+5, idx_class).trim();
		String clas = str.substring(idx_class+len, idx_code).trim();
		if(string.contains(name)) {
			int string_idx_name = string
			int string_idx_clas = string.indexOf("class");
			int string_idx_code = 0;
			if(string.indexOf("code", string_idx_clas) > 0) {
				string_idx_code = string.indexOf("code", string_idx_clas);
			}else {
				string_idx_code = string.indexOf("itemType", string_idx_clas);
			}
			String string_clas = string.substring(string_idx_clas+6, string_idx_code).trim();
			if(!clas.equalsIgnoreCase(string_clas)) {
				string += line+"\n";
			}else if(clas.equalsIgnoreCase("struct")) {
				if(line.equals("</type>")) {
					string += ""+"\n";
				}
			}
		}else if(!string.contains(name)){
			string += line+"\n";
		}

	}
}
