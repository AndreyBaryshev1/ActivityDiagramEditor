package org.bist.activitydiagram.Elements;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

/**
 * Class for divides
 */
public class Divide extends Group implements Serializable {
    transient double width=0;
    transient protected TextField editableText;
    transient protected Label showText;
    public String text;

    public Divide(String title)
    {
        this.text=title;
        showText = new Label(text);
        editableText= new TextField(text);
        showText.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                this.text = showText.getText();
                editableText.setText(this.text);
                showText.setVisible(false);
                editableText.setVisible(true);
                editableText.toFront();
                editableText.requestFocus();
            }
        });
        editableText.setOnKeyTyped(e -> this.text=editableText.getText());
        editableText.focusedProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue) {
                showText.setVisible(true);
                editableText.setVisible(false);
                showText.setText(this.text);
                showText.toFront();
            }
        });
        editableText.setOnKeyReleased(e -> {
            this.text=editableText.getText();
            if(e.getCode() == KeyCode.ENTER)
            {
                showText.setVisible(true);
                editableText.setVisible(false);
                showText.setText(this.text);
                showText.toFront();
            }
        });

        showText.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        editableText.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        showText.setAlignment(Pos.BASELINE_CENTER);
        editableText.setVisible(false);
        getChildren().add(showText);
        getChildren().add(editableText);
    }

    public double getWidth() {
        return width;
    }

    /** Visualization
     * @param count of divides
     * @param zeroX start dot on X-coordinates
     * @param width of divide
     * @param height of divide
     */
    public void draw(int count,double zeroX,double width,double height)
    {
        showText.applyCss();
        showText.layout();
        editableText.layout();
        editableText.applyCss();
        getChildren().clear();
        float offset = 25f;
        var textHeight = showText.getPrefHeight();

        Rectangle rect = new Rectangle(
                zeroX,
                0,
                width/count,
                textHeight + offset);

        Rectangle clip = new Rectangle(
                zeroX,
                textHeight + offset,
                width/count,
                height);
        rect.setFill(Color.WHITE);
        clip.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);
        clip.setStroke(Color.BLACK);
        toBack();
        getChildren().add(rect);
        getChildren().add(clip);
        getChildren().add(showText);
        getChildren().add(editableText);
        editableText.setTranslateX(rect.getX()+rect.getWidth()/2-offset);
        editableText.setTranslateY(offset * -0.2f);
        showText.setTranslateX(rect.getX()+rect.getWidth()/2-offset);
        showText.setTranslateY(offset * -0.1f);
        this.width=width/count;
    }
}
