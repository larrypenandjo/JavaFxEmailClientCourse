/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Larry
 */
//this class is a customized TreeItem offering more possibilities than a normal TreeItem to store emails
public class EmailTreeItem<String> extends TreeItem<String> {//emails are also going to be stored here

    private String name;//name of an item in the TreeItem
    private ObservableList<EmailMessage> emailMessages;
    private int unreadMessagesCount;//to display the number of unread message on the folder name

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.emailMessages = FXCollections.observableArrayList();//there is no constructor for the ObservableList since its an Interface-->no constructor for Interfaces
    }
    
    public ObservableList<EmailMessage>getEmailMessages(){
        return emailMessages;
    }

    public void addEmail(Message message) throws MessagingException {
        EmailMessage emailMessage = fetchMessage(message);
        emailMessages.add(emailMessage);        
    }

    public void addEmailToTop(Message message) throws MessagingException {
        EmailMessage emailMessage = fetchMessage(message);
        emailMessages.add(0, emailMessage);
    }
    
    private EmailMessage fetchMessage(Message message) throws MessagingException {
        boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
        EmailMessage emailMessage = new EmailMessage(message.getSubject(),
                message.getFrom()[0].toString(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSize(),
                message.getSentDate(),
                messageIsRead,
                message
        );
        emailMessages.add(emailMessage);
        if(!messageIsRead){
            incrementMessagesCount();
        }
        return emailMessage;
    }

    public void incrementMessagesCount() {
        unreadMessagesCount++;
        updateName();
    }
    
    public void decrementMessagesCount() {
        unreadMessagesCount--;
        updateName();
    }

    private void updateName() {
        if (unreadMessagesCount > 0) {
            this.setValue((String) (name + "(" + unreadMessagesCount + ")"));
        } else {
            this.setValue(name);
        }
    }
}
