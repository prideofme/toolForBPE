import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class scanDirectory {
    private static FileText fileText;
/*
    public static void main(String[] args) throws IOException {
        String path = "D:\\scanner";
        String input = "test.scalabpe4ACProxy,UdbRegister.updateUserInfo,multiAccountUserPhone.updatephone,REGISTERSERVER.syncMobileToSdp";
        String input_path = "D:\\scanner\\in.txt";
        scannAndWrite(path,input,input_path);
    }
    */
    public static void scannAndWrite(String path,String inputString,String input_path) throws IOException {
        HashMap<String, String> input = new HashMap<>();
        String[] string = inputString.split(",");
        for (int i = 0; i < string.length; i++) {
            String str = string[i];
            int index = str.indexOf(".");
            String key = str.substring(0,index);
            String value = str.substring(index+1);
            input.put(key,value);
        }

        File file = new File(path);
        fileText = new FileText();
        fileText.func(file);
        HashMap<String, HashMap<String, ArrayList<String>>> hm_service_msg= fileText.getHm_service_msg();
        HashMap<String, ArrayList<String>> hm_service_type = fileText.getHm_service_type();

//        System.out.println(hm_service_type);
//        System.out.println(hm_service_msg);

        HashMap<String, ArrayList<String>> msgName_msgTypes;
        ArrayList<String> list_msgType;
        ArrayList<String> list_serviceType;
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(input_path),true));
        for(Map.Entry<String,String> entry : input.entrySet()){
            String service_name = entry.getKey();
            String msg_name = entry.getValue();
            if(service_name.equalsIgnoreCase("test") && hm_service_type.get(msg_name) != null){
                list_serviceType = hm_service_type.get(msg_name);
                for (int z = 0; z < list_serviceType.size(); z++) {
                    bw.write(list_serviceType.get(z));
                }
            }else{
                if(hm_service_msg.get(service_name) != null && hm_service_type.get(service_name) != null){
                    list_serviceType = hm_service_type.get(service_name);
                    msgName_msgTypes = hm_service_msg.get(service_name);
                    if(msgName_msgTypes.containsKey(msg_name)){
                        list_msgType = msgName_msgTypes.get(msg_name);
                        for (int i = 0; i < list_msgType.size(); i++) {
                            String msgType = list_msgType.get(i);
                            for (int j = 0; j < list_serviceType.size(); j++) {
                                String serviceType = list_serviceType.get(j);
                                if(serviceType.contains(msgType)){
                                    bw.write(serviceType);
                                    if(serviceType.contains("itemType")){
                                        int index_itemType = serviceType.indexOf("itemType");
                                        int index_end = serviceType.indexOf("/>");
                                        String itemType = serviceType.substring(index_itemType + 9,index_end).trim().replaceAll("\"","");
                                        if(list_serviceType.get(j-1).contains(itemType))
                                            bw.write(list_serviceType.get(j-1));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        bw.close();
    }
}
