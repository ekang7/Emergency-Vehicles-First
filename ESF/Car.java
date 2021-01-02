import java.util.ArrayList;
import java.util.List;
/**
 * Write a description of class Car2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Car
{
    private Square start;
    private Square end;
    private Square[][] squares;
    private Calc calc;
    private ArrayList<Square> path;
    private static final int acceleration = 10;
    private ArrayList<String> turns = new ArrayList<String>();
    private int type;
    private static ArrayList<Square> no; 
    private ArrayList<Square> stop = new ArrayList<Square>(); 
    
    public static void main(String[] args) {
        Square[][] squares = SquareGenerator2.getSquares();
        Car car = new Car(new Square(0, 1, 1), new Square(0, 66, 11), squares, 1);
        ArrayList<Square> path = car.calculatePath();
        System.out.println(path);
        
        Square s1 = car.getPosition(0);
        Square s2 = car.getPosition(1);
        System.out.println(s1.toString() + ", " + s1.can());
        System.out.println(s2.toString() + ", " + s2.can());
    }

    public Car(Square start, Square end, Square[][] squares, int type) {
        this.start = start;
        this.end = end;
        this.squares = squares;
        this.calc = new Calc();
        this.type = type;
        if(type==-1)
        {
        
        no = this.calculatePath2(); 
        }
        
        calculatePath();
    }

    public int getType() {
        return type;
    }

    public Square getStart() {
        return start;
    }

    public Square getEnd() {
        return end;
    }

    public void setStart(Square start) {
        this.start = start;
    }
      public ArrayList<Square> calculatePath2() {
        Vertex[][] vertices = new Vertex[squares.length][squares[0].length];

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                vertices[i][j] = new Vertex(squares[i][j].toString());
            }
        }

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                ArrayList<Square> sq = getConnectedSquares(squares[i][j], squares);
                for (Square x : sq) {
                    vertices[i][j].addNeighbour(new Edge(1, vertices[i][j], vertices[x.getY()][x.getX()]));
                }
            }
        }

        DijkstraShortestPath shortestPath = new DijkstraShortestPath();
        shortestPath.computeShortestPaths(vertices[start.getY()][start.getX()]);
        List<Vertex> path = shortestPath.getShortestPathTo(vertices[end.getY()][end.getX()]);

        ArrayList<Square> squarePath = new ArrayList<Square>();
        for (Vertex point : path) {
            String coor = point.toString();
            int x = Integer.parseInt(coor.split("\\.")[0]);
            int y = Integer.parseInt(coor.split("\\.")[1]);

            Square s = new Square(0, x, y);
            squarePath.add(s);
        }

        this.path = squarePath;
        return squarePath;
    }
    public ArrayList<Square> calculatePath() {
        Vertex[][] vertices = new Vertex[squares.length][squares[0].length];
        for (int i = 0; type == -1 && i<no.size() && i< this.calculatePath2().size(); i++)
        {
            if(no.get(i).equals(path.get(i)))
            {
                stop.add(no.get(i));
            }
        }
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                vertices[i][j] = new Vertex(squares[i][j].toString());
            }
        }

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                ArrayList<Square> sq = getConnectedSquares(squares[i][j], squares);
                for (Square x : sq) {
                    int val = 1;
                    for(int k = 0; k<stop.size(); k++)
                    {
                    if(stop.get(k).equals(x))
                    {
                    val = (int)(2* Math.pow(10,5));
                    }
                    
                    }
                    vertices[i][j].addNeighbour(new Edge(val, vertices[i][j], vertices[x.getY()][x.getX()]));
                }
            }
        }
        
        DijkstraShortestPath shortestPath = new DijkstraShortestPath();
        shortestPath.computeShortestPaths(vertices[start.getY()][start.getX()]);
        List<Vertex> path = shortestPath.getShortestPathTo(vertices[end.getY()][end.getX()]);

        ArrayList<Square> squarePath = new ArrayList<Square>();
        for (Vertex point : path) {
            String coor = point.toString();
            int x = Integer.parseInt(coor.split("\\.")[0]);
            int y = Integer.parseInt(coor.split("\\.")[1]);

            Square s = new Square(0, x, y);
            squarePath.add(s);
        }

        this.path = squarePath;
        return squarePath;
    }
    

    public static ArrayList<Square> getConnectedSquares(Square square, Square[][] squares) {
        ArrayList<Square> connected = new ArrayList<Square>();
        if (square.canUp && square.getY() > 0) {
            connected.add(squares[square.getY() - 1][square.getX()]);
        }
        if (square.canDown && square.getY() < squares.length - 1) {
            connected.add(squares[square.getY() + 1][square.getX()]);
        }
        if (square.canRight && square.getX() < squares[0].length - 1) {
            connected.add(squares[square.getY()][square.getX() + 1]);
        }
        if (square.canLeft && square.getX() > 0) {
            connected.add(squares[square.getY()][square.getX() - 1]);
        }
        return connected;
    }

    // true - horizontal
    // false - vertical
    public boolean getDirection(Square before, Square after) {
        return (before.getX() - after.getX() != 0);
    }

    public boolean didDirectionChange(Square before, Square current, Square after) {
        boolean dir1 = getDirection(before, current);
        boolean dir2 = getDirection(current, after);
        return dir1 != dir2;
    }

    public ArrayList<Square> getIntersections() {
        ArrayList<Square> intersections = new ArrayList<Square>();
        if (path.size() >= 2) {
            intersections.add(path.get(0));
            for (int i = 1; i < path.size() - 1; i++) {
                if (didDirectionChange(path.get(i-1), path.get(i), path.get(1+1))) {
                    intersections.add(path.get(i));
                }
            }
            intersections.add(path.get(path.size() - 1));
        }
        return intersections;
    }

    public ArrayList<Double> calcTotalPathTime(ArrayList<Square> path, int a) {
        ArrayList<Double> timeFrames = new ArrayList<Double>();
        String currentTurn = "0";
        String nextTurn = "l";
        turns = new ArrayList<String>();
        for (int i=0; i<path.size()-1; i++) {
            turns.add(currentTurn);
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
                timeFrames.add(time + calc.timeToRest(path.get(i).getX(),path.get(i).getY(),path.get(i+1).getX(),path.get(i+1).getY(),nextTurn,a));
            }
            else {
                double time = 0;
                for (int c=0; c<timeFrames.size();c++) {
                    time += timeFrames.get(c);
                }
                timeFrames.add(time + calc.timeStraight(path.get(i).getX(),path.get(i).getY(),path.get(i+1).getX(),path.get(i+1).getY(), currentTurn, nextTurn,a));
            }
        }
        //turns.add(nextTurn);
        return timeFrames;
    }

    public int getDistance(Square s1, Square s2) {
        if (s1.getX() - s2.getX() == 0) {
            return Math.abs(s2.getY() - s1.getY());
        }
        return Math.abs(s2.getX() - s2.getY());
    }

    /*
    public int[] getPosition(int time) {
    ArrayList<Double> timeframes = calcTotalPathTime(getIntersections(), acceleration);
    int timeframe = -1;
    for (int i = 0; i < timeframes.size(); i++) {
    if (time - timeframes.get(i) > 0) {
    time -= timeframes.get(i);
    } else {
    timeframe = i;
    }
    }

    if (timeframe == -1) {
    int[] pos = {path.get(path.size() - 1).getX(), path.get(path.size() - 1).getX()};
    return pos;
    }

    String beginningTurn = turns.get(timeframe);
    double initialVelocity = 4.9248;
    if (beginningTurn.equals("l")) {
    initialVelocity = 8.53;
    }

    String endTurn = turns.get(timeframe + 1);
    double finalVelocity = 4.9248;
    if (endTurn.equals("l")) {
    finalVelocity = 8.53;
    }

    Square beginningSquare = path.get(timeframe);
    Square endSquare = path.get(timeframe + 1);
    int distance = getDistance(beginningSquare, endSquare) * 5;

    double dx = (2 * acceleration * distance + Math.pow(initialVelocity, 2) - Math.pow(finalVelocity, 2)) / (4 * acceleration);

    double accDist = distance - dx;

    double centerTime = (Math.sqrt(Math.pow(initialVelocity, 2) + 2 * acceleration * accDist) - initialVelocity) / acceleration;

    double d = (Math.pow(time * acceleration + initialVelocity, 2) - Math.pow(initialVelocity, 2)) / (2 * acceleration);
    if (time > centerTime) {
    d = (Math.pow(time * -acceleration + initialVelocity, 2) - Math.pow(initialVelocity, 2)) / (2 * -acceleration) + accDist;
    }

    double y = 0;
    double x = 0;
    if (getDirection(beginningSquare, endSquare)) { // horizontal
    y = beginningSquare.getY() * 5;
    if (endSquare.getX() - beginningSquare.getX() > 0) { //right
    x = beginningSquare.getX() * 5 + d;
    } else {
    x = beginningSquare.getX() * 5 - d;
    }
    } else { // vertical
    x = beginningSquare.getX() * 5;
    if (endSquare.getY() - beginningSquare.getY() > 0) { // down
    y = beginningSquare.getY() * 5 + d;
    } else {
    y = beginningSquare.getY() * 5 - d;
    }
    }

    int[] pos = {(int)x, (int)y};

    return pos;
    }*/
/*
    public Square getPosition(int time) {
        ArrayList<Double> timeframes = calcTotalPathTime(getIntersections(), acceleration);
        int timeframe = -1;
        for (int i = 0; i < timeframes.size(); i++) {
            if (time - timeframes.get(i) > 0) {
                time -= timeframes.get(i);
            } else {
                timeframe = i;
            }
        }

        if (timeframe == -1) {
            //int[] pos = {path.get(path.size() - 1).getX(), path.get(path.size() - 1).getX()};
            //return pos;
            return path.get(path.size() - 1);
        }

        String beginningTurn = turns.get(timeframe);
        double initialVelocity = 4.9248;
        if (beginningTurn.equals("l")) {
            initialVelocity = 8.53;
        }

        String endTurn = turns.get(timeframe + 1);
        double finalVelocity = 4.9248;
        if (endTurn.equals("l")) {
            finalVelocity = 8.53;
        }

        Square beginningSquare = path.get(timeframe);
        Square endSquare = path.get(timeframe + 1);
        int distance = getDistance(beginningSquare, endSquare);

        double dx = (2 * acceleration * distance + Math.pow(initialVelocity, 2) - Math.pow(finalVelocity, 2)) / (4 * acceleration);

        double accDist = distance - dx;

        double centerTime = (Math.sqrt(Math.pow(initialVelocity, 2) + 2 * acceleration * accDist) - initialVelocity) / acceleration;

        double d = (Math.pow(time * acceleration + initialVelocity, 2) - Math.pow(initialVelocity, 2)) / (2 * acceleration);
        if (time > centerTime) {
            d = (Math.pow(time * -acceleration + initialVelocity, 2) - Math.pow(initialVelocity, 2)) / (2 * -acceleration) + accDist;
        }

        double y = 0;
        double x = 0;
        if (getDirection(beginningSquare, endSquare)) { // horizontal
            y = beginningSquare.getY();
            if (endSquare.getX() - beginningSquare.getX() > 0) { //right
                x = beginningSquare.getX() + d;
            } else {
                x = beginningSquare.getX() - d;
            }
        } else { // vertical
            x = beginningSquare.getX();
            if (endSquare.getY() - beginningSquare.getY() > 0) { // down
                y = beginningSquare.getY() + d;
            } else {
                y = beginningSquare.getY() - d;
            }
        }

        //int[] pos = {(int)x, (int)y};

        System.out.println(squares[(int)y][(int)x].toString() + "\t" + squares[(int)y][(int)x].can().toString());

        Square[] abc = new Square[4];
        abc[0] = squares[(int)Math.ceil(y)][(int)Math.ceil(x)];
        abc[1] = squares[(int)Math.ceil(y)][(int)Math.floor(x)];
        abc[2] = squares[(int)Math.floor(y)][(int)Math.ceil(x)];
        abc[3] = squares[(int)Math.floor(y)][(int)Math.floor(x)];
        for (int i = 0; i < abc.length; i++) {
            if (abc[i].getType() == 0) {
                return abc[i];
            }
        }
        throw new Error();
        
    }
    */
   
   public Square getPosition(int time) {
       if (time < path.size()) {
            return path.get(time);
        }
        return path.get(path.size() - 1);
    }
}
