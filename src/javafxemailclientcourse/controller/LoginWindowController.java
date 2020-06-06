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
import javafxemailclientcourse.controller.services.LoginService;
import javafxemailclientcourse.model.EmailAccount;
import javafxemailclientcourse.view.ViewFactory;

/**
 *
 * @author Larry
 */
public class LoginWindowController extends BaseController {

    @FXML
    private TextField emailAdrdressField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    private void loginBtnAction() {
        if (fieldsAreValid()) {
            EmailAccount emailAccount = new EmailAccount(emailAdrdressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            EmailLoginResult emailLoginResult = loginService.login();

            switch (emailLoginResult) {
                case SUCCESS:
                    System.out.println("login successfull!!!" + emailAccount);
                    return;
            }

        }
        System.out.println("LoginWindowController!!");

        viewFactory.showMainWindow();//viewFactory ist protected attribute of base class BaseController
        Stage current = (Stage) passwordField.getScene().getWindow();
        viewFactory.closeStage(current);
    }

    private boolean fieldsAreValid() {
        if (emailAdrdressField.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        }
        if (passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        }
        return true;
    }

}
