package com.lab.task.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Meal {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private long id;
    
   private String name;
   private float price;

   @ManyToMany
   private Set<Orders> meals;
   
   @ManyToOne
    private Resturant resturantId;
   
    public Meal() {}
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	public Resturant getResturantId() {
		return resturantId;
	}
	public void setResturantId(Resturant resturantId) {
		this.resturantId = resturantId;
	}
	public Set<Orders> getMeals() {
		return meals;
	}
	public void setMeals(Set<Orders> meals) {
		this.meals = meals;
	}

    
	
    
	
}
