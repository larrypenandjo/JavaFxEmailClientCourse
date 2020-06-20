/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javafxemailclientcourse.EmailManager;
import javafxemailclientcourse.controller.services.MessageRendererService;
import javafxemailclientcourse.model.EmailMessage;
import javafxemailclientcourse.model.EmailTreeItem;
import javafxemailclientcourse.model.SizeInteger;
import javafxemailclientcourse.view.ViewFactory;

/**
 *
 * @author Larry
 */
public class MainWindowController extends BaseController implements Initializable{

    @FXML
    private TableView<EmailMessage> emailsTableView;//for displaying emails or email visualization
    @FXML
    private TreeView<String> emailsTreeView;
    
    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;
    
    @FXML
    private WebView emailWebView;
    
    private MessageRendererService messageRendererService;
    
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpEmailsTreeView();
        setUpEmailsTableView();//setting up the cell values-->these values are hold by the property values define in the bean class EmailMessage
        setUpFolderSelection();//what happen when email folder in treeview is selected
        setUpBoldRows();//unread messges are set bold
        setUpMessageRendererService();//visualize message
        setUpMessageSelection();//the message renderer service shall be executed for all selected messages
    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.getFoldersRoot());//set the root element of the treeview
        emailsTreeView.setShowRoot(false);//this root is set to false because it just an empty element
    }

    private void setUpEmailsTableView() {
        senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("sender"));//pass the name of the bean (sender) as string. it has to be identical
        subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("subject"));
        recipientCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("recipient"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, SizeInteger>("size"));
        dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, Date>("date"));
    }

    private void setUpFolderSelection() {
        emailsTreeView.setOnMouseClicked(e->{
            EmailTreeItem<String> item = (EmailTreeItem<String>)emailsTreeView.getSelectionModel().getSelectedItem();
            if(item!=null){
                emailsTableView.setItems(item.getEmailMessages());
            }
        });
    }

    private void setUpBoldRows() {
        emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>(){
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> param) {
                return new TableRow<EmailMessage>(){
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty){//manually overriding the updateItem method of the Cell class
                        super.updateItem(item, empty);
                        if(item!=null){
                            if(item.isRead()){
                                setStyle("");
                            }else{
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }

    private void setUpMessageRendererService() {
        messageRendererService = new MessageRendererService(emailWebView.getEngine());
    }

    private void setUpMessageSelection() {
        emailsTableView.setOnMouseClicked(e->{
            EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
            if(emailMessage!=null){
                messageRendererService.setEmailMessage(emailMessage);
                messageRendererService.restart();//restart for this service because it can be called many times -->with the start()-method can only be used onced, if called twice an error is going to be thrown
            }
        });
    }
    
}
