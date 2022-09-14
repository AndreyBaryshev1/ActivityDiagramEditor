package org.bist.activitydiagram.Elements.ElementType;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import org.bist.activitydiagram.Path;

import java.io.Serializable;

/**
 * Class for transition
 */
public class Transition extends Group implements Serializable {
    transient private final Line line;
    public Element from;
    public Element to;
    transient private final Text text = new Text();
    private static final double arrowLength = 20;
    private static final double arrowWidth = 7;

    public Transition(Line line, Line arrow1, Line arrow2,Polygon triangle, Element parent, Element child) {
        super(line, arrow1, arrow2,triangle);
        this.from = parent;
        this.to = child;
        this.line = line;

        InvalidationListener updater = o -> {
            double ex = getEndX();
            double ey = getEndY();
            double sx = getStartX();
            double sy = getStartY();
            text.setY((ey + sy) * 0.5f);
            text.setX((ex + sx) * 0.5f);
            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);
            if (ex == sx && ey == sy) {
                arrow1.setStartX(ex);
                arrow1.setStartY(ey);
                arrow2.setStartX(ex);
                arrow2.setStartY(ey);
            } else {
                double hypot = Math.hypot(sx - ex, sy - ey);
                double factor = arrowLength / hypot;
                double factorO = arrowWidth / hypot;
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;
                arrow1.setStartX(ex + dx - oy);
                arrow1.setStartY(ey + dy + ox);
                arrow2.setStartX(ex + dx + oy);
                arrow2.setStartY(ey + dy - ox);
            }
            triangle.getPoints().addAll(arrow1.getEndX(), arrow1.getEndY(),
                    arrow1.getStartX(), arrow1.getStartY(),
                    arrow2.getStartX(), arrow2.getStartY());
            triangle.setFill(Color.BLACK);
        };
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    }
    public final double getStartX() {
        return line.getStartX();
    }
    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }
    public final double getStartY() {
        return line.getStartY();
    }
    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }
    public final double getEndX() {
        return line.getEndX();
    }
    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }
    public final double getEndY() {
        return line.getEndY();
    }
    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }

    /**
     * @param parent block
     * @param child block
     * @return path from parent to child
     */
    public static Path<Point2D, Point2D> getArrowPoints(Element parent, Element child) {
        var fromPoints = parent.getArrayOfMinMaxPoints();
        var toPoints = child.getArrayOfMinMaxPoints();

        Point2D pointFromFinal = Point2D.ZERO;
        Point2D pointToFinal = Point2D.ZERO;
        double lowestDistance = Double.POSITIVE_INFINITY;

        for (Point2D fromPoint : fromPoints) {
            for (Point2D toPoint : toPoints) {
                var newDistance = fromPoint.distance(toPoint);
                if (newDistance < lowestDistance) {
                    pointFromFinal = fromPoint;
                    pointToFinal = toPoint;
                    lowestDistance = newDistance;
                }
            }
        }

        return new Path<>(pointFromFinal, pointToFinal);
    }
}