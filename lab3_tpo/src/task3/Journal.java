package task3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Journal {
    public List<Group> groups = new ArrayList<>();

    public void addGroup(Group group) {
        groups.add(group);
    }

    public Journal() {
        Group group1 = new Group("first");
        Group group2 = new Group("second");
        Group group3 = new Group("third");

        addGroup(group1);
        addGroup(group2);
        addGroup(group3);

        group1.addStudent(new Student("Student1"));
        group1.addStudent(new Student("Student2"));
        group1.addStudent(new Student("Student3"));
        group2.addStudent(new Student("Student4"));
        group2.addStudent(new Student("Student5"));
        group2.addStudent(new Student("Student6"));
        group3.addStudent(new Student("Student7"));
        group3.addStudent(new Student("Student8"));
        group3.addStudent(new Student("Student9"));
    }

    public void addMarks(int week, int mark, String code, String student_name) {
        synchronized (this) {
            for (Group group : groups) {
                if (group.code.equals(code)) {
                    group.addMarks(week, mark, student_name);
                }
            }
        }
    }


    public synchronized void show() {
        for (Group group : groups) {
            System.out.println("Group name: " + group.code + "  ");
            for (Student student : group.students) {
                System.out.print(student.name + " - ");
                for (Mark mark : student.marks) {
                    int a = mark.mmark;
                    System.out.print(mark.mmark + ", ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}