import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeEditor;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSourceEditor;

public class TransactionAttributeSourceEditorFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String source = SMFData;
        TransactionAttributeSourceEditor editor = new TransactionAttributeSourceEditor();
        try {
            editor.setAsText(source);
        } catch (IllegalArgumentException e) {
        }
        TransactionAttributeSource tas = (TransactionAttributeSource) editor.getValue();
        if (tas == null) {
            return;
        }
        TransactionAttribute ta = null;
        try {
            ta = tas.getTransactionAttribute(Object.class.getMethod("dummyMethod"), null);
        } catch (NoSuchMethodException e) {
        }
        if (ta == null) {
            return;
        }
        ta.getPropagationBehavior();
        ta.rollbackOn(new RuntimeException());
    }
}
