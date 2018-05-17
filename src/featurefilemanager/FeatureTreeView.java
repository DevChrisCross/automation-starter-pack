/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package featurefilemanager;

import static featurefilemanager.FileDirectoryManager.getFilesOfDirectory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Chris
 */
public class FeatureTreeView{
    
    public static void buildFileDirectory(
            TreeView<File> treeView, 
            File directory,
            TabPane tabPane) 
            throws IOException 
    {
        String rootDirectoryURI = directory.getCanonicalPath();
        Path rootDirectoryPath = Paths.get(rootDirectoryURI);
            
        treeView.setRoot(getFilesOfDirectory(rootDirectoryPath.toFile()));
        treeView.setEditable(true);
        treeView.setCellFactory((TreeView<File> tView) -> {
            FeatureTreeCell ftCell = new FeatureTreeCell(tabPane);
            return ftCell;
        });
    }
    
    
}
