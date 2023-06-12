import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import static com.google.common.jimfs.PathNormalization.CASE_FOLD_UNICODE;
import static com.google.common.jimfs.PathNormalization.NFD;
import java.nio.file.Paths;

public class FileSystemFuzzerMain {

    public static void FuzzOne(String SMFData) {
        Configuration.Builder configBuilder;
        if (data.consumeBoolean()) {
            configBuilder = Configuration.unix().toBuilder();
        } else {
            configBuilder = Configuration.windows().toBuilder();
        }
        FileSystem fs;
        try {
            fs = Jimfs.newFileSystem(configBuilder.setNameCanonicalNormalization(NFD, CASE_FOLD_UNICODE).setBlockSize(data.consumeInt(1, 1000)).setMaxSize(data.consumeLong(1, 100000)).setRoots(data.consumeString(50)).setWorkingDirectory("/" + data.consumeString(50)).setMaxCacheSize(data.consumeLong(1, 100000)).setPathEqualityUsesCanonicalForm(data.consumeBoolean()).build());
        } catch (java.lang.IllegalArgumentException e) {
            return;
        }
        Path foo;
        try {
            foo = fs.getPath(data.consumeString(50));
        } catch (InvalidPathException e) {
            return;
        }
        String content = SMFData;
        try {
            Files.createDirectory(foo);
            Path dummyFile = foo.resolve("dummy.txt");
            Files.write(dummyFile, ImmutableList.of(content), StandardCharsets.UTF_8);
            String fileContent = Files.readString(dummyFile);
            if (!fileContent.contains(content)) {
                throw new FuzzerSecurityIssueLow("Content not entirely written to file");
            }
        } catch (IOException ignored) {
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
