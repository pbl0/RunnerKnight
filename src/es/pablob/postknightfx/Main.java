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
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author PC15
 */
public class Main extends Application {
    
    //int ballCenterX = 10;
    //int ballCurrentSpeedX = 3;
    int windowWidth = 640;
    int windowHeight = 480;
    int bgX = 0;
    double bgCurrentSpeed = 1;
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.LIGHTGREEN);
        primaryStage.setTitle("Postknight");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Circle circleBall = new Circle(ballCenterX, 30, 7, Color.WHITE);
        
        //  GRUPO PERSONAJE
        Rectangle rectangleCuerpo = new Rectangle(0, 0, 48, 60);
        Rectangle rectanglePierna1 = new Rectangle(1, 50, 10, 20);
        Rectangle rectanglePierna2 = new Rectangle(37, 50, 10, 20);
        Circle circleOjo = new Circle(32, 22, 5);
        Rectangle rectanglePanuelo = new Rectangle(-1, 7, 50, 13);
        
        //Coloreamos
        rectangleCuerpo.setFill(Color.BLACK);    
        rectanglePierna1.setFill(Color.BLACK);    
        rectanglePierna2.setFill(Color.BLACK);    
        circleOjo.setFill(Color.WHITE);        
        rectanglePanuelo.setFill(Color.RED);
        
        //Grupo
        Group groupPerson = new Group();
        groupPerson.getChildren().add(rectangleCuerpo);
        groupPerson.getChildren().add(rectanglePierna1);
        groupPerson.getChildren().add(rectanglePierna2);
        groupPerson.getChildren().add(circleOjo);
        groupPerson.getChildren().add(rectanglePanuelo);
        
        //bg
        ImageView bg = new ImageView(new Image("/bg.png"));
        
        //Posicionamos
        groupPerson.setLayoutX(100);
        groupPerson.setLayoutY(300);
        //iv1.relocate(10, 10);
        
        root.getChildren().add(groupPerson);
        //root.getChildren().add(circleBall);
        root.getChildren().add(bg);
        
        
        
        
        AnimationTimer animationBall = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //circleBall.setCenterX(ballCenterX);
                bg.setX(bgX);
                //ballCenterX += ballCurrentSpeedX;
                bgX -= bgCurrentSpeed;
                /*if(ballCenterX >= 640) {
                    ballCurrentSpeedX = -3;
                }
                else if (ballCenterX <= 0) {
                    ballCurrentSpeedX = 3;
                }
                */
                
                if(bgX < windowWidth-992) {
                    
                }
                System.out.println(bgX);
               
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
