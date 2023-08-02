package com.lab.task.repo;

import java.util.List;

import javax.ejb.Stateless;

import com.lab.task.model.Meal;
import com.lab.task.model.User;
import com.lab.task.model.Resturant;

@Stateless
public class resturantOwnerRepo extends  userRepo{
	public resturantOwnerRepo () {}
	
//	
//    public void insertOwnerSignIn(LastSignin Owner) {
//        entityManager.persist(Owner);
//    }
    
    public void insertResturant(Resturant rest) {
    	entityManager.persist(rest);
    }
    
    public void insertMenu(Meal meal) {
    	entityManager.persist(meal);
    }
    
    public void updateResturant(Resturant rest) {
    	entityManager.merge(rest);
    }
    public void updateMeal(Meal meal) {
    	entityManager.merge(meal);
    }
    
	public List<User> selectResturantOwners(){
		return entityManager.createQuery("SELECT m From User m where m.role LIKE 'ResturantOwner%'",User.class).getResultList();
	}
	
//	public List<LastSignin> selecSignInOwners(){
//		return entityManager.createQuery("SELECT m From LastSignin m ",LastSignin.class).getResultList();
//	}
//	
	
	public List<Resturant> getAllResturant() {
		return entityManager.createQuery("SELECT m FROM Resturant m ",Resturant.class).getResultList();
	}
	public Resturant getResturantById(Long id) {
		return entityManager.find(Resturant.class, id);
	}
	public Meal getMealById(Long id) {
		return entityManager.find(Meal.class, id);
	}
	public void deleteMealById(Long id) {
		Meal meal =new Meal();
	  meal=entityManager.find(Meal.class, id);
	   entityManager.remove(meal);
	}
	public List<Meal> getResturantMeal() {
		return entityManager.createQuery("SELECT m FROM Meal m ",Meal.class).getResultList();
	}	
}
