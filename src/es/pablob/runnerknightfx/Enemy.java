/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.pablob.runnerknightfx;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author PC15
 */
public class Enemy {
    
    int posX;
    int dmgGive;
    int dmgRecibido;
    
    public ImageView setImage(String file){
        Image gifEnemy = new Image(getClass().getResourceAsStream(file));
        ImageView enemyImage = new ImageView(gifEnemy);
        return enemyImage;
    }
    
    public Group setGroup(ImageView i, Rectangle r, double escala, int altura){
        Group groupEnemy = new Group(r, i);
        groupEnemy.setScaleX(escala);
        groupEnemy.setScaleY(escala);
        groupEnemy.setLayoutY(altura);
        return groupEnemy;
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
