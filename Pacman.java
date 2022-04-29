/*
* Team 4: Mislav Čuljak and Sven Kovačević
* Course: ISTE-121
* Project Pacman - single player
* Date: 29/04/2022
*/
//@ASSESSME.INTENSITY:LOW
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import javafx.scene.image.PixelReader;

/**
 * PackmanGEOStarter with JavaFX and Thread
 */
// Whole code copied from this file because when I saved it, it was all gone.
public class Pacman extends Application {
   // Window attributes
   private Stage stage;
   private Scene scene;//pacman game
   private Scene scene2;//login screen
   private Scene scene3;//losing screen
   private Scene scene4;//winning screen
   private StackPane root;//stackpane

   private static String[] args;

   private final static String ICON_IMAGE = "pacman.gif"; // file with icon for a pacman
   //private final static String ICON_IMAGE2 = "pacman2.gif"; // file with icon for a pacman2
   private final static String GHOST1 = "mon1.gif"; // file with icon for a monster
   private final static String CHERRY = "cherry.png"; // file with icon for a cherry
   private final static String COIN = "coin.png";// copied from my partner's coin
   private int rot;// rotation
   private int iconWidth; // width (in pixels) of the icon
   private int iconHeight; // height (in pixels) or the icon
   private PacmanRacer racer = null; // array of racers
   //private PacmanRacer racer2 = null;
   private GhostRacer ghostRacerob;//GhostRacer object
   // private Cherry cherryob;
   // private Coins coinsob;
   private ArrayList<GhostRacer> ghost = new ArrayList<>();//arraylist ghost
   private ArrayList<Coins> coins = new ArrayList<>();//arraylist coins
   private ArrayList<Cherry> fruit = new ArrayList<>();//arraylist cherry

   private Button btnIn = null;//button for login for login screen
   private Button btnExit = null;//button for exit login screen
   private Button btnAgain = null;//button play again for losing screen
   private Button btnQuit = null;//button play quite for losing screen
   private Button btnAgain2 = null;//button play again for winning screen 
   private Button btnQuit2 = null;//button exit for the winning screen
   private VBox root2;//root for loging screen
   private VBox root3;//root for losing screen
   private VBox root4;//root for winning screen

   private Image pacman = null;//pacman image object
   //private Image pacman2 = null;
   private Image mon1 = null;//monster image object
   private Image coin = null;//coin image object
   private Image cherry = null;//cherry image object
   private int pacUD;//up down movement
   private int pacLR;//left right movement
   private Image img;//redmap image object
   private int lifePoints = 3;//lifepoints in the game
   private int coinPoints;//coin points
   Label lblLives;//label for lives
   Label lblPoints;//label for points

   private AnimationTimer timer; // timer to control animation

   public void startMenu() {//starting menu
      root2 = new VBox();//
      scene2 = new Scene(root2, 800, 500);//screen dimensions
      scene2.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());//getting the css file

      FlowPane fp1 = new FlowPane(8, 8);
      btnIn = new Button("Play");//play button
      btnIn.setAlignment(Pos.BOTTOM_CENTER);

      btnExit = new Button("Exit");//exit button
      btnExit.setAlignment(Pos.BOTTOM_CENTER);
      fp1.setAlignment(Pos.BOTTOM_CENTER);
      fp1.getChildren().addAll(btnIn, btnExit);
      root2.getChildren().addAll(fp1);//adding to root
      root2.setAlignment(Pos.BOTTOM_CENTER);
      root2.setId("picture");//css class to get image
      stage.setScene(scene2);//setting the scene
      stage.show();

      btnIn.setOnAction(e -> initializeScene());//press to get to the game
      btnExit.setOnAction(e -> System.exit(0));//exit the GUI
   }

   public void endMenu() {//losing menu
      root3 = new VBox();
      scene3 = new Scene(root3, 355, 591);//screen dimensions
      scene3.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

      FlowPane fp3 = new FlowPane(8, 8);
      btnAgain = new Button("Play Again");//play again button
      btnAgain.setAlignment(Pos.BOTTOM_CENTER);

      btnQuit = new Button("Quit");//exit button
      btnQuit.setAlignment(Pos.BOTTOM_CENTER);
      fp3.setAlignment(Pos.BOTTOM_CENTER);
      fp3.getChildren().addAll(btnAgain, btnQuit);
      root3.getChildren().addAll(fp3);//adding to root
      root3.setAlignment(Pos.BOTTOM_CENTER);
      root3.setId("gameover");//css to get image
      stage.setScene(scene3);
      stage.show();

      btnAgain.setOnAction(e -> startMenu());//press to get to the game
      btnQuit.setOnAction(e -> System.exit(0));// exiting the GUI
   }

   public void win() {//winning screen
      root4 = new VBox();
      scene4 = new Scene(root4, 355, 591);//screen dimensions
      scene4.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

      FlowPane fp4 = new FlowPane(8, 8);
      btnAgain2 = new Button("Play Again");//play again
      btnAgain2.setAlignment(Pos.BOTTOM_CENTER);

      btnQuit2 = new Button("Quit");//exit button
      btnQuit2.setAlignment(Pos.BOTTOM_CENTER);
      fp4.setAlignment(Pos.BOTTOM_CENTER);
      fp4.getChildren().addAll(btnAgain2, btnQuit2);
      root4.getChildren().addAll(fp4);
      root4.setAlignment(Pos.BOTTOM_CENTER);
      root4.setId("win");//css to get image
      stage.setScene(scene4);
      stage.show();

      btnAgain2.setOnAction(e -> startMenu());//going to start menu
      btnQuit2.setOnAction(e -> System.exit(0));// exiting the GUI
   }

   // main program
   public static void main(String[] _args) {
      args = _args;
      launch(args);
   }

   // start() method, called via launch
   public void start(Stage _stage) {
      // stage seteup
      stage = _stage;
      stage.setTitle("Game2D Starter");
      stage.setOnCloseRequest(
            new EventHandler<WindowEvent>() {
               public void handle(WindowEvent evt) {
                  System.exit(0);
               }
            });

      // root pane
      root = new StackPane();

      // create an array of Racers (Panes) and start
      startMenu();
   }

   // start the race
   public void initializeScene() {

      // Make an icon image to find its size
      try {
         pacman = new Image(new FileInputStream(ICON_IMAGE)); //pacman image
         // pacman2 = new Image(new FileInputStream(ICON_IMAGE2));
         img = new Image("redmap.png"); //map image
         mon1 = new Image(new FileInputStream(GHOST1)); //ghost image
         coin = new Image(new FileInputStream(COIN)); //coin image
         cherry = new Image(new FileInputStream(CHERRY)); //cherry img
      } catch (Exception e) {
         System.out.println("Exception: " + e);
         System.exit(1);
      }

      // Get pacman image size
      iconWidth = (int) pacman.getWidth();
      iconHeight = (int) pacman.getHeight();

       // Get image size
      //  iconWidth = (int) pacman2.getWidth();
      //  iconHeight = (int) pacman2.getHeight();

      // Get monster image size
      iconWidth = (int) mon1.getWidth();
      iconHeight = (int) mon1.getHeight();

      racer = new PacmanRacer(40, 40);//pacman position
      // racer2 = new PacmanRacer(440, 40);

      ghost.add(new GhostRacer(280, 90)); //adding ghost from arraylist
      ghost.add(new GhostRacer(380, 160));//adding ghost from arraylist
      ghost.add(new GhostRacer(480, 280));//adding ghost from arraylist

      coins.add(new Coins(212, 173));//adding coin from arraylist
      coins.add(new Coins(325, 123));//adding coin from arraylist
      coins.add(new Coins(121, 233));//adding coin from arraylist
      coins.add(new Coins(442, 35));//adding coin from arraylist
      coins.add(new Coins(148, 49));//adding coin from arraylist
      coins.add(new Coins(245, 223));//adding coin from arraylist
      coins.add(new Coins(321, 153));//adding coin from arraylist
      coins.add(new Coins(212, 415));//adding coin from arraylist
      coins.add(new Coins(521, 253));//adding coin from arraylist
      coins.add(new Coins(600, 275));//adding coin from arraylist

      fruit.add(new Cherry(170, 250));//adding fruit from arraylist
      fruit.add(new Cherry(600, 230));//adding fruit from arraylist



      lifePoints = 3;//number of lives
      coinPoints = 10;//point worth

      lblLives = new Label("LifePoints: " + 3);//label for LifePoints
      lblPoints = new Label("Coins: " + 0);//label for coins
      FlowPane fp2 = new FlowPane(8, 0);
      fp2.setAlignment(Pos.BOTTOM_LEFT);
      fp2.getChildren().addAll(lblPoints, lblLives);//adding to flowpane
//for loop to add all the ghosts to the root
      for (int i = 0; i < ghost.size(); i++) {
         root.getChildren().addAll(ghost.get(i));
      }
      for (int i = 0; i < coins.size(); i++) {//for loop to add all the coins to the root
         root.getChildren().addAll(coins.get(i));
      }
      for (int i = 0; i < fruit.size(); i++) {//for loop to add all the fruit to the root
         root.getChildren().addAll(fruit.get(i));
      }
      root.getChildren().addAll(racer, fp2); //adding pacman and labels to the root

      root.setId("pane");// copied from my partner's code

      // display the window
      scene = new Scene(root, 800, 500);
      scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
      stage.setScene(scene);
      stage.show();

      System.out.println("Starting race...");
//setting on key pressed
      scene.setOnKeyPressed(
            new EventHandler<KeyEvent>() {
               @Override
               public void handle(KeyEvent events) {
                  if (events.getCode() == KeyCode.LEFT) {//LEFT

                     if (!racer.checkLeftPix()) {//if false the pacman can not move
                        pacLR = 0;
                        racer.xAxs(pacLR);
                     } else {//if true he can move
                        pacLR = -6;
                        racer.xAxs(pacLR);
                     }
                     rot = 180;//rotation so the gif turns to the right side of the button pressed
                     racer.rotation(rot);

                  } else if (events.getCode() == KeyCode.RIGHT) {//RIGHT

                     if (!racer.checkRightPix()) {//if false the pacman can not move
                        pacLR = 0;
                        racer.xAxs(pacLR);
                     } else {//if true he can move
                        pacLR = 6;
                        racer.xAxs(pacLR);

                     }
                     rot = 0;//rotation so the gif turns to the right side of the button pressed
                     racer.rotation(rot);

                  } else if (events.getCode() == KeyCode.DOWN) {

                     if (!racer.checkDownPix()) {//if false the pacman can not move
                        pacUD = 0;
                        racer.yAxs(pacUD);
                     } else {//if true he can move
                        pacUD = 3;
                        racer.yAxs(pacUD);
                     }
                     rot = 90;//rotation so the gif turns to the right side of the button pressed
                     racer.rotation(rot);

                  } else if (events.getCode() == KeyCode.UP) {

                     if (!racer.checkUpperPix()) {//if false the pacman should not move
                        pacUD = 0;
                        racer.yAxs(pacUD);
                     } else {//if true he can move
                        pacUD = -3;
                        racer.yAxs(pacUD);
                     }
                     rot = 270;//rotation so the gif turns to the right side of the button pressed
                     racer.rotation(rot);

                  }
               }
            });
//when keys are released
      scene.setOnKeyReleased(
            new EventHandler<KeyEvent>() {
               @Override
               public void handle(KeyEvent events) {
                  if (events.getCode() == KeyCode.LEFT) { //going left
                     pacLR = 0;//pacman stays in place if the button is realeased
                     racer.xAxs(pacLR);

                  } else if (events.getCode() == KeyCode.RIGHT) { //going right
                     pacLR = 0;//pacman stays in place if the button is realeased
                     racer.xAxs(pacLR);

                  } else if (events.getCode() == KeyCode.DOWN) { //going down
                     pacUD = 0;//pacman stays in place if the button is realeased
                     racer.yAxs(pacUD);

                  } else if (events.getCode() == KeyCode.UP) { //going up
                     pacUD = 0;//pacman stays in place if the button is realeased
                     racer.yAxs(pacUD);

                  }
               }
            });

            // scene.setOnKeyPressed(
            //    new EventHandler<KeyEvent>() {
            //       @Override
            //       public void handle(KeyEvent events) {
            //          if (events.getCode() == KeyCode.A) {

            //             if (!racer2.checkLeftPix()) {
            //                pacLR = 0;
            //                racer2.xAxs(pacLR);
            //             } else {
            //                pacLR = -6;
            //                racer2.xAxs(pacLR);
            //             }
            //             rot = 180;
            //             racer2.rotation(rot);

            //          } else if (events.getCode() == KeyCode.D) {

            //             if (!racer2.checkRightPix()) {
            //                pacLR = 0;
            //                racer2.xAxs(pacLR);
            //             } else {
            //                pacLR = 6;
            //                racer2.xAxs(pacLR);
   
            //             }
            //             rot = 0;
            //             racer2.rotation(rot);

            //          } else if (events.getCode() == KeyCode.S) {

            //             if (!racer2.checkDownPix()) {
            //                pacUD = 0;
            //                racer2.yAxs(pacUD);
            //             } else {
            //                pacUD = 3;
            //                racer2.yAxs(pacUD);
            //             }
            //             rot = 90;
            //             racer2.rotation(rot);

            //          } else if (events.getCode() == KeyCode.W) {

            //             if (!racer2.checkUpperPix()) {
            //                pacUD = 0;
            //                racer2.yAxs(pacUD);
            //             } else {
            //                pacUD = -3;
            //                racer2.yAxs(pacUD);
            //             }
            //             rot = 270;
            //             racer2.rotation(rot);

            //          } 
            //       }
            //    });
            //    scene.setOnKeyReleased(
            // new EventHandler<KeyEvent>() {
            //    @Override
            //    public void handle(KeyEvent events) {
            //       if (events.getCode() == KeyCode.A) {
            //          pacLR = 0;
            //          racer2.xAxs(pacLR);

            //       } else if (events.getCode() == KeyCode.D) {
            //          pacLR = 0;
            //          racer2.xAxs(pacLR);

            //       } else if (events.getCode() == KeyCode.S) {
            //          pacUD = 0;
            //          racer2.yAxs(pacUD);

            //       } else if (events.getCode() == KeyCode.W) {
            //          pacUD = 0;
            //          racer2.yAxs(pacUD);

            //       } 
            //    }
            // });


       // Use an animation to update the screen
       timer = new AnimationTimer() {
         public void handle(long now) {
            racer.update();//calling PacmanRacer class
            // racer2.update();
            for(int i = 0; i<ghost.size();i++){
               ghost.get(i).update();//calling GhostRacer class with the for loop
            }
            // System.out.println("Groovy");
         }
      };

      // TimerTask to delay start of race for 2 seconds
      TimerTask task = new TimerTask() {
         public void run() {
            timer.start();
         }
      };
      Timer startTimer = new Timer();
      long delay = 1000L;
      startTimer.schedule(task, delay);

   }

   /**
    * Racer creates the race lane (Pane) and the ability to
    * keep itself going (Runnable)
    */
   protected class PacmanRacer extends Pane {
      private int racePosX = 0; // x position of the racer
      private int racePosY = 0; // y position of the racer
      private int raceROT = 0; // rotation position of the racer
      private ImageView pac; // a view of the icon ... used to display and move the image
      // private ImageView pac2;
      private int xAxis = 0;//x axis
      private int yAxis = 0;//y axis
      private Color readerRight;//color for right
      private Color readerLeft;//color for left
      private Color readerUp;//color for up
      private Color readerDown;//color for down
      private double colRight;//double for right
      private double colLeft;//double for left
      private double colUp;//double for up
      private double colDown;//double for down

//parameterized constructor
      public PacmanRacer(int racePosX, int racePosY) {
         this.racePosX = racePosX;
         this.racePosY = racePosY;
         pac = new ImageView(pacman);//adding pacman
         // pac2 = new ImageView(pacman2);
         this.getChildren().add(pac);//adding to pane
         // this.getChildren().add(pac2);
         pac.setTranslateX(racePosX);//translate to x
         pac.setTranslateY(racePosY);//translate to y
         // pac2.setTranslateX(racePosX);
         // pac2.setTranslateY(racePosY);
      }

      public void rotation(int rot) {//rotation
         raceROT = rot;
      }

      public void xAxs(int pacLR) {//xAxs
         this.xAxis = pacLR;
      }

      public void yAxs(int pacUD) {//yAxs
         this.yAxis = pacUD;
      }
//boolean for checking right pixels, if red false, if green true
      public boolean checkRightPix() {
         if (colRight == 1.0) {
            return false;
         } else
            return true;

      }
//boolean for checking left pixels, if red false, if green true
      public boolean checkLeftPix() {
         if (colLeft == 1.0) {
            return false;
         } else
            return true;
      }
//boolean for checking upper pixels, if red false, if green true
      public boolean checkUpperPix() {
         if (colUp == 1.0) {
            return false;
         } else
            return true;
      }
//boolean for checking down pixels, if red false, if green true
      public boolean checkDownPix() {
         if (colDown == 1.0) {
            return false;
         } else
            return true;
      }

      /**
       * update() method keeps the thread (racer) alive and moving.
       */
      public void update() {
         racePosX += pacLR;//+ left and right
         racePosY += pacUD;//+ down and up
         pac.setTranslateX(racePosX);//translate to posX
         pac.setTranslateY(racePosY);//translate to posY
         pac.setRotate(raceROT);//rotRot
         // pac2.setTranslateX(racePosX);
         // pac2.setTranslateY(racePosY);
         // pac2.setRotate(raceROT);
         readerRight = img.getPixelReader().getColor(this.racePosX + 60, this.racePosY + 30);//using pixel reader for right side
         readerLeft = img.getPixelReader().getColor(this.racePosX + 60, this.racePosY + 30);//using pixel reader for left side
         readerUp = img.getPixelReader().getColor(this.racePosX + 30, this.racePosY + 60);//using pixel reader for up
         readerDown = img.getPixelReader().getColor(this.racePosX + 30, this.racePosY + 60);//using pixel reader for down
         colRight = readerRight.getRed();//get red for right
         colLeft = readerRight.getRed();//get red for left
         colUp = readerRight.getRed();//get red for up
         colDown = readerRight.getRed();//get red for down

         
         for (int i = 0; i < coins.size(); i++) {
            int position = (int) Math.sqrt(Math.pow(racer.getRacePosX() - coins.get(i).getPosX(), 2)
                  + Math.pow(racer.getRacePosY() - coins.get(i).getPosY(), 2));//getting the position of the coin with racer positons and math equations
            if (position < 15) {
               coins.get(i).setVisible(false);//coins disappears
               lblPoints.setText("Coins:  " + coinPoints);//coins + points
               coinPoints++;
            }
            if(coinPoints == 130){//if it reaches the number, you win
               //the number is 130, becuse the coinpoints increment way too much
               win();//win menu
            }
         }

         for (int i = 0; i < fruit.size(); i++) {
            int position = (int) Math.sqrt(Math.pow(racer.getRacePosX() - fruit.get(i).getPosX(), 2)
                  + Math.pow(racer.getRacePosY() - fruit.get(i).getPosY(), 2));//getting the position of the fruti with racer positons and math equations
            if (position < 15) {
               fruit.get(i).setVisible(false);//if you eat the fruit with the pacman
               ghost.get(i).setVisible(false);//you insta kill a ghost
            }
         }

         for (int i = 0; i < ghost.size(); i++) {
            int position = (int) Math.sqrt(Math.pow(racer.getRacePosX() - ghost.get(i).getPosX(), 2)
                  + Math.pow(racer.getRacePosY() - ghost.get(i).getPosY(), 2));//getting the position of the ghost with racer positons and math equations
            if (position < 15) {
               racer.setRacePosX(40);//if you touch the ghost, you reset to your starting positon
               racer.setRacePosY(40);
               lifePoints--;//you lose one life
               lblLives.setText("Lives:  " + lifePoints); 
            }
            if(lifePoints == 0){//if your life reaches 0 you lose
               endMenu();//end menu
            }  
         }

         if (racePosX > 800)
            racePosX = 0;
         if (racePosY > 500)
            racePosY = 0;

      } // end update()

      //getters
      public int getRacePosX() {
         return this.racePosX;
      }
//getters
      public int getRacePosY() {
         return this.racePosY;
      }
//getters
      public void setRacePosX(int racePosX) {
         this.racePosX = racePosX;
      }
//getters
      public void setRacePosY(int racePosY) {
         this.racePosY = racePosY;
      }
//getters
   } // end inner class Racer

}
