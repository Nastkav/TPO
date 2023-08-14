import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Pit {
    private BallCanvas canvas;
    public static final int SIZE = 22;
    public int x = 0;
    public int y = 0;


    public Pit(BallCanvas canvas) {
        this.canvas = canvas;

        this.x = new Random().nextInt(this.canvas.getWidth() - SIZE);
        this.y = new Random().nextInt(this.canvas.getHeight() - SIZE);
    }
    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fill(new Ellipse2D.Double(x, y, SIZE, SIZE));
    }


}
