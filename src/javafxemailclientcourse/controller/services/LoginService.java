/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafxemailclientcourse.EmailManager;
import javafxemailclientcourse.controller.EmailLoginResult;
import javafxemailclientcourse.model.EmailAccount;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author Larry
 */
/*LoginService extends the class Service-->LoginService is now a service and must implement the inherited code=which is a task
An intantiated Service can be started an stopped many time, this is not the case of a task
The Service runs in a seperate thread in the background, and the application itself in the main Application thread -->this enables concurrency
Difference btw. Task and Service: 
A task can only ran onced, to rerun a task, the complete code has to be executed once more
A Service can be run many time without need of executing the code
*/
public class LoginService extends Service<EmailLoginResult>{

    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    private EmailLoginResult login() {
        Authenticator authenticator = new Authenticator() {//Authenticator represents an object that knows how to obtain authentication for a network connection
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {//authentifies if password and email address are correct
                return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
            }
        };
        /*Accessing the session: Try catch needed because smthg can go wrong anytime*/
        try {
//            Thread.sleep(6000);//create a delay of 6s before starting the logging session
            Session session = Session.getInstance(emailAccount.getProperties(), authenticator);//create a 
            Store store = session.getStore("imaps");
            store.connect(emailAccount.getProperties().getProperty("incomingHost"), emailAccount.getAddress(), emailAccount.getPassword());
            emailAccount.setStore(store);
            emailManager.addEmailAccount(emailAccount);
        } catch (NoSuchProviderException e) {//for line 43
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_NETWORK;
        } catch (AuthenticationFailedException e) {//for line 45
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_CREDENTIALS;
        } catch (MessagingException e) {//for line 44
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
        } catch (Exception e) {//generic error, dont know what it might be
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
        }
        return EmailLoginResult.SUCCESS;//if nothing (no Exception) gets wrong
    }

    @Override
    protected Task<EmailLoginResult> createTask() {//creates a new thread which runs in dependently of the Application Thread
        return new Task<EmailLoginResult>() {
            @Override
            protected EmailLoginResult call() throws Exception {
                return login();
            }
        };
    }
}
