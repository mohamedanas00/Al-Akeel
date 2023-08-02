package com.lab.task.model;
import javax.persistence.*;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Resturant  {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private long id;
   
	@OneToMany(mappedBy = "resturantId",fetch = FetchType.EAGER)
    private Set<Meal> meals;
	
	@OneToMany(mappedBy = "resturantData",fetch = FetchType.EAGER)
    private Set<Orders> orders;

    private String name;
   
    private long ownerId;
   
    public Resturant () {
    }
 
    

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public long getOwnerId() {
		return ownerId;
	}



	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Set<Meal> getMeals() {
		return meals;
	}

	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}

	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}


	

 
}
