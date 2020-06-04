/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import javafxemailclientcourse.EmailManager;
import javafxemailclientcourse.view.ViewFactory;

/**
 *
 * @author Larry
 */
public class MainWindowController extends BaseController{

    @FXML
    private TableView<?> emailsTableView;
    @FXML
    private TreeView<?> emailsTreeView;
    @FXML
    private WebView emailWebView;
    
    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName){
        super(emailManager, viewFactory, fxmlName);
    }
    
    @FXML
    private void optionsAction() {
        viewFactory.showOptionsWindow();
    }

    @FXML
    private void addAccountAction(ActionEvent event) {//the email client offers the possibility to login more than one account
        viewFactory.showLoginWindow();
    }
    
}
