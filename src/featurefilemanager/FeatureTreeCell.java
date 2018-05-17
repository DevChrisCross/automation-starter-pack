/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package featurefilemanager;

import java.io.File;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Chris
 */
public final class FeatureTreeCell extends TreeCell<File> {

    private TextField textField;
    private final ContextMenu contextMenu;
    
    public FeatureTreeCell() {
        contextMenu = new FeatureTreeContextMenu(this);
    }   
       
    @Override
    public void startEdit() {
        super.startEdit();
        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().getName());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getItem().getName());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getItem().getName());
                setGraphic(getTreeItem().getGraphic());
                if (getItem().isDirectory()){
                    setContextMenu(contextMenu);
                }
            }
        }
    }
   
    private void createTextField() {
        textField = new TextField(getItem().getName());
        textField.setOnKeyReleased((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                File renamedFile = new File(textField.getText());
                getItem().renameTo(renamedFile);
                commitEdit(renamedFile);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }
}
