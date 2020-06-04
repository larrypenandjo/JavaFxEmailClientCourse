/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.view;

import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafxemailclientcourse.EmailManager;
import javafxemailclientcourse.controller.BaseController;
import javafxemailclientcourse.controller.LoginWindowController;
import javafxemailclientcourse.controller.MainWindowController;
import javafxemailclientcourse.controller.OptionsWindowController;
import javafxemailclientcourse.view.ColorTheme;
import javafxemailclientcourse.view.FontSize;

/**
 *
 * @author Larry
 */
public class ViewFactory {

    private EmailManager emailManager;
    private ArrayList<Stage>activeStages;
    
    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<Stage>();
    }

    /*View options handling*/
    private ColorTheme colorTheme = ColorTheme.DARK;//in a more advanced program, value are write in persistance
    private FontSize fontSize = FontSize.MEDIUM;

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void showLoginWindow() {
        System.out.println("Show Login window called");

        BaseController controller = new LoginWindowController(emailManager, this, "LoginWindow.fxml");//new LoginWindowController is created by ourself(and not the one of JavaFX) to have more controll on the controller 
        initializeStage(controller);
    }

    public void showMainWindow() {
        System.out.println("Show Main window called");

        BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
        initializeStage(controller);
    }

    public void showOptionsWindow() {
        System.out.println("Show Options window called");

        BaseController controller = new OptionsWindowController(emailManager, this, "OptionsWindow.fxml");
        initializeStage(controller);
    }

    private void initializeStage(BaseController baseController) {//assigment repeat itself in the methods showLoginWindow and showMainWindow
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public void updateStyles() {
        for(Stage stage:activeStages){
            Scene scene = stage.getScene();//need to get the scene of this stage-->styles are applied only to scenes
            scene.getStylesheets().clear();//delete last styles before applying new ones
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }
    }
}
