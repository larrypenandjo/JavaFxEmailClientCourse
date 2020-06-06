/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller;

/**
 *
 * @author Larry
 */
public enum EmailLoginResult {
    /*different types of result when login to emailaccount*/
    SUCCESS,
    FAILED_BY_CREDENTIALS,
    FAILED_BY_NETWORK,
    FAILED_BY_UNEXPECTED_ERROR;
}
