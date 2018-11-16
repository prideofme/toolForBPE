package toolForBPE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * 分解type，剔除重复的type类型
 */
public class toolForBPE {
	private ArrayList<String> list = new ArrayList<>();
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
				
				getFeild(line);
				
			}else if(str.startsWith("<field")) {
				getFeild(line);
			}else if(str.contains("</type>")) {
				int length = list.size();
				String last = list.get(length-1).trim();
				if(last.startsWith("<field")) {
					string += line+"\n";
					list.add(str);
				}
			}
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("D:\\out.txt")));
		bw.write(string);
		br.close();
		bw.close();
		System.out.println("done");
	}
	
	public void getFeild(String line) {
		String str = line.trim();
		int idx_class= 0;
		int idx_code = 0;
		int len = 0;
		int idx_name = str.indexOf("name");
		if(str.startsWith("<type")) {
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
		if(string.contains(name) && !str.startsWith("<field")) {
			int string_idx_clas = 0;
			int begin = 0;
			int string_idx_code = 0;
			int string_idx_name = string.indexOf(name);
			if(str.startsWith("<type")) {
				string_idx_clas = string.indexOf("class",string_idx_name);				
				if(string.indexOf("code", string_idx_clas) > 0 && !str.contains("itemType")) {
					string_idx_code = string.indexOf("code", string_idx_clas);
				}else if(string.indexOf("itemType", string_idx_clas) > 0){
					string_idx_code = string.indexOf("itemType", string_idx_clas);
				}
				begin = 6;
			}else {
				string_idx_clas = string.indexOf("type",string_idx_name);				
				string_idx_code = string.indexOf("/>", string_idx_clas);
				begin = 5;
			}
			String string_clas = string.substring(string_idx_clas+begin, string_idx_code).trim();
			if(!clas.equalsIgnoreCase(string_clas)) {
				list.add(str);
				string += line+"\n";
			}
		}else if(string.contains(name) && str.startsWith("<field")) {
			int length = list.size();
			String last = list.get(length-1).trim();
			if(last.startsWith("<field") || last.contains("struct")) {
				string += line+"\n";
				list.add(str);
			}
		}else if(!string.contains(name)){
			list.add(str);
			string += line+"\n";
		}

	}
}
