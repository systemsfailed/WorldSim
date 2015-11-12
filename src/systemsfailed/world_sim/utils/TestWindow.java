	package systemsfailed.world_sim.utils;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import systemsfailed.world_sim.world.World;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JInternalFrame;

public class TestWindow {

	private JFrame frame;
	private JTextField widthField;
	private JTextField heightField;
	private JTextField seedField;
	private World world;
	private BufferedImage image;
	private ImageIcon icon;
	private JLabel imgLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestWindow window = new TestWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestWindow() {
		initialize();
		imgLabel = new JLabel();
		icon = new ImageIcon();
		imgLabel.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(imgLabel, BorderLayout.CENTER);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblWidth = new JLabel("Width(X): ");
		buttonPanel.add(lblWidth);
		
		widthField = new JTextField();
		buttonPanel.add(widthField);
		widthField.setColumns(10);
		
		JLabel lblHeighty = new JLabel("Height(Y): ");
		buttonPanel.add(lblHeighty);
		
		heightField = new JTextField();
		buttonPanel.add(heightField);
		heightField.setColumns(10);
		
		JLabel lblSeed = new JLabel("Seed: ");
		buttonPanel.add(lblSeed);
		
		seedField = new JTextField();
		buttonPanel.add(seedField);
		seedField.setColumns(10);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(seedField.getText().length() == 0)
					world = new World(Integer.parseInt(heightField.getText()), Integer.parseInt(widthField.getText()));
				else
					world = new World(seedField.getText(), Integer.parseInt(heightField.getText()), Integer.parseInt(widthField.getText()));
				
				printWorld(Integer.parseInt(heightField.getText()), Integer.parseInt(widthField.getText()));
				
			}
		});
		buttonPanel.add(btnGenerate);
	}
	
	private void printWorld(int height, int width)
	{
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		int[][] heightmap = world.getHeightmap();
		int[][] heatmap = world.getHeatmap();
		int largest = world.getMaxHeight();
		int maxtemp = world.getMaxTemp();
		
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int rgb = 0;
				
				if(heightmap[y][x] < largest * .65)
					rgb = 0x0000EE;
				else if(heightmap[y][x] < largest * .85)
					{
					if(heatmap[y][x] < maxtemp * .4)
						rgb = 0xFCFCFC;
					else if(heatmap[y][x] < maxtemp * .50)
						rgb = 0x2264800;
					else if(heatmap[y][x] < maxtemp * .75)
						rgb = 0x008B00;
					else
						rgb = 0xF4A460;
					}
				else if(heightmap[y][x] <= 255)
					rgb = 0x808A87;
				
				
				image.setRGB(x, y, rgb);
				
			}
		}
		icon.setImage(image);
		imgLabel.setIcon(icon);
		
		frame.repaint();
		frame.setVisible(true);
		
		System.out.println("Finished");
	}

}
