/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxemailclientcourse.EmailManager;
import javafxemailclientcourse.view.ViewFactory;

/**
 *
 * @author Larry
 */
public class LoginWindowController extends BaseController{

    @FXML
    private TextField emailAdrdressField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;
    
    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName){
        super(emailManager, viewFactory, fxmlName);
    }
    
    @FXML
    private void loginBtnAction() {
        System.out.println("LoginWindowController!!");
        
        viewFactory.showMainWindow();//viewFactory ist protected attribute of base class BaseController
        Stage current = (Stage) passwordField.getScene().getWindow();
        viewFactory.closeStage(current);
    }
    
}
