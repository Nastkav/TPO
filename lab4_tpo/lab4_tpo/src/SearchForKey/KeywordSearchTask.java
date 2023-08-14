package SearchForKey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class KeywordSearchTask extends RecursiveTask<List<File>> {
    private final File directory;
    private final List<String> keywords;
    private static final Object lock = new Object();

    public KeywordSearchTask(File directory, List<String> keywords) {
        this.directory = directory;
        this.keywords = keywords;
    }

    @Override
    protected List<File> compute() {
        List<KeywordSearchTask> subTasks = new ArrayList<>();
        List<File> matchingFiles = new ArrayList<>();


        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    KeywordSearchTask subTask = new KeywordSearchTask(file, keywords);
                    subTasks.add(subTask);
                    subTask.fork();
                }
            }
        } else {
            if (containsKeywords(directory)) {
                matchingFiles.add(directory);
            }
        }

        for (KeywordSearchTask subTask : subTasks) {
            matchingFiles.addAll(subTask.join());
        }

        return matchingFiles;
    }

    private boolean containsKeywords(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int totalLines = 0;
            int linesWithKeywords = 0;
            HashMap<String, Integer> keywordOccurrences = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                totalLines++;

                for (String keyword : keywords) {
                    if (line.toLowerCase().contains(keyword.toLowerCase())) {
                        linesWithKeywords++;
                        keywordOccurrences.put(keyword, keywordOccurrences.getOrDefault(keyword, 0) + 1);
                    }
                }
            }

            if (!keywordOccurrences.isEmpty()) {
                synchronized (lock) {
                    System.out.println("File: " + file.getAbsolutePath());
                    System.out.println("File name: " + file.getName());
                    System.out.println("Percentage of keywords: " + (linesWithKeywords * 100 / totalLines) + "%");
                    System.out.println("Keywords found: " + keywordOccurrences);
                    System.out.println();
                }
            }

            return !keywordOccurrences.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}



