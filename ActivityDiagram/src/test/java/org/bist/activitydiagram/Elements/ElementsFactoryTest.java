package org.bist.activitydiagram.Elements;

import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import org.bist.activitydiagram.Elements.ElementType.Element;
import org.bist.activitydiagram.Elements.ElementType.ElementType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;


@RunWith(JfxRunner.class)
class ElementsFactoryTest {

    @Test
    void createState() {
        JFXPanel fxPanel = new JFXPanel();
        ElementsFactory elementsFactory = new ElementsFactory();
        Element element = elementsFactory.createState(ElementType.STATE,new Point2D(0,0),"d",()->{});
        Assert.assertEquals(element.type,ElementType.STATE);
        Assert.assertEquals(element.text,"d");
   }
}