package task3;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ProfessorTask extends RecursiveAction {
    private String name;
    private List<String> groups;
    private Journal journal;

    public ProfessorTask(String name, List<String> groups, Journal journal) {
        this.name = name;
        this.groups = groups;
        this.journal = journal;
    }

    @Override
    protected void compute() {
        for (int i = 0; i < 32; i++) {
            for (String code : groups) {
                for (Group group : journal.groups) {
                    if (group.code.equals(code)) {
                        List<Student> students = group.students;
                        for (Student student : students) {
                            String student_name = student.name;
                            int mark = (int) (Math.random() * 101);
                            journal.addMarks(i, mark, code, student_name);
                        }
                    }
                }
            }
        }
    }
}

