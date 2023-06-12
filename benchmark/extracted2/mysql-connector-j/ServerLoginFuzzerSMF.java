import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerLoginFuzzerSMF extends TestServer {

    ServerLoginFuzzer() {
    }

    public static void FuzzOne(String SMFData) throws Exception {
        ServerLoginFuzzer closure = new ServerLoginFuzzer();
        try {
            closure.getConnection(SMFData);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
