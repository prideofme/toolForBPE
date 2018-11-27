import java.io.IOException;

public class parametersInput {
    public static void main(String[] args) throws IOException {
        String scan_path = "D:\\scanner";
        String input = "test.scalabpe4ACProxy,UdbRegister.updateUserInfo,multiAccountUserPhone.updatephone,REGISTERSERVER.syncMobileToSdp";
        String input_path = "D:\\scanner\\in.txt";
        scanDirectory.scannAndWrite(scan_path, input, input_path);
        toolForBPE tfb = new toolForBPE();
        tfb.name(input_path);
        System.out.println("done");
    }
}
