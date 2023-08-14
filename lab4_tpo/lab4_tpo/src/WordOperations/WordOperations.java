package WordOperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;


public class WordOperations {
    public static List<String> getWords(File file) throws IOException {
        List<String> words = new ArrayList<>();
        var reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.readLine();
        while (currentLine != null) {
            var tokens = currentLine.split("[\\s,:;.?!—\"]+");
            for (var token : tokens) {
                if (token.matches("\\p{L}[\\p{L}-']*")) {
                    words.add(token);
                }
            }

            currentLine = reader.readLine();
        }
        reader.close();
        return words;
    }

    public static Set<String> sequentialWordSearch(File textFile) {
        Set<String> commonWords = new HashSet<>();
        List<File> files = new ArrayList<>();
        processFiles(textFile, commonWords, files);
        return commonWords;
    }

    private static void processFiles(File file, Set<String> commonWords, List<File> files) {
        if (file.isDirectory()) {
            var subFiles = file.listFiles();
            if (subFiles != null) {
                for (var subFile : subFiles) {
                    processFiles(subFile, commonWords, files);
                }
            }
        } else {
            try {
                var words = getWords(file);
                commonWords.addAll(words);
                files.add(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        var textsDirectory = new File("C:\\Users\\User\\IdeaProjects\\lab4_tpo\\TestText");
        Set<String> commonWords;
        Helper searchTask = new Helper(textsDirectory);


        long startTime = System.currentTimeMillis();
        var forkJoinPool = new ForkJoinPool();
        commonWords = forkJoinPool.invoke(searchTask);
        forkJoinPool.shutdown();
        long endTime = System.currentTimeMillis();
        long forkJoinTime = endTime - startTime;

        System.out.println("Frequently encountered words found (Count - " + commonWords.size() + "):");
        int printedWords = 0;
        for (var word : commonWords) {
            System.out.print(word);
            printedWords++;
            System.out.print(printedWords % 10 == 0 ? "\n" : " ");
        }
        System.out.println();

        startTime = System.currentTimeMillis();
        sequentialWordSearch(textsDirectory);
        endTime = System.currentTimeMillis();
        long sequentialTime = endTime - startTime;


        System.out.println("ForkJoinFramework time: " + forkJoinTime + " ms");
        System.out.println("Sequential time: " + sequentialTime + " ms");

        if (forkJoinTime < sequentialTime) {
            System.out.println("ForkJoinFramework is faster.");
        } else {
            System.out.println("Sequential execution is faster.");
        }

        List<String> allWords = new ArrayList<>();
        for (var file : searchTask.getFiles()) {
            try {
                allWords.addAll(getWords(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int wordCount = allWords.size();
        int totalLength = 0;
        int maxLength = 0;
        int minLength = Integer.MAX_VALUE;

        for (var word : allWords) {
            int length = word.length();
            totalLength += length;
            if (length > maxLength) {
                maxLength = length;
            }
            if (length < minLength) {
                minLength = length;
            }
        }

        double averageLength = (double) totalLength / wordCount;
        double variance = 0;
        for (var word : allWords) {
            int length = word.length();
            variance += Math.pow(length - averageLength, 2);
        }
        variance /= wordCount;
        double standardDeviation = Math.sqrt(variance);

        System.out.println("Characteristics of word length:");
        System.out.println("Total words: " + wordCount);
        System.out.println("Average length: " + averageLength);
        System.out.println("Maximum length: " + maxLength);
        System.out.println("Minimum length: " + minLength);
        System.out.println("Variance: " + variance);
        System.out.println("Standard deviation: " + standardDeviation);
    }
}


