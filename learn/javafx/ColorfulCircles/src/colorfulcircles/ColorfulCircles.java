/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colorfulcircles;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import static java.lang.Math.random;

/**
 *
 * @author itang
 */
public class ColorfulCircles extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(ColorfulCircles.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        //主舞台标题
        primaryStage.setTitle("Hello World");
        //布局 道具
        Group root = new Group();
        //场景
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
//        Button btn = new Button();
//        btn.setLayoutX(100);
//        btn.setLayoutY(80);
//        btn.setText("Hello World");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World");
//            }
//        });
//        //增加道具
//        root.getChildren().add(btn);

        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new Stop[]{
                    new Stop(0, Color.web("#f8bd55")),
                    new Stop(0.14, Color.web("#c0fe56")),
                    new Stop(0.28, Color.web("#5dfbc1")),
                    new Stop(0.43, Color.web("#64c2f8")),
                    new Stop(0.57, Color.web("#be4af7")),
                    new Stop(0.71, Color.web("#ed5fc2")),
                    new Stop(0.85, Color.web("#ef504c")),
                    new Stop(1, Color.web("#f2660f"))}));
        // root.getChildren().add(colors);

        Group circles = new Group();
        for (int i = 0; i < 30; i++) {
            Circle circle = new Circle(150, Color.web("white", 0.05));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
        }
        circles.setEffect(new BoxBlur(10, 10, 3));
        //root.getChildren().add(circles);

        Group blendModeGroup = new Group(new Group(
                new Rectangle(scene.getWidth(), scene.getHeight(), Color.BLACK), circles), colors);
        blendModeGroup.setBlendMode(BlendMode.OVERLAY);
        root.getChildren().add(blendModeGroup);


        Timeline timeline = new Timeline();
        for (Node circle : circles.getChildren()) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0
                    new KeyValue<Number>(circle.translateXProperty(), random() * 800),
                    new KeyValue<Number>(circle.translateYProperty(), random() * 600)),
                    new KeyFrame(new Duration(40000), // set end position at 40s
                    new KeyValue<Number>(circle.translateXProperty(), random() * 800),
                    new KeyValue<Number>(circle.translateYProperty(), random() * 600)));
        }
        // play 40s of animation
        timeline.play();

        //舞台上设置场景
        primaryStage.setScene(scene);
        //拉开舞台大幕(可见)
        primaryStage.setVisible(true);
    }
}
