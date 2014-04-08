
/* Hari Kaushik
   March 3, 2014
   Pacman.java
   This program will run the popular game of Pacman. A user-controlled
   piece will move around a board avoiding enemies and attempting to obtain
   all the cheese. The program will utilize ActionListener, Timers, KeyListener, as well as Panel Layout.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
public class Pacman {
    JFrame frame;
    MyCenter panel;
    MySouth south;
    public static void main (String[] args) {
        Pacman te = new Pacman();
        te.Run();
    }
    public void Run() {
        frame = new JFrame("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create JPanel and add to frame
        panel = new MyCenter();
        south = new MySouth();
        frame.getContentPane().add(panel, BorderLayout.CENTER); // add center game panel to frame
        frame.getContentPane().add(south, BorderLayout.SOUTH); // add instruction panel           
        frame.setSize(530, 600);        // explicitly set size in pixels
        frame.setVisible(true);     // set to false to make invisible
        frame.setResizable(false);
    }    
}   // end class Pacman
// JPanel with a private ActionListener class called "Mover"
class MyCenter extends JPanel implements KeyListener{
    private Rectangle[][]spaces = new Rectangle[10][10]; // 2D array of rectangles for board
    private Rectangle[]cheese = new Rectangle[6];   // array of rectangles that hold 6 pieces of cheese
    private Rectangle[]monster = new Rectangle[6];  // array of rectangle that hold 6 enemies
    private Random randgen;                         // random number generator
    private Rectangle pacman;                       // rectangle that holds pacman
    private int pac1, pac2;                         // array coordinates of pacman i.e. pacman[pac1][pac2]
    private boolean[] eaten = new boolean[6];       // boolean array to determine whether cheese is eaten or not
    private boolean win;                            // has game been won or not?
    private boolean dead;                           // is pacman dead?
    private int t;                                  // timer increments to open/close pacman
    private boolean mouth;                          // is mouth open or closed?
    private class Mover implements ActionListener {
        public void actionPerformed (ActionEvent e){    
            monsterMover();                         // call method that moves the enemies
            repaint();                              
        }
    }
    // The JPanel's constructor
    public MyCenter () {
        randgen = new Random();                     // create random generator object
        pac1 = 4;                                   // starting location for pacman
        pac2 = 5;
        addKeyListener(this);                
        int n, r; 
        for (int i = 0; i < 10; i++){                  // set board as array of rectangles
            for(int j = 0; j < 10; j++){            
                spaces[i][j] = new Rectangle(15 + j * 50, 15 + i * 50, 45, 45);
            }
        }
        // create rectangle  for location of pacman on board
        pacman = new Rectangle((int)spaces[pac1][pac2].getX(), (int)spaces[pac1][pac2].getY(), (int)spaces[pac1][pac2].getWidth(), (int)spaces[pac1][pac2].getHeight()); 
        for(int i = 0 ; i < 6; i++){
            cheese[i] = new Rectangle(spaces[0][0]);
        }
        
        for(int i = 0; i < 6; i++){  // randomly generate locations of cheese
           do{
                n = randgen.nextInt(9);
                r = randgen.nextInt(9);
            }while(spaces[n][r].equals(pacman) || spaces[n][r].equals(cheese[0]) || spaces[n][r].equals(cheese[1]) || spaces[n][r].equals(cheese[2]) || spaces[n][r].equals(cheese[3]) || spaces[n][r].equals(cheese[4]) || spaces[n][r].equals(cheese[5]) );
            // ensures that no cheese begin on same space as pacman or another cheese
            cheese[i] = new Rectangle(spaces[n][r]); 
            eaten[i] = false;   // set each cheese piece as uneaten
        }
        
        for(int i = 0; i < 6; i++){
            monster[i] = new Rectangle(spaces[0][0]);
        }
        
        for(int i = 0; i < 6; i++){ // randomly generate starting locations of enemies
            do{
                n = randgen.nextInt(10);
                r = randgen.nextInt(10);
            }while(spaces[n][r].equals(pacman) || spaces[n][r].equals(cheese[0]) || spaces[n][r].equals(cheese[1]) || spaces[n][r].equals(cheese[2]) || spaces[n][r].equals(cheese[3]) || spaces[n][r].equals(cheese[4]) || spaces[n][r].equals(cheese[5]));
            // ensures that enemies do not start on pacman or cheese
            monster[i] = new Rectangle(spaces[n][r]);
        }
        win = false;       
        dead = false;
        mouth = true;
        t = 0;
        Mover mover = new Mover();  
        Timer timer = new Timer(500, mover);        // initialize timer
        timer.start();                  // start timer
    }

    public void monsterMover(){ // method that moves enemies randomly
        if(!dead && !win){
            for(int i = 0; i < 6; i++){    // individually pick direction to move for each enemy
                int m = randgen.nextInt(3) + 1;
                switch(m){              // move enemy left or wrap
                    case 1:
                        if(monster[i].getX() - 50 < 15){
                            monster[i].setLocation((int)monster[i].getX() + 450, (int)monster[i].getY());
                        }
                        else{
                            monster[i].setLocation((int)monster[i].getX() - 50, (int)monster[i].getY());
                        }
                    break;
                    
                    case 2:   // move enemy right or wrap
                        if(monster[i].getX() + 50 > 465){
                            monster[i].setLocation((int)monster[i].getX() - 450, (int)monster[i].getY());
                        }
                        else{
                            monster[i].setLocation((int)monster[i].getX() + 50, (int)monster[i].getY());
                        }                    
                    break;
                    
                    case 3:    // move enemy down or wrap                   
                        if(monster[i].getY() + 50 > 465){
                            monster[i].setLocation((int)monster[i].getX(), (int)monster[i].getY() - 450);
                        }
                        else{
                            monster[i].setLocation((int)monster[i].getX(), (int)monster[i].getY() + 50);
                        }    
                    break;
                    
                    case 4:     // move enemy up or wrap
                    
                        if(monster[i].getY() - 50 < 15){
                            monster[i].setLocation((int)monster[i].getX(), (int)monster[i].getY() + 450);
                        }
                        else{
                            monster[i].setLocation((int)monster[i].getX(), (int)monster[i].getY() - 50);
                        }
                    break;
                }
            }
        }
        if(monster[0].equals(pacman) || monster[1].equals(pacman) ||monster[2].equals(pacman) ||monster[3].equals(pacman) ||monster[4].equals(pacman) || monster[5].equals(pacman)){
            dead = true;        // if enemy moves onto pacman then pacman dies
        }
    }
    public void paintComponent(Graphics g) {
        grabFocus();
        super.paintComponent(g);
        setBackground(Color.blue);  // blue background
        g.setColor(Color.white);
        g.fillRect(10, 10, 500, 500);
        g.setColor(Color.gray);
        for(int i = 1; i <= 10; i++){// draw horizontal grid lines
            g.drawLine(10, 10 + 50 * i, 510, 10 + 50 * i);
            g.drawLine(10, 10 + 50 * i + 1, 510, 10 + 50 * i + 1);
            g.drawLine(10, 10 + 50 * i + 2, 510, 10 + 50 * i + 2);
            g.drawLine(10, 10 + 50 * i + 3, 510, 10 + 50 * i + 3);
            g.drawLine(10, 10 + 50 * i + 4, 510, 10 + 50 * i + 4);
        }
        for(int i = 1; i <= 10; i++){// draw vertical grid lines
            g.drawLine(10 + 50 * i + 0, 10, 10 + 50 * i + 0, 510);
            g.drawLine(10 + 50 * i + 1, 10, 10 + 50 * i + 1, 510);
            g.drawLine(10 + 50 * i + 2, 10, 10 + 50 * i + 2, 510);
            g.drawLine(10 + 50 * i + 3, 10, 10 + 50 * i + 3, 510);
            g.drawLine(10 + 50 * i + 4, 10, 10 + 50 * i + 4, 510);
        }
        g.drawLine(10, 10, 10, 510);
        g.drawLine(11, 10, 11, 510);
        g.drawLine(12, 10, 12, 510);
        g.drawLine(13, 10, 13, 510);
        g.drawLine(14, 10, 14, 510);
        g.drawLine(10, 10, 510, 10);
        g.drawLine(10, 11, 510, 11);
        g.drawLine(10, 12, 510, 12);
        g.drawLine(10, 13, 510, 13);
        g.drawLine(10, 14, 510, 14);
        

        g.setColor(Color.yellow);
        for(int i = 0; i < 6; i++){ // make cheese pieces based on rectangle dimensions
            if(eaten[i] == true){
                continue;           // if cheese is eaten do not create visual
            }
            else{
                g.fillRect((int)cheese[i].getX() + 5, (int)cheese[i].getY() + 5, (int)cheese[i].getWidth()-10, (int)cheese[i].getHeight()-10);
            }
        }


        g.setColor(Color.red);
        if (t == 0){
            mouth = true;       // mouth open
            t++;
        }
        else if(t == 1){
            mouth = false;         // mouth closed
            t--;
        }
        for(int i = 0; i < 6; i++){ // draw 6 enemies based on rectangle dimensions
            g.setColor(Color.red);
            g.fillOval((int)monster[i].getX(), (int)monster[i].getY(), (int)monster[i].getWidth(), (int)monster[i].getWidth());
            g.setColor(Color.black);
            g.fillOval((int)monster[i].getX() + 10, (int)monster[i].getY() + 10, 10, 10);
            g.fillOval((int)monster[i].getX() + 25, (int)monster[i].getY() + 10, 10, 10);
            g.fillRect((int)monster[i].getX() + 12, (int)monster[i].getY() + 30, 25, 5);
        }

        g.setColor(Color.blue);         // draw pacman based on rectangle dimensions
        if(mouth) // draw open mouth
            g.fillArc((int)pacman.getX(), (int)pacman.getY(), (int)pacman.getWidth(), (int)pacman.getHeight(), 40, 300);
        else if (!mouth)        // draw closed mouth
            g.fillArc((int)pacman.getX(), (int)pacman.getY(), (int)pacman.getWidth(), (int)pacman.getHeight(), 20, 340);

        g.setColor(Color.green);

        Font font = new Font("Verdana", Font.BOLD, 90);
        g.setFont(font);
        if(eaten[0] && eaten[1] && eaten[2] && eaten[3] && eaten[4] && eaten[5]){ // print win message when all cheese are eaten
            g.drawString("YOU  WIN!", (int)spaces[5][0].getX(), (int)spaces[5][0].getY());
        }

        if(dead && !win){ // print lose message if pacman is killed
            g.setColor(Color.red);
            g.drawString("YOU LOSE:(", (int)spaces[5][0].getX(), (int)spaces[5][0].getY());
        }
    }


    /* Three required methods for KeyListener:
        1) keyPressed   <------ only this one used
        2) keyReleased
        3) keyTyped
    */         
    public void keyPressed (KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'r'){   // reset board and values if r is pressed
            pac1 = 4;
            pac2 = 5;
            int n, r;
            for (int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    spaces[i][j] = new Rectangle(15 + j * 50, 15 + i * 50, 45, 45);
                }
            }
            pacman = new Rectangle((int)spaces[pac1][pac2].getX(), (int)spaces[pac1][pac2].getY(), (int)spaces[pac1][pac2].getWidth(), (int)spaces[pac1][pac2].getHeight());
            for(int i = 0 ; i < 6; i++){
                cheese[i] = new Rectangle(spaces[0][0]);
            }
            for(int i = 0; i < 6; i++){
               do{
                    n = randgen.nextInt(9);
                    r = randgen.nextInt(9);
                }while(spaces[n][r].equals(pacman) || spaces[n][r].equals(cheese[0]) || spaces[n][r].equals(cheese[1]) || spaces[n][r].equals(cheese[2]) || spaces[n][r].equals(cheese[3]) || spaces[n][r].equals(cheese[4]) || spaces[n][r].equals(cheese[5]) );

                cheese[i] = new Rectangle(spaces[n][r]);
                eaten[i] = false;
            }
            for(int i = 0; i < 6; i++){
                monster[i] = new Rectangle(spaces[0][0]);
            }
            for(int i = 0; i < 6; i++){
                do{
                    n = randgen.nextInt(10);
                    r = randgen.nextInt(10);
                }while(spaces[n][r].equals(pacman) || spaces[n][r].equals(cheese[0]) || spaces[n][r].equals(cheese[1]) || spaces[n][r].equals(cheese[2]) || spaces[n][r].equals(cheese[3]) || spaces[n][r].equals(cheese[4]) || spaces[n][r].equals(cheese[5]));

                monster[i] = new Rectangle(spaces[n][r]);
            }
            win = false;
            dead = false;
            mouth = true;
            t = 0;
            Mover mover = new Mover();
            Timer timer = new Timer(200, mover);
            
            repaint();
        }
        
        do{    
            if( c == 'w'){       
                if (pac1 == 0){ // wrap
                    pac1 = 9;
                }
                else{
                    pac1--;     // move up
                }
                pacman.setLocation((int)spaces[pac1][pac2].getX(), (int)spaces[pac1][pac2].getY()); // replace pacman rectangle with new location
            }
            else if( c == 'a'){ 
                if (pac2 == 0){     // wrap
                    pac2 = 9;
                }
                else{
                    pac2--;     // move left
                }
                pacman.setLocation((int)spaces[pac1][pac2].getX(), (int)spaces[pac1][pac2].getY());
            }
            else if( c == 'd'){
                if (pac2 == 9){     // wrap
                    pac2 = 0;
                }
                else{
                    pac2++;         // right
                }
                pacman.setLocation((int)spaces[pac1][pac2].getX(), (int)spaces[pac1][pac2].getY());
            }
            else if ( c == 's'){
                if (pac1 == 9){     // wrap
                    pac1 = 0;
                }
                else{
                    pac1++;            // move down
                }
                pacman.setLocation((int)spaces[pac1][pac2].getX(), (int)spaces[pac1][pac2].getY());
            }
        }while(dead && win);
        
        for(int i = 0; i < 6; i++){ // eat cheese if pacman rectangle is same as cheese rectangle
            if(pacman.equals(cheese[i]) && !dead){      // disables eating after death
                eaten[i] = true;
            }
        }

        if((pacman.equals(monster[0]) || pacman.equals(monster[1]) || pacman.equals(monster[2]) ||pacman.equals(monster[3]) ||pacman.equals(monster[4]) || pacman.equals(monster[5])) && win == false){
            dead = true;        // pacman dies if pacman Rectangle is equal to any enemy rectangle
        }

        if((eaten[0] && eaten[1] && eaten[2] && eaten[3] && eaten[4] && eaten[5]) && dead == false){
            win = true;         // victory after cheese is eaten
        }
        repaint();
    } 
    public void keyReleased (KeyEvent e) {} 

    public void keyTyped (KeyEvent e) {}        
}   // end class DrawPanel


class MySouth extends JPanel{ //Draws bottom rectangle panel
    public MySouth() {
        setPreferredSize(new Dimension(652, 50));   // set size of panel because South has to have specified size
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.red);
        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.BOLD, 16)); //Sets font 
        g.drawString("Directions: a = left, d = right, w = up, s = down, r = reset", 15, 35);//draws string
    }
}
