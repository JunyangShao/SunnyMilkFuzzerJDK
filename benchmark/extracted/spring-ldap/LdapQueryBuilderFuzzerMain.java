import org.springframework.ldap.InvalidNameException;
import static org.springframework.ldap.query.LdapQueryBuilder.query;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LdapQueryBuilderFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try {
            LdapQuery query = query().base(data.consumeString(100)).searchScope(SearchScope.ONELEVEL).timeLimit(30).countLimit(60).where(data.consumeString(100)).is(data.consumeString(100)).and(data.consumeString(100)).is(SMFData);
        } catch (InvalidNameException e) {
        }
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
