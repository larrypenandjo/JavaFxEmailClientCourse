/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller.services;

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
public class LoginService {

    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    public EmailLoginResult login() {
        Authenticator authenticator = new Authenticator() {//Authenticator represents an object that knows how to obtain authentication for a network connection
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {//authentifies if password and email address are correct
                return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
            }
        };
        /*Accessing the session: Try catch needed because smthg can go wrong anytime*/
        try {
            Session session = Session.getInstance(emailAccount.getProperties(), authenticator);//create a 
            Store store = session.getStore("imaps");
            store.connect(emailAccount.getProperties().getProperty("incomingHost"), emailAccount.getAddress(), emailAccount.getPassword());
            emailAccount.setStore(store);
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
}
