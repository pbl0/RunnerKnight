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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;

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
    int heroeWidth = 88;
    int heroeHeight = 64;
    int heroeX = 10;
    int heroePosX = 200;
    int sueloY = 290;
    int heroeSpeed = 0;
    int hp = 200;
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.CYAN);
        primaryStage.setTitle("Postknight");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //FONDO:
        Image bg = new Image(getClass().getResourceAsStream("images/bg.png"));
        ImageView bg1 = new ImageView(bg);
        ImageView bg2 = new ImageView(bg);
        
        bg1.setFitHeight(root.getHeight());
        bg2.setFitHeight(root.getHeight());
        
        //HEROE:
        //heroe corriendo
        Image runGif = new Image(getClass().getResourceAsStream("images/run.gif"));
        ImageView run = new ImageView(runGif);
        run.setFitWidth(heroeWidth);
        run.setFitHeight(heroeHeight);
        run.setVisible(false);
        
        //heroe parado
        Image idleGif = new Image(getClass().getResourceAsStream("images/idle.gif"));
        ImageView idle = new ImageView(idleGif);
        idle.setFitHeight(heroeHeight);
        idle.setFitWidth(heroeWidth);
        
        //heroe atacando
        Image attackGif = new Image(getClass().getResourceAsStream("images/attack.gif"));
        ImageView attack = new ImageView(attackGif);
        attack.setFitHeight(heroeHeight);
        attack.setFitWidth(heroeWidth);
        attack.setVisible(false);
        
        //rectangulo para colision
        Rectangle rectangleHeroe = new Rectangle(25, 8,32,56);
        //rectangleHeroe.setVisible(false);
        
        //grupo heroe
        Group groupHeroe = new Group();
        groupHeroe.getChildren().addAll(rectangleHeroe,run, idle, attack);
        groupHeroe.setLayoutX(heroeX);
        groupHeroe.setLayoutY(sueloY);
        
        //ENEMIGOS:
        //araña:
        Image spiderGif = new Image(getClass().getResourceAsStream("images/spider.gif"));
        ImageView spider = new ImageView(spiderGif);
        
        
        Rectangle rectangleSpider = new Rectangle(12,16,36,36);
        Group spiderGroup = new Group(rectangleSpider,spider );
        spiderGroup.setLayoutX(100);
        spiderGroup.setLayoutY(sueloY+15);
        
        //colision
        
        
        //HP
        /*
        Rectangle rectangleHP = new Rectangle(60,15,200,15);
        rectangleHP.setFill(Color.GREEN);
        Text textHP = new Text("HP:");
        textHP.setFill(Color.GREEN);
        textHP.setX(10);
        textHP.setY(30);
        textHP.setFont(Font.font(22));
        Group groupHP = new Group(textHP,rectangleHP);
        */
        
        root.getChildren().addAll(bg1,bg2,groupHeroe,spiderGroup);
        
        AnimationTimer animationBG = new AnimationTimer() {
            @Override
            public void handle(long now) { 
                bg1.setX(bg1X);
                bg2.setX(bg2X);

                groupHeroe.setLayoutX(heroeX);
                
                heroeX -= heroeSpeed;
                bg1X -= bgCurrentSpeed;
                bg2X -= bgCurrentSpeed;
                
                //colision
                Shape shapeColision = Shape.intersect(rectangleHeroe,rectangleSpider);
                boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
                if (colisionVacia == false) {
                    hp -= 5;
                    System.out.println(hp);
                }
                
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
            } else if(key.getCode()==KeyCode.LEFT) {
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
            } else if(key.getCode()==KeyCode.A) {
                idle.setVisible(false);
                run.setVisible(false);
                attack.setVisible(true);
                bgCurrentSpeed = 0;
                heroeSpeed = 0;
            }       
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                idle.setVisible(true);
                run.setVisible(false);
                bgCurrentSpeed = 0;
                heroeSpeed = 0;
            } else if(key.getCode()==KeyCode.LEFT) {
                idle.setVisible(true);
                run.setVisible(false);
                bgCurrentSpeed = 0;
                heroeSpeed = 0;
            } else if(key.getCode()==KeyCode.A) {
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
