import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
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
            String path = file.getAbsolutePath().replace("../oss-fuzz/projects/", "../extracted2/");
            Path directoryPath = Paths.get(path).getParent();
            Files.createDirectories(directoryPath);

            cu.getClassByName(file.getName().replace(".java", "")).ifPresent(c -> c.setName(file.getName().replace(".java", "Jazzer")));
            Files.writeString(Paths.get(path.replace(".java", "Jazzer.java")), cu.toString());

            cu = StaticJavaParser.parse(file);
            cu.getClassByName(file.getName().replace(".java", "")).ifPresent(c -> c.setName(file.getName().replace(".java", "SMF")));
            cu.findAll(MethodDeclaration.class).stream()
                .filter(m -> m.getDeclarationAsString(false, false, false).startsWith("void fuzzerTestOneInput(FuzzedDataProvider"))
                .forEach(m -> {
                    m.setName("FuzzOne");
                    m.getParameters().get(0).setType("String").setName("SMFData");
                    m.getBody().ifPresent(b -> b.findAll(MethodCallExpr.class, mc -> mc.getNameAsString().equals("consumeRemainingAsString")).forEach(mc -> mc.replace(new NameExpr("SMFData"))));
                });
            Files.writeString(Paths.get(path.replace(".java", "SMF.java")), cu.toString());
        } catch (IOException e) {
            System.out.println("Error creating copies: " + file.getAbsolutePath());
        }
    }
}
