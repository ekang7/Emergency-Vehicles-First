
public class Landmark
{
    private String name, description;
    private int xPos, yPos;
    public Landmark(String n, String d, int x, int y)
    {
        name = n;
        description = d;
        xPos = x;
        yPos = y;
    }

    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
    public int getX()
    {
        return xPos;
    }
    public int getY()
    {
        return yPos;
    }
}
