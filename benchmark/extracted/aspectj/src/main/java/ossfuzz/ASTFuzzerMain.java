package ossfuzz;

import java.util.HashMap;
import org.aspectj.org.eclipse.jdt.core.dom.AST;
import org.aspectj.org.eclipse.jdt.core.dom.ASTParser;
import org.aspectj.org.eclipse.jdt.core.dom.CompilationUnit;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ASTFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try {
            int astlevel = fuzzedDataProvider.consumeInt();
            String source = SMFData;
            ASTParser parser = ASTParser.newParser(astlevel);
            parser.setSource(source.toCharArray());
            parser.setCompilerOptions(new HashMap());
            CompilationUnit cu = (CompilationUnit) parser.createAST(null);
            cu.getAST();
        } catch (IllegalArgumentException ex) {
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
