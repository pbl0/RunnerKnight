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
public class Hero {
    int vida = 200;
    int posX;
    int width = 88;
    int height = 64;
    
    Image runGif = new Image(getClass().getResourceAsStream("images/run.gif"));
    ImageView run = new ImageView(runGif);
    
    
    Image idleGif = new Image(getClass().getResourceAsStream("images/idle.gif"));
    ImageView idle = new ImageView(idleGif);
    
    Image attackGif = new Image(getClass().getResourceAsStream("images/attack.gif"));
    ImageView attack = new ImageView(attackGif);
    
    Rectangle rect = new Rectangle(25, 8,32,56);
    
    Rectangle rectAtaque = new Rectangle(45,8,32,56);
    
    
    Group group = new Group( rect, rectAtaque,run, idle, attack);
    
    
    public void setVida(int x) {
        vida = x;
    }
    
    public void setX(int x) {
        posX = x;
    }
    
    
}
