package org.bist.activitydiagram.Elements.ElementType;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.bist.activitydiagram.Dragger;

/**
 * Class for State
 */
public class StateElement extends Element{

    public StateElement(Point2D point, String text, Dragger onDrag) {
        super(ElementType.STATE,point,text,onDrag);
    }

    /**
     * Visualization
     */
    @Override
    public void draw()
    {
        showText.applyCss();
        showText.layout();
        editableText.layout();
        editableText.applyCss();

        getChildren().clear();

        var textWidth = showText.prefWidth(-1);
        var textHeight = showText.prefHeight(-1);

        float offset = 10f;
        Rectangle rect = new Rectangle(
                0,
                0,
                textWidth + offset,
                textHeight + offset);

        float lineWidth = 2f;
        Rectangle clip = new Rectangle(
                lineWidth,
                lineWidth,
                textWidth + offset - lineWidth * 2,
                textHeight + offset - lineWidth * 2);


        point = new Point2D(getTranslateX(), getTranslateY());
        width = rect.prefWidth(-1);
        height = rect.prefHeight(-1);

        rect.setArcWidth(15.0);
        rect.setArcHeight(15.0);
        clip.setArcWidth(15.0);
        clip.setArcHeight(15.0);
        rect.setFill(Color.BLACK);
        clip.setFill(Color.WHITE);

        getChildren().add(rect);
        getChildren().add(clip);
        getChildren().add(showText);
        getChildren().add(editableText);
        showText.setTranslateX(offset * 0.5f);
        showText.setTranslateY(offset * 0.5f);
    }
}
