
/**
 * Write a description of class SquareGenerator2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SquareGenerator2
{
    private static int[][] horizontal = {
        {0, 0, 671},
        {0, 54, 671},
        {0, 116, 671},
        {0, 183, 393},
        {0, 229, 671},
        {216, 291, 455},
        {0, 349, 671},
        {104, 411, 112},
        {330, 411, 106},
        {0, 474, 671}
    };
    
    private static int[][] vertical = {
        {0, 0, 474},
        {104, 0, 474},
        {216, 0, 474},
        {330, 0, 474},
        {436, 0, 474},
        {551, 0, 474},
        {671, 0, 474}
    };
    
    private static int totalWidth = 671;
    private static int totalHeight = 474;
    
    private static double size = 5.0;
    
    public static int convert(int num) {
        return (int) Math.round(num / size);
    }
    
    public static void setHorizontal(int x, int y, int length, Square[][] squares) {
        for (int i = x; i < x + length; i++) {
            squares[y][i].setType(0);
            squares[y][i].canLeft = true;
            squares[y + 1][i].setType(0);
            squares[y + 1][i].canRight = true;
        }
    }
    
    public static void setVertical(int x, int y, int length, Square[][] squares) {
        for (int i = y; i < length; i++) {
            squares[i][x].setType(0);
            squares[i][x].canDown = true;
            squares[i][x + 1].setType(0);
            squares[i][x + 1].canUp = true;
        }
    }
    
    public static Square[][] getEmptySquares() {
        int width = convert(totalWidth) + 2;
        int height = convert(totalHeight) + 2;
        Square[][] squares = new Square[height][width];
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j] = new Square(1, j, i);
            }
        }
        return squares;
    }
    
    public static Square[][] getSquares() {
        Square[][] squares = getEmptySquares();
        for (int i = 0; i < horizontal.length; i++) {
            int x = convert(horizontal[i][0]);
            int y = convert(horizontal[i][1]);
            int length = convert(horizontal[i][2]);
            setHorizontal(x, y, length, squares);
        }
        
        for (int i = 0; i < vertical.length; i++) {
            int x = convert(vertical[i][0]);
            int y = convert(vertical[i][1]);
            int length = convert(vertical[i][2]);
            setVertical(x, y, length, squares);
        }
        
        return squares;
    }
}
