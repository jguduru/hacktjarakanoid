import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ArkanoidDriver
{
   public static JFrame m;
   public static void main(String[] args)
   {
      m = new JFrame("Menu");
      m.setSize(500, 600);
      m.setLocation(100,50);
      m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      m.setContentPane(new MenuPanel());
      m.setVisible(true);       
   }
}

class MenuPanel extends JPanel
{
   public static JFrame f;
   public MenuPanel()
   {
      setLayout(new GridLayout(3, 3));
      setBackground(new Color(46, 42, 250));
         
      JLabel title = new JLabel("WELCOME TO ARKANOID!");
      title.setFont(new Font("Serif", Font.BOLD, 20));
      title.setHorizontalAlignment(SwingConstants.CENTER);
      add(title, BorderLayout.NORTH);
      
      JButton menuButton = new JButton("PLAY GAME");
      menuButton.setBackground(new Color(240, 180, 229));
      menuButton.setForeground(new Color(240, 180, 229));
      menuButton.addActionListener(new Listener_menuButton());
      add(menuButton);
   }
   
   private class Listener_menuButton implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         MenuPanel.f = new JFrame("Arkanoid");
         f.setSize(500, 600);
         f.setLocation(100,50);
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         f.setContentPane(new ArkanoidPanel());
         f.setVisible(true);
         ArkanoidDriver.m.setVisible(false);
      }
   }
}

class ArkanoidPanel extends JPanel
{
   private ScorePanel score = new ScorePanel();
   private Ball ball;
   private Timer t;
   private Paddle paddle;
   private BufferedImage myImage;
   private Graphics myBuffer;
   private int dimensions = 500;
   
   private int[][] xCoordinates = new int[2][50];
   private int[][] yCoordinates = new int[2][50];
   private int[] strengths;
   private JFrame g;
   
   private int i = 0;
   public void setCoords()
   {
      for (int x = 0; x < 10; x++)
      {
         for (int y = 0; y < 5; y++)
         {
            xCoordinates[0][i] = x*50;
            xCoordinates[1][i] = x*50+50;
            yCoordinates[0][i] = y*15+100;
            yCoordinates[1][i] = y*15+115;
            i++;
         }
      }
   }
   
   private Color background = new Color(52, 232, 235);
      
   private WeakBrick wb = new WeakBrick();
   private StrongBrick sb = new StrongBrick();
   
   public ArkanoidPanel()
   {
      try{
      Scanner infile = new Scanner (new File("strengths.txt"));
      infile.useDelimiter(", ");
      strengths = new int[50];
      for(int i = 0; i < 50; i++)
      {
         strengths[i] = Integer.parseInt(infile.next());
      }
      infile.close();
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      
      myImage = new BufferedImage(dimensions, 600, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();
      myBuffer.setColor(background);
      myBuffer.fillRect(0, 0, dimensions, 600);
      
      paddle = new Paddle();
      ball = new Ball(paddle, xCoordinates, yCoordinates, strengths, score);
      
      t = new Timer(5, new AnimationListener());
      t.start();
      setCoords();
      
      addKeyListener(new Key());
      setFocusable(true);
      
      add(score);
   }
   
   public void paintComponent(Graphics g)
   {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }
   
   public void animate()
   {
      myBuffer.setColor(background);
      myBuffer.fillRect(0, 0, dimensions, 600);
   
      ball.move(myBuffer);
      if (ball.ifHitFloor())
      {
         ball.setCoords();
         g = new JFrame("LOSE PANEL");
         g.setSize(500, 600);
         g.setLocation(100,50);
         g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         g.setContentPane(new GameOverPanel());
         g.setVisible(true);
         MenuPanel.f.setVisible(false);
      }
      int i = 0;
      for (int x = 0; x < 50; x++)
      {
         
         if (strengths[x] == 0)
         {
            wb.draw(myBuffer, xCoordinates[0][x], yCoordinates[0][x]);
            i++;
         }
         if (strengths[x] == 1)
         {
            sb.draw(myBuffer, xCoordinates[0][x], yCoordinates[0][x]);
            i++;
         }
      }
      if(i == 0)
      {
         ball.setCoords();
         JFrame h = new JFrame("WIN PANEL");
         h.setSize(500, 600);
         h.setLocation(100,50);
         h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         h.setContentPane(new WinPanel());
         h.setVisible(true);
         g.setVisible(false);
      }
      
      paddle.draw(myBuffer);
      
      score.update();
      
      repaint();
   }
   
   private class AnimationListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         animate();
      }
   }
   
   private class Key extends KeyAdapter
   {
      public void keyPressed(KeyEvent e)
      {
         // //Check for left and right key
         if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            paddle.moveRight();
         else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            paddle.moveLeft();
      }
   }
}

class GameOverPanel extends JPanel
{
   public GameOverPanel()
   {
      setLayout(new GridLayout(3, 3));
      setBackground(new Color(50, 50, 50));
         
      JLabel title = new JLabel("YOU LOST!");
      title.setFont(new Font("Serif", Font.BOLD, 20));
      title.setHorizontalAlignment(SwingConstants.CENTER);
      add(title, BorderLayout.NORTH);
   }
}

class WinPanel extends JPanel
{
   public WinPanel()
   {
      setLayout(new GridLayout(3, 3));
      setBackground(new Color(34, 214, 242));
         
      JLabel title = new JLabel("YOU WON!");
      title.setFont(new Font("Serif", Font.BOLD, 20));
      title.setHorizontalAlignment(SwingConstants.CENTER);
      add(title, BorderLayout.NORTH);
   }
}

class ScorePanel extends JPanel
{
   private JTextField scoreField;
   private int score;
   public ScorePanel()
   {
      scoreField = new JTextField("0");
      scoreField.setHorizontalAlignment(SwingConstants.CENTER);
      scoreField.setPreferredSize(new Dimension(100, 50));
      score = 0;
      add(scoreField);
   }
   
   public void increaseScore(int amt)
   {
      score += amt;
   }
   
   public void update()
   {
      String s = "" + score;
      scoreField.setText(s);
   }
}