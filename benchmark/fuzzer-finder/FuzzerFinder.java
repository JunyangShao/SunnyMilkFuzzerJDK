import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FuzzerFinder {

    public static void main(String[] args) throws IOException {
        Path start = Paths.get("../oss-fuzz/projects/");
        try (Stream<Path> stream = Files.walk(start)) {
            stream
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .filter(file -> file.getName().endsWith(".java"))
                    .forEach(FuzzerFinder::checkFile);
        }
    }

    public static void checkFile(File file) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(file);
            cu.findAll(MethodDeclaration.class).stream()
                    .filter(m -> m.getDeclarationAsString(false, false, false).startsWith("void fuzzerTestOneInput(FuzzedDataProvider"))
                    .forEach(m -> {
                        long count = m.findAll(MethodCallExpr.class, mc -> mc.getNameAsString().equals("consumeRemainingAsString")).size();
                        if (count == 1) {
                            createCopies(file, cu.clone(), m.clone());
                        }
                    });
        } catch (Exception e) {
            System.out.println("Error processing file: " + file.getAbsolutePath());
        }
    }

    public static void createCopies(File file, CompilationUnit cu, MethodDeclaration method) {
        try {
            String path = file.getAbsolutePath().replace("../oss-fuzz/projects/", "../extracted/");
            Path directoryPath = Paths.get(path).getParent();
            Files.createDirectories(directoryPath);
    
            cu.getClassByName(file.getName().replace(".java", "")).ifPresent(c -> c.setName(file.getName().replace(".java", "Jazzer")));
            Files.writeString(Paths.get(path.replace(".java", "Jazzer.java")), cu.toString());
    
            cu = StaticJavaParser.parse(file);
            cu.getImports().removeIf(id -> id.getNameAsString().equals("com.code_intelligence.jazzer.api.FuzzedDataProvider"));
            cu.getClassByName(file.getName().replace(".java", "")).ifPresent(c -> c.setName(file.getName().replace(".java", "SMF")));
            cu.findAll(MethodDeclaration.class).stream()
                .filter(m -> m.getDeclarationAsString(false, false, false).startsWith("void fuzzerTestOneInput(FuzzedDataProvider"))
                .forEach(m -> {
                    m.setName("FuzzOne");
                    m.getParameters().get(0).setType("String").setName("SMFData");
                    m.getBody().ifPresent(b -> b.findAll(MethodCallExpr.class, mc -> mc.getNameAsString().equals("consumeRemainingAsString")).forEach(mc -> mc.replace(new NameExpr("SMFData"))));
                });
            Files.writeString(Paths.get(path.replace(".java", "SMF.java")), cu.toString());
    
            cu.getClassByName(file.getName().replace(".java", "SMF")).ifPresent(c -> c.setName(file.getName().replace(".java", "Main")));
            
            cu.addImport("java.nio.file.Files");
            cu.addImport("java.nio.file.Paths");
            TypeDeclaration<?> typeDeclaration = cu.getType(0);
            String mainMethodCode =
                "public static void main(String[] args) {" +
                "File folder = new File(\"./fuzzerOut\");" +
                "File[] listOfFiles = folder.listFiles();" +
                "if (listOfFiles != null) {" +
                "for (File file : listOfFiles) {" +
                "if (file.isFile()) {" +
                "String content = readFileAsString(file.getAbsolutePath());" +
                "FuzzOne(content);" +
                "}" +
                "}" +
                "} else {" +
                "System.out.println(\"The directory is empty or it does not exist.\");" +
                "}" +
                "}";
            String readFileAsStringCode = "private static String readFileAsString(String fileName) {" +
                "try {" +
                "String content = new String(Files.readAllBytes(Paths.get(fileName)));" +
                "return content;" +
                "} catch (IOException e) {" +
                "return \"\";" +
                "}" +
                "}";
            typeDeclaration.addMember(StaticJavaParser.parseBodyDeclaration(mainMethodCode));
            typeDeclaration.addMember(StaticJavaParser.parseBodyDeclaration(readFileAsStringCode));
            Files.writeString(Paths.get(path.replace(".java", "Main.java")), cu.toString());
        } catch (IOException e) {
            System.out.println("Error creating copies: " + file.getAbsolutePath());
        }
    }    
}
