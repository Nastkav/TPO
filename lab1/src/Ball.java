import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;
public class Ball {
    private BallCanvas canvas;

    private int threadPriority;
    public Color color;
    public static final int XSIZE = 20;
    public static final int YSIZE = 20;
    public int x = 0;
    public int  y = 0;
    private int dx = 2;
    private int dy = 2;


    public Ball(BallCanvas c, Color color){
        this.canvas = c;
        this.color = color;

        if (color == Color.RED) {
            this.threadPriority = Thread.MAX_PRIORITY;
        } else {
            this.threadPriority = Thread.MIN_PRIORITY;
        }

        x = this.canvas.getWidth()/5;
        y = 0;
        /*if(Math.random()<0.5){
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        }else{
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }*/
    }

    public void start(){
        BallThread ballThread = new BallThread(this, this.threadPriority);
        ballThread.start();
    }
    public void draw (Graphics2D g2, Color color){
        g2.setColor(color);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));

    }


    public void move(){
        x+=dx;
        y+=dy;
        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        if (this.canvas.checkCollision(this)) {
            this.canvas.remove(this); // видалення м'яча з полотна
            Thread.currentThread().interrupt(); // зупинка потока


            return;
        }
        this.canvas.repaint();


    }
}


