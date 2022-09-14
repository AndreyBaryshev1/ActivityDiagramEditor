package org.bist.activitydiagram.Elements.ElementType;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.bist.activitydiagram.Dragger;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class for Elements
 */
public class Element extends Group implements Serializable {
    transient public ArrayList<Dragger> listeners = new ArrayList<>();
    transient protected TextField editableText;
    public String text;
    protected double width;
    transient protected Label showText;
    protected double height;
    transient protected Point2D point;
    public double pX;
    public double pY;
    public ElementType type;
    private double mouseAnchorX;
    private double mouseAnchorY;

    public Element(ElementType type, Point2D point, String text, Dragger onDrag)
    {
        this.text=text;
        pX=point.getX();
        pY=point.getY();
        this.type=type;
        float div=0;
        if(type == ElementType.END || type == ElementType.START)
            div = 16;
        setTranslateX(point.getX());
        setTranslateY(point.getY());

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
                draw();
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
                draw();
            }
        });
        showText.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        editableText.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-border-color: transparent; -fx-background-color:transparent;  -fx-faint-focus-color: -fx-control-inner-background ;");
        showText.setAlignment(Pos.BASELINE_CENTER);
        this.point = point;
        pX = this.point.getX();
        pY = this.point.getY();
        this.width = computePrefWidth(-1);
        this.height = computePrefHeight(-1);
        editableText.setVisible(false);
        getChildren().add(showText);
        getChildren().add(editableText);
        float finalDiv = div;
        setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });
        listeners.add(onDrag);

        setOnMouseDragged(mouseEvent -> {
            double xBound=0,yBound=0;
            if(type==ElementType.END || type==ElementType.START)
            {
                xBound=getWidth()/2;
                yBound=getHeight()/2;
            }
            setTranslateX(Math.max(getTranslateX() + mouseEvent.getX() - mouseAnchorX, xBound));
            setTranslateY(Math.max(getTranslateY() + mouseEvent.getY() - mouseAnchorY, yBound+25f));

            this.point = new Point2D(getTranslateX()-finalDiv, getTranslateY()-finalDiv);
            pX=this.point.getX();
            pY=this.point.getY();
            for (Dragger listener : listeners) {
                listener.onDrag();
            }
        });
    }

    /**
     * Visualization
     */
    public void draw() {}

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public Point2D getPosition() {
        return point;
    }
    public double minY() {
        return getPosition().getY();
    }

    public double minX() {
        return getPosition().getX();
    }

    public double maxY() {
        return getPosition().getY() + getHeight();
    }

    public double maxX() {
        return getPosition().getX() + getWidth();
    }

    public ArrayList<Point2D> getArrayOfMinMaxPoints() {
        ArrayList<Point2D> fromPoints = new ArrayList<>();
        fromPoints.add(new Point2D(maxX(), maxY() - getHeight() * 0.5));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.5, minY()));
        fromPoints.add(new Point2D(maxX() - getWidth() * 0.5, maxY()));
        fromPoints.add(new Point2D(minX(), maxY() - getHeight() * 0.5));

        return fromPoints;
    }

    /**
     * @param element equable
     * @return result of operation
     */
    public boolean Equals(Element element)
    {
        return this.text.equals(element.text) && this.pX == element.pX && this.pY == element.pY && this.type == element.type;
    }

}

