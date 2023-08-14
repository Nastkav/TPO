package task3;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Student {
    public String name;
    public List<Mark> marks = new ArrayList<>();
    final Lock lock = new ReentrantLock();
    public Student(String name){
        this.name=name;
    }
    public void addMarks(int week, int mark) {
       // synchronized (this)
          lock.lock() ;
          try {

              marks.add(new Mark(week, mark));
          } finally {
              lock.unlock();
          }

    }
}
