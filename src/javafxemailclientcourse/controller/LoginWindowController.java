/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class LoginWindowController extends BaseController implements Initializable {//immediatly set the login credentials onced the controller is instantiated 

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
        System.out.println("LoginWindowController!!");

        if (fieldsAreValid()) {
            EmailAccount emailAccount = new EmailAccount(emailAdrdressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();//calls the login method defined within the Task in a seperate thread. The login method is then executed in the background Task
            loginService.setOnSucceeded(event -> {//only be called when the task state is succeeded-->in this case when the login method returns the login result

                EmailLoginResult emailLoginResult = loginService.getValue();//getting the return value of the login()-method executed within the service LoginService

                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("login successfull!!!" + emailAccount);
                        if(!viewFactory.isMainViewInitialized()){//to avoid opening a new MainWindow stage when login into a new account
                            viewFactory.showMainWindow();//viewFactory ist protected attribute of base class BaseController
                        }                        
                        Stage current = (Stage) passwordField.getScene().getWindow();
                        viewFactory.closeStage(current);
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("invalid credentials!");
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("unexpected error!");
                        return;
                    default:
                        return;
                }
            });
        }
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //credentials are immediatly set without need of any action or event
        emailAdrdressField.setText("dummytest237@gmail.com");
        passwordField.setText("Dummytest237.");
    }
}
