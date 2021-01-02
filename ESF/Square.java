import java.util.ArrayList;
public class Square
{
    private int type;
    private int direct = 3;
    private int xPos;
    private int yPos;
    private ArrayList<Double> timeFrames = new ArrayList<Double>();
    private ArrayList<Integer> timeFramesCars = new ArrayList<Integer>();
    public boolean canRight = false;
    public boolean canLeft = false;
    public boolean canUp = false;
    public boolean canDown = false;

    /**
     * Constructor for objects of class Square
     */
    public Square(int x, int corX, int corY)
    {
        type = x;
        xPos = corX;
        yPos = corY;
    }

    public int getType() {
        return type;
    }
    
    public void setType(int t) {
        type = t;
    }
    
    public void setDirection(int dir) {
        direct = dir;
    }
    
    public ArrayList<Integer> can() {
        ArrayList<Integer> canStuff = new ArrayList<Integer>();
        canStuff.add(canUp ? 1 : 0);
        canStuff.add(canDown ? 1 : 0);
        canStuff.add(canLeft ? 1 : 0);
        canStuff.add(canRight ? 1 : 0);
        if (canStuff.get(0) + canStuff.get(1) + canStuff.get(2) + canStuff.get(3) > 1) {
            canStuff.add(0);
        } else {
            canStuff.add(5);
        }
        return canStuff;
    }
    public void addTF(double x, int y) {
        timeFrames.add(x);
        timeFramesCars.add(y);
    }
    public int getX() {
        return xPos;
    }
    public int getY() {
        return yPos;
    }
    public String toString() {
        return xPos + "." + yPos;
    }
    public boolean equals(Square square) {
        return square.toString().equals(this.toString());
    }
}
