public abstract class SunnyMilkFuzzer {
    static {
        System.loadLibrary("SunnyMilkFuzzer");
    }
    private long time_elapsed = 0;
    private long loop_start = 0;
    private native void PrintCoverage();
    public abstract void FuzzOne(String input);
    public void Loop() {
        String input = "\r\n{\r\n    \"glossary\": {\r\n        \"title\": \"example glossary\",\r\n\t\t\"GlossDiv\": {\r\n            \"title\": \"S\",\r\n\t\t\t\"GlossList\": {\r\n                \"GlossEntry\": {\r\n                    \"ID\": \"SGML\",\r\n\t\t\t\t\t\"SortAs\": \"SGML\",\r\n\t\t\t\t\t\"GlossTerm\": \"Standard Generalized Markup Language\",\r\n\t\t\t\t\t\"Acronym\": \"SGML\",\r\n\t\t\t\t\t\"Abbrev\": \"ISO 8879:1986\",\r\n\t\t\t\t\t\"GlossDef\": {\r\n                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\r\n\t\t\t\t\t\t\"GlossSeeAlso\": [\"GML\", \"XML\"]\r\n                    },\r\n\t\t\t\t\t\"GlossSee\": \"markup\"\r\n                }\r\n            }\r\n        }\r\n    }\r\n}";
        loop_start = System.nanoTime();
        for(int i = 0; i < 100000; i++) {
            PrintCoverage();
            long start = System.nanoTime();
            FuzzOne(input);
            long finish = System.nanoTime();
            time_elapsed += finish - start;
        }
        System.out.println(time_elapsed);
        System.out.println(System.nanoTime() - loop_start);
    }
}