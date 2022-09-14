package org.bist.activitydiagram.Elements.ElementType;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.bist.activitydiagram.Dragger;

/**
 * Class for Starts
 */
public class StartState extends Element{


    public StartState(Point2D point, String text, Dragger onDrag) {
        super(ElementType.START,point,text,onDrag);
    }

    /**
     * Visualization
     */
    public void draw()
    {
        getChildren().clear();

        float offset = 10f;
        Circle circle = new Circle(
                0,
                0,
                offset + 6);
        point = new Point2D(getTranslateX()-circle.getRadius() , getTranslateY()-circle.getRadius());
        width = circle.prefWidth(-1);
        height = circle.prefHeight(-1);
        circle.setFill(Color.BLACK);
        getChildren().add(circle);
    }
}
