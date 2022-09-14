package org.bist.activitydiagram.Elements.ElementType;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.bist.activitydiagram.Dragger;

/**
 * Class for Branches
 */
public class BranchElement extends Element{

    public BranchElement(Point2D point, String text, Dragger onDrag) {
        super(ElementType.BRANCH,point,text,onDrag);
    }

    /**
     * Visualization
     */
    @Override
    public void draw() {
        showText.applyCss();
        showText.layout();
        editableText.layout();
        editableText.applyCss();
        getChildren().clear();

        var textWidth = showText.prefWidth(-1) ;
        var textHeight = showText.prefHeight(-1);

        float offset = 20f;
        float lineWidth = 3f;
        var widthOffset = offset + textWidth * 0.5f;
        var heightOffset = offset + textHeight * 0.5f;

        Polygon poly = new Polygon();
        poly.getPoints().addAll(
                0d, (textHeight + heightOffset) * 0.5f,
                (textWidth  + widthOffset) * 0.5f, 0d,
                textWidth + widthOffset, (textHeight + heightOffset) * 0.5f ,
                (textWidth + widthOffset) * 0.5f, textHeight + heightOffset);

        Polygon clip = new Polygon(
                lineWidth, (textHeight + heightOffset) * 0.5f,
                (textWidth +  widthOffset) * 0.5f, lineWidth,
                textWidth + widthOffset - lineWidth * 2, (textHeight + heightOffset) * 0.5f,
                (textWidth + widthOffset) * 0.5f, textHeight + heightOffset - lineWidth);

        poly.setFill(Color.BLACK);
        clip.setFill(Color.WHITE);

        point = new Point2D(getTranslateX(), getTranslateY());
        width = poly.prefWidth(-1);
        height = poly.prefHeight(-1);

        getChildren().add(poly);
        getChildren().add(clip);
        getChildren().add(showText);
        getChildren().add(editableText);
        showText.setTranslateX(widthOffset * 0.5f);
        showText.setTranslateY(heightOffset * 0.5f);
        editableText.setTranslateX(widthOffset * 0.1f);
        editableText.setTranslateY(heightOffset * 0.4f);
    }
}
