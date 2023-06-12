import org.osgi.framework.Version;

public class CoreVersionFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        Version v = null;
        try {
            v = new Version(SMFData);
        } catch (IllegalArgumentException ex) {
            return;
        }
        v.getMajor();
        v.getMinor();
        v.getMicro();
        v.getQualifier();
        v.toString();
    }
}
