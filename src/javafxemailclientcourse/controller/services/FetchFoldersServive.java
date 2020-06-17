/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafxemailclientcourse.model.EmailTreeItem;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

/**
 *
 * @author Larry
 */
public class FetchFoldersServive extends Service<Void> {

    private Store store;
    private EmailTreeItem<String> foldersRoot;

    public FetchFoldersServive(Store store, EmailTreeItem<String> foldersRoot) {
        this.store = store;
        this.foldersRoot = foldersRoot;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                fetchFolders();
                return null;
            }
        };
    }

    private void fetchFolders() throws MessagingException {
        Folder[] folders = store.getDefaultFolder().list();
        handleFolders(folders, foldersRoot);
    }

    private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) throws MessagingException {
        for(Folder folder: folders){
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
            foldersRoot.getChildren().add(emailTreeItem);
            foldersRoot.setExpanded(true);
            if(folder.getType()==Folder.HOLDS_FOLDERS){ //to display subfolders make sure there some
                Folder[] subFolders = folder.list();
                handleFolders(subFolders, emailTreeItem);
            }
        }
    }
}
