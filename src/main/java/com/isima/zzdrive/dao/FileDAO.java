/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.isima.zzdrive.dao;

import com.isima.zzdrive.model.File;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jeremy
 */
@Repository
public class FileDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List getFilesByUserId(int id) {
        List list = getSessionFactory().getCurrentSession()
                                            .createQuery("from File as file where file.userid = :userid")
                                            .setParameter("userid", id).list();
        return list;
    }

    public void addFile(File file) {
        getSessionFactory().getCurrentSession().save(file);
    }

    public void deleteFile(File file) {
        getSessionFactory().getCurrentSession().delete(file);
    }

    public void updateFile(File file) {
        getSessionFactory().getCurrentSession().update(file);
    }
    
}
