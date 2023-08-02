package com.lab.task.repo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lab.task.model.Resturant;
import com.lab.task.model.User;

@Stateless
public class userRepo {
    @PersistenceContext
	protected EntityManager entityManager;

    public userRepo() {
    }

    public void insert(User user) {
        entityManager.persist(user);
    }

    
    public List<User> selectUser() {
        return entityManager.createQuery("SELECT u FROM User u ", User.class).getResultList();
    }
    
    public boolean CheckUserExist(String mail,String password) {
    	List<User> myList = new ArrayList<User>();
    	myList=selectUser();
        for (int i = 0; i < myList.size(); i++) {
            User element = myList.get(i);
            if(element.getEmail().equals(mail)){
            	return false;
            }
            if(element.getEmail().equals(mail)&&element.getPassword().equals(password)) {
            	return false;
            }
        }
        return true;
    }
    public User GetSpacificUser(String mail,String password) {
    	List<User> myList = new ArrayList<User>();
    	myList=selectUser();
        for (int i = 0; i < myList.size(); i++) {
            User element = myList.get(i);
            if(element.getEmail().equals(mail)&&element.getPassword().equals(password)) {
            	return element ;
            }
        }return null;
    }
	public List<Resturant> getAllResturant() {
		return entityManager.createQuery("SELECT m FROM Resturant m ",Resturant.class).getResultList();}
	

}
        
