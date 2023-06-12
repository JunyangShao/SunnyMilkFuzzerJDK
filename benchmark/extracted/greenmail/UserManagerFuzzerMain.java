import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.user.UserManager;
import com.icegreen.greenmail.user.UserException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserManagerFuzzerMain {

    public static void FuzzOne(String SMFData) {
        GreenMail greenMail = new GreenMail(ServerSetup.ALL);
        String email = data.consumeString(240);
        String login = data.consumeString(240);
        String pwd = SMFData;
        try {
            UserManager userManger = greenMail.getUserManager();
            userManger.createUser(email, login, pwd);
            GreenMailUser greenMailUser = userManger.getUser(login);
            if (!greenMailUser.getLogin().equals(login)) {
                throw new FuzzerSecurityIssueMedium("User is not created");
            }
            if (!userManger.test(login, pwd)) {
                throw new FuzzerSecurityIssueMedium("Loggin is not possible!");
            }
            userManger.deleteUser(greenMailUser);
        } catch (UserException e) {
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
