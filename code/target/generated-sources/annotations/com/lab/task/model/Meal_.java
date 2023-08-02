package com.lab.task.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Meal.class)
public abstract class Meal_ {

	public static volatile SingularAttribute<Meal, Resturant> resturantId;
	public static volatile SingularAttribute<Meal, Float> price;
	public static volatile SingularAttribute<Meal, String> name;
	public static volatile SingularAttribute<Meal, Long> id;
	public static volatile SetAttribute<Meal, Orders> meals;

}

