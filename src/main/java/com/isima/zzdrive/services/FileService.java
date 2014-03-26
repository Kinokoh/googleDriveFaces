/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zzdrive.services;

import com.isima.zzdrive.dao.FileDAO;
import com.isima.zzdrive.model.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arnaud CHALIEZ and Jérémy BOUNY
 */
@Service("FileService")
@Transactional(readOnly = true)
public class FileService {
    
    @Autowired
    FileDAO fileDAO;
    
    public FileService() {
    }

    public FileDAO getFileDAO() {
        return fileDAO;
    }

    public void setUserDAO(FileDAO fileDAO) {
        this.fileDAO = fileDAO;
    }
    
    public List getFileUser(int inUserId) {
        return fileDAO.getFilesByUserId(inUserId);
    }
    
    @Transactional(readOnly = false)
    public void saveFile(File inFile) {
        fileDAO.addFile(inFile);
    }
            
}
