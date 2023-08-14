import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

// клас, який створює вікно програми та відображає на ньому кулі

public class BounceFrame extends JFrame {

    private JLabel textCounter = new JLabel("Pit count: 0");
    private BallCanvas canvas; //поле для малювання

    private int thr;
    private Color col;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;

    public BounceFrame() {
        // Встановлюємо розмір та заголовок вікна
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm");



        // Додаємо до вікна полотно для малювання
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);



        // Додаємо панель з кнопками Start та Stop
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);


        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");
        JButton buttonAddPit = new JButton("Pit+");
        JButton buttonAddRed = new JButton("Red+");
        JButton buttonPriority = new JButton("Priority");
        // Додаємо обробники подій для кнопок

        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b = new Ball(canvas, Color.BLUE);
                canvas.add(b);
                BallThread thread = new BallThread(b, Thread.MIN_PRIORITY);
                thread.start();
                System.out.println("Thread name = " + thread.getName());
            }
        });

        buttonPriority.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i <= 199; i++) {
                    if(i == 99){
                        thr = Thread.NORM_PRIORITY;
                        col = Color.RED;
                    }
                    else {
                        thr = Thread.MAX_PRIORITY;
                        col = Color.BLUE;
                    }
                    Ball b = new Ball(canvas, col);
                    canvas.add(b);
                    BallThread thread = new BallThread(b, thr);
                    thread.start();
                    System.out.println("Thread name = " + thread.getName());
                }

            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Завершуємо програму при натисканні кнопки стоп
                System.exit(0);
            }

        });
        buttonAddPit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pit p = new Pit(canvas);
                canvas.addPit(p);
            }
        });

        buttonAddRed.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Ball b = new Ball(canvas, Color.RED);
                canvas.add(b);
                BallThread thread = new BallThread(b, Thread.MAX_PRIORITY);
                thread.start();
                System.out.println("Thread name = " + thread.getName());
            }
        });



        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonAddRed);
        buttonPanel.add(buttonStop);
        buttonPanel.add(buttonAddPit);
        buttonPanel.add(buttonPriority);


        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}



