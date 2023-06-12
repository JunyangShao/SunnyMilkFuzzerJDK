package ossfuzz;

import com.code_intelligence.jazzer.api.*;
import org.glassfish.jaxb.runtime.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataTypeConverterFuzzerMain {

    String m_string;

    int m_int;

    DataTypeConverterFuzzer(int integer, String string) {
        m_int = integer;
        m_string = string;
    }

    void test() {
        DatatypeConverterImpl.theInstance.printHexBinary(m_string.getBytes());
        Calendar calendar = null;
        try {
            calendar = DatatypeConverterImpl.theInstance.parseTime(m_string);
            DatatypeConverterImpl.theInstance.printTime(calendar);
        } catch (IllegalArgumentException e) {
        }
        try {
            String base64 = DatatypeConverterImpl.theInstance.printBase64Binary(m_string.getBytes());
            DatatypeConverterImpl.theInstance.parseBase64Binary(base64);
        } catch (IllegalArgumentException e) {
        }
        try {
            String type = DatatypeConverterImpl.theInstance.parseAnySimpleType(m_string);
            DatatypeConverterImpl.theInstance.printAnySimpleType(type);
        } catch (IllegalArgumentException e) {
        }
        try {
            boolean bool = DatatypeConverterImpl.theInstance.parseBoolean(m_string);
            DatatypeConverterImpl.theInstance.printBoolean(bool);
        } catch (IllegalArgumentException e) {
        }
        try {
            BigDecimal bigDecimal = DatatypeConverterImpl.theInstance.parseDecimal(m_string);
            DatatypeConverterImpl.theInstance.printDecimal(bigDecimal);
        } catch (NumberFormatException e) {
        } catch (IllegalArgumentException e) {
        }
        try {
            byte m_byte = DatatypeConverterImpl.theInstance.parseByte(m_string);
            DatatypeConverterImpl.theInstance.printByte(m_byte);
        } catch (IllegalArgumentException e) {
        }
        try {
            double dbl = DatatypeConverterImpl.theInstance.parseDouble(m_string);
            DatatypeConverterImpl.theInstance.printDouble(dbl);
        } catch (NumberFormatException e) {
        }
        try {
            Calendar dateTime = DatatypeConverterImpl.theInstance.parseDateTime(m_string);
            DatatypeConverterImpl.theInstance.printDateTime(dateTime);
        } catch (IllegalArgumentException e) {
        }
        try {
            long longV = DatatypeConverterImpl.theInstance.parseUnsignedInt(m_string);
            DatatypeConverterImpl.theInstance.printUnsignedInt(longV);
        } catch (NumberFormatException e) {
        }
        try {
            int shrt = DatatypeConverterImpl.theInstance.parseUnsignedShort(m_string);
            DatatypeConverterImpl.theInstance.printUnsignedShort(shrt);
        } catch (NumberFormatException e) {
        }
        try {
            Calendar time = DatatypeConverterImpl.theInstance.parseTime(m_string);
            DatatypeConverterImpl.theInstance.printTime(time);
        } catch (IllegalArgumentException e) {
        }
        try {
            Calendar date = DatatypeConverterImpl.theInstance.parseDate(m_string);
            DatatypeConverterImpl.theInstance.printDate(date);
        } catch (IllegalArgumentException e) {
        }
        try {
            String smplType = DatatypeConverterImpl.theInstance.parseAnySimpleType(m_string);
            DatatypeConverterImpl.theInstance.printAnySimpleType(smplType);
        } catch (IllegalArgumentException e) {
        }
        try {
            String strg = DatatypeConverterImpl.theInstance.parseString(m_string);
            DatatypeConverterImpl.theInstance.printString(strg);
        } catch (IllegalArgumentException e) {
        }
        try {
            BigInteger bigInteger = DatatypeConverterImpl.theInstance.parseInteger(m_string);
            DatatypeConverterImpl.theInstance.printInteger(bigInteger);
        } catch (IllegalArgumentException e) {
        }
        try {
            int intgr = DatatypeConverterImpl.theInstance.parseInt(m_string);
            DatatypeConverterImpl.theInstance.printInt(intgr);
        } catch (IllegalArgumentException e) {
        }
        try {
            long lng = DatatypeConverterImpl.theInstance.parseLong(m_string);
            DatatypeConverterImpl.theInstance.printLong(lng);
        } catch (IllegalArgumentException e) {
        }
        try {
            short shrt = DatatypeConverterImpl.theInstance.parseShort(m_string);
            DatatypeConverterImpl.theInstance.printShort(shrt);
        } catch (IllegalArgumentException e) {
        }
        try {
            float flt = DatatypeConverterImpl.theInstance.parseFloat(m_string);
            DatatypeConverterImpl.theInstance.printFloat(flt);
        } catch (IllegalArgumentException e) {
        }
    }

    public static void FuzzOne(String SMFData) {
        DataTypeConverterFuzzer testClosure = new DataTypeConverterFuzzer(fuzzedDataProvider.consumeInt(), SMFData);
        testClosure.test();
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
