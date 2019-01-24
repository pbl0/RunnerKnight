/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.pablob.postknightfx;


import java.util.Random;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author PC15
 */
public class Main extends Application {

    int bgWidth = 1024;
    int suelo1X;
    int suelo2X;
    double sky1X;
    double sky2X;
    int currentSpeed;
    int heroeCurrentSpeed;
    int speed = 2;
    int heroCurrentX;
    int hp;
    float distancia;
    int enemyX;
    boolean enemy;
    int enemySpeed;
    Group groupEnemy = new Group();
    
    public void reinicio() {
        suelo1X = 0;
        suelo2X = bgWidth;
        sky1X = 0;
        sky2X = bgWidth;
        distancia = 0;
        hp = 200;
        currentSpeed = 0;
        heroCurrentX = 10;
        heroeCurrentSpeed = 0;
        enemyX = 640;
        enemy = false;
        groupEnemy.getChildren().clear();
        
    }
    public void colision(Rectangle rect1, Rectangle rect2, int dmg) {
        Shape shapeColision = Shape.intersect(rect1,rect2);
        boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
        if (colisionVacia == false) {
            hp -= dmg;
        } 
    }

    
    
    @Override
    public void start(Stage primaryStage) {
        int windowWidth = 640;
        int windowHeight = 480;
        double skySpeed = 0.5;
        int heroeWidth = 88;
        int heroeHeight = 64;
        int heroePosX = 200;
        int alturaSuelo = 290;
        
        this.reinicio();
        
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
        //Group groupSuelo = new Group(suelo1, suelo2);
        //System.out.println(imageSuelo.getWidth());
        
        
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
        groupHeroe.setLayoutX(heroCurrentX);
        groupHeroe.setLayoutY(alturaSuelo);
        
        
        //ENEMIGOS:
        //araña:
        Image gifSpider = new Image(getClass().getResourceAsStream("images/spider.gif"));
        ImageView spider = new ImageView(gifSpider);
        
        Rectangle rectangleSpider = new Rectangle(12,16,36,36);
        rectangleSpider.setVisible(false);
        
        Group groupSpider = new Group(rectangleSpider,spider );
        groupSpider.setLayoutY(alturaSuelo+15);
        
        //groupSpider.setVisible(true);
        
        
        Image gifBat = new Image(getClass().getResourceAsStream("images/bat.gif"));
        ImageView bat = new ImageView(gifBat);
        
        Rectangle rectangleBat = new Rectangle(0,46,36,36);
        rectangleBat.setVisible(false);
        
        Group groupBat = new Group(rectangleBat, bat);
        groupBat.setLayoutY(alturaSuelo-36);
        
        
        
        
        
        //HP
        Rectangle rectangleHP = new Rectangle(60,15,hp,15);
        rectangleHP.setFill(Color.GREEN);
        Text textHP = new Text("HP:");
        textHP.setFill(Color.GREEN);
        textHP.setX(10);
        textHP.setY(30);
        textHP.setFont(Font.font(22));
        Group groupHP = new Group(textHP,rectangleHP);
        
        //distancia
        Text textDistancia = new Text("0.0  m");
        textDistancia.setX(windowWidth-80);
        textDistancia.setFill(Color.RED);
        //textDistancia.setStroke(Color.WHITE);
        textDistancia.setY(30);
        textDistancia.setFont(Font.font(22));
        
        
        
        root.getChildren().addAll(sky1, sky2, suelo1, suelo2,groupHeroe,groupHP, textDistancia, groupEnemy);
        
        
        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long now) { 
                //System.out.println(distancia/10);
                
                suelo1.setX(suelo1X);
                suelo2.setX(suelo2X);
                
                sky1.setX(sky1X);
                sky2.setX(sky2X);
                
                groupHeroe.setLayoutX(heroCurrentX);
                
                heroCurrentX -= heroeCurrentSpeed;
                
                suelo1X -= currentSpeed;
                suelo2X -= currentSpeed;
                
                sky1X -= skySpeed;
                sky2X -= skySpeed;
                
                textDistancia.setText(distancia/10+" m");
                
                if (enemy == true) {
                    enemyX -= enemySpeed;
                    groupEnemy.setLayoutX(enemyX);
                    
                    colision(rectangleHeroe, rectangleSpider,2);
                    colision(rectangleHeroe, rectangleBat,1);

                }
                
                rectangleHP.setWidth(hp);
                
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

                if (distancia/10 % 20 == 0 && distancia != 0 && enemy == false) {

                    Random randomNum = new Random();
                    int enemyNum = randomNum.nextInt(2);
                    System.out.println(enemyNum);
                    switch (enemyNum) {
                        case 0: groupEnemy.getChildren().add(groupSpider);
                                break;
                        case 1: groupEnemy.getChildren().add(groupBat);
                                break;
                    }
                    
                    groupEnemy.setLayoutX(enemyX);
                    //root.getChildren().add(groupEnemy);
                    enemy = true;
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
                if (heroCurrentX <= heroePosX) {
                    currentSpeed = 0;
                    heroeCurrentSpeed =-speed;
                    
                } else {
                    heroeCurrentSpeed = 0;
                    currentSpeed = speed;
                    distancia +=1;
                    
                    enemySpeed = 4;
                }
            } else if(key.getCode()==KeyCode.LEFT) {
                groupHeroe.setScaleX(-1);
                idle.setVisible(false);
                run.setVisible(true);
                attack.setVisible(false);
                currentSpeed = 0;
                if (heroCurrentX <= 2){
                    heroeCurrentSpeed = 0;
                } else {
                    heroeCurrentSpeed = speed;
                }  
            } else if(key.getCode()==KeyCode.A) {
                idle.setVisible(false);
                run.setVisible(false);
                attack.setVisible(true);
                currentSpeed = 0;
                heroeCurrentSpeed = 0;
            } else if(key.getCode()==KeyCode.R) {
                this.reinicio();
                //System.out.println(randomNum.nextInt(100));
                //System.out.println(rectangleHP.getWidth());
            }      
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                idle.setVisible(true);
                run.setVisible(false);
                currentSpeed = 0;
                heroeCurrentSpeed = 0;
                enemySpeed = 2;
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
