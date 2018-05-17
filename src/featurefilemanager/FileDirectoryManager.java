/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package featurefilemanager;

import java.io.File;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author CCMolina
 */
public class FileDirectoryManager {
    
    public static File askDirectory(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Directory");
        File defaultDirectory = new File("C:/");
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = directoryChooser.showDialog(stage);
        return selectedDirectory;
    }
    
    public static TreeItem<File> getFilesOfDirectory(File fileDirectory) {
        TreeItem<File> rootItem = new TreeItem<>(fileDirectory);
    
        for (File file : fileDirectory.listFiles()) {
            if (file.isDirectory()) {
                rootItem.getChildren().add(getFilesOfDirectory(file));
            } else {
                String fileName = file.getName();
                int dotIndex = fileName.lastIndexOf('.');
                String fileType = fileName.substring(dotIndex + 1);
                if (fileType.equals("feature")) {
                    rootItem.getChildren().add(new TreeItem<>(file));
                }
            }
        }
        return rootItem;
    }
}
