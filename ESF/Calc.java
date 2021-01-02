import java.util.ArrayList;
/**
 * Write a description of class CalcTime here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Calc
{  
    public double timeStraight (int x1, int y1, int x2, int y2, String ti, String tf, int accel) {
        int dist = 0;
        if (x1 == x2) {
            dist = (y2 - y1)*5;
        } else if (y1 == y2) {
            dist = (x2 - x1)*5;
        }
        double v_init = 0;
        double v_fin = 0;
        if (ti.equals ("l")) {
            v_init = 8.53;
        } else if (ti.equals ("r")) {
            v_init = 4.9248;
        }
        if (tf.equals ("l")) {
            v_fin = 8.53;
        } else if (tf.equals ("r")) {
            v_fin = 4.9248;
        }
        return ((Math.sqrt(2*((2*accel*dist)+Math.pow (v_fin,2)+Math.pow(v_init,2)))-v_fin-v_init)/accel);
    }
    
    public double timeToRest (int x1, int y1, int x2, int y2, String t, int accel) {
        int dist = 0;
        if (x1 == x2) {
            dist = (y2 - y1)*5;
        } else if (y1 == y2) {
            dist = (x2 - x1)*5;
        }
        double v_init = 0;
        double v_fin = 0;
        if (t.equals ("l")) {
            v_init = 8.53;
        } else if (t.equals ("r")) {
            v_init = 4.9248;
        }
        return ((Math.sqrt(2*((2*accel*dist)+Math.pow(v_init,2)))-v_init)/accel);
    }
    public ArrayList<Double> calcTotalPathTime(ArrayList<Square> path, int a) {
        ArrayList<Double> timeFrames = new ArrayList<Double>();
        String currentTurn = "0";
        String nextTurn = "l";
        for (int i=0; i<path.size()-1; i++) {
            if (currentTurn.equals("l")) {
                timeFrames.add(1.9532);
            }
            else if (currentTurn.equals("r")) {
                timeFrames.add(1.12768);
            }
            if (i != path.size()-1) {
                if (path.get(i).getX() > path.get(i+1).getX()) {
                    if (path.get(i+1).getY() > path.get(i+2).getY()) {
                        currentTurn = nextTurn;
                        nextTurn = "r";
                    }
                    if (path.get(i+1).getY() < path.get(i+2).getY()) {
                        currentTurn = nextTurn;
                        nextTurn = "l";
                    }
                }
                if (path.get(i).getX() < path.get(i+1).getX()) {
                    if (path.get(i+1).getY() > path.get(i+2).getY()) {
                        currentTurn = nextTurn;
                        nextTurn = "l";
                    }
                    if (path.get(i+1).getY() < path.get(i+2).getY()) {
                        currentTurn = nextTurn;
                        nextTurn = "r";
                    }
                }
                if (path.get(i).getY() < path.get(i+1).getY()) {
                    if (path.get(i+1).getX() > path.get(i+2).getX()) {
                        currentTurn = nextTurn;
                        nextTurn = "r";
                    }
                    if (path.get(i+1).getX() < path.get(i+2).getX()) {
                        currentTurn = nextTurn;
                        nextTurn = "l";
                    }
                }
                if (path.get(i).getY() > path.get(i+1).getY()) {
                    if (path.get(i+1).getX() > path.get(i+2).getX()) {
                        currentTurn = nextTurn;
                        nextTurn = "l";
                    }
                    if (path.get(i+1).getX() < path.get(i+2).getX()) {
                        currentTurn = nextTurn;
                        nextTurn = "r";
                    }
                }
            }
            if (i==0 || i==path.size()-1) {
                double time = 0;
                for (int c=0; c<timeFrames.size();c++) {
                    time += timeFrames.get(c);
                }
                timeFrames.add(time + timeToRest(path.get(i).getX(),path.get(i).getY(),path.get(i+1).getX(),path.get(i+1).getY(),nextTurn,a));
            }
            else {
                double time = 0;
                for (int c=0; c<timeFrames.size();c++) {
                    time += timeFrames.get(c);
                }
                timeFrames.add(time + timeStraight(path.get(i).getX(),path.get(i).getY(),path.get(i+1).getX(),path.get(i+1).getY(), currentTurn, nextTurn,a));
            }
        }
        return timeFrames;
    }
}
