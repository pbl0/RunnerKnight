/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.pablob.postknightfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author PC15
 */
public class Main extends Application {
    
    int ballCenterX = 10;
    int ballCurrentSpeedX = 3;
    
    @Override
   public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 640, 480, Color.BLACK);
        primaryStage.setTitle("Postknight");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Circle circleBall = new Circle(ballCenterX, 30, 7, Color.WHITE);
        root.getChildren().add(circleBall);
        
        AnimationTimer animationBall = new AnimationTimer() {
            @Override
            public void handle(long now) {
                circleBall.setCenterX(ballCenterX);
                ballCenterX += ballCurrentSpeedX;
                if(ballCenterX >= 640) {
                    ballCurrentSpeedX = -3;
                }
                else if (ballCenterX <= 0) {
                    ballCurrentSpeedX = 3;
                }
                //System.out.println(ballCenterX);
               
            };
        };
        animationBall.start();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
