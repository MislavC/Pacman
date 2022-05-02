/*
* Team 4: Mislav Čuljak and Sven Kovačević
* Course: ISTE-121
* Project Pacman - single player
* Date: 29/04/2022
*/

import java.io.Serializable;
//Serialization - conversion of the state of an object into a byte stream

public class Status implements Serializable {
    private int ID;
    private int sliderStatus;

    public Status(int id, int sliderStatus) {
        this.ID = id;
        this.sliderStatus = sliderStatus;
    }

    
    /** 
     * @return int
     */
    int getID() {
        return ID;
    }

    
    /** 
     * @return int
     */
    int getSliderStatus() {
        return sliderStatus;
    }

    
    /** 
     * @return String
     */
    public String toString() {
        return "Status [ID=" + ID + ", sliderStatus=" + sliderStatus + "]";
    }

}

