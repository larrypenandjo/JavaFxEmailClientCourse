/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module JavaFxEmailClientCourse {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens javafxemailclientcourse to javafx.fxml;
    exports javafxemailclientcourse;
}
