import java.awt.event.*;
import java.applet.Applet;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.Image;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.awt.*;
import java.util.Timer;
public class Component extends JComponent implements MouseListener
{
    //Private Timer
    private static java.util.Timer timer;
    private boolean paused = false;

    //Cars/Ambulance
    private static ArrayList<Car> cars = new ArrayList<Car>();
    //private Ambulance ambulanceO = new Ambulance();
    private int maxCars = 7;

    //Landmark
    private Landmark searsTower = new Landmark("Willis Tower","Tall-has windows-at least this big",150,750);
    private Landmark civis = new Landmark("Civis Analytics","A data science software consultancy company",600,400);
    private Landmark church = new Landmark("St Peters Catholic Church","A historic place of worship and as an elegant architectural creation",800,190);
    private Landmark hyatt = new Landmark("Hyatt Centric The Loop Chicago","Premium, chic downtown hotel steps away from transportation hubs",820,400);
    private Landmark italy = new Landmark("Italian Village","Romantic Italian restaurant serves classic dishes",960,490);
    private Landmark deloitte = new Landmark("Deloitte","One of the 'Big Four' accounting organizations",90,500);
    private Landmark rookery = new Landmark("Rookery Building","Rookery",760,720);
    private Landmark ibm = new Landmark("IBM Chicago Office","Historical business building, considered as an architectural masterpiece",120,400);
    private Landmark quinta = new Landmark("La Quinnta Inn & Suites","Hotel",260,250);

    //Images
    static BufferedImage ambulance;
    static BufferedImage car1;
    static BufferedImage car2;
    static BufferedImage car3;
    static BufferedImage car4;
    static BufferedImage car5;
    static BufferedImage car6;
    static BufferedImage car7;
    static BufferedImage landmark;
    static BufferedImage shoplandmark;
    static BufferedImage foodlandmark;
    static BufferedImage photolandmark;
    static BufferedImage greylandmark;
    static BufferedImage bedlandmark;
    static BufferedImage churchlandmark;
    private int carW = 18, carH = 30;

    //Map
    private static Square[][] map = SquareGenerator2.getSquares();
    private Color road = new Color(217, 217, 217);
    private Color building = new Color(252, 247, 240);
    private Color river = new Color(51, 153, 255);
    private int currentX = 0;
    private int currentY = 100;
    private int wT = 675*2;
    private int hT = 475*2;
    
    private int time = 0;
    private static TimeStuff ts = new TimeStuff();

    //Mouse
    private int xMouse = 0, yMouse = 0;

    /****************************************************
     * The following code should NOT be changed for any
     * reason. Doing so will prevent the app from running
     ****************************************************/
    public static void main (String [] args) {
        System.out.println("a");
        //System.out.println(windowTitle + " created " + new Date());
        JFrame frame = new JFrame();
        Component component = new Component ();
        frame.add (component);
        frame.addMouseListener((MouseListener) component);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
        try {
            car1 = ImageIO.read(new File("Images/BlueCar.png"));
            car2 = ImageIO.read(new File("Images/GreenCar.png"));
            car3 = ImageIO.read(new File("Images/BlackCar.png"));
            car4 = ImageIO.read(new File("Images/GreyCar.png"));
            car5 = ImageIO.read(new File("Images/OrangeCar.png"));
            car6 = ImageIO.read(new File("Images/RedCar.png"));
            car7 = ImageIO.read(new File("Images/YellowCar.png"));
            ambulance = ImageIO.read(new File("Images/ambulance.png"));
            landmark = ImageIO.read(new File("Images/landmark.png"));
            shoplandmark = ImageIO.read(new File("Images/shoplandmark.png"));
            foodlandmark = ImageIO.read(new File("Images/foodlandmark.png"));
            photolandmark = ImageIO.read(new File("Images/photolandmark.png"));
            greylandmark = ImageIO.read(new File("Images/greylandmark.png"));
            bedlandmark = ImageIO.read(new File("Images/sleeplandmark.PNG"));
            churchlandmark = ImageIO.read(new File("Images/churchlandmark.PNG"));
        }
        catch(Exception e) {
            System.out.println("Error loading content...");
        }
        
        timer = new Timer();
        timer.schedule(ts, 0, 100);
        
        ArrayList<Square> eyoy = new ArrayList<Square>();
        for(int r=0; r<map.length; r++) {
            for(int c=0; c<map[r].length; c++) {
                if (map[r][c].getType() == 0 && map[r][c].can().get(4) != 0) {
                    eyoy.add(map[r][c]);
                }
            }
        }
        
        Square startSquare = eyoy.get((int)(Math.random() * eyoy.size()));
        Square endSquare = eyoy.get((int)(Math.random() * eyoy.size()));
        while(Math.sqrt(Math.pow(startSquare.getX() - endSquare.getX(), 2) + Math.pow(startSquare.getY() - endSquare.getY(), 2)) < 70) {
            endSquare = eyoy.get((int)(Math.random() * eyoy.size()));
        }
        cars.add(new Car(startSquare,endSquare,map,-1));
        
        while (true) {
            try {
                Thread.sleep (20);
            } catch (Exception e) {}
            frame.repaint ();
        }
    }  

    public void mouseClick (MouseEvent click) {
        System.out.println ("hullo sire");
    }

    public void paintComponent(Graphics g)
    {
        time = ts.time;
        //System.out.println(time);
        
        if (cars.size() < maxCars) {
            ArrayList<Square> eyoy = new ArrayList<Square>();
            for(int r=0; r<map.length; r++) {
                for(int c=0; c<map[r].length; c++) {
                    if (map[r][c].getType() == 0 && map[r][c].can().get(4) != 0) {
                        eyoy.add(map[r][c]);
                    }
                }
            }
            
            Square startSquare = eyoy.get((int)(Math.random() * eyoy.size()));
            Square endSquare = eyoy.get((int)(Math.random() * eyoy.size()));
            while(Math.sqrt(Math.pow(startSquare.getX() - endSquare.getX(), 2) + Math.pow(startSquare.getY() - endSquare.getY(), 2)) < 40) {
                endSquare = eyoy.get((int)(Math.random() * eyoy.size()));
            }
            cars.add(new Car(startSquare,endSquare,map,(int)(Math.random()*6)));
        }
        
        int squareWidth = 10;
        int squareHeight = 10;
        currentX = 0;
        currentY = 0;
        g.setFont(new Font("Rockwell", Font.PLAIN, 12));
        for(int r=0; r<map.length; r++) {
            for(int c=0; c<map[r].length; c++) {
                if (map[r][c].getType() == 0){
                    g.setColor(road);
                }
                if (map[r][c].getType() == 1){
                    g.setColor(building);
                }
                if (map[r][c].getType() == 2){
                    g.setColor(river);
                }
                g.fillRect(currentX, currentY, squareWidth, squareHeight);
                if (currentX < wT) {
                    currentX += squareWidth;
                } else {
                    currentX = 0;
                    currentY += squareHeight;
                }
            }
        }
        for (int i = 0; i<cars.size(); i++) {
            System.out.println(cars.get(i).getStart().can());
            System.out.println(cars.get(i).getPosition(time).can());
            
            if (cars.get(i).getType()==-1) {
                
            g.drawImage(ambulance,cars.get(i).getPosition(time).getX() * 10,cars.get(i).getPosition(time).getY() * 10,carW,carH,this);
            }
            if (cars.get(i).getType()==0) {
                
            g.drawImage(car1,cars.get(i).getPosition(time).getX() * 10,cars.get(i).getPosition(time).getY() * 10,carW,carH,this);
            }
            if (cars.get(i).getType()==1) {
            g.drawImage(car2,cars.get(i).getPosition(time).getX() * 10,cars.get(i).getPosition(time).getY() * 10,carW,carH,this);
            }
            if (cars.get(i).getType()==2) {
            g.drawImage(car3,cars.get(i).getPosition(time).getX() * 10,cars.get(i).getPosition(time).getY() * 10,carW,carH,this);
            }
            if (cars.get(i).getType()==3) {
            g.drawImage(car4,cars.get(i).getPosition(time).getX() * 10,cars.get(i).getPosition(time).getY() * 10,carW,carH,this);
            }
            if (cars.get(i).getType()==4) {
            g.drawImage(car5,cars.get(i).getPosition(time).getX() * 10,cars.get(i).getPosition(time).getY() * 10,carW,carH,this);
            }
            if (cars.get(i).getType()==5) {
            g.drawImage(car6,cars.get(i).getPosition(time).getX() * 10,cars.get(i).getPosition(time).getY() * 10,carW,carH,this);
            }
            if (cars.get(i).getType()==6) {
            g.drawImage(car7,cars.get(i).getPosition(time).getX() * 10,cars.get(i).getPosition(time).getY() * 10,carW,carH,this);
            }
        }

        //Landmarks
        g.setColor(new Color(102, 153, 255));
        g.drawString(searsTower.getName(),(int)(searsTower.getX()+25),(int)(searsTower.getY())+20);
        g.drawImage(photolandmark,searsTower.getX()-12,searsTower.getY()-25,48,78,this);
        g.drawString(rookery.getName(),(int)(rookery.getX()+25),(int)(rookery.getY())+20);
        g.drawImage(photolandmark,rookery.getX()-12,rookery.getY()-25,48,78,this);
        g.setColor(new Color(255, 0, 0));
        g.drawString(civis.getName(),(int)(civis.getX()+25),(int)(civis.getY())+20);
        g.drawImage(landmark,civis.getX(),civis.getY(),20,34,this);
        g.setColor(new Color(153, 153, 153));
        g.drawString(church.getName(),(int)(church.getX()+25),(int)(church.getY())+20);
        g.drawImage(churchlandmark,church.getX(),church.getY(),20,34,this);
        g.drawString(deloitte.getName(),(int)(deloitte.getX()+25),(int)(deloitte.getY())+20);
        g.drawImage(greylandmark,deloitte.getX(),deloitte.getY(),24,34,this);
        g.drawString(ibm.getName(),(int)(ibm.getX()+25),(int)(ibm.getY())+20);
        g.drawImage(greylandmark,ibm.getX(),ibm.getY(),24,34,this);
        g.setColor(new Color(255, 51, 153));
        g.drawString(hyatt.getName(),(int)(hyatt.getX()+25),(int)(hyatt.getY())+20);
        g.drawImage(bedlandmark,hyatt.getX(),hyatt.getY(),20,34,this);
        g.drawString(quinta.getName(),(int)(quinta.getX()+25),(int)(quinta.getY())+20);
        g.drawImage(bedlandmark,quinta.getX(),quinta.getY(),20,34,this);
        g.setColor(new Color(255, 153, 51));
        g.drawString(italy.getName(),(int)(italy.getX()+25),(int)(italy.getY())+20);
        g.drawImage(foodlandmark,italy.getX(),italy.getY(),20,34,this);
        if (xMouse > quinta.getX()-20 && yMouse > quinta.getY() && xMouse < quinta.getX()+50 && yMouse < quinta.getY()+50) {
            g.setColor(Color.WHITE);
            g.fillRect(quinta.getX()+25,quinta.getY()-25,450,75);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Rockwell", Font.BOLD, 16));
            g.drawString(quinta.getName(),quinta.getX()+30,quinta.getY());
            g.setFont(new Font("Rockwell", Font.PLAIN, 12));
            g.drawString(quinta.getDescription(),quinta.getX()+30,quinta.getY()+15);
        }
        if (xMouse > ibm.getX()-20 && yMouse > ibm.getY() && xMouse < ibm.getX()+50 && yMouse < ibm.getY()+50) {
            g.setColor(Color.WHITE);
            g.fillRect(ibm.getX()+25,ibm.getY()-25,450,75);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Rockwell", Font.BOLD, 16));
            g.drawString(ibm.getName(),ibm.getX()+30,ibm.getY());
            g.setFont(new Font("Rockwell", Font.PLAIN, 12));
            g.drawString(ibm.getDescription(),ibm.getX()+30,ibm.getY()+15);
        }
        if (xMouse > rookery.getX()-20 && yMouse > rookery.getY() && xMouse < rookery.getX()+50 && yMouse < rookery.getY()+50) {
            g.setColor(Color.WHITE);
            g.fillRect(rookery.getX()+25,rookery.getY()-25,450,75);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Rockwell", Font.BOLD, 16));
            g.drawString(rookery.getName(),rookery.getX()+30,rookery.getY());
            g.setFont(new Font("Rockwell", Font.PLAIN, 12));
            g.drawString(rookery.getDescription(),rookery.getX()+30,rookery.getY()+15);
        }
        if (xMouse > deloitte.getX()-20 && yMouse > deloitte.getY() && xMouse < deloitte.getX()+50 && yMouse < deloitte.getY()+50) {
            g.setColor(Color.WHITE);
            g.fillRect(deloitte.getX()+25,deloitte.getY()-25,300,75);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Rockwell", Font.BOLD, 16));
            g.drawString(deloitte.getName(),deloitte.getX()+30,deloitte.getY());
            g.setFont(new Font("Rockwell", Font.PLAIN, 12));
            g.drawString(deloitte.getDescription(),deloitte.getX()+30,deloitte.getY()+15);
        }
        if (xMouse > italy.getX()-20 && yMouse > italy.getY() && xMouse < italy.getX()+50 && yMouse < italy.getY()+50) {
            g.setColor(Color.WHITE);
            g.fillRect(italy.getX()+25,italy.getY()-25,350,75);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Rockwell", Font.BOLD, 16));
            g.drawString(italy.getName(),italy.getX()+30,italy.getY());
            g.setFont(new Font("Rockwell", Font.PLAIN, 12));
            g.drawString(italy.getDescription(),italy.getX()+30,italy.getY()+15);
        }
        if (xMouse > 130 && yMouse > 750 && xMouse < 200 && yMouse < 800) {
            g.setColor(Color.WHITE);
            g.fillRect(searsTower.getX()+25,searsTower.getY()-25,175,75);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Rockwell", Font.BOLD, 16));
            g.drawString(searsTower.getName(),searsTower.getX()+30,searsTower.getY());
            g.setFont(new Font("Rockwell", Font.PLAIN, 12));
            g.drawString(searsTower.getDescription(),searsTower.getX()+30,searsTower.getY()+15);
        }
        if (xMouse > 580 && yMouse > 400 && xMouse < 630 && yMouse < 450) {
            g.setColor(Color.WHITE);
            g.fillRect(civis.getX()+25,civis.getY()-25,300,75);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Rockwell", Font.BOLD, 16));
            g.drawString(civis.getName(),civis.getX()+30,civis.getY());
            g.setFont(new Font("Rockwell", Font.PLAIN, 12));
            g.drawString(civis.getDescription(),civis.getX()+30,civis.getY()+15);
        }
        if (xMouse > 780 && yMouse > 190 && xMouse < 850 && yMouse < 240) {
            g.setColor(Color.WHITE);
            g.fillRect(church.getX()+25,church.getY()-25,400,75);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Rockwell", Font.BOLD, 16));
            g.drawString(church.getName(),church.getX()+30,church.getY());
            g.setFont(new Font("Rockwell", Font.PLAIN, 12));
            g.drawString(church.getDescription(),church.getX()+30,church.getY()+15);
        }
        if (xMouse > 800 && yMouse > 400 && xMouse < 870 && yMouse < 450) {
            g.setColor(Color.WHITE);
            g.fillRect(hyatt.getX()+25,hyatt.getY()-25,400,75);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Rockwell", Font.BOLD, 16));
            g.drawString(hyatt.getName(),hyatt.getX()+30,hyatt.getY());
            g.setFont(new Font("Rockwell", Font.PLAIN, 12));
            g.drawString(hyatt.getDescription(),hyatt.getX()+30,hyatt.getY()+15);
        }
        //Helper
        g.setColor(Color.BLACK);
        g.fillRect(30,30,100,5);
        g.fillRect(30,30,5,10);
        g.fillRect(125,30,5,10);
        g.drawString("50 meters",50,50);
        //Pause
        g.fillRect(1300,30,10,30);
        g.fillRect(1320,30,10,30);
        if (xMouse > 1280 && yMouse > 30 && xMouse < 1350 && yMouse < 60) {
            if (ts.paused) {
                ts.paused = false;
            }
            else {
                ts.paused = true;
            }
        }
        if (!paused) {

        }
        //Car Details
        for (int i = 0; i<cars.size(); i++) {
            if (xMouse > cars.get(i).getPosition(time).getX() * 10-20 && yMouse > cars.get(i).getPosition(time).getY() * 10 && xMouse < cars.get(i).getPosition(time).getX()*10+50 && yMouse < cars.get(i).getPosition(time).getY()*10+50) {
                g.setColor(new Color(102, 255, 51));
                g.fillRect(cars.get(i).getStart().getX()*10,cars.get(i).getStart().getY()*10,squareWidth, squareHeight);
                g.setColor(new Color(255, 0, 0));
                g.fillRect(cars.get(i).getEnd().getX()*10,cars.get(i).getStart().getY()*10,squareWidth, squareHeight);
                g.setColor(new Color(0, 51, 204));
                ArrayList<Square> temp = cars.get(i).getIntersections();
                for (int c=0; c<temp.size()-1;c++) {
                    g.drawLine(temp.get(c).getX()*10,temp.get(c).getY()*10,temp.get(c+1).getX()*10,temp.get(c+1).getY()*10);
                }
            }
        }
    }

    public void mouseExited(MouseEvent me){ //draws when leave the screen

    }

    public void mouseEntered(MouseEvent me){ //draws when enter the screen

    }

    public void mouseReleased(MouseEvent me){ //draws when mouse released

    }

    public void mousePressed(MouseEvent me){ //draws when moused pressed

    }

    public void mouseClicked(MouseEvent me){ //draws when pressed and released at same spot
        xMouse=me.getX();
        yMouse=me.getY();
    }
}