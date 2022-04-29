/*
* Team 4: Mislav Čuljak and Sven Kovačević
* Course: ISTE-121
* Project Pacman - single player
* Date: 29/04/2022
*/
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
//cherry class
public class Cherry extends Pane {
    
    //attributes
    private int posX=0;//posX
    private int posY=0;//posY
    public boolean eats=false;//boolean for eating cherry
    private ImageView cherry;//cherry
    private Image cherryImg = new Image("cherry.png"); //cherry img
 
    
 
 // parameterized constructor
    public Cherry(int posX,int posY){
       this.posX=posX;//position x 
       this.posY=posY;//position y
       cherry= new ImageView(cherryImg);//adding cherryimage
       this.getChildren().add(cherry);//adding to pane
       cherry.setTranslateX(posX);//translate x
       cherry.setTranslateY(posY);//tranlsate y
       //counter++;
 //getters
    }
    public int getPosX(){
       return this.posX;
    }
    public int getPosY(){
       return this.posY;
    }
}
