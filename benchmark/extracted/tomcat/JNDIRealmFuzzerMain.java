import com.code_intelligence.jazzer.api.FuzzerSecurityIssueHigh;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.lang.model.util.SimpleAnnotationValueVisitor9;
import javax.naming.NamingException;
import org.apache.juli.logging.LogFactory;
import org.apache.catalina.realm.JNDIRealm;
import org.apache.catalina.realm.GenericPrincipal;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.AddRequest;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldif.LDIFReader;
import com.unboundid.ldif.LDIFException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JNDIRealmFuzzerMain {

    static InMemoryDirectoryServer ldapServer;

    static String username = "admin";

    static String credentials = "password";

    public static class JNDIRW extends JNDIRealm {

        JNDIConnection connection = null;

        ClassLoader ocl = null;

        public JNDIRW() {
            this.containerLog = LogFactory.getLog(JNDIRealmFuzzer.class);
            this.setConnectionURL("ldap://localhost:" + ldapServer.getListenPort());
            this.setUserPattern("cn={0},ou=people,dc=example,dc=com");
            this.setUserSearch(null);
            this.setUserBase(null);
            this.setRoleSearch("member=cn={1},ou=people,dc=example,dc=com");
            this.setRoleBase("ou=people,dc=example,dc=com");
            this.setUserRoleAttribute("cn");
            this.setRoleName("cn");
            this.setRoleNested(true);
            this.setConnectionPoolSize(1);
            try {
                connection = super.get();
            } catch (Exception e) {
                containerLog.error(sm.getString("jndiRealm.exception"), e);
                super.release(connection);
                if (containerLog.isDebugEnabled()) {
                    containerLog.debug("Returning null principal.");
                }
            } finally {
                if (!isUseContextClassLoader()) {
                    Thread.currentThread().setContextClassLoader(ocl);
                }
            }
        }

        void release() {
            super.release(connection);
        }
    }

    public static void fuzzerTearDown() {
        ldapServer.shutDown(true);
    }

    public static void FuzzOne(String SMFData) {
        username = data.consumeString(500);
        credentials = SMFData;
        if (username.isEmpty() || credentials.isEmpty() || (username.equals("admin") && credentials.equals("password"))) {
            return;
        }
        if (ldapServer instanceof InMemoryDirectoryServer) {
            ldapServer.shutDown(true);
        }
        try {
            createLDAP();
        } catch (LDAPException | LDIFException | UnknownHostException e) {
            e.printStackTrace();
        }
        JNDIRW realm = new JNDIRW();
        GenericPrincipal p = null;
        try {
            p = (GenericPrincipal) realm.authenticate(realm.connection, username, credentials);
        } catch (NullPointerException | NamingException e) {
        }
        if (p != null) {
            throw new FuzzerSecurityIssueHigh("Invalid user `" + username + "` could authenticate");
        }
        realm.release();
    }

    public static void createLDAP() throws LDIFException, LDAPException, UnknownHostException {
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=example,dc=com");
        InetAddress localhost = InetAddress.getByName("localhost");
        InMemoryListenerConfig listenerConfig = new InMemoryListenerConfig("localListener", localhost, 0, null, null, null);
        config.setListenerConfigs(listenerConfig);
        config.setEnforceSingleStructuralObjectClass(false);
        config.setEnforceAttributeSyntaxCompliance(true);
        ldapServer = new InMemoryDirectoryServer(config);
        ldapServer.startListening();
        LDAPConnection conn = ldapServer.getConnection();
        AddRequest addBase = new AddRequest("dn: dc=example,dc=com", "objectClass: top", "objectClass: domain", "dc: example");
        LDAPResult result = conn.processOperation(addBase);
        assert ResultCode.SUCCESS == result.getResultCode();
        AddRequest addPeople = new AddRequest("dn: ou=people,dc=example,dc=com", "objectClass: top", "objectClass: organizationalUnit");
        result = conn.processOperation(addPeople);
        assert ResultCode.SUCCESS == result.getResultCode();
        AddRequest addUserAdmin = new AddRequest("dn: cn=admin,ou=people,dc=example,dc=com", "objectClass: top", "objectClass: person", "objectClass: organizationalPerson", "cn: admin", "sn: Admin", "userPassword: password");
        result = conn.processOperation(addUserAdmin);
        assert ResultCode.SUCCESS == result.getResultCode();
    }

    public static void main(String[] args) {
        File folder = new File("./fuzzerOut");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String content = readFileAsString(file.getAbsolutePath());
                    FuzzOne(content);
                }
            }
        } else {
            System.out.println("The directory is empty or it does not exist.");
        }
    }

    private static String readFileAsString(String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return content;
        } catch (IOException e) {
            return "";
        }
    }
}
