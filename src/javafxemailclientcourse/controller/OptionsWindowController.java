/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafxemailclientcourse.EmailManager;
import javafxemailclientcourse.view.ColorTheme;
import javafxemailclientcourse.view.FontSize;
import javafxemailclientcourse.view.ViewFactory;

/**
 *
 * @author Larry
 */
/*Initializable interface: use to set Slider and ChoiceBox field right after the Controller is instantiated*/
public class OptionsWindowController extends BaseController implements Initializable {

    public OptionsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName){
        super(emailManager, viewFactory, fxmlName);
    }
    
    @FXML
    private Slider fontSizePicker;
    @FXML
    private ChoiceBox<ColorTheme> themePicker;    
    
    @FXML
    private void applyBtnAction() {
        viewFactory.setColorTheme(themePicker.getValue());
        viewFactory.setFontSize(FontSize.values()[(int)fontSizePicker.getValue()]);//int-cast because the getValue() returns a double
//        System.out.println(viewFactory.getColorTheme());
//        System.out.println(viewFactory.getFontSize());
        
        viewFactory.updateStyles();//selected theme and font size are updated on the stage
    }

    @FXML
    private void cancelBtnAction() {
        Stage current = (Stage) fontSizePicker.getScene().getWindow();
        viewFactory.closeStage(current);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpThemePicker(); //sets the items(dropdown menu)of the ChoiceBox right after the controller is instantiated
        setUpSizePicker();
    }

    private void setUpThemePicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values()));//setting element of choice in the themepicker
        themePicker.setValue(viewFactory.getColorTheme());//set the theme picker with the default value-->default value with which the ColorTheme is initialized in ViewFactory
        
    }

    private void setUpSizePicker() {
        fontSizePicker.setMin(0);
        fontSizePicker.setMax(FontSize.values().length-1);//FontSize.values() returns an array of enums
        fontSizePicker.setValue(viewFactory.getFontSize().ordinal());//ordinal gives the position of the enum element in the enum declaration(where the initial constant is assigned an ordinal of zero)
        fontSizePicker.setMinorTickCount(0);
        fontSizePicker.setMajorTickUnit(1);
        fontSizePicker.setBlockIncrement(1);
        fontSizePicker.setSnapToTicks(true);//slider always stops at a tick even if it is droped btw. two ticks
        fontSizePicker.setShowTickMarks(true);
        fontSizePicker.setShowTickLabels(true);
        
        fontSizePicker.setLabelFormatter(new StringConverter<Double>() { 
            //Converter defines conversion behavior between strings and objects(if any type).
            //The type of objects and formats of strings are defined by the subclasses of Converter.
            @Override
            public String toString(Double object) {//Converts the object provided into its string form.
                int i = object.intValue();//returns value of double as an int-->this represent the actual position of the slider
                return FontSize.values()[i].toString();//returns the name of the enum constant at position [i]
            }

            @Override
            public Double fromString(String string) {//Converts the string provided into an object defined by the specific converter.
                return null;
            }
        });
        fontSizePicker.valueProperty().addListener((obs, oldVal, newVal) -> {//slider switches faster
            fontSizePicker.setValue(newVal.intValue());
        });
    }
    
}
