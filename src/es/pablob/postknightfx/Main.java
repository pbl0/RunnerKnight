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
//import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.Circle;
//import javafx.scene.Group;

/**
 *
 * @author PC15
 */
public class Main extends Application {
    int windowWidth = 640;
    int windowHeight = 480;
    int bg1X = 0;
    int bg2X = 992;
    int bgSize = 992;
    int bgCurrentSpeed = 1;
    
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
        
        root.getChildren().add(bg1);
        root.getChildren().add(bg2);

        AnimationTimer animationBG = new AnimationTimer() {
            @Override
            public void handle(long now) { 
                bg1.setX(bg1X);
                bg2.setX(bg2X);
                
                bg1X -= bgCurrentSpeed;
                bg2X -= bgCurrentSpeed;
                
                if(bg1X == -bgSize) {
                    root.getChildren().remove(bg1);
                    bg1X = bgSize;
                    bg1.setX(bg1X);
                    root.getChildren().add(bg1);
                    //System.out.println("bg1");
                    
                } else if (bg2X == -bgSize) {
                    root.getChildren().remove(bg2);
                    bg2X = bgSize;
                    bg2.setX(bg2X);
                    root.getChildren().add(bg2);
                    //System.out.println("bg2");
                }
            };
        };
        animationBG.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
