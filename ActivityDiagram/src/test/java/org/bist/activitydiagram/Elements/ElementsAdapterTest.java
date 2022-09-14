package org.bist.activitydiagram.Elements;

import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import org.bist.activitydiagram.Elements.ElementType.ElementType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
class ElementsAdapterTest {

    ElementsAdapter elementsAdapter=new ElementsAdapter(()->{});

    @Test
    void clearTest() {
        JFXPanel fxPanel = new JFXPanel();
        elementsAdapter.addElement(ElementType.TEXT,new Point2D(1,1),"");
        elementsAdapter.clear();
        Assert.assertEquals(elementsAdapter.getElements().size(), 0);
    }

    @Test
    void addElementTest() {
        JFXPanel fxPanel = new JFXPanel();
        elementsAdapter.addElement(ElementType.TEXT,new Point2D(12,1),"2");
        Assert.assertEquals(elementsAdapter.getElements().size(), 1);
    }

    @Test
    void addDivideTest() {
        JFXPanel fxPanel = new JFXPanel();
        elementsAdapter.addDivide("2");
        Assert.assertEquals(elementsAdapter.getDivides().size(), 1);
    }
}