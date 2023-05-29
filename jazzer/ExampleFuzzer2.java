// Copyright 2021 Code Intelligence GmbH
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import java.security.SecureRandom;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

public class ExampleFuzzer2 {
  private static int cnt = 0;
  private static long time_elapsed = 0;
  private static long start = 0;
  private static final String s =
  "\r\n{\r\n" +
  "    \"glossary\": {\r\n" +
  "        \"title\": \"example glossary\",\r\n" +
  "        \"GlossDiv\": {\r\n" +
  "            \"title\": \"S\",\r\n" +
  "            \"GlossList\": {\r\n" +
  "                \"GlossEntry\": {\r\n" +
  "                    \"ID\": \"SGML\",\r\n" +
  "                    \"SortAs\": \"SGML\",\r\n" +
  "                    \"GlossTerm\": \"Standard Generalized Markup Language\",\r\n" +
  "                    \"Acronym\": \"SGML\",\r\n" +
  "                    \"Abbrev\": \"ISO 8879:1986\",\r\n" +
  "                    \"GlossDef\": {\r\n" +
  "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\r\n" +
  "                        \"GlossSeeAlso\": [\"GML\", \"XML\"]\r\n" +
  "                    },\r\n" +
  "                    \"GlossSee\": \"markup\"\r\n" +
  "                }\r\n" +
  "            }\r\n" +
  "        }\r\n" +
  "    }\r\n" +
  "}";
  public static void fuzzerInitialize() {
    time_elapsed = 0;
    // Optional initialization to be run before the first call to fuzzerTestOneInput.
    start = System.nanoTime();
  }

  private static String getVersion() {
    String version = System.getProperty("java.version");
    if(version.startsWith("1.")) {
      version = version.substring(2, 3);
    } else {
      int dot = version.indexOf(".");
      if(dot != -1) { version = version.substring(0, dot); }
    } return version;
  }

  public static void fuzzerTestOneInput(FuzzedDataProvider data) {
    cnt++;
    if (cnt == 1000000) {
      	    // time_elapsed += System.nanoTime() - start;
	    System.out.println(System.nanoTime() - start);
	    System.out.println(time_elapsed);
      	    System.out.println(getVersion());
	    throw new FuzzerSecurityIssueMedium("mustNeverBeCalled has been called");
    }
    try {
      long start = System.nanoTime();
      JSON.parse(s);
      time_elapsed += System.nanoTime() - start;
    } catch (JSONException ignored) {
      return;
    }
  }

  private static void mustNeverBeCalled() {
    //throw new FuzzerSecurityIssueMedium("mustNeverBeCalled has been called");
  }
}
