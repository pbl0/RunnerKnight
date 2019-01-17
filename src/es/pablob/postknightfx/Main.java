/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.pablob.postknightfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import javafx.scene.shape.Circle;

/**
 *
 * @author PC15
 */
public class Main extends Application {
    int windowWidth = 640;
    int windowHeight = 480;
    int bgWidth = 1024;
    int bg1X = 0;
    int bg2X = bgWidth;
    int bgCurrentSpeed = 0;
    int speed = 2;
    int heroeWidht = 88;
    int heroeHeight = 64;
    int heroeX = 2;
    int heroePosX = 200;
    int heroeY = 270;
    int heroeSpeed = 0;
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.CYAN);
        primaryStage.setTitle("Postknight");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //imagenes de fondo
        Image bg = new Image(getClass().getResourceAsStream("images/bg.png"));
        ImageView bg1 = new ImageView(bg);
        ImageView bg2 = new ImageView(bg);
        
        bg1.setFitHeight(root.getHeight());
        bg2.setFitHeight(root.getHeight());
        
        //heroe corriendo
        Image runGif = new Image(getClass().getResourceAsStream("images/run.gif"));
        ImageView run = new ImageView(runGif);
        run.setFitWidth(heroeWidht);
        run.setFitHeight(heroeHeight);
        run.setVisible(false);
        
        //heroe parado
        Image idleGif = new Image(getClass().getResourceAsStream("images/idle.gif"));
        ImageView idle = new ImageView(idleGif);
        idle.setFitHeight(heroeHeight);
        idle.setFitWidth(heroeWidht);

        
        //heroe atacando
        Image attackGif = new Image(getClass().getResourceAsStream("images/attack.gif"));
        ImageView attack = new ImageView(attackGif);
        attack.setFitHeight(heroeHeight);
        attack.setFitWidth(heroeWidht);
        attack.setVisible(false);
        
        //grupo heroe
        Group groupHeroe = new Group(run, idle, attack);
        groupHeroe.setLayoutX(heroeX);
        groupHeroe.setLayoutY(heroeY);
        
        //Rectangle rectanglePersonaje = new Rectangle(200,230,64,64);
        //rectanglePersonaje.setFill(Color.TRANSPARENT);
        
        root.getChildren().addAll(bg1,bg2,groupHeroe);
        
        
        AnimationTimer animationBG = new AnimationTimer() {
            @Override
            public void handle(long now) { 
                bg1.setX(bg1X);
                bg2.setX(bg2X);

                groupHeroe.setLayoutX(heroeX);
                heroeX -= heroeSpeed;
                bg1X -= bgCurrentSpeed;
                bg2X -= bgCurrentSpeed;
                
                if(bg1X == -bgWidth) {
                    root.getChildren().remove(bg1);
                    bg1X = bgWidth;
                    bg1.setX(bg1X);
                    root.getChildren().add(bg1);
                    groupHeroe.toFront();
                    
                } else if (bg2X == -bgWidth) {
                    root.getChildren().remove(bg2);
                    bg2X = bgWidth;
                    bg2.setX(bg2X);
                    root.getChildren().add(bg2);
                    groupHeroe.toFront();  
                }
            };
        };
        
        animationBG.start();
        
        //Teclado
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                groupHeroe.setScaleX(1);
                idle.setVisible(false);
                run.setVisible(true);
                attack.setVisible(false);
                if (heroeX <= heroePosX) {
                    bgCurrentSpeed = 0;
                    heroeSpeed =-2;
                } else {
                    heroeSpeed = 0;
                    bgCurrentSpeed = speed; 
                }
            }   
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                idle.setVisible(true);
                run.setVisible(false);
                
                bgCurrentSpeed = 0;
                heroeSpeed = 0;
            }   
        });
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.LEFT) {
                groupHeroe.setScaleX(-1);
                idle.setVisible(false);
                run.setVisible(true);
                attack.setVisible(false);
                bgCurrentSpeed = 0;
                if (heroeX <= 2){
                    heroeSpeed = 0;
                } else {
                    heroeSpeed = 2;
                }  
            };   
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if(key.getCode()==KeyCode.LEFT) {
                idle.setVisible(true);
                run.setVisible(false);
                bgCurrentSpeed = 0;
                heroeSpeed = 0;
            }   
        });
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.A) {
                idle.setVisible(false);
                run.setVisible(false);
                attack.setVisible(true);
                bgCurrentSpeed = 0;
                heroeSpeed = 0;
            }   
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if(key.getCode()==KeyCode.A) {
                idle.setVisible(true);
                run.setVisible(false);
                attack.setVisible(false);
                bgCurrentSpeed = 0;
                heroeSpeed = 0;
            }   
        });
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
