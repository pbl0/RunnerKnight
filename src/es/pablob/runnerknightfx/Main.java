/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.pablob.runnerknightfx;



//import com.sun.javafx.perf.PerformanceTracker;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.shape.Circle;

/**
 *
 * @author PC15
 */
public class Main extends Application {
    //fondos
    final int ANCHO_FONDO = 1024;
    int suelo1X;
    int suelo2X;
    double sky1X;
    double sky2X;
    
    //velocidades y distancia
    final int SPEED = 4;
    int currentSpeed;
    int heroCurrentSpeed;
    float distancia;
    
    //enemigos
    Group groupEnemy = new Group();
    boolean enemyBool;
    int distanciaEnemigos;
    int enemyHP;
    int enemySpeed;
    int enemyX;
    boolean ataque;
    int tocado = 0;
    
    //heroe
    Hero hero = new Hero();
    
    boolean vivo;
    
    //pocion
    Group groupPocion = new Group();
    boolean pocionBool;
    int pocionX;
    
    //Reiniciar juego
    public void reinicio() {
        
        suelo1X = 0;
        suelo2X = ANCHO_FONDO;
        sky1X = 0;
        sky2X = ANCHO_FONDO;
        
        distancia = 0;
        currentSpeed = 0;
        heroCurrentSpeed = 0;
        
        hero.setVida(200);
        hero.posX = 10;
        vivo = true;
        hero.group.setVisible(true);
        
        groupEnemy.getChildren().clear();
        enemyX = 640;
        enemyHP = 100;
        distanciaEnemigos = 20;
        enemyBool = false;
        
        groupPocion.getChildren().clear();
        pocionX = enemyX;
        pocionBool = false;
        
    }
    //colision daño al heroe
    public void colisionDmg(Rectangle rectHero, Rectangle rectEnemy, int dmg) {
        Shape shapeColision = Shape.intersect(rectHero,rectEnemy);
        boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
        if (colisionVacia == false) {
            hero.setVida(hero.vida-dmg);
        } 
    }
    //colision de ataque
    public boolean colisionAtaque(Rectangle rectHero, Rectangle rectEnemy) {
        Shape shapeColision = Shape.intersect(rectHero,rectEnemy);
        boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
        return colisionVacia;
    }
    //colision pocion
    public void colisionPocion(Rectangle rectHero, Circle circlePocion){
        Shape shapeColision = Shape.intersect(rectHero,circlePocion);
        boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
        if (colisionVacia == false) {
            hero.setVida(hero.vida+30);
            groupPocion.getChildren().clear();
            pocionX = 640;
            pocionBool = false;
        }
    }
    

    
    @Override
    public void start(Stage primaryStage) {
        
        this.reinicio();
        
        
        int windowWidth = 640;
        int windowHeight = 480;
        final double CIELO_SPEED = 0.5;
        ataque = false;
        
        //posiciones
        int heroePosX = 200;
        int alturaSuelo = 290;
        
        //pane
        Pane root = new Pane();
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.CYAN);
        primaryStage.setTitle("RunnerKnight");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //FONDO:
        //suelo
        Image imageSuelo = new Image(getClass().getResourceAsStream("images/suelo.png"));
        ImageView suelo1 = new ImageView(imageSuelo);
        ImageView suelo2 = new ImageView(imageSuelo);
        //cielo
        Image imageSky = new Image(getClass().getResourceAsStream("images/cielo.png"));
        ImageView sky1 = new ImageView(imageSky);
        ImageView sky2 = new ImageView(imageSky);

        //HEROE:
        //heroe corriendo
        ImageView heroRun = hero.run;
        heroRun.setFitWidth(hero.width);
        heroRun.setFitHeight(hero.height);
        heroRun.setVisible(false);
        
        //heroe parado
        ImageView heroParado = hero.idle;
        heroParado.setFitHeight(hero.height);
        heroParado.setFitWidth(hero.width);
        
        //heroe atacando
        ImageView heroAtaque = hero.attack;
        heroAtaque.setFitHeight(hero.height);
        heroAtaque.setFitWidth(hero.width);
        heroAtaque.setVisible(false);
        
        //rectangulos para colisiones
        hero.rect.setVisible(false);
        hero.rectAtaque.setVisible(false);
        
        //grupo heroe
        Group groupHero = hero.group;
        //Group groupHero = new Group();
        //groupHero.getChildren().addAll(hero.rectAtaque, hero.rect, heroRun, heroParado, heroAtaque);
        groupHero.setLayoutX(hero.posX);
        groupHero.setLayoutY(alturaSuelo);
         
        //ENEMIGOS:
        //araña:
        Enemy enemySpider = new Enemy();
        enemySpider.setDmg(2);
        enemySpider.setDmgRecibido(34);
        ImageView spider = enemySpider.setImage("images/spider.gif");
        Rectangle rectangleSpider = new Rectangle(12,16,36,36);
        rectangleSpider.setVisible(false);        
        Group groupSpider = enemySpider.setGroup(spider, rectangleSpider, 1, alturaSuelo+15);

        //murcielago
        Enemy enemyBat = new Enemy();
        enemyBat.setDmg(1);
        enemyBat.setDmgRecibido(60);
        ImageView bat = enemyBat.setImage("images/bat.gif");
        Rectangle rectangleBat = new Rectangle(0,46,36,36);
        Group groupBat = enemyBat.setGroup(bat,rectangleBat, 0.8,alturaSuelo-36);
        rectangleBat.setVisible(false);
        
        
        //ciclope
        Enemy enemyCyclop = new Enemy();
        enemyCyclop.setDmg(5);
        enemyCyclop.setDmgRecibido(25);
        ImageView cyclop = enemyCyclop.setImage("images/cyclops.gif");
        Rectangle rectangleCyclop = new Rectangle(20,26,26,38);
        rectangleCyclop.setVisible(false);
        Group groupCyclop = enemyBat.setGroup(cyclop,rectangleCyclop, 1.5, alturaSuelo-18);

        
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
        Text textDistancia = new Text();
        textDistancia.setX(windowWidth-100);
        textDistancia.setFill(Color.DARKRED);
        textDistancia.setY(30);
        textDistancia.setFont(Font.font(22));
        
        //pocion:
        Rectangle rectPotion1 = new Rectangle(95,74,10,11);
        
        Rectangle rectPotion2 = new Rectangle(90,67,20,7);
        Circle circlePotion = new Circle(100,100,16);
        
        circlePotion.setFill(Color.valueOf("#d40c0c"));
        rectPotion1.setFill(Color.LIGHTBLUE);
        rectPotion2.setFill(Color.BROWN);
        
        circlePotion.setStroke(Color.BLACK);
        rectPotion1.setStroke(Color.BLACK);
        rectPotion2.setStroke(Color.BLACK);

        groupPocion = new Group();
        groupPocion.setScaleX(0.8);
        groupPocion.setScaleY(0.8);
        groupPocion.setLayoutY(alturaSuelo-50);
        groupPocion.setLayoutX(pocionX);
        
        Group groupHUD = new Group(groupHP, textDistancia);
        
        //GameOver
        VBox vbox = new VBox();
        Text textGameOver = new Text("GAME OVER");
        textGameOver.setFont(Font.font(50));
        textGameOver.setFill(Color.DARKRED);
        Text textReinicio = new Text("Pulsa 'R' para reiniciar");
        textReinicio.setFont(Font.font(25));
        textReinicio.setFill(Color.DARKRED);
        vbox.getChildren().addAll(textGameOver, textReinicio);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(root.getWidth(), root.getHeight());

        root.getChildren().addAll(sky1, sky2, suelo1, suelo2,groupHero,groupHUD,groupPocion, groupEnemy);
        
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) { 
                    
                    //System.out.println(distancia/10);
                    //System.out.println(hero.vida);
                    //PerformanceTracker perfTracker = PerformanceTracker.getSceneTracker(scene);
                    //System.out.println(perfTracker.getInstantFPS());
                    
                    if (vivo == true) {
                        distancia += currentSpeed/2;
                        hero.posX -= heroCurrentSpeed;
                        groupHero.setLayoutX(hero.posX);
                    }
                    
                    suelo1.setX(suelo1X);
                    suelo2.setX(suelo2X);

                    sky1.setX(sky1X);
                    sky2.setX(sky2X);

                    suelo1X -= currentSpeed;
                    suelo2X -= currentSpeed;

                    sky1X -= CIELO_SPEED;
                    sky2X -= CIELO_SPEED;

                    textDistancia.setText(distancia/10+" m");

                    switch (Math.round(distancia/10)) {
                        case 50:
                            distanciaEnemigos = 15;
                            break;
                        case 100:
                            distanciaEnemigos = 10;
                            break;
                        case 200:
                            distanciaEnemigos = 5;
                            break;

                    }

                    if (enemyBool == true) {
                        //System.out.println(tocado);
                        if (ataque == true) {
                            boolean colisionVaciaSpider = colisionAtaque(hero.rectAtaque,rectangleSpider);
                            boolean colisionVaciaBat = colisionAtaque(hero.rectAtaque,rectangleBat);
                            boolean colisionVaciaCyclop = colisionAtaque(hero.rectAtaque, rectangleCyclop);
                            if (colisionVaciaSpider == false) {
                                enemyHP -= enemySpider.dmgRecibido;
                                tocado = 10;
                            } 
                            if (colisionVaciaBat == false) {
                                enemyHP -= enemyBat.dmgRecibido;
                                tocado = 10;
                            }
                            if (colisionVaciaCyclop == false) {
                                enemyHP -= enemyCyclop.dmgRecibido;
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
                        
                        colisionDmg(hero.rect, rectangleSpider,enemySpider.dmgGive);
                        colisionDmg(hero.rect, rectangleBat,enemyBat.dmgGive);
                        colisionDmg(hero.rect, rectangleCyclop,enemyCyclop.dmgGive);

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
                        enemyBool = true;
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
                        
                    }

                    if (hero.vida <= 0) {
                        if (vivo == true) {
                            //vbox.getChildren().add(textDistancia);
                            root.getChildren().add(vbox);
                            vivo = false;
                        }
                        currentSpeed = 1;
                        groupHero.setVisible(false);
                    }
                    if (distancia/10 % 200 == 0 && distancia != 0 && pocionBool == false) {

                        groupPocion.getChildren().addAll(circlePotion,rectPotion1,rectPotion2);
                        groupPocion.setLayoutX(pocionX);
                        pocionBool = true;
                        //System.out.println(pocionBool);

                    }

                    if (pocionBool == true)  {
                        pocionX -= currentSpeed;
                        groupPocion.setLayoutX(pocionX);
                        colisionPocion(hero.rect,circlePotion);

                    }
                }
            })
        );       
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        
        //Teclado
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) { 
                //andar hacia la derecha
                groupHero.setScaleX(1);
                heroParado.setVisible(false);
                heroRun.setVisible(true);
                heroAtaque.setVisible(false);
                if (hero.posX <= heroePosX) {
                    //suelo no se mueve, heroe se mueve.
                    currentSpeed = 0;
                    heroCurrentSpeed =-SPEED;
                    
                } else {
                    //suelo se mueve, heroe no se mueve.
                    heroCurrentSpeed = 0;
                    currentSpeed = SPEED;
                    enemySpeed = SPEED + 2;
                }
            } else if(key.getCode()==KeyCode.LEFT) {
                //andar hacia la izquierda
                groupHero.setScaleX(-1);
                heroParado.setVisible(false);
                heroRun.setVisible(true);
                heroAtaque.setVisible(false);
                currentSpeed = 0;
                if (hero.posX <= 1){
                    heroCurrentSpeed = 0;
                } else {
                    heroCurrentSpeed = SPEED;
                }  
            } else if(key.getCode()==KeyCode.A) {
                heroParado.setVisible(false);
                heroRun.setVisible(false);
                heroAtaque.setVisible(true);
                currentSpeed = 0;
                heroCurrentSpeed = 0;
                ataque = true;
            } else if(key.getCode()==KeyCode.R) {
                this.reinicio();
                root.getChildren().remove(vbox);
            }      
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                heroParado.setVisible(true);
                heroRun.setVisible(false);
                currentSpeed = 0;
                heroCurrentSpeed = 0;
                enemySpeed = 2;
            } else if(key.getCode()==KeyCode.LEFT) {
                heroParado.setVisible(true);
                heroRun.setVisible(false);
                currentSpeed = 0;
                heroCurrentSpeed = 0;
            } else if(key.getCode()==KeyCode.A) {
                heroParado.setVisible(true);
                heroRun.setVisible(false);
                heroAtaque.setVisible(false);
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