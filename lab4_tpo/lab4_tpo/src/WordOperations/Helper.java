package WordOperations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class Helper extends RecursiveTask<Set<String>> {
    private final File textFile;
    private Set<String> commonWords = new HashSet<>();
    private List<File> files = new ArrayList<>();

    public Helper(File textFile) {
        this.textFile = textFile;
    }

    public List<File> getFiles() {
        return files;
    }

    @Override
    protected Set<String> compute() {
        if (textFile.isDirectory()) {
            List<Helper> subTasks = processDirectory();
            subTasks.forEach(ForkJoinTask::fork);

            for (var subTask : subTasks)
                intersectWordSets(subTask.join());

        } else {
            try {
                processSingleTextFile();
                files.add(textFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return commonWords;
    }

    private void processSingleTextFile() throws IOException {
        var words = WordOperations.getWords(textFile);
        for (var word : words) {
            commonWords.add(word.toLowerCase());
        }
    }

    private List<Helper> processDirectory() {
        List<Helper> subTasks = new ArrayList<>();
        var subFiles = textFile.listFiles();
        assert subFiles != null;
        for (var subFile : subFiles) {
            var subTask = new Helper(subFile);
            subTasks.add(subTask);
        }
        return subTasks;
    }

    private synchronized void intersectWordSets(Set<String> intersectedSet) {
        if (commonWords.isEmpty()) {
            commonWords.addAll(intersectedSet);
        } else {
            commonWords.retainAll(intersectedSet);
        }
    }

}