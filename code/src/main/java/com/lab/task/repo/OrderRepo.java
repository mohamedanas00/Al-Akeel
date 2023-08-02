package com.lab.task.repo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lab.task.model.Orders;

@Stateless
public class OrderRepo {
    @PersistenceContext
	protected EntityManager entityManager;
    
    public OrderRepo() {}
	
    public void insertOrder(Orders order) {
        entityManager.persist(order);
    }
    public void updateOrder(Orders order) {
        entityManager.merge(order);
    }
    
	public Orders getOrderById(Long id) {
		return entityManager.find(Orders.class, id);
	}
    

}
