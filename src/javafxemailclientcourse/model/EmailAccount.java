/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.model;

import java.util.Properties;
import javax.mail.Store;

/**
 *
 * @author Larry
 */
public class EmailAccount {

    private String address;
    private String password;
    private Properties properties;
    private Store store;//use for retrieving and sending messages

    public EmailAccount(String address, String password) {
        this.address = address;
        this.password = password;
        properties = new Properties();
        /*optons used for sending and receiving emails*/
        properties.put("incomingHost", "imap.gmail.com");//imaps protocol for sending emails
        properties.put("mail.store.protocol", "imaps");

        properties.put("mail.transport.protocol", "smtps");//smtp secure protocol for retrieving email
        properties.put("mail.smtps.host", "smtp.gmail.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "smtp.gmail.com");

    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProperties() {
        return properties;
    }

    public Store getStore() {
        return store;
    }
    
    /*no need for setters for password and address because they are unique*/
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
