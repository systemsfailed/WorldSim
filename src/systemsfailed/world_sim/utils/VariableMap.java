package systemsfailed.world_sim.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import systemsfailed.world_sim.world.World;

public class VariableMap extends JPanel
{
	private World world;
	private int height, width, maxHeight,maxTemp,
	numRows, numCols, camerax, cameray;
	private byte[] heatMap;
	private short[] heightmap;
	private boolean gridMarks;
	
	/**
	 * Creates a map representation of a given world
	 * @param world
	 * 	The world object to be painted in map form
	 * @param numRows
	 * 	The number of grid squares to be displayed at once vertically
	 * @param numCols
	 * 	The number of grid squares to be displayed at one horizontally
	 * @param gridSize
	 * 	The starting size, in pixels of the grid squares
	 */
	public VariableMap(World world, int numRows, int numCols, int gridSize)
	{
		super(new BorderLayout(10, 10));
		
		this.world = world;
		height = world.getHeight();
		width = world.getWidth();
		heatMap = world.getHeatmap();
		heightmap = world.getHeightmap();
		maxHeight = world.getMaxHeight();
		maxTemp = world.getMaxTemp();
		this.numRows = numRows;
		this.numCols = numCols;
		camerax = width/2;
		cameray = height/2;
		gridMarks = true;
		
		for(int i = 0; i < commands.length; i++)
			registerKeyboardAction(action, commands[i], KeyStroke.getKeyStroke(commands[i])
					, JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		int preferedWidth = numCols * gridSize;
		int preferedHeight = numRows * gridSize;
		setPreferredSize(new Dimension(preferedWidth, preferedHeight));
	
	
	}
	
	/**
	 * Toggles the grid squares between visible and non-visible
	 */
	public void toggleGridMarks()
	{
		gridMarks = !gridMarks;
	}
	
	/**
	 * Currently testing a way to zoom the view of the map out
	 */
	public void zoomOut()
	{
		if((numRows < height - 10) & (numCols < width - 10))
		{
			numRows += 10;
			numCols += 10;
			if(numRows > height)
				numRows = height;
			if(numCols > width)
				numCols = width;
		}
		
		System.out.printf("w:%d h:%d\n",numCols, numRows);
		
		repaint();
		
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		//Determines the size of the grid square based on how many are displayed and screen size
		int rectWidth = getWidth()/numCols;
		int rectHeight = getHeight()/numRows;
		
		for(int i = 0; i < numRows; i++)
			for(int j = 0; j < numCols; j++)
			{
				int y = i * rectWidth;
				int x = j * rectHeight;
				
				
				//Determines what this locations terrain is and paints accordingly
				Color color = new Color(getTerrainColor(camerax + j,cameray + i));
				g.setColor(color);
				g.fillRect(x,y,rectWidth,rectHeight);
				
				if(gridMarks) //Draws Grid marks if toggled true
				{
					g.setColor(Color.BLACK);
					g.drawRect(x,y,rectWidth,rectHeight);
				}
			}
				
	}
	
	/**
	 * 
	 * @param x
	 * The X coordinate of the chosen cell within the map
	 * @param y
	 * The Y coordinate of the chosen cell within the map
	 * @return
	 * Returns an integer RGB value for use in painting the map
	 */
	private int getTerrainColor(int x, int y)
	{
		if(heightmap[x + y * width] < maxHeight * .65)
			return 0x0000EE;
		else if(heightmap[x + y * width] < maxHeight * .85)
			{
			if(heatMap[x + y * width] < maxTemp * .4)
				return 0xFCFCFC;
			else if(heatMap[x + y * width] < maxTemp * .50)
				return 0x2264800;
			else if(heatMap[x + y * width] < maxTemp * .75)
				return 0x008B00;
			else
				return 0xF4A460;
			}
		else if(heightmap[x + y * width] <= 255)
			return 0x808A87;
		
		System.out.printf("Something went wrong!");
		return 0x0;
		
	}
	
	/*
	 * List of all of the commands used on the map
	 */
	private String[] commands =
		{
			"UP",
			"DOWN",
			"LEFT",
			"RIGHT",
			"SPACE"
		};
	
	private ActionListener action = new ActionListener()
	{
		public void actionPerformed(ActionEvent ae)
		{

			String command = (String) ae.getActionCommand();
			
			if(command.equals(commands[4]))
				zoomOut();
			
			//Checks for map scrolling keys
			if(command.equals(commands[0]))
				cameray -= height/100;
			if(command.equals(commands[1]))
				cameray += height/100;
			if(command.equals(commands[2]))
				camerax -= height/100;
			if(command.equals(commands[3]))
				camerax += height/100;
			
			//Keeps the camera within the bounds of the map
			if(cameray < 0)
				cameray = 0;
			if(cameray > height - numRows)
				cameray = height - numRows;
			if(camerax < 0)
				camerax = 0;
			if(camerax > width - numCols)
				camerax = width - numCols;
			
			repaint();
		
		}
	};
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable(){
		public void run()
		{
			JFrame frame = new JFrame();
			frame.setLayout(new BorderLayout(10,10));
			VariableMap map = new VariableMap(new World("TestWorld", 600, 600), 100, 100, 10);
			frame.setSize(1920, 1080);
			frame.add(map, BorderLayout.CENTER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		}
		
		});
		
	}
	
}
