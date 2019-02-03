/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.pablob.postknightfx;


//import java.io.File;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author PC15
 */
public class Main extends Application {
    
    final int ANCHO_FONDO = 1024;
    final int SPEED = 2;
    
    int suelo1X;
    int suelo2X;
    double sky1X;
    double sky2X;
    
    int currentSpeed;
    int heroCurrentSpeed;
    
    float distancia;
    
    Group groupEnemy = new Group();
    boolean enemyBool;
    int distanciaEnemigos;
    int enemyHP;
    int enemySpeed;
    int enemyX;
    boolean ataque;
    int tocado = 0;
    
    Group groupPocion = new Group();
    boolean pocionBool;
    int pocionX;
    
    Hero hero = new Hero();
    
    
    
    public void reinicio() {
        
        suelo1X = 0;
        suelo2X = ANCHO_FONDO;
        sky1X = 0;
        sky2X = ANCHO_FONDO;
        distancia = 0;
        currentSpeed = 0;
        heroCurrentSpeed = 0;
        enemyX = 640;
        enemyHP = 100;
        distanciaEnemigos = 20;
        enemyBool = false;
        groupEnemy.getChildren().clear();
        pocionX = enemyX;
        pocionBool = false;
        hero.setVida(200);
        hero.posX = 10;
        
    }
    public void colisionDmg(Rectangle rectHero, Rectangle rectEnemy, int dmg) {
        Shape shapeColision = Shape.intersect(rectHero,rectEnemy);
        boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
        if (colisionVacia == false) {
            hero.setVida(hero.vida-dmg);
        } 
    }
    public boolean colisionAtaque(Rectangle rectHero, Rectangle rectEnemy) {
        Shape shapeColision = Shape.intersect(rectHero,rectEnemy);
        boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
        return colisionVacia;
    }
    
    public void colisionPocion(Rectangle rectHero, Rectangle rectPocion){
        Shape shapeColision = Shape.intersect(rectHero,rectPocion);
        boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
        if (colisionVacia == false) {
            hero.setVida(hero.vida+30);
            groupPocion.getChildren().clear();
            pocionBool = false;
        
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
        
        
        ataque = false;
        
        this.reinicio();
        
        Pane root = new Pane();
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.CYAN);
        primaryStage.setTitle("Postknight");
        primaryStage.setScene(scene);
        primaryStage.show();
        
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
        Rectangle rectangleHero = new Rectangle(25, 8,32,56);
        rectangleHero.setVisible(false);
        Rectangle rectangleAtaque = new Rectangle(45,8,32,56);
        //rectangleAtaque.setFill(Color.RED);
        rectangleAtaque.setVisible(false);
        
        //grupo heroe
        Group groupHeroe = new Group();
        groupHeroe.getChildren().addAll(rectangleAtaque, rectangleHero, run, idle, attack);
        groupHeroe.setLayoutX(hero.posX);
        groupHeroe.setLayoutY(alturaSuelo);
         
        //ENEMIGOS:
        //araña:
        Image gifSpider = new Image(getClass().getResourceAsStream("images/spider.gif"));
        ImageView spider = new ImageView(gifSpider);
        
        Rectangle rectangleSpider = new Rectangle(12,16,36,36);
        rectangleSpider.setVisible(false);
        
        Group groupSpider = new Group(rectangleSpider,spider );
        groupSpider.setLayoutY(alturaSuelo+15);
        
        //murcielago
        Image gifBat = new Image(getClass().getResourceAsStream("images/bat.gif"));
        ImageView bat = new ImageView(gifBat);
        
        Rectangle rectangleBat = new Rectangle(0,46,36,36);
        rectangleBat.setVisible(false);
        
        Group groupBat = new Group(rectangleBat, bat);
        groupBat.setLayoutY(alturaSuelo-36);
        
        //ciclope
        Image gifCyclop = new Image(getClass().getResourceAsStream("images/cyclops.gif"));
        ImageView cyclop = new ImageView(gifCyclop);
        Rectangle rectangleCyclop = new Rectangle(20,26,26,38);
        rectangleCyclop.setVisible(false);
        Group groupCyclop = new Group(rectangleCyclop, cyclop);
        groupCyclop.setScaleX(1.5);
        groupCyclop.setScaleY(1.5);
        groupCyclop.setLayoutY(alturaSuelo-18);
        
        //vida enemiga
        Rectangle rectangleEnemyHP = new Rectangle(0,alturaSuelo,enemyHP/2,6);
        Rectangle rectangleFondoEnemyHP = new Rectangle(0,alturaSuelo,enemyHP/2,6);
        rectangleFondoEnemyHP.setFill(Color.BLACK);
        rectangleEnemyHP.setFill(Color.valueOf("#d40c0c"));
        

        //Vida
        Rectangle rectangleHP = new Rectangle(60,15,hero.vida,15);
        rectangleHP.setFill(Color.valueOf("#d40c0c"));
        Text textHP = new Text("HP:");
        textHP.setFill(Color.valueOf("#d40c0c"));
        textHP.setX(10);
        textHP.setY(30);
        textHP.setFont(Font.font(22));
        Group groupHP = new Group(textHP,rectangleHP);
        
        //distancia
        Text textDistancia = new Text("0.0  m");
        textDistancia.setX(windowWidth-100);
        textDistancia.setFill(Color.DARKRED);
        textDistancia.setTextAlignment(TextAlignment.RIGHT);
        //textDistancia.setStroke(Color.WHITE);
        textDistancia.setY(30);
        textDistancia.setFont(Font.font(22));
        
        //pocion:
        Image imagePocion = new Image(getClass().getResourceAsStream("images/pocion_roja.png"));
        ImageView pocion = new ImageView(imagePocion);
        Rectangle rectPocion = new Rectangle(16,16);
        rectPocion.setVisible(false);
        

        groupPocion = new Group();
        groupPocion.setScaleX(2.2);
        groupPocion.setScaleY(2.2);
        groupPocion.setLayoutY(alturaSuelo+36);
        groupPocion.setLayoutX(pocionX);
        
        
        //GameOver
        VBox vbox = new VBox();
        Text textGameOver = new Text("GAME OVER");
        textGameOver.setFont(Font.font(50));
        textGameOver.setFill(Color.DARKRED);
        Text textReinicio = new Text("Pulsa 'R' para reinciar");
        textReinicio.setFont(Font.font(25));
        textReinicio.setFill(Color.DARKRED);
        vbox.getChildren().addAll(textGameOver, textReinicio);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(root.getWidth(), root.getHeight());
        
        Group groupHUD = new Group(groupHP, textDistancia);
        
        
//        AudioClip music = new AudioClip(getClass().getResource("music.mp3").toString());
//        //MediaPlayer musicplayer = new MediaPlayer(music);
//        music.setVolume(1);
//        music.play();
        
        root.getChildren().addAll(sky1, sky2, suelo1, suelo2,groupHeroe,groupHUD, groupEnemy,groupPocion);
        
        
        
        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long now) { 
                //System.out.println(distancia/10);
                //System.out.println(hero.vida);
                suelo1.setX(suelo1X);
                suelo2.setX(suelo2X);
                
                sky1.setX(sky1X);
                sky2.setX(sky2X);
                
                groupHeroe.setLayoutX(hero.posX);
                
                hero.posX -= heroCurrentSpeed;
                
                suelo1X -= currentSpeed;
                suelo2X -= currentSpeed;
                
                sky1X -= skySpeed;
                sky2X -= skySpeed;
                
                textDistancia.setText(distancia/10+" m");
                
                switch (Math.round(distancia/10)) {
                    case 50:
                        distanciaEnemigos = 10;
                        break;
                    case 100:
                        distanciaEnemigos = 5;
                        break;
                    case 150:
                        
                        break;

                }

                    
                
                if (enemyBool == true) {
                    //System.out.println(tocado);
                    if (ataque == true) {
                        boolean colisionVaciaSpider = colisionAtaque(rectangleAtaque,rectangleSpider);
                        boolean colisionVaciaBat = colisionAtaque(rectangleAtaque,rectangleBat);
                        boolean colisionVaciaCyclop = colisionAtaque(rectangleAtaque, rectangleCyclop);
                        if (colisionVaciaSpider == false) {
                            enemyHP -= 34;
                            tocado = 10;
                        } 
                        if (colisionVaciaBat == false) {
                            enemyHP -= 60;
                            tocado = 10;
                        }
                        if (colisionVaciaCyclop == false) {
                            enemyHP -= 25;
                            tocado = 10;
                        }
                    }
                    if (tocado > 0){
                            //System.out.println(tocado);
                                groupEnemy.setOpacity(0.50);
                                tocado -= 1;
                                enemyX += 10;
                                groupEnemy.setLayoutX(enemyX);
                    } 
                    else{
                            enemyX -= enemySpeed;
                            groupEnemy.setLayoutX(enemyX);
                            groupEnemy.setOpacity(1.00);
                    } 

                    rectangleEnemyHP.setWidth(enemyHP/2);
                    
                    colisionDmg(rectangleHero, rectangleSpider,2);
                    colisionDmg(rectangleHero, rectangleBat,1);
                    colisionDmg(rectangleHero, rectangleCyclop,5);

                    if (enemyX <= -10 || enemyHP <= 0) {
                        groupEnemy.getChildren().clear();
                        enemyHP = 100;
                        enemyBool = false;
                        enemyX = 640;
                        groupEnemy.setOpacity(1.00);
                        tocado = 0;
                    }
                }

                rectangleHP.setWidth(hero.vida);
                
                if(suelo1X == -ANCHO_FONDO) {
                    suelo1X = ANCHO_FONDO;
                    suelo1.setX(suelo1X);


                    
                } else if (suelo2X == -ANCHO_FONDO) {
                    suelo2X = ANCHO_FONDO;
                    suelo2.setX(suelo2X);


                }
                if(sky1X == -ANCHO_FONDO) {
                    sky1X = ANCHO_FONDO;
                    sky1.setX(sky1X);

                    
                } else if (sky2X == -ANCHO_FONDO) {
                    sky2X = ANCHO_FONDO;
                    sky2.setX(sky2X);

                }
                Random randomNum = new Random();
                if (distancia/10 % distanciaEnemigos == 0 && distancia != 0 && enemyBool == false) {

                    
                    int enemyNum = randomNum.nextInt(3);
                    //System.out.println(enemyNum);
                    switch (enemyNum) {
                        case 0: groupEnemy.getChildren().addAll(groupSpider, rectangleFondoEnemyHP,rectangleEnemyHP);
                                break;
                        case 1: groupEnemy.getChildren().addAll(groupBat,rectangleFondoEnemyHP,rectangleEnemyHP);
                                break;
                        case 2: groupEnemy.getChildren().addAll(groupCyclop,rectangleFondoEnemyHP,rectangleEnemyHP);
                                break;
                    }
                    groupEnemy.setLayoutX(enemyX);
                    enemyBool = true;
                }
                    
                if (hero.vida <= 0) {
                    //System.out.println("muerto");
                    root.getChildren().add(vbox);
                }
                if (distancia/10 % 100 == 0 && distancia != 0) {
                    pocionBool = randomNum.nextBoolean();
                    if (pocionBool == true) {
                        groupPocion.getChildren().addAll(rectPocion, pocion );
                        groupPocion.setLayoutX(pocionX);
                    }
                }
                System.out.println(pocionBool);
                if (pocionBool == true)  {
                    pocionX -= currentSpeed;
                    groupPocion.setLayoutX(pocionX);
                    colisionPocion(rectangleHero,rectPocion);
                    
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
                if (hero.posX <= heroePosX) {
                    currentSpeed = 0;
                    heroCurrentSpeed =-SPEED;
                    
                } else {
                    heroCurrentSpeed = 0;
                    currentSpeed = SPEED;
                    distancia +=1;
                    
                    enemySpeed = 4;
                }
            } else if(key.getCode()==KeyCode.LEFT) {
                groupHeroe.setScaleX(-1);
                idle.setVisible(false);
                run.setVisible(true);
                attack.setVisible(false);
                currentSpeed = 0;
                if (hero.posX <= 1){
                    heroCurrentSpeed = 0;
                } else {
                    heroCurrentSpeed = SPEED;
                }  
            } else if(key.getCode()==KeyCode.A) {
                idle.setVisible(false);
                run.setVisible(false);
                attack.setVisible(true);
                currentSpeed = 0;
                heroCurrentSpeed = 0;
                ataque = true;
            } else if(key.getCode()==KeyCode.R) {
                this.reinicio();
                root.getChildren().remove(vbox);
                //System.out.println(randomNum.nextInt(100));
                //System.out.println(rectangleHP.getWidth());
            }      
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                idle.setVisible(true);
                run.setVisible(false);
                currentSpeed = 0;
                heroCurrentSpeed = 0;
                enemySpeed = 2;
            } else if(key.getCode()==KeyCode.LEFT) {
                idle.setVisible(true);
                run.setVisible(false);
                currentSpeed = 0;
                heroCurrentSpeed = 0;
            } else if(key.getCode()==KeyCode.A) {
                idle.setVisible(true);
                run.setVisible(false);
                attack.setVisible(false);
                ataque = false;
                currentSpeed = 0;
                heroCurrentSpeed = 0;

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