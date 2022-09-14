package org.bist.activitydiagram.Elements.ElementType;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.bist.activitydiagram.Dragger;


/**
 * Class for Ends
 */
public class EndState extends Element{
    public EndState(Point2D point, String text, Dragger onDrag) {
        super(ElementType.END,point,text,onDrag);
    }

    /**
     * Visualization
     */
    public void draw()
    {
        getChildren().clear();
        float offset = 10f;
        Circle inner_circle = new Circle(
                0,
                0,
                3 + offset);

        Circle Mega_outer_circle = new Circle(
                0,
                0,
                offset + 6);
        Circle outer_circle = new Circle(
                0,
                0,
                offset + 5);

        point = new Point2D(getTranslateX()-Mega_outer_circle.getRadius() , getTranslateY()-Mega_outer_circle.getRadius());
        width = Mega_outer_circle.prefWidth(-1);
        height = Mega_outer_circle.prefHeight(-1);

        inner_circle.setFill(Color.BLACK);
        outer_circle.setFill(Color.WHITE);
        Mega_outer_circle.setFill(Color.BLACK);

        getChildren().add(Mega_outer_circle);
        getChildren().add(outer_circle);
        getChildren().add(inner_circle);
    }

}
