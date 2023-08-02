package com.lab.task.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Resturant.class)
public abstract class Resturant_ {

	public static volatile SingularAttribute<Resturant, String> name;
	public static volatile SetAttribute<Resturant, Orders> orders;
	public static volatile SingularAttribute<Resturant, Long> id;
	public static volatile SingularAttribute<Resturant, Long> ownerId;
	public static volatile SetAttribute<Resturant, Meal> meals;

}

