package com.lab.task.repo;

import java.util.List;

import javax.ejb.Stateless;

import com.lab.task.model.Resturant;
import com.lab.task.model.User;

@Stateless
public class customerRepo extends  userRepo {	
	
	public customerRepo() {}
  
	public List<User> selectCustomer(){
		return entityManager.createQuery("SELECT m From User m where m.role LIKE 'Customer%'",User.class).getResultList();
	}
	
	public Resturant getResturantById(Long id) {
		return entityManager.find(Resturant.class, id);
	}
	
} 