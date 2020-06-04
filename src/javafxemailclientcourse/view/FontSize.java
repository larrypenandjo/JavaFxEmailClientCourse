/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.view;

/**
 *
 * @author Larry
 */
public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;
    
    public static String getCssPath(FontSize fontSize){
        switch(fontSize){
            case MEDIUM:
                return "css/fontMedium.css";
            case BIG:
                return "css/fontBig.css";
            case SMALL:
                return "css/fontSmall.css";
            default://default always necessary
                return null;
        }
    }
}
