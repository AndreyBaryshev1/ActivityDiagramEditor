package org.bist.activitydiagram;

import org.bist.activitydiagram.Elements.ElementsAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * class for file
 */
public class DiagramFile {

    /**
     * @param file for saving
     * @param adapter for list of elements
     * @throws IOException for file
     */
    public static void save(File file, ElementsAdapter adapter) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream outStream = new ObjectOutputStream(fos);
        outStream.writeObject(adapter);
        outStream.flush();
        outStream.close();
        System.out.println("Сохранено!");
    }
}
