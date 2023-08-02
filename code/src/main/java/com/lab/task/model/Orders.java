package com.lab.task.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Orders {
	@Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private long id;
    
    @Column(name = "login_time")
    private String Date;
    
    private float total_price;
    private String order_status;
     
    @ManyToOne
    private Runner RunnerData;
    
    @ManyToOne
    private Resturant resturantData;
   
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "MealesXOrders",
              joinColumns = @JoinColumn(name = "orderId"),
                inverseJoinColumns = @JoinColumn(name="mealID"))
	private Set<Meal> items;    
    
    public Orders() {}
   
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getTotal_price() {
		return total_price;
	}
	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}
    
	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public Resturant getResturantData() {
		return resturantData;
	}

	public void setResturantData(Resturant resturantData) {
		this.resturantData = resturantData;
	}

	public Set<Meal> getItems() {
		return items;
	}

	public void setItems(Set<Meal> items) {
		this.items = items;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public Runner getRunnerData() {
		return RunnerData;
	}

	public void setRunnerData(Runner runnerData) {
		RunnerData = runnerData;
	}

   
	
}
