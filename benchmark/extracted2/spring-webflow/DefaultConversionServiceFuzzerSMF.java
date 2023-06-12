import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import java.util.*;
import java.security.Principal;
import org.springframework.binding.convert.service.*;
import org.springframework.binding.convert.ConversionException;
import org.springframework.binding.convert.ConversionExecutionException;
import org.springframework.binding.convert.ConversionExecutor;
import org.springframework.binding.convert.ConversionExecutorNotFoundException;
import org.springframework.binding.convert.converters.Converter;
import org.springframework.binding.convert.converters.FormattedStringToNumber;
import org.springframework.binding.convert.converters.StringToBoolean;
import org.springframework.binding.convert.converters.StringToObject;
import org.springframework.binding.convert.converters.TwoWayConverter;
import org.springframework.binding.format.DefaultNumberFormatFactory;

public class DefaultConversionServiceFuzzerSMF {

    static Class[] classes = { String.class, String[].class, Boolean.class, int.class, int[].class, Integer.class, Integer[].class, Collection.class, Set.class, LinkedList.class, List.class, HashMap.class, Principal.class, Principal[].class };

    public static void FuzzOne(String SMFData) {
        Class c1 = data.pickValue(classes);
        Class c2 = data.pickValue(classes);
        try {
            DefaultConversionService service = new DefaultConversionService();
            ConversionExecutor executor = service.getConversionExecutor(c1, c2);
            if (c1 == String.class) {
                executor.execute(SMFData);
            } else if (c1 == String[].class) {
                String[] stringArr = new String[data.consumeInt(0, 50)];
                for (int i = 0; i < stringArr.length; i++) {
                    stringArr[i] = data.consumeString(1000);
                }
                executor.execute(stringArr);
            } else if (c1 == Boolean.class) {
                executor.execute(data.consumeBoolean());
            } else if (c1 == int.class) {
                executor.execute(data.consumeInt());
            } else if (c1 == int[].class) {
                int[] intArr = new int[data.consumeInt(0, 50)];
                for (int i = 0; i < intArr.length; i++) {
                    intArr[i] = data.consumeInt();
                }
                executor.execute(intArr);
            } else if (c1 == Integer.class) {
                executor.execute(data.consumeInt());
            } else if (c1 == Integer[].class) {
                Integer[] integerArr = new Integer[data.consumeInt(0, 50)];
                for (int i = 0; i < integerArr.length; i++) {
                    integerArr[i] = data.consumeInt();
                }
                executor.execute(integerArr);
            } else if (c1 == Collection.class) {
                int sz = data.consumeInt(0, 50);
                Collection<String> collection = new ArrayList<String>();
                for (int i = 0; i < sz; i++) {
                    collection.add(data.consumeString(1000));
                }
                executor.execute(collection);
            } else if (c1 == Set.class) {
                int sz = data.consumeInt(0, 50);
                Set<String> set = new LinkedHashSet<>();
                for (int i = 0; i < sz; ++i) {
                    set.add(data.consumeString(1000));
                }
                executor.execute(set);
            } else if (c1 == LinkedList.class) {
                int sz = data.consumeInt(0, 50);
                LinkedList<String> result = new LinkedList<String>();
                for (int i = 0; i < sz; ++i) {
                    result.add(data.consumeString(1000));
                }
                executor.execute(result);
            } else if (c1 == List.class) {
                int sz = data.consumeInt(0, 50);
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < sz; ++i) {
                    list.add(data.consumeString(1000));
                }
                executor.execute(list);
            } else if (c1 == HashMap.class) {
                int sz = data.consumeInt(0, 50);
                HashMap<Integer, String> hash_map = new HashMap<Integer, String>();
                for (int i = 0; i < sz; ++i) {
                    hash_map.put(data.consumeInt(), data.consumeString(1000));
                }
                executor.execute(hash_map);
            } else if (c1 == Principal.class) {
                Principal principal = () -> data.consumeString(1000);
                executor.execute(principal);
            } else if (c1 == Principal[].class) {
                Principal[] principalArr = new Principal[data.consumeInt(0, 50)];
                for (int i = 0; i < principalArr.length; i++) {
                    Principal principal = () -> data.consumeString(1000);
                    principalArr[i] = principal;
                }
                executor.execute(principalArr);
            } else {
                return;
            }
        } catch (ConversionExecutionException | ConversionExecutorNotFoundException e) {
        }
    }
}
