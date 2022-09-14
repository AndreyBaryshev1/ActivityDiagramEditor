package org.bist.activitydiagram;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import org.bist.activitydiagram.Elements.Divide;
import org.bist.activitydiagram.Elements.ElementType.Element;
import org.bist.activitydiagram.Elements.ElementType.ElementType;
import org.bist.activitydiagram.Elements.ElementType.Transition;
import org.bist.activitydiagram.Elements.ElementsAdapter;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller
 */
public class HelloController implements Initializable {

    public Pane diagram;
    public BorderPane borderPane;
    Element[] selectedElements = new Element[2];
    ElementsAdapter adapter;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adapter = new ElementsAdapter(this::drawTransitions);
        initDivides();
    }

    /**
     * delete a divide
     */
    @FXML
    private void deleteDivide()
    {
        if(adapter.getDivides().size()>1) {
            Divide deleting = adapter.getDivides().get(adapter.getDivides().size() - 1);
            diagram.getChildren().remove(deleting);
            adapter.getDivides().remove(deleting);
            drawDivides();
        }
    }

    /**
     * start bundle of divides
     */
    @FXML
    private void initDivides()
    {
        addDivide("actor");
        diagram.widthProperty().addListener(e ->{
            double offset1=0;
            for (Divide divide1: adapter.getDivides()) {
                divide1.draw(adapter.getDivides().size(),offset1,diagram.getWidth(),diagram.getHeight());
                offset1 =offset1+ divide1.getWidth();
            }
        });
        diagram.heightProperty().addListener(e ->{
            double offset1=0;
            for (Divide divide1: adapter.getDivides()) {
                divide1.draw(adapter.getDivides().size(),offset1,diagram.getWidth(),diagram.getHeight());
                offset1 =offset1+ divide1.getWidth();
            }
        });
    }

    /** save on file
     * @throws IOException fileExeption
     */
    @FXML
    private void saveFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите папку для сохранения...");
        fileChooser.setInitialFileName("Диаграмма");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл", "*.adm"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) DiagramFile.save(file,adapter);
    }

    /** open a file
     * @throws IOException fileException
     * @throws ClassNotFoundException classException
     */
    @FXML
    private void openFile() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл диаграммы деятельности");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Диаграмма деятельности", "*.adm");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        if (file!=null)
            readFile(file);
    }

    /**
     * delete lists of elements
     */
    @FXML
    public void clearAllElements()
    {
        diagram.getChildren().removeAll(adapter.getElements());
        diagram.getChildren().removeAll(adapter.getDivides());
        String text=adapter.getDivides().get(0).text;
        diagram.getChildren().removeAll(adapter.getTransitions());
        adapter.clear();
        addDivide(text);
    }

    /** read data from file
     * @param file readable
     * @throws IOException fileException
     * @throws ClassNotFoundException classException
     */
    public void readFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream inputStream = new ObjectInputStream(fis);
        ElementsAdapter temp=(ElementsAdapter) inputStream.readObject();
        for (Element element:temp.getElements())
            drawElement(adapter.addElement(element.type,new Point2D(element.pX,element.pY),element.text));
        for (Transition transition:temp.getTransitions()) {
            Element[] connected = new Element[2];
            for (Element element:adapter.getElements()) {
                if (element.Equals(transition.from))
                    connected[0] = element;
                if (element.Equals(transition.to))
                    connected[1] = element;
            }
            addTransition(connected[0], connected[1]);
        }
        for (Divide divide:temp.getDivides())
            addDivide(divide.text);
        inputStream.close();
    }

    /**
     * @throws IOException fileException
     */
    @FXML
    private void screenshot() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображения (*.png)", "*.png"));
        fileChooser.setInitialFileName("Диаграмма деятельности");
        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            WritableImage snapShot = diagram.snapshot(new SnapshotParameters(), null);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(snapShot, null);
            ImageIO.write(renderedImage, "png", file);
        }
    }

    /**
     * Visualization
     */
    private void drawDivides()
    {
        double offset1=0;
        for (Divide divide1: adapter.getDivides()) {
            divide1.draw(adapter.getDivides().size(),offset1,diagram.getWidth(),diagram.getHeight());
            offset1 =offset1+ divide1.getWidth();
        }
    }

    /**
     * @param text add new divide
     */
    private void addDivide(String text)
    {
        Divide divide = adapter.addDivide(text);
        diagram.getChildren().add(divide);
        divide.draw(adapter.getDivides().size(),0,504,375);
        drawDivides();
    }

    /**
     * add start block
     */
    @FXML
    private void addStartElement()
    {
        drawElement(adapter.addElement(ElementType.START,new Point2D(30,60),""));
    }

    /**
     * add end block
     */
    @FXML
    private void addEndElement()
    {
        drawElement(adapter.addElement(ElementType.END,new Point2D(30,60),""));
    }

    /**
     * add branch block
     */
    @FXML
    private void addBranchElement()
    {
        drawElement(adapter.addElement(ElementType.BRANCH,new Point2D(30,60),"Ветвление"));
    }

    /**
     * add action block
     */
    @FXML
    private void addActionElement()
    {
        drawElement(adapter.addElement(ElementType.ACTION,new Point2D(30,60),"Действие"));
    }

    /**
     * add state block
     */
    @FXML
    private void addStateElement()
    {
        drawElement(adapter.addElement(ElementType.STATE,new Point2D(30,60),"Состояние"));
    }

    /**
     * add synchronize block
     */
    @FXML
    private void addSynchronizeElement()
    {
        drawElement(adapter.addElement(ElementType.SYNCHRONIZE,new Point2D(30,60),""));
    }

    /**
     * add text block
     */
    @FXML
    private void addTextElement()
    {
        drawElement(adapter.addElement(ElementType.TEXT,new Point2D(30,60),"Текст"));
    }

    /** Visualization
     * @param element for visualization
     */
    private void drawElement(Element element)
    {
        diagram.getChildren().add(element);
        element.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.isControlDown()) {
                diagram.getChildren().remove(element);
                adapter.getElements().remove(element);
            }
            else if(mouseEvent.isShiftDown())
                if(selectedElements[0] == null)
                    selectedElements[0]=element;
                else if(selectedElements[1] == null) {
                    selectedElements[1] = element;
                    addTransition(selectedElements[0], selectedElements[1]);
                    selectedElements = new Element[2];
                }
        });
        element.draw();
    }

    /** add new transition
     * @param parent block
     * @param child block
     */
    private void addTransition(Element parent, Element child)
    {
        var points = Transition.getArrowPoints(parent, child);
        Line line = new Line(points.item1.getX(), points.item1.getY(), points.item2.getX(), points.item2.getY());
        Transition transition = adapter.addTransition(line,parent,child);
        diagram.getChildren().add(transition);
        transition.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.isControlDown()) {
                diagram.getChildren().remove(transition);
                adapter.getTransitions().remove(transition);
            }
        });
    }

    /**
     * Visualization
     */
    private void drawTransitions() {
        diagram.autosize();
        List<Element[]> transitionElements=new ArrayList<>();
        for (Transition transition : adapter.getTransitions()) {
            transitionElements.add(new Element[]{transition.from, transition.to});
            diagram.getChildren().remove(transition);
        }
        adapter.getTransitions().clear();
        for (Element[] connections : transitionElements)
            addTransition(connections[0], connections[1]);
    }

    /**
     * information for users
     */
    public void help()
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION, """
                Левая панель инструментов предназначена для работы с элементами диаграммы
                Ctrl+ЛКМ - удаление объекта
                Shift+ЛКМ - установка метки на соединение объекта""");
        alert.setHeaderText("Справка");
        alert.setTitle("Справка");
        alert.show();
    }
}