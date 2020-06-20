/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller.services;

import java.io.IOException;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;
import javafxemailclientcourse.model.EmailMessage;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

/**
 *
 * @author Larry
 */
/*visualize message*/
public class MessageRendererService extends Service {

    private EmailMessage emailMessage;
    private WebEngine webEngine;
    private StringBuffer stringBuffer;//holds the content that will be rendrered by the webEngine. content of loaded message is save in stringbuffer and rendered by webEngine

    public MessageRendererService(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();
        this.setOnSucceeded(e->{
            displayMessage();
        });
    }

    public void setEmailMessage(EmailMessage emailMessage) {//setting the email message before loading it
        this.emailMessage = emailMessage;
    }
    
    private void displayMessage(){
        webEngine.loadContent(stringBuffer.toString());
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    loadMessage();
                }catch(Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private void loadMessage() throws MessagingException, IOException {
        stringBuffer.setLength(0);//clears the StringBuffer
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType();
        if (isSimpleType(contentType)) {
            stringBuffer.append(message.getContent().toString());
        } else if (isMultipartType(contentType)) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = multipart.getCount() - 1; i >= 0; i--) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String bodyPartContentType = bodyPart.getContentType();
                if (isSimpleType(bodyPartContentType)) {
                    stringBuffer.append(bodyPart.getContent().toString());
                }
            }
        }
    }

    private boolean isSimpleType(String contentType) {
        if (contentType.contains("TEXT/HTML") || contentType.contains("mixed") || contentType.contains("text")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isMultipartType(String contentType) {
        if (contentType.contains("multipart")) {
            return true;
        } else {
            return false;
        }
    }
}
