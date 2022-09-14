package org.bist.activitydiagram.Elements.ElementType;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.bist.activitydiagram.Dragger;

import java.util.ArrayList;

/**
 * Class for Synchronize
 */
public class SynchronizeElement extends  Element{
    public SynchronizeElement(Point2D point, String text, Dragger onDrag) {
        super(ElementType.SYNCHRONIZE,point,text,onDrag);
    }

    /**
     * Visualization
     */
    @Override
    public void draw()
    {
        getChildren().clear();
        Rectangle rect = new Rectangle(
                0,
                0,
                100,
                8);
        point = new Point2D(getTranslateX(), getTranslateY());
        width = rect.prefWidth(-1);
        height = rect.prefHeight(-1);
        rect.setFill(Color.BLACK);
        getChildren().add(rect);
    }
    @Override
    public ArrayList<Point2D> getArrayOfMinMaxPoints() {
        ArrayList<Point2D> fromPoints = new ArrayList<>();
        fromPoints.add(new Point2D(maxX(), maxY() - getHeight() * 0.5));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.5, minY()));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.25, minY()));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.75, minY()));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.5, maxY()));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.75, maxY()));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.25, maxY()));
        fromPoints.add(new Point2D(minX(), maxY() - getHeight() * 0.5));

        return fromPoints;
    }
}
