
/*
* Team 4: Mislav Čuljak and Sven Kovačević
* Course: ISTE-121
* Project Pacman - single player
* Date: 29/04/2022
*/
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
//class for ghosts
 public class GhostRacer extends Pane {
    public int speed = 3; //speed of ghosts
    private int posX; // x position of the ghost
    private int posY; // y position of the ghost
    private ImageView monImg; // a view of the icon ... used to display and move the image
    private Image mon1 = new Image("mon1.gif");//ghost image
  

    //parameterized constructor
    public GhostRacer(int posX, int posY) {
       // Draw the icon for the racer
       this.posX = posX;
       this.posY = posY;
       monImg = new ImageView(mon1);//adding the ghost image
       this.getChildren().add(monImg);//adding to pane
       monImg.setTranslateX(posX);//translate posx
       monImg.setTranslateX(posY);//translate posy

    }
    /**
     * update() method keeps the thread (racer) alive and moving.
     */
    public void update() {
//if statement
      if(posX > 700 && speed > 0){//if position 700 speed -4
         speed = -4;
         
      }else if(posX < 70 && speed <= 0){//if position 70 speed 4
         speed = 4;
      }
      posX += speed;
      monImg.setTranslateX(posX);//transalte posX
      monImg.setTranslateY(posY);//translate posY

//if statement
       if (posX > 800)
       posX = 20;
       if (posY > 500)
       posY = 10;

    } // end update()
    
    /** 
     * @return int
     */
    //getters

    public int getPosX(){
      return this.posX;
   }
   
   /** 
    * @return int
    */
   public int getPosY(){
      return this.posY;
   }
    
}//end of GhostRacer