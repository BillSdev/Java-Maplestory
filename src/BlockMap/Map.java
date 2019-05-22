package BlockMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Map
{
	private double x;
	private double y;
	private int maxX;    //min and max for x and y to restrict the map from going of the full map 
	private int minX;
	private int maxY;
	private int minY;
	private double catchUp;    //small effect - the map slowly follows after the player instead of keeping the player in the middle at all times.
	private int[][] map;
	private int blockWidth;
	private int blockHeight;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	private BufferedImage blockset;
	private int numBlocksInLine;
	private Block[][] blocks;
	private int rowOffset;               //from what row we start to draw
	private int colOffset;
	private int numRowsToDraw;     //how many rows we want to draw 
	private int numColsToDraw;

	public Map(int blockWidth, int blockHeight)
	{
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		numRowsToDraw = (GamePanel.HEIGHT / blockHeight + 2);
		numColsToDraw = (GamePanel.WIDTH / blockWidth + 2);
	}

	//loading the array containing the block objects from the blockset
	public void loadBlocks(String s)
	{
		try
		{
			blockset = ImageIO.read(getClass().getResourceAsStream(s));
			numBlocksInLine = (this.blockset.getWidth() / this.blockWidth);
			blocks = new Block[2][this.numBlocksInLine];
			for (int col = 0; col < this.numBlocksInLine; col++)
			{
				BufferedImage subimage = this.blockset.getSubimage(col * blockWidth, 0, blockWidth, blockHeight);
				blocks[0][col] = new Block(subimage, 0);

				subimage = this.blockset.getSubimage(col * this.blockWidth, this.blockHeight, this.blockWidth, this.blockHeight);
				blocks[1][col] = new Block(subimage, 1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//loading the map from the map file 
	public void loadMap(String s)
	{
		try
		{
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());

			map = new int[this.numRows][this.numCols];
			width = (this.numCols * this.blockWidth);
			height = (this.numRows * this.blockHeight);

			maxX = width - GamePanel.WIDTH;
			minX = 0;
			maxY = height - GamePanel.HEIGHT;
			minY = 0;

			for (int row = 0; row < numRows; row++)
			{
				String line = br.readLine();
				String[] lineArr = line.split("\\s+");
				for (int col = 0; col < this.numCols; col++) {
					this.map[row][col] = Integer.parseInt(lineArr[col]);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public int getBlockWidth() { 	return this.blockWidth; }

	public int getBlockHeight() { 	return this.blockHeight; 	}

	public double getx() { return this.x; 	}

	public double gety() 	{ 	return this.y; }

	public int getWidth() { return this.width; }

	public int getHeight() { 	return this.height; }

	public int getType(int row, int col) {
		int block = this.map[row][col];
		return this.blocks[block / this.numBlocksInLine][block % this.numBlocksInLine].getType();
	}

	public void setTween(double d) { 	this.catchUp = d; 	}

	public void setPosition(double x, double y) {
		this.x = this.x + (x - this.x) * catchUp;
		this.y = this.y + (y - this.y) * catchUp;
//		this.x = x;
//		this.y = y;

		if(this.x < minX)
			this.x = minX;
		if(this.x > maxX)
			this.x = maxX;
		if(this.y < minY)
			this.y = minY;
		if(this.y > maxY)
			this.y = maxY;

		this.colOffset = ((int)this.x / this.blockWidth);
		this.rowOffset = ((int)this.y / this.blockHeight);
		System.out.println("colOffSet = " + this.colOffset + " rowOffSet = " + this.rowOffset);
	}


	public void draw(Graphics2D g) {
		for (int row = this.rowOffset; row < this.rowOffset + this.numRowsToDraw; row++) {   	  
			for (int col = this.colOffset; col < this.colOffset + this.numColsToDraw; col++) {  	  
				if (col >= this.numCols || row >= this.numRows || col < 0 || row < 0) 
					break;
				if (this.map[row][col] != 0) {
					int block = this.map[row][col];
					g.drawImage(this.blocks[block / this.numBlocksInLine][block % this.numBlocksInLine].getImage(),  col * this.blockWidth - (int)this.x,  row * this.blockHeight - (int)this.y,  null);
				}
			}
		}
	}

}
