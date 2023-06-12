import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;
import java.lang.IllegalArgumentException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConvertValueFuzzerMain {

    public static Class[] classes = { DummyClass.class, Integer.class, String.class, Byte.class, List.class, Map.class, TreeMap.class, BitSet.class, TimeZone.class, Date.class, Calendar.class, Locale.class };

    public static void FuzzOne(String SMFData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            int idx = data.consumeInt(0, classes.length - 1);
            mapper.convertValue(SMFData, classes[idx]);
        } catch (IllegalArgumentException e) {
        }
    }

    public static class DummyClass {

        public TreeMap<String, Integer> _treeMap;

        public List<String> _arrayList;

        public Set<String> _hashSet;

        public Map<String, Object> _hashMap;

        public List<Integer> _asList = Arrays.asList(1, 2, 3);

        public int[] _intArray;

        public long[] _longArray;

        public short[] _shortArray;

        public float[] _floatArray;

        public double[] _doubleArray;

        public byte[] _byteArray;

        public char[] _charArray;

        public String[] _stringArray;

        public BitSet _bitSet;

        public Date _date;

        public TimeZone _timeZone;

        public Calendar _calendar;

        public Locale _locale;

        public Integer[] _integerArray;

        public boolean _boolean;

        public char _char;

        public byte _byte;

        public short _short;

        public int _int;

        public float _float;
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
