/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package featurefilemanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Chris
 */
public class FeatureTreeView {

    public void populateFeatureDirectory(Stage stage, Scene scene) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Feature Directory");
        File defaultDirectory = new File("C:/");
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = directoryChooser.showDialog(stage);
        
        try {
            String rootDirectoryURI = selectedDirectory.getCanonicalPath();
            Path rootDirectoryPath = Paths.get(rootDirectoryURI);
            int rootDirectoryLevel = rootDirectoryPath.getNameCount();

            TreeView<File> featureTree = (TreeView) scene.lookup("#featureDirectory");
            featureTree.setRoot(getFilesOfDirectory(rootDirectoryPath.toFile()));
            featureTree.setEditable(true);
            featureTree.setCellFactory((TreeView<File> treeView) -> {
                FeatureTreeCell ftcell = new FeatureTreeCell();
                ftcell.setOnMouseClicked((MouseEvent event) -> openFileInTab(event));
                return ftcell;
            });
        } catch (IOException ex) {
            Logger.getLogger(FeatureFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openFileInTab(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 2){

            }
        }
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
