/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse;

import javafx.scene.control.TreeItem;
import javafxemailclientcourse.controller.services.FetchFoldersServive;
import javafxemailclientcourse.model.EmailAccount;
import javafxemailclientcourse.model.EmailTreeItem;

/**
 *
 * @author Larry
 */
/*this class will hold the information about the program state and data*/
public class EmailManager {
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");
    
    public EmailTreeItem<String> getFoldersRoot(){
        return foldersRoot;
    }
    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
//        treeItem.setExpanded(true);
//        treeItem.getChildren().add(new TreeItem<>("Inbox"));
//        treeItem.getChildren().add(new TreeItem<>("Sent"));
        FetchFoldersServive fetchFoldersService = new FetchFoldersServive(emailAccount.getStore(), treeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }
}
