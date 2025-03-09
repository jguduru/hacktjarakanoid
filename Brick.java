import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

public abstract class Brick
{
   public int height;
   public int width;
   
   public Brick()
   {
      width = 50;
      
      height = 15;
   }
   public abstract void draw(Graphics myBuffer, int startX, int startY);
}

class WeakBrick extends Brick
{

   public WeakBrick()
   {
      super();
   }
   
   public void draw(Graphics myBuffer, int startX, int startY)
   {
      myBuffer.setColor(new Color(156, 115, 151));
      myBuffer.fillRect(startX, startY, width,  height);
      myBuffer.setColor(new Color(255, 255, 255));
      myBuffer.drawRect(startX, startY, width,  height);
   }
}

class StrongBrick extends Brick
{

   public StrongBrick()
   {
      super();
   }
   
   public void draw(Graphics myBuffer, int startX, int startY)
   {
      myBuffer.setColor(new Color(71, 52, 69));
      myBuffer.fillRect(startX, startY, width,  height);
      myBuffer.setColor(new Color(255, 255, 255));
      myBuffer.drawRect(startX, startY, width,  height);
   }
}