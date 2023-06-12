import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.user.UserManager;
import com.icegreen.greenmail.user.UserException;

public class UserManagerFuzzerSMF {

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
}
