package com.lab.task.model;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Runner {
	
    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Long id;
    
    private String name;
    private String email;
    private String password;
	private boolean status;
	private float delivery_fees;

	
	@OneToMany(mappedBy = "RunnerData",fetch = FetchType.EAGER)
    private Set<Orders> orders;
	   
	public Runner() {}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public float getDelivery_fees() {
		return delivery_fees;
	}
	public void setDelivery_fees(float delivery_fees) {
		this.delivery_fees = delivery_fees;
	}
	public Set<Orders> getOrders() {
		return orders;
	}
	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
	
	
}