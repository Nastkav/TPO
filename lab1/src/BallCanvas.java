import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

public class BallCanvas extends JPanel{
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Pit> pits = new ArrayList<>();

    private int pitCount = 0;

    public int getPitCount() {
        return pitCount;
    }
    public boolean checkCollision(Ball ball) {
        for (Pit pit : pits) {
            if (ball.x + Ball.XSIZE > pit.x && ball.x < pit.x + Pit.SIZE &&
                    ball.y + Ball.YSIZE > pit.y && ball.y < pit.y + Pit.SIZE) {
                pitCount++;
                System.out.println("------------------------------------------- Count = " + pitCount + " -------------------------------------------");
                return true;
            }
        }
        return false;
    }

    public void remove(Ball b) { this.balls.remove(b); }
    public void add(Ball b){
        this.balls.add(b);
    }
    public void addPit(Pit p) { this.pits.add(p); }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for(int i=0; i<balls.size();i++){
            Ball b = balls.get(i);
            b.draw(g2, b.color);
        }
        for (int i = 0; i < pits.size(); i++) {
            Pit p = pits.get(i);
            p.draw(g2);
        }
    }



}

