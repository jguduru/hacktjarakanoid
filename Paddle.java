import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

public class Paddle
{
   private int xStart;
   private int xEnd;
   private int yStart;
   private int yEnd;
   
   public Paddle()
   {
      xStart = 200;
      xEnd = 300;
      yStart = 535;
      yEnd = 550;
   }
   
   public void moveLeft()
   {
      if(xStart > 0)
      {
      xStart -= 10;
      xEnd -= 10;
      }
   }
   
   public void moveRight()
   {
      if(xEnd < 500)
      {
      xStart += 10;
      xEnd += 10;
      }
   }
   
   public void draw(Graphics myBuffer)
   {
      myBuffer.setColor(new Color(255, 0, 0));
      myBuffer.fillRect(xStart, yStart, 100, 15);
   }
   
   public int getXStart()
   {
      return xStart;
   }
   
   public int getXEnd()
   {
      return xEnd;
   }
   
   public int getYStart()
   {
      return yStart;
   }
   
   public int getYEnd()
   {
      return yEnd;
   }
}