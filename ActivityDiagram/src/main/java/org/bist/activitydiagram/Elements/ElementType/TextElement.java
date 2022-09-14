package org.bist.activitydiagram.Elements.ElementType;


import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.bist.activitydiagram.Dragger;


/**
 * Class for text
 */
public class TextElement extends Element{
    public TextElement(Point2D point, String text, Dragger onDrag) {
        super(ElementType.TEXT, point,text, onDrag);
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

        float lineWidth = 2f;
        Rectangle clip = new Rectangle(
                lineWidth,
                lineWidth,
                textWidth*1.3 + offset - lineWidth * 2,
                textHeight*2 + offset - lineWidth * 2);
        point = new Point2D(getTranslateX(), getTranslateY());
        width = clip.prefWidth(-1);
        height = clip.prefHeight(-1);
        clip.setFill(Color.TRANSPARENT);
        getChildren().add(clip);
        getChildren().add(showText);
        getChildren().add(editableText);
        showText.setTranslateX(offset * 0.5f);
        showText.setTranslateY(offset * 0.5f);
    }
}
