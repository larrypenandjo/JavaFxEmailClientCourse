/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxemailclientcourse.controller.services;

import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.mail.Folder;

/**
 *
 * @author Larry
 */
/*update message folders dynamically*/
public class FolderUpdaterService extends Service{
    
    private List<Folder> folderList;

    public FolderUpdaterService(List<Folder> folderList) {
        this.folderList = folderList;
    }    
    

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for(;;){//service starts when our program starts and never end because we will always check if there are new message folders
                    try{
                        Thread.sleep(5000);//ask for new events every 5s
                        for(Folder folder:folderList){
                            if(folder.getType() !=Folder.HOLDS_FOLDERS && folder.isOpen()){
                                folder.getMessageCount();
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
    }
    
}
