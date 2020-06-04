/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller;

import javafxemailclientcourse.EmailManager;
import javafxemailclientcourse.view.ViewFactory;

/**
 *
 * @author Larry
 */
public abstract class BaseController {
    
    protected EmailManager emailManager;
    protected ViewFactory viewFactory;    
    private String fxmlName;//indication to fxml file
    
    public BaseController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
