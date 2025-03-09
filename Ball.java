import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Ball
{
   private int xCoord;
   private int yCoord;
   private int xDir;
   private int yDir;
   private Paddle p;
   private int[][] arrX;
   private int[][] arrY;
   private int[] arr;
   
   private ScorePanel score;
   
   public Ball(Paddle paddle, int[][] arrXcoords, int[][] arrYcoords, int[] arrStrength, ScorePanel s)
   {
      p = paddle;
      arrX = arrXcoords;
      arrY = arrYcoords;
      arr = arrStrength;
      yCoord = 300;
      xCoord = 250;
      
      xDir = 1;
      yDir = -1;
      
      score = s;
   }
   
   public void move(Graphics myBuffer)
   {
      int speed = 3;
      int xMove = xDir * speed;
      int yMove = yDir * speed;
      
      xCoord = xCoord + xMove;
      yCoord = yCoord + yMove;
      
      if(ifHitPaddle())
         onHitPaddle();
      if(ifHitWall())
         onHitWall();
      if(ifHitCeiling())
         onHitCeiling();
      if(ifHitBrick())
         onHitBrick();
         
      draw(myBuffer);
   }
   
   public boolean ifHitPaddle()
   {
      if (xCoord >= p.getXStart() && xCoord <= p.getXEnd() && yCoord >= p.getYStart() && yCoord <= p.getYEnd())
      {
         return true;
      }
      return false;
   }
   
   public boolean ifHitWall()
   {
      if (xCoord >= 500 || xCoord <= 0)
      {
         return true;
      }
      return false;
   }
   
   public boolean ifHitCeiling()
   {
      if (yCoord <= 0)
      {
         return true;
      }
      return false;
   }
   
   public boolean ifHitBrick()//This does everything but change the direction of the ball
   {
      for(int x = 0; x < arr.length; x++)
      {
         if(xCoord >= arrX[0][x] && xCoord <= arrX[1][x] && yCoord >= arrY[0][x] && yCoord <= arrY[1][x])
         {
            if (arr[x] == 0)
            {
               score.increaseScore(100);
               arr[x] = arr[x] - 1;
               arrX[0][x] = -100;
               arrX[1][x] = -100;
               arrY[0][x] = -100;
               arrY[1][x] = -100;
            } else {
               score.increaseScore(50);
               arr[x] = arr[x] - 1;
            }
            return true;
         }
      }
      return false;
   }
   
   public boolean ifHitFloor()
   {
      if (yCoord > 600)
      {
         return true;
      }
      return false;
   }
   
   public void onHitPaddle()
   {
      yDir = -1 * yDir;
   }
   
   public void onHitWall()
   {
      xDir = -1 * xDir;
   }
   
   public void onHitBrick()
   {
      yDir = -1 * yDir;
   }
   
   public void onHitCeiling()
   {
      yDir = -1 * yDir;
   }
   
   public void draw(Graphics myBuffer)
   {
      myBuffer.setColor(new Color(2, 97, 250));
      myBuffer.fillOval(xCoord, yCoord, 10, 10);
   }
   
   public void setCoords()
   {
      xCoord = 250;
      yCoord = 300;
      yDir = 0;
      xDir = 0;
   }
}