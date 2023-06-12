import org.springframework.ldap.InvalidNameException;
import static org.springframework.ldap.query.LdapQueryBuilder.query;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;

public class LdapQueryBuilderFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        try {
            LdapQuery query = query().base(data.consumeString(100)).searchScope(SearchScope.ONELEVEL).timeLimit(30).countLimit(60).where(data.consumeString(100)).is(data.consumeString(100)).and(data.consumeString(100)).is(SMFData);
        } catch (InvalidNameException e) {
        }
    }
}
