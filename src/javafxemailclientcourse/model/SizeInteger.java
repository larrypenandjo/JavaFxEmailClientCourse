/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.model;

/**
 *
 * @author Larry
 */
/*class to ajust the displayed size of the email in KB and MB*/
public class SizeInteger implements Comparable<SizeInteger>{

    private int size;

    public SizeInteger(int size) {
        this.size = size;
    }

    //override the toString method because its the one that is call for displaying data in our GUI
    @Override
    public String toString() {
        if (size <= 0) {
            return "0";
        } else if (size < 1024) {
            return size + " B";
        } else if (size < 1048576) {
            return size / 1024 + " kB";
        } else {
            return size / 1048576 + " MB";
        }
    }

    @Override
    public int compareTo(SizeInteger o) {//to sort message by their size and not by the string values (which is the return value of the overriden toString method)
        if(size>o.size){
            return 1;
        }else if(o.size>size){
            return -1;
        }else{
            return 0;
        }
    }
}
