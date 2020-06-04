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
public enum ColorTheme {
    LIGHT,
    DEFAULT,
    DARK;

    public static String getCssPath(ColorTheme colorTheme) {
        switch (colorTheme){
            case LIGHT:
            return "css/themeLight.css";
            case DEFAULT:
            return "css/themeDefault.css";
            case DARK:
            return "css/themeDark.css";
            default://default always necessary
            return null;
        }
    }
}
