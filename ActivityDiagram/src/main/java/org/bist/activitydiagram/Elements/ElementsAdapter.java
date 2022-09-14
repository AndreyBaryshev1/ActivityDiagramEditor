package org.bist.activitydiagram.Elements;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import org.bist.activitydiagram.Dragger;
import org.bist.activitydiagram.Elements.ElementType.Element;
import org.bist.activitydiagram.Elements.ElementType.ElementType;
import org.bist.activitydiagram.Elements.ElementType.Transition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for list of elements
 */
public class ElementsAdapter implements Serializable {
    transient private final ElementsFactory stateFactory;
    private final List<Element> elements;
    private final List<Transition> transitions;
    private final List<Divide> divides;
    transient Dragger onDrag;
    public ElementsAdapter(Dragger onDrag)
    {
        this.onDrag = onDrag;
        this.stateFactory = new ElementsFactory();
        elements = new ArrayList<>();
        transitions = new ArrayList<>();
        divides = new ArrayList<>();
    }

    /**
     * delete all elements
     */
    public void clear()
    {
        elements.clear();
        transitions.clear();
        divides.clear();
    }

    /** add Block
     * @param type of block
     * @param point2D on diagram
     * @param text of block
     * @return element
     */
    public Element addElement(ElementType type, Point2D point2D, String text) {
        Element element = stateFactory.createState(type,point2D,text,onDrag);
        elements.add(element);
        return element;
    }

    /**
     * @param text tittle of didvide
     * @return divide
     */
    public Divide addDivide(String text) {
        Divide divide = new Divide(text);
        divides.add(divide);
        return divide;
    }
    public List<Divide> getDivides()
    {
        return divides;
    }

    public List<Transition> getTransitions()
    {
        return transitions;
    }

    /** add transition
     * @param line of transition
     * @param parent element
     * @param children element
     * @return transition
     */
    public Transition addTransition(Line line,Element parent,Element children)
    {
        Transition transition = new Transition(line, new Line(), new Line(),new Polygon(),parent,children);
        transitions.add(transition);
        return transition;
    }
    public List<Element> getElements()
    {
        return elements;
    }
}
