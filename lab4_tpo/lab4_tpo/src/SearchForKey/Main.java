package SearchForKey;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File directory = new File("C:\\Users\\User\\IdeaProjects\\lab4_tpo\\TestText");
        List<String> keywords = List.of("parallel", "sort", "graphic", "computer", "framework", "algorithm");

        KeywordSearchTask searchTask = new KeywordSearchTask(directory, keywords);
        List<File> matchingFiles = searchTask.invoke();

        System.out.println("Matching files:");
        for (File file : matchingFiles) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
