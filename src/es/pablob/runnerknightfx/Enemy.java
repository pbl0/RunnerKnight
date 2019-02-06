/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.pablob.runnerknightfx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author PC15
 */
public class Enemy {
    //int vida = 200;
    int posX;
    int dmgGive;
    int dmgRecibido;
    
    public ImageView setImage(String file){
        Image gifEnemy = new Image(getClass().getResourceAsStream(file));
        ImageView enemyImage = new ImageView(gifEnemy);
        return enemyImage;
    }
    
    public void setX(int x) {
        posX = x;
    }
    
    public void setDmg(int x) { 
        dmgGive = x;
    }
    
    public void setDmgRecibido(int x) {
        dmgRecibido = x;
    }
}
