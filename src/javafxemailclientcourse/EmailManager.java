/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;
import javafxemailclientcourse.controller.services.FetchFoldersServive;
import javafxemailclientcourse.controller.services.FolderUpdaterService;
import javafxemailclientcourse.model.EmailAccount;
import javafxemailclientcourse.model.EmailMessage;
import javafxemailclientcourse.model.EmailTreeItem;
import javax.mail.Flags;
import javax.mail.Folder;

/**
 *
 * @author Larry
 */
/*this class will hold the information about the program state and data
Here we have access to all folders*/
public class EmailManager {
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");
    private FolderUpdaterService folderUpdaterService;
    
    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;
    
    private List<Folder> folderList = new ArrayList<>();
    public List<Folder> getFolderList(){
        return this.folderList;
    }
    
    /*use the folderUpdaterService to update the folder*/
    public EmailManager(){
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }
    
    public EmailTreeItem<String> getFoldersRoot(){
        return foldersRoot;
    }
    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
//        treeItem.setExpanded(true);
//        treeItem.getChildren().add(new TreeItem<>("Inbox"));
//        treeItem.getChildren().add(new TreeItem<>("Sent"));
        FetchFoldersServive fetchFoldersService = new FetchFoldersServive(emailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }
/*set message to read when it is read and decrement number of unread messages*/
    public void setRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementMessagesCount();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setUnRead() {
        try {
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementMessagesCount();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void deleteSelectedMessage() {
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
