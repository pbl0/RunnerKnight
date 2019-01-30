/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.pablob.postknightfx;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 *
 * @author PC15
 */
public class Hero {
    int vida = 200;
    
    int heroeWidth = 88;
    int heroeHeight = 64;
    int heroePosX = 200;
    Image runGif = new Image(getClass().getResourceAsStream("images/run.gif"));
    ImageView run = new ImageView(runGif);

    public void herido(int dmg) {
        vida =- dmg;
    }
    
    public int getDmg() {
        return 10;
    }
    
    public int getHP() {
        return vida;
    }
    
    
//    public Group heroGroup {
//        int a = 0;
//        return;
//    }

}
