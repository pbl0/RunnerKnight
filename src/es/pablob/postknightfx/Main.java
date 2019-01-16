/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.pablob.postknightfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.Circle;
//import javafx.scene.Group;

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
    int heroeX = 200;
    int heroeY = 230;
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.LIGHTGREEN);
        primaryStage.setTitle("Postknight");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //imagenes de fondo
        Image bg = new Image(getClass().getResourceAsStream("images/bg.png"));
        ImageView bg1 = new ImageView(bg);
        ImageView bg2 = new ImageView(bg);
        
        //Personaje corriendo
        Image runGif = new Image(getClass().getResourceAsStream("images/run.gif"));
        ImageView run = new ImageView(runGif);
        run.setFitWidth(heroeWidht);
        run.setFitHeight(heroeHeight);
        run.setX(heroeX);
        run.setY(heroeY);
        run.setVisible(false);
        
        //Personaje parado
        Image idleGif = new Image(getClass().getResourceAsStream("images/idle.gif"));
        ImageView idle = new ImageView(idleGif);
        idle.setFitHeight(heroeHeight);
        idle.setFitWidth(heroeWidht);
        idle.setX(heroeX);
        idle.setY(heroeY);
        
        
        //Rectangle rectanglePersonaje = new Rectangle(200,230,64,64);
        //rectanglePersonaje.setFill(Color.TRANSPARENT);
        
        root.getChildren().addAll(bg1,bg2,idle,run);

        AnimationTimer animationBG = new AnimationTimer() {
            @Override
            public void handle(long now) { 
                bg1.setX(bg1X);
                bg2.setX(bg2X);
                
                bg1X -= bgCurrentSpeed;
                bg2X -= bgCurrentSpeed;
                
                if(bg1X == -bgWidth) {
                    root.getChildren().remove(bg1);
                    bg1X = bgWidth;
                    bg1.setX(bg1X);
                    root.getChildren().add(bg1);
                    run.toFront();
                    idle.toFront();
                    
                } else if (bg2X == -bgWidth) {
                    root.getChildren().remove(bg2);
                    bg2X = bgWidth;
                    bg2.setX(bg2X);
                    root.getChildren().add(bg2);
                    run.toFront();
                    idle.toFront();
                    
                }
            };
        };
        
        animationBG.start();
        
        //Teclado
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                run.setScaleX(1);
                idle.setVisible(false);
                run.setVisible(true);
                bgCurrentSpeed = speed;
            }   
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                idle.setVisible(true);
                run.setVisible(false);
                bgCurrentSpeed = 0;
            }   
        });
                scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.LEFT) {
                run.setScaleX(-1);
                idle.setVisible(false);
                run.setVisible(true);
                bgCurrentSpeed = -speed;
            }   
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if(key.getCode()==KeyCode.LEFT) {
                idle.setVisible(true);
                run.setVisible(false);
                bgCurrentSpeed = 0;
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
