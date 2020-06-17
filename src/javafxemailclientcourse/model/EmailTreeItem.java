/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.model;

import javafx.scene.control.TreeItem;

/**
 *
 * @author Larry
 */
public class EmailTreeItem<String> extends TreeItem<String>{
    private String name;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
    }
    
}
