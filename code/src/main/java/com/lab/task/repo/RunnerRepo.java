package com.lab.task.repo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lab.task.model.Runner;


@Stateless
public class RunnerRepo {
    @PersistenceContext
	protected EntityManager entityManager;
    
	public RunnerRepo() {}
    public void insertRuner(Runner runner) {
        entityManager.persist(runner);
    }
    
    public void updateRunner(Runner runner) {
    	entityManager.merge(runner);
    }
    
	public List<Runner> selectAvailiableRunner(){
		return entityManager.createQuery("SELECT m FROM Runner m WHERE m.status = true", Runner.class).getResultList();
	}
	
	public Runner getRunnerById(Long id) {
		return entityManager.find(Runner.class, id);
	}
	
	public List<Runner> selectAllRunner(){
		return entityManager.createQuery("SELECT m From Runner m ",Runner.class).getResultList();
	}
    public boolean CheckRunnerExist(String mail,String password) {
    	List<Runner> myList = new ArrayList<Runner>();
    	myList=selectAllRunner();
        for (int i = 0; i < myList.size(); i++) {
            Runner element = myList.get(i);
            if(element.getEmail().equals(mail)){
            	return false;
            }
            if(element.getEmail().equals(mail)&&element.getPassword().equals(password)) {
            	return false;
            }
        }
        return true;
    }
    public Runner GetSpacificRunner(String mail,String password) {
    	List<Runner> myList = new ArrayList<Runner>();
    	myList=selectAllRunner();
        for (int i = 0; i < myList.size(); i++) {
            Runner element = myList.get(i);
            if(element.getEmail().equals(mail)&&element.getPassword().equals(password)) {
            	return element ;
            }
        }return null;
    }

}
