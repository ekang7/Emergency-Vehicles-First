import java.util.TimerTask;
/**
 * Write a description of class TimeStuff here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TimeStuff extends TimerTask
{
    public int time = 0;
    public boolean paused = false;
    public void run() {
        time += paused ? 0 : 1;
    }
 
}
