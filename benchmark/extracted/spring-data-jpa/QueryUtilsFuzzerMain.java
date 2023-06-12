import java.util.List;
import java.util.Vector;
import java.lang.IllegalArgumentException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.query.QueryUtils;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QueryUtilsFuzzerMain {

    public static Sort.Direction[] da = { Sort.Direction.ASC, Sort.Direction.DESC };

    public static Sort.NullHandling[] na = { Sort.NullHandling.NATIVE, Sort.NullHandling.NULLS_FIRST, Sort.NullHandling.NULLS_LAST };

    public static void FuzzOne(String SMFData) {
        int num = data.consumeInt(0, 200);
        String str = null;
        String str1 = null;
        String str2 = null;
        try {
            List<Order> orders = new Vector<Order>();
            for (int i = 0; i < num; ++i) {
                str = data.consumeString(1000);
                Order o = new Order(data.pickValue(da), str, data.pickValue(na));
                orders.add(o);
            }
            Sort sort = Sort.by(orders);
            str1 = data.consumeString(1000);
            str2 = SMFData;
            QueryUtils.applySorting(str1, sort, str2);
        } catch (IllegalArgumentException | org.springframework.dao.InvalidDataAccessApiUsageException e) {
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
