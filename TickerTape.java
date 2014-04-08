/*  Hari Kaushik
	March 11, 2014
	TickerTape.java
	The objective .
*/

import java.awt.*;					//importing everything
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class TickerTape1 {
	JFrame frame;											//create a frame
	Timer timer1, timer2;												//create a timer for the moving of the letters and color changing
	MyPanel p1, p2, p3;											//three panels- two border panels and a center grid panel for the text
	TextPanel[] writing = new TextPanel[30];								//create an array of the 30 text panels 
	String str = "The only place success comes before work is in the dictionary.";						//quote to make the program move
	int heyo = 0;												//have a counter called heyo for every time the text moves
	public TickerTape1() {										//initialize the panels and create a timer for moving them
		// Create panels
		p1 = new MyPanel(Color.green);							//initialize the panels as "new MyPanels" so they have access to all the methods in myPanel
		p2 = new MyPanel(Color.white);
		p3 = new MyPanel(Color.blue);

		for (int a = 0; a < 30; a++) {							//initialize the array and make them accessible to all the methods in TextPanel so they can move
			writing[a] = new TextPanel();
		}

		ActionListener flash = new ActionListener() {				//create an ActionListener for flashing borders
			public void actionPerformed(ActionEvent e) {
				p1.setColor(getRandomColor());					//get random colors every time
				p3.setColor(getRandomColor());
							//repaint the panel
			}
		};
		ActionListener scroll = new ActionListener() {				//create an ActionListener for the scrolling animation
			public void actionPerformed(ActionEvent e) {
				if (++heyo > str.length()) {					//restart the quote if it is over
					heyo = 0;
				}

				String display = writeStr();					//call writeStr so you can draw the text
				for (int i = 0; i < 30; i++) {
					writing[i].setText(display.substring(i, i+1));//put each character in a panel
				}

				p2.redo();								//repaint the panel
			}
		};
		timer1 = new Timer(200, flash);	
		timer2 = new Timer(500, scroll);						//create the timer
		timer1.start();
		timer2.start();
	}

	public String writeStr() {									//use this method to get the display
		int reps = (int)(30/str.length());						//just in case it is less than 30 characters
		String s = "";											//create a string
		s = str.substring(heyo);								//draw the substring starting from heyo which changes everytime it moves
		for (int i = 0; i <= reps; i++) {						//keep drawing the "***" everytime the string is over
			s = s + "***" + str;
		}
		s = s.substring(0, 30);									//the first 30 characters -> panel
		return s;												//return s whenever writeStr() is called
	}
	
	public static void main(String[] args) { 						//main method
		TickerTape sl = new TickerTape();						//create object
		sl.Run();									//run the run method 
	}
	public void Run() {
		MakeBorderLayout(); 								// BorderLayout window
	}
	public void MakeBorderLayout() {											
		// Create a JFrame with BorderLayout
		frame = new JFrame("Ticker Tape"); // Create the JFrame

		// Set the layout to BorderLayout
		frame.setLayout(new BorderLayout());
		p1.setPreferredSize(new Dimension(1000, 25)); 
		p2.setPreferredSize(new Dimension(1000, 150));
		p3.setPreferredSize(new Dimension(1000, 25));
		
		GridLayout grid = new GridLayout(1, 30); // set grid layout with 30 cells
		p2.setLayout(grid);	
		
		for (int t = 0; t < 30; t++) {
			writing[t].setPreferredSize(new Dimension(33, 150)); // make a for loop for adding each of the characters (on separate panels) to the panel
			p2.add(writing[t]);
		}
		
		//to specify you do setPreferredSize
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the window if close is pressed
		frame.setSize(1000, 200); // setting appropriate size & location
		frame.setLocation(10, 10);
		
		 // Add panels to the frame
		frame.getContentPane().add(p1, BorderLayout.NORTH); //put p1 in north location
		frame.getContentPane().add(p2, BorderLayout.CENTER); //p2 on center (where i write everything)
		frame.getContentPane().add(p3, BorderLayout.SOUTH); //p3 on south
		
		 // Make the JFrame visible
		frame.setVisible(true);
	}

	public Color getRandomColor() { //not going to set 5 random colors so this generates diff colors for the flashing banners woooooooow
		int r = (int)(Math.random() * 255); //calculations for getting these random colors
		int g = (int)(Math.random() * 255); 
		int b = (int)(Math.random() * 255);
		Color co = new Color(r, g, b);
		return co;
	}
}

class MyPanel extends JPanel {  // border panels that only have background changing
	Color color;

	public MyPanel(Color c) {
		setBackground(c);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void setColor(Color d) {
		setBackground(d);
	}

	public void redo() {
		repaint();
	}
} // end class MyPanel

class TextPanel extends JPanel {
	Color color;
	String txt = " ";
	public TextPanel() {
		setBackground(Color.white);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(txt, 10, 75);
	}

	public void setText(String t) { // letter that is to be printed in each panel is passed through
		txt = t;
	}

	public void setColor(Color m) {
		setBackground(m);
	}

	public void redo() {
		repaint();
	}
}
