package BlockMap;

import java.awt.image.BufferedImage;

//object representing a certain tile out of the tiles in the tile set

public class Block
{
  private BufferedImage image;
  private int type;
  public static final int NORMAL = 0;    //NORMAL - basically decoration
  public static final int BLOCKED = 1;   //BLOCKED - map objects can interact with the tile.
  
  public Block(BufferedImage image, int type)
  {
    this.image = image;
    this.type = type;
  }
  
  public BufferedImage getImage()
  {
    return image;
  }
  
  public int getType()
  {
    return type;
  }
}
