/*Ahmed Hatem Ghazy Mostafa
Data Management Alexandria 
Intake 44*/

//Project Title: Simple Paint Brush in Java 

/*Project Description:
----------------------
The "Simple Paint Brush" is a Java-based drawing program that allows 
users to create and manipulate various shapes on an Applet. The application 
provides a straightforward graphical user interface (GUI) with buttons for 
selecting different drawing modes and colors, making it user-friendly and 
accessible for individuals of all skill levels.*/

/*Usage Instructions:
---------------------
1- Launch the application.
2- Choose a drawing mode by clicking on the corresponding button (Rectangle, Line, Oval, Eraser, or Pencil).
3- Select a color by clicking on the Red, Blue, or Green button.
4- Optionally, toggle the "Fill Shape" checkbox to determine whether shapes should be filled.
5- Click and drag the mouse to draw shapes on the Applet.
6- Use the "Undo" button to remove the last drawn shape.
7- Click "Clear All" to reset the Applet.*/

import java.awt.*;												//Importing the needed packages
import java.applet.Applet;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.ArrayList;

public class Paint extends Applet{
	//Defining variables
	private int startX, startY, endX, endY;  					//Co-ordinates of the drawn shape
	private boolean isSolid = false;		 					//Variable to check if the shape is solid
	protected int currentMode = 2;					 			//Variable to set the current mode selected by the user
	protected Color currentColor = Color.black;					//Initiating the current color at black
	protected Checkbox fillBox;									//Initiating the checkbox
	protected ArrayList <Shape> shape = new ArrayList<>(); 		//Making an arraylist to save the shapes in it
	Shape currentShape = null;									//Making a reference that points to null

	//Current Modes
	public static final int rect = 1;
	public static final int line = 2;
	public static final int oval = 3;
	public static final int eraser = 4;
	public static final int pencil = 5;	
	
	public void init(){
	
		//Rectangle Button
		Button btnRectangle = new Button("Rectangle");
		btnRectangle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentMode = 1;
			}
		});
		add(btnRectangle);	
		
		//Line Button
		Button btnLine = new Button("Line");
		btnLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentMode = 2;
			}
		});
		add(btnLine);
		
		//Oval Button
		Button btnOval = new Button("Oval");
		btnOval.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentMode = 3;
			}
		});
		add(btnOval);
		
		//Red Button
		Button btnRed = new Button("Red");
		btnRed.setBackground(Color.red);
		btnRed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.red;
			}
		});
		add(btnRed);
		
		//Blue Button
		Button btnBlue = new Button("Blue");
		btnBlue.setBackground(Color.blue);
		btnBlue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.blue;
			}
		});
		add(btnBlue);
		
		//Green Button
		Button btnGreen = new Button("Green");
		btnGreen.setBackground(Color.green);
		btnGreen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.green;
			}
		});
		add(btnGreen);
				
		//Pencil
		Button btnPencil = new Button("Pencil");
		btnPencil.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentMode = 5;
			}
		});
		add(btnPencil);
		
		//Eraser
		Button btnEraser = new Button("Eraser");
		btnEraser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentMode = 4;
			}
		});
		add(btnEraser);
		
		//Clear All Button
		Button btnClearAll = new Button("Clear All");
		btnClearAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				shape.clear();
				repaint();
			}
		});
		add(btnClearAll);
		
		//Undo Button
		Button btnUndo = new Button("Undo");
		btnUndo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(shape.size() > 0){
					shape.remove(shape.size() - 1);
					repaint();
				}
			}
		});
		add(btnUndo);
		
		//Checkbox Button
		Checkbox fillBox = new Checkbox("Fill Shape", false);
		fillBox.addItemListener(new CheckboxListener());
		add(fillBox);
		
		//Registering the mouse listeners
		addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseListener());
	}
	
	//The checkbox listener class
	class CheckboxListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			// Implementation of the itemStateChanged method
			if (e.getStateChange() == ItemEvent.SELECTED) {
                isSolid = true;
            } else {
                isSolid = false;
            }
		}
	}
		
	public void paint(Graphics g){
		//We loop on every shape in the array list and draw it according to it's draw method specified in the child class
		for (Shape s: shape){
			s.draw(g);
		}
	}
		
	//The mouse listener class 
	class MyMouseListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			//Starting drawing when the mouse is pressed
			startX = e.getX();
			startY = e.getY();	
			}
		
		public void mouseDragged(MouseEvent e){
			//Drawing when the mouse is dragged
			endX = e.getX();
			endY = e.getY();
			
			switch (currentMode) {
			case 4:
				shape.add(currentShape = new Eraser(startX, startY, endX, endY));
				startX = endX;
				startY = endY;
				break;
			case 5:
				shape.add(currentShape = new Pencil(currentColor, startX, startY, endX, endY));
				startX = endX;
				startY = endY;					
				break;		
			}
			
			//repainting
			repaint();
			}
		
		public void mouseReleased(MouseEvent e){
			endX = e.getX();
			endY = e.getY();
					
			/*Switch on the current mode being pressed by the user 
			and saving an oject to the shape array list accordingly*/
			switch (currentMode) {
				case 1:
					currentShape = new Rectangle(currentColor, isSolid, startX , startY , endX , endY);
					break;
				case 2:
					currentShape = new Line(currentColor, isSolid, startX , startY , endX , endY);
					break;
				case 3:
					currentShape = new Oval(currentColor , isSolid, startX , startY , endX , endY);
					break;
				case 4:
					currentShape = new Eraser(startX, startY, endX, endY);
					break;
				case 5:
					currentShape = new Pencil(currentColor , startX, startY, endX, endY);
					break;	
			}		
			
			if(currentShape != null){
				shape.add(currentShape);
				repaint();
			}
		}
			
	}
} 

	//The Parent class (Shape) 
	abstract class Shape{
		// Shape Dimensions
		protected int startX, startY, endX, endY;
		
		//Abstarct the method for the shapes to override with it's own draw method
		abstract void draw(Graphics g);
		
		//Constructing the Parent Shape
		public Shape(){}
		}

	class Rectangle extends Shape{
		// Fill the Shape or not
		protected boolean isSolid = false;
	
		//Referencing the color
		Color color;
		
		//Constructing the Rectangle
		public Rectangle(Color color, boolean isSolid, int startX , int startY , int endX , int endY){
			this.color = color;
			this.isSolid = isSolid;
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
		}
	
		//Overriding the draw method
		public void draw(Graphics g){
			
			// Calculate width and height
			int width = Math.abs(endX - startX);
			int height = Math.abs(endY - startY);

			// Calculate starting point for drawing
			int x = Math.min(startX, endX);
			int y = Math.min(startY, endY);
			
			//Setting the color 
			g.setColor(color);

			// If the user did not check the solid box, then draw a rectangle
			if (!isSolid) {
				g.drawRect(x, y, width, height);
			} else {
				g.fillRect(x, y, width, height);
			}
		}
	}
	
	class Oval extends Shape{
		// Fill the Shape or not
		protected boolean isSolid = false;
	
		//Referencing the color
		Color color;
		
		//Constructing the Oval
		public Oval(Color color, boolean isSolid, int startX , int startY , int endX , int endY){
			this.color = color;
			this.isSolid = isSolid;
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
		}
	
		//Overriding the draw method
		public void draw(Graphics g){
			// Calculate width and height
			int width = Math.abs(endX - startX);
			int height = Math.abs(endY - startY);

			// Calculate starting point for drawing
			int x = Math.min(startX, endX);
			int y = Math.min(startY, endY);
			
			//Setting the color 
			g.setColor(color);
					
			// If the user did not check the solid box, then draw an oval
			if (!isSolid) {
				g.drawOval(x, y, width, height);
			} else {
				g.fillOval(x, y, width, height);
			}
		}
	}
	
	class Line extends Shape{
		// Fill the Shape or not
		protected boolean isSolid = false;
	
		//Referencing the color
		Color color;
		
		//Constructing the Line
		public Line(Color color, boolean isSolid, int startX , int startY , int endX , int endY){
			this.color = color;
			this.isSolid = isSolid;
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
		}
	
			//Overriding the draw method
			public void draw(Graphics g){
			//Setting the color 
			g.setColor(color);
		
			//Drawing the line
			g.drawLine(startX, startY, endX, endY);
			}
		}
	
	class Eraser extends Shape{
		// Fill the Shape or not
		protected boolean isSolid = false;
	
		//Referencing the color
		Color color;
		
		//Constructing the Eraser
		public Eraser(int startX, int startY, int endX, int endY){
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
		}
		
		//Drawing the eraser
		public void draw(Graphics g){
			g.setColor(Color.white);
			g.fillRect(startX, startY, 30, 30);
		}
		
	}
	
	class Pencil extends Shape{
		// Fill the Shape or not
		protected boolean isSolid = false;
	
		//Referencing the color
		Color color;
		
		//Constructing the Eraser
		public Pencil(Color color, int startX, int startY, int endX, int endY){
			this.color = color;
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
		}
		
		// Drawing the Pencil
		public void draw(Graphics g){
			g.setColor(color);
			g.drawLine(startX, startY, endX, endY);
		}	
	}
