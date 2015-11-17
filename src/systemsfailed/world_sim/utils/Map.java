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

public class Map extends JPanel
{

	public static final int NUM_ROWS = 100;
	public static final int NUM_COLS = 150;
	public static final int GRID_SIZE = 10;
	private World world;
	private int height, width, maxHeight,maxTemp;
	private byte[] heatMap;
	private short[] heightmap;
	private int camerax, cameray;
	
	public Map()
	{
		super(new BorderLayout(10, 10));
		
		world = new World("TestWorld", 1000, 1000);
		height = world.getHeight();
		width = world.getWidth();
		heatMap = world.getHeatmap();
		heightmap = world.getHeightmap();
		maxHeight = world.getMaxHeight();
		maxTemp = world.getMaxTemp();
		camerax = 1000/2;
		cameray = 1000/2;
		
		for(int i = 0; i < commands.length; i++)
			registerKeyboardAction(action, commands[i], KeyStroke.getKeyStroke(commands[i])
					, JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		int preferedWidth = NUM_COLS * GRID_SIZE;
		int preferedHeight = NUM_ROWS * GRID_SIZE;
		setPreferredSize(new Dimension(preferedWidth, preferedHeight));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		int rectWidth = getWidth()/NUM_COLS;
		int rectHeight = getHeight()/NUM_ROWS;
		
		for(int i = 0; i < NUM_ROWS; i++)
			for(int j = 0; j < NUM_COLS; j++)
			{
				int y = i * rectWidth;
				int x = j * rectHeight;
				
				
				
				Color color = new Color(getTerrainColor(camerax + j,cameray + i));
				g.setColor(color);
				g.fillRect(x,y,rectWidth,rectHeight);
				g.setColor(Color.BLACK);
				g.drawRect(x,y,rectWidth,rectHeight);
			}
				
	}
	
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
	
	private String[] commands =
		{
			"UP",
			"DOWN",
			"LEFT",
			"RIGHT"
		};
	
	private ActionListener action = new ActionListener()
	{
		public void actionPerformed(ActionEvent ae)
		{
			String command = (String) ae.getActionCommand();
		
			if(command.equals(commands[0]))
				cameray -= 10;
			if(command.equals(commands[1]))
				cameray += 10;
			if(command.equals(commands[2]))
				camerax -= 10;
			if(command.equals(commands[3]))
				camerax += 10;
			
			if(cameray < 0)
				cameray = 0;
			if(cameray > 900)
				cameray = 900;
			if(camerax < 0)
				camerax = 0;
			if(camerax > 850)
				camerax = 850;
			
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
			Map map = new Map();
			frame.setSize(1920, 1080);
			frame.add(map, BorderLayout.CENTER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		}
		
		});
		
	}
	
}
