package Entity;

import BlockMap.Map;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MapObject
{
	//Objects that interact with the map (collision etc) and with themselves.

	protected Map map;
	protected int tileWidth;
	protected int tileHeight;
	protected double xmap;
	protected double ymap;
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected int width;           //drawing width
	protected int height;         //drawing height
	protected int cwidth;        //collision width
	protected int cheight;      //collision height
	protected int currRow;
	protected int currColumn;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;

	public MapObject(Map tm)
	{
		map = tm;
		tileWidth = map.getBlockWidth();
		tileHeight = map.getBlockHeight();
	}

	public boolean intersects(MapObject o)
	{
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}

	public Rectangle getRectangle()
	{
		return new Rectangle((int)x - cwidth / 2, (int)y - cheight / 2, cwidth, cheight);
	}

	//checking what corners colliding with a block so we can know to fall\stop falling etc..
	public void checkCornersForColl(double x, double y)
	{
		//find all sides
		int leftSide = (int)(x - cwidth / 2) / tileWidth;
		int rightSide = (int)(x + cwidth / 2 - 1) / tileWidth;
		int topSide = (int)(y - cheight / 2) / tileHeight;
		int bottomSide = (int)(y + cheight / 2 - 1) / tileHeight;

		//update if corners of the 4 sides collide.
		topLeft = (map.getType(topSide, leftSide) == 1);
		topRight = (map.getType(topSide, rightSide) == 1);
		bottomLeft = (map.getType(bottomSide, leftSide) == 1);
		bottomRight = (map.getType(bottomSide, rightSide) == 1);
	}

	public void checkBlockMapCollision()
	{
		currColumn = ((int)x / tileWidth);
		currRow = ((int)y / tileHeight);
		xdest = (x + dx);
		ydest = (y + dy);
		xtemp = x;
		ytemp = y;

		checkCornersForColl(x, ydest);   //first check if we can continue out vertical movement

		if (dy > 0) {    //if falling
			if ((bottomLeft) || (bottomRight)){    //if floor below, stop
				dy = 0;
				ytemp = ((currRow + 1) * tileHeight - cheight / 2);
				falling = false;
			}
			else
				ytemp += dy;
		}
		if (dy < 0) {    //if jumping
			if ((topLeft) || (topRight)){    //if ceiling above, stop going higher.
				dy = 0;
				ytemp = (currRow * tileHeight + cheight / 2);
			}
			else
				ytemp += dy;
		}
		checkCornersForColl(xdest, y);   //now checking if we can continue horizontal movement
		if (dx > 0) {    //if moving right
			if ((topRight) || (bottomRight)) {   //if colliding on the right, stop   
				dx = 0;
				xtemp = ((currColumn + 1) * tileWidth - cwidth / 2);
			}
			else
				xtemp += dx;
		}
		if (dx < 0) {   //if moving left
			if ((topLeft) || (bottomLeft)) {    //colliding on left -> stop
				dx = 0;
				xtemp = (currColumn * tileWidth + cwidth / 2);
			}
			else
				xtemp += dx; 
		}
		if (!falling){         //if not falling test if floor below. if now start falling.
			checkCornersForColl(x, ydest + 1);
			if ((!bottomLeft) && (!bottomRight)) {
				falling = true;
			}
		}
	}

	public int getWidth(){ return width;}

	public int getHeight() {return height;}

	public int getCWidth(){return cwidth;}

	public int getCHeight(){return cheight;}

	public double getx(){ return x;}

	public double gety(){return y;}

	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}

	public void setMapPosition(){
		xmap = map.getx();
		ymap = map.gety();
	}

	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}

	public void setLeft(boolean b) {   left = b; }

	public void setRight(boolean b){right = b;}

	public void setUp(boolean b){up = b;}

	public void setDown(boolean b){down = b;}

	public void setJumping(boolean b){ jumping = b;}

	public boolean notOnScreen(){return (x - xmap + width < 0) || (y - ymap + height < 0) || (x - xmap - width > 1366) || (y - ymap - height > 768); }

	public void draw(Graphics2D g) {
		if (!facingRight) 
			g.drawImage(animation.getImage(),  (int)(x - xmap - width / 2), (int)(y - ymap - height / 2), null);
		else 
			g.drawImage(animation.getImage(), (int)(x - xmap - width / 2 + width), (int)(y - ymap - height / 2), -width, height,  null);

	}
}
