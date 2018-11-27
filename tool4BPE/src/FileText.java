import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author wangke05 create on 2018/09/25
 * 功能：实现从工程文件中遍历所有的符合正则表达式的所有不重复的字符串，并且存放在HashSet中
 */
public class FileText {
	//public static HashSet<String> hashSet = new HashSet<>();
	private HashMap<String, HashMap<String, ArrayList<String>>> hm_service_msg = new HashMap<>();
	private HashMap<String, ArrayList<String>> hm_service_type = new HashMap<>();
	/*private static HashMap<String, ArrayList<String>> hm_msg = new HashMap<>();
	private static ArrayList<String> list_type = new ArrayList<>();
	private static ArrayList<String> list_msg_type = new ArrayList<>();*/
/*
	public static void main(String[] args) throws IOException {

		String path = "D:\\scanner";
		File file = new File(path);
		FileText ft = new FileText();
		ft.func(file);
	}
*/

	public HashMap<String, HashMap<String, ArrayList<String>>> getHm_service_msg() {
		return hm_service_msg;
	}

	public HashMap<String, ArrayList<String>> getHm_service_type() {
		return hm_service_type;
	}

	public void func(File file) throws IOException {
		File[] fs = file.listFiles();
		//int count = fs.length;
		for (File f : fs) {
			HashMap<String, ArrayList<String>> hm_msg = new HashMap<>();
			ArrayList<String> list_type = new ArrayList<>();

			ArrayList<String> list_msg_type = null;

			if (f.isDirectory())    //若是目录，则递归打印该目录下的文件
				func(f);
			if (f.isFile()) {
				String fname = f.getName();
				String fpath = f.getAbsolutePath();
				if (fname.endsWith(".xml")) {
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fpath)));//若是文件，直接打印
					String line = "";
					String type_string = "";
					String service_name = "";
					String msg_name = "";
					Boolean flag_type = true;
					while ((line = br.readLine()) != null) {
						String line_new = line.trim();

						if (line_new.startsWith("<service")) {
							int indx_service_name = line_new.indexOf("name");
							int indx_service_id = line_new.indexOf("id");
							service_name = line_new.substring(indx_service_name + 5, indx_service_id).trim().replaceAll("\"","");
						}
						if (line_new.startsWith("<type") && flag_type) {
							if (line_new.endsWith("/>")) {
								type_string += line + "\n";
								list_type.add(type_string);
							} else if (line_new.endsWith(">")) {
								type_string += line + "\n";
								continue;
							}
						} else if (line_new.startsWith("<field") && flag_type) {
							type_string += line + "\n";
							continue;
						} else if (line_new.startsWith("</type>") && flag_type) {
							type_string += line + "\n";
							list_type.add(type_string);
						}
						type_string = "";

						if (line_new.startsWith("<message")) {
							list_msg_type = new ArrayList<>();
							int indx_msg_name = line_new.indexOf("name");
							int indx_msg_id = line_new.indexOf("id");
							msg_name = line_new.substring(indx_msg_name + 5, indx_msg_id).trim().replaceAll("\"","");
							flag_type = false;
						}
						if (line_new.startsWith("<field") && !flag_type) {
							//int indx_msg_req_name = line_new.indexOf("name");
							int indx_msg_req_type = line_new.indexOf("type");
							//String msg_req_name = line_new.substring(indx_msg_req_name + 5, indx_msg_req_type);
							String msg_req_type = null;
							int indx_msg_req_end = 0;
							if (line_new.indexOf("required") > 0) {
								indx_msg_req_end = line_new.indexOf("required");
								msg_req_type = line_new.substring(indx_msg_req_type + 5, indx_msg_req_end).trim().replaceAll("\"","");
							} else {
								indx_msg_req_end = line_new.indexOf("/>");
								msg_req_type = line_new.substring(indx_msg_req_type + 5, indx_msg_req_end).trim().replaceAll("\"","");
							}
							list_msg_type.add(msg_req_type);
						}

						if(line_new.startsWith("</message>") && !flag_type){
							hm_msg.put(msg_name, list_msg_type);
							flag_type = true;
							//list_msg_type.clear();
						}

					}
					//hm_msg.put(msg_name, list_msg_type);
					hm_service_type.put(service_name, list_type);
					hm_service_msg.put(service_name, hm_msg);

					br.close();
				}
			}
		}
/*
		for (Map.Entry entry : hm_service_type.entrySet()){
			String key = (String) entry.getKey();
			System.out.println(key);
		}*/
	}
}
