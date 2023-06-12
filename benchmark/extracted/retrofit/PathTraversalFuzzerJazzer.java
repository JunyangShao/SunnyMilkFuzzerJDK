package retrofit2;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import static retrofit2.TestingUtils.buildRequest;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class PathTraversalFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String str = data.consumeRemainingAsString();
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
}
