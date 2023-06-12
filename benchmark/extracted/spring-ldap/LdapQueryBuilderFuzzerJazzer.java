import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.springframework.ldap.InvalidNameException;
import static org.springframework.ldap.query.LdapQueryBuilder.query;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;

public class LdapQueryBuilderFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try {
            LdapQuery query = query().base(data.consumeString(100)).searchScope(SearchScope.ONELEVEL).timeLimit(30).countLimit(60).where(data.consumeString(100)).is(data.consumeString(100)).and(data.consumeString(100)).is(data.consumeRemainingAsString());
        } catch (InvalidNameException e) {
        }
    }
}
