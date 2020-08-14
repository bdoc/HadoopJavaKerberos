import org.junit.Test;

public class DemoTest {

    @Test
    public void main() {
        // without krb5.conf
        System.setProperty("java.security.krb5.realm", "T2.IGETKDC.COM");
        System.setProperty("java.security.krb5.kdc", "node06.test");

        String keyUser = "dwadmin";
        String keyPath = System.getenv("HOME").concat("/Documents/krb5/dwadmin.keytab-t2");
        String filePath = "/tmp";
        String defaultFs = "hdfs://hdfs-ha";

        String[] args = new String[4];
        args[0] = keyUser;
        args[1] = keyPath;
        args[2] = filePath;
        args[3] = defaultFs;
        Demo.main(args);
    }
}