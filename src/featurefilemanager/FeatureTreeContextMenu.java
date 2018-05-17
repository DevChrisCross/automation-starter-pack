/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package featurefilemanager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author Chris
 */
public class FeatureTreeContextMenu extends ContextMenu{
    
    private final FeatureTreeCell ftCell;
    
    public FeatureTreeContextMenu(FeatureTreeCell ftCell) {
        this.ftCell = ftCell;
//        this.setStyle("-fx-max-width: 1000;");
//        this.setPrefWidth(200.0);
//        this.setMinWidth(USE_PREF_SIZE);
//        this.setMaxWidth(USE_PREF_SIZE);
//        this.setWidth(1000.0);
//        this.setPrefWidth(1000.0);
        // TODO: transfer rename to context menu
        // TODO: prevent double file tree creation
      
        MenuItem addFeatureFileItem = new MenuItem("Feature File");
        MenuItem addFolderItem = new MenuItem("Folder");
        MenuItem editItem = new MenuItem("Edit");
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        MenuItem renameItem = new MenuItem("Rename");
        MenuItem deleteItem = new MenuItem("Delete");
        Menu newMenu = new Menu("New", null, addFeatureFileItem, addFolderItem);

        addFeatureFileItem.setOnAction((ActionEvent event) -> { generateFile(event, "feature"); });
        addFolderItem.setOnAction((ActionEvent event) -> { generateFile(event, "folder"); });
        renameItem.setOnAction((ActionEvent event) -> { generateFile(event, "rename"); });
        deleteItem.setOnAction((ActionEvent event) -> { deleteFile(); });
        
        KeyCombination cutShorcut = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        KeyCombination renameShorcut = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        KeyCombination deleteShorcut = new KeyCodeCombination(KeyCode.DELETE);
        
        renameItem.setAccelerator(renameShorcut);
        deleteItem.setAccelerator(deleteShorcut);
        
        this.getItems().addAll(
                newMenu, editItem,
                new SeparatorMenuItem(),
                cutItem, copyItem, pasteItem,
                new SeparatorMenuItem(),
                renameItem, deleteItem
        );
    }
    
    private void generateFile(ActionEvent event, String key) {
        Optional<String> fileName = askNameOf(key);
        fileName.ifPresent((String name) -> {
            switch(key) {
                case "feature":
                    createNewFeatureFile(name);
                    break;
                case "folder":
                    createNewFolder(name);
                    break;
                case "rename":
                    renameFile(name);
                    break;
            }           
        });
    }
    
    private Optional<String> askNameOf(String name) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setGraphic(null);
        textInputDialog.setHeaderText(null);
        textInputDialog.setTitle(
                Character.toUpperCase(name.charAt(0)) + name.substring(1)
                + " file"
        );
        textInputDialog.setContentText("Enter file name: ");
        if(name.equals("rename")) {
            textInputDialog.getEditor()
                    .setText(ftCell.getTreeItem().getValue().getName());
        }
        return textInputDialog.showAndWait();
    }
    
    private void createNewFeatureFile(String name) {
        TreeItem<File> currentTreeItem = ftCell.getTreeItem();
        String currentPath = currentTreeItem.getValue().getAbsolutePath();
        name = name.endsWith(".feature") ? name : name + ".feature";
        File newFile = new File(currentPath + "\\" + name);
        TreeItem<File> newFeatureFile = new TreeItem<>(newFile);
        currentTreeItem.getChildren().add(newFeatureFile);
        try {
            newFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(FeatureTreeView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createNewFolder(String name) {
        TreeItem<File> currentTreeItem = ftCell.getTreeItem();
        String currentPath = currentTreeItem.getValue().getAbsolutePath();
        File newFolder = new File(currentPath + "\\" + name);
        TreeItem<File> newFolderFile = new TreeItem<>(newFolder);
        currentTreeItem.getChildren().add(newFolderFile);
        newFolder.mkdir();
    }
    
    private void renameFile(String name) {
        File renamedFile = new File(name);
        ftCell.getTreeItem().getValue().renameTo(renamedFile);
        ftCell.getTreeItem().setValue(renamedFile);
    }
    
    private void deleteFile() {
        TreeItem<File> currentTreeItem = ftCell.getTreeItem();
        File currentFile = currentTreeItem.getValue();
        boolean isItemRemoved = currentTreeItem.getParent()
                .getChildren().remove(currentTreeItem);
        if(isItemRemoved) {
            currentFile.delete();
        }
    }
}
