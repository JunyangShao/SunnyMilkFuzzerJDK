package retrofit2;

import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import static retrofit2.TestingUtils.buildRequest;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class PathTraversalFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String str = SMFData;
        class Example {

            @GET("/foo/bar/{ping}/")
            Call<ResponseBody> method(@Path("ping") String ping) {
                return null;
            }
        }
        try {
            Request request = buildRequest(Example.class, str);
            assert request.method().equals("GET") : new FuzzerSecurityIssueLow("Method is not GET");
            assert request.headers().size() == 0 : new FuzzerSecurityIssueLow("Headers are not zero");
            if (!request.url().toString().contains("bar")) {
                throw new FuzzerSecurityIssueLow("Path Traversal!");
            }
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
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
