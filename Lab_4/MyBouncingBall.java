package com.example.lab6;
// importam pachetele
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;;

public class MyBouncingBall extends Application {

    @Override
    public void start(Stage stage) {
        //Tipul Canvas
        Pane getsvenrToWork = new Pane();

        //Adaugăm canvasul la ecran
        Scene scene = new Scene (getsvenrToWork, 300, 300);
        e
        //Creăm un obiect cerc
        Circle c = new Circle(30, Color.MEDIUMTURQUOISE);

        int max = 200;
        int min = 50;
        int range = max - min + 1;

        // generam coordonate aleatorii pentru pozitia initiala
        double randomX = (int)(Math.random() * range) + min;
        double randomY = (int)(Math.random() * range) + min;
        c.relocate(randomX, randomY);

        //Adaugam noduri la canvas, care se afla in scena
        getsvenrToWork.getChildren().addAll(c);

        // Adaugam o linie aleatorie la scena
        Line line = new Line(50, 150, 60, 180);
        double lineY = (int) (Math.random() * (scene.getHeight() - line.getStartY()));
        line.setTranslateY(lineY);
        getsvenrToWork.getChildren().add(line);

        //Setam scena in etapa
        stage.setScene(scene);

        //Afisam etapa
        stage.show();
        Timeline time = new Timeline();
        time.getKeyFrames().add(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

            double velocityX = 7;//viteza
            double angleY = 9;//unghiul
            @Override
            public void handle(ActionEvent t) {
                //mișcăm mingea
                c.setLayoutX(c.getLayoutX() + velocityX);
                c.setLayoutY(c.getLayoutY() + angleY);

                Bounds ballBounds = c.getBoundsInParent();
                Bounds lineBounds = line.getBoundsInParent();

                // daca mingea intersecteaza linia, opriti animatia
                if (ballBounds.intersects(lineBounds)) {
                    time.stop();
                }

                Bounds b = getsvenrToWork.getBoundsInLocal();

                //daca mingea atinge partea stanga sau dreapta, facem valoarea negativa si schimbam directia
                if(c.getLayoutX() <= (b.getMinX() + c.getRadius())
                        ||//sau in cazul unei afirmatii
                        c.getLayoutX()  >= (b.getMaxX() - c.getRadius()) )
                {
                    //setam valorile la negativ daca loveste un perete
                    velocityX = -velocityX;
                }
                //daca mingea atinge partea de sus sau de jos, facem cercul negativ si schimbam directia
                if((c.getLayoutY() >= (b.getMaxY() - c.getRadius()))
                        ||
                        (c.getLayoutY()  <= (b.getMinY() + c.getRadius()) ))
                {
                    //setăm valoarea la negativ în caz dacă atinge linia
                    angleY = -angleY;

                } }
        }));

        time.setCycleCount(Timeline.INDEFINITE);
        time.play();}

    public static void main(String[] args) {
        launch(args);
    }}
