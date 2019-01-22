/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.pablob.postknightfx;

import static java.lang.Math.round;
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
    
    int suelo1X = 0;
    int suelo2X = bgWidth;
    double sky1X = 0;
    double sky2X = bgWidth;
    
    double skySpeed = 0.5;
    
    int currentSpeed = 0;
    int heroeCurrentSpeed = 0;
    int speed = 2;
    
    int heroeWidth = 88;
    int heroeHeight = 64;
    int heroeX = 10;
    int heroePosX = 200;
    
    int alturaSuelo = 290;

    int hp = 200;
    
    float distancia = 0;
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.CYAN);
        primaryStage.setTitle("Postknight");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //FONDO:
        //Image bg = new Image(getClass().getResourceAsStream("images/bg.png"));
        //ImageView bg1 = new ImageView(bg);
        //ImageView bg2 = new ImageView(bg);
        
        
        //FONDO:
        Image imageSuelo = new Image(getClass().getResourceAsStream("images/suelo.png"));
        ImageView suelo1 = new ImageView(imageSuelo);
        ImageView suelo2 = new ImageView(imageSuelo);
        Group groupSuelo = new Group(suelo1, suelo2);
        
        Image imageSky = new Image(getClass().getResourceAsStream("images/cielo.png"));
        ImageView sky1 = new ImageView(imageSky);
        ImageView sky2 = new ImageView(imageSky);
        
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
        rectangleHeroe.setVisible(false);
        //rectangleHeroe.setVisible(false);
        
        //grupo heroe
        Group groupHeroe = new Group();
        groupHeroe.getChildren().addAll(rectangleHeroe,run, idle, attack);
        groupHeroe.setLayoutX(heroeX);
        groupHeroe.setLayoutY(alturaSuelo);
        
        
        //ENEMIGOS:
        //araña:
        Image spiderGif = new Image(getClass().getResourceAsStream("images/spider.gif"));
        ImageView spider = new ImageView(spiderGif);
        Rectangle rectangleSpider = new Rectangle(12,16,36,36);
        Group spiderGroup = new Group(rectangleSpider,spider );
        spiderGroup.setLayoutX(100);
        spiderGroup.setLayoutY(alturaSuelo+15);
        rectangleSpider.setVisible(false);
        spiderGroup.setVisible(true);
        
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
        
        
        
        //colision
        /*
        Shape shapeColision = Shape.intersect(rectangleHeroe,rectangleSpider);
        boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
        if (colisionVacia == false) {
            hp -= 5;
            System.out.println(hp);
            }
        */
        root.getChildren().addAll(sky1, sky2, suelo1, suelo2,spiderGroup,groupHeroe);
        
        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long now) { 
                System.out.println(distancia/10);
                
                suelo1.setX(suelo1X);
                suelo2.setX(suelo2X);
                
                sky1.setX(sky1X);
                sky2.setX(sky2X);
                
                groupHeroe.setLayoutX(heroeX);
                
                heroeX -= heroeCurrentSpeed;
                
                suelo1X -= currentSpeed;
                suelo2X -= currentSpeed;
                
                sky1X -= skySpeed;
                sky2X -= skySpeed;
                

                if(suelo1X == -bgWidth) {
                    root.getChildren().remove(suelo1);
                    suelo1X = bgWidth;
                    suelo1.setX(suelo1X);
                    root.getChildren().add(suelo1);
                    groupHeroe.toFront();
                    
                } else if (suelo2X == -bgWidth) {
                    root.getChildren().remove(suelo2);
                    suelo2X = bgWidth;
                    suelo2.setX(suelo2X);
                    root.getChildren().add(suelo2);
                    groupHeroe.toFront();  
                }
                if(sky1X == -bgWidth) {
                    root.getChildren().remove(sky1);
                    sky1X = bgWidth;
                    sky1.setX(sky1X);
                    root.getChildren().add(sky1);
                    suelo1.toFront();
                    suelo2.toFront();
                    groupHeroe.toFront();
                    
                } else if (sky2X == -bgWidth) {
                    root.getChildren().remove(sky2);
                    sky2X = bgWidth;
                    sky2.setX(sky2X);
                    root.getChildren().add(sky2);
                    suelo1.toFront();
                    suelo2.toFront();
                    groupHeroe.toFront();  
                }
                Shape shapeColision = Shape.intersect(rectangleHeroe,rectangleSpider);
                boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
                if (colisionVacia == false) {
                    //run.setOpacity(0.7);
                    //idle.setOpacity(0.7);
                    hp -= 5;
                    //System.out.println(hp);
                    
;
                }
            };
        };
        
        animation.start();
        
        //Teclado
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                groupHeroe.setScaleX(1);
                idle.setVisible(false);
                run.setVisible(true);
                attack.setVisible(false);
                if (heroeX <= heroePosX) {
                    currentSpeed = 0;
                    heroeCurrentSpeed =-2;
                } else {
                    heroeCurrentSpeed = 0;
                    currentSpeed = speed;
                    distancia +=1;
                }
            } else if(key.getCode()==KeyCode.LEFT) {
                groupHeroe.setScaleX(-1);
                idle.setVisible(false);
                run.setVisible(true);
                attack.setVisible(false);
                currentSpeed = 0;
                if (heroeX <= 2){
                    heroeCurrentSpeed = 0;
                } else {
                    heroeCurrentSpeed = 2;
                }  
            } else if(key.getCode()==KeyCode.A) {
                idle.setVisible(false);
                run.setVisible(false);
                attack.setVisible(true);
                currentSpeed = 0;
                heroeCurrentSpeed = 0;
            }       
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                idle.setVisible(true);
                run.setVisible(false);
                currentSpeed = 0;
                heroeCurrentSpeed = 0;
            } else if(key.getCode()==KeyCode.LEFT) {
                idle.setVisible(true);
                run.setVisible(false);
                currentSpeed = 0;
                heroeCurrentSpeed = 0;
            } else if(key.getCode()==KeyCode.A) {
                idle.setVisible(true);
                run.setVisible(false);
                attack.setVisible(false);
                currentSpeed = 0;
                heroeCurrentSpeed = 0;
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
