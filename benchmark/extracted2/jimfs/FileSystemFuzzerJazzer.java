import com.code_intelligence.jazzer.api.FuzzedDataProvider;
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

public class FileSystemFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
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
        String content = data.consumeRemainingAsString();
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
}
