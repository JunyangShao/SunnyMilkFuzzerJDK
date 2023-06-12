import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.InvalidPropertyException;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BeanWrapperFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String property = data.consumeString(100);
        Bean bean = new Bean();
        BeanWrapper bw = new BeanWrapperImpl(bean);
        try {
            bw.setPropertyValue(property, SMFData);
            bw.getPropertyType(property);
        } catch (ConversionNotSupportedException | InvalidPropertyException ignored) {
        }
    }

    public static class Bean {

        private String prop;

        private Bean nested;

        private Bean[] array;

        private Bean[][] multiArray;

        private Bean[][][] threeDimensionalArray;

        private List<Bean> list;

        private List<List<Bean>> multiList;

        private List listNotParameterized;

        private Map<String, Bean> map;

        public String getProp() {
            return prop;
        }

        public void setProp(String prop) {
            this.prop = prop;
        }

        public Bean getNested() {
            return nested;
        }

        public void setNested(Bean nested) {
            this.nested = nested;
        }

        public Bean[] getArray() {
            return array;
        }

        public void setArray(Bean[] array) {
            this.array = array;
        }

        public Bean[][] getMultiArray() {
            return multiArray;
        }

        public void setMultiArray(Bean[][] multiArray) {
            this.multiArray = multiArray;
        }

        public Bean[][][] getThreeDimensionalArray() {
            return threeDimensionalArray;
        }

        public void setThreeDimensionalArray(Bean[][][] threeDimensionalArray) {
            this.threeDimensionalArray = threeDimensionalArray;
        }

        public List<Bean> getList() {
            return list;
        }

        public void setList(List<Bean> list) {
            this.list = list;
        }

        public List<List<Bean>> getMultiList() {
            return multiList;
        }

        public void setMultiList(List<List<Bean>> multiList) {
            this.multiList = multiList;
        }

        public List getListNotParameterized() {
            return listNotParameterized;
        }

        public void setListNotParameterized(List listNotParameterized) {
            this.listNotParameterized = listNotParameterized;
        }

        public Map<String, Bean> getMap() {
            return map;
        }

        public void setMap(Map<String, Bean> map) {
            this.map = map;
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
