package org.bist.activitydiagram.Elements;

import javafx.geometry.Point2D;
import org.bist.activitydiagram.Dragger;
import org.bist.activitydiagram.Elements.ElementType.*;

/**
 * Class for different types of elements
 */
public class ElementsFactory {

    /**
     * @param type of element
     * @param point2D on diagram
     * @param text of block
     * @param onDrag listener
     * @return element
     */
    public Element createState(ElementType type, Point2D point2D, String text, Dragger onDrag) {

        return switch (type) {
            case ACTION -> new ActionElement(point2D, text, onDrag);
            case STATE -> new StateElement(point2D, text, onDrag);
            case START -> new StartState(point2D, text, onDrag);
            case TEXT -> new TextElement(point2D, text, onDrag);
            case END -> new EndState(point2D, text, onDrag);
            case BRANCH -> new BranchElement(point2D, text, onDrag);
            case SYNCHRONIZE -> new SynchronizeElement(point2D, text, onDrag);
        };
    }
}
