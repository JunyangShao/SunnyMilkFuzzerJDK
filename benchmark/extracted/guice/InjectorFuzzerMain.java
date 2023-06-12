import com.google.inject.*;
import com.google.inject.internal.Annotations;
import com.google.inject.internal.InternalFlags;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Named;
import com.google.inject.CreationException;
import com.google.inject.name.Names;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static com.google.inject.name.Names.named;
import com.google.inject.ConfigurationException;
import java.lang.annotation.Retention;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InjectorFuzzerMain {

    @Retention(RUNTIME)
    @BindingAnnotation
    @interface NumericValue {
    }

    @Retention(RUNTIME)
    @BindingAnnotation
    @interface EnumValue {
    }

    @Retention(RUNTIME)
    @BindingAnnotation
    @interface ClassName {
    }

    public enum Bar {

        TEE, BAZ, BOB
    }

    public static void FuzzOne(String SMFData) {
        String value = SMFData;
        try {
            Injector injector = Guice.createInjector(new AbstractModule() {

                @Override
                protected void configure() {
                    bindConstant().annotatedWith(NumericValue.class).to(value);
                    bindConstant().annotatedWith(EnumValue.class).to(value);
                    bindConstant().annotatedWith(ClassName.class).to(value);
                }
            });
            DummyClass foo = injector.getInstance(DummyClass.class);
        } catch (CreationException | ConfigurationException e) {
        }
    }

    public static class DummyClass {

        @Inject
        @EnumValue
        Bar enumField;

        @Inject
        @ClassName
        Class<?> classField;

        @Inject
        @NumericValue
        Byte byteField;
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
