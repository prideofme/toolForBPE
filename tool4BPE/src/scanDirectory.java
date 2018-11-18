import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class scanDirectory {
    public static void main(String[] args) throws IOException {
        String path = "D:\\scanner";
        File file = new File(path);
        FileText ft = new FileText();
        ft.func(file);
        HashMap<String, HashMap<String, ArrayList<String>>> hm_service_msg = ft.getHm_service_msg();
        HashMap<String, ArrayList<String>> hm_service_type = ft.getHm_service_type();


    }
}
