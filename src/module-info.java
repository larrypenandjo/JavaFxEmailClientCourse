/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module JavaFxEmailClientCourse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.swt;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;
    requires java.mail;
    requires activation;
    
    opens javafxemailclientcourse to javafx.fxml;
    opens javafxemailclientcourse.view to javafx.fxml;
    opens javafxemailclientcourse.controller to javafx.fxml;
    opens javafxemailclientcourse.model to javafx.base;
    
    exports javafxemailclientcourse;
    exports javafxemailclientcourse.controller;
}
