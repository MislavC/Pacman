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
//coins
public class Coins extends Pane{

//atributes
    private int posX=0;//pos x
    private int posY=0;//pox Y
    public boolean eats=false;//boolean for eating coin
    private ImageView coin;//coins imageview
    private Image coinImg = new Image("coin.png");//coin image
 
    
 //coin parameterized constructor
 
    public Coins(int posX,int posY){
       this.posX=posX;//position x
       this.posY=posY;// position y
       coin= new ImageView(coinImg);//adding coing iamge
       this.getChildren().add(coin);//adding to pane
       coin.setTranslateX(posX);//translate x
       coin.setTranslateY(posY);//translate y
       
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